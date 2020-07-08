package comp3350.mealbuddy.tests.objects;

import comp3350.mealbuddy.objects.consumables.FoodIntPair;
import comp3350.mealbuddy.objects.consumables.Meal;
import comp3350.mealbuddy.objects.consumables.Edible;
import comp3350.mealbuddy.objects.consumables.Food;



import org.junit.Assert;

import java.util.ArrayList;
import java.util.List;

public class MealTest {

    public static final int NEGATIVEQUANTITY_TESTVALUE = 0;
    public static final int POSITIVEQUANTITY_TESTVALUE = 2;


    public void test_mealConstructor() {
        List<String> labels = new ArrayList<String>();
        Edible meal = new Meal("Breakfast", labels);


        Assert.assertTrue(labels.equals(meal.labels));
        Assert.assertTrue("Breakfast".equals(meal.name));
    }

    public void updateFood_negativeQuantity_updateFood() {
        List<String> mealLabels = new ArrayList<String>();
        List<String> foodLabels = new ArrayList<String>();

        Meal meal = new Meal("Lunch", mealLabels);
        Food food = new Food("Bakedbeans", foodLabels);

        meal.updateFood(food, NEGATIVEQUANTITY_TESTVALUE);

        Assert.assertFalse(meal.ediblesInMeal.contains(food));
    }

    public void updateFood_positiveQuantity_updateFood() {
        List<String> mealLabels = new ArrayList<String>();
        List<String> foodLabels = new ArrayList<String>();

        Meal meal = new Meal("Lunch", mealLabels);
        Food food = new Food("Bakedbeans", foodLabels);

        meal.updateFood(food, POSITIVEQUANTITY_TESTVALUE);

        Assert.assertTrue(meal.ediblesInMeal.contains(new FoodIntPair(food, POSITIVEQUANTITY_TESTVALUE)));
    }


    public void updateFood_nullFood_dontUpdate() {
        List<String> mealLabels = new ArrayList<String>();
        Meal meal = new Meal("Dinner", mealLabels);
        Food food = null;

        try {
            meal.updateFood(food, POSITIVEQUANTITY_TESTVALUE);
            Assert.fail();
        } catch(NullPointerException e) {
            Assert.assertTrue(true);
        }
    }

    public void updateFood_multipleFoods_dontUpdate() {
        List<String> mealLabels = new ArrayList<String>();
        List<String> foodLabels = new ArrayList<String>();
        Meal meal = new Meal("Lunch", mealLabels);
        Food eggs = new Food("eggs", foodLabels);
        Food beans = new Food("beans", foodLabels);
        Food fries = new Food("fries", foodLabels);


        meal.updateFood(eggs, POSITIVEQUANTITY_TESTVALUE);
        meal.updateFood(beans, POSITIVEQUANTITY_TESTVALUE);
        meal.updateFood(fries, POSITIVEQUANTITY_TESTVALUE);

        Assert.assertTrue(meal.ediblesInMeal.contains(new FoodIntPair(eggs, POSITIVEQUANTITY_TESTVALUE)));
        Assert.assertTrue(meal.ediblesInMeal.contains(new FoodIntPair(beans, POSITIVEQUANTITY_TESTVALUE)));
        Assert.assertTrue(meal.ediblesInMeal.contains(new FoodIntPair(fries, POSITIVEQUANTITY_TESTVALUE)));
    }

    public void removeFood_nullFoodObject_dontRemove() {
        List<String> mealLabels = new ArrayList<String>();
        Food food = null;
        Meal meal = new Meal("Dinner", mealLabels);

        try {
            meal.removeFood(food);
            Assert.fail();
        } catch(NullPointerException e) {
            Assert.assertTrue(true);
        }
    }

    public void removeFood_singleFood_remove() {
        List<String> mealLabels = new ArrayList<String>();
        List<String> foodLabels = new ArrayList<String>();
        Meal meal = new Meal("Dinner", mealLabels);
        Food food = new Food("fries", foodLabels);

        meal.updateFood(food, POSITIVEQUANTITY_TESTVALUE);
        meal.removeFood(food);

        Assert.assertFalse(meal.ediblesInMeal.contains(food));
    }

    /*
    public void removeFood_multipleFoods_remove() {
        List<String> mealLabels = new ArrayList<String>();
        List<String> foodLabels = new ArrayList<String>();
        Meal meal = new Meal("Lunch", mealLabels);
        Food fish = new Food("fries", foodLabels);
        Food chicken = new Food("Chicken", foodLabels);
        Food chickenBurger = new Food("ChickenBurger", foodLabels);

        // meal.ediblesInMeal.add(fish);


        meal.removeFood(fish);
        meal.removeFood(chicken);
        meal.removeFood(chickenBurger);

        Assert.assertFalse(meal.ediblesInMeal.contains(fish));
        Assert.assertFalse(meal.ediblesInMeal.contains(chicken));
        Assert.assertFalse(meal.ediblesInMeal.contains(chickenBurger));
    }

    public void removeFood_foodNotInList_dontRemove() {
        List<String> mealLabels = new ArrayList<String>();
        List<String> foodLabels = new ArrayList<String>();
        Meal meal = new Meal("Breakfast", mealLabels);
        Food food = new Food("Sandwich", foodLabels);

        meal.removeFood(food);

        Assert.assertFalse(meal.ediblesInMeal.contains(food));
    }

     */

}
