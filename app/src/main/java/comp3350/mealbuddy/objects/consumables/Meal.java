/****************************************
 * Meal
 * An object containing a list of FoodIntPairs
 ****************************************/
package comp3350.mealbuddy.objects.consumables;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Meal extends Edible {

    private ArrayList<EdibleIntPair> ediblesInMeal;

    /*
     * Constructor
     * Create a meal from a name and a list of labels
     * Parameters:
     *     @param name - The name of the meal.
     *     @param labels - The list of labels associated with the meal.
     */
    public Meal(String name, List<String> labels) {
        super(name, labels);
        ediblesInMeal = new ArrayList<>();
    }

    /*
     * Creates a meal with a name and creates an empty list of labels.
     * Parameters:
     *     @param name - The name of the meal.
     */
    public Meal(String name) {
        super(name, new ArrayList<>());
        ediblesInMeal = new ArrayList<>();
    }

    /*
     * iterator
     * Returns an iterator for a meal object
     */
    @NonNull
    @Override
    public Iterator<Edible> iterator() {
        return new MealIterator(ediblesInMeal);
    }

    /*
     * setEdible
     * Updates the quantity of a Food in the meal, or adds it if not already present.
     * Parameters:
     *     @param food - The food we are updating
     *     @param quantity - The quantity of the food we are updating to
     */
    public void setEdible(Edible edible, int quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("can't set quantity to a negative number:" + quantity);
        }
        ediblesInMeal.remove(edible);   //easier to remove then add to update the value//otherwise have to check if it contains or not and add if it doesnt or just update if it does
        if (quantity > 0) {
            EdibleIntPair edibleIntPair = new EdibleIntPair(edible, quantity);
            ediblesInMeal.add(edibleIntPair);
        }
    }

    /*
     * Gets the edible int pair associated with the edible
     * Parameters:
     *     @param edible - The name of the edible to look for
     */
    public EdibleIntPair getEdibleIntPair(Edible edible) {
        if (ediblesInMeal.contains(edible))
            return ediblesInMeal.get(ediblesInMeal.indexOf(edible));
        return null;
    }

    /*
     * containsFood
     * Checks if the meal contains the food.
     * Parameters:
     *     @param food - The name of the food.
     */
    public boolean containsEdible(Edible edible) {
        return ediblesInMeal.contains(edible); //returns true as long as food is the same
    }

    /*
     * toString
     * Override for the toString method.
     */
    @Override
    public String toString() {
        String meal = name + ": ";
        for (EdibleIntPair pair : ediblesInMeal) {
            meal += (pair.quantity + " " + pair.edible + " ");
        }
        return meal;
    }

    /*
     * getMacroGrams
     * returns the sum of all the grams of a certain macro in the meal.
     * This is recursive and will traverse all levels of the meal.
     * accounts for the quantity of each edible
     * Parameters:
     *     @param macro - the macro to get the grams for
     */
    @Override
    public int getMacroGrams(Macros macro) {
        int grams = 0;
        Edible edible;
        int quantity;
        for (EdibleIntPair edibleIntPair : ediblesInMeal) {
            edible = edibleIntPair.edible;
            quantity = edibleIntPair.quantity;

            grams += edible.getMacroGrams(macro) * quantity;
        }
        return grams;
    }


    /*
     * getMicroGrams
     * returns the sum of all the grams of a certain micro in the meal.
     * This is recursive and will traverse all levels of the meal.
     * Takes into account the quantity of each edible
     * Parameters:
     *     @param micro - the micro to get the grams for
     */
    @Override
    public int getMicroGrams(Micros micro) {
        int grams = 0;
        Edible edible;
        int quantity;
        for (EdibleIntPair edibleIntPair : ediblesInMeal) {
            edible = edibleIntPair.edible;
            quantity = edibleIntPair.quantity;

            grams += edible.getMicroGrams(micro) * quantity;
        }
        return grams;
    }

    /****************************************
     * getEdibleIntPairIterator()
     * An iterator for the edibleIntPairs in meal
     ****************************************/
    public Iterator<EdibleIntPair> getEdibleIntPairIterator() {
        return ediblesInMeal.iterator();
    }

    /****************************************
     * MealIterator
     * An iterator for the edibles in meals
     ****************************************/
    class MealIterator implements Iterator<Edible> {

        private Iterator<EdibleIntPair> edibleIntPairIterator;

        /*
         * constructor
         * creates new meal iterator.  Does not recursively iterate
         * Parameters:
         *     @param list - the list of edible int pairs from the meal.
         *     We only wish to iterate over the edibles and are not concerned
         *     with how many of the edibles are in the list for the iterator
         */
        public MealIterator(ArrayList<EdibleIntPair> list) {
            edibleIntPairIterator = list.listIterator();
        }

        /*
         * hasNext
         * returns true if there is another edible in the meal
         */
        @Override
        public boolean hasNext() {
            return edibleIntPairIterator.hasNext();
        }


        /*
         * next
         * returns the next edible in the meal
         */
        @Override
        public Edible next() {
            Edible edible = null;
            if (edibleIntPairIterator.hasNext()) {
                edible = edibleIntPairIterator.next().edible;
            }
            return edible;
        }
    }


}
