/****************************************
 * FoodIntPair
 * Object containing a food, and a quantity of that food
 ****************************************/
package comp3350.mealbuddy.objects.consumables;

public class FoodIntPair {

    public Food food;
    public Integer quantity;

    /*
     * Constructor
     * Create a FoodIntPair object.
     */
    public FoodIntPair(Food food, Integer quantity) {
        this.food = food;
        this.quantity = quantity;
    }

    /*
     * equals
     * Override for the equals method.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FoodIntPair)) return false;
        FoodIntPair that = (FoodIntPair) o;
        return food.equals(that.food);
    }

}
