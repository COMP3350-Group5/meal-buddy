/****************************************
 * FoodIntPair
 * Object containing a food, and a quantity of that food
 ****************************************/
package comp3350.mealbuddy.objects.consumables;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.Objects;

public class EdibleIntPair {

    public Edible edible;
    public Integer quantity;

    /*
     * Constructor
     * Create a FoodIntPair object.
     * Parameters:
     *     @param edible - The edible
     *     @param quantity - The quantity of the edible
     */
    public EdibleIntPair(Edible edible, Integer quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("The quantity of the can not be less than or equal to zero");
        }
        this.edible = edible;
        this.quantity = quantity;
    }

    /*
     * equals
     * Override for the equals method.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EdibleIntPair)) return false;
        EdibleIntPair that = (EdibleIntPair) o;
        return edible.equals(that.edible);
    }


}
