package comp3350.mealbuddy.tests.objects;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import comp3350.mealbuddy.objects.Day;
import comp3350.mealbuddy.objects.Exercise;
import comp3350.mealbuddy.objects.consumables.Food;
import comp3350.mealbuddy.objects.goals.CalorieGoal;
import comp3350.mealbuddy.objects.goals.Goal;

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

    @Test
    public void isExerciseEmpty_avgCases_trueIfEmpty() {
        Day newDay = new Day(1);
        Exercise exer = new Exercise("exe", 1, Exercise.Intensity.High);

        Assert.assertTrue(newDay.isExerciseEmpty());

        newDay.addExercise(exer);
        Assert.assertFalse(newDay.isExerciseEmpty());
    }

    @Test
    public void isGoalsEmpty_avgCases_trueIfEmpty() {
        Day newDay = new Day(1);
        Goal goal = new CalorieGoal(1, 4);

        Assert.assertTrue(newDay.isGoalsEmpty());

        newDay.addGoal(goal);
        Assert.assertFalse(newDay.isGoalsEmpty());
    }

    @Test
    public void containsGoal_avgCases_trueIfContians() {
        Day newDay = new Day(1);
        Goal goal = new CalorieGoal(1, 4);

        Assert.assertFalse(newDay.containsGoal(goal));

        newDay.addGoal(goal);
        Assert.assertTrue(newDay.containsGoal(goal));
    }

    @Test
    public void getGoal_avgCases_returnGoalIfInList() {
        Day newDay = new Day(1);
        Goal goal = new CalorieGoal(1, 4);

        Assert.assertNull(newDay.getGoal(goal));

        newDay.addGoal(goal);
        Assert.assertEquals(goal, newDay.getGoal(goal));
    }

    @Test
    public void getExercise_avgCases_returnExerciseIfInList() {
        Day newDay = new Day(1);
        Exercise exercise = new Exercise("run", 1, Exercise.Intensity.High);

        Assert.assertNull(newDay.getExercise(exercise));

        newDay.addExercise(exercise);
        Assert.assertEquals(exercise, newDay.getExercise(exercise));
    }


    @Test
    public void addExercise_notInList_add() {
        Day newDay = new Day(1);
        Exercise exer = new Exercise("exe", 1, Exercise.Intensity.High);

        //confirm that exercises is empty
        Assert.assertTrue(newDay.isExerciseEmpty());
        newDay.addExercise(exer);
        //check that its added
        Assert.assertEquals(newDay.getExercises().next(), exer);
    }

    @Test
    public void addExercise_InList_increaseDuration() {
        Day newDay = new Day(1);
        Exercise exer = new Exercise("exe", 1, Exercise.Intensity.High);

        //confirm that exercises is empty
        Assert.assertTrue(newDay.isExerciseEmpty());
        newDay.addExercise(exer);
        Exercise exAdded = newDay.getExercise(exer);
        Assert.assertEquals(exAdded, exer);
        Assert.assertEquals(1, exAdded.duration, 0.0);
        newDay.addExercise(exer); //add it again
        exAdded = newDay.getExercise(exer);
        Assert.assertEquals(2, exAdded.duration, 0.0);
    }

    @Test
    public void addExercise_nullItem_throwException() {
        Day newDay = new Day(1);
        try {
            //remove the item
            newDay.addExercise(null);
            Assert.fail();
        } catch (NullPointerException IAE) {
            Assert.assertTrue(true);
        }
    }

    @Test
    public void addGoal_notInList_add() {
        Day newDay = new Day(1);
        Goal goal = new CalorieGoal(1, 1);

        //confirm that exercises is empty
        Assert.assertTrue(newDay.isGoalsEmpty());
        newDay.addGoal(goal);
        //check that its added
        Assert.assertEquals(newDay.getGoal(goal), goal);
    }

    @Test
    public void addGoal_InList_dontAdd() {
        Day newDay = new Day(1);
        Goal goal = new CalorieGoal(1, 1);
        Goal newGoal = new CalorieGoal(6, 9);

        //confirm that exercises is empty
        Assert.assertTrue(newDay.isGoalsEmpty());
        newDay.addGoal(goal);
        //check that its added
        Assert.assertEquals(newDay.getGoal(goal), goal);
        newDay.addGoal(goal);
        //should still have the old goal values
        Assert.assertEquals(goal.lowerBound, newDay.getGoal(goal).lowerBound);
        Assert.assertEquals(goal.upperBound, newDay.getGoal(goal).upperBound);
    }

    @Test
    public void addGoal_nullItem_throwException() {
        Day newDay = new Day(1);
        try {
            //remove the item
            newDay.addGoal(null);
            Assert.fail();
        } catch (NullPointerException IAE) {
            Assert.assertTrue(true);
        }
    }

    @Test
    public void removeExercise_inList_remove() {
        Day newDay = new Day(1);
        Exercise exer = new Exercise("exe", 1, Exercise.Intensity.High);

        //confirm that exercises is empty
        Assert.assertTrue(newDay.isExerciseEmpty());
        newDay.addExercise(exer);
        //check that its added
        Assert.assertEquals(newDay.getExercise(exer), exer);
        //remove the item
        newDay.removeExercise(exer);
        //check if the item is removed
        Assert.assertTrue(newDay.isExerciseEmpty());
    }

    @Test
    public void removeExercise_notInList_throwException() {
        Day newDay = new Day(1);
        Exercise exer = new Exercise("exe", 1, Exercise.Intensity.High);
        try {
            //remove the item
            newDay.removeExercise(exer);
            Assert.fail();
        } catch (IllegalArgumentException IAE) {
            Assert.assertTrue(true);
        }
    }

    @Test
    public void removeExercise_nullItem_throwException() {
        Day newDay = new Day(1);
        try {
            //remove the item
            newDay.removeExercise(null);
            Assert.fail();
        } catch (NullPointerException NPE) {
            Assert.assertTrue(true);
        }
    }

    @Test
    public void removeGoal_byIteminList_remove() {
        Day newDay = new Day(1);
        Goal goal = new CalorieGoal(1, 2);
        //confirm that exercises is empty
        Assert.assertTrue(newDay.isGoalsEmpty());
        newDay.addGoal(goal);
        //check that its added
        Assert.assertEquals(newDay.getGoal(goal), goal);
        //remove the item
        newDay.removeGoal(goal);
        //check if the item is removed
        Assert.assertTrue(newDay.isGoalsEmpty());
    }

    @Test
    public void removeGoal_byItemNotInList_throwException() {
        Day newDay = new Day(1);
        Goal goal = new CalorieGoal(1, 2);
        try {
            //remove the item
            newDay.removeGoal(goal);
            Assert.fail();
        } catch (IllegalArgumentException IAE) {
            Assert.assertTrue(true);
        }
    }

    @Test
    public void removeGoal_byItemNullItem_throwException() {
        Day newDay = new Day(1);
        try {
            //remove the item
            newDay.removeGoal(null);
            Assert.fail();
        } catch (NullPointerException NPE) {
            Assert.assertTrue(true);
        }
    }

    @Test
    public void removeGoal_byIndexInList_remove() {
        Day newDay = new Day(1);
        Goal goal = new CalorieGoal(1, 2);
        //confirm that exercises is empty
        Assert.assertTrue(newDay.isGoalsEmpty());
        newDay.addGoal(goal);
        //check that its added
        Assert.assertEquals(newDay.getGoal(goal), goal);
        //remove the item
        newDay.removeGoal(0);
        //check if the item is removed
        Assert.assertTrue(newDay.isGoalsEmpty());
    }

    @Test
    public void removeGoal_byIndexOutOfBoundsLower_throwException() {
        Day newDay = new Day(1);
        try {
            //remove the item
            newDay.removeGoal(-1);
            Assert.fail();
        } catch (IndexOutOfBoundsException IAE) {
            Assert.assertTrue(true);
        }
    }

    @Test
    public void removeGoal_byIndexOutOfBoundsUpper_throwException() {
        Day newDay = new Day(1);
        try {
            //remove the item
            newDay.removeGoal(1);
            Assert.fail();
        } catch (IndexOutOfBoundsException IAE) {
            Assert.assertTrue(true);
        }
    }

}
