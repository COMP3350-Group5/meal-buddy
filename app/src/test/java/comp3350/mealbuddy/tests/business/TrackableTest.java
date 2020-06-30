package comp3350.mealbuddy.tests.business;

import org.junit.Test;

import comp3350.mealbuddy.business.QuantityTracker;
import comp3350.mealbuddy.business.RatioTracker;
import comp3350.mealbuddy.business.Trackable;
import comp3350.mealbuddy.persistence.FoodDb;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class TrackableTest {


    @Test
    public void test_constructor_negativeAmount_throwException() {
        try{
            Trackable trackable = new QuantityTracker(-3, 50, "Fat", new FoodDb(5));
            fail("Expected Exception");
        }
        catch (IllegalArgumentException e){
        }
    }

    @Test
    public void test_constructor_negativeVarience_throwException() {
        try{
            Trackable trackable = new QuantityTracker(50, -42, "Fat", new FoodDb(5));
            fail("Expected Exception");
        }
        catch (IllegalArgumentException e){
        }
    }

    @Test
    public void test_constructor_nullName_throwException() {
        try{
            Trackable trackable = new QuantityTracker(50, 3, null, new FoodDb(5));
            fail("Expected Exception");
        }
        catch (NullPointerException e){
        }
    }

    @Test
    public void test_constructor_tooBigVariance_throwException() {
        try{
            Trackable trackable = new QuantityTracker(35, 40, "Fat", new FoodDb(5));
            fail("Expected Exception");
        }
        catch (IllegalArgumentException e){
        }
    }




}
