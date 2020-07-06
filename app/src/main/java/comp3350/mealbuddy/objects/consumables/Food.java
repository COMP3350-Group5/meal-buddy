package comp3350.mealbuddy.objects.consumables;

import java.util.List;


public class Food extends Edible {
    public int weight; //grams & ml

    public Food(String name, List<String> labels) {
        super(name, labels);
    }

    public void setWeight(int weight){
        this.weight = weight;
    }

    @Override
    public String toString(){
        return name + " " + weight + "g";
    }
}
