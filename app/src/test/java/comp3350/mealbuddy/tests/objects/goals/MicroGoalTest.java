package comp3350.mealbuddy.tests.objects.goals;

import org.junit.Assert;
import org.junit.Test;

import comp3350.mealbuddy.objects.consumables.Edible;
import comp3350.mealbuddy.objects.goals.Goal;
import comp3350.mealbuddy.objects.goals.MacroGoal;
import comp3350.mealbuddy.objects.goals.MicroGoal;

import static org.junit.Assert.fail;

public class MicroGoalTest {


    @Test
    public void equals_sameId_isEqual() {
        //arrange
        int lowerBound1 = 0, upperBound1 = 0;
        int lowerBound2 = 3, upperBound2 = 3;

        //act
        MacroGoal goal1 = new MacroGoal(lowerBound1, upperBound1, Goal.GoalType.RATIO, Edible.Macros.Fat);
        MacroGoal goal2 = new MacroGoal(lowerBound2, upperBound2, Goal.GoalType.QUANTITY, Edible.Macros.Fat);

        //assert
        Assert.assertEquals(goal1, goal2);
    }

    @Test
    public void constructor_nullMacro_throwException() {
        //arrange
        Goal.GoalType nullGoalType = Goal.GoalType.RATIO;
        int lowerBound = 10;
        int upperBound = 130;
        Edible.Micros nullMicro = null;

        //act
        try {
            Goal goal = new MicroGoal(lowerBound, upperBound, nullMicro);
            fail();
        }//assert
        catch (IllegalArgumentException e) {
            Assert.assertTrue(true);
        }
    }

}
