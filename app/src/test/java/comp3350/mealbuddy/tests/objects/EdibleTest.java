package comp3350.mealbuddy.tests.objects;

import org.junit.Assert;
import org.junit.Before;
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

public class EdibleTest {

    private static Food food;

    @Before
    public void initFood() {
        String name = "egg";
        food = new Food(name);
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
        Edible egg = new Food(name);

        //assert
        for (Edible.Micros micro : allMicros) {
            Assert.assertTrue(egg.micros.containsKey(micro));
            Assert.assertEquals(initValue, (int) egg.micros.get(micro));
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
        Edible egg = new Food(name);

        //assert
        for (Edible.Micros micro : allMicros) {
            Assert.assertTrue(egg.micros.containsKey(micro));
            Assert.assertEquals(initValue, (int) egg.micros.get(micro));
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
        Edible egg = new Food(name);

        //assert
        for (Edible.Macros macro : allMacros) {
            Assert.assertTrue(egg.macros.containsKey(macro));
            Assert.assertEquals(initValue, (int) egg.macros.get(macro));

        }
    }

    @Test
    public void updateMicro_positiveValue_microUpdated() {
        //arrange
        int updatedValue = 10;
        int expectedValue = 10;

        //act
        food.updateMicro(Zinc, updatedValue);

        //assert
        Assert.assertEquals(expectedValue, (int) food.micros.get(Zinc));
    }


    @Test
    public void updateMacro_positiveValue_macroUpdated() {
        //arrange
        int updatedValue = 10;
        int expectedValue = 10;

        //act
        food.updateMacro(Fat, updatedValue);

        //assert
        Assert.assertEquals(expectedValue, (int) food.macros.get(Fat));
    }

    @Test
    public void updateMacro_negativeValue_macroSetToZero() {
        //arrange
        int updatedValue = -10;
        int expectedValue = 0;

        //act
        food.updateMacro(Fat, updatedValue);

        //assert
        Assert.assertEquals(expectedValue, (int) food.macros.get(Fat));
    }

    @Test
    public void updateMicro_negativeValue_microSetToZero() {
        //arrange
        int negativeValue = -10;
        int expectedValue = 0;

        //act
        food.updateMicro(Zinc, negativeValue);

        //assert
        Assert.assertEquals(expectedValue, (int) food.micros.get(Zinc));
    }

    @Test
    public void updateMicro_nullMicro_throwException() {
        //arrange
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


}