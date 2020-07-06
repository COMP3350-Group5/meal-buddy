package comp3350.mealbuddy.tests.business;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import comp3350.mealbuddy.business.GoalFactory.DefaultGoalFactory;
import comp3350.mealbuddy.business.GoalFactory.GoalFactory;
import comp3350.mealbuddy.objects.CalorieGoal;
import comp3350.mealbuddy.objects.Edible.Macros;
import comp3350.mealbuddy.objects.Goal;
import comp3350.mealbuddy.objects.Goal.GoalType;
import comp3350.mealbuddy.objects.MacroGoal;
import comp3350.mealbuddy.objects.MicroGoal;
import comp3350.mealbuddy.objects.UserInfo;

import static comp3350.mealbuddy.objects.Edible.Micros.Zinc;
import static org.junit.Assert.fail;

public class DefaultGoalFactorTest {

    private static UserInfo user;

    @Before
    public void createDefaultUser(){
        String username = "sandy";
        String password = "KrustyKrab";
        String fullName = "sandyCheeks";
        double weight = 100;
        double height = 64;
        UserInfo.ActivityLevel activityLevel = UserInfo.ActivityLevel.MEDIUM;
        UserInfo.Sex sex = UserInfo.Sex.FEMALE;
        int age = 35;
        user = new UserInfo(fullName,username, password, weight, height, activityLevel, sex, age);
    }

    @Test
    public void makeGoals_avgUser_totalCalorieGoalCreated(){
        //arrange
        GoalFactory goalFactory = new DefaultGoalFactory(user);
        Goal expectedGoal = new CalorieGoal(1700, 2100);

        //act
        List<Goal> goalList = goalFactory.makeGoals();

        //assert
        assert goalList.contains(expectedGoal);
    }

    @Test
    public void makeGoals_avgUser_microZincGoalCreated(){
        //arrange
        GoalFactory goalFactory = new DefaultGoalFactory(user);
        int expectedLowerBound = 10;
        int expectedUpperBound = 40;
        MicroGoal expectedGoal = new MicroGoal(expectedLowerBound, expectedUpperBound, Zinc);

        //act
        List<Goal> goalList = goalFactory.makeGoals();

        //assert
        assert goalList.contains(expectedGoal);
    }

    @Test
    public void makeGoals_avgUser_microZincCorrectValues(){
        //arrange
        GoalFactory goalFactory = new DefaultGoalFactory(user);
        int expectedLowerBound = 10;
        int expectedUpperBound = 40;
        MicroGoal expectedGoal = new MicroGoal(expectedLowerBound, expectedUpperBound, Zinc);

        //act
        List<Goal> list = goalFactory.makeGoals();
        Goal actualGoal = list.get(list.indexOf(expectedGoal));

        //assert
        Assert.assertEquals(expectedGoal.lowerBound, actualGoal.lowerBound);
        Assert.assertEquals(expectedGoal.upperBound, actualGoal.upperBound);
        Assert.assertEquals(GoalType.QUANTITY, actualGoal.goalType);
        Assert.assertTrue(actualGoal instanceof MicroGoal);
        Assert.assertEquals(expectedGoal.id, ((MicroGoal)actualGoal).id);
    }

    @Test
    public void makeGoals_avgUser_macroFatGoalCreated(){
        //arrange
        GoalFactory goalFactory = new DefaultGoalFactory(user);
        int expectedLowerBound = 20;
        int expectedUpperBound = 30;
        MicroGoal expectedGoal = new MicroGoal(expectedLowerBound, expectedUpperBound, Zinc);

        //act
        List<Goal> goalList = goalFactory.makeGoals();

        //assert
        assert goalList.contains(expectedGoal);
    }

    @Test
    public void makeGoals_avgUser_macroFatGoalCorrectValues(){
        //arrange
        GoalFactory goalFactory = new DefaultGoalFactory(user);
        int expectedLowerBound = 20;
        int expectedUpperBound = 30;
        MacroGoal expectedGoal = new MacroGoal(expectedLowerBound, expectedUpperBound, GoalType.RATIO, Macros.Fat);

        //act
        List<Goal> list = goalFactory.makeGoals();
        Goal actualGoal = list.get(list.indexOf(expectedGoal));

        //assert
        Assert.assertEquals(expectedGoal.lowerBound, actualGoal.lowerBound);
        Assert.assertEquals(expectedGoal.upperBound, actualGoal.upperBound);
        Assert.assertEquals(expectedGoal.goalType, actualGoal.goalType);
        Assert.assertTrue(actualGoal instanceof MacroGoal);
        Assert.assertEquals(expectedGoal.id, ((MacroGoal)actualGoal).id);
    }


    @Test
    public void makeGoals_nullUserName_returnExpected(){
        //arrange
        user.username = null;
        GoalFactory goalFactory = new DefaultGoalFactory(user);
        Goal expectedGoal = new CalorieGoal(1700, 2100);

        //act
        List<Goal> goalList = goalFactory.makeGoals();

        //assert
        assert goalList.contains(expectedGoal);
    }

    @Test
    public void makeGoals_nullPassword_returnExpected(){
        //arrange
        user.password = null;
        GoalFactory goalFactory = new DefaultGoalFactory(user);
        Goal expectedGoal = new CalorieGoal(1700, 2100);

        //act
        List<Goal> goalList = goalFactory.makeGoals();

        //assert
        assert goalList.contains(expectedGoal);
    }

    @Test
    public void makeGoals_nullFullName_returnExpected(){
        //arrange
        user.fullname = null;
        GoalFactory goalFactory = new DefaultGoalFactory(user);
        Goal expectedGoal = new CalorieGoal(1700, 2100);

        //act
        List<Goal> goalList = goalFactory.makeGoals();

        //assert
        assert goalList.contains(expectedGoal);
    }

    @Test
    public void makeGoals_negativeWeight_throwException(){
        //arrange
        user.weight = -69;
        GoalFactory goalFactory = new DefaultGoalFactory(user);

        //act
        try {
            List<Goal> goalList = goalFactory.makeGoals();
            fail();
        }
        //assert
        catch (IllegalArgumentException e){
            Assert.assertTrue(true);
        }
    }

    @Test
    public void makeGoals_negativeHeight_throwException(){
        //arrange
        user.height = -69;
        GoalFactory goalFactory = new DefaultGoalFactory(user);

        //act
        try {
            List<Goal> goalList = goalFactory.makeGoals();
            fail();
        }
        //assert
        catch (IllegalArgumentException e){
            Assert.assertTrue(true);
        }
    }

    @Test
    public void makeGoals_negativeAge_throwException(){
        //arrange
        user.age = -69;
        GoalFactory goalFactory = new DefaultGoalFactory(user);

        //act
        try {
            List<Goal> goalList = goalFactory.makeGoals();
            fail();
        }
        //assert
        catch (IllegalArgumentException e){
            Assert.assertTrue(true);
        }
    }


    @Test
    public void makeGoals_nullActivity_throwException(){
        //arrange
        user.activityLevel = null;
        GoalFactory goalFactory = new DefaultGoalFactory(user);

        //act
        try {
            List<Goal> goalList = goalFactory.makeGoals();
            fail();
        }
        //assert
        catch (IllegalArgumentException e){
            Assert.assertTrue(true);
        }
    }


    @Test
    public void makeGoals_nullSex_throwException(){
        //arrange
        user.sex = null;
        GoalFactory goalFactory = new DefaultGoalFactory(user);

        //act
        try {
            List<Goal> goalList = goalFactory.makeGoals();
            fail();
        }
        //assert
        catch (IllegalArgumentException e){
            Assert.assertTrue(true);
        }
    }


}
