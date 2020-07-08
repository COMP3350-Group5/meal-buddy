package comp3350.mealbuddy.tests.objects;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import comp3350.mealbuddy.objects.Day;
import comp3350.mealbuddy.objects.consumables.FoodIntPair;
import comp3350.mealbuddy.objects.consumables.Food;
import comp3350.mealbuddy.objects.consumables.Meal;

public class DayTest {
    @Test
    public void constructor_outOfBounds_throwException() {
        //pass a day that doesn't exist i.e., not in 1-365
        //arrange
        try {
            //act
            Day day = new Day(-1);
            Assert.fail();
        } catch(IllegalArgumentException iae) {
            //assert
            Assert.assertTrue(true);
        }
    }

    @Test
    public void addFood_nullFood_dontAdd() {
        //arrange
        Food eddie = null;
        Day.MealTimeType MTT = Day.MealTimeType.BREAKFAST;
        Day day = new Day(1);
        //act
        day.addFood(MTT, eddie);
        //assert
        Assert.assertFalse(day.breakfast.contains(eddie));
    }

    @Test
    public void addFood_nullMealTime_dontAdd() {
        //arrange
        Food eddie = new Food("fake food", new ArrayList<>(Arrays.asList("fake")));
        Day.MealTimeType MTT = null;
        Day day = new Day(1);
        //act
        day.addFood(MTT, eddie);
        //assert
        Assert.assertFalse(day.breakfast.contains(eddie));
    }

    @Test
    public void addFood_realFood_add() {
        //arrange
        Food eddie = new Food("fake food", new ArrayList<>(Arrays.asList("fake")));
        Day.MealTimeType MTT = Day.MealTimeType.BREAKFAST;
        Day day = new Day(1);
        //act
        day.addFood(MTT, eddie);
        //assert
        Assert.assertTrue(day.breakfast.contains(eddie));
        Assert.assertFalse(day.lunch.contains(eddie));
        Assert.assertFalse(day.dinner.contains(eddie));
        Assert.assertFalse(day.snack.contains(eddie));
    }

    @Test
    public void addFood_realMeal_add() {
        //arrange
        Food eddie = new Food("fake food", new ArrayList<>(Arrays.asList("fake")));
        Day.MealTimeType MTT = Day.MealTimeType.DINNER;
        Day day = new Day(1);
        //act
        day.addFood(MTT, eddie);
        //assert
        Assert.assertTrue(day.dinner.contains(eddie));
    }

    @Test
    public void removeFood() {

    }

    @Test
    public void getMeal_nullMealTimeType_dontGet() {
        Food eddie = new Food("fake food", new ArrayList<>(Arrays.asList("fake")));
        Day.MealTimeType MTT = null;
        Day day = new Day(1);

        day.addFood(MTT, eddie);
        String meal = day.getMeal(MTT);

       Assert.assertTrue(meal.equals(""));
    }


    public void getMeal_realMealTimeType_get() {
        Food eddie = new Food("fake food", new ArrayList<>(Arrays.asList("fake")));
        Day.MealTimeType MTT = Day.MealTimeType.DINNER;
        Day day = new Day(1);

        day.addFood(MTT, eddie);
        String meal = day.getMeal(MTT);

        Assert.assertFalse(meal.equals(""));
    }

    public void getMeal_noFoodAdded_get() {
        Food eddie = new Food("fake food", new ArrayList<>(Arrays.asList("fake")));
        Day.MealTimeType MTT = Day.MealTimeType.DINNER;
        Day day = new Day(1);

        String meal = day.getMeal(MTT);

        Assert.assertFalse(meal.equals(""));
    }


    @Test
    public void getMealTimeList() {
    }

}
