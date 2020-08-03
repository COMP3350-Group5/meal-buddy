package comp3350.mealbuddy.tests.business;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        GoalTrackerTest.class,
        CalculatorTest.class,
        AccessAccountTest.class,
        AccessLabelTest.class,
        AccessEdibleTest.class
})

public class BusinessTests {
    // This class remains empty, it is used only as a holder for the above annotations
}

