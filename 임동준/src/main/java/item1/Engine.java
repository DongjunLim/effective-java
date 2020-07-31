package item1;

public class Engine{
    private String name;
    private int power;

    private Engine(String name, int power){
        this.name = name;
        this.power = power;
    }

    public static Engine getH310Instance(){
        String name = "H310";
        int power = 100;

        return new Engine(name, power);
    }

    public String getName() {
        return name;
    }

    public int getPower() {
        return power;
    }
}