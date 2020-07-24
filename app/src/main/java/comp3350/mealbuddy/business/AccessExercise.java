/****************************************
 * AccessEdible
 * Business Objects for managing exercises
 ****************************************/
package comp3350.mealbuddy.business;

import comp3350.mealbuddy.application.Main;
import comp3350.mealbuddy.application.Services;
import comp3350.mealbuddy.objects.Exercise;
import comp3350.mealbuddy.persistence.DataAccess;


public class AccessExercise {
    private DataAccess DAS;

    /*
     * Constructor
     * Creates an object representing exercises in the database.
     */
    public AccessExercise(){
        DAS = Services.getDataAccess(Main.DATABASE_NAME);
    }

    /*
     * addExercise
     * Add an exercise to the database.
     * Parameters:
     *     @param e - The exercise to add to the database.
     */
    public void addExercise(Exercise e){
        DAS.addExercise("", 0, e);
    }

    /*
     * updateExercise
     * Update an exercise in the database.
     * Parameters:
     *     @param e - The exercise to update.
     */
    public void updateExercise(Exercise e){
        DAS.updateExercise("", 0, e);
    }

    /*
     * removeExercise
     * Remove an exercise from the database.
     * Parameters:
     *     @param e - The exercise to remove.
     */
    public void removeExercise(Exercise e){
        DAS.removeExercise("", 0, e);
    }

}