package comp3350.mealbuddy.tests.objects;
import comp3350.mealbuddy.objects.goals.CalorieGoal;
import comp3350.mealbuddy.objects.goals.Goal;

import org.junit.Assert;

public class TestCalorieGoal {
    public static final int lowerBound = 5;
    public static final int upperBound = 10;


    public void test_calorieGoalConstructor() {
        CalorieGoal goal = new CalorieGoal(lowerBound, upperBound);

        Assert.assertTrue(goal.lowerBound == lowerBound);
        Assert.assertTrue(goal.upperBound == upperBound);
    }

    public void testEquals_calorieObject_shouldPass() {
        CalorieGoal goal = new CalorieGoal(lowerBound, upperBound);

        Assert.assertTrue(goal.equals(new CalorieGoal(lowerBound, upperBound)));
    }

    public void testEquals_nullObject_shouldFail() {
        CalorieGoal goal = new CalorieGoal(lowerBound, upperBound);
        CalorieGoal nullGoal = null;
        try {
            goal.equals(nullGoal);
            Assert.fail();
        } catch (NullPointerException e) {
            Assert.assertTrue(true);
        }
    }
}
