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

import static androidx.test.espresso.Espresso.closeSoftKeyboard;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasErrorText;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withSpinnerText;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.core.AllOf.allOf;

@RunWith(AndroidJUnit4ClassRunner.class)
@LargeTest
public class CustomGoalTests {

    private Account TEST_ACCOUNT;
    private AccessEdible ACCESS_EDIBLE;
    private AccessAccount ACCESS_ACCOUNT;
    private Food DUMMY_FOOD;
    private UserInfo TESTER_INFO;
    private Day DAY;
    private Calculator CALCULATOR;

    @Before
    public void init(){
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
    public void cleanup(){
        System.err.println("CLEANUP");
        try {
            ACCESS_ACCOUNT.removeAccount(TESTER_INFO.username);
        } catch (Exception e){}
        try {
            ACCESS_EDIBLE.removeEdible(DUMMY_FOOD.name);
        } catch (Exception e){}
    }

    @Rule
    public ActivityTestRule<HomeActivity> main = new ActivityTestRule<>(HomeActivity.class);

    @Test
    public void addGoals() {
        init();
        login();
        //navigate to the view analytics page;
        onView(withId(R.id.bottom_navigation)).perform(ClickNav.clickGoals()).check(matches(isDisplayed()));
        onView(withId(R.id.fabAddGoal)).perform(click());
        onView(withId(R.id.btnContinue)).perform(click());
        onView(withId(R.id.lowerBound)).perform(typeText("1"));
        onView(withId(R.id.upperBound)).perform(typeText("1000"));
        closeSoftKeyboard();
        onView(withId(R.id.btnContinue)).perform(click());
        onView(withId(R.id.txtPassedGoals)).check(matches(withText(containsString("Calorie"))));
        onView(withId(R.id.fabAddGoal)).perform(click());
        onView(withId(R.id.btnContinue)).perform(click());
        onView(withId(R.id.lowerBound)).perform(typeText("2000"));
        onView(withId(R.id.upperBound)).perform(typeText("3000"));
        closeSoftKeyboard();
        onView(withId(R.id.btnContinue)).perform(click());
        onView(withId(R.id.addCalGoalErrorBox)).check(matches(hasErrorText("Day already contains a calorie goal")));
        Espresso.pressBack();
        onView(withId(R.id.txtGoals)).check(matches(withText(containsString("Calorie"))));
        cleanup();
    }

    @Test
    public void removeGoal() {
        init();
        login();
        //navigate to the view analytics page;
        onView(withId(R.id.bottom_navigation)).perform(ClickNav.clickGoals()).check(matches(isDisplayed()));
        onView(withId(R.id.fabAddGoal)).perform(click());
        onView(withId(R.id.btnContinue)).perform(click());
        onView(withId(R.id.lowerBound)).perform(typeText("1"));
        onView(withId(R.id.upperBound)).perform(typeText("1000"));
        closeSoftKeyboard();
        onView(withId(R.id.btnContinue)).perform(click());
        onView(withId(R.id.txtPassedGoals)).check(matches(withText(containsString("Calorie"))));
        onView(withId(R.id.btnMore)).perform(click());
        onView(withText("Remove Goal")).perform(click());
        onView(withId(R.id.btnContinue)).perform(click());
        onView(withText("Calorie")).check(doesNotExist());
    }

    @Test
    public void invalidInputs(){
        init();
        login();
        onView(withId(R.id.bottom_navigation)).perform(ClickNav.clickGoals()).check(matches(isDisplayed()));
        onView(withId(R.id.fabAddGoal)).perform(click());
        onView(withId(R.id.btnContinue)).perform(click());
        onView(withId(R.id.btnContinue)).perform(click());
        onView(withId(R.id.lowerBound)).check(matches(hasErrorText("Lower bound is required")));
        onView(withId(R.id.lowerBound)).perform(typeText("1"));
        onView(withId(R.id.btnContinue)).perform(click());
        onView(withId(R.id.upperBound)).check(matches(hasErrorText("Upper bound is required")));
        Espresso.pressBack();
        onView(withText("Calorie")).check(doesNotExist());
        cleanup();
    }

    private void login() {
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.etUsername)).perform(typeText(TESTER_INFO.username), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.etPassword)).perform(typeText(TESTER_INFO.password), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.btnLogin)).perform(click());
    }

}
