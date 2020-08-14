import org.junit.runners.Suite;
import org.junit.runner.RunWith;

import comp3350.mealbuddy.acceptance.ExerciseTests;
import comp3350.mealbuddy.acceptance.HomeTests;
import comp3350.mealbuddy.acceptance.LabelTests;
import comp3350.mealbuddy.acceptance.LogFoodTests;
import comp3350.mealbuddy.acceptance.RecommendationTests;
import comp3350.mealbuddy.acceptance.ViewStatsTests;


@RunWith(Suite.class)
@Suite.SuiteClasses({
        HomeTests.class,
        ExerciseTests.class,
        LabelTests.class,
        LogFoodTests.class,
        ViewStatsTests.class,
        RecommendationTests.class

})

public class RunAcceptanceTests
{
    public RunAcceptanceTests()
    {
        System.out.println("Sample Acceptance tests");
    }
}

