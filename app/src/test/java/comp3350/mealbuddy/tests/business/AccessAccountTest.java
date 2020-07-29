package comp3350.mealbuddy.tests.business;

import org.hsqldb.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import comp3350.mealbuddy.application.Main;
import comp3350.mealbuddy.application.Services;
import comp3350.mealbuddy.business.AccessAccount;
import comp3350.mealbuddy.objects.Account;
import comp3350.mealbuddy.objects.Day;
import comp3350.mealbuddy.objects.UserInfo;
import comp3350.mealbuddy.persistence.DataAccess;
import comp3350.mealbuddy.tests.persistence.DataAccessStub;

public class AccessAccountTest {
    private static AccessAccount accessAccount;
    private static Account account;
    private static Day day;
    private static UserInfo userInfo;
    private static UserInfo dummyUserInfo;
    private static final String ACCOUNT_USERNAME = "TESTMuskyBoi";
    private static final String ACCOUNT_PASSWORD = "T3sla";
    private static final int DAY_OF_YEAR = 123;




    @Before
    public void initAccessAccount(){
        Services.initializeDB(Main.DATABASE_NAME);
        accessAccount = new AccessAccount();
        userInfo = new UserInfo("Elon Musk", ACCOUNT_USERNAME, ACCOUNT_PASSWORD, 280.0, 170.5, UserInfo.ActivityLevel.LOW, UserInfo.Sex.MALE, 40);
        account = new Account(userInfo);
        day = new Day(DAY_OF_YEAR);
        account.addDay(day);
        dummyUserInfo = new UserInfo("John Cena", "uCantCMe", "alwaysblue", 100.0, 180.0, UserInfo.ActivityLevel.LOW, UserInfo.Sex.MALE, 35);
        accessAccount.addAccount(account);

    }

    @Test
    public void addAccount_oneNewAccount_add(){
        //verify that the account isnt in the database
        Assert.assertNull(accessAccount.getAccount(dummyUserInfo.username));
        //add
        accessAccount.addAccount(dummyUserInfo);
        //check if it was properly added
        Assert.assertNotNull(accessAccount.getAccount(dummyUserInfo.username));
    }

    @Test
    public void addAccount_multipleAccounts_dontAdd(){
        Assert.assertNotNull(accessAccount.getAccount(userInfo.username));
        //add it 100 times
        for(int i = 0; i < 100; i++)
            accessAccount.addAccount(userInfo);
        //check if the account is still the same
        Assert.assertEquals(accessAccount.getAccount(userInfo.username), account);
    }

    @Test
    public void addAccount_multipleUsername_dontAdd(){
        Assert.assertEquals(userInfo, accessAccount.getAccount(ACCOUNT_USERNAME).user);
        //create a new user with different info and try to add
        UserInfo newUserInfo = new UserInfo(
          "", ACCOUNT_USERNAME, "", 0, 0, UserInfo.ActivityLevel.LOW, UserInfo.Sex.MALE, 1
        );
        accessAccount.addAccount(newUserInfo);
        //it shouldnt be the new user info, it should be the old one.
        Assert.assertNotEquals(newUserInfo.password, accessAccount.getAccount(ACCOUNT_USERNAME).user.password);
        Assert.assertEquals(userInfo, accessAccount.getAccount(ACCOUNT_USERNAME).user);
    }

    @Test
    public void addAccount_nullAccount_throwException(){
        //adding a null account to the database
        try {
            accessAccount.addAccount((Account) null);
        } catch (IllegalArgumentException IAE){
            Assert.assertTrue(true);
        }
        //adding a null userinfo to the database
        try {
            accessAccount.addAccount((UserInfo)null);
        } catch (IllegalArgumentException IAE){
            Assert.assertTrue(true);
        }
    }

    
    @Test
    public void updateAccountTest(){
        //---normal cases----
        //testing updating account
        Assert.assertEquals(ACCOUNT_USERNAME, accessAccount.getAccount(ACCOUNT_USERNAME).user.username);
        accessAccount.updateAccount(account.user.username, new Account(dummyUserInfo));
        Assert.assertEquals(dummyUserInfo.username, accessAccount.getAccount(dummyUserInfo.username).user.username);
        accessAccount.updateAccount(ACCOUNT_USERNAME, new Account(userInfo)); //reset it
        Assert.assertEquals(ACCOUNT_USERNAME, accessAccount.getAccount(ACCOUNT_USERNAME).user.username);

        //---testing edge cases---
        //verify the following account isnt in the database for the next couple of tests
        Assert.assertNull(accessAccount.getAccount("Idontexist"));
        //trying to update an account that doesnt exist
        accessAccount.updateAccount("Idontexist", null);
        Assert.assertNull(accessAccount.getAccount("Idontexist"));

        //trying to update null pointers
        accessAccount.updateAccount(null, null);
        Assert.assertTrue(true); //we didn't crash lol

    }

    @Test
    public void removeAccountTest(){
        //remove account normally
        Assert.assertNotNull(accessAccount.getAccount(ACCOUNT_USERNAME));
        accessAccount.removeAccount(ACCOUNT_USERNAME);
        Assert.assertNull(accessAccount.getAccount(ACCOUNT_USERNAME));
        accessAccount.addAccount(account);
        Assert.assertNotNull(accessAccount.getAccount(ACCOUNT_USERNAME));

        //verify the following account isnt in the database for the next couple of tests
        Assert.assertNull(accessAccount.getAccount("Idontexist"));

        //trying to remove and update accounts that dont exist
        accessAccount.removeAccount("Idontexist");
        Assert.assertNull(accessAccount.getAccount("Idontexist"));
        //trying to remove and update null pointers
        accessAccount.removeAccount(null);
        Assert.assertTrue(true); //we didn't crash lol
    }

    @Test
    public void getAndVerifyAccountTest(){
        //tests that should pass
        Assert.assertEquals(account, accessAccount.getAccount(ACCOUNT_USERNAME));
        Assert.assertEquals(account, accessAccount.validateLogin(ACCOUNT_USERNAME, ACCOUNT_PASSWORD));

        //---testing edge cases---
        //account not in DB
        Assert.assertNull(accessAccount.getAccount("ThisDoesntExist"));
        Assert.assertNull(accessAccount.validateLogin("This doesntExist", "this doesnt exist"));

        //account in DB but wrong password
        Assert.assertNull(accessAccount.validateLogin(ACCOUNT_USERNAME, "wrong password"));

        //passing null values
        Assert.assertNull(accessAccount.getAccount(null));
        Assert.assertNull(accessAccount.validateLogin(ACCOUNT_USERNAME, null));
        Assert.assertNull(accessAccount.validateLogin(null, "password"));
        Assert.assertNull(accessAccount.validateLogin(null, null));
        //---end of testing edge cases---
    }


    /* Day Testing */

    @Test
    public void getDayTest() {
        //get the dummy day
        Assert.assertEquals(day, accessAccount.getDay(ACCOUNT_USERNAME, DAY_OF_YEAR));

        //get a day that isn't currently being tracked by the account
        Assert.assertEquals(new Day(244), accessAccount.getDay(ACCOUNT_USERNAME, 244));

        //---testing edge cases
        //invalid day
        try {
            accessAccount.getDay(ACCOUNT_USERNAME, 34234234);
        } catch( IllegalArgumentException IAE) {
            Assert.assertTrue(true);
        }
    }
}
