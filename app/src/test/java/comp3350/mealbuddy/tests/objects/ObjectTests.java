package comp3350.mealbuddy.tests.objects;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import comp3350.mealbuddy.tests.objects.consumables.ConsumablesTests;
import comp3350.mealbuddy.tests.objects.goals.GoalsTests;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        AccountTest.class,
        ExerciseTest.class,
        UserInfoTest.class,
        DayTest.class,
        GoalsTests.class,
        ConsumablesTests.class
})

public class ObjectTests {
    // This class remains empty, it is used only as a holder for the above annotations
}
