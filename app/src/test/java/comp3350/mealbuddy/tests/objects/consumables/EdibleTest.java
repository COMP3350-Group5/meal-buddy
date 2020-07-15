package comp3350.mealbuddy.tests.objects.consumables;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import comp3350.mealbuddy.objects.consumables.Edible;
import comp3350.mealbuddy.objects.consumables.EdibleIntPair;
import comp3350.mealbuddy.objects.consumables.Food;
import comp3350.mealbuddy.objects.consumables.Meal;

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
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class EdibleTest {


    @Test
    public void equals_EdibleIntPairSameFood_isEqual() {
        //arrange
        String name = "egg";
        Food food = new Food(name);
        int quantity = 1;
        EdibleIntPair edibleIntPair = new EdibleIntPair(food, quantity);
        //act
        boolean isEqual = food.equals(edibleIntPair);

        //assert
        assertTrue(isEqual);
    }


    @Test
    public void containsLabel_foodHaslabel_true() {
        //arrange
        String name = "egg";
        Food food = new Food(name);
        String label = "keto";

        //act
        food.labels.add(label);

        //assert
        assertTrue(food.containsLabel(label));
    }


    @Test
    public void containsLabel_mealHasLabelMealEdiblesDont_true() {
        //arrange
        Food food = new Food("butter");
        String name = "egg";
        Meal meal = new Meal(name);
        int quantity = 1;
        meal.setEdible(food, quantity);
        String label = "keto";

        //act
        meal.labels.add(label);

        //assert
        assertTrue(meal.containsLabel(label));
    }


    @Test
    public void containsLabel_mealEdiblesHasLabelMealDoesnt_false() {
        //arrange
        String label = "keto";
        Food food = new Food("Butter");
        food.labels.add(label);
        int quantity = 1;

        Meal meal = new Meal("toast");

        //act
        meal.setEdible(food, quantity);

        //assert
        assertFalse(meal.containsLabel(label));
    }


}