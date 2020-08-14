package comp3350.mealbuddy.acceptance;

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

import comp3350.mealbuddy.R;
import comp3350.mealbuddy.business.AccessEdible;
import comp3350.mealbuddy.presentation.HomeActivity;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4ClassRunner.class)
@LargeTest
public class MealTests {
    @Rule
    public ActivityTestRule<HomeActivity> main = new ActivityTestRule<>(HomeActivity.class);

    AccessEdible accessEdible;

    @Before
    public void init() {
        accessEdible = new AccessEdible();
    }

    @Test
    public void testAddAndRemoveMeal() {
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.etUsername)).perform(typeText("admin"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.etPassword)).perform(typeText("group5"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.btnLogin)).perform(click());
        onView(withId(R.id.fabAdd)).perform(click());
        onView(withId(R.id.fabFood)).perform(click());
        onView(withId(R.id.fabAdd)).perform(click());
        onView(withId(R.id.fabMeal)).perform(click());
        onView(withId(R.id.mealName)).perform(typeText("Chicken breast and beans"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.mealLabels)).perform(typeText("Gluten free"), ViewActions.closeSoftKeyboard());
        onView(withText("Chicken breast")).perform(click());
        onView(withId(R.id.edibleQuantity)).perform(typeText("2"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.confirmBtn)).perform(click());
        onView(withText("Beans")).perform(click());
        onView(withId(R.id.edibleQuantity)).perform(typeText("3"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.confirmBtn)).perform(click());
        onView(withId(R.id.btnAdd)).perform(click());
        onView(withText("Chicken breast and beans")).check(matches(isDisplayed()));
        onView(withText("Chicken breast and beans")).perform(click());
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.removeEdibleFromDb)).perform(click());
        onView(withText("Chicken breast and beans")).check(doesNotExist());
    }


    @Test
    public void testAddMultipleMeals() {
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.etUsername)).perform(typeText("admin"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.etPassword)).perform(typeText("group5"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.btnLogin)).perform(click());
        onView(withId(R.id.fabAdd)).perform(click());
        onView(withId(R.id.fabFood)).check(matches(isDisplayed()));
        onView(withId(R.id.fabFood)).perform(click());
        onView(withId(R.id.fabAdd)).perform(click());
        onView(withId(R.id.fabMeal)).perform(click());
        onView(withId(R.id.mealName)).perform(typeText("Broccoli and cucumber"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.mealLabels)).perform(typeText("Gluten free"), ViewActions.closeSoftKeyboard());
        onView(withText("Broccoli")).perform(click());
        onView(withId(R.id.edibleQuantity)).perform(typeText("2"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.confirmBtn)).perform(click());
        onView(withText("Cucumber")).perform(click());
        onView(withId(R.id.edibleQuantity)).perform(typeText("3"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.confirmBtn)).perform(click());
        onView(withId(R.id.btnAdd)).perform(click());
        onView(withId(R.id.fabAdd)).perform(click());
        onView(withId(R.id.fabMeal)).perform(click());
        onView(withId(R.id.mealName)).perform(typeText("Bacon and cheese"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.mealLabels)).perform(typeText("Gluten free"), ViewActions.closeSoftKeyboard());
        onView(withText("Bacon")).perform(click());
        onView(withId(R.id.edibleQuantity)).perform(typeText("2"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.confirmBtn)).perform(click());
        onView(withText("Cheese")).perform(click());
        onView(withId(R.id.edibleQuantity)).perform(typeText("3"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.confirmBtn)).perform(click());
        onView(withId(R.id.btnAdd)).perform(click());
    }

    @After
    public void cleanup() {
        accessEdible.removeEdible("Broccoli and cucumber");
        accessEdible.removeEdible("Bacon and cheese");
        accessEdible.removeEdible("Chicken breast and beans");
    }
}
