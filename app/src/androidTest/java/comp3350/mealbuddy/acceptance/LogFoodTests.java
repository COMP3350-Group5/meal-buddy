package comp3350.mealbuddy.acceptance;

import org.junit.*;
import org.junit.runner.RunWith;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

import static androidx.test.espresso.Espresso.*;
import static androidx.test.espresso.action.ViewActions.*;
import static androidx.test.espresso.matcher.ViewMatchers.*;
import static androidx.test.espresso.assertion.ViewAssertions.*;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

import comp3350.mealbuddy.business.AccessAccount;
import comp3350.mealbuddy.business.AccessEdible;
import comp3350.mealbuddy.business.Calculator;
import comp3350.mealbuddy.objects.Account;
import comp3350.mealbuddy.objects.Day;
import comp3350.mealbuddy.objects.UserInfo;
import comp3350.mealbuddy.objects.consumables.Edible;
import comp3350.mealbuddy.objects.consumables.Food;
import comp3350.mealbuddy.presentation.HomeActivity;
import comp3350.mealbuddy.R;

@RunWith(AndroidJUnit4ClassRunner.class)
@LargeTest
public class LogFoodTests {

    private Account TEST_ACCOUNT;
    private AccessEdible ACCESS_EDIBLE;
    private AccessAccount ACCESS_ACCOUNT;
    private Food DUMMY_FOOD;
    private UserInfo TESTER_INFO;
    private Day DAY;
    private Calculator CALCULATOR;
    private String NEW_FOOD_NAME = "Lobster Tails";

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
        try {
            ACCESS_EDIBLE.removeEdible(NEW_FOOD_NAME);
        } catch (Exception e){}
    }

    @Rule
    public ActivityTestRule<HomeActivity> main = new ActivityTestRule<>(HomeActivity.class);

    @Test
    public void testAddFoodToMealTime() {
        init();
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.etUsername)).perform(typeText(TESTER_INFO.username), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.etPassword)).perform(typeText(TESTER_INFO.password), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.btnLogin)).perform(click());


        onView(withId(R.id.fabAdd)).perform(click());
        onView(withId(R.id.fabFood)).perform(click()).check(matches(isDisplayed()));

        onData(allOf(is(instanceOf(String.class)), is("Honey"))).perform(click());
        onView(withId(R.id.etQuantity)).perform(typeText("3"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.btnConfirm)).perform(click());

        onView(withId(R.id.bottom_navigation)).perform(ClickNav.clickTimeline());

        // Check to make sure the food is added to breakfast and the calories are correct
        try {
            onData(allOf(is(instanceOf(String.class)), is("Honey")));
        } catch (Exception e){
            Assert.fail();
        }
        cleanup();
    }

    @Test
    public void testTotalCaloriesFoodAdded() {
        init();
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.etUsername)).perform(typeText(TESTER_INFO.username), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.etPassword)).perform(typeText(TESTER_INFO.password), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.btnLogin)).perform(click());

        onView(withId(R.id.fabAdd)).perform(click());
        onView(withId(R.id.fabFood)).perform(click()).check(matches(isDisplayed()));

        onData(allOf(is(instanceOf(String.class)), is("Milk"))).perform(click());
        onView(withId(R.id.etQuantity)).perform(typeText("3"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.btnConfirm)).perform(click());
        onView(withId(R.id.bottom_navigation)).perform(ClickNav.clickTimeline());
        CALCULATOR = new Calculator(ACCESS_ACCOUNT.getDay(TESTER_INFO.username,Calendar.getInstance().get(Calendar.DAY_OF_YEAR)));

        // Check to make sure the total Calories for the day is updated
        onView(withId(R.id.txtBreakfastCals)).check(matches(withText(CALCULATOR.getMealTimeCalories(Day.MealTimeType.BREAKFAST) + " cals")));
        cleanup();
    }

    @Test
    public void logFoodtoDb() {
        init();
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.etUsername)).perform(typeText(TESTER_INFO.username), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.etPassword)).perform(typeText(TESTER_INFO.password), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.btnLogin)).perform(click());

        onView(withId(R.id.fabAdd)).perform(click());
        onView(withId(R.id.fabFood)).perform(click()).check(matches(isDisplayed()));

        onView(withId(R.id.fabAdd)).perform(click());
        onView(withId(R.id.fabFood)).perform(click());

        Espresso.closeSoftKeyboard();
        onView(withId(R.id.etFoodName)).perform(typeText(NEW_FOOD_NAME), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.etLabels)).perform(typeText("Gluten friendly"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.etFat)).perform(typeText("2"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.etCarbs)).perform(typeText("3"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.etProtein)).perform(typeText("22"), ViewActions.closeSoftKeyboard());

        onView(withId(R.id.etWeight)).perform(typeText("85"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.etVitA)).perform(typeText("5"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.etVitC)).perform(typeText("25"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.etCalcium)).perform(typeText("15"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.etIron)).perform(typeText("7"), ViewActions.closeSoftKeyboard());

        onView(withId(R.id.btnAddFood)).perform(click());
        Espresso.closeSoftKeyboard();

        onData(allOf(is(instanceOf(String.class)), is("Lobster Tails"))).perform(click());
        onView(withId(R.id.etQuantity)).perform(typeText("3"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.btnConfirm)).perform(click());

        CALCULATOR = new Calculator(ACCESS_ACCOUNT.getDay(TESTER_INFO.username,Calendar.getInstance().get(Calendar.DAY_OF_YEAR)));
        // Check to make sure the total Calories for the day is updated
        onView(withId(R.id.txtBreakfastCals)).check(matches(withText(CALCULATOR.getMealTimeCalories(Day.MealTimeType.BREAKFAST) + " cals")));

        //remove the food
        onView(withId(R.id.fabAdd)).perform(click());
        onView(withId(R.id.fabFood)).perform(click()).check(matches(isDisplayed()));
        onData(allOf(is(instanceOf(String.class)), is("Lobster Tails"))).perform(click());
        onView(withId(R.id.removeEdibleFromDb)).perform(click());
        try{
            onData(allOf(is(instanceOf(String.class)), is("Lobster Tails"))).perform(click());
            Assert.fail();
        } catch (Exception e){
            System.err.println(e);
        }
        cleanup();
    }

    @Test
    public void testBadInputs() {
        init();
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.etUsername)).perform(typeText(TESTER_INFO.username), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.etPassword)).perform(typeText(TESTER_INFO.password), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.btnLogin)).perform(click());

        onView(withId(R.id.fabAdd)).perform(click());
        onView(withId(R.id.fabFood)).perform(click()).check(matches(isDisplayed()));

        onView(withId(R.id.fabAdd)).perform(click());
        onView(withId(R.id.fabFood)).perform(click());

        Espresso.closeSoftKeyboard();

        onView(withId(R.id.btnAddFood)).perform(click());
        Espresso.closeSoftKeyboard();

        onView(withId(R.id.etFoodName)).check(matches(hasErrorText("Name is required")));
        onView(withId(R.id.etFat)).check(matches(hasErrorText("Fat is required")));
        onView(withId(R.id.etCarbs)).check(matches(hasErrorText("Carbs are required")));
        onView(withId(R.id.etProtein)).check(matches(hasErrorText("Protein is required")));
        onView(withId(R.id.etWeight)).check(matches(hasErrorText("Weight is required")));

        Espresso.closeSoftKeyboard();
        onView(withId(R.id.etFoodName)).perform(typeText("Almonds"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.etProtein)).perform(typeText("1"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.etFat)).perform(typeText("2"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.etCarbs)).perform(typeText("4"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.etWeight)).perform(typeText("4"), ViewActions.closeSoftKeyboard());

        onView(withId(R.id.btnAddFood)).perform(click());

        onView(withId(R.id.etFoodName)).check(matches(hasErrorText("Edible already exists choose a different name")));

        cleanup();
    }

}
