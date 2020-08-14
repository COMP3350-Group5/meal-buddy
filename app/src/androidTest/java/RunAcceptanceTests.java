import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import comp3350.mealbuddy.acceptance.ExerciseTests;
import comp3350.mealbuddy.acceptance.HomeTests;
import comp3350.mealbuddy.acceptance.LabelTests;
import comp3350.mealbuddy.acceptance.LogFoodTests;
import comp3350.mealbuddy.acceptance.MealTests;
import comp3350.mealbuddy.acceptance.RecommendationTests;
import comp3350.mealbuddy.acceptance.ViewStatsTests;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        ExerciseTests.class,
        HomeTests.class,
        LabelTests.class,
        LogFoodTests.class,
        MealTests.class,
        ViewStatsTests.class,
        RecommendationTests.class,
        ViewStatsTests.class
})

public class RunAcceptanceTests {
    public RunAcceptanceTests() {
        System.out.println("Sample Acceptance tests");
    }
}


