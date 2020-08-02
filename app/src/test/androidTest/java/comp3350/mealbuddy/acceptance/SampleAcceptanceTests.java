package comp3350.mealbuddy.acceptance;

import org.junit.*;
import org.junit.runner.RunWith;

//import androidx.test.espresso.Espresso;
import androidx.test.runner.AndroidJUnit4;
import androidx.test.filters.LargeTest;
//import androidx.test.rule.ActivityTestRule;
//
//import static androidx.test.espresso.Espresso.*;
//import static androidx.test.espresso.action.ViewActions.*;
//import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
//import static androidx.test.espresso.matcher.ViewMatchers.*;
//import static androidx.test.espresso.assertion.ViewAssertions.*;
//
//import static org.hamcrest.Matchers.not;
//
//import comp3350.srsys.R;
//import comp3350.srsys.presentation.HomeActivity;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class SampleAcceptanceTests {
//
//    @Rule
//    public ActivityTestRule<HomeActivity> homeActivity = new ActivityTestRule<>(HomeActivity.class);
//
    @Test
    public void fakeTest() {
    }

//    @Test
//    public void testHomeScreen() {
//        onView(withText("Student Records System")).check(matches(isDisplayed()));
//        onView(withId(R.id.buttonRegistrations)).check(matches(isDisplayed())).check(matches(not(isEnabled())));
//        // alternatively:
//        onView(withText("REGISTRATIONS")).check(matches(isDisplayed())).check(matches(not(isEnabled())));
//    }
//
//    @Test
//    public void testEditStudent() {
//        onView(withText("STUDENTS")).perform(click());
//        onView(withText("Student ID")).check(matches(isDisplayed()));
//
//        onView(withText("400: Mary Bailey")).check(matches(isDisplayed())).perform(click());
//        onView(withId(R.id.editStudentID)).check(matches(withText("400")));
//        onView(withId(R.id.editStudentName)).check(matches(withText("Mary Bailey")));
//        onView(withId(R.id.editStudentAddress)).check(matches(withText("Off Campus")));
//
//        onView(withId(R.id.editStudentName)).perform(clearText(), typeText("Mary Bucket"));
//        onView(withId(R.id.editStudentAddress)).perform(clearText(), typeText("Somewhere Else"));
//        Espresso.closeSoftKeyboard();
//
//        onView(withText("UPDATE")).perform(click());
//
//        onView(withText("400: Mary Bucket")).check(matches(isDisplayed()));
//
//        Espresso.pressBack();
//        onView(withText("Student Records System")).check(matches(isDisplayed()));
//
//        onView(withId(R.id.buttonStudents)).perform(click());
//        onView(withText("400: Mary Bucket")).check(matches(isDisplayed())).perform(click());
//
//        onView(withId(R.id.editStudentID)).check(matches(withText("400")));
//        onView(withId(R.id.editStudentName)).check(matches(withText("Mary Bucket")));
//        onView(withId(R.id.editStudentAddress)).check(matches(withText("Somewhere Else")));
//
//        onView(withText("DELETE")).perform(click());
//
//        onView(withText("400: Mary Bucket")).check(doesNotExist());
//
//        // clean up
//        onView(withId(R.id.editStudentID)).perform(clearText(), typeText("400"));
//        onView(withId(R.id.editStudentName)).perform(clearText(), typeText("Mary Bailey"));
//        onView(withId(R.id.editStudentAddress)).perform(clearText(), typeText("Off Campus"));
//        Espresso.closeSoftKeyboard();
//
//        onView(withText("CREATE")).perform(click());
//
//        onView(withText("400: Mary Bailey")).check(matches(isDisplayed()));
//    }
//
//    @Test
//    public void testInvalidStudent() {
//        onView(withText("STUDENTS")).perform(click());
//
//        onView(withText("CREATE")).perform(click());
//        onView(withText("Student ID required")).inRoot(withDecorView(not(homeActivity.getActivity().getWindow().getDecorView()))).check(matches(isDisplayed()));
//
//        Espresso.pressBack();
//
//        onView(withText("100: Gary Chalmers")).check(matches(isDisplayed())).perform(click());
//        onView(withId(R.id.editStudentName)).perform(clearText());
//        Espresso.closeSoftKeyboard();
//
//        onView(withText("UPDATE")).perform(click());
//        onView(withText("Student name required")).inRoot(withDecorView(not(homeActivity.getActivity().getWindow().getDecorView()))).check(matches(isDisplayed()));
//
//        Espresso.pressBack();
//    }
}
