package comp3350.mealbuddy.objects.consumables;

import android.os.Build;
import android.util.ArraySet;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Meal extends Edible {

    public Set<FoodIntPair> ediblesInMeal;

    public Meal(String name, List<String> labels) {
        super(name, labels);
        ediblesInMeal = new HashSet<>();
    }

    public Meal(String name) {
        super(name, new ArrayList<String>());
        ediblesInMeal = new HashSet<>();
    }

    public void updateFood(Food toUpdate, int quantity){
        if (quantity <= 0) ediblesInMeal.remove(toUpdate);
        else ediblesInMeal.add(new FoodIntPair(toUpdate, quantity));
    }

    public void removeFood(Food toRemove) {
        ediblesInMeal.remove(toRemove);
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
