/****************************************
 * Edible
 * Abstract class for edible items
 ****************************************/
package comp3350.mealbuddy.objects.consumables;

import androidx.annotation.NonNull;

import java.util.EnumMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public abstract class Edible implements Iterable<Edible> {

    public String name;
    public List<String> labels;

    public enum Macros {
        Fat, Carbohydrates, Protein, Alcohol
    }

    public enum Micros {
        Iron, Zinc, VitaminA, VitaminB12, VitaminC, VitaminE, Calcium, Choline, Magnesium, Sodium, Potassium, Niacin
    }


    public abstract int getMacroGrams(Macros macro);

    public abstract int getMicroGrams(Micros micro);

    @NonNull
    @Override
    public abstract Iterator<Edible> iterator();

    /*
     * Constructor
     * Creates an edible object.
     * Parameters:
     *     @param name - The name of the edible
     *     @param labels - The labels associated with the edible.
     */
    public Edible(String name, List<String> labels) {
        if (name == null || labels == null)
            throw new NullPointerException("Name and labels can't be null");
        this.name = name;
        this.labels = labels;
    }

    public static Edible copyEdible(Edible e) {
        Edible result = null;
        if (e instanceof Food) {
            result = new Food((Food)e);
        }
        else if (e instanceof Meal) {
            result = new Meal((Meal)e);
        }
        return result;
    }

    /*
     * containsLabel
     * checks if a food contains a given Label
     * Parameters:
     *     @param label - Name of label
     */
    public boolean containsLabel(String label) {
        return labels.contains(label);
    }

    /*
     * equals
     * Override for the equals method.
     */
    @Override
    public boolean equals(Object o) {
        boolean isEqual = false;
        if (o instanceof Edible) {
            Edible edible = (Edible) o;
            isEqual = name.equals(edible.name);
        } else if (o instanceof EdibleIntPair) {
            EdibleIntPair edibleIntPair = (EdibleIntPair) o;
            isEqual = name.equals(edibleIntPair.edible.name);
        }
        return isEqual;
    }


}
