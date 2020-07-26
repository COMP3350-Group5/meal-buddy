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
        meal.add(differentFood, initQuantity);

        //assert
        Assert.assertFalse(meal.containsEdible(food));
        Assert.assertFalse(meal.containsEdible("egg"));
    }

    @Test
    public void containsEdible_foodInList_returnsTrue() {
        //arrange
        String name = "mealName";
        Meal meal = new Meal(name);
        int initQuantity = 1;

        //act
        meal.add(food, initQuantity);

        //assert
        Assert.assertTrue(meal.containsEdible(food));
        Assert.assertTrue(meal.containsEdible("egg"));
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
        meal.add(food, 1);

        //assert
        Assert.assertTrue(meal.containsEdible(food));
    }

    @Test
    public void setEdible_foodNotContainedQuantityZero_doesntAdd() {
        //arrange
        String name = "mealName";
        Meal meal = new Meal(name);

        //act
        meal.add(food, 0);

        //assert
        Assert.assertFalse(meal.containsEdible(food));
    }

    @Test
    public void add_foodContainedPositiveQuantity_quantityUpdated() {
        //arrange
        String name = "mealName";
        Meal meal = new Meal(name);
        int initQuantity = 1;
        meal.add(food, initQuantity);
        //act
        meal.add(food, 5);
        //assert
        assertEquals(new Integer(6), meal.getEdibleIntPair(food).quantity);
        meal.add(food);
        assertEquals(new Integer(7), meal.getEdibleIntPair(food).quantity);
    }


    @Test
    public void add_foodNotContainedNegativeQuantity_throwException() {
        //arrange
        String name = "mealName";
        Meal meal = new Meal(name);
        int initQuantity = 1;
        int updatedQuantity = -5;
        meal.add(food, initQuantity);

        //act
        try {
            meal.add(food, updatedQuantity);
            Assert.fail();
        } catch (IllegalArgumentException e) {
            // Assert
            Assert.assertTrue(true);
        }
    }


    @Test
    public void removeAll_foodNotContainedSetToZero_foodRemoved() {
        //arrange
        String name = "mealName";
        Meal meal = new Meal(name);
        int initQuantity = 1;
        int updatedQuantity = 0;
        meal.add(food, initQuantity);

        //act
        meal.removeAll(food);

        //assert
        assertFalse(meal.containsEdible(food));
        meal.removeAll(food);
    }

    @Test
    public void remove_foodContained_foodOneLessAmount() {
        //arrange
        String name = "mealName";
        Meal meal = new Meal(name);
        int initQuantity = 2;
        meal.add(food, initQuantity);

        //act
        //assert
        meal.remove(food);
        assertEquals(1, meal.getQuantity(food));
        meal.remove(food);
        assertEquals(0, meal.getQuantity(food));
        meal.remove(food);
        assertFalse(meal.containsEdible(food));
    }

    @Test
    public void getEdibleIntPair_pairContained_pairRetrieved() {
        //arrange
        String name = "mealName";
        Meal meal = new Meal(name);
        int initQuantity = 1;
        meal.add(food, initQuantity);

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
        meal.add(cereal, quantity);

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
        meal.add(cereal, quantity);

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
        meal.add(nestedMeal, quantity);

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
        meal.add(cereal, quantity);

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
        meal.add(cereal, quantity);

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
        meal.add(nestedMeal, quantity);

        // assert
        Assert.assertEquals(expectedGrams, meal.getMacroGrams(Fat));
    }

    @Test
    public void getQuantity_foodInList_returnQuantity() {
        // arrange
        Meal meal = new Meal("meal");
        Food egg = new Food("egg");

        // act
        meal.add(egg, 3);

        // assert
        Assert.assertEquals(3, meal.getQuantity("egg"));
        Assert.assertEquals(3, meal.getQuantity(egg));
    }

    @Test
    public void getQuantity_foodNotInList_returnZero() {
        // arrange
        Meal meal = new Meal("meal");
        Food egg = new Food("egg");

        // assert
        Assert.assertEquals(0, meal.getQuantity("egg"));
        Assert.assertEquals(0, meal.getQuantity(egg));
    }

    @Test
    public void add_foodNotInList_foodAdded() {
        // arrange
        Meal meal = new Meal("meal");
        Food egg = new Food("egg");
        Food grape = new Food("grape");

        //act
        meal.add(egg);
        meal.add(grape);
        meal.add(grape);

        // assert
        Assert.assertEquals(1, meal.getQuantity(egg));
        Assert.assertEquals(2, meal.getQuantity(grape));
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

    @Test
    public void remove_foodNotInList_foodRemoved() {
        // arrange
        Meal meal = new Meal("meal");
        Food egg = new Food("egg");
        Food grape = new Food("grape");

        //act
        meal.removeAll(egg);             //nothing happens since not in list yet
        meal.removeAll("egg");    //nothing happens since not in list yet
        meal.add(egg);
        meal.add(grape);
        meal.removeAll(egg);
        meal.removeAll(grape);

        // assert
        Assert.assertEquals(0, meal.getQuantity(egg));
        Assert.assertEquals(0, meal.getQuantity(grape));
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
        nestedMeal.add(egg, 1);
        nestedMeal.add(bacon, 2);
        nestedMeal.add(cereal, 2);

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
        cereal.add(milk, 2);
        cereal.add(cheerios, 1);

        return cereal;
    }


}
