package comp3350.mealbuddy.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import comp3350.mealbuddy.tests.business.BusinessTests;
import comp3350.mealbuddy.tests.objects.ObjectTests;
import comp3350.mealbuddy.tests.persistence.PersistenceTests;


@RunWith(Suite.class)
@Suite.SuiteClasses({
        PersistenceTests.class,
        ObjectTests.class,
        BusinessTests.class
})

public class RunUnitTests {
    // This class remains empty, it is used only as a holder for the above annotations
}
