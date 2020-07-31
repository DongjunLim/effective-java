package item1;

public class Car {
    private String name;
    private Engine engine;
    private String color;

    public Car(String name, Engine engine, String color){
        this.name = name;
        this.engine = engine;
        this.color = color;
    }

    public Engine getEngine(){
        return this.engine;
    }

    public void setEngine(Engine engine){
        this.engine = engine;
    }

    public String getColor() {
        return color;
    }

    @Override
    public String toString(){
        return "engine: " + this.getEngine().getName() + " Color: " + this.getColor();
    }

    public String getName() {
        return name;
    }
}