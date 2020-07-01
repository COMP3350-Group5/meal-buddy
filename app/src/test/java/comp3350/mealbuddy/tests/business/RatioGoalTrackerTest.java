package comp3350.mealbuddy.tests.business;

import org.junit.Test;

import comp3350.mealbuddy.business.RatioGoalTracker;
import comp3350.mealbuddy.objects.Goal;
import comp3350.mealbuddy.objects.QuantityGoal;
import comp3350.mealbuddy.objects.RatioGoal;
import comp3350.mealbuddy.persistence.FoodEatenManagerStub;
import comp3350.mealbuddy.persistence.IFoodEatenManager;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class RatioGoalTrackerTest {

    @Test
    public void test_isAchieved_withinUpperBound_returnTrue() {
        //arrange
        int targetAmount = 51;
        int variance = 1;
        String nameOfTracked = "Fat";
        RatioGoal ratioGoal = new RatioGoal(targetAmount, variance, nameOfTracked);

        int amount = 50;
        int total = 100;
        IFoodEatenManager stub = new FoodEatenManagerStub(amount, total);

        //act
        RatioGoalTracker ratioGoalTracker = new RatioGoalTracker(ratioGoal, stub);

        //assert
        assertTrue(ratioGoalTracker.isAchieved());
    }

    @Test
    public void test_isAchieved_withinLowerBound_returnTrue() {
        //arrange
        int targetAmount = 49;
        int variance = 1;
        String nameOfTracked = "Fat";
        RatioGoal ratioGoal = new RatioGoal(targetAmount, variance, nameOfTracked);

        int amount = 50;
        int total = 100;
        IFoodEatenManager stub = new FoodEatenManagerStub(amount, total);

        //act
        RatioGoalTracker ratioGoalTracker = new RatioGoalTracker(ratioGoal, stub);

        //assert
        assertTrue(ratioGoalTracker.isAchieved());
    }


    @Test
    public void test_isAchieved_outSideLowerBound_returnTrue() {
        //arrange
        int targetAmount = 52;
        int variance = 1;
        String nameOfTracked = "Fat";
        RatioGoal ratioGoal = new RatioGoal(targetAmount, variance, nameOfTracked);

        int amount = 50;
        int total = 100;
        IFoodEatenManager stub = new FoodEatenManagerStub(amount, total);

        //act
        RatioGoalTracker ratioGoalTracker = new RatioGoalTracker(ratioGoal, stub);

        //assert
        assertFalse(ratioGoalTracker.isAchieved());
    }

    @Test
    public void test_isAchieved_outSideUpperBound_returnTrue() {
        //arrange
        int targetAmount = 48;
        int variance = 1;
        String nameOfTracked = "Fat";
        RatioGoal ratioGoal = new RatioGoal(targetAmount, variance, nameOfTracked);

        int amount = 50;
        int total = 100;
        IFoodEatenManager stub = new FoodEatenManagerStub(amount, total);

        //act
        RatioGoalTracker ratioGoalTracker = new RatioGoalTracker(ratioGoal, stub);

        //assert
        assertFalse(ratioGoalTracker.isAchieved());
    }




}
