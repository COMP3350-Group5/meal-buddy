package comp3350.mealbuddy.tests.objects.consumables;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import comp3350.mealbuddy.objects.consumables.Edible;
import comp3350.mealbuddy.objects.consumables.Food;

import static comp3350.mealbuddy.objects.consumables.Edible.Macros.Alcohol;
import static comp3350.mealbuddy.objects.consumables.Edible.Macros.Carbohydrates;
import static comp3350.mealbuddy.objects.consumables.Edible.Macros.Fat;
import static comp3350.mealbuddy.objects.consumables.Edible.Macros.Protein;
import static comp3350.mealbuddy.objects.consumables.Edible.Micros.Calcium;
import static comp3350.mealbuddy.objects.consumables.Edible.Micros.Choline;
import static comp3350.mealbuddy.objects.consumables.Edible.Micros.Iron;
import static comp3350.mealbuddy.objects.consumables.Edible.Micros.Magnesium;
import static comp3350.mealbuddy.objects.consumables.Edible.Micros.Niacin;
import static comp3350.mealbuddy.objects.consumables.Edible.Micros.Potassium;
import static comp3350.mealbuddy.objects.consumables.Edible.Micros.Sodium;
import static comp3350.mealbuddy.objects.consumables.Edible.Micros.VitaminA;
import static comp3350.mealbuddy.objects.consumables.Edible.Micros.VitaminB12;
import static comp3350.mealbuddy.objects.consumables.Edible.Micros.VitaminC;
import static comp3350.mealbuddy.objects.consumables.Edible.Micros.VitaminE;
import static comp3350.mealbuddy.objects.consumables.Edible.Micros.Zinc;
import static org.junit.Assert.fail;

public class FoodTest {


    @Test
    public void constructorTwoParam_nullList_throwException() {
        //arrange
        List<String> nullList = null;
        String name = "egg";
        //act
        try {
            Food food = new Food(name, nullList);
            Assert.fail();
        } catch (NullPointerException e) {
            //assert
            Assert.assertTrue(true);
        }
    }

    @Test
    public void constructorTwoParam_nullName_throwException() {
        //arrange
        List<String> nullList = new ArrayList<>();
        String name = null;
        //act
        try {
            Food food = new Food(name, nullList);
            Assert.fail();
        } catch (NullPointerException e) {
            //assert
            Assert.assertTrue(true);
        }
    }

    @Test
    public void constructorSingleParam_nullName_throwException() {
        //arrange
        String name = null;
        //act
        try {
            Food food = new Food(name);
            Assert.fail();
        } catch (NullPointerException e) {
            //assert
            Assert.assertTrue(true);
        }
    }


    @Test
    public void setWeight_negativeWeight_weightSetToZero() {
        // arrange
        int negativeValue = -5;
        int expectedValue = 0;
        Food food = makeFood();

        // act
        food.setWeight(negativeValue);

        // assert
        Assert.assertEquals(expectedValue, food.weight);
    }

    @Test
    public void getMacroGrams_negativeWeight_weightSetToZero() {
        // arrange
        int negativeValue = -5;
        int expectedValue = 0;
        Food food = makeFood();

        // act
        food.setWeight(negativeValue);

        // assert
        Assert.assertEquals(expectedValue, food.weight);
    }


    @Test
    public void getMacroGrams_avgCase_returnAmount() {
        // arrange
        Food food = makeFood();
        int amount = 10;

        // act
        food.updateMacro(Fat, amount);

        // assert
        Assert.assertEquals(food.getMacroGrams(Fat), amount);
    }

    @Test
    public void getMicroGrams_avgCase_returnAmount() {
        // arrange
        Food food = makeFood();
        int amount = 10;

        // act
        food.updateMicro(Zinc, amount);

        // assert
        Assert.assertEquals(food.getMicroGrams(Zinc), amount);
    }


    @Test
    public void constructor_avgCase_AllMicrosCreated() {
        //arrange
        String name = "egg";
        int initValue = 0;
        Edible.Micros[] allMicros = {
                Iron, Zinc, VitaminA, VitaminB12, VitaminC, VitaminE,
                Calcium, Choline, Magnesium, Sodium, Potassium, Niacin
        };

        //act
        Food egg = new Food(name);

        //assert
        for (Edible.Micros micro : allMicros) {
            Assert.assertTrue(egg.containsMicro(micro));
            Assert.assertEquals(initValue, egg.getMicroGrams(micro));
        }
    }

    @Test
    public void constructor_nullList_AllMicrosCreated() {
        //arrange
        String name = "egg";
        int initValue = 0;
        Edible.Micros[] allMicros = {
                Iron, Zinc, VitaminA, VitaminB12, VitaminC, VitaminE,
                Calcium, Choline, Magnesium, Sodium, Potassium, Niacin
        };

        //act
        Food egg = new Food(name);

        //assert
        for (Edible.Micros micro : allMicros) {
            Assert.assertTrue(egg.containsMicro(micro));
            Assert.assertEquals(initValue, egg.getMicroGrams(micro));
        }
    }

    @Test
    public void constructor_avgCase_AllMacrosCreated() {
        //arrange
        String name = "egg";
        int initValue = 0;
        Edible.Macros[] allMacros = {
                Fat, Carbohydrates, Protein, Alcohol
        };

        //act
        Food egg = new Food(name);

        //assert
        for (Edible.Macros macro : allMacros) {
            Assert.assertTrue(egg.containsMacro(macro));
            Assert.assertEquals(initValue, egg.getMacroGrams(macro));
        }
    }

    @Test
    public void updateMicro_positiveValue_microUpdated() {
        //arrange
        Food food = makeFood();
        int updatedValue = 10;
        int expectedValue = 10;

        //act
        food.updateMicro(Zinc, updatedValue);

        //assert
        Assert.assertEquals(expectedValue, food.getMicroGrams(Zinc));
    }


    @Test
    public void updateMacro_positiveValue_macroUpdated() {
        //arrange
        Food food = makeFood();
        int updatedValue = 10;
        int expectedValue = 10;

        //act
        food.updateMacro(Fat, updatedValue);

        //assert
        Assert.assertEquals(expectedValue, food.getMacroGrams(Fat));
    }

    @Test
    public void updateMacro_negativeValue_macroSetToZero() {
        //arrange
        Food food = makeFood();
        int updatedValue = -10;
        int expectedValue = 0;

        //act
        food.updateMacro(Fat, updatedValue);

        //assert
        Assert.assertEquals(expectedValue, food.getMacroGrams(Fat));
    }

    @Test
    public void updateMicro_negativeValue_microSetToZero() {
        //arrange
        Food food = makeFood();
        int negativeValue = -10;
        int expectedValue = 0;

        //act
        food.updateMicro(Zinc, negativeValue);

        //assert
        Assert.assertEquals(expectedValue, food.getMicroGrams(Zinc));
    }

    @Test
    public void updateMicro_nullMicro_throwException() {
        //arrange
        Food food = makeFood();
        int microValue = 0;
        Edible.Micros nullMicro = null;

        //act
        try {
            food.updateMicro(nullMicro, microValue);
            fail();
        }//assert
        catch (NullPointerException e) {
            Assert.assertTrue(true);
        }
    }

    @Test
    public void updateMacro_nullMacro_throwException() {
        //arrange
        Food food = makeFood();
        int microValue = 0;
        Edible.Macros nullMacro = null;

        //act
        try {
            food.updateMacro(nullMacro, microValue);
            fail();
        }//assert
        catch (NullPointerException e) {
            Assert.assertTrue(true);
        }
    }


    public Food makeFood() {
        String name = "egg";
        return new Food(name);
    }


}
