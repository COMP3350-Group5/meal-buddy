package comp3350.mealbuddy.business;

import java.util.ArrayList;
import java.util.List;

import comp3350.mealbuddy.objects.Day;
import comp3350.mealbuddy.objects.Edible;
import comp3350.mealbuddy.objects.Goal;
import comp3350.mealbuddy.objects.LabelGoal;
import comp3350.mealbuddy.objects.MacroGoal;
import comp3350.mealbuddy.objects.MicroGoal;

public class GoalTracker {

    /*
    * getPassedGoals
    * takes a day and returns a list of passed goals
     */
    public static List<Goal> getPassedGoals(Calculator calc, List<Goal> goals){
        ArrayList<Goal> passedGoals = new ArrayList<>();
        int actualVal, upperVal, lowerVal;
        for (Goal g: goals){
            //determine what kind of goal it is and set values accordingly
            if (g instanceof MacroGoal){
                MacroGoal macGoal = (MacroGoal)g;
                actualVal = calc.getMacroCalories(macGoal.id);
                upperVal = macGoal.upperBound;
                lowerVal = macGoal.lowerBound;
            } else if (g instanceof MicroGoal){
                MicroGoal micGoal = (MicroGoal) g;
                actualVal = calc.getMicroCalories(micGoal.id);
                upperVal = micGoal.upperBound;
                lowerVal = micGoal.lowerBound;
            } else {
                LabelGoal labGoal = (LabelGoal)g;
                actualVal = calc.getLabelCalories(labGoal.id);
                upperVal = labGoal.upperBound;
                lowerVal = labGoal.lowerBound;
            }

            //if its a ratio, convert to ratio
            actualVal = Goal.GoalType.RATIO == g.goalType
                    ? (int)(100 * (actualVal /(double)calc.getTotalCalories()))
                    : actualVal;

            //add the goal to the list if it passes
            if (withinBounds(actualVal, lowerVal, upperVal))
                passedGoals.add(g);
        }

        return passedGoals;
    }

    private static boolean withinBounds(int val, int lowerBound, int upperBound){
        return (val >= lowerBound && val <= upperBound);
    }
}


