/****************************************
 * Food
 * Object containing one single food item
 ****************************************/
package comp3350.mealbuddy.objects.consumables;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


public class Food extends Edible {
    public int weight; //grams & ml
    private Map<Micros, Integer> micros;
    private Map<Macros, Integer> macros;

    /*
     * Constructor
     * Creates a food object.
     * Parameters:
     *     @param name - The name of the food.
     *     @param labels - The labels associated with the food.
     */
    public Food(String name, List<String> labels) {
        super(name, labels);
        initNutrientLists();
    }

    /*
     * Constructor
     * Creates a food object, and creates an empty list of labels.
     * Parameters:
     *     @param name - The name of the food.
     */
    public Food(String name) {
        super(name, new ArrayList<>());
        initNutrientLists();
    }

    /*
     * Copy Constructor
     * Creates a food object, and creates an empty list of labels.
     * Parameters:
     *     @param name - The name of the food.
     */
    public Food(Food original) {
        super(original.name, original.labels);

        Map<Macros, Integer> macroMap = new EnumMap<>(Macros.class);
        Macros[] macroArr = Macros.values();
        for (Macros macro : macroArr) {
            macroMap.put(macro, original.getMacroGrams(macro));
        }
        macros = macroMap;

        Map<Micros, Integer> microMap = new EnumMap<>(Micros.class);
        Micros[] microArr = Micros.values();
        for (Micros micro : microArr) {
            microMap.put(micro, original.getMicroGrams(micro));
        }
        micros = microMap;
    }

    /*
     * iterator
     * inits all the nutrients of a food to 0
     */
    private void initNutrientLists() {
        micros = makeMicros();
        macros = makeMacros();
    }


    /*
     * makeMacros
     * Function to create the macros enum map for an edible
     */
    private static Map<Macros, Integer> makeMacros() {
        Map<Macros, Integer> macroMap = new EnumMap<>(Macros.class);
        Macros[] macroArr = Macros.values();
        for (Macros macro : macroArr) {
            macroMap.put(macro, 0);
        }
        return macroMap;
    }

    /*
     * makeMicros
     * Function to create the micros enum map for an edible
     */
    private static Map<Micros, Integer> makeMicros() {
        Map<Micros, Integer> microMap = new EnumMap<>(Micros.class);
        Micros[] microArr = Micros.values();
        for (Micros micro : microArr) {
            microMap.put(micro, 0);
        }
        return microMap;
    }


    /*
     * updateMacro
     * Updates a macro for an edible.
     * Parameters:
     *     @param macro - The macro to update.
     *     @param amount - The amount to update to.
     */
    public void updateMacro(Macros macro, int amount) {
        if (amount < 0)
            amount = 0;
        macros.put(macro, amount);
    }

    /*
     * updateMicro
     * Updates a micro for an edible.
     * Parameters:
     *     @param micro - The micro to update.
     *     @param amount - The amount to update to.
     */
    public void updateMicro(Micros micro, int amount) {
        if (amount < 0)
            amount = 0;
        micros.put(micro, amount);
    }

    /*
     * iterator
     * Returns an iterator for a food object
     */
    @NonNull
    @Override
    public Iterator<Edible> iterator() {
        return new FoodIterator(this);
    }

    /*
     * setWeight
     * Sets the weight for one serving of this food.
     * Parameters:
     *     @param weight - The weight in grams (mL if it's a liquid)
     */
    public void setWeight(int weight) {
        if (weight < 0) {
            weight = 0;
        }
        this.weight = weight;
    }

    /*
     * containsMacro
     * determines if the food contains a macro. Should always be true.
     * Used for testing purposes.
     * Parameters:
     *     @param macro - the macro to check
     */
    public boolean containsMacro(Macros macro) {
        return macros.containsKey(macro);
    }

    /*
     * containsMicro
     * determines if the food contains a micro. Should always be true.
     * Used for testing purposes.
     * Parameters:
     *     @param micro - the micro to check
     */
    public boolean containsMicro(Micros micro) {
        return micros.containsKey(micro);
    }

    /*
     * toString
     * Override for the toString method.
     */
    @Override
    public String toString() {
        return name + " " + weight + "g";
    }

    /*
     * getMacroGrams
     * gets the grams of a Macro
     * Parameters:
     *     @param macro - Macro to get the grams of
     */
    @Override
    public int getMacroGrams(Macros macro) {
        return macros.get(macro);
    }

    /*
     * getMicroGrams
     * gets the grams of a Micro
     * Parameters:
     *     @param micro - Micro to get the grams of
     */
    @Override
    public int getMicroGrams(Micros micro) {
        return micros.get(micro);
    }

    /**
     * Iterator for iterating over a food
     */
    class FoodIterator implements Iterator<Edible> {

        private boolean finishedIteration = false;
        private Food food;

        /*
         * constructor
         * creates new food iterator
         * Parameters:
         *     @param food - food to 'iterate' over
         */
        public FoodIterator(Food food) {
            this.food = food;
        }

        /*
         * hasNext
         * returns true if there is still the food to iterate over
         */
        @Override
        public boolean hasNext() {
            return !finishedIteration;
        }

        /*
         * next
         * returns the food if its the first time it is called
         * after the first time it returns null
         */
        @Override
        public Edible next() {
            if (!finishedIteration) {
                finishedIteration = true;
                return food;
            }
            return null;
        }

    }


}
