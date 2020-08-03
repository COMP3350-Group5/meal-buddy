package comp3350.mealbuddy.tests.integration;

import org.junit.Test;

import comp3350.mealbuddy.application.Main;
import comp3350.mealbuddy.application.Services;
import comp3350.mealbuddy.persistence.DataAccess;
import comp3350.mealbuddy.tests.persistence.DataAccessTest;

public class DataAccessHSQLDBTest {

    @Test
    public void testDataAccess() {
        DataAccess dataAccess;

        Services.closeDAS();

        System.out.println("\nStarting Integration test DataAccess (using default DB)");

        // Use the following two statements to run with the real database
        Services.createDataAccess(Main.DATABASE_NAME);
        dataAccess = Services.getDataAccess(Main.DATABASE_NAME);

        DataAccessTest.dataAccessTest(dataAccess);

        Services.closeDAS();

        System.out.println("Finished Integration test DataAccess (using default DB)");
    }

}
