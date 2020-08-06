package comp3350.mealbuddy.tests.business;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import comp3350.mealbuddy.business.RecommendedGoalFactory;
import comp3350.mealbuddy.objects.UserInfo;
import comp3350.mealbuddy.objects.consumables.Edible;
import comp3350.mealbuddy.objects.goals.CalorieGoal;
import comp3350.mealbuddy.objects.goals.Goal;
import comp3350.mealbuddy.objects.goals.MacroGoal;
import comp3350.mealbuddy.objects.goals.MicroGoal;

import static comp3350.mealbuddy.objects.consumables.Edible.Micros.Zinc;
import static org.junit.Assert.fail;

public class RecommendedGoalFactorTest {

    private static UserInfo user;

    @Before
    public void createDefaultUser() {
        String username = "sandy";
        String password = "KrustyKrab";
        String fullName = "sandyCheeks";
        double weight = 100;
        double height = 64;
        UserInfo.ActivityLevel activityLevel = UserInfo.ActivityLevel.MEDIUM;
        UserInfo.Sex sex = UserInfo.Sex.FEMALE;
        int age = 35;
        user = new UserInfo(fullName, username, password, weight, height, activityLevel, sex, age);
    }

    @Test
    public void makeGoals_avgUser_totalCalorieGoalCreated() {
        //arrange
        Goal expectedGoal = new CalorieGoal(1700, 2100);

        //act
        List<Goal> goalList = RecommendedGoalFactory.makeGoals(user);

        //assert
        assert goalList.contains(expectedGoal);
    }

    @Test
    public void makeGoals_avgUser_microZincGoalCreated() {
        //arrange
        int expectedLowerBound = 10;
        int expectedUpperBound = 40;
        MicroGoal expectedGoal = new MicroGoal(expectedLowerBound, expectedUpperBound, Zinc);

        //act
        List<Goal> goalList = RecommendedGoalFactory.makeGoals(user);

        //assert
        assert goalList.contains(expectedGoal);
    }

    @Test
    public void makeGoals_avgUser_microZincCorrectValues() {
        //arrange
        int expectedLowerBound = 10;
        int expectedUpperBound = 40;
        MicroGoal expectedGoal = new MicroGoal(expectedLowerBound, expectedUpperBound, Zinc);

        //act
        List<Goal> list = RecommendedGoalFactory.makeGoals(user);
        Goal actualGoal = list.get(list.indexOf(expectedGoal));

        //assert
        Assert.assertEquals(expectedGoal.lowerBound, actualGoal.lowerBound);
        Assert.assertEquals(expectedGoal.upperBound, actualGoal.upperBound);
        Assert.assertEquals(Goal.GoalType.QUANTITY, actualGoal.goalType);
        Assert.assertTrue(actualGoal instanceof MicroGoal);
        Assert.assertEquals(expectedGoal.id, ((MicroGoal) actualGoal).id);
    }

    @Test
    public void makeGoals_avgUser_macroFatGoalCreated() {
        //arrange
        int expectedLowerBound = 20;
        int expectedUpperBound = 30;
        MicroGoal expectedGoal = new MicroGoal(expectedLowerBound, expectedUpperBound, Zinc);

        //act
        List<Goal> goalList = RecommendedGoalFactory.makeGoals(user);

        //assert
        assert goalList.contains(expectedGoal);
    }

    @Test
    public void makeGoals_avgUser_macroFatGoalCorrectValues() {
        //arrange
        int expectedLowerBound = 20;
        int expectedUpperBound = 30;
        MacroGoal expectedGoal = new MacroGoal(expectedLowerBound, expectedUpperBound, Goal.GoalType.RATIO, Edible.Macros.Fat);

        //act
        List<Goal> list = RecommendedGoalFactory.makeGoals(user);
        Goal actualGoal = list.get(list.indexOf(expectedGoal));

        //assert
        Assert.assertEquals(expectedGoal.lowerBound, actualGoal.lowerBound);
        Assert.assertEquals(expectedGoal.upperBound, actualGoal.upperBound);
        Assert.assertEquals(expectedGoal.goalType, actualGoal.goalType);
        Assert.assertTrue(actualGoal instanceof MacroGoal);
        Assert.assertEquals(expectedGoal.id, ((MacroGoal) actualGoal).id);
    }


    @Test
    public void makeGoals_nullUserName_returnExpected() {
        //arrange
        user.username = null;
        Goal expectedGoal = new CalorieGoal(1700, 2100);

        //act
        List<Goal> goalList = RecommendedGoalFactory.makeGoals(user);

        //assert
        assert goalList.contains(expectedGoal);
    }

    @Test
    public void makeGoals_nullPassword_returnExpected() {
        //arrange
        user.password = null;
        Goal expectedGoal = new CalorieGoal(1700, 2100);

        //act
        List<Goal> goalList = RecommendedGoalFactory.makeGoals(user);

        //assert
        assert goalList.contains(expectedGoal);
    }

    @Test
    public void makeGoals_nullFullName_returnExpected() {
        //arrange
        user.fullname = null;
        Goal expectedGoal = new CalorieGoal(1700, 2100);

        //act
        List<Goal> goalList = RecommendedGoalFactory.makeGoals(user);

        //assert
        assert goalList.contains(expectedGoal);
    }

    @Test
    public void makeGoals_negativeWeight_throwException() {
        //arrange
        user.weight = -69;

        //act
        try {
            List<Goal> goalList = RecommendedGoalFactory.makeGoals(user);
            fail();
        }
        //assert
        catch (IllegalArgumentException e) {
            Assert.assertTrue(true);
        }
    }

    @Test
    public void makeGoals_negativeHeight_throwException() {
        //arrange
        user.height = -69;

        //act
        try {
            List<Goal> goalList = RecommendedGoalFactory.makeGoals(user);
            fail();
        }
        //assert
        catch (IllegalArgumentException e) {
            Assert.assertTrue(true);
        }
    }

    @Test
    public void makeGoals_negativeAge_throwException() {
        //arrange
        user.age = -69;

        //act
        try {
            List<Goal> goalList = RecommendedGoalFactory.makeGoals(user);
            fail();
        }
        //assert
        catch (IllegalArgumentException e) {
            Assert.assertTrue(true);
        }
    }


    @Test
    public void makeGoals_nullActivity_throwException() {
        //arrange
        user.activityLevel = null;

        //act
        try {
            List<Goal> goalList = RecommendedGoalFactory.makeGoals(user);
            fail();
        }
        //assert
        catch (IllegalArgumentException e) {
            Assert.assertTrue(true);
        }
    }


    @Test
    public void makeGoals_nullSex_throwException() {
        //arrange
        user.sex = null;

        //act
        try {
            List<Goal> goalList = RecommendedGoalFactory.makeGoals(user);
            fail();
        }
        //assert
        catch (IllegalArgumentException e) {
            Assert.assertTrue(true);
        }
    }


}