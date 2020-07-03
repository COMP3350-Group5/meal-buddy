
package comp3350.mealbuddy.tests;

import junit.framework.Test;
import junit.framework.TestSuite;

import comp3350.mealbuddy.tests.business.RatioGoalTrackerTest;
import comp3350.mealbuddy.tests.objects.GoalTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;


@RunWith(Suite.class)
@Suite.SuiteClasses({
        GoalTest.class,
        RatioGoalTrackerTest.class,
})

public class AllTests {
    // This class remains empty, it is used only as a holder for the above annotations
}