package comp3350.mealbuddy.acceptance;

import androidx.test.filters.LargeTest;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.runner.RunWith;

import comp3350.mealbuddy.presentation.HomeActivity;
import comp3350.mealbuddy.presentation.SearchFoodActivity;

@RunWith(AndroidJUnit4ClassRunner.class)
@LargeTest
public class EdibleTests {

    @Rule
    public ActivityTestRule<SearchFoodActivity> homeTest = new ActivityTestRule<>(SearchFoodActivity.class);


}
