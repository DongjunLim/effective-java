## [Item 3] private 생성자나 열거 타입으로 싱글턴임을 보증하라.

## 싱글턴
- 인스턴스를 오직 하나만 생성할 수 있는 클래스
- 시스템에서 유일성을 보장해야 하는 경우, 혹은 자원의 낭비를 막기 위해 생성한다.
- 싱글턴 객체는 mock 객체로 대체할 수 없기 때문에 클라이언트를 테스트하기 어려워 질 수 있다.


## 싱글턴을 생성하는 방법

### 1. private static final 필드 방식
```java
// 코드 1 - 생성자 + static 방식
public class Elvis {
    public static final Elvis INSTANCE = new Elvis();
    private Elvis() {}
    
    public void leaveTheBuilding() {}
}
```
<h4>코드 1의 장점</h4>
- 해당 클래스가 싱글턴임이 API에 명백히 드러남
- 코드가 간결함

<h4>코드 1의 단점</h4>
- 권한이 있는 클라이언트는 리플렉션 API인 
`AccessibleObject.setAccessible`을 사용해 private생성자를 호출할 수 있음. <br><br>
- 클래스를 직렬화하려면 readResolve 메서드를 제공해야함.
    ```java
      // 코드 1-1 - readResolve 메서드 예
      public Object readResolve() {
          return INSTANCE;
      }  
    ```
---

### 2. static factory method 방식
```java
// 코드 2 - 정적 팩터리 메서드 방식
public class Elvis {
    private static final Elvis INSTANCE = new Elvis();
    private Elvis() {}
    public Elvis getInstance(){ return INSTANCE; }
    
    public void leaveTheBuilding(){}
}
```
<h4>코드 2의 장점</h4>
- API를 바꾸지 않고도 싱글턴이 아니게 변경할 수 있음(확장성 + 유연성)
- 정적 팩터리를 제네릭 싱글턴 팩터리로 만들 수 있음.
- 정적 팩터리의 메서드 참조를 공급자로 사용할 수 있음.
    ```java
      // 코드 2-1 - supplier로 사용 예시
      public class Elvis {
        private static final Elvis INSTANCE = new Elvis();
        private Elvis() {}
        public static Elvis getInstance() {
          return INSTANCE;
        }
      
        public static Supplier<Elvis> get() {
          return () -> INSTANCE;
        }
      }
  ```

<h4>코드 2의 단점</h4>
- 코드 1과 마찬가지로 권한이 있는 클라이언트 리플렉션 API인 
`AccessibleObject.setAccessible`을 사용해 private생성자를 호출할 수 있음.시
- 코드 1과 마판가지로 직렬화를 위해 transient를 선언하고 readResolve 메서드를 구현해야함.
 <br><br>
---

### 3. enum 타입 방식
```java
// 코드 2 - 정적 팩터리 메서드 방식
public enum Elvis {
    INSTANCE;
    
    public void leaveTheBuilding(){}
}
```
<h4>코드 3의 장점</h4>
- 간결하다.
- 추가적인 코드 없이 직렬화 할 수 있다.
- 리플렉션 공격에서 제 2의 인스턴스가 생기는 일을 막아준다.


<h4>코드 3의 단점</h4>
- 만드려는 싱글턴이 enum 외의 다른 클래스를 상속해야 한다면 이 방법은 사용 할 수 없다.
- Context 의존성이 있는 환경일 경우 싱글턴 초기화 과정에 Context 의존성이 끼어들 수 있다.
<br><br>
---

### 4. LazyHolder 방식
```java
// 코드 4 - LazyHolder 방식
public class Elvis {
    private Elvis(){}
    public static Elvis getInstance(){
        return LazyHolder.INSTANCE;
    }
    
    private static class LazyHolder {
        private static final Elvis INSTANCE = new Elvis();   
    }
}
```

<h4>코드 4의 장점</h4>
- getInstance가 호출될때 내부 클래스인 LazyHolder Class가 로딩되어 초기화가 진행되는데, Class를 로딩하고 초기화 하는 시점은 Thread-safe가 보장된다.

<h4>코드 4의 단점</h4>
- 코드 1, 2와 마찬가지로 리플렉션을 이용한 내부 생성자 호출 공격에 취약하다.
- 역직렬화시 새로운 객체가 생성된다.
<br><br>




