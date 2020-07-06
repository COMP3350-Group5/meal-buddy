package comp3350.mealbuddy.business;

import comp3350.mealbuddy.application.Main;
import comp3350.mealbuddy.application.Services;
import comp3350.mealbuddy.objects.consumables.Edible;
import comp3350.mealbuddy.persistence.DataAccessStub;

public class AccessEdible {
    private DataAccessStub DAS;
    private DataAccessStub.DatabaseType databaseType;

    public AccessEdible(){
        DAS = Services.openDAS(Main.DATABASE_NAME);
        database_type = DataAccessStub.DatabaseType.EDIBLES;
    }

    public void addEdible(Edible e){
        DAS.addToDB(database_type, e);
    }

    public void updateEdible(Edible e){
        DAS.updateDB(database_type, e);
    }

    public void removeEdible(Edible e){
        DAS.removeFromDB(database_type, e);
    }

}
