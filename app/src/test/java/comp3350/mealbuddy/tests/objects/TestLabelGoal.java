package comp3350.mealbuddy.tests.objects;

import comp3350.mealbuddy.objects.consumables.Edible;
import comp3350.mealbuddy.objects.consumables.Food;
import comp3350.mealbuddy.objects.goals.LabelGoal;
import comp3350.mealbuddy.objects.goals.Goal;
import comp3350.mealbuddy.objects.goals.MicroGoal;

import org.junit.Assert;

import java.util.ArrayList;
import java.util.List;

public class TestLabelGoal {
    public static final int lowerBound = 5;
    public static final int upperBound = 10;


    public void test_CalorieGoalConstructor() {
        LabelGoal goal = new LabelGoal(lowerBound, upperBound, Goal.GoalType.QUANTITY, "Low Fat");

        Assert.assertTrue(goal.lowerBound == lowerBound);
        Assert.assertTrue(goal.upperBound == upperBound);
        Assert.assertTrue("Low Fat".equals(goal.id));
        Assert.assertTrue(goal.goalType == Goal.GoalType.QUANTITY);
    }


    public void testEquals_sameReferences_shouldPass() {
        LabelGoal goal = new LabelGoal(lowerBound, upperBound, Goal.GoalType.QUANTITY, "Low Fat");
        LabelGoal testGoal = goal;

        Assert.assertTrue(goal.equals(testGoal));
    }

    public void testEquals_differentObjects_shouldFail() {
        List<String> labels = new ArrayList<String>();
        LabelGoal goal = new LabelGoal(lowerBound, upperBound, Goal.GoalType.RATIO, "High Fat");
        Food food = new Food("Eggs", labels);


        Assert.assertFalse(goal.equals(food));
    }

    public void testEquals_sameId_shouldPass() {
        LabelGoal goal = new LabelGoal(lowerBound, upperBound, Goal.GoalType.RATIO, "High Fat");
        LabelGoal testGoal = new LabelGoal(lowerBound, upperBound, Goal.GoalType.QUANTITY, "High Fat");

        Assert.assertTrue(goal.equals(testGoal));
    }

    public void testEquals_nullObject_shouldFail() {
        LabelGoal goal = new LabelGoal(lowerBound, upperBound, Goal.GoalType.RATIO, "High Fat");
        LabelGoal testGoal = null;

        try {
            Assert.assertTrue(goal.equals(testGoal));
        } catch (NullPointerException e) {
            Assert.assertTrue(true);
        }
    }

}
