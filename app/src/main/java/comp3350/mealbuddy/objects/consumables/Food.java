/****************************************
 * Food
 * Object containing one single food item
 ****************************************/
package comp3350.mealbuddy.objects.consumables;

import java.util.ArrayList;
import java.util.List;


public class Food extends Edible {
    public int weight; //grams & ml

    /*
     * Constructor
     * Creates a food object.
     * Parameters:
     *     @param name - The name of the food.
     *     @param labels - The labels associated with the food.
     */
    public Food(String name, List<String> labels) {
        super(name, labels);
    }

    /*
     * Constructor
     * Creates a food object, and creates an empty list of labels.
     * Parameters:
     *     @param name - The name of the food.
     */
    public Food(String name) {
        super(name, new ArrayList<String>());
    }

    /*
     * setWeight
     * Sets the weight for one serving of this food.
     * Parameters:
     *     @param weight - The weight in grams (mL if it's a liquid)
     */
    public void setWeight(int weight){
        this.weight = weight;
    }

    /*
     * toString
     * Override for the toString method.
     */
    @Override
    public String toString(){
        return name + " " + weight + "g";
    }
}
