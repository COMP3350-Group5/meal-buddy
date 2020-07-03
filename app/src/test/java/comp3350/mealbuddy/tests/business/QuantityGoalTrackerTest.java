//package comp3350.mealbuddy.tests.business;
//
//import org.junit.Test;
//
//import QuantityGoal;
//import comp3350.mealbuddy.persistence.FoodEatenManagerStub;
//import comp3350.mealbuddy.persistence.IFoodEatenManager;
//
//import static org.junit.Assert.assertFalse;
//import static org.junit.Assert.assertTrue;
//
//public class QuantityGoalTrackerTest {
//
//    @Test
//    public void test_isAchieved_boundsEqualSameAmountAsTargert_returnTrue() {
//        //arrange
//        int lowerBound = 50;
//        int upperBound = 50;
//        String nameOfTracked = "Fat";
//        QuantityGoal quantityGoal = new QuantityGoal(lowerBound, upperBound, nameOfTracked);
//
//        int amount = 50;
//        IFoodEatenManager stub = new FoodEatenManagerStub(amount);
//
//        //act
//        QuantityGoalTracker quantityGoalTracker = new QuantityGoalTracker(quantityGoal, stub);
//
//        //assert
//        assertTrue(quantityGoalTracker.isAchieved());
//    }
//
//    @Test
//    public void test_isAchieved_withinLowerBound_returnTrue() {
//        //arrange
//        int lowerBound = 50;
//        int upperBound = 55;
//        String nameOfTracked = "Fat";
//        QuantityGoal quantityGoal = new QuantityGoal(lowerBound, upperBound, nameOfTracked);
//
//        int amount = 50;
//        IFoodEatenManager stub = new FoodEatenManagerStub(amount);
//
//        //act
//        QuantityGoalTracker quantityGoalTracker = new QuantityGoalTracker(quantityGoal, stub);
//
//        //assert
//        assertTrue(quantityGoalTracker.isAchieved());
//    }
//
//    @Test
//    public void test_isAchieved_withinUpperBound_returnTrue() {
//        //arrange
//
//        int lowerBound = 50;
//        int upperBound = 55;
//        String nameOfTracked = "Fat";
//        QuantityGoal quantityGoal = new QuantityGoal(lowerBound, upperBound, nameOfTracked);
//
//        int amount = 55;
//        IFoodEatenManager stub = new FoodEatenManagerStub(amount);
//
//        //act
//        QuantityGoalTracker quantityGoalTracker = new QuantityGoalTracker(quantityGoal, stub);
//
//        //assert
//        assertTrue(quantityGoalTracker.isAchieved());
//    }
//
//    @Test
//    public void test_isAchieved_outsideUpperBound_returnFalse() {
//        //arrange
//        int lowerBound = 50;
//        int upperBound = 55;
//        String nameOfTracked = "Fat";
//        QuantityGoal quantityGoal = new QuantityGoal(lowerBound, upperBound, nameOfTracked);
//
//        int amount = 56;
//        IFoodEatenManager stub = new FoodEatenManagerStub(amount);
//
//        //act
//        QuantityGoalTracker quantityGoalTracker = new QuantityGoalTracker(quantityGoal, stub);
//
//        //assert
//        assertFalse(quantityGoalTracker.isAchieved());
//    }
//
//    @Test
//    public void test_isAchieved_outsideLowerBound_returnFalse() {
//        //arrange
//        int lowerBound = 50;
//        int upperBound = 55;
//        String nameOfTracked = "Fat";
//        QuantityGoal quantityGoal = new QuantityGoal(lowerBound, upperBound, nameOfTracked);
//
//        int amount = 49;
//        IFoodEatenManager stub = new FoodEatenManagerStub(amount);
//
//        //act
//        QuantityGoalTracker quantityGoalTracker = new QuantityGoalTracker(quantityGoal, stub);
//
//        //assert
//        assertFalse(quantityGoalTracker.isAchieved());
//    }
//
//    @Test
//    public void test_isAchieved_tooLowAmountEqualBounds_returnFalse() {
//        //arrange
//        int lowerBound = 50;
//        int upperBound = 50;
//        String nameOfTracked = "Fat";
//
//        QuantityGoal quantityGoal = new QuantityGoal(lowerBound, upperBound, nameOfTracked);
//
//        int amount = 42;
//        IFoodEatenManager stub = new FoodEatenManagerStub(amount);
//
//        //act
//        QuantityGoalTracker quantityGoalTracker = new QuantityGoalTracker(quantityGoal, stub);
//
//        //assert
//        assertFalse(quantityGoalTracker.isAchieved());
//    }
//
//    @Test
//    public void test_isAchieved_tooHighAmountEqualBounds_returnFalse() {
//        //arrange
//        int lowerBound = 50;
//        int upperBound = 50;
//        String nameOfTracked = "Fat";
//
//        QuantityGoal quantityGoal = new QuantityGoal(lowerBound, upperBound, nameOfTracked);
//
//        int amount = 69;
//        IFoodEatenManager stub = new FoodEatenManagerStub(amount);
//
//        //act
//        QuantityGoalTracker quantityGoalTracker = new QuantityGoalTracker(quantityGoal, stub);
//
//        //assert
//        assertFalse(quantityGoalTracker.isAchieved());
//    }
//
//}