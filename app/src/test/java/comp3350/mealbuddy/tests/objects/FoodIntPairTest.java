package comp3350.mealbuddy.tests.objects;


import org.junit.Assert;
import org.junit.Test;

import java.util.List;

import comp3350.mealbuddy.objects.consumables.FoodIntPair;
import comp3350.mealbuddy.objects.consumables.Food;


public class FoodIntPairTest {


    @Test
    public void constructor_negativeQuantity_quantitySetToZero(){
        // Arrange
        Integer expectedValue = 0;
        Integer integerValue = -5;
        String name = "egg";
        Food food = new Food(name);

        // Act
        FoodIntPair foodIntPair = new FoodIntPair(food, integerValue);

        //assert
        Assert.assertEquals(expectedValue, foodIntPair.quantity);
    }


    @Test
    public void constructor_nullFood_throwException(){
        //arrange
        String name = null;
        //act
        try{
            Food food = new Food(name);
            Assert.fail();
        }
        catch(NullPointerException e){
            //assert
            Assert.assertTrue(true);
        }
    }

    @Test
    public void equals_differentFoodNames_returnsFalse(){
        //arrange
        Integer integerValue = 5;
        String carb = "rice";
        String protein = "eggs";
        Food rice = new Food(carb);
        Food eggs = new Food(protein);

        //act
        FoodIntPair riceFoodIntPair = new FoodIntPair(rice, integerValue);
        FoodIntPair eggsFoodIntPair = new FoodIntPair(eggs, integerValue);


        // Assert
        Assert.assertFalse(riceFoodIntPair.equals(eggsFoodIntPair));
    }

    @Test
    public void equals_sameFoodNames_returnsTrue(){
        //arrange
        Integer quantity1 = 5;
        Integer quantity2 = 2;
        String foodName = "rice";
        Food rice1 = new Food(foodName);
        Food rice2 = new Food(foodName);

        //act
        FoodIntPair riceFoodIntPair1 = new FoodIntPair(rice1, quantity1);
        FoodIntPair riceFoodIntPair2 = new FoodIntPair(rice2, quantity2);

        // Assert
        Assert.assertTrue(riceFoodIntPair1.equals(riceFoodIntPair2));
    }

}
