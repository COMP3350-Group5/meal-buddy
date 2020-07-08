package comp3350.mealbuddy.tests.business;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import comp3350.mealbuddy.business.Calculator;
import comp3350.mealbuddy.objects.Day;
import comp3350.mealbuddy.objects.consumables.Food;
import comp3350.mealbuddy.objects.consumables.Meal;

import static comp3350.mealbuddy.objects.consumables.Edible.Macros.*;
import static comp3350.mealbuddy.objects.consumables.Edible.Micros.*;
import static org.junit.Assert.fail;

public class CalculatorTest {

    private static Day day;

    @Before
    public void createDefaultDay() {
        int dayOfYear = 23;
        day = new Day(dayOfYear);

        Food bigMac = makeBigMac();
        Food fries = makeFries();
        Meal bigMacMeal = makeBigMacMeal();

        day.lunch.add(bigMac);

        day.dinner.add(bigMacMeal);
        day.dinner.add(fries);

        day.snack.add(bigMacMeal);
    }

    @Test
    public void constructor_nullDay_throwException() {
        //arrange
        Day nullDay = null;
        //act
        try {
            Calculator newCalculator = new Calculator(nullDay);
            Assert.fail();
            //Food machine broke
        }
        catch (NullPointerException npe) {
            //Understandable, have a good day
            //assert
            Assert.assertTrue(true);
        }

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
    public void getMealTimeCalories_multipleEdiblesInMeal_returnSummedCalories() {
        //arrange
        int expectedCalories = 3400;
        Calculator calculator = new Calculator(day);

        //act
        int actualCalories = calculator.getMealTimeCalories(Day.MealTimeType.DINNER);

        //assert
        Assert.assertEquals(expectedCalories, actualCalories);
    }

    @Test
    public void getTotalCalories_multipleMeals_returnSummedCalories() {
        //arrange
        int expectedCalories = 7700;
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

    @Test
    public void getMealTimeMacroCalories_emptyMeal_returnZero() {
        //arrange
        int expectedCalories = 0;
        Calculator calculator = new Calculator(day);

        //act
        int actualCalories = calculator.getMealTimeMacroCalories(Day.MealTimeType.BREAKFAST, Fat);

        //assert
        Assert.assertEquals(expectedCalories, actualCalories);
    }

    @Test
    public void getMealTimeMacroCalories_oneFoodInMeal_returnCalories() {
        //arrange
        int expectedCalories = 900;
        Calculator calculator = new Calculator(day);

        //act
        int actualCalories = calculator.getMealTimeMacroCalories(Day.MealTimeType.LUNCH, Fat);

        //assert
        Assert.assertEquals(expectedCalories, actualCalories);
    }

    @Test
    public void getMealTimeMacroCalories_oneMealInMeal_returnCalories() {
        //arrange
        int expectedCalories = 1800;
        Calculator calculator = new Calculator(day);

        //act
        int actualCalories = calculator.getMealTimeMacroCalories(Day.MealTimeType.SNACK, Fat);

        //assert
        Assert.assertEquals(expectedCalories, actualCalories);
    }

    @Test
    public void getMealTimeMacroCalories_multipleEdiblesInMeal_returnCalories() {
        //arrange
        int expectedCalories = 1800;
        Calculator calculator = new Calculator(day);

        //act
        int actualCalories = calculator.getMealTimeMacroCalories(Day.MealTimeType.DINNER, Fat);

        //assert
        Assert.assertEquals(expectedCalories, actualCalories);
    }

    @Test
    public void getMacroCalories_multipleMeals_returnSummedCalories() {
        //arrange
        int expectedCalories = 4500;
        Calculator calculator = new Calculator(day);

        //act
        int actualCalories = calculator.getMacroCalories(Fat);

        //assert
        Assert.assertEquals(expectedCalories, actualCalories);
    }

    @Test
    public void getMacroCalories_allEmptyMeals_returnZero() {
        //arrange
        int expectedCalories = 0;
        int dayOfYear = 1;
        Day emptyDay = new Day(dayOfYear);
        Calculator calculator = new Calculator(emptyDay);

        //act
        int actualCalories = calculator.getMacroCalories(Fat);

        //assert
        Assert.assertEquals(expectedCalories, actualCalories);
    }

    @Test
    public void getMealTimeMicroMgs_emptyMeal_returnZero() {
        //arrange
        int expectedCalories = 0;
        Calculator calculator = new Calculator(day);

        //act
        int actualCalories = calculator.getMealTimeMicroMgs(Day.MealTimeType.BREAKFAST, Sodium);

        //assert
        Assert.assertEquals(expectedCalories, actualCalories);
    }

    @Test
    public void getMealTimeMicroMgs_oneFoodInMeal_returnMgs() {
        //arrange
        int expectedMgs = 200;
        Calculator calculator = new Calculator(day);

        //act
        int actualMgs = calculator.getMealTimeMicroMgs(Day.MealTimeType.LUNCH, Sodium);

        //assert
        Assert.assertEquals(expectedMgs, actualMgs);
    }

    @Test
    public void getMealTimeMicroMgs_oneMealInMeal_returnMgs() {
        //arrange
        int expectedMgs = 500;
        Calculator calculator = new Calculator(day);

        //act
        int actualMgs = calculator.getMealTimeMicroMgs(Day.MealTimeType.SNACK, Sodium);

        //assert
        Assert.assertEquals(expectedMgs, actualMgs);
    }

    @Test
    public void getMealTimeMicroMgs_multipleEdiblesInMeal_returnMgs() {
        //arrange
        int expectedMgs = 600;
        Calculator calculator = new Calculator(day);

        //act
        int actualMgs = calculator.getMealTimeMicroMgs(Day.MealTimeType.DINNER, Sodium);

        //assert
        Assert.assertEquals(expectedMgs, actualMgs);
    }

    @Test
    public void getMicroMgs_multipleMeals_returnSummedMgs() {
        //arrange
        int expectedMgs = 1300;
        Calculator calculator = new Calculator(day);

        //act
        int actualMgs = calculator.getMicroMgs(Sodium);

        //assert
        Assert.assertEquals(expectedMgs, actualMgs);
    }

    @Test
    public void getMicroMgs_allEmptyMeals_returnZero() {
        //arrange
        int expectedMgs = 0;
        int dayOfYear = 1;
        Day emptyDay = new Day(dayOfYear);
        Calculator calculator = new Calculator(emptyDay);

        //act
        int actualMgs = calculator.getMicroMgs(Sodium);

        //assert
        Assert.assertEquals(expectedMgs, actualMgs);
    }

    @Test
    public void getMealTimeLabelCalories_emptyMeal_returnZero() {
        //arrange
        int expectedCalories = 0;
        Calculator calculator = new Calculator(day);
        String label = "Super Size Me";

        //act
        int actualCalories = calculator.getMealTimeLabelCalories(Day.MealTimeType.BREAKFAST, label);

        //assert
        Assert.assertEquals(expectedCalories, actualCalories);
    }

    @Test
    public void getMealTimeLabelCalories_oneFoodInMeal_returnCalories() {
        //arrange
        int expectedCalories = 1300;
        Calculator calculator = new Calculator(day);
        String label = "Super Size Me";

        //act
        int actualCalories = calculator.getMealTimeLabelCalories(Day.MealTimeType.LUNCH, label);

        //assert
        Assert.assertEquals(expectedCalories, actualCalories);
    }

    @Test
    public void getMealTimeLabelCalories_oneMealInMeal_returnCalories() {
        //arrange
        int expectedCalories = 2600;
        Calculator calculator = new Calculator(day);
        String label = "Super Size Me";

        //act
        int actualCalories = calculator.getMealTimeLabelCalories(Day.MealTimeType.SNACK, label);

        //assert
        Assert.assertEquals(expectedCalories, actualCalories);
    }

    @Test
    public void getMealTimeLabelCalories_multipleEdiblesInMeal_returnCalories() {
        //arrange
        int expectedCalories = 2600;
        Calculator calculator = new Calculator(day);
        String label = "Super Size Me";

        //act
        int actualCalories = calculator.getMealTimeLabelCalories(Day.MealTimeType.DINNER, label);

        //assert
        Assert.assertEquals(expectedCalories, actualCalories);
    }

    @Test
    public void getLabelCalories_multipleMeals_returnSummedCalories() {
        //arrange
        int expectedCalories = 6500;
        String label = "Super Size Me";
        Calculator calculator = new Calculator(day);


        //act
        int actualCalories = calculator.getLabelCalories(label);

        //assert
        Assert.assertEquals(expectedCalories, actualCalories);
    }

    @Test
    public void getLabelCalories_allEmptyMeals_returnZero() {
        //arrange
        int expectedCalories = 0;
        int dayOfYear = 1;
        Day emptyDay = new Day(dayOfYear);
        Calculator calculator = new Calculator(emptyDay);
        String label = "Super Size Me";

        //act
        int actualCalories = calculator.getLabelCalories(label);

        //assert
        Assert.assertEquals(expectedCalories, actualCalories);
    }

    private Food makeFries() {
        Food fries = new Food("Fries");
        fries.updateMacro(Carbohydrates, 100);
        fries.updateMicro(Sodium, 100);
        fries.updateMicro(Potassium, 100);
        return fries;
    }

    private Food makeBigMac() {
        ArrayList<String> labels = new ArrayList<>();
        labels.add("Super Size Me");
        Food bigMac = new Food("Big Mac Meal", labels);
        bigMac.updateMacro(Fat, 100);
        bigMac.updateMacro(Protein, 100);
        bigMac.updateMicro(Sodium, 200);
        return bigMac;
    }

    private Meal makeBigMacMeal() {
        Meal bigMacMeal = new Meal("Big Mac Meal");
        bigMacMeal.updateFood(makeBigMac(), 2);
        bigMacMeal.updateFood(makeFries(), 1);
        return bigMacMeal;
    }
}
