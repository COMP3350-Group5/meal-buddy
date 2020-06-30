package comp3350.mealbuddy.tests.business;

import org.junit.Assert;
import org.junit.Test;

import comp3350.mealbuddy.business.QuantityTracker;
import comp3350.mealbuddy.business.RatioTracker;
import comp3350.mealbuddy.business.Trackable;
import comp3350.mealbuddy.persistence.FoodDb;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class RatioTrackerTest {


    @Test
    public void test_constructor_goalAchievedNoVariance_throwException() {
        Trackable trackable = new RatioTracker(50, 0, "Fat", 100, new FoodDb(50));
        assertTrue(trackable.goalAchieved());
    }


}
