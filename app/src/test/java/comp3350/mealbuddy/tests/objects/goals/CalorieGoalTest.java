package comp3350.mealbuddy.tests.objects.goals;

import org.junit.Assert;
import org.junit.Test;

import comp3350.mealbuddy.objects.goals.CalorieGoal;

public class CalorieGoalTest {

    @Test
    public void equals_differentCalorieGoal_isEqual(){
        //arrange
        //act
        int lowerBound1=0, upperBound1 = 0;
        int lowerBound2=3, upperBound2 = 3;

        CalorieGoal goal1 = new CalorieGoal(lowerBound1, upperBound1);
        CalorieGoal goal2 = new CalorieGoal(lowerBound2, upperBound2);

        //assert
        Assert.assertEquals(goal1, goal2);
    }


}
