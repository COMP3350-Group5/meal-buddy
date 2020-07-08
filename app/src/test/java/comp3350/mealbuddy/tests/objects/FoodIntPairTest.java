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
        Integer integerValue = new Integer(-5);
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
        Integer integerValue = new Integer(5);
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

}
