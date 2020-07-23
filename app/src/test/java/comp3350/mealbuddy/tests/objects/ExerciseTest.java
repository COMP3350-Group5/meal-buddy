package comp3350.mealbuddy.tests.objects;

import org.junit.Assert;
import org.junit.Test;

import comp3350.mealbuddy.objects.Exercise;

public class ExerciseTest {

    @Test
    public void constructor_nullExerciseName_throwException() {
        // Arrange
        String exerciseName = null;

        //Act
        try {
            Exercise exercise = new Exercise(exerciseName, 1, Exercise.Intensity.High);
            Assert.fail();
        } catch (IllegalArgumentException e) {
            // Assert
            Assert.assertTrue(true);
        }

    }
}
