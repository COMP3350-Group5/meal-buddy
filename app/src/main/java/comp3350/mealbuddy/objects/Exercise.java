/****************************************
 * Exercise
 * Class that contains an exercise amount and intensity
 ****************************************/

package comp3350.mealbuddy.objects;

import java.util.Objects;

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
     * toString
     * toString function
     * Return:
     *     The string representation of the exercise
     */
    @Override
    public String toString() {
        return name + " " + duration + " mins, " + intensity + " intensity ";
    }

    /*
     * copyConstructor
     * Return:
     *     The new exercise
     * Parameters:
     *      @param original - the exercise to copy
     */
    public Exercise(Exercise original) {
        this.name = original.name;
        this.duration = original.duration;
        this.intensity = original.intensity;
    }

    /*
     * equals
     * Return:
     *     true or false
     * Parameters:
     *      @param o - the object to compare to
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Exercise exercise = (Exercise) o;
        return name.equals(exercise.name) &&
                intensity == exercise.intensity;
    }

}
