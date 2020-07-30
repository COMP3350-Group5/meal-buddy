package comp3350.mealbuddy.tests.business;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import comp3350.mealbuddy.application.Main;
import comp3350.mealbuddy.application.Services;
import comp3350.mealbuddy.business.AccessEdible;
import comp3350.mealbuddy.business.AccessLabel;
import comp3350.mealbuddy.objects.consumables.Edible;
import comp3350.mealbuddy.objects.consumables.Food;

public class AccessEdibleTest {

    private static AccessEdible accessEdible;
    private static AccessLabel accessLabel;

    private static Edible edible;
    private static final String EDIBLE_NAME = "testfood123123";
    private static final List<String> LABELS = Arrays.asList("labeltest123");


    @Before
    public void initAccessEdibleTest() {
        Services.initializeDB(Main.DATABASE_NAME);
        accessEdible = new AccessEdible();
        accessLabel = new AccessLabel();
        for (String label : LABELS)
            accessLabel.addLabel(label);
        edible = new Food(EDIBLE_NAME, LABELS);
        accessEdible.addEdible(edible);
    }

    @Test
    public void addEdible_nullEdible_throwException() {
        try {
            accessEdible.addEdible(null);
            Assert.fail();
        } catch (NullPointerException npe) {
            Assert.assertTrue(true);
        }
    }

    @Test
    public void addEdible_duplicateEdible_dontAdd() {
        Assert.assertNotNull(accessEdible.getEdible(EDIBLE_NAME));
        for (int x = 0; x < 100; x++)
            accessEdible.addEdible(edible);
        Assert.assertNotNull(accessEdible.getEdible(EDIBLE_NAME));
    }

    @Test
    public void updateEdible_nullFoodString_throwException() {
        try {
            accessEdible.updateEdible(null, edible);
            Assert.fail();
        } catch (NullPointerException npe) {
            Assert.assertTrue(true);
        }
    }

    @Test
    public void updateEdible_nullFood_throwException() {
        try {
            accessEdible.updateEdible(EDIBLE_NAME, null);
            Assert.fail();
        } catch (NullPointerException npe) {
            Assert.assertTrue(true);
        }
    }

    @Test
    public void updateEdible_foodDoesntExist_throwException() {
        try {
            accessEdible.updateEdible("edible name that doesnt exist", edible);
            Assert.fail();
        } catch (IllegalArgumentException npe) {
            Assert.assertTrue(true);
        }
    }

    @Test
    public void removeEdible_nullFood_throwException() {
        try {
            accessEdible.removeEdible(null);
            Assert.fail();
        } catch (NullPointerException npe) {
            Assert.assertTrue(true);
        }
    }

    @Test
    public void removeEdible_foodDoesntExist_throwException() {
        try {
            accessEdible.removeEdible("edible name that doesnt exist");
            Assert.fail();
        } catch (IllegalArgumentException npe) {
            Assert.assertTrue(true);
        }
    }

    @Test
    public void getEdible_nullName_throwException() {
        try {
            accessEdible.getEdible(null);
            Assert.fail();
        } catch (NullPointerException npe) {
            Assert.assertTrue(true);
        }
    }

    @After
    public void clean() {
        //remove the food and labels if they still exist
        try {
            accessEdible.removeEdible(EDIBLE_NAME);
        } catch (IllegalArgumentException iae) {
        }
        try {
            for (String label : LABELS)
                accessLabel.removeLabel(label);
        } catch (IllegalArgumentException iae) {
        }
    }

}
