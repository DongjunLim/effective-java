package item1;

public class StaticFactoryMethodExampleTest {

    public static void main(String[] args){
        StaticFactoryMethodExampleTest sFME = new StaticFactoryMethodExampleTest();
        sFME.test();
    }

    public void test(){
        Truck dumpTruck = Truck.getDumpTruckInstance();
        System.out.println(dumpTruck.toString());
    }
}
