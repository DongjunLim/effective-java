package item1;

public class ConstructorExampleTest {

    public static void main(String[] args){
        ConstructorExampleTest ce = new ConstructorExampleTest();
        ce.test();
    }

    public void test(){
        Truck dumpTruck = new Truck("dumptruck", new Engine("H310", 100), "Yellow", 10);
        System.out.println(dumpTruck.toString());
    }


    class Engine{
        private String name;
        private int power;

        public Engine(String name, int power){
            this.name = name;
            this.power = power;
        }

        public String getName() {
            return name;
        }

        public int getPower() {
            return power;
        }
    }


    class Car{
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

    class Truck extends Car{
        private int capacity;
        public Truck(String name, Engine engine, String color, int capacity) {
            super(name, engine, color);
            this.capacity = capacity;
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
}
