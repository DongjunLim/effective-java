## [Item 5] 자원을 직접 명시하지 말고 의존 객체 주입을 사용하라

## 핵심요약
사용하는 자원에 따라 동작이 달라지는 클래스에는 정적 유틸리티 클래스나 싱글턴 방식이 적합하지 않다. 이럴 때는 **의존 객체 주입 기법**을 사용하자.
<br><br>

### 의존객체주입이란
필요한 인스턴스를 직접 생성하는 것이 아니고 외부에서 주입하여 객체 간 결합도를 줄이고 유연한 코드를 작성하게 하는 방법.
```java
// 코드 1 - 의존객체주입의 예
public class Computer {
    private Cpu cpu;
    
    public Computer(Cpu cpu) {
        this.cpu = cpu;
    }
}
```

### 부적절한 클래스 예시
```java
// 코드 2 - 정적 유틸리티를 잘못 사용한 예
public class SpellChecker {
    private static final Lexicon dictionary = ...;
    private SpellChecker() {}

    private static boolean isValid(String word) {}
    private static List<String> suggestion(String type) {}
}
```
```java
// 코드 3 - 싱글턴을 잘못 사용한 예
public class SpellChecker {
    private final Lexicon dictionary = ...;
    private SpellChecker() {}
    public static SpellChecker INSTANCE = new SpellChecker();

    private static boolean isValid(String word) {}
    private static List<String> suggestion(String type) {}
}
```
위의 코드 2, 3은 여러 종류의 사전이 있을 경우를 생각한다면 좋지 못한 코드다. 여러 종류의 dictionary 객체가 필요할 경우 확장하지 못하기 때문이다.
이렇게 객체가 사용하는 자원에 따라 동작이 달라지는 클래스는 싱글턴 방식이나 정적 유틸리티 방식이 적합하지 않다.
<br><br>

### 의존 객체 주입을 사용한 예시
```java
// 코드 4 - 의존 객체 주입 사용 예
public class SpellChecker {
    private final Lexicon dictionary;
    private SpellChecker(Lexicon dictionary) {
        this.dictionary = Objects.requireNonNull(dictionary);
    }

    private boolean isValid(String word) {}
    private List<String> suggestion(String type) {}
}
```
위의 코드는 외부에서 dictionary 객체를 받아와 내부 dictionary에 주입하여 객체의 의존도를 낮췄다.
이를 통해 Lexicon 클래스에 변경이 생기더라도 Spellchecker를 고치지 않아도 되며, 여러 종류의 dictionary를 만들 수도 있다.