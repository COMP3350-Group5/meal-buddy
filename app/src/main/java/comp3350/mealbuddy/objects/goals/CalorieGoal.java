/****************************************
 * CalorieGoal
 * A goal to reach a certain amount of total Calories
 ****************************************/
package comp3350.mealbuddy.objects.goals;

import androidx.annotation.Nullable;

public class CalorieGoal extends Goal {
    /*
     * Constructor
     * Create a new CalorieGoal
     */
    public CalorieGoal(int lowerBound, int upperBound) {
        super(lowerBound, upperBound, GoalType.QUANTITY);
    }

    /*
     * equals
     * Override for the equals method.
     */
    @Override
    //Can only have one total calorie goal.
    public boolean equals(@Nullable Object obj) {
        return obj instanceof CalorieGoal;
    }

}
