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
    * takes a day and returns a list of passed goals
     */
    public static List<Goal> getPassedGoals(Calculator calc, List<Goal> goals){
        if (calc == null || goals == null) {
            throw new NullPointerException("Calculator and goals must be not null");
        }

        ArrayList<Goal> passedGoals = new ArrayList<>();
        for (Goal g : goals){
            //if its a ratio, convert to ratio
            int actualVal = (Goal.GoalType.RATIO == g.goalType)
                    ? (int)(100 * (getAmount(calc, g)/(double)calc.getTotalCalories()))
                    : getAmount(calc, g);

            //add the goal to the list if it passes
            if (withinBounds(actualVal, g.lowerBound, g.upperBound))
                passedGoals.add(g);
        }
        return passedGoals;
    }

    private static boolean withinBounds(int val, int lowerBound, int upperBound){
        return (val >= lowerBound && val <= upperBound);
    }

    private static int getAmount(Calculator calc, Goal g){
        //determine what kind of goal it is and set values accordingly
        if (g instanceof MacroGoal)
            return calc.getMacroCalories(((MacroGoal)g).id);
        else if (g instanceof MicroGoal)
            return calc.getMicroMgs(((MicroGoal)g).id);
        else if (g instanceof LabelGoal)
            return calc.getLabelCalories(((LabelGoal)g).id);
        else    //calorie goal
            return calc.getTotalCalories();
    }
}


