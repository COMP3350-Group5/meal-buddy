package comp3350.mealbuddy.tests.objects.goals;

import org.junit.Assert;
import org.junit.Test;

import comp3350.mealbuddy.objects.consumables.Edible;
import comp3350.mealbuddy.objects.goals.CalorieGoal;
import comp3350.mealbuddy.objects.goals.Goal;
import comp3350.mealbuddy.objects.goals.MacroGoal;

import static org.junit.Assert.fail;

public class GoalTest {


    @Test
    public void constructor_nullGoalType_throwException(){
        //arrange
        Goal.GoalType nullGoalType = null;
        int lowerBound = 0;
        int upperBound = 10;
        Edible.Macros macro = Edible.Macros.Fat;

        //act
        try{
            Goal goal = new MacroGoal(lowerBound, upperBound, nullGoalType, macro);
            fail();
        }//assert
        catch(IllegalArgumentException e){
            Assert.assertTrue(true);
        }
    }

    @Test
    public void constructor_lowerBoundGreaterThanUpperBound_throwException(){
        //arrange
        Goal.GoalType nullGoalType = Goal.GoalType.QUANTITY;
        int lowerBound = 10;
        int upperBound = 0;
        Edible.Macros macro = Edible.Macros.Fat;

        //act
        try{
            Goal goal = new MacroGoal(lowerBound, upperBound, nullGoalType, macro);
            fail();
        }//assert
        catch(IllegalArgumentException e){
            Assert.assertTrue(true);
        }
    }

    @Test
    public void constructor_negativeLowerBound_throwException(){
        //arrange
        Goal.GoalType nullGoalType = Goal.GoalType.QUANTITY;
        int lowerBound = -10;
        int upperBound = 0;
        Edible.Macros macro = Edible.Macros.Fat;

        //act
        try{
            Goal goal = new MacroGoal(lowerBound, upperBound, nullGoalType, macro);
            fail();
        }//assert
        catch(IllegalArgumentException e){
            Assert.assertTrue(true);
        }
    }


    @Test
    public void constructor_greaterThan100RatioGoal_throwException(){
        //arrange
        Goal.GoalType nullGoalType = Goal.GoalType.RATIO;
        int lowerBound = 10;
        int upperBound = 130;
        Edible.Macros macro = Edible.Macros.Fat;

        //act
        try{
            Goal goal = new MacroGoal(lowerBound, upperBound, nullGoalType, macro);
            fail();
        }//assert
        catch(IllegalArgumentException e){
            Assert.assertTrue(true);
        }
    }

    @Test
    public void constructor_greaterThan100QuantityGoal_objectCreated(){
        //arrange
        Goal.GoalType nullGoalType = Goal.GoalType.QUANTITY;
        int lowerBound = 10;
        int upperBound = 130;
        Edible.Macros macro = Edible.Macros.Fat;

        //act
        Goal goal = new MacroGoal(lowerBound, upperBound, nullGoalType, macro);

        //assert
        Assert.assertNotNull(goal); //no error thrown
    }

    @Test
    public void copyConstructorTest() {
        //arrange
        Goal goal = new MacroGoal(1, 100, Goal.GoalType.QUANTITY, Edible.Macros.Alcohol);
        Goal copiedGoal = Goal.copyGoal(goal);

        //act
        copiedGoal.lowerBound = 5;
        copiedGoal.upperBound = 200;
        copiedGoal.goalType = Goal.GoalType.RATIO;
        copiedGoal.id = Edible.Macros.Carbohydrates;

        //assert
        Assert.assertNotEquals(goal.lowerBound, copiedGoal.lowerBound);
        Assert.assertNotEquals(goal.upperBound, copiedGoal.upperBound);
        Assert.assertNotEquals(goal.goalType, copiedGoal.goalType);
        Assert.assertNotEquals(goal.id, copiedGoal.id);
    }



}



