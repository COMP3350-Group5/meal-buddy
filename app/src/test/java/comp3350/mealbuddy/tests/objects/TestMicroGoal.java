package comp3350.mealbuddy.tests.objects;

import comp3350.mealbuddy.objects.consumables.Edible;
import comp3350.mealbuddy.objects.consumables.Food;
import comp3350.mealbuddy.objects.goals.MacroGoal;
import comp3350.mealbuddy.objects.goals.MicroGoal;
import comp3350.mealbuddy.objects.goals.Goal;
import comp3350.mealbuddy.objects.consumables.Edible.Micros;



import org.junit.Assert;

import java.util.ArrayList;
import java.util.List;


public class TestMicroGoal {
    public static final int lowerBound = 5;
    public static final int upperBound = 10;


    public void test_CalorieGoalConstructor() {
        Micros micros = Micros.Calcium;
        MicroGoal goal = new MicroGoal(lowerBound, upperBound, micros);

        Assert.assertTrue(goal.lowerBound == lowerBound);
        Assert.assertTrue(goal.upperBound == upperBound);
        Assert.assertTrue(goal.id == micros);
    }

    public void testEquals_sameReferences_shouldPass() {
        Micros micros = Micros.Potassium;
        MicroGoal goal = new MicroGoal(lowerBound, upperBound, micros);
        MicroGoal testGoal = goal;

        Assert.assertTrue(goal.equals(testGoal));
    }

    public void testEquals_differentObjects_shouldFail() {
        List<String> labels = new ArrayList<String>();
        Micros micros = Micros.Choline;
        MicroGoal goal = new MicroGoal(lowerBound, upperBound, micros);
        Food food = new Food("Eggs", labels);

        Assert.assertFalse(goal.equals(food));
    }

    public void testEquals_sameId_shouldPass() {
        Micros micros = Micros.Iron;
        MicroGoal goal = new MicroGoal(lowerBound, upperBound, micros);
        MicroGoal testGoal = new MicroGoal(lowerBound, upperBound, micros);


        Assert.assertTrue(goal.equals(testGoal));
    }

    public void testEquals_nullObject_shouldFail() {
        Micros micros = Micros.Choline;
        MicroGoal goal = new MicroGoal(lowerBound, upperBound, micros);

        MicroGoal testGoal = null;

        try {
            Assert.assertTrue(goal.equals(testGoal));
        } catch (NullPointerException e) {
            Assert.assertTrue(true);
        }
    }
}
