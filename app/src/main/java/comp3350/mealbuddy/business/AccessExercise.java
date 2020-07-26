/****************************************
 * AccessEdible
 * Business Objects for managing exercises
 ****************************************/
package comp3350.mealbuddy.business;

import comp3350.mealbuddy.application.Main;
import comp3350.mealbuddy.application.Services;
import comp3350.mealbuddy.objects.Exercise;
import comp3350.mealbuddy.persistence.DataAccessStub;

public class AccessExercise {
    private DataAccessStub DAS;
    private DataAccessStub.DatabaseType databaseType;

    /*
     * Constructor
     * Creates an object representing exercises in the database.
     */
    public AccessExercise(){
        DAS = Services.openDAS(Main.DATABASE_NAME);
        databaseType = DataAccessStub.DatabaseType.EXERCISES;
    }

    /*
     * addEdible
     * Add an exercise to the database.
     * Parameters:
     *     @param e - The exercise to add to the database.
     */
    public void addExercise(Exercise e){
        DAS.addToDB(databaseType, e);
    }

    /*
     * updateEdible
     * Update an exercise in the database.
     * Parameters:
     *     @param e - The exercise to update.
     */
    public void updateExercise(Exercise e){
        DAS.updateDB(databaseType, e);
    }

    /*
     * removeEdible
     * Remove an exercise from the database.
     * Parameters:
     *     @param e - The exercise to remove.
     */
    public void removeExercise(Exercise e){
        DAS.removeFromDB(databaseType, e);
    }

}
