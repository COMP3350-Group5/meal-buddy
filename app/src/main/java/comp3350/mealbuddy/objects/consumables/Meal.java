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
     * Parameters:
     *     @param name - The name of the meal.
     *     @param labels - The list of labels associated with the meal.
     */
    public Meal(String name, List<String> labels) {
        super(name, labels);
        ediblesInMeal = new HashSet<>();
    }

    /*
     * Creates a meal with a name and creates an empty list of labels.
     * Parameters:
     *     @param name - The name of the meal.
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
    public void updateFood(Food foodToUpdate, int quantity){
        FoodIntPair foodIntPair = new FoodIntPair(foodToUpdate, quantity);
        ediblesInMeal.remove(foodIntPair);
        if(quantity>0)
            ediblesInMeal.add(foodIntPair);
    }

    /*
     * containsFood
     * Checks if the meal contains the food.
     * Parameters:
     *     @param food - The name of the food.
     */
    public boolean containsFood(Food food){
        return ediblesInMeal.contains( new FoodIntPair(food, 1) ); //returns true as long as food is the same
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
