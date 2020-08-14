package comp3350.mealbuddy.acceptance;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import comp3350.mealbuddy.R;
import comp3350.mealbuddy.business.AccessAccount;
import comp3350.mealbuddy.business.AccessEdible;
import comp3350.mealbuddy.business.AccessLabel;
import comp3350.mealbuddy.objects.UserInfo;
import comp3350.mealbuddy.presentation.HomeActivity;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

public class LabelTests {

    AccessAccount accessAccount;
    AccessEdible accessEdible;
    AccessLabel accessLabel;
    @Rule
    public ActivityTestRule<HomeActivity> homeTest = new ActivityTestRule<>(HomeActivity.class);

    @Before
    public void init() {
        accessEdible = new AccessEdible();
        accessLabel = new AccessLabel();
        accessAccount = new AccessAccount();
        accessAccount.addAccount(new UserInfo("Goal Test", "goaltest", "goaltest", 100.0, 100.0, UserInfo.ActivityLevel.LOW, UserInfo.Sex.FEMALE, 2));
    }

    //Test adding, updating, and removing a label, as well as creating a food with a label
    @Test
    public void labelTest() {
        onView(withId(R.id.etUsername)).perform(typeText("goaltest"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.etPassword)).perform(typeText("goaltest"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.btnLogin)).perform(click());
        onView(withId(R.id.btnMore)).perform(click());
        onView(withText("Labels")).perform(click());
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.labelAddLabelText)).perform(typeText("Vegan"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.labelBtnAdd)).perform(click());
        onView(withText("Vegan")).check(matches(isDisplayed()));
        onView(withText("Vegan")).perform(click());
        onView(withId(R.id.labelUpdateLabelText)).perform(typeText("Vegetarian"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.labelBtnUpdate)).perform(click());
        onView(withText("Vegan")).check(doesNotExist());
        onView(withText("Vegetarian")).check(matches(isDisplayed()));
        onView(withId(R.id.labelAddLabelText)).perform(typeText("Vegan"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.labelBtnAdd)).perform(click());
        onView(withText("Vegan")).perform(click());
        onView(withId(R.id.labelBtnRemove)).perform(click());
        onView(withText("Vegan")).check(doesNotExist());
        Espresso.pressBack();
        onView(withId(R.id.fabAdd)).perform(click());
        onView(withId(R.id.fabFood)).perform(click());
        onView(withId(R.id.fabAdd)).perform(click());
        onView(withId(R.id.fabFood)).perform(click());
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.etFoodName)).perform(typeText("A single blade of grass"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.etLabels)).perform(typeText("Vegetarian"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.etFat)).perform(typeText("2"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.etCarbs)).perform(typeText("3"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.etProtein)).perform(typeText("22"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.etWeight)).perform(typeText("85"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.etVitA)).perform(typeText("5"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.etVitC)).perform(typeText("25"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.etCalcium)).perform(typeText("15"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.etIron)).perform(typeText("7"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.btnAddFood)).perform(click());
        onView(withText("A single blade of grass")).perform(click());
        onView(withId(R.id.etQuantity)).perform(typeText("1"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.btnConfirm)).perform(click());
        onView(withText("Breakfast")).perform(click());
        onView(withText("<Vegetarian> ")).check(matches(isDisplayed()));
    }



    @After
    public void cleanup() {
        accessAccount.removeAccount("goaltest");
        accessLabel.removeLabel("Vegan");
        accessLabel.removeLabel("Vegetarian");
        accessEdible.removeEdible("A single blade of grass");
    }

}


