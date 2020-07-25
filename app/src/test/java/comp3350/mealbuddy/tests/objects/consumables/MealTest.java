package comp3350.mealbuddy.tests.objects.consumables;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import comp3350.mealbuddy.objects.consumables.EdibleIntPair;
import comp3350.mealbuddy.objects.consumables.Food;
import comp3350.mealbuddy.objects.consumables.Meal;

import static comp3350.mealbuddy.objects.consumables.Edible.Macros.Fat;
import static comp3350.mealbuddy.objects.consumables.Edible.Micros.Zinc;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

public class MealTest {


    private static Food food;

    @Before
    public void initFood() {
        String name = "egg";
        food = new Food(name);
    }

    @Test
    public void containsEdible_foodNotInList_returnsFalse() {
        //arrange
        String name = "mealName";
        Meal meal = new Meal(name);
        int initQuantity = 1;
        Food differentFood = new Food("NotEgg");

        //act
        meal.setEdible(differentFood, initQuantity);

        //assert
        Assert.assertFalse(meal.containsEdible(food));
    }

    @Test
    public void containsEdible_foodInList_returnsTrue() {
        //arrange
        String name = "mealName";
        Meal meal = new Meal(name);
        int initQuantity = 1;

        //act
        meal.setEdible(food, initQuantity);

        //assert
        Assert.assertTrue(meal.containsEdible(food));
    }


    @Test
    public void constructor_nullList_throwException() {
        //arrange
        List<String> nullList = null;
        String name = "egg";

        //act
        try {
            Meal meal = new Meal(name, nullList);
            fail();
        }//assert
        catch (NullPointerException e) {
            Assert.assertTrue(true);
        }
    }

    @Test
    public void constructor_nullName_throwException() {
        //arrange
        List<String> listOfLabels = new ArrayList<>();
        String nullName = null;

        //act
        try {
            Meal meal = new Meal(nullName, listOfLabels);
            fail();
        }//assert
        catch (NullPointerException e) {
            Assert.assertTrue(true);
        }
    }

    @Test
    public void setEdible_foodNotContained_foodAdded() {
        //arrange
        String name = "mealName";
        Meal meal = new Meal(name);

        //act
        meal.setEdible(food, 1);

        //assert
        Assert.assertTrue(meal.containsEdible(food));
    }

    @Test
    public void setEdible_foodNotContainedQuantityZero_doesntAdd() {
        //arrange
        String name = "mealName";
        Meal meal = new Meal(name);

        //act
        meal.setEdible(food, 0);

        //assert
        Assert.assertFalse(meal.containsEdible(food));
    }

    @Test
    public void setEdible_foodContainedPositiveQuantity_quantityUpdated() {
        //arrange
        String name = "mealName";
        Meal meal = new Meal(name);
        int initQuantity = 1;
        int updatedQuantity = 5;
        meal.setEdible(food, initQuantity);
        //act
        meal.setEdible(food, updatedQuantity);
        int actualQuantity = meal.getEdibleIntPair(food).quantity;
        //assert
        assertEquals(updatedQuantity, actualQuantity);
    }


    @Test
    public void setEdible_foodNotContainedNegativeQuantity_throwException() {
        //arrange
        String name = "mealName";
        Meal meal = new Meal(name);
        int initQuantity = 1;
        int updatedQuantity = -5;
        meal.setEdible(food, initQuantity);

        //act
        try {
            meal.setEdible(food, updatedQuantity);
            Assert.fail();
        } catch (IllegalArgumentException e) {
            // Assert
            Assert.assertTrue(true);
        }
    }


    @Test
    public void setEdible_foodNotContainedSetToZero_foodRemoved() {
        //arrange
        String name = "mealName";
        Meal meal = new Meal(name);
        int initQuantity = 1;
        int updatedQuantity = 0;
        meal.setEdible(food, initQuantity);

        //act
        meal.setEdible(food, updatedQuantity);

        //assert
        assertFalse(meal.containsEdible(food));
    }

    @Test
    public void getEdibleIntPair_pairContained_pairRetrieved() {
        //arrange
        String name = "mealName";
        Meal meal = new Meal(name);
        int initQuantity = 1;
        meal.setEdible(food, initQuantity);

        //act
        EdibleIntPair edibleIntPair = meal.getEdibleIntPair(food);

        //assert
        assertEquals(edibleIntPair.edible, food);
    }


    @Test
    public void getEdibleIntPair_pairNotContained_nullReturned() {
        //arrange
        String name = "mealName";
        Meal meal = new Meal(name);

        //act
        EdibleIntPair edibleIntPair = meal.getEdibleIntPair(meal);

        //assert
        assertNull(edibleIntPair);
    }


    @Test
    public void getMicroGrams_emptyMeal_zeroGrams() {
        // arrange
        Meal emptyMeal = new Meal("empty");
        int expectedGrams = 0;

        // act

        // assert
        Assert.assertEquals(expectedGrams, emptyMeal.getMicroGrams(Zinc));
    }

    @Test
    public void getMicroGrams_oneLevelMealSingleQuantity_returnGrams() {
        // arrange
        Meal meal = new Meal("empty");
        Meal cereal = makeCerealMeal();
        int expectedGrams = 30;
        int quantity = 1;

        // act
        meal.setEdible(cereal, quantity);

        // assert
        Assert.assertEquals(expectedGrams, meal.getMicroGrams(Zinc));
    }


    @Test
    public void getMicroGrams_oneLevelMealMultiQuantity_returnGrams() {
        // arrange
        Meal meal = new Meal("empty");
        Meal cereal = makeCerealMeal();
        int expectedGrams = 90;
        int quantity = 3;

        // act
        meal.setEdible(cereal, quantity);

        // assert
        Assert.assertEquals(expectedGrams, meal.getMicroGrams(Zinc));
    }


    @Test
    public void getMicroGrams_nestedMeal_returnGrams() {
        // arrange
        Meal meal = new Meal("empty");
        Meal nestedMeal = makeNestedMeal();
        int expectedGrams = 90;
        int quantity = 1;

        // act
        meal.setEdible(nestedMeal, quantity);

        // assert
        Assert.assertEquals(expectedGrams, meal.getMicroGrams(Zinc));
    }


    @Test
    public void getMacroGrams_emptyMeal_zeroGrams() {
        // arrange
        Meal emptyMeal = new Meal("empty");
        int expectedGrams = 0;

        // act

        // assert
        Assert.assertEquals(expectedGrams, emptyMeal.getMacroGrams(Fat));
    }


    @Test
    public void getMacroGrams_oneLevelMealSingleQuantity_returnGrams() {
        // arrange
        Meal meal = new Meal("empty");
        Meal cereal = makeCerealMeal();
        int expectedGrams = 30;
        int quantity = 1;

        // act
        meal.setEdible(cereal, quantity);

        // assert
        Assert.assertEquals(expectedGrams, meal.getMacroGrams(Fat));
    }


    @Test
    public void getMacroGrams_oneLevelMealMultiQuantity_returnGrams() {
        // arrange
        Meal meal = new Meal("empty");
        Meal cereal = makeCerealMeal();
        int expectedGrams = 90;
        int quantity = 3;

        // act
        meal.setEdible(cereal, quantity);

        // assert
        Assert.assertEquals(expectedGrams, meal.getMacroGrams(Fat));
    }


    @Test
    public void getMacroGrams_nestedMeal_returnGrams() {
        // arrange
        Meal meal = new Meal("empty");
        Meal nestedMeal = makeNestedMeal();
        int expectedGrams = 90;
        int quantity = 1;

        // act
        meal.setEdible(nestedMeal, quantity);

        // assert
        Assert.assertEquals(expectedGrams, meal.getMacroGrams(Fat));
    }

    @Test
    public void getEdibleIntPairIterator_noFood_hasNextFalse() {
        // arrange
        Meal meal = new Meal("empty");

        // act
        Iterator<EdibleIntPair> intPairIterator = meal.getEdibleIntPairIterator();

        // assert
        Assert.assertFalse(intPairIterator.hasNext());
    }

    @Test
    public void getEdibleIntPairIterator_hasFood_iterates() {
        // arrange
        Meal meal = makeCerealMeal();

        // act
        Iterator<EdibleIntPair> intPairIterator = meal.getEdibleIntPairIterator();

        // assert
        EdibleIntPair eip = intPairIterator.next();
        Assert.assertEquals("milk", eip.edible.name);
        Assert.assertEquals(new Integer(2), eip.quantity);

        eip = intPairIterator.next();
        Assert.assertEquals("cheerios", eip.edible.name);
        Assert.assertEquals(new Integer(1), eip.quantity);

        Assert.assertFalse(intPairIterator.hasNext());
    }

    private Meal makeNestedMeal() {
        Food egg = new Food("egg");
        egg.updateMacro(Fat, 10);
        egg.updateMicro(Zinc, 10);

        Food bacon = new Food("bacon");
        bacon.updateMacro(Fat, 10);
        bacon.updateMicro(Zinc, 10);

        Meal cereal = makeCerealMeal();

        Meal nestedMeal = new Meal("nestedMeal");
        nestedMeal.setEdible(egg, 1);
        nestedMeal.setEdible(bacon, 2);
        nestedMeal.setEdible(cereal, 2);

        return nestedMeal;
    }

    private Meal makeCerealMeal() {
        Food milk = new Food("milk");
        milk.updateMacro(Fat, 10);
        milk.updateMicro(Zinc, 10);

        Food cheerios = new Food("cheerios");
        cheerios.updateMacro(Fat, 10);
        cheerios.updateMicro(Zinc, 10);

        Meal cereal = new Meal("cereal");
        cereal.setEdible(milk, 2);
        cereal.setEdible(cheerios, 1);

        return cereal;
    }


}
