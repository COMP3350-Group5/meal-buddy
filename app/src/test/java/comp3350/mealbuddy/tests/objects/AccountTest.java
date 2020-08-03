package comp3350.mealbuddy.tests.objects;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Iterator;

import comp3350.mealbuddy.objects.Account;
import comp3350.mealbuddy.objects.Day;
import comp3350.mealbuddy.objects.Exercise;
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
            Assert.fail();
        } catch (IllegalArgumentException e) {
            //Assert
            Assert.assertTrue(true);
        }
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
    public void setDefaultDay_illegalArguments_exceptionThrown() {
        //arrange
        Day day = null;
        //act
        try {
            account.setDefaultDay(day);
            Assert.fail();
        } catch (NullPointerException e) {
            //Assert
            Assert.assertTrue(true);
        }
    }

    @Test
    public void setDefaultDay_AvgValue_defaultDaySetToValue() {
        Day newDefault = new Day(42);
        Exercise exercise = new Exercise("run", 1, Exercise.Intensity.High);
        newDefault.addExercise(exercise);
        account.setDefaultDay(newDefault);
        Assert.assertEquals(exercise, account.getDefaultDayCopy(1).getExercise(exercise));
    }

    @Test
    public void getDefaultDayCopy_variousValues_defaultDeepCopyReturned() {
        Day newDefault = new Day(69);
        Exercise exercise = new Exercise("run", 1, Exercise.Intensity.High);
        newDefault.addExercise(exercise);
        account.setDefaultDay(newDefault);
        Day actualDefaultDay = account.getDefaultDayCopy(69);
        Assert.assertEquals(69, actualDefaultDay.dayOfYear);
        Assert.assertEquals(exercise, actualDefaultDay.getExercise(exercise));
        actualDefaultDay.getExercise(exercise).name = "Sprint";
        Assert.assertEquals("run", account.getDefaultDayCopy(1).getExercise(exercise).name);    //changing the new copy doesnt change the default
    }

    @Test
    public void getDefaultDayCopy_illegalValues_ExceptionThrown() {
        try {
            account.getDefaultDayCopy(Account.DEFAULT_DAY_NUM); //dont allow duplicates of default day
            Assert.fail();
        } catch (IllegalArgumentException e) {
            Assert.assertTrue(true);
        }
        try {
            account.getDefaultDayCopy(400);
            Assert.fail();
        } catch (IllegalArgumentException e) {
            Assert.assertTrue(true);
        }
    }

    @Test
    public void resetDefaultDay_defaultSet_defaultSetToZero() {
        //arrange
        Day defaultDay = new Day(5);
        Exercise e = new Exercise("run", 1, Exercise.Intensity.High);
        defaultDay.addExercise(e);
        account.setDefaultDay(defaultDay);

        //act
        Assert.assertEquals(e, account.getDefaultDayCopy(4).getExercise(e));
        account.resetDefaultDay();

        //assert
        Assert.assertTrue(account.getDefaultDayCopy(4).isExerciseEmpty());
    }

    @Test
    public void getDayIterator_avgCases_iteratesOverDays() {
        //arrange
        Iterator<Day> dayIterator = account.getDayIterator();

        account.addDay(new Day(1));
        dayIterator = account.getDayIterator();
        Assert.assertTrue(dayIterator.hasNext());
        Assert.assertEquals(0, dayIterator.next().dayOfYear);   //default day
        Assert.assertEquals(1, dayIterator.next().dayOfYear);
        Assert.assertFalse(dayIterator.hasNext());
    }


    @Test
    public void copyConstructor() {
        //arrange
        Account updatedAccount = new Account(account);
        Goal testGoal = new CalorieGoal(1, 100);

        //act
        updatedAccount.addDay(12);

        //assert
        Assert.assertFalse(account.isDayTracked(12));
    }

}
