package comp3350.mealbuddy.acceptance;
import android.content.Context;
import android.content.Intent;

import org.hamcrest.Matchers;
import org.junit.*;
import org.junit.runner.RunWith;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

import java.util.Calendar;

import static androidx.test.espresso.Espresso.*;
import static androidx.test.espresso.action.ViewActions.*;
import static androidx.test.espresso.matcher.ViewMatchers.*;
import static androidx.test.espresso.assertion.ViewAssertions.*;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.AllOf.allOf;
import comp3350.mealbuddy.R;
import comp3350.mealbuddy.business.AccessAccount;
import comp3350.mealbuddy.business.Calculator;
import comp3350.mealbuddy.objects.Account;
import comp3350.mealbuddy.objects.Day;
import comp3350.mealbuddy.objects.UserInfo;
import comp3350.mealbuddy.presentation.HomeActivity;
@RunWith(AndroidJUnit4ClassRunner.class)
@LargeTest
public class ExerciseTest {
    private AccessAccount ACCESS_ACCOUNT;
    private UserInfo TESTER_INFO;
    private Day DAY;

    @Before
    public void init(){
        cleanup();
        System.err.println("INIT");
        ACCESS_ACCOUNT = new AccessAccount();
        TESTER_INFO = new UserInfo("testFirst testLast", "tester", "test123", 10.0, 10.0, UserInfo.ActivityLevel.LOW, UserInfo.Sex.MALE, 10);
        ACCESS_ACCOUNT.addAccount(TESTER_INFO);
        DAY = ACCESS_ACCOUNT.getDay(TESTER_INFO.username, Calendar.getInstance().get(Calendar.DAY_OF_YEAR));
    }

    @After
    public void cleanup(){
        System.err.println("CLEANUP");
        try {
            ACCESS_ACCOUNT.removeAccount(TESTER_INFO.username);
        } catch (Exception e){}
    }
    @Rule
    public ActivityTestRule<HomeActivity> main = new ActivityTestRule<HomeActivity>(HomeActivity.class);
    @Test
    public void testAddSingleExercise() {
        login();
        onView(withId(R.id.fabAdd)).perform(click());
        onView(withId(R.id.fabExercise)).check(matches(isDisplayed()));
        onView(withId(R.id.fabExercise)).perform(click());
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.etExerciseName)).perform(typeText("Bike"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.spnIntensity)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("Low"))).perform(click());
        onView(withId(R.id.spnIntensity)).check(matches(withSpinnerText(containsString("Low"))));
        onView(withId(R.id.etDuration)).perform(typeText("25"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.btnAddExercise)).perform(click());
        onView(withId(R.id.cardExercise)).perform(click());
        onData(allOf())
                .inAdapterView(withId(R.id.lvViewExercise))
                .atPosition(0)
                .check(matches(withText(containsString("Bike"))));
        Espresso.pressBack();
        onView(withId(R.id.txtTotalsCalsBurned)).check(matches(withText(containsString("5"))));
        onView(withId(R.id.txtExercisesCals)).check(matches(withText(containsString("5 cals"))));
        cleanup();
    }

    @Test
    public void testAddMultipleExercises() {
        login();
        onView(withId(R.id.fabAdd)).perform(click());
        onView(withId(R.id.fabExercise)).check(matches(isDisplayed()));
        onView(withId(R.id.fabExercise)).perform(click());
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.etExerciseName)).perform(typeText("Bike"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.spnIntensity)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("Low"))).perform(click());
        onView(withId(R.id.spnIntensity)).check(matches(withSpinnerText(containsString("Low"))));
        onView(withId(R.id.etDuration)).perform(typeText("25"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.btnAddExercise)).perform(click());
        onView(withId(R.id.txtTotalsCalsBurned)).check(matches(withText(containsString("5"))));
        onView(withId(R.id.txtExercisesCals)).check(matches(withText(containsString("5 cals"))));
        onView(withId(R.id.cardExercise)).perform(click());
        onData(allOf())
                .inAdapterView(withId(R.id.lvViewExercise))
                .atPosition(0)
                .check(matches(withText(containsString("Bike"))));
        Espresso.pressBack();
        onView(withId(R.id.fabAdd)).perform(click());
        onView(withId(R.id.fabExercise)).check(matches(isDisplayed()));
        onView(withId(R.id.fabExercise)).perform(click());
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.etExerciseName)).perform(typeText("Run"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.spnIntensity)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("Medium"))).perform(click());
        onView(withId(R.id.spnIntensity)).check(matches(withSpinnerText(containsString("Medium"))));
        onView(withId(R.id.etDuration)).perform(typeText("30"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.btnAddExercise)).perform(click());
        onView(withId(R.id.cardExercise)).perform(click());
        onData(allOf())
                .inAdapterView(withId(R.id.lvViewExercise))
                .atPosition(0)
                .check(matches(withText(containsString("Bike"))));
        onData(allOf())
                .inAdapterView(withId(R.id.lvViewExercise))
                .atPosition(1)
                .check(matches(withText(containsString("Run"))));
        Espresso.pressBack();
        onView(withId(R.id.txtTotalsCalsBurned)).check(matches(withText(containsString("16"))));
        onView(withId(R.id.txtExercisesCals)).check(matches(withText(containsString("16 cals"))));
        cleanup();
    }

    @Test
    public void testRemoveExercises() {
        login();
        onView(withId(R.id.fabAdd)).perform(click());
        onView(withId(R.id.fabExercise)).check(matches(isDisplayed()));
        onView(withId(R.id.fabExercise)).perform(click());
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.etExerciseName)).perform(typeText("Bike"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.spnIntensity)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("Low"))).perform(click());
        onView(withId(R.id.spnIntensity)).check(matches(withSpinnerText(containsString("Low"))));
        onView(withId(R.id.etDuration)).perform(typeText("25"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.btnAddExercise)).perform(click());
        onView(withId(R.id.fabAdd)).perform(click());
        onView(withId(R.id.fabExercise)).check(matches(isDisplayed()));
        onView(withId(R.id.fabExercise)).perform(click());
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.etExerciseName)).perform(typeText("Run"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.spnIntensity)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("Medium"))).perform(click());
        onView(withId(R.id.spnIntensity)).check(matches(withSpinnerText(containsString("Medium"))));
        onView(withId(R.id.etDuration)).perform(typeText("30"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.btnAddExercise)).perform(click());

        onView(withId(R.id.cardExercise)).perform(click());
        onView(withId(R.id.btnRemoveExercise)).perform(click());
        onView(withId(R.id.btnContinue)).perform(click());

        onView(withId(R.id.txtTotalsCalsBurned)).check(matches(withText(containsString("11"))));
        onView(withId(R.id.txtExercisesCals)).check(matches(withText(containsString("11 cals"))));

        onView(withId(R.id.cardExercise)).perform(click());
        onData(allOf())
                .inAdapterView(withId(R.id.lvViewExercise))
                .atPosition(0)
                .check(matches(withText(containsString("Run"))));
        onView(withId(R.id.btnRemoveExercise)).perform(click());

        onView(withId(R.id.btnContinue)).perform(click());

        onView(withId(R.id.txtTotalsCalsBurned)).check(matches(withText(containsString("0"))));
        onView(withId(R.id.txtExercisesCals)).check(matches(withText(containsString("0 cals"))));
        cleanup();
    }

    @Test
    public void testBadInputs() {
        init();
        login();
        onView(withId(R.id.fabAdd)).perform(click());
        onView(withId(R.id.fabExercise)).check(matches(isDisplayed()));
        onView(withId(R.id.fabExercise)).perform(click());
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.btnAddExercise)).perform(click());
        onView(withId(R.id.etExerciseName)).check(matches(hasErrorText("Name is required")));
        onView(withId(R.id.etDuration)).check(matches(hasErrorText("Duration is required")));
        onView(withId(R.id.etExerciseName)).perform(typeText("Bike"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.etDuration)).check(matches(hasErrorText("Duration is required")));
        onView(withId(R.id.btnAddExercise)).perform(click());
        onView(withId(R.id.etExerciseName)).perform(clearText(), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.etDuration)).perform(typeText("25"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.btnAddExercise)).perform(click());
        onView(withId(R.id.etExerciseName)).check(matches(hasErrorText("Name is required")));
        Espresso.pressBack();
        onView(withId(R.id.txtTotalsCalsBurned)).check(matches(withText(containsString("0"))));
        onView(withId(R.id.txtExercisesCals)).check(matches(withText(containsString("0 cals"))));
        cleanup();
    }

    private void login() {
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.etUsername)).perform(typeText("tester"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.etPassword)).perform(typeText("test123"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.btnLogin)).perform(click());
    }

}
