package comp3350.mealbuddy.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import comp3350.mealbuddy.tests.integration.IntegrationTests;


@RunWith(Suite.class)
@Suite.SuiteClasses({
        IntegrationTests.class
})

public class RunIntegrationTests {
    // This class remains empty, it is used only as a holder for the above annotations
}

