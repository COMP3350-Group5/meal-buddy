/****************************************
 * Meal
 * An object containing a list of FoodIntPairs
 ****************************************/
package comp3350.mealbuddy.objects.consumables;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Meal extends Edible {

    public Set<FoodIntPair> ediblesInMeal;

    /*
     * Constructor
     * Create a meal from a name and a list of labels
     */
    public Meal(String name, List<String> labels) {
        super(name, labels);
        ediblesInMeal = new HashSet<>();
    }

    /*
     * Creates a meal with a name and creates an empty list of labels.
     */
    public Meal(String name) {
        super(name, new ArrayList<String>());
        ediblesInMeal = new HashSet<>();
    }

    /*
     * updateFood
     * Updates the quantity of a Food in the meal, or adds it if not already present.
     * Parameters:
     *     @param food - The food we are updating
     *     @param quantity - The quantity of the food we are updating to
     */
    public void updateFood(Food food, int quantity){
        if (quantity <= 0) ediblesInMeal.remove(food);
        else ediblesInMeal.add(new FoodIntPair(food, quantity));
    }

    /*
     * removeFood
     * Removes a food from the meal
     * Parameters:
     *     @param removeFood - The food to remove
     */
    public void removeFood(Food toRemove) {
        ediblesInMeal.remove(toRemove);
    }

    /*
     * toString
     * Override for the toString method.
     */
    @Override
    public String toString(){
        String meal = name + ": ";
        for (FoodIntPair pair: ediblesInMeal) {
            meal += (pair.quantity + " " + pair.food + " ");
        }
        return meal;
    }
}
