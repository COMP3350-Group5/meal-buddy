package comp3350.mealbuddy.tests.objects.consumables;


import org.junit.Assert;
import org.junit.Test;

import comp3350.mealbuddy.objects.consumables.EdibleIntPair;
import comp3350.mealbuddy.objects.consumables.Food;


public class EdibleIntPairTest {


    @Test
    public void constructor_negativeQuantity_throwException() {
        // Arrange
        Integer integerValue = -5;
        String name = "egg";
        Food food = new Food(name);

        // Act
        try {
            EdibleIntPair edibleIntPair = new EdibleIntPair(food, integerValue);
            Assert.fail();
        } catch (IllegalArgumentException e) {
            //Assert
            Assert.assertTrue(true);
        }
    }


    @Test
    public void constructor_nullFood_throwException() {
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
    public void equals_differentFoodNames_returnsFalse() {
        //arrange
        Integer integerValue = 5;
        String carb = "rice";
        String protein = "eggs";
        Food rice = new Food(carb);
        Food eggs = new Food(protein);

        //act
        EdibleIntPair riceEdibleIntPair = new EdibleIntPair(rice, integerValue);
        EdibleIntPair eggsEdibleIntPair = new EdibleIntPair(eggs, integerValue);


        // Assert
        Assert.assertNotEquals(riceEdibleIntPair, eggsEdibleIntPair);
    }

    @Test
    public void equals_sameFoodNames_returnsTrue() {
        //arrange
        Integer quantity1 = 5;
        Integer quantity2 = 2;
        String foodName = "rice";
        Food rice1 = new Food(foodName);
        Food rice2 = new Food(foodName);

        //act
        EdibleIntPair riceEdibleIntPair1 = new EdibleIntPair(rice1, quantity1);
        EdibleIntPair riceEdibleIntPair2 = new EdibleIntPair(rice2, quantity2);

        // Assert
        Assert.assertEquals(riceEdibleIntPair1, riceEdibleIntPair2);
    }

}
