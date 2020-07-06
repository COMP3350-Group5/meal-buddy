package comp3350.mealbuddy.objects.consumables;

import android.os.Build;
import android.util.ArraySet;

import androidx.annotation.RequiresApi;

import java.util.List;
import java.util.Set;

public class Meal extends Edible {

    public Set<FoodIntPair> ediblesInMeal;

    @RequiresApi(api = Build.VERSION_CODES.M)
    public Meal(String name, List<String> labels) {
        super(name, labels);
        ediblesInMeal = new ArraySet<>();
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
