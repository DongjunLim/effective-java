package item1;

import java.util.Objects;

public class Truck extends Car{
    private static Truck instance;
    private int capacity;


    private Truck(String name, Engine engine, String color, int capacity) {
        super(name, engine, color);
        this.capacity = capacity;
    }

    // 장점 1: 식별 가능, 장점 2: 인스턴스 통제
    public static Truck getDumpTruckInstance(){
        if(Objects.isNull(instance)){
            Engine dumpTruckEngine = Engine.getH310Instance();
            String color = "Black";
            int capacity = 100;
            instance = new Truck("DumpTruck", dumpTruckEngine, color, capacity);
        }
        
        return instance;
    }


    @Override
    public String toString(){
        return "car name: " + this.getName()
                + "   engine: " + this.getEngine().getName()
                + "   Color: " + this.getColor()
                + "   Capacity: " + this.getCapacity();
    }

    public int getCapacity() {
        return capacity;
    }
}
