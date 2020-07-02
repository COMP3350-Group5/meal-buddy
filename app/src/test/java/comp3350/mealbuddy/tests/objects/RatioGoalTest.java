package comp3350.mealbuddy.tests.objects;

import org.junit.Test;

import comp3350.mealbuddy.objects.Goal;
import comp3350.mealbuddy.objects.RatioGoal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class RatioGoalTest {


    @Test
    public void test_constructor_equalLowerUpperBound_errorThrown() {
        //arrange
        int lowerBound = 50;
        int upperBound = 50;
        String nameOfTracked = "Fat";

        //act
        try {
            Goal goal = new RatioGoal(lowerBound, upperBound, nameOfTracked);
            fail();
        }
        //assert
        catch (IllegalArgumentException e){
            assertTrue(true);
        }

    }


}
