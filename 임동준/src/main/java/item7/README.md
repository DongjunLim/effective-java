## [Item 7] 다 쓴 객체 참조를 해제하라

## 핵심요약

자바에서는 가비지컬렉터가 메모리 누수를 관리해준다. 그러나 몇몇 특수한 경우에는 사용자가 직접 메모리 누수를 관리해야 한다.
<br><br>

### 자바에서의 메모리 누수 원인들

1. 스스로 메모리를 직접 관리하는 클래스
2. 캐시 참조
3. 리스너와 콜백



### 1. 클래스가 직접 메모리를 관리 하는 경우

```java
// 코드 1 - 메모리 누수를 고려 안하고 구현한 스택
class Stack {
    public Object[] elements;
    protected int size = 0;
    private static final int DEFAULT_INITIAL_CAPACITY = 4;

    public Stack(){
        elements = new Object[DEFAULT_INITIAL_CAPACITY];
    }

    public void push(Object e){
        ensureCapacity();
        elements[size++] = e;
    }

    public Object pop() {
        if(size == 0)
            throw new EmptyStackException();
        return elements[--size];
    }

    private void ensureCapacity(){
        if(elements.length - 1 == size){
            elements = Arrays.copyOf(elements, 2 * size + 1);
        }
    }
}
```

위 코드는 메모리 누수를 고려 안하고 구현한 스택 클래스다. pop 메서드를 자세히 보면 elements배열의 포인터를 감소시키면서 원소를 반환한다. 이 과정에서 포인터변수 size를 1 줄여 이전 원소를 가리키게 하지만 **elements배열에서 실제 원소가 사라지지는 않았다**. 

직접 구현한 Stack에 5개의 객체를 삽입하고 2개의 객체를 pop 해보겠다.

```java
// 코드 2 - 메모리 누수를 고려하지 않은 스택 테스트
Stack s = new Stack();
for(int i = 1; i <=5; i++)
  s.push(new Node());
s.pop();        
s.pop();
System.out.println("참조 해제를 안했을 경우");    
System.out.println(Arrays.toString(s.elements));
```

##### 출력결과

<img width="955" alt="스크린샷 2020-08-18 오후 11 54 30" src="https://user-images.githubusercontent.com/40556417/90528967-29332c00-e1ae-11ea-826b-98b59e0085e2.png">

Stack의 참조변수가 5개 그대로 있는 남아 있는 것을 확인할 수 있다(null 값은 스택의 사이즈를 가변적으로 늘리는 과정에서 생겨난 것이므로 무시해도 좋다.). 이 경우 4, 5번째 원소는 더 이상 사용하지 않음에도 불구하고, elements 배열에서 참조변수를 가지고 있기 때문에 **가비지 컬렉터의 메모리 해제 대상에 포함되지 않는다**.

**이제 메모리 누수를 고려해 pop 메서드를 다시 구현해보자.**

```java
// 코드 3 - 메모리 누수를 고려해 pop 메서드를 재정의한 newStack 클래스
class newStack extends Stack{
    public newStack(){
        super();
    }

    @Override
    public Object pop() {
        if(size == 0)
            throw new EmptyStackException();
        Object result = elements[--size];
        elements[size] = null;
        return result;
    }
}
```

위 코드는 pop 메서드를 호출할 때마다 반환값을 elements 배열에서 지웠다. 

위에서 테스트 한 것 처럼 똑같이 객체 5개를 삽입하고 2개를 pop 해보겠다.

```java
newStack ns = new newStack();
   
for(int i = 1; i <=5; i++)
  ns.push(new Node());

ns.pop();
ns.pop();

System.out.println("참조 해제를 했을 경우");
System.out.println(Arrays.toString(ns.elements));
```

##### 출력결과

##### <img width="714" alt="스크린샷 2020-08-18 오후 11 54 44" src="https://user-images.githubusercontent.com/40556417/90529007-318b6700-e1ae-11ea-9b2e-deb0f68bd017.png">

위 출력결과와 같이 참조변수가 3개만 남은 것을 확인할 수 있다.

elements 배열의 크기는 동일하지만 heap 영역에 있는 2개의 Node 인스턴스를 더 이상 아무도 참조하지 않으므로 가비지컬렉터가 인스턴스에 할당한 메모리를 회수할 것이다. 

<br><br>

### 2. 캐시 참조의 경우

#### 캐시의 유효기간을 아는 경우

캐시 외부에서 키(key)를 참조하는 동안만 엔트리가 살아 있는 캐시가 필요한 상황이라면 `WeakHashMap` 을 사용해 캐시를 만들면 사용을 마치고 가비지 컬렉터가 수거해간다.

#### 캐시의 유효기간을 모르는 경우

이 경우는 시간이 지날수록 엔트리의 가치를 떨어뜨리는 방식을 사용하며, 주기적으로 쓰지 않는 엔트리를 확인하며 청소를 해야 해줘야 한다.  주로 백그라운드 스레드를 활용하거나 캐시에 새 엔트리를 추가할 때 부수 작업으로 수행하는 방법이 있다.



### 3. 리스너나 콜백의 경우

캐시의 경우와 비슷하다. 콜백을 `weakHashMap` 에 키로 저장하면 가비지 컬렉터가 알아서 해결해준다. 