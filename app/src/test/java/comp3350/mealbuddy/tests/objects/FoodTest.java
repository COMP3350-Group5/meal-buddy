package comp3350.mealbuddy.tests.objects;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import comp3350.mealbuddy.objects.consumables.Edible;
import comp3350.mealbuddy.objects.consumables.Food;

import static comp3350.mealbuddy.objects.consumables.Edible.Micros.Zinc;

public class FoodTest {


    @Test
    public void constructorTwoParam_nullList_throwException(){
        //arrange
        List<String> nullList = null;
        String name = "egg";
        //act
        try{
            Food food = new Food(name, nullList);
            Assert.fail();
        }
        catch(NullPointerException e){
            //assert
            Assert.assertTrue(true);
        }
        //assert
    }

    @Test
    public void constructorTwoParam_nullName_throwException(){
        //arrange
        List<String> nullList = new ArrayList<String>();
        String name = null;
        //act
        try{
            Food food = new Food(name, nullList);
            Assert.fail();
        }
        catch(NullPointerException e){
            //assert
            Assert.assertTrue(true);
        }
        //assert
    }

    @Test
    public void constructorSingleParam_nullName_throwException(){
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
        //assert
    }


    @Test
    public void setWeight_negativeWeight_weightSetToZero() {
        // arrange
        int negativeValue = -5;
        int expectedValue = 0;
        String name = "rice";
        Food food = new Food(name);

        // act
        food.setWeight(negativeValue);

        // assert
        Assert.assertTrue(expectedValue == food.weight);
    }
}
