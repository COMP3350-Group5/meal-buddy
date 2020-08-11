package comp3350.mealbuddy.tests.business;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import comp3350.mealbuddy.application.Services;
import comp3350.mealbuddy.business.AccessEdible;
import comp3350.mealbuddy.business.AccessLabel;
import comp3350.mealbuddy.objects.consumables.Edible;
import comp3350.mealbuddy.objects.consumables.Food;
import comp3350.mealbuddy.objects.consumables.Meal;
import comp3350.mealbuddy.tests.persistence.DataAccessStub;

@SuppressWarnings("CatchMayIgnoreException")
public class AccessEdibleTest {

    private static final String EDIBLE_NAME = "testfood123123";
    private static final List<String> LABELS = Arrays.asList("labeltest123");
    private static AccessEdible accessEdible;
    private static AccessLabel accessLabel;
    private static Edible edible;

    @Before
    public void initAccessEdibleTest() {
        Services.createDataAccess(new DataAccessStub("Stub"));    //stub
        //Services.createDataAccess(Main.DATABASE_NAME);    //hsql
        accessEdible = new AccessEdible();
        accessLabel = new AccessLabel();
        for (String label : LABELS)
            accessLabel.addLabel(label);
        edible = new Food(EDIBLE_NAME, LABELS);
        accessEdible.addEdible(edible);
    }

    @Test
    public void edibleExists_avgCases_trueIfExistsInDb() {
        Assert.assertTrue(accessEdible.edibleExists(edible.name));
        Assert.assertFalse(accessEdible.edibleExists("FAKE NAME"));
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
    public void addEdible_avgCases_ediblesAdded() {
        Food food = new Food("food");
        Meal meal = new Meal("meal");
        accessEdible.addEdible(food);
        accessEdible.addEdible(meal);
        Assert.assertTrue(accessEdible.getEdibles().contains(food));
        Assert.assertTrue(accessEdible.getEdibles().contains(meal));
    }

    @Test
    public void addEdible_duplicateEdible_throwError() {
        Assert.assertNotNull(accessEdible.getEdible(EDIBLE_NAME));
        try {
            accessEdible.addEdible(edible);
            Assert.fail();
        } catch (IllegalArgumentException e) {
            Assert.assertTrue(true);
        }
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
    public void updateEdible_foodInDb_foodUpdated() {
        Food updatedFood = new Food("updatedFood");
        accessEdible.updateEdible(edible.name, updatedFood);
        Assert.assertEquals(updatedFood, accessEdible.getEdible(updatedFood.name));
    }

    @Test
    public void removeEdible_avgCases_ediblesRemoved() {
        Food food = new Food("food");
        Meal meal = new Meal("meal");
        accessEdible.addEdible(food);
        accessEdible.addEdible(meal);
        accessEdible.removeEdible(food.name);
        accessEdible.removeEdible(meal.name);
        Assert.assertFalse(accessEdible.getEdibles().contains(food));
        Assert.assertFalse(accessEdible.getEdibles().contains(meal));
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
    public void removeEdible_foodDoesntExist_nothingHappens() {
        accessEdible.removeEdible("edible name that doesnt exist");
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

    @Test
    public void getEdible_foodInDb_returnFood() {
        Assert.assertEquals(edible, accessEdible.getEdible(edible.name));
    }

    @Test
    public void getEdible_foodNotDb_nullReturned() {
        Assert.assertNull(accessEdible.getEdible("Name Not In DB"));
    }

    @Test
    public void getEdibles_foodNotDb_emptyListReturned() {
        List<Edible> ediblesInDb = accessEdible.getEdibles();
        for (Edible e : ediblesInDb) {
            accessEdible.removeEdible(e.name);
        }
        Assert.assertEquals(0, accessEdible.getEdibles().size());
    }

    @Test
    public void getEdibles_foodIn_allFoodReturned() {
        Food food = new Food("food");
        Meal meal = new Meal("meal");
        accessEdible.addEdible(food);
        accessEdible.addEdible(meal);
        Assert.assertTrue(accessEdible.getEdibles().contains(food));
        Assert.assertTrue(accessEdible.getEdibles().contains(meal));
        Assert.assertTrue(accessEdible.getEdibles().contains(edible));
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
        Services.closeDAS();
    }

}
