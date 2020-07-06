
package comp3350.mealbuddy.tests;


import comp3350.mealbuddy

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import comp3350.mealbuddy.business.GoalFactory.RecommendedCalorieCalculator;
import comp3350.mealbuddy.tests.business.DefaultGoalFactorTest;
import comp3350.mealbuddy.tests.business.RecommendedCalorieCalculatorTest;


@RunWith(Suite.class)
@Suite.SuiteClasses({
        DefaultGoalFactorTest.class,
        RecommendedCalorieCalculatorTest.class
})

public class AllTests {
    // This class remains empty, it is used only as a holder for the above annotations
}