/****************************************
 * Exercise
 * Class that contains an exercise amount and intensity
 ****************************************/

package comp3350.mealbuddy.objects;

public class Exercise {

    public enum Intensity {
        Low, Medium, High,
    }

    public String name;
    public double duration;

    /*
     * Constructor
     * Creates an Exercise
     * Parameters:
     *     @param name - The name of the exercise.
     */
    public Exercise(String name) {
        this.name = name;
    }
}
