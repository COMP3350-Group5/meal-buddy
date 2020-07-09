package comp3350.mealbuddy.tests.business.goalFactory;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import comp3350.mealbuddy.business.goalFactory.RecommendedCalorieCalculator;
import comp3350.mealbuddy.objects.UserInfo;
import comp3350.mealbuddy.objects.UserInfo.ActivityLevel;
import comp3350.mealbuddy.objects.UserInfo.Sex;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class RecommendedCalorieCalculatorTest {

    private static UserInfo user;

    @Before
    public void createDefaultUser(){
        String username = "sandy";
        String password = "KrustyKrab";
        String fullName = "sandyCheeks";
        double weight = 100;
        double height = 64;
        ActivityLevel activityLevel = ActivityLevel.MEDIUM;
        Sex sex = Sex.FEMALE;
        int age = 35;
        user = new UserInfo(fullName,username, password, weight, height, activityLevel, sex, age);
    }

    @Test
    public void getTotalRecommendedCalories_avgFemale_returnsExpectedAmount(){
        //arrange
        int expected = 1900;

        //act
        int actual = RecommendedCalorieCalculator.getTotalRecommendedCalories(user);

        //assert
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getTotalRecommendedCalories_avgMale_returnsGreaterThanFemale(){
        //arrange
        user.sex = Sex.MALE;
        int expected = 1962;

        //act
        int actual = RecommendedCalorieCalculator.getTotalRecommendedCalories(user);

        //assert
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getTotalRecommendedCalories_tallFemale_returnsGreaterThanAvgHeight(){
        //arrange
        user.height = 80;
        int expected = 2017;

        //act
        int actual = RecommendedCalorieCalculator.getTotalRecommendedCalories(user);

        //assert
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getTotalRecommendedCalories_youngFemale_returnsMoreThanAvgAge(){
        //arrange
        user.age = 20;
        int expected = 2010;

        //act
        int actual = RecommendedCalorieCalculator.getTotalRecommendedCalories(user);

        //assert
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getTotalRecommendedCalories_activeFemale_returnsMoreThanAvgAge(){
        //arrange
        user.activityLevel = ActivityLevel.HIGH;
        int expected = 2330;

        //act
        int actual = RecommendedCalorieCalculator.getTotalRecommendedCalories(user);

        //assert
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getTotalRecommendedCalories_nullUsername_returnsExpectedAmount(){
        //arrange
        user.username = null;
        int expected = 1900;

        //act
        int actual = RecommendedCalorieCalculator.getTotalRecommendedCalories(user);

        //assert
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getTotalRecommendedCalories_nullFullName_returnsExpectedAmount(){
        //arrange
        user.fullname = null;
        int expected = 1900;

        //act
        int actual = RecommendedCalorieCalculator.getTotalRecommendedCalories(user);

        //assert
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getTotalRecommendedCalories_nullPassword_returnsExpectedAmount(){
        //arrange
        user.password = null;
        int expected = 1900;

        //act
        int actual = RecommendedCalorieCalculator.getTotalRecommendedCalories(user);

        //assert
        Assert.assertEquals(expected, actual);
    }


    @Test
    public void getTotalRecommendedCalories_negativeAge_throwsException(){
        //arrange
        user.age = -69;

        //act
        try {
            int actual = RecommendedCalorieCalculator.getTotalRecommendedCalories(user);
            fail();
        }
        //assert
        catch(IllegalArgumentException e){
            assertTrue(true);
        }
    }

    @Test
    public void getTotalRecommendedCalories_negativeHeight_throwsException(){
        //arrange
        user.height = -69;

        //act
        try {
            int actual = RecommendedCalorieCalculator.getTotalRecommendedCalories(user);
            fail();
        }
        //assert
        catch(IllegalArgumentException e){
            assertTrue(true);
        }
    }

    @Test
    public void getTotalRecommendedCalories_negativeWeight_throwsException(){
        //arrange
        user.weight = -69;

        //act
        try {
            int actual = RecommendedCalorieCalculator.getTotalRecommendedCalories(user);
            fail();
        }
        //assert
        catch(IllegalArgumentException e){
            assertTrue(true);
        }
    }

    @Test
    public void getTotalRecommendedCalories_nullActivity_throwsException(){
        //arrange
        user.activityLevel = null;

        //act
        try {
            int actual = RecommendedCalorieCalculator.getTotalRecommendedCalories(user);
            fail();
        }
        //assert
        catch(IllegalArgumentException e){
            assertTrue(true);
        }
    }

    @Test
    public void getTotalRecommendedCalories_nullSex_throwsException(){
        //arrange
        user.sex = null;

        //act
        try {
            int actual = RecommendedCalorieCalculator.getTotalRecommendedCalories(user);
            fail();
        }
        //assert
        catch(IllegalArgumentException e){
            assertTrue(true);
        }
    }


}
