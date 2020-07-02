package comp3350.mealbuddy.tests.objects;

import org.junit.Test;

import comp3350.mealbuddy.objects.QuantityGoal;
import comp3350.mealbuddy.objects.Goal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class GoalTest {


    @Test
    public void test_constructor_negativeAmount_throwException() {
        //arrange
        int targetAmount = -3;
        int variance = 42;
        String nameOfTracked = "Fat";

        //act
        try {
            Goal goal = new QuantityGoal(targetAmount, variance, nameOfTracked);
            fail("Expected Exception");
        }
        catch (IllegalArgumentException e){
            assertTrue(true);
        }

    }

    @Test
    public void test_constructor_negativeVariance_throwException() {
        //arrange
        int targetAmount = 50;
        int variance = -42;
        String nameOfTracked = "Fat";

        try{
            Goal goal = new QuantityGoal(targetAmount, variance, nameOfTracked);
            fail("Expected Exception");
        }
        catch (IllegalArgumentException e){
            assertTrue(true);
        }
    }

    @Test
    public void test_constructor_nullName_throwException() {
        //arrange
        int targetAmount = 50;
        int variance = 42;
        String nameOfTracked = null;

        try{
            Goal goal = new QuantityGoal(targetAmount, variance, nameOfTracked);
            fail("Expected Exception");
        }
        catch (NullPointerException e){
            assertTrue(true);
        }
    }

    @Test
    public void test_constructor_emptyName_throwException() {
        //arrange
        int targetAmount = 50;
        int variance = 3;
        String nameOfTracked = "";

        try{
            Goal goal = new QuantityGoal(targetAmount, variance, nameOfTracked);
            fail("Expected Exception");
        }
        catch (IllegalArgumentException e){
            assertTrue(true);
        }
    }

    @Test
    public void test_constructor_tooBigVariance_throwException() {
        //arrange
        int targetAmount = 40;
        int variance = 42;
        String nameOfTracked = "Fat";

        try{
            Goal goal = new QuantityGoal(targetAmount, variance, nameOfTracked);
            fail("Expected Exception");
        }
        catch (IllegalArgumentException e){
            assertTrue(true);
        }
    }




}
