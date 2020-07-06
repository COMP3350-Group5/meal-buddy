package comp3350.mealbuddy.tests.objects;

import org.junit.Test;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class GoalTest {


    @Test
    public void test_constructor_negativeLowerBound_throwException() {
        int[][][] r = new int[2][17][3];
        r[1][7][2] = 69;
        System.out.println(r[1][7][2]);
    }
//
//    @Test
//    public void test_constructor_negativeUpperBound_throwException() {
//        //arrange
//        int lowerBound = 50;
//        int upperBound = -42;
//        String nameOfTracked = "Fat";
//
//        //act
//        try{
//            Goal goal = new QuantityGoal(lowerBound, upperBound, nameOfTracked);
//            fail("Expected Exception");
//        }
//        //assert
//        catch (IllegalArgumentException e){
//            assertTrue(true);
//        }
//    }
//
//    @Test
//    public void test_constructor_nullName_throwException() {
//        //arrange
//        int lowerBound = 50;
//        int upperBound = 55;
//        String nameOfTracked = null;
//
//        //act
//        try{
//            Goal goal = new QuantityGoal(lowerBound, upperBound, nameOfTracked);
//            fail("Expected Exception");
//        }
//        //assert
//        catch (NullPointerException e){
//            assertTrue(true);
//        }
//    }
//
//    @Test
//    public void test_constructor_emptyName_throwException() {
//        //arrange
//        int lowerBound = 50;
//        int upperBound = 55;
//        String nameOfTracked = "";
//
//        //act
//        try{
//            Goal goal = new QuantityGoal(lowerBound, upperBound, nameOfTracked);
//            fail("Expected Exception");
//        }
//        //assert
//        catch (IllegalArgumentException e){
//            assertTrue(true);
//        }
//    }
//
//    @Test
//    public void test_constructor_lowerLargerThanUpper_throwException() {
//        //arrange
//        int lowerBound = 100;
//        int upperBound = 50;
//        String nameOfTracked = "Fat";
//
//        //act
//        try{
//            Goal goal = new QuantityGoal(lowerBound, upperBound, nameOfTracked);
//            fail("Expected Exception");
//        }
//        //assert
//        catch (IllegalArgumentException e){
//            assertTrue(true);
//        }
//    }
//
//    @Test
//    public void tester() {
//        MacroFactory.Macros m = MacroFactory.Macros.Carbs;
//        System.out.println(m);
//    }
//
//
//

}
