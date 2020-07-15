package comp3350.mealbuddy.objects.consumables;

import android.os.Build;
import android.util.ArraySet;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Meal extends Edible {

    private Set<FoodIntPair> ediblesInMeal;

    public Meal(String name, List<String> labels) {
        super(name, labels);
        ediblesInMeal = new HashSet<>();
    }

    public Meal(String name) {
        super(name, new ArrayList<String>());
        ediblesInMeal = new HashSet<>();
    }

    public void updateFood(Food foodToUpdate, int quantity){
        FoodIntPair foodIntPair = new FoodIntPair(foodToUpdate, quantity);
        ediblesInMeal.remove(foodIntPair);
        if(quantity>0)
            ediblesInMeal.add(foodIntPair);
    }

    public boolean containsFood(Food food){
        return ediblesInMeal.contains( new FoodIntPair(food, 1) ); //returns true as long as food is the same
    }

    public void addFood(Food foodToAdd, int quantity) {
        FoodIntPair foodIntPair = new FoodIntPair(foodToAdd, quantity);
        if (!ediblesInMeal.contains(foodIntPair)) {
            ediblesInMeal.add(foodIntPair);
        } else {
            updateFood(foodToAdd, quantity);
        }
    }

    public void removeFood(Food foodToRemove, int quantity) {
        FoodIntPair foodIntPair = new FoodIntPair(foodToRemove, quantity);
        if (ediblesInMeal.contains(foodIntPair)) {
            ediblesInMeal.remove(foodIntPair);
        }
    }

    @Override
    public String toString(){
        String meal = name + ": ";
        for (FoodIntPair pair: ediblesInMeal) {
            meal += (pair.quantity + " " + pair.food + " ");
        }
        return meal;
    }
}
