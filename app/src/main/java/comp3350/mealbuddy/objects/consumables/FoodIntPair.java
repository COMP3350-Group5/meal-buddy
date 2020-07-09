package comp3350.mealbuddy.objects.consumables;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.Objects;

public class FoodIntPair {

    public Food food;
    public Integer quantity;

    public FoodIntPair(Food food, Integer quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("The quantity of the can not be less than or equal to zero");
        }
        this.food = food;
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FoodIntPair)) return false;
        FoodIntPair that = (FoodIntPair) o;
        return food.equals(that.food);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public int hashCode() {
        return Objects.hash(food.name);
    }

}
