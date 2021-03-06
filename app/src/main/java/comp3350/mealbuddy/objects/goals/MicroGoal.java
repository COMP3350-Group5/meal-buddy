/****************************************
 * MicroGoal
 * A goal to reach a certain amount of a Micro in a day
 ****************************************/
package comp3350.mealbuddy.objects.goals;

import androidx.annotation.NonNull;

import comp3350.mealbuddy.objects.consumables.Edible.Micros;

public class MicroGoal extends Goal {

    public Micros id;

    /*
     * Constructor
     * Creates a MicroGoal
     * Parameters:
     *     @param lowerBound - The lower bound for the goal.
     *     @param upperBound - The upper bound for the goal.
     *     @param id - The Micro associated with the goal.
     */
    public MicroGoal(int lowerBound, int upperBound, Micros id) {
        super(lowerBound, upperBound, GoalType.QUANTITY, id);
        if (id == null)
            throw new IllegalArgumentException("Micro must be specified");
        this.id = id;
    }

    /*
     * toString
     * Override for the toString method.
     * Return string representation of the goal.
     */
    @Override
    @NonNull
    public String toString() {
        return "MicroGoal{" +
                "id=" + id +
                ", lowerBound=" + lowerBound + "mg" +
                ", upperBound=" + upperBound + "mg" +
                '}';
    }


}
