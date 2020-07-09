package comp3350.mealbuddy.tests.objects.consumables;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import comp3350.mealbuddy.objects.consumables.Food;
import comp3350.mealbuddy.objects.consumables.FoodIntPair;
import comp3350.mealbuddy.objects.consumables.Meal;

import static org.junit.Assert.fail;

public class MealTest {


    private static Food food;

    @Before
    public void initFood(){
        String name = "egg";
        food = new Food(name);
    }

    @Test
    public void containsFood_foodNotInSet_returnsFalse(){
        //arrange
        String name = "mealName";
        Meal meal = new Meal(name);
        int initQuantity = 1;
        Food differentFood = new Food("NotEgg");
        FoodIntPair diffFoodIntPair = new FoodIntPair(differentFood, initQuantity);

        //act
        meal.ediblesInMeal.add(diffFoodIntPair);

        //assert
        Assert.assertFalse(meal.containsFood(food));
    }

    @Test
    public void containsFood_foodInSet_returnsTrue(){
        //arrange
        String name = "mealName";
        Meal meal = new Meal(name);
        int initQuantity = 1;
        FoodIntPair foodIntPair = new FoodIntPair(food, initQuantity);

        //act
        meal.ediblesInMeal.add(foodIntPair);

        //assert
        Assert.assertTrue(meal.containsFood(food));
    }


    @Test
    public void constructor_nullList_throwException(){
        //arrange
        List<String> nullList = null;
        String name = "egg";

        //act
        try{
            Meal meal = new Meal(name, nullList);
            fail();
        }//assert
        catch(NullPointerException e){
            Assert.assertTrue(true);
        }
    }

    @Test
    public void constructor_nullName_throwException(){
        //arrange
        List<String> listOfLabels = new ArrayList<>();
        String nullName = null;

        //act
        try{
            Meal meal = new Meal(nullName, listOfLabels);
            fail();
        }//assert
        catch(NullPointerException e){
            Assert.assertTrue(true);
        }
    }

    @Test
    public void updateFood_foodNotContained_foodAdded(){
        //arrange
        String name = "mealName";
        Meal meal = new Meal(name);

        //act
        meal.updateFood(food, 1);

        //assert
        Assert.assertTrue(meal.containsFood(food));
    }

    @Test
    public void updateFood_foodNotContainedQuantityZero_throwException(){
        //arrange
        String name = "mealName";
        Meal meal = new Meal(name);

        //act
        try {
            meal.updateFood(food, 0);
            Assert.fail();
        } catch (IllegalArgumentException e) {
            // Assert
            Assert.assertTrue(true);
        }

    }

    @Test
    public void updateFood_foodContainedPositiveQuantity_quantityUpdated(){
        //arrange
        String name = "mealName";
        Meal meal = new Meal(name);
        int initQuantity = 1;
        Integer updatedQuantity = 5;
        FoodIntPair foodIntPair = new FoodIntPair(food, initQuantity);
        meal.ediblesInMeal.add(foodIntPair);

        //act
        meal.updateFood(food, updatedQuantity);

        //assert
        for(FoodIntPair pairInSet: meal.ediblesInMeal){
            Assert.assertEquals( updatedQuantity, pairInSet.quantity );
        }
    }

    @Test
    public void updateFood_foodNotContainedNegativeQuantity_throwException() {
        //arrange
        String name = "mealName";
        Meal meal = new Meal(name);
        int initQuantity = 1;
        Integer updatedQuantity = -5;
        FoodIntPair foodIntPair = new FoodIntPair(food, initQuantity);
        meal.ediblesInMeal.add(foodIntPair);

        //act
        try {
            meal.updateFood(food, updatedQuantity);
            Assert.fail();
        } catch (IllegalArgumentException e) {
            // Assert
            Assert.assertTrue(true);
        }
    }


}
