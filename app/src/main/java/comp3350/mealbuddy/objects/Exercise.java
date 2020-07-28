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
    public Intensity intensity;

    /*
     * Constructor
     * Creates an Exercise
     * Parameters:
     *     @param name - The name of the exercise.
     */
    public Exercise(String name, double duration, Intensity intensity) {
        if (name == null) {
            throw new IllegalArgumentException("Exercise name can not be null");
        }
        this.name = name;
        this.duration = duration;
        this.intensity = intensity;
    }

    /*
     * Copy Constructor
     * Creates an Exercise from an existing Exercise
     * Parameters:
     *     @param original - The original exercise.
     */
    public Exercise(Exercise original) {
        this.name = original.name;
        this.duration = original.duration;
        this.intensity = original.intensity;
    }
}
