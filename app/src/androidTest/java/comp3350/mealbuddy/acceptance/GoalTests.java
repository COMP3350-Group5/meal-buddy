package comp3350.mealbuddy.acceptance;


import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.contrib.PickerActions;
import androidx.test.filters.LargeTest;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import comp3350.mealbuddy.R;
import comp3350.mealbuddy.business.AccessAccount;
import comp3350.mealbuddy.objects.UserInfo;
import comp3350.mealbuddy.presentation.HomeActivity;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.*;
import static org.hamcrest.Matchers.allOf;

@RunWith(AndroidJUnit4ClassRunner.class)
@LargeTest
public class GoalTests {

    AccessAccount accessAccount;
    @Rule
    public ActivityTestRule<HomeActivity> homeTest = new ActivityTestRule<>(HomeActivity.class);

    @Before
    public void init() {
        accessAccount = new AccessAccount();
        accessAccount.addAccount(new UserInfo("Goal Test", "goaltest", "goaltest", 100.0, 100.0, UserInfo.ActivityLevel.LOW, UserInfo.Sex.FEMALE, 2));
    }

    @Test
    public void setRecommendedGoals() {
        onView(withId(R.id.etUsername)).perform(typeText("goaltest"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.etPassword)).perform(typeText("goaltest"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.btnLogin)).perform(click());
        onView(withId(R.id.bottom_navigation)).perform(ClickNav.clickGoals());
        onView(withId(R.id.btnMore)).perform(click());
        onView(withText("Set Recommended Goals")).perform(click());
        onView(withText("CalorieGoal{lowerBound=2816, upperBound=3216}\n" +
                "MacroGoal{id=Carbohydrates, lowerBound=45g, upperBound=55g}\n" +
                "MacroGoal{id=Fat, lowerBound=20g, upperBound=30g}\n" +
                "MacroGoal{id=Protein, lowerBound=20g, upperBound=30g}\n" +
                "MicroGoal{id=Calcium, lowerBound=1000mg, upperBound=2000mg}\n" +
                "MicroGoal{id=Niacin, lowerBound=500mg, upperBound=3500mg}\n" +
                "MicroGoal{id=Iron, lowerBound=8mg, upperBound=45mg}\n" +
                "MicroGoal{id=Magnesium, lowerBound=310mg, upperBound=3000mg}\n" +
                "MicroGoal{id=Potassium, lowerBound=3500mg, upperBound=4700mg}\n" +
                "MicroGoal{id=Sodium, lowerBound=1200mg, upperBound=2300mg}\n" +
                "MicroGoal{id=VitaminA, lowerBound=1mg, upperBound=4mg}\n" +
                "MicroGoal{id=VitaminB12, lowerBound=10mg, upperBound=50mg}\n" +
                "MicroGoal{id=VitaminC, lowerBound=10mg, upperBound=50mg}\n" +
                "MicroGoal{id=VitaminE, lowerBound=15mg, upperBound=100mg}\n" +
                "MicroGoal{id=Zinc, lowerBound=10mg, upperBound=40mg}\n")).check(matches(isDisplayed()));
    }


    @After
    public void cleanup() {
        accessAccount.removeAccount("goaltest");
    }

}
