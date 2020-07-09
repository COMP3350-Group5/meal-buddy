/****************************************
 * Edible
 * Abstract class for edible items
 ****************************************/
package comp3350.mealbuddy.objects.consumables;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public abstract class Edible {

    public String name;
    public List<String> labels;
    public Map<Micros, Integer> micros;
    public Map<Macros, Integer> macros;

    public enum Macros{
        Fat, Carbohydrates, Protein, Alcohol
    }

    public enum Micros{
        Iron, Zinc, VitaminA, VitaminB12, VitaminC,  VitaminE, Calcium, Choline, Magnesium, Sodium, Potassium, Niacin
    }

    /*
     * Constructor
     * Creates an edible object.
     * Parameters:
     *     @param name - The name of the edible
     *     @param labels - The labels associated with the edible.
     */
    public Edible(String name, List<String> labels) {
        this.name = name;
        this.labels = labels;
        micros = makeMicros();
        macros = makeMacros();
    }


    /*
     * makeMacros
     * Function to create the macros enum map for an edible
     */
    private static Map<Macros, Integer> makeMacros(){
        Map<Macros, Integer> macroMap = new EnumMap<>(Macros.class);
        Macros[] macroArr = Macros.values();
        for (Macros macro:macroArr) {
            macroMap.put(macro, 0);
        }
        return macroMap;
    }

    /*
     * makeMicros
     * Function to create the micros enum map for an edible
     */
    private static Map<Micros, Integer> makeMicros(){
        Map<Micros, Integer> microMap = new EnumMap<>(Micros.class);
        Micros[] microArr = Micros.values();
        for (Micros micro:microArr) {
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
    public void updateMacro(Macros macro, int amount){
        macros.put(macro, amount);
    }

    /*
     * updateMicro
     * Updates a micro for an edible.
     * Parameters:
     *     @param micro - The micro to update.
     *     @param amount - The amount to update to.
     */
    public void updateMicro(Micros micro, int amount){
        micros.put(micro, amount);
    }

    /*
     * equals
     * Override for the equals method.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || ! (o instanceof Edible)) return false;
        Edible edible = (Edible) o;
        return name == edible.name ;
    }
}
