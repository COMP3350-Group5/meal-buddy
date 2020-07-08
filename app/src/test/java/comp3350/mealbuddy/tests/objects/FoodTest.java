package comp3350.mealbuddy.tests.objects;

import org.junit.Test;

import java.util.List;

import comp3350.mealbuddy.objects.consumables.Food;

public class FoodTest {

    @Test
    public void constructor_nullList_throwException(){
        //arrange
        List<String> nullList = null;
        String name = "egg";
        //act
        try{
            Food food = new Food(name, nullList);
        }
        catch{
            //assert
        }

        //assert


    }




}
