package comp3350.mealbuddy.tests.objects;
import comp3350.mealbuddy.objects.consumables.Edible;
import comp3350.mealbuddy.objects.consumables.FoodIntPair;
import comp3350.mealbuddy.objects.consumables.Food;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;





public class TestFoodIntPair {

    public void test_Constructor() {
        Integer quantity = new Integer(5);
        Food food = new Food("Ham", new ArrayList<String>());
        FoodIntPair foodIntPair = new FoodIntPair(food, quantity);

        Assert.assertTrue(foodIntPair.food.equals(food));
        Assert.assertTrue(foodIntPair.quantity.equals(quantity));
    }

    public void testEquals_foodIntPairObject_shouldPass() {
        List<String> labels = new ArrayList<String>();
        Food food = new Food("Shrimp", labels);
        Integer quantity = new Integer(6);

        FoodIntPair foodIntPair = new FoodIntPair(food, quantity);

        Assert.assertTrue(foodIntPair.equals(new FoodIntPair(new Food("Shrimp", labels), quantity)));
    }


    public void testEquals_foodIntPairObject_shouldFail() {
        List<String> labels = new ArrayList<String>();
        Food food = new Food("Fish", labels);
        Integer quantity = new Integer(9);

        FoodIntPair foodIntPair = new FoodIntPair(food, quantity);

        Assert.assertFalse(foodIntPair.equals(new FoodIntPair(new Food("Beans", labels), quantity)));
    }


    public void testEquals_nullFoodIntPairObject_shouldPass() {
        List<String> labels = new ArrayList<String>();
        Food food = new Food("Cereal", labels);
        Integer quantity = new Integer(7);

        FoodIntPair foodIntPair = new FoodIntPair(food, quantity);
        FoodIntPair nullFoodIntPair = null;

        try {
            Assert.assertEquals(foodIntPair, nullFoodIntPair);
            Assert.fail();
        } catch(NullPointerException e) {
            Assert.assertTrue(true);
        }
    }
}
