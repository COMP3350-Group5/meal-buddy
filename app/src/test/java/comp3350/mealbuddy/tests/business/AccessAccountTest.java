package comp3350.mealbuddy.tests.business;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import comp3350.mealbuddy.application.Services;
import comp3350.mealbuddy.business.AccessAccount;
import comp3350.mealbuddy.objects.Account;
import comp3350.mealbuddy.objects.Day;
import comp3350.mealbuddy.objects.UserInfo;
import comp3350.mealbuddy.objects.goals.CalorieGoal;
import comp3350.mealbuddy.objects.goals.Goal;
import comp3350.mealbuddy.tests.persistence.DataAccessStub;

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
        Services.createDataAccess(new DataAccessStub("Stub"));  //stub
        // Services.createDataAccess(Main.DATABASE_NAME);    //hsql
        accessAccount = new AccessAccount();
        userInfo = new UserInfo("Elon Musk", ACCOUNT_USERNAME, ACCOUNT_PASSWORD, 280.0, 170.5, UserInfo.ActivityLevel.LOW, UserInfo.Sex.MALE, 40);
        account = new Account(userInfo);
        day = new Day(DAY_OF_YEAR);
        account.addDay(day);
        dummyUserInfo = new UserInfo("John Cena", "uCantCMe", "alwaysblue", 100.0, 180.0, UserInfo.ActivityLevel.LOW, UserInfo.Sex.MALE, 35);
        accessAccount.addAccount(userInfo);
        accessAccount.addAccount(dummyUserInfo);
    }

    @Test
    public void accountExists_avgCases_returnTrueIfExists() {
        Assert.assertTrue(accessAccount.accountExists(userInfo.username));
        Assert.assertFalse(accessAccount.accountExists("FAKE ACCOUNT"));
    }

    @Test
    public void addAccount_oneNewAccount_add() {
        //verify that the account isnt in the database
        accessAccount.removeAccount(dummyUserInfo.username);
        Assert.assertNull(accessAccount.getUserInfo(dummyUserInfo.username));
        //add
        accessAccount.addAccount(dummyUserInfo);
        //check if it was properly added
        Assert.assertNotNull(accessAccount.getUserInfo(dummyUserInfo.username));
    }


    @Test
    public void addAccount_multipleAccounts_dontAdd() {
        Assert.assertNotNull(accessAccount.getUserInfo(userInfo.username));
        try {
            accessAccount.addAccount(userInfo);
            Assert.fail();
        } catch (IllegalArgumentException e) {
            Assert.assertTrue(true);
        }
        //check if the account is still the same
        Assert.assertEquals(accessAccount.getUserInfo(userInfo.username), account.user);
    }


    @Test
    public void addAccount_nullAccount_throwException() {
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
        Assert.assertEquals(ACCOUNT_USERNAME, accessAccount.getUserInfo(ACCOUNT_USERNAME).username);
        //update with dummy info
        dummyUserInfo.username = ACCOUNT_USERNAME;
        accessAccount.updateUserInfo(ACCOUNT_USERNAME, dummyUserInfo);

        //verify that it updated
        Assert.assertEquals(dummyUserInfo, accessAccount.getUserInfo(ACCOUNT_USERNAME));
        //reset it back to the other account
        accessAccount.updateUserInfo(ACCOUNT_USERNAME, userInfo);
        Assert.assertEquals(ACCOUNT_USERNAME, accessAccount.getUserInfo(ACCOUNT_USERNAME).username);
    }

    @Test
    public void updateAccount_changeUserNameToExistingUser_throwException() {
        //update with dummy info
        try {
            accessAccount.updateUserInfo(account.user.username, dummyUserInfo);
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

        Assert.assertNull(accessAccount.getUserInfo(ui2.username));
        accessAccount.addAccount(ui2);
        Assert.assertNotNull(accessAccount.getUserInfo(ui2.username));

        //confirm that the new username isnt in the db first
        Assert.assertNull(accessAccount.getUserInfo(ui.username));
        accessAccount.updateUserInfo(ui2.username, ui);
        Assert.assertNotNull(accessAccount.getUserInfo(ui.username));

        accessAccount.removeAccount(ui.username); //remove the new user
    }

    @Test
    public void updateAccount_usernameDoesntExist_throwException() {
        try {
            //confirm that it doesnt exist
            Assert.assertNull(accessAccount.getUserInfo("Idontexist"));
            //trying to update an account that doesnt exist
            accessAccount.updateUserInfo("Idontexist", dummyUserInfo);
            Assert.fail();
        } catch (NullPointerException iae) {
            //confirm that it wasnt added
            Assert.assertNull(accessAccount.getUserInfo("Idontexist"));
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
        Assert.assertNotNull(accessAccount.getUserInfo(ACCOUNT_USERNAME));
        accessAccount.removeAccount(ACCOUNT_USERNAME);
        Assert.assertNull(accessAccount.getUserInfo(ACCOUNT_USERNAME));

        //add it back for other tests
        accessAccount.addAccount(account.user);
        Assert.assertNotNull(accessAccount.getUserInfo(ACCOUNT_USERNAME));
    }

    @Test
    public void removeAccount_usernameDoesntExist_nothingHappens() {
        accessAccount.removeAccount("Idontexist");
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
    public void getAndVerifyUserInfo_getUserInDb_returnAccount() {
        Assert.assertEquals(account.user, accessAccount.getUserInfo(ACCOUNT_USERNAME));
        Assert.assertEquals(account.user, accessAccount.validateLogin(ACCOUNT_USERNAME, ACCOUNT_PASSWORD));
    }

    @Test
    public void getAndVerifyUserInfo_getUserNotInDb_returnNull() {
        Assert.assertNull(accessAccount.getUserInfo("ThisDoesntExist"));
        Assert.assertNull(accessAccount.validateLogin("This doesntExist", "this doesnt exist"));
    }

    @Test
    public void verifyAccount_wrongPassword_returnNull() {
        Assert.assertNull(accessAccount.validateLogin(ACCOUNT_USERNAME, "wrong password"));
    }

    @Test
    public void getAndVerifyUserInfo_nullValues_returnNull() {
        Assert.assertNull(accessAccount.getUserInfo(null));
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
    public void getDay_dayNotBeingTracked_returnDefaultDay() {
        //arrange
        Day defaultDay = new Day(Account.DEFAULT_DAY_NUM);
        Goal goal = new CalorieGoal(1, 2);
        defaultDay.addGoal(new CalorieGoal(1, 2));
        accessAccount.updateDay(ACCOUNT_USERNAME, defaultDay);

        //act
        Day fetchedDay = accessAccount.getDay(ACCOUNT_USERNAME, 300);

        //assert
        Assert.assertEquals(300, fetchedDay.dayOfYear);
        Assert.assertTrue(fetchedDay.isExerciseEmpty());
        Assert.assertEquals(goal, fetchedDay.getGoal(goal));
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

    @Test
    public void getDays_nullUsername_throwException() {
        try {
            accessAccount.getDays(null);
            Assert.fail();
        } catch (NullPointerException IAE) {
            Assert.assertTrue(true);
        }
    }

    @Test
    public void getDays_usernameDoesntExist_throwException() {
        try {
            accessAccount.getDays("i dont EXistt123");
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
        Services.closeDAS();
    }
}
