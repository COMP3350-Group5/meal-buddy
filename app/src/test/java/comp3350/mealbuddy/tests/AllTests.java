
package comp3350.mealbuddy.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import comp3350.mealbuddy.tests.business.CalculatorTest;
//import comp3350.mealbuddy.tests.business.goalFactory.DefaultGoalFactorTest;
import comp3350.mealbuddy.tests.business.goalFactory.DefaultGoalFactorTest;
import comp3350.mealbuddy.tests.business.GoalTrackerTest;
import comp3350.mealbuddy.tests.business.goalFactory.RecommendedCalorieCalculatorTest;
import comp3350.mealbuddy.tests.objects.AccountTest;
import comp3350.mealbuddy.tests.objects.DayTest;
import comp3350.mealbuddy.tests.objects.ExerciseTest;
import comp3350.mealbuddy.tests.objects.UserInfoTest;
import comp3350.mealbuddy.tests.objects.consumables.EdibleTest;
import comp3350.mealbuddy.tests.objects.consumables.FoodIntPairTest;
import comp3350.mealbuddy.tests.objects.consumables.FoodTest;
import comp3350.mealbuddy.tests.objects.consumables.MealTest;
import comp3350.mealbuddy.tests.objects.goals.CalorieGoalTest;
import comp3350.mealbuddy.tests.objects.goals.GoalTest;
import comp3350.mealbuddy.tests.objects.goals.LabelGoalTest;
import comp3350.mealbuddy.tests.objects.goals.MacroGoalTest;
import comp3350.mealbuddy.tests.objects.goals.MicroGoalTest;


@RunWith(Suite.class)
@Suite.SuiteClasses({
        FoodIntPairTest.class,
        FoodTest.class,
        MealTest.class,
        CalorieGoalTest.class,
        GoalTest.class,
        LabelGoalTest.class,
        MacroGoalTest.class,
        MicroGoalTest.class,
        AccountTest.class,
        EdibleTest.class,
        GoalTrackerTest.class,
        CalculatorTest.class,
        DefaultGoalFactorTest.class,
        RecommendedCalorieCalculatorTest.class,
        ExerciseTest.class,
        UserInfoTest.class,
        DayTest.class

})

public class AllTests {
    // This class remains empty, it is used only as a holder for the above annotations
}