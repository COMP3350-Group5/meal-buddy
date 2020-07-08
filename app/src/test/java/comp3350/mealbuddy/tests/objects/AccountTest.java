package comp3350.mealbuddy.tests.objects;
import org.junit.Assert;
import org.junit.Test;
import comp3350.mealbuddy.objects.Account;
import comp3350.mealbuddy.objects.UserInfo;


public class AccountTest {


    public void test_Constructor() {
        UserInfo userInfo;
        userInfo = new UserInfo("Mark Spencer", "marks", "pumpkin101",
                150.55, 170.61, UserInfo.ActivityLevel.HIGH, UserInfo.Sex.MALE, 25);
        Account account = new Account(userInfo);



        Assert.assertEquals(account.user, userInfo);
    }


}
