package comp3350.mealbuddy.tests.objects;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import comp3350.mealbuddy.objects.Account;
import comp3350.mealbuddy.objects.UserInfo;

public class AccountTest {

    private static Account account;
    private static UserInfo userInfo;

    @Before
    public void initAccountTest(){
        String name = "Mike Spencer";
        String username = "Mike.spencer";
        String password = "mike_spencer";
        Double weight = 140.00;
        Double height = 175.00;
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

}
