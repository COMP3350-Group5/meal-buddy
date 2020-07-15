package comp3350.mealbuddy.tests.objects.consumables;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.microedition.khronos.egl.EGLDisplay;

import comp3350.mealbuddy.objects.consumables.Edible;
import comp3350.mealbuddy.objects.consumables.Food;
import comp3350.mealbuddy.objects.consumables.Meal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class MealIteratorTest {


    @Test
    public void hasNext_notIteratedOver_true() {
        //arrange
        Meal meal = new Meal("meal");
        meal.setEdible(new Food("fish"), 1);

        //act
        Iterator<Edible> iterator = meal.iterator();

        //assert
        assertTrue(iterator.hasNext());
    }

    @Test
    public void hasNext_notIteratedOverEmptyMeal_false() {
        //arrange
        Meal emptyMeal = new Meal("mealName");
        Iterator<Edible> iterator = emptyMeal.iterator();
        //act
        //assert
        assertFalse(iterator.hasNext());
    }

    @Test
    public void next_notIteratedOverEmptyMeal_returnNull() {
        //arrange
        Meal emptyMeal = new Meal("mealName");
        Iterator<Edible> iterator = emptyMeal.iterator();
        //act
        //assert
        assertNull(iterator.next());
    }

    @Test
    public void next_iteratedOver_returnNull() {
        //arrange
        Meal meal = new Meal("mealName");
        Iterator<Edible> iterator = meal.iterator();
        //act
        while (iterator.hasNext()) {
            iterator.next();
        }
        //assert
        assertNull(iterator.next());
    }

    @Test
    public void next_notIteratedOver_returnNextEdibleInMeal() {
        //arrange
        Food fish = new Food("fish");
        Meal meal = new Meal("fishMeal");
        meal.setEdible(fish, 1);

        //act
        Iterator<Edible> iterator = meal.iterator();

        //assert
        assertEquals(fish, iterator.next());
    }

    @Test
    public void next_nestedMeal_iterateOneLevelOnly() {
        //arrange
        Meal nestedMeal = new Meal("nested");
        int quantity = 1;
        List<Edible> edibleList = getFoodList();

        for (Edible edible : edibleList) {
            nestedMeal.setEdible(edible, quantity);
        }

        //act
        Iterator<Edible> iterator = nestedMeal.iterator();

        //assert
        for (Edible edible : edibleList) {
            assertEquals(edible, iterator.next());
        }
        assertFalse(iterator.hasNext());

    }


    private List<Edible> getFoodList() {
        Food egg = new Food("egg");
        Food bacon = new Food("bacon");
        Meal cereal = new Meal("cereal");
        Food milk = new Food("milk");
        Food cheerios = new Food("cheerios");
        cereal.setEdible(milk, 1);
        cereal.setEdible(cheerios, 1);

        List<Edible> list = new ArrayList<>();
        list.add(egg);
        list.add(bacon);
        list.add(cereal);

        return list;
    }

}
