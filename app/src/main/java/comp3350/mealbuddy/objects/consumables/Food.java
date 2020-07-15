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

    //used for testing should contain all of them at all times
    public boolean containsMacro(Macros macro) {
        return macros.containsKey(macro);
    }

    //used for testing should always contain them all
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


    @Override
    public int getMacroGrams(Macros macro) {
        return macros.get(macro);
    }

    @Override
    public int getMicroGrams(Micros micro) {
        return micros.get(micro);
    }


}
