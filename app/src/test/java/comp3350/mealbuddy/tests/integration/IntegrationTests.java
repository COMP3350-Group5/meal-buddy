package comp3350.mealbuddy.tests.integration;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;


@RunWith(Suite.class)
@Suite.SuiteClasses({
        DataAccessHSQLDBTest.class,
        BusinessPersistenceSeamTest.class
})

public class IntegrationTests {
    // This class remains empty, it is used only as a holder for the above annotations
}

