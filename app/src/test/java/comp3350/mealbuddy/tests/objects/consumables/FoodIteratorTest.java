package comp3350.mealbuddy.tests.objects.consumables;

import org.junit.Before;
import org.junit.Test;

import java.util.Iterator;

import comp3350.mealbuddy.objects.consumables.Edible;
import comp3350.mealbuddy.objects.consumables.EdibleIntPair;
import comp3350.mealbuddy.objects.consumables.Food;
import comp3350.mealbuddy.objects.consumables.FoodIterator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class FoodIteratorTest {

    private static Food food;

    @Before
    public void initFood() {
        String name = "egg";
        food = new Food(name);
    }

    @Test
    public void hasNext_notIteratedOver_true() {
        //arrange
        Iterator<Edible> foodIterator = food.iterator();

        //act
        //assert
        assertTrue(foodIterator.hasNext());
    }


    @Test
    public void hasNext_alreadyIterated_false() {
        //arrange
        Iterator<Edible> foodIterator = food.iterator();

        //act
        foodIterator.next();
        //assert
        assertFalse(foodIterator.hasNext());
    }

    @Test
    public void next_alreadyIterated_returnsNull() {
        //arrange
        Iterator<Edible> foodIterator = food.iterator();

        //act
        foodIterator.next();

        //assert
        assertNull(foodIterator.next());
    }


    @Test
    public void next_notIterated_returnsFood() {
        //arrange
        Iterator<Edible> foodIterator = food.iterator();

        //act

        //assert
        assertEquals(food, foodIterator.next());
    }

}
