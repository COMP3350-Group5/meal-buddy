package comp3350.mealbuddy.tests.business;

import org.junit.Test;

import comp3350.mealbuddy.business.RatioGoalTracker;
import comp3350.mealbuddy.objects.RatioGoal;
import comp3350.mealbuddy.persistence.FoodEatenManagerStub;
import comp3350.mealbuddy.persistence.IFoodEatenManager;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class RatioGoalTrackerTest {

    @Test
    public void test_isAchieved_withinLowerBound_returnTrue() {
        //arrange
        int lowerBound = 50;
        int upperBound = 51;
        String nameOfTracked = "Fat";
        RatioGoal ratioGoal = new RatioGoal(lowerBound, upperBound, nameOfTracked);

        int amount = 50;
        int total = 100;
        IFoodEatenManager stub = new FoodEatenManagerStub(amount, total);

        //act
        RatioGoalTracker ratioGoalTracker = new RatioGoalTracker(ratioGoal, stub);

        //assert
        assertTrue(ratioGoalTracker.isAchieved());
    }

    @Test
    public void test_isAchieved_withinUpperBound_returnTrue() {
        //arrange
        int lowerBound = 49;
        int upperBound = 50;
        String nameOfTracked = "Fat";
        RatioGoal ratioGoal = new RatioGoal(lowerBound, upperBound, nameOfTracked);

        int amount = 50;
        int total = 100;
        IFoodEatenManager stub = new FoodEatenManagerStub(amount, total);

        //act
        RatioGoalTracker ratioGoalTracker = new RatioGoalTracker(ratioGoal, stub);

        //assert
        assertTrue(ratioGoalTracker.isAchieved());
    }


    @Test
    public void test_isAchieved_withinBounds_returnTrue() {
        //arrange
        int lowerBound = 40;
        int upperBound = 60;
        String nameOfTracked = "Fat";
        RatioGoal ratioGoal = new RatioGoal(lowerBound, upperBound, nameOfTracked);

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
        int lowerBound = 40;
        int upperBound = 50;
        String nameOfTracked = "Fat";
        RatioGoal ratioGoal = new RatioGoal(lowerBound, upperBound, nameOfTracked);

        int amount = 30;
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
        int lowerBound = 40;
        int upperBound = 50;
        String nameOfTracked = "Fat";
        RatioGoal ratioGoal = new RatioGoal(lowerBound, upperBound, nameOfTracked);

        int amount = 60;
        int total = 100;
        IFoodEatenManager stub = new FoodEatenManagerStub(amount, total);

        //act
        RatioGoalTracker ratioGoalTracker = new RatioGoalTracker(ratioGoal, stub);

        //assert
        assertFalse(ratioGoalTracker.isAchieved());
    }




}
