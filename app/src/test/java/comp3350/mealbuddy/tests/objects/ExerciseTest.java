package comp3350.mealbuddy.tests.objects;

import org.junit.Assert;
import org.junit.Test;

import comp3350.mealbuddy.objects.Exercise;



public class ExerciseTest{
    public static final double DURATION_TESTVALUE = 10.00;
    public static final double ASSERT_DELTAPRECISION = 0.000000001;

    public void testExercise() {
        Exercise exercise = new Exercise("lunges");
        exercise.duration = 10.00;

        Assert.assertTrue("lunges".equals(exercise.name));
        Assert.assertEquals(DURATION_TESTVALUE, exercise.duration, ASSERT_DELTAPRECISION);
    }
}
