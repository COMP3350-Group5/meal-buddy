package comp3350.mealbuddy.tests.objects;
import org.junit.Assert;

import java.util.ArrayList;
import java.util.List;


import comp3350.mealbuddy.objects.consumables.Edible;
import comp3350.mealbuddy.objects.consumables.Edible.Macros;
import comp3350.mealbuddy.objects.consumables.Edible.Micros;
import comp3350.mealbuddy.objects.consumables.Food;



public class FoodTest {

    public static final int TEST_VALUE = 20;
    public void test_Constructor() {
        List<String> labels = new ArrayList<String>();
        Edible food = new Food("Eggs", labels);

        Assert.assertEquals(food.name, "Eggs");
        Assert.assertEquals(food.labels, labels);
    }

    public void addMacros_addSingleMacro_addMacros() {
        List<String> labels = new ArrayList<String>();
        Edible food = new Food("Chicken", labels);
        Macros foodMacros = Macros.Protein;

        food.addMacro(foodMacros, TEST_VALUE);

        Assert.assertTrue(food.macros.containsKey(foodMacros));
    }

    public void addMacros_addMultipleMacros_dontAdd() {
        List<String> labels = new ArrayList<String>();
        Edible food = new Food("Eggs", labels);
        Macros protein = Macros.Protein;
        Macros carbs = Macros.Carbohydrates;
        Macros fat = Macros.Fat;


        food.addMacro(protein, TEST_VALUE);
        food.addMacro(carbs, TEST_VALUE);
        food.addMacro(fat, TEST_VALUE);

        Assert.assertTrue(food.macros.containsKey(protein) && food.macros.containsKey(carbs) && food.macros.containsKey(fat));
    }

    public void addMacros_nullMacro_dontAdd() {
        List<String> labels = new ArrayList<String>();
        Edible food = new Food("Butter", labels);
        Macros fat = null;

        try {
            food.addMacro(fat, TEST_VALUE);
            Assert.fail();
        } catch (NullPointerException e) {
            Assert.assertTrue(true);
        }
    }

    public void addMicros_addSingleMicro_addMicros() {
        List<String> labels = new ArrayList<String>();
        Edible food = new Food("Rice", labels);
        Micros vitamins = Micros.VitaminA;

        food.addMicro(vitamins, TEST_VALUE);

        Assert.assertTrue(food.micros.containsKey(vitamins));
    }

    public void addMicros_addMultipleMicros_addMicros() {
        List<String> labels = new ArrayList<String>();
        Edible food = new Food("Sphagetti", labels);
        Micros minerals = Micros.Calcium;
        Micros vitamins = Micros.VitaminB12;

        food.addMicro(minerals, TEST_VALUE);
        food.addMicro(vitamins, TEST_VALUE);

        Assert.assertTrue(food.micros.containsKey(minerals) && food.micros.containsKey(vitamins));
    }


    public void addMicros_nullMicro_dontAdd() {
        List<String> labels = new ArrayList<String>();
        Edible food = new Food("Steak", labels);
        Micros zinc = null;

        try {
            food.addMicro(zinc, TEST_VALUE);
            Assert.fail();
        } catch (NullPointerException e) {
            Assert.assertTrue(true);
        }
    }

    public void testEquals_foodObject_shouldPass() {
        List<String> labels = new ArrayList<String>();
        Edible food = new Food("Shrimp", labels);

        Assert.assertTrue(food.equals(new Food("Shrimp", labels)));
    }

    public void testEquals_nullObject_shouldFail() {
        List<String> labels = new ArrayList<String>();
        Edible food = new Food("Banana", labels);
        Food banana = null;

        try {
            Assert.assertEquals(food, banana);
            Assert.fail();
        } catch(NullPointerException e) {
            Assert.assertTrue(true);
        }
    }

    public void testEquals_differentFoodObjects_shouldFail() {
        List<String> labels = new ArrayList<String>();
        Edible food = new Food("Milk", labels);

        Assert.assertFalse(food.equals(new Food("Pizza", labels)));
    }

    public void testEquals_sameFoodObjects_shouldPass() {
        List<String> labels = new ArrayList<String>();
        Edible food = new Food("Sphagetti", labels);
        Edible pizza = food;

        Assert.assertTrue(food.equals(pizza));
    }
}
