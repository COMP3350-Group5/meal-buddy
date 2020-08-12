package comp3350.mealbuddy.acceptance;

import org.junit.*;
import org.junit.runner.RunWith;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;;

import static androidx.test.espresso.Espresso.*;
import static androidx.test.espresso.action.ViewActions.*;
import static androidx.test.espresso.matcher.ViewMatchers.*;
import static androidx.test.espresso.assertion.ViewAssertions.*;

import comp3350.mealbuddy.business.AccessAccount;
import comp3350.mealbuddy.presentation.HomeActivity;
import comp3350.mealbuddy.R;
import comp3350.mealbuddy.presentation.SignUpActivity;

@RunWith(AndroidJUnit4ClassRunner.class)
@LargeTest
public class HomeTests {

    @Rule
    public ActivityTestRule<HomeActivity> main = new ActivityTestRule<>(HomeActivity.class);

    @Test
    public void cantLogin() {
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.etUsername)).perform(typeText("admin"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.etPassword)).perform(typeText("group6"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.btnLogin)).perform(click());
        onView(withText("Invalid Login.")).check(matches(isDisplayed()));
    }

    @Test
    public void canLogin() {
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.etUsername)).perform(typeText("admin"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.etPassword)).perform(typeText("group5"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.btnLogin)).perform(click());
    }

    @Test
    public void canCreateAccount() {
        AccessAccount access = new AccessAccount();

        Espresso.closeSoftKeyboard();
        onView(withId(R.id.btnCreateAccount)).perform(click());
        onView(withId(R.id.etFullName)).perform(typeText("testing tester"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.etUsername)).perform(typeText("tester"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.etPassword)).perform(typeText("tester123"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.etAge)).perform(typeText("100"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.etWeight)).perform(typeText("200"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.etHeight)).perform(typeText("200"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.spnSex)).perform(click());
        onView(withText("Male")).perform(click());
        onView(withId(R.id.spnActivityLevel)).perform(click());
        onView(withText("Low")).perform(click());
        onView(withId(R.id.btnCreateAccount)).perform(click());

        access.removeAccount("tester");
    }

    @Test public void emptyFields() {
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.btnCreateAccount)).perform(click());
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.btnCreateAccount)).perform(click());
        onView(withId(R.id.etUsername)).check(matches(hasErrorText("Username is required")));
        onView(withId(R.id.etPassword)).check(matches(hasErrorText("Password is required")));
        onView(withId(R.id.etFullName)).check(matches(hasErrorText("Full Name is required")));
        onView(withId(R.id.etAge)).check(matches(hasErrorText("Age is required")));
        onView(withId(R.id.etHeight)).check(matches(hasErrorText("Height is required")));
        onView(withId(R.id.etWeight)).check(matches(hasErrorText("Weight is required")));
    }

}