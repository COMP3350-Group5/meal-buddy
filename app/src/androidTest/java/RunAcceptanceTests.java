import org.junit.runners.Suite;
import org.junit.runner.RunWith;

import comp3350.mealbuddy.acceptance.HomeTests;


@RunWith(Suite.class)
@Suite.SuiteClasses({
        HomeTests.class
})

public class RunAcceptanceTests
{
    public RunAcceptanceTests()
    {
        System.out.println("Sample Acceptance tests");
    }
}

