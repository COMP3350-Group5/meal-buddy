package comp3350.mealbuddy.tests.business;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import comp3350.mealbuddy.application.Main;
import comp3350.mealbuddy.application.Services;
import comp3350.mealbuddy.business.AccessEdible;
import comp3350.mealbuddy.objects.consumables.Edible;
import comp3350.mealbuddy.objects.consumables.Food;

public class AccessEdibleTest {

    private static AccessEdible accessEdible;
    private static Edible edible;
    private static final String EDIBLE_NAME = "testfood123123";


    @Before
    public void initAccessEdibleTest(){
        Services.initializeDB(Main.DATABASE_NAME);
        accessEdible = new AccessEdible();
        edible = new Food(EDIBLE_NAME, Arrays.asList("labeltest123"));
        accessEdible.addEdible(edible);
    }

    @Test
    public void addEdible_nullEdible_dontAdd(){
        try {
            accessEdible.addEdible(null);
            Assert.fail();
        } catch (NullPointerException npe){
            Assert.assertTrue(true);
        }
    }

    @Test
    public void addEdible_duplicateEdible_dontAdd(){
        Assert.assertNotNull(accessEdible.getEdible(EDIBLE_NAME));
        for (int x = 0; x < 100; x++)
            accessEdible.addEdible(edible);
        Assert.assertNotNull(accessEdible.getEdible(EDIBLE_NAME));
    }
    
}
