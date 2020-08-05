package item3;

public class Code2 {
    private static final Code2 INSTANCE = new Code2();
    private Code2(){}
    public Code2 getInstance(){
        return INSTANCE;
    }
    public void foo(){}
}
