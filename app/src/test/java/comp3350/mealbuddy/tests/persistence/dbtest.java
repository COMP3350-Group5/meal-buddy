package comp3350.mealbuddy.tests.persistence;

import org.junit.Assert;
import org.junit.Test;

import comp3350.mealbuddy.application.Main;
import comp3350.mealbuddy.application.Services;
import comp3350.mealbuddy.objects.Account;
import comp3350.mealbuddy.persistence.DataAccess;

public class dbtest {

    private DataAccess DAS = Services.openDAS(Main.DATABASE_NAME);


    @Test
    public void tester() {
        Account a = DAS.getAccount("admin");
        Assert.assertNull(a);
    }
}