package comp3350.mealbuddy.tests.objects;

import comp3350.mealbuddy.objects.consumables.Food;
import comp3350.mealbuddy.objects.goals.MacroGoal;
import comp3350.mealbuddy.objects.goals.Goal;
import comp3350.mealbuddy.objects.consumables.Edible.Macros;
import java.util.ArrayList;
import java.util.List;


import org.junit.Assert;


public class TestMacroGoal {
    public static final int lowerBound = 5;
    public static final int upperBound = 10;


    public void test_macroGoalConstructor() {
        Macros macros = Macros.Protein;
        MacroGoal goal = new MacroGoal(lowerBound, upperBound, Goal.GoalType.QUANTITY, macros);

        Assert.assertTrue(goal.lowerBound == lowerBound);
        Assert.assertTrue(goal.upperBound == upperBound);
        Assert.assertTrue(goal.goalType == Goal.GoalType.QUANTITY);
        Assert.assertTrue(goal.id == macros);
    }

    public void testEquals_sameReferences_shouldPass() {
        Macros macros = Macros.Carbohydrates;
        MacroGoal goal = new MacroGoal(lowerBound, upperBound, Goal.GoalType.RATIO, macros);
        MacroGoal testGoal = goal;

        Assert.assertTrue(goal.equals(testGoal));
    }

    public void testEquals_differentObjects_shouldFail() {
        List<String>labels = new ArrayList<String>();
        Macros macros = Macros.Carbohydrates;
        MacroGoal goal = new MacroGoal(lowerBound, upperBound, Goal.GoalType.RATIO, macros);
        Food food = new Food("Eggs", labels);

        Assert.assertFalse(goal.equals(food));
    }

    public void testEquals_sameId_shouldPass() {
        Macros macros = Macros.Fat;
        MacroGoal goal = new MacroGoal(lowerBound, upperBound, Goal.GoalType.QUANTITY, macros);
        MacroGoal testGoal = new MacroGoal(lowerBound, upperBound, Goal.GoalType.RATIO, macros);

        Assert.assertTrue(goal.equals(testGoal));
    }

    public void testEquals_nullObject_shouldFail() {
        Macros macros = Macros.Fat;
        MacroGoal goal = new MacroGoal(lowerBound, upperBound, Goal.GoalType.QUANTITY, macros);

        MacroGoal testGoal = null;

        try {
            Assert.assertTrue(goal.equals(testGoal));
        } catch (NullPointerException e) {
            Assert.assertTrue(true);
        }
    }
}
