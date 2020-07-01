package comp3350.mealbuddy.tests.business;

import org.junit.Test;

import comp3350.mealbuddy.business.QuantityGoalTracker;
import comp3350.mealbuddy.objects.QuantityGoal;
import comp3350.mealbuddy.persistence.FoodEatenManagerStub;
import comp3350.mealbuddy.persistence.IFoodEatenManager;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class QuantityGoalTrackerTest {

    @Test
    public void test_isAchieved_noVarianceSameAmount_returnTrue() {
        //arrange
        int targetAmount = 50;
        int variance = 0;
        String nameOfTracked = "Fat";
        QuantityGoal quantityGoal = new QuantityGoal(targetAmount, variance, nameOfTracked);

        int amount = 50;
        IFoodEatenManager stub = new FoodEatenManagerStub(amount);

        //act
        QuantityGoalTracker quantityGoalTracker = new QuantityGoalTracker(quantityGoal, stub);

        //assert
        assertTrue(quantityGoalTracker.isAchieved());
    }

    @Test
    public void test_isAchieved_withVarianceLowerBound_returnTrue() {
        //arrange
        int targetAmount = 50;
        int variance = 5;
        String nameOfTracked = "Fat";
        QuantityGoal quantityGoal = new QuantityGoal(targetAmount, variance, nameOfTracked);

        int amount = 45;
        IFoodEatenManager stub = new FoodEatenManagerStub(amount);

        //act
        QuantityGoalTracker quantityGoalTracker = new QuantityGoalTracker(quantityGoal, stub);

        //assert
        assertTrue(quantityGoalTracker.isAchieved());
    }

    @Test
    public void test_isAchieved_withVarianceUpperBound_returnTrue() {
        //arrange

        int targetAmount = 50;
        int variance = 5;
        String nameOfTracked = "Fat";
        QuantityGoal quantityGoal = new QuantityGoal(targetAmount, variance, nameOfTracked);

        int amount = 55;
        IFoodEatenManager stub = new FoodEatenManagerStub(amount);

        //act
        QuantityGoalTracker quantityGoalTracker = new QuantityGoalTracker(quantityGoal, stub);

        //assert
        assertTrue(quantityGoalTracker.isAchieved());
    }

    @Test
    public void test_isAchieved_tooHighAmountWithVariance_returnFalse() {
        //arrange
        int targetAmount = 50;
        int variance = 5;
        String nameOfTracked = "Fat";
        QuantityGoal quantityGoal = new QuantityGoal(targetAmount, variance, nameOfTracked);

        int amount = 56;
        IFoodEatenManager stub = new FoodEatenManagerStub(56);

        //act
        QuantityGoalTracker quantityGoalTracker = new QuantityGoalTracker(quantityGoal, stub);

        //assert
        assertFalse(quantityGoalTracker.isAchieved());
    }

    @Test
    public void test_isAchieved_tooLowAmountWithVariance_returnFalse() {
        //arrange
        int targetAmount = 50;
        int variance = 5;
        String nameOfTracked = "Fat";
        QuantityGoal quantityGoal = new QuantityGoal(targetAmount, variance, nameOfTracked);

        int amount = 44;
        IFoodEatenManager stub = new FoodEatenManagerStub(44);

        //act
        QuantityGoalTracker quantityGoalTracker = new QuantityGoalTracker(quantityGoal, stub);

        //assert
        assertFalse(quantityGoalTracker.isAchieved());
    }

    @Test
    public void test_isAchieved_tooLowAmountNoVariance_returnFalse() {
        //arrange
        int targetAmount = 50;
        int variance = 0;
        String nameOfTracked = "Fat";

        QuantityGoal quantityGoal = new QuantityGoal(targetAmount, variance, nameOfTracked);

        int amount = 42;
        IFoodEatenManager stub = new FoodEatenManagerStub(amount);

        //act
        QuantityGoalTracker quantityGoalTracker = new QuantityGoalTracker(quantityGoal, stub);

        //assert
        assertFalse(quantityGoalTracker.isAchieved());
    }

    @Test
    public void test_isAchieved_tooHighAmountNoVariance_returnFalse() {
        //arrange
        int targetAmount = 50;
        int variance = 0;
        String nameOfTracked = "Fat";

        QuantityGoal quantityGoal = new QuantityGoal(targetAmount, variance, nameOfTracked);

        int amount = 69;
        IFoodEatenManager stub = new FoodEatenManagerStub(amount);

        //act
        QuantityGoalTracker quantityGoalTracker = new QuantityGoalTracker(quantityGoal, stub);

        //assert
        assertFalse(quantityGoalTracker.isAchieved());
    }

}