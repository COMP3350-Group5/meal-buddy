package comp3350.mealbuddy.objects;

import android.os.Build;
import android.util.ArraySet;

import androidx.annotation.RequiresApi;
import androidx.core.util.Pair;

import java.util.List;
import java.util.Set;

public class Meal extends Edible{

    public Set<Pair<Food, Integer>> foodInMeal;

    @RequiresApi(api = Build.VERSION_CODES.M)
    public Meal(String name, List<String> labels) {
        super(name, labels);
        foodInMeal = new ArraySet<>();
    }

    public void updateFood(Food toAdd, int quantity){
        foodInMeal.add(new Pair<toAdd, quantity>);
    }

    public void removeFood(Food toRemove) {
        foodInMeal.remove(toRemove);
    }
}
