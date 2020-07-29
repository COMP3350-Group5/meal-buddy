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

    public String toString() {
        return name + " " + duration + " mins, " + intensity + " intensity ";
    }
    public Exercise(Exercise original) {
        this.name = original.name;
        this.duration = original.duration;
        this.intensity = original.intensity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Exercise exercise = (Exercise) o;
        return name.equals(exercise.name) &&
                intensity == exercise.intensity;
    }

}
