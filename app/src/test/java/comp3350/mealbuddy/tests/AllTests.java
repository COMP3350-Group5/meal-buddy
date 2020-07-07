
package comp3350.mealbuddy.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import comp3350.mealbuddy.tests.business.CalculatorTest;
import comp3350.mealbuddy.tests.business.DefaultGoalFactorTest;
import comp3350.mealbuddy.tests.business.GoalTrackerTest;
import comp3350.mealbuddy.tests.business.RecommendedCalorieCalculatorTest;


@RunWith(Suite.class)
@Suite.SuiteClasses({
        GoalTrackerTest.class,
        CalculatorTest.class,
        DefaultGoalFactorTest.class,
        RecommendedCalorieCalculatorTest.class
})

public class AllTests {
    // This class remains empty, it is used only as a holder for the above annotations
}