package comp3350.mealbuddy.objects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Meal extends Edible{
    HashMap<Food, Integer> foodInMeal;

    public Meal(String name, List<String> labels) {
        super(name, labels);
        foodInMeal = new HashMap<>();
    }

    public void updateFood(Food toAdd, int quantity){
        foodInMeal.put(toAdd, quantity);
    }

    public void removeFood(Food toRemove) {
        foodInMeal.remove(toRemove);
    }

    @Override
    public String toString(){
        String meal = name + ": ";
        for (HashMap.Entry<Food, Integer> food : foodInMeal.entrySet()) {
            meal += (food.getKey().toString() + " quantity: " + food.getValue().toString());
        }
        return meal;
    }
}
