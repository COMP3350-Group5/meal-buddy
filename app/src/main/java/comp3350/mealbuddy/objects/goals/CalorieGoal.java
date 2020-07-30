/****************************************
 * CalorieGoal
 * A goal to reach a certain amount of total Calories
 ****************************************/
package comp3350.mealbuddy.objects.goals;

import androidx.annotation.NonNull;

import static comp3350.mealbuddy.objects.goals.CalorieGoal.CalorieId.CALORIE_GOAL;

public class CalorieGoal extends Goal {

    /*
     * Constructor
     * Create a new CalorieGoal
     * Parameters:
     *     @param lowerBound - The lower bound for the goal.
     *     @param upperBound - The upper bound for the goal.
     */
    public CalorieGoal(int lowerBound, int upperBound) {
        super(lowerBound, upperBound, GoalType.QUANTITY, CALORIE_GOAL);
    }

    /*
     * toString
     * returns string interpretation of the goal
     * Return:
     *      returns the string interpretation
     */
    @NonNull
    @Override
    public String toString() {
        return "CalorieGoal{" +
                "lowerBound=" + lowerBound +
                ", upperBound=" + upperBound +
                '}';
    }

    public enum CalorieId {
        CALORIE_GOAL
    }
}
