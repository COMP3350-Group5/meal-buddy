package comp3350.mealbuddy.objects;

import java.util.Objects;

public class FoodIntPair {

    public Food food;
    public Integer quantity;

    public FoodIntPair(Food food, Integer quantity) {
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

}
