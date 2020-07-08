package comp3350.mealbuddy.tests.objects;
import org.junit.Assert;
import comp3350.mealbuddy.objects.UserInfo;


public class UserInfoTest {
    public static final double HEIGHT_TESTVALUE = 150.55;
    public static final double WEIGHT_TESTVALUE = 170.61;
    public static final int AGE_TESTVALUE = 25;
    public static final double ASSERT_DELTAPRECISION = 0.000000001;


    public void test_ConstructorUserInfo() {
        UserInfo userInfo = new UserInfo("Mark Spencer", "marks",
                "pumpkin101", WEIGHT_TESTVALUE, HEIGHT_TESTVALUE, UserInfo.ActivityLevel.HIGH, UserInfo.Sex.MALE, AGE_TESTVALUE);

        Assert.assertNotNull(userInfo);
        Assert.assertTrue("Mark Spencer".equals(userInfo.fullname));
        Assert.assertTrue("marks".equals(userInfo.username));
        Assert.assertTrue("pumpkin101".equals(userInfo.password));
        Assert.assertEquals(WEIGHT_TESTVALUE, userInfo.weight, ASSERT_DELTAPRECISION);
        Assert.assertEquals(HEIGHT_TESTVALUE , userInfo.height, ASSERT_DELTAPRECISION);
        Assert.assertTrue(UserInfo.ActivityLevel.HIGH.equals(userInfo.activityLevel));
        Assert.assertTrue(UserInfo.Sex.MALE.equals(userInfo.sex));
        Assert.assertEquals(AGE_TESTVALUE, userInfo.age);
    }
}
