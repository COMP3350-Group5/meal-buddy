package comp3350.mealbuddy.acceptance.acceptance;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.filters.LargeTest;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;


import comp3350.mealbuddy.R;
import comp3350.mealbuddy.business.AccessAccount;
import comp3350.mealbuddy.business.AccessEdible;
import comp3350.mealbuddy.business.Calculator;
import comp3350.mealbuddy.objects.Account;
import comp3350.mealbuddy.objects.Day;
import comp3350.mealbuddy.objects.UserInfo;
import comp3350.mealbuddy.objects.consumables.Edible;
import comp3350.mealbuddy.objects.consumables.Food;
import comp3350.mealbuddy.presentation.HomeActivity;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.anything;

@RunWith(AndroidJUnit4ClassRunner.class)
@LargeTest
public class ViewStatsTests {

    @Rule
    public ActivityTestRule<HomeActivity> main = new ActivityTestRule<>(HomeActivity.class);
    private Account TEST_ACCOUNT;
    private AccessEdible ACCESS_EDIBLE;
    private AccessAccount ACCESS_ACCOUNT;
    private Food DUMMY_FOOD;
    private UserInfo TESTER_INFO;
    private Day DAY;
    private Calculator CALCULATOR;

    @Before
    public void init() {
        cleanup();
        System.err.println("INIT");
        ACCESS_ACCOUNT = new AccessAccount();
        ACCESS_EDIBLE = new AccessEdible();
        TESTER_INFO = new UserInfo("testFirst testLast", "tester", "test123", 10.0, 10.0, UserInfo.ActivityLevel.LOW, UserInfo.Sex.MALE, 10);
        DUMMY_FOOD = new Food("123food123", new ArrayList<>(Arrays.asList("yo", "yo")));
        DUMMY_FOOD.setWeight(42);
        DUMMY_FOOD.updateMacro(Edible.Macros.Carbohydrates, 42);
        DUMMY_FOOD.updateMacro(Edible.Macros.Protein, 42);
        DUMMY_FOOD.updateMacro(Edible.Macros.Fat, 42);
        ACCESS_EDIBLE.addEdible(DUMMY_FOOD);
        TEST_ACCOUNT = new Account(TESTER_INFO);
        ACCESS_ACCOUNT.addAccount(TESTER_INFO);
        DAY = ACCESS_ACCOUNT.getDay(TESTER_INFO.username, Calendar.getInstance().get(Calendar.DAY_OF_YEAR));
        DAY.addToMeal(Day.MealTimeType.BREAKFAST, DUMMY_FOOD, 1);
        ACCESS_ACCOUNT.updateDay(TESTER_INFO.username, DAY);
        CALCULATOR = new Calculator(DAY);
    }

    @After
    public void cleanup() {
        System.err.println("CLEANUP");
        try {
            ACCESS_ACCOUNT.removeAccount(TESTER_INFO.username);
        } catch (Exception e) {
        }
        try {
            ACCESS_EDIBLE.removeEdible(DUMMY_FOOD.name);
        } catch (Exception e) {
        }
    }

    @Test
    public void viewTotalAnalytics() {
        init();
        //login
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.etUsername)).perform(typeText(TESTER_INFO.username), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.etPassword)).perform(typeText(TESTER_INFO.password), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.btnLogin)).perform(click());
        //navigate to the view analytics page;
        onView(withId(R.id.bottom_navigation)).perform(ClickNav.clickStats()).check(matches(isDisplayed()));
        onView(withId(R.id.tvStatsCaloriesValue)).check(matches(withText(String.valueOf(CALCULATOR.getTotalCalories()))));
        //go back to timeline
        onView(withId(R.id.bottom_navigation)).perform(ClickNav.clickTimeline()).check(matches(isDisplayed()));
        //add food
        onView(withId(R.id.fabAdd)).perform(click());
        onView(withId(R.id.fabFood)).perform(click()).check(matches(isDisplayed()));
        //add 123test123 to the food
        onData(anything()).inAdapterView(withId(R.id.lvSearchbar)).atPosition(0).perform(click());
        onView(withId(R.id.etQuantity)).perform(typeText("1"));
        onView(withId(R.id.btnConfirm)).perform(click());
        //navigate to the view analytics page
        onView(withId(R.id.bottom_navigation)).perform(ClickNav.clickStats()).check(matches(isDisplayed()));
        CALCULATOR = new Calculator(ACCESS_ACCOUNT.getDay(TESTER_INFO.username, Calendar.getInstance().get(Calendar.DAY_OF_YEAR)));
        onView(withId(R.id.tvStatsCaloriesValue)).check(matches(withText(String.valueOf(CALCULATOR.getTotalCalories()))));
        cleanup();
    }

    @Test
    public void viewMealAnalytics() {
        init();
        //login
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.etUsername)).perform(typeText(TESTER_INFO.username), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.etPassword)).perform(typeText(TESTER_INFO.password), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.btnLogin)).perform(click());
        //open the breakfast pop up
        onView(withId(R.id.cardBreakfast)).perform(click());
        onView(withId(R.id.tvViewFoodFat)).check(matches(withText("Fat: " + CALCULATOR.getMealTimeMacroCalories(Day.MealTimeType.BREAKFAST, Edible.Macros.Fat) + "g")));
        onView(withId(R.id.tvViewFoodCarbs)).check(matches(withText("Carbs: " + CALCULATOR.getMealTimeMacroCalories(Day.MealTimeType.BREAKFAST, Edible.Macros.Carbohydrates) + "g")));
        onView(withId(R.id.tvViewFoodProtein)).check(matches(withText("Protein: " + CALCULATOR.getMealTimeMacroCalories(Day.MealTimeType.BREAKFAST, Edible.Macros.Protein) + "g")));
        Espresso.pressBack();
        //add food
        onView(withId(R.id.fabAdd)).perform(click());
        onView(withId(R.id.fabFood)).perform(click()).check(matches(isDisplayed()));
        //add 123test123 to the food
        onData(anything()).inAdapterView(withId(R.id.lvSearchbar)).atPosition(0).perform(click());
        onView(withId(R.id.etQuantity)).perform(typeText("1"));
        onView(withId(R.id.btnConfirm)).perform(click());

        CALCULATOR = new Calculator(ACCESS_ACCOUNT.getDay(TESTER_INFO.username, Calendar.getInstance().get(Calendar.DAY_OF_YEAR)));

        onView(withId(R.id.cardBreakfast)).perform(click());
        onView(withId(R.id.tvViewFoodFat)).check(matches(withText("Fat: " + CALCULATOR.getMealTimeMacroCalories(Day.MealTimeType.BREAKFAST, Edible.Macros.Fat) + "g")));
        onView(withId(R.id.tvViewFoodCarbs)).check(matches(withText("Carbs: " + CALCULATOR.getMealTimeMacroCalories(Day.MealTimeType.BREAKFAST, Edible.Macros.Carbohydrates) + "g")));
        onView(withId(R.id.tvViewFoodProtein)).check(matches(withText("Protein: " + CALCULATOR.getMealTimeMacroCalories(Day.MealTimeType.BREAKFAST, Edible.Macros.Protein) + "g")));
        cleanup();
    }

}
