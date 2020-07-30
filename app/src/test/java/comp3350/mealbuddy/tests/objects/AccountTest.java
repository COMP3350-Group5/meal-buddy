package comp3350.mealbuddy.tests.objects;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import comp3350.mealbuddy.objects.Account;
import comp3350.mealbuddy.objects.Day;
import comp3350.mealbuddy.objects.UserInfo;
import comp3350.mealbuddy.objects.goals.CalorieGoal;
import comp3350.mealbuddy.objects.goals.Goal;

public class AccountTest {

    private static Account account;
    private static UserInfo userInfo;

    @Before
    public void initAccountTest() {
        String name = "Mike Spencer";
        String username = "Mike.spencer";
        String password = "mike_spencer";
        double weight = 140.00;
        double height = 175.00;
        UserInfo.ActivityLevel activityLevel = UserInfo.ActivityLevel.HIGH;
        UserInfo.Sex sex = UserInfo.Sex.MALE;
        int age = 100;
        userInfo = new UserInfo(name, username, password, weight, height, activityLevel, sex, age);
        account = new Account(userInfo);
    }

    @Test
    public void constructor_nullUserInfo_throwException() {
        // Arrange
        userInfo = null;

        // Act
        try {
            account = new Account(userInfo);
        } catch (IllegalArgumentException e) {
            //Assert
            Assert.assertTrue(true);
        }
    }

    @Test
    public void addGoal_goalNotContained_GoalAdded() {
        //arrange
        Goal goal = new CalorieGoal(0, 1);

        //act
        account.addGoal(goal);

        //assert
        Assert.assertTrue(account.containsGoal(goal));
    }

    @Test
    public void addGoal_goalContained_GoalNotAdded() {
        //arrange
        int expectedSize = 1;
        Goal goal = new CalorieGoal(0, 1);
        account.addGoal(goal);

        //act
        account.addGoal(goal);

        //assert
        Assert.assertEquals(expectedSize, account.getGoalSize());
    }

    @Test
    public void getDay_dayNotContained_dayCreated() {
        //arrange
        int dayOfYear = 69;

        //act
        Day actualDay = account.getDay(dayOfYear);

        //assert
        Assert.assertEquals(dayOfYear, actualDay.dayOfYear);
    }

    @Test
    public void getDay_dayContained_dayFetched() {
        //arrange
        int day2Date = 69;
        Day day1 = new Day(6);
        Day day2 = new Day(day2Date);
        Day day3 = new Day(200);
        account.addDay(day1);
        account.addDay(day2);
        account.addDay(day3);

        //act
        Day retrievedDay = account.getDay(day2Date);

        //assert
        Assert.assertEquals(day2, retrievedDay);
    }


    @Test
    public void copyConstructor() {
        //arrange
        Account updatedAccount = new Account(account);
        Goal testGoal = new CalorieGoal(1, 100);

        //act
        updatedAccount.addDay(12);
        updatedAccount.addGoal(testGoal);

        //assert
        Assert.assertFalse(account.isDayTracked(12));
        Assert.assertTrue(updatedAccount.containsGoal(testGoal));
        Assert.assertFalse(account.containsGoal(testGoal));
    }

}
