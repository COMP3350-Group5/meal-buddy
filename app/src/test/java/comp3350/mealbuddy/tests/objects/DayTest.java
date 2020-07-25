package comp3350.mealbuddy.tests.objects;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import comp3350.mealbuddy.objects.Day;
import comp3350.mealbuddy.objects.consumables.Food;

public class DayTest {


    @Test
    public void constructor_outOfBounds_throwException() {
        //pass a day that doesn't exist i.e., not in 1-365
        //arrange
        try {
            //act
            Day day = new Day(-1);
            Assert.fail();
        } catch (IllegalArgumentException iae) {
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
        Assert.assertFalse(day.breakfast.containsEdible(eddie));
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
        Assert.assertFalse(day.breakfast.containsEdible(eddie));
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
        Assert.assertTrue(day.breakfast.containsEdible(eddie));
    }


    @Test
    public void removeFood() {
        //arrange
        Food eddie = new Food("fake food", new ArrayList<>(Arrays.asList("fake")));
        Day.MealTimeType MTT = Day.MealTimeType.BREAKFAST;
        Day day = new Day(1);
        day.addFood(MTT, eddie);

        //arrange
        day.removeFood(MTT, eddie);

        //act
        Assert.assertFalse(day.breakfast.containsEdible(eddie));

    }

    @Test
    public void getMeal() {
        //arrange
        Food eddie = new Food("fake food", new ArrayList<>(Arrays.asList("fake")));
        Day.MealTimeType breakfast = Day.MealTimeType.BREAKFAST;
        Day day = new Day(1);
        day.addFood(breakfast, eddie);
        day.addFood(breakfast, eddie);

        //arrange
        String expected = "fake food 0g\n" +
                            "fake food 0g\n";

        //act
        Assert.assertEquals(2, day.breakfast.getQuantity("fake food"));
    }

    @Test
    public void getMealTime() {
        //arrange
        Food eddie = new Food("fake food", new ArrayList<>(Arrays.asList("fake")));
        Day.MealTimeType breakfast = Day.MealTimeType.BREAKFAST;
        Day day = new Day(1);
        day.addFood(breakfast, eddie);

        //arrange
        String expected = "fake food 0g\n" +
                "fake food 0g\n";

        //act
        Assert.assertEquals(day.breakfast, day.getMealTime(breakfast));
    }

}
