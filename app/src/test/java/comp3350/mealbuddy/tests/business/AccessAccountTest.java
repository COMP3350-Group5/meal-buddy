package comp3350.mealbuddy.tests.business;

import org.hsqldb.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import comp3350.mealbuddy.application.Main;
import comp3350.mealbuddy.application.Services;
import comp3350.mealbuddy.business.AccessAccount;
import comp3350.mealbuddy.objects.Account;
import comp3350.mealbuddy.objects.UserInfo;
import comp3350.mealbuddy.persistence.DataAccess;
import comp3350.mealbuddy.tests.persistence.DataAccessStub;

public class AccessAccountTest {
    private static AccessAccount accessAccount;
    private static Account account;
    private static UserInfo userInfo;
    private static UserInfo dummyUserInfo;
    private static final String ACCOUNT_USERNAME = "TESTMuskyBoi";
    private static final String ACCOUNT_PASSWORD = "T3sla";


    @Before
    public void initAccessAccount(){
        Services.initializeDB(Main.DATABASE_NAME);
        accessAccount = new AccessAccount();
        userInfo = new UserInfo("Elon Musk", ACCOUNT_USERNAME, ACCOUNT_PASSWORD, 280.0, 170.5, UserInfo.ActivityLevel.LOW, UserInfo.Sex.MALE, 40);
        account = new Account(userInfo);
        dummyUserInfo = new UserInfo("John Cena", "uCantCMe", "alwaysblue", 100.0, 180.0, UserInfo.ActivityLevel.LOW, UserInfo.Sex.MALE, 35);

        accessAccount.addAccount(account);
    }

    @Test
    public void addAccountTest(){

        //adding multiple accounts
        Assert.assertNotNull(accessAccount.getAccount(userInfo.username));
        for(int i = 0; i < 100; i++)
            accessAccount.addAccount(userInfo);
        Assert.assertNotNull(accessAccount.getAccount(userInfo.username));

        //adding new account
        accessAccount.removeAccount(ACCOUNT_USERNAME);
        Assert.assertNull(accessAccount.getAccount(ACCOUNT_USERNAME));
        accessAccount.addAccount(account);
        Assert.assertNotNull(accessAccount.getAccount(ACCOUNT_USERNAME));

        //adding a null account to the database
        accessAccount.addAccount((Account)null);
        Assert.assertTrue(true); //we didnt crash
        accessAccount.addAccount((UserInfo)null);
        Assert.assertTrue(true); //we didnt crash
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
    public void getAndVerifyAccount(){
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


}
