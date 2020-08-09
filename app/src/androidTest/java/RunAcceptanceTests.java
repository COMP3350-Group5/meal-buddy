import org.junit.runners.Suite;
import org.junit.runner.RunWith;

import comp3350.mealbuddy.acceptance.SampleAcceptanceTests;


@RunWith(Suite.class)
@Suite.SuiteClasses({
        SampleAcceptanceTests.class
})

public class RunAcceptanceTests
{
    public RunAcceptanceTests()
    {
        System.out.println("Sample Acceptance tests");
    }
}

