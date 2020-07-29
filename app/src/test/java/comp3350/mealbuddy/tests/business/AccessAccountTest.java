package comp3350.mealbuddy.tests.business;

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

            //STUBDB

    private static AccessAccount accessAccount;
    private static Account account;
    private static final String ACCOUNT_USERNAME = "TESTMuskyBoi";
    private static final String ACCOUNT_PASSWORD = "T3sla";


    @Before
    public void initAccessAccount(){
        Services.initializeDB(new DataAccessStub("Test"));
        accessAccount = new AccessAccount();
        account = new Account(new UserInfo("Elon Musk", ACCOUNT_USERNAME, ACCOUNT_PASSWORD, 280.0, 170.5, UserInfo.ActivityLevel.LOW, UserInfo.Sex.MALE, 40));
        accessAccount.addAccount(account);
    }

    @Test
    public void getAndVerifyAccount(){

        Assert.assertEquals(account, accessAccount.getAccount(ACCOUNT_USERNAME));
        Assert.assertEquals(account, accessAccount.validateLogin(ACCOUNT_USERNAME, ACCOUNT_PASSWORD));

    }
}
