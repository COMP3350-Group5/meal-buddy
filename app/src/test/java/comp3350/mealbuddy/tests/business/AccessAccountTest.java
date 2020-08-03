package comp3350.mealbuddy.tests.business;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import comp3350.mealbuddy.application.Main;
import comp3350.mealbuddy.application.Services;
import comp3350.mealbuddy.business.AccessAccount;
import comp3350.mealbuddy.objects.Account;
import comp3350.mealbuddy.objects.Day;
import comp3350.mealbuddy.objects.UserInfo;

public class AccessAccountTest {
    private static final String ACCOUNT_USERNAME = "TESTMuskyBoi";
    private static final String ACCOUNT_PASSWORD = "T3sla";
    private static final int DAY_OF_YEAR = 123;
    private static AccessAccount accessAccount;
    private static Account account;
    private static Day day;
    private static UserInfo userInfo;
    private static UserInfo dummyUserInfo;

    @Before
    public void initAccessAccount() {
        Services.initializeDB(Main.DATABASE_NAME);
        accessAccount = new AccessAccount();
        userInfo = new UserInfo("Elon Musk", ACCOUNT_USERNAME, ACCOUNT_PASSWORD, 280.0, 170.5, UserInfo.ActivityLevel.LOW, UserInfo.Sex.MALE, 40);
        account = new Account(userInfo);
        day = new Day(DAY_OF_YEAR);
        account.addDay(day);
        dummyUserInfo = new UserInfo("John Cena", "uCantCMe", "alwaysblue", 100.0, 180.0, UserInfo.ActivityLevel.LOW, UserInfo.Sex.MALE, 35);
        accessAccount.addAccount(account);
        accessAccount.addAccount(dummyUserInfo);
    }

    @Test
    public void addAccount_oneNewAccount_add() {
        //verify that the account isnt in the database
        accessAccount.removeAccount(dummyUserInfo.username);
        Assert.assertNull(accessAccount.getAccount(dummyUserInfo.username));
        //add
        accessAccount.addAccount(dummyUserInfo);
        //check if it was properly added
        Assert.assertNotNull(accessAccount.getAccount(dummyUserInfo.username));
    }

    @Test
    public void addAccount_multipleAccounts_dontAdd() {
        Assert.assertNotNull(accessAccount.getAccount(userInfo.username));
        //add it 100 times
        for (int i = 0; i < 100; i++)
            accessAccount.addAccount(userInfo);
        //check if the account is still the same
        Assert.assertEquals(accessAccount.getAccount(userInfo.username), account);
    }

    @Test
    public void addAccount_multipleUsername_dontAdd() {
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
    public void addAccount_nullAccount_throwException() {
        //adding a null account to the database
        try {
            accessAccount.addAccount((Account) null);
            Assert.fail();
        } catch (IllegalArgumentException IAE) {
            Assert.assertTrue(true);
        }
        //adding a null userinfo to the database
        try {
            accessAccount.addAccount((UserInfo) null);
            Assert.fail();
        } catch (IllegalArgumentException IAE) {
            Assert.assertTrue(true);
        }
    }

    @Test
    public void updateAccount_sameUserName_update() {
        //testing updating account
        Assert.assertEquals(ACCOUNT_USERNAME, accessAccount.getAccount(ACCOUNT_USERNAME).user.username);
        //update with dummy info
        dummyUserInfo.username = ACCOUNT_USERNAME;
        accessAccount.updateUserInfo(ACCOUNT_USERNAME, new Account(dummyUserInfo));

        //verify that it updated
        Assert.assertEquals(dummyUserInfo, accessAccount.getAccount(ACCOUNT_USERNAME).user);
        //reset it back to the other account
        accessAccount.updateUserInfo(ACCOUNT_USERNAME, new Account(userInfo));
        Assert.assertEquals(ACCOUNT_USERNAME, accessAccount.getAccount(ACCOUNT_USERNAME).user.username);
    }

    @Test
    public void updateAccount_changeUserNameToExistingUser_throwException() {
        //update with dummy info
        try {
            accessAccount.updateUserInfo(account.user.username, new Account(dummyUserInfo));
            Assert.fail();
        } catch (IllegalArgumentException iae) {
            Assert.assertTrue(true);
        }
    }

    @Test
    public void updateAccount_changeUserName_update() {
        UserInfo ui = new UserInfo(dummyUserInfo);
        UserInfo ui2 = new UserInfo(userInfo);
        ui2.username = "firstUsername";
        ui.username = "newUsername";

        Assert.assertNull(accessAccount.getAccount(ui2.username));
        accessAccount.addAccount(ui2);
        Assert.assertNotNull(accessAccount.getAccount(ui2.username));

        //confirm that the new username isnt in the db first
        Assert.assertNull(accessAccount.getAccount(ui.username));
        accessAccount.updateUserInfo(ui2.username, new Account(ui));
        Assert.assertNotNull(accessAccount.getAccount(ui.username));

        accessAccount.removeAccount(ui.username); //remove the new user
    }

    @Test
    public void updateAccount_usernameDoesntExist_throwException() {
        try {
            //confirm that it doesnt exist
            Assert.assertNull(accessAccount.getAccount("Idontexist"));
            //trying to update an account that doesnt exist
            accessAccount.updateUserInfo("Idontexist", new Account(dummyUserInfo));
            Assert.fail();
        } catch (NullPointerException iae) {
            //confirm that it wasnt added
            Assert.assertNull(accessAccount.getAccount("Idontexist"));
        }
    }

    @Test
    public void updateAccount_nullAccount_throwException() {
        try {
            accessAccount.updateUserInfo("Idontexist", null);
            Assert.fail();
        } catch (NullPointerException iae) {
            Assert.assertTrue(true);
        }
    }

    @Test
    public void removeAccount_accountExists_remove() {
        //remove account normally
        Assert.assertNotNull(accessAccount.getAccount(ACCOUNT_USERNAME));
        accessAccount.removeAccount(ACCOUNT_USERNAME);
        Assert.assertNull(accessAccount.getAccount(ACCOUNT_USERNAME));

        //add it back for other tests
        accessAccount.addAccount(account);
        Assert.assertNotNull(accessAccount.getAccount(ACCOUNT_USERNAME));
    }

    @Test
    public void removeAccount_usernameDoesntExist_throwException() {
        try {
            accessAccount.removeAccount("Idontexist");
            Assert.fail();
        } catch (NullPointerException npe) {
            Assert.assertTrue(true);
        }
    }

    @Test
    public void removeAccount_nullUserName_throwException() {
        try {
            accessAccount.removeAccount(null);
            Assert.fail();
        } catch (NullPointerException npe) {
            Assert.assertTrue(true);
        }
    }

    @Test
    public void getAndVerifyAccount_getUserInDb_returnAccount() {
        Assert.assertEquals(account, accessAccount.getAccount(ACCOUNT_USERNAME));
        Assert.assertEquals(account, accessAccount.validateLogin(ACCOUNT_USERNAME, ACCOUNT_PASSWORD));

    }

    @Test
    public void getAndVerifyAccount_getUserNotInDb_returnNull() {
        Assert.assertNull(accessAccount.getAccount("ThisDoesntExist"));
        Assert.assertNull(accessAccount.validateLogin("This doesntExist", "this doesnt exist"));
    }

    @Test
    public void verifyAccount_wrongPassword_returnNull() {
        Assert.assertNull(accessAccount.validateLogin(ACCOUNT_USERNAME, "wrong password"));
    }

    @Test
    public void getAndVerifyAccount_nullValues_returnNull() {
        Assert.assertNull(accessAccount.getAccount(null));
        Assert.assertNull(accessAccount.validateLogin(ACCOUNT_USERNAME, null));
        Assert.assertNull(accessAccount.validateLogin(null, "password"));
        Assert.assertNull(accessAccount.validateLogin(null, null));
    }

    @Test
    public void getDay_dayInTracked_returnDay() {
        Assert.assertEquals(day, accessAccount.getDay(ACCOUNT_USERNAME, DAY_OF_YEAR));
    }

    @Test
    public void getDay_dayNotBeingTracked_returnDay() {
        Assert.assertEquals(new Day(244), accessAccount.getDay(ACCOUNT_USERNAME, 244));
    }

    @Test
    public void getDay_dayOutOfRange_throwException() {
        try {
            accessAccount.getDay(ACCOUNT_USERNAME, 34234234);
            Assert.fail();
        } catch (IllegalArgumentException IAE) {
            Assert.assertTrue(true);
        }
    }

    @Test
    public void updateDay_userNameDoesntExist_throwException() {
        try {
            accessAccount.updateDay(null, day);
            Assert.fail();
        } catch (IllegalArgumentException IAE) {
            Assert.assertTrue(true);
        }
    }

    @Test
    public void updateDay_dayIsNull_throwException() {
        try {
            accessAccount.updateDay(ACCOUNT_USERNAME, null);
            Assert.fail();
        } catch (IllegalArgumentException IAE) {
            Assert.assertTrue(true);
        }
    }

    @After
    public void clean() {
        //remove if the accounts still exist
        try {
            accessAccount.removeAccount(ACCOUNT_USERNAME);
        } catch (NullPointerException iae) {
        }
        try {
            accessAccount.removeAccount(dummyUserInfo.username);
        } catch (NullPointerException iae) {
        }
    }
}
