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
    //private static DataAccess database = Services.createDataAccess(Main.DATABASE_NAME);  //HQSQLDB
    private static DataAccess database = Services.createDataAccess(new DataAccessStub("StubDB"));         //STUBDB
    private static AccessAccount accessAccount = new AccessAccount();
    private static Account account;
    private static final String ACCOUNT_USERNAME = "TESTMuskyBoi";
    private static final String ACCOUNT_PASSWORD = "T3sla";


    @Before
    public void initAccessAccount(){
        account = new Account(new UserInfo("Elon Musk", ACCOUNT_USERNAME, ACCOUNT_PASSWORD, 280.0, 170.5, UserInfo.ActivityLevel.LOW, UserInfo.Sex.MALE, 40));
        accessAccount.addAccount(account);
    }

    @Test
    public void getAndVerifyAccount(){

        Assert.assertEquals(account, accessAccount.getAccount(ACCOUNT_USERNAME));
        Assert.assertEquals(account, accessAccount.validateLogin(ACCOUNT_USERNAME, ACCOUNT_PASSWORD));

    }
}
