package comp3350.mealbuddy.business;

import comp3350.mealbuddy.application.Main;
import comp3350.mealbuddy.application.Services;
import comp3350.mealbuddy.objects.Exercise;
import comp3350.mealbuddy.persistence.DataAccessStub;

public class AccessExercise {
    private DataAccessStub DAS;
    private DataAccessStub.Database_t database_t;

    public AccessExercise(){
        DAS = Services.openDAS(Main.DATABASE_NAME);
        database_t = DataAccessStub.Database_t.EXERCISES;
    }

    public void addExercise(Exercise e){
        DAS.addToDB(database_t, e);
    }

    public void updateExercise(Exercise e){
        DAS.updateDB(database_t, e);
    }

    public void removeExercise(Exercise e){
        DAS.removeFromDB(database_t, e);
    }

}
