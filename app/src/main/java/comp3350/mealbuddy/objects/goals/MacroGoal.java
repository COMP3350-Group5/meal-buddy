/****************************************
 * MacroGoal
 * A goal to reach a certain amount of calories from a Macro in a day
 ****************************************/
package comp3350.mealbuddy.objects.goals;

import comp3350.mealbuddy.objects.consumables.Edible.Macros;


import androidx.annotation.NonNull;

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
        super(lowerBound, upperBound, goalType);
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
                "id=" + id + "g"+
                ", lowerBound=" + lowerBound +
                ", upperBound=" + upperBound +
                '}';
    }

    /*
     * equals
     * Override for the equals method.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MacroGoal)) return false;
        MacroGoal macroGoal = (MacroGoal) o;
        return id == macroGoal.id;
    }

}
