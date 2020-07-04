package comp3350.mealbuddy.business;

import comp3350.mealbuddy.application.Main;
import comp3350.mealbuddy.application.Services;
import comp3350.mealbuddy.objects.Edible;
import comp3350.mealbuddy.persistence.DataAccessStub;

public class AccessEdible {
    private DataAccessStub DAS;
    private DataAccessStub.Database_t database_t;

    public AccessEdible(){
        DAS = Services.openDAS(Main.DATABASE_NAME);
        database_t = DataAccessStub.Database_t.EDIBLES;
    }

    public void AddEdible(Edible e){
        DAS.addToDB(database_t, e);
    }

    public void updateEdible(Edible e){
        DAS.updateDB(database_t, e);
    }

    public void removeEdible(Edible e){
        DAS.removeFromDB(database_t, e);
    }

}
