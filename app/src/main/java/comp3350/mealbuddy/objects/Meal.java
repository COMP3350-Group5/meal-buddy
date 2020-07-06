package comp3350.mealbuddy.objects;

import android.os.Build;
import android.util.ArraySet;

import androidx.annotation.RequiresApi;
import androidx.core.util.Pair;

import java.util.List;
import java.util.Set;

public class Meal extends Edible{

    public Set<FoodIntPair> foodInMeal;

    @RequiresApi(api = Build.VERSION_CODES.M)
    public Meal(String name, List<String> labels) {
        super(name, labels);
        foodInMeal = new ArraySet<>();
    }

    public void updateFood(Food toUpdate, int quantity){
        if (quantity <= 0) foodInMeal.remove(toUpdate);
        else foodInMeal.add(new FoodIntPair(toUpdate, quantity));
    }

    public void removeFood(Food toRemove) {
        foodInMeal.remove(toRemove);
    }

    @Override
    public String toString(){
        String meal = name + ": ";
        for (FoodIntPair pair: foodInMeal) {
            meal += (pair.quantity + " " + pair.food + " ");
        }
        return meal;
    }
}
