package comp3350.mealbuddy.tests.business;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import comp3350.mealbuddy.business.Calculator;
import comp3350.mealbuddy.objects.goals.CalorieGoal;
import comp3350.mealbuddy.objects.Day;
import comp3350.mealbuddy.objects.consumables.Edible;
import comp3350.mealbuddy.objects.consumables.Food;
import comp3350.mealbuddy.objects.goals.Goal;
import comp3350.mealbuddy.objects.consumables.Meal;

import static comp3350.mealbuddy.objects.consumables.Edible.Macros.*;
import static comp3350.mealbuddy.objects.consumables.Edible.Micros.*;

public class CalculatorTest {

    private static Day day;

    @Before
    public void createDefaultDay() {
        int dayOfYear = 23;
        day = new Day(dayOfYear);

        Food bigMac = new Food("Big Mac", new ArrayList<String>());
        bigMac.updateMacro(Fat, 100);
        bigMac.updateMacro(Protein, 100);
        bigMac.updateMicro(Sodium, 100);

        Food fries = new Food("Fries", new ArrayList<String>());
        fries.updateMacro(Carbohydrates, 100);
        fries.updateMicro(Sodium, 100);
        fries.updateMicro(Potassium, 100);

        Meal bigMacMeal = new Meal("Big Mac Meal", new ArrayList<String>());
        bigMacMeal.updateFood(bigMac, 2);
        bigMacMeal.updateFood(fries, 1);

        day.lunch.add(bigMac);
        day.dinner.add(bigMac);
        day.dinner.add(fries);
        day.snack.add(bigMacMeal);
    }

    @Test
    public void getMealTimeCalories_emptyMeal_zero() {
        //arrange
        int expectedCalories = 0;
        Calculator calculator = new Calculator(day);

        //act
        int actualCalories = calculator.getMealTimeCalories(Day.MealTimeType.BREAKFAST);

        //assert
        Assert.assertEquals(expectedCalories, actualCalories);
    }

    @Test
    public void getMealTimeCalories_oneFoodInMeal_returnCalories() {
        //arrange
        int expectedCalories = 1300;
        Calculator calculator = new Calculator(day);

        //act
        int actualCalories = calculator.getMealTimeCalories(Day.MealTimeType.LUNCH);

        //assert
        Assert.assertEquals(expectedCalories, actualCalories);
    }

    @Test
    public void getMealTimeCalories_oneMealInMeal_returnCalories() {
        //arrange
        int expectedCalories = 3000;
        Calculator calculator = new Calculator(day);

        //act
        int actualCalories = calculator.getMealTimeCalories(Day.MealTimeType.SNACK);

        //assert
        Assert.assertEquals(expectedCalories, actualCalories);
    }

    @Test
    public void getMealTimeCalories_twoFoodsInMeal_returnSummedCalories() {
        //arrange
        int expectedCalories = 1700;
        Calculator calculator = new Calculator(day);

        //act
        int actualCalories = calculator.getMealTimeCalories(Day.MealTimeType.DINNER);

        //assert
        Assert.assertEquals(expectedCalories, actualCalories);
    }

    @Test
    public void getTotalCalories_multipleMeals_returnSummedCalories() {
        //arrange
        int expectedCalories = 3000;
        Calculator calculator = new Calculator(day);

        //act
        int actualCalories = calculator.getTotalCalories();

        //assert
        Assert.assertEquals(expectedCalories, actualCalories);
    }

    @Test
    public void getTotalCalories_allEmptyMeals_returnZero() {
        //arrange
        int expectedCalories = 0;
        int dayOfYear = 1;
        Day emptyDay = new Day(dayOfYear);
        Calculator calculator = new Calculator(emptyDay);

        //act
        int actualCalories = calculator.getTotalCalories();

        //assert
        Assert.assertEquals(expectedCalories, actualCalories);
    }


}
