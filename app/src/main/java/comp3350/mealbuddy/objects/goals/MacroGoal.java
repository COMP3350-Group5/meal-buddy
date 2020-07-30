/****************************************
 * MacroGoal
 * A goal to reach a certain amount of calories from a Macro in a day
 ****************************************/
package comp3350.mealbuddy.objects.goals;

import androidx.annotation.NonNull;

import comp3350.mealbuddy.objects.consumables.Edible.Macros;

public class MacroGoal extends Goal {

    public Macros id;

    /*
     * Constructor
     * Creates a MacroGoal
     * Parameters:
     *     @param lowerBound - The lower bound for the goal.
     *     @param upperBound - The upper bound for the goal.
     *     @param goalType - The type of goal.
     *     @param id - The Macro associated associated with the goal.
     */
    public MacroGoal(int lowerBound, int upperBound, GoalType goalType, Macros id) {
        super(lowerBound, upperBound, goalType, id);
        if (id == null)
            throw new IllegalArgumentException("Macro must be specified");
        this.id = id;
    }

    /*
     * toString
     * Override for the toString method.
     */
    @Override
    @NonNull
    public String toString() {
        return "MacroGoal{" +
                "id=" + id +
                ", lowerBound=" + lowerBound + "g" +
                ", upperBound=" + upperBound + "g" +
                '}';
    }


}
