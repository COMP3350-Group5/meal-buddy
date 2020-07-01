package comp3350.mealbuddy.tests.objects;

import org.junit.Test;

import comp3350.mealbuddy.objects.Goal;
import comp3350.mealbuddy.objects.QuantityGoal;
import comp3350.mealbuddy.objects.RatioGoal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class RatioGoalTest {


    @Test
    public void test_constructor_tooSmallVariance_varianceSetTo1() {
        //arrange
        int targetAmount = 50;
        int variance = 0;
        String nameOfTracked = "Fat";

        //act
        Goal goal = new RatioGoal(targetAmount, variance, nameOfTracked);

        //assert
        assertEquals(goal.variance, 1);
    }

    @Test
    public void test_constructor_normalVariance_varianceUnchanged() {
        //arrange
        int targetAmount = 50;
        int variance = 42;
        String nameOfTracked = "Fat";

        //act
        Goal goal = new RatioGoal(targetAmount, variance, nameOfTracked);

        //assert
        assertEquals(goal.variance, 42);
    }

}
