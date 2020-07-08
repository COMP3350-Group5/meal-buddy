package comp3350.mealbuddy.objects.consumables;

import java.util.ArrayList;
import java.util.List;


public class Food extends Edible {
    public int weight; //grams & ml

    public Food(String name, List<String> labels) {
        super(name, labels);
    }

    public Food(String name) {
        super(name, new ArrayList<String>());
    }

    public void setWeight(int weight){
        if (weight < 0) {
            weight = 0;
        }
        this.weight = weight;
    }

    @Override
    public String toString(){
        return name + " " + weight + "g";
    }
}
