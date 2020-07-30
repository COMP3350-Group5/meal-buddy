/****************************************
 * GoalTracker
 * Class used to track which goals are passed
 ****************************************/
package comp3350.mealbuddy.business;

import java.util.ArrayList;
import java.util.List;

import comp3350.mealbuddy.objects.goals.Goal;
import comp3350.mealbuddy.objects.goals.LabelGoal;
import comp3350.mealbuddy.objects.goals.MacroGoal;
import comp3350.mealbuddy.objects.goals.MicroGoal;

public class GoalTracker {

    /*
     * getPassedGoals
     * takes a calculator and returns a list of passed goals for that day
     * Parameters:
     *     @param calc - The calculator
     *     @param goals - The list of goals to check
     * Return:
     *     The list of passed goals for the day.
     */
    public static List<Goal> getPassedGoals(Calculator calc, List<Goal> goals) {
        if (calc == null || goals == null) {
            throw new NullPointerException("Calculator and goals must be not null");
        }

        ArrayList<Goal> passedGoals = new ArrayList<>();
        for (Goal g : goals) {
            //if its a ratio, convert to ratio
            int actualVal = (Goal.GoalType.RATIO == g.goalType)
                    ? (int) (100 * (getAmount(calc, g) / (double) calc.getTotalCalories()))
                    : getAmount(calc, g);

            //add the goal to the list if it passes
            if (withinBounds(actualVal, g.lowerBound, g.upperBound))
                passedGoals.add(g);
        }
        return passedGoals;
    }

    /*
     * withinBounds
     * Check if a value is in between two numbers.
     * Parameters:
     *     @param val - The value to check
     *     @lowerBound - The lower bound for the comparison.
     *     @upperBound - The upper bound for the comparison.
     * Return:
     *     A boolean indicating if the value is within the bounds (inclusive).
     */
    private static boolean withinBounds(int val, int lowerBound, int upperBound) {
        return (val >= lowerBound && val <= upperBound);
    }

    /*
     * getAmount
     * Takes a calculator and a goal, and returns the amount of calories/mgs from the calculator required by the goal
     * Parameters:
     *     @param calc - The calculator
     *     @param g - the goal
     * Return:
     *     The amount of calories/mgs corresponding to the goal.
     */
    private static int getAmount(Calculator calc, Goal g) {
        if (g instanceof MacroGoal)
            return calc.getMacroCalories(((MacroGoal) g).id);
        else if (g instanceof MicroGoal)
            return calc.getMicroMgs(((MicroGoal) g).id);
        else if (g instanceof LabelGoal)
            return calc.getLabelCalories(((LabelGoal) g).id);
        else    //calorie goal
            return calc.getTotalCalories();
    }
}


