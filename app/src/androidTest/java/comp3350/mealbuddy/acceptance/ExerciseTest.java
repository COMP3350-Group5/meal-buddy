package comp3350.mealbuddy.acceptance;
import android.content.Context;
import android.content.Intent;
import org.junit.*;
import org.junit.runner.RunWith;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import static androidx.test.espresso.Espresso.*;
import static androidx.test.espresso.action.ViewActions.*;
import static androidx.test.espresso.matcher.ViewMatchers.*;
import static androidx.test.espresso.assertion.ViewAssertions.*;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.AllOf.allOf;
import comp3350.mealbuddy.R;
import comp3350.mealbuddy.presentation.HomeActivity;
@RunWith(AndroidJUnit4ClassRunner.class)
@LargeTest
public class ExerciseTest {
    @Rule
    public ActivityTestRule<HomeActivity> main = new ActivityTestRule<HomeActivity>(HomeActivity.class);
    @Test
    public void testAddSingleExercise() {
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.etUsername)).perform(typeText("admin"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.etPassword)).perform(typeText("group5"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.btnLogin)).perform(click());
        onView(withId(R.id.fabAdd)).perform(click());
        onView(withId(R.id.fabExercise)).check(matches(isDisplayed()));
        onView(withId(R.id.fabExercise)).perform(click());
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.etExerciseName)).perform(typeText("Squats"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.spnIntensity)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("Low"))).perform(click());
        onView(withId(R.id.spnIntensity)).check(matches(withSpinnerText(containsString("Low"))));
        onView(withId(R.id.etDuration)).perform(typeText("10"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.btnAddExercise)).perform(click());
    }
    @Test
    public void testAddMultipleExercises() {
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.etUsername)).perform(typeText("admin"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.etPassword)).perform(typeText("group5"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.btnLogin)).perform(click());
        onView(withId(R.id.fabAdd)).perform(click());
        onView(withId(R.id.fabExercise)).check(matches(isDisplayed()));
        onView(withId(R.id.fabExercise)).perform(click());
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.etExerciseName)).perform(typeText("Squats"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.spnIntensity)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("Low"))).perform(click());
        onView(withId(R.id.spnIntensity)).check(matches(withSpinnerText(containsString("Low"))));
        onView(withId(R.id.etDuration)).perform(typeText("25"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.btnAddExercise)).perform(click());
        onView(withId(R.id.fabAdd)).perform(click());
        onView(withId(R.id.fabExercise)).check(matches(isDisplayed()));
        onView(withId(R.id.fabExercise)).perform(click());
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.etExerciseName)).perform(typeText("Lunges"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.spnIntensity)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("Medium"))).perform(click());
        onView(withId(R.id.spnIntensity)).check(matches(withSpinnerText(containsString("Medium"))));
        onView(withId(R.id.etDuration)).perform(typeText("30"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.btnAddExercise)).perform(click());
    }
}
