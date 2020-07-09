package comp3350.mealbuddy.tests.objects;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import comp3350.mealbuddy.objects.UserInfo;
import comp3350.mealbuddy.objects.consumables.Food;

public class UserInfoTest {

    private static UserInfo userInfo;

    @Before
    public void initUserInfo(){
        String name = "Mike Spencer";
        String username = "Mike.spencer";
        String password = "mike_spencer";
        Double weight = 140.00;
        Double height = 175.00;
        UserInfo.ActivityLevel activityLevel = UserInfo.ActivityLevel.HIGH;
        UserInfo.Sex sex= UserInfo.Sex.MALE;
        int age = 100;
        userInfo = new UserInfo(name, username, password, weight, height, activityLevel, sex, age);
    }


    @Test
    public void constructor_sexEnumNull_throwException(){
        //arrange
        userInfo.sex = null;

        //act
        try{
            userInfo.validateUserInfo();
            Assert.fail();
        }
        catch(IllegalArgumentException e){
            //assert
            Assert.assertTrue(true);
        }
        //assert
    }


    @Test
    public void constructor_activityLevelEnumNull_throwException(){
        //arrange
        userInfo.activityLevel = null;

        //act
        try{
            userInfo.validateUserInfo();
            Assert.fail();
        }
        catch(IllegalArgumentException e){
            //assert
            Assert.assertTrue(true);
        }
    }


    @Test
    public void constructor_nullFullName_throwException(){
        //arrange
        userInfo.fullname = null;

        //act
        try{
            userInfo.validateUserInfo();
            Assert.fail();
        }
        catch(IllegalArgumentException e){
            //assert
            Assert.assertTrue(true);
        }
    }


    @Test
    public void constructor_nullUsername_throwException(){
        // arrange
        userInfo.username = null;

        // act
        try{
            userInfo.validateUserInfo();
            Assert.fail();
        }
        catch(IllegalArgumentException e){
            //assert
            Assert.assertTrue(true);
        }
    }


    @Test
    public void constructor_nullPassword_throwException(){
        //arrange
        userInfo.password = null;

        //act
        try{
            userInfo.validateUserInfo();
            Assert.fail();
        }
        catch(IllegalArgumentException e){
            //assert
            Assert.assertTrue(true);
        }
    }


    @Test
    public void constructor_negativeWeight_throwException(){
        //arrange
        userInfo.weight = -50;

        //act
        try{
            userInfo.validateUserInfo();
            Assert.fail();
        }
        catch(IllegalArgumentException e){
            //assert
            Assert.assertTrue(true);
        }
    }


    @Test
    public void constructor_negativeHeight_throwException(){
        //arrange
        userInfo.height = -100;

        //act
        try{
            userInfo.validateUserInfo();
            Assert.fail();
        }
        catch(IllegalArgumentException e){
            //assert
            Assert.assertTrue(true);
        }
    }


    @Test
    public void constructor_negativeAge_throwException(){
        //arrange
        userInfo.age = -50;

        //act
        try{
            userInfo.validateUserInfo();
            Assert.fail();
        }
        catch(IllegalArgumentException e){
            //assert
            Assert.assertTrue(true);
        }
    }

    @Test
    public void constructor_ageTooBig_throwException(){
        //arrange
        userInfo.age = 125;

        //act
        try{
            userInfo.validateUserInfo();
            Assert.fail();
        }
        catch(IllegalArgumentException e){
            //assert
            Assert.assertTrue(true);
        }
    }
}
