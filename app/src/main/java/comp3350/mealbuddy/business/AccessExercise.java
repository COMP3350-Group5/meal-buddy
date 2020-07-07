package comp3350.mealbuddy.business;

import comp3350.mealbuddy.application.Main;
import comp3350.mealbuddy.application.Services;
import comp3350.mealbuddy.objects.Exercise;
import comp3350.mealbuddy.persistence.DataAccessStub;

public class AccessExercise {
    private DataAccessStub DAS;
    private DataAccessStub.DatabaseType database_type;

    public AccessExercise(){
        DAS = Services.openDAS(Main.DATABASE_NAME);
        databaseType = DataAccessStub.DatabaseType.EXERCISES;
    }

    public void addExercise(Exercise e){
        DAS.addToDB(databaseType, e);
    }

    public void updateExercise(Exercise e){
        DAS.updateDB(databaseType, e);
    }

    public void removeExercise(Exercise e){
        DAS.removeFromDB(database_type, e);
    }

}
