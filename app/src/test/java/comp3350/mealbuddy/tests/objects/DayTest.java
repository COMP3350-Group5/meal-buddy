package comp3350.mealbuddy.tests.objects;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import comp3350.mealbuddy.objects.Day;
import comp3350.mealbuddy.objects.Exercise;
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
    public void getMealTime_nullMealTime_throwExecption() {
        //arrange
        Day.MealTimeType MTT = null;
        Day day = new Day(1);
        //act
        try {
            day.getMealTime(MTT);
            Assert.fail();
            //assert
        } catch (NullPointerException e) {
            Assert.assertTrue(true);
        }
    }

    @Test
    public void getMealTime_validMT_returnMeal() {
        //arrange
        Day.MealTimeType MTT = Day.MealTimeType.BREAKFAST;
        Day day = new Day(1);
        //act
        //assert
        Assert.assertEquals("Breakfast", day.getMealTime(Day.MealTimeType.BREAKFAST).name);
        Assert.assertEquals("Lunch", day.getMealTime(Day.MealTimeType.LUNCH).name);
        Assert.assertEquals("Dinner", day.getMealTime(Day.MealTimeType.DINNER).name);
        Assert.assertEquals("Snack", day.getMealTime(Day.MealTimeType.SNACK).name);
    }


    @Test
    public void getMeal() {
        //arrange
        Food eddie = new Food("fake food", new ArrayList<>(Arrays.asList("fake")));
        Day.MealTimeType breakfast = Day.MealTimeType.BREAKFAST;
        Day day = new Day(1);
        day.getMealTime(breakfast).add(eddie);
        day.getMealTime(breakfast).add(eddie);

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
        day.getMealTime(breakfast).add(eddie);

        //arrange
        String expected = "fake food 0g\n" +
                "fake food 0g\n";

        //act
        Assert.assertEquals(day.breakfast, day.getMealTime(breakfast));
    }

    @Test
    public void copyConstructor() {
        //arrange
        Day newDay = new Day(1);
        Food eddie = new Food("fake food", new ArrayList<>(Arrays.asList("fake")));

        //act
        Day updatedDay = new Day(newDay);
        updatedDay.snack.add(eddie);
        updatedDay.addExercise(new Exercise("Sitting", 1, Exercise.Intensity.Low));

        //assert
        Assert.assertNotEquals(newDay.getExercises(), updatedDay.getExercises());
        Assert.assertFalse(newDay.snack.containsEdible(eddie));
        Assert.assertTrue(updatedDay.snack.containsEdible(eddie));

    }

}
