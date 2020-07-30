package comp3350.mealbuddy.tests.objects.goals;

import org.junit.Assert;
import org.junit.Test;

import comp3350.mealbuddy.objects.goals.Goal;
import comp3350.mealbuddy.objects.goals.LabelGoal;

import static org.junit.Assert.fail;

public class LabelGoalTest {

    @Test
    public void equals_sameId_isEqual() {
        //arrange
        //act
        int lowerBound1 = 0, upperBound1 = 0;
        int lowerBound2 = 3, upperBound2 = 3;

        LabelGoal goal1 = new LabelGoal(lowerBound1, upperBound1, Goal.GoalType.RATIO, "name");
        LabelGoal goal2 = new LabelGoal(lowerBound2, upperBound2, Goal.GoalType.QUANTITY, "name");

        //assert
        Assert.assertEquals(goal1, goal2);
    }

    @Test
    public void constructor_nullLabel_throwException() {
        //arrange
        Goal.GoalType nullGoalType = Goal.GoalType.RATIO;
        int lowerBound = 10;
        int upperBound = 130;
        String labelId = null;

        //act
        try {
            Goal goal = new LabelGoal(lowerBound, upperBound, nullGoalType, labelId);
            fail();
        }//assert
        catch (IllegalArgumentException e) {
            Assert.assertTrue(true);
        }
    }

}
