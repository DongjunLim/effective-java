package item2.pizza;

import static item2.pizza.NyPizza.Size.SMALL;
import static item2.pizza.Pizza.Topping.*;

public class SampleCodeExample {
    public static void main(String[] args){
        NyPizza np = new NyPizza.Builder(SMALL)
                .addTopping(SAUSAGE)
                .addTopping(ONION)
                .build();
        
        Calzone cz = new Calzone.Builder()
                .addTopping(HAM)
                .sauceInside()
                .build();
    }
}
