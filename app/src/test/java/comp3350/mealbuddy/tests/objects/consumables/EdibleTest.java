package comp3350.mealbuddy.tests.objects.consumables;

import org.junit.Test;

import comp3350.mealbuddy.objects.consumables.Edible;
import comp3350.mealbuddy.objects.consumables.EdibleIntPair;
import comp3350.mealbuddy.objects.consumables.Food;
import comp3350.mealbuddy.objects.consumables.Meal;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

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
        meal.add(food, quantity);
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
        meal.add(food, quantity);

        //assert
        assertFalse(meal.containsLabel(label));
    }

    @Test
    public void copyConstructorTest() {
        //Arrange
        Edible food = new Food("Butter");
        food.labels.add("Test");
        Edible copiedFood = Edible.copyEdible(food);

        Meal meal = new Meal("Meal1");
        meal.add(food);
        Meal copiedMeal = (Meal) Edible.copyEdible(meal);
        Edible food2 = new Food("Toast");

        //Act
        food.labels.add("Original");
        copiedFood.labels.add("Copied");
        copiedMeal.add(food2);

        //Assert
        assertFalse(food.containsLabel("copied"));
        assertTrue(copiedFood.containsLabel("Test"));
        assertTrue(copiedMeal.containsEdible(food));
        assertFalse(meal.containsEdible(food2));

    }

}