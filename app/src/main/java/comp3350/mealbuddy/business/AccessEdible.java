/****************************************
 * AccessAccount
 * Business Objects for managing edibles
 ****************************************/
package comp3350.mealbuddy.business;

import comp3350.mealbuddy.application.Main;
import comp3350.mealbuddy.application.Services;
import comp3350.mealbuddy.objects.consumables.Edible;
import comp3350.mealbuddy.persistence.DataAccessStub;

public class AccessEdible {
    private DataAccessStub DAS;
    private DataAccessStub.DatabaseType databaseType;

    /*
     * Constructor
     * Creates an object representing edibles in the database.
     */
    public AccessEdible(){
        DAS = Services.openDAS(Main.DATABASE_NAME);
        databaseType = DataAccessStub.DatabaseType.EDIBLES;
    }


    /*
     * addEdible
     * Add an edible to the database.
     * Parameters:
     *     @param e - The edible to add to the database.
     */
    public void addEdible(Edible e){
        DAS.addToDB(databaseType, e);
    }

    /*
     * updateEdible
     * Update an edible in the database.
     * Parameters:
     *     @param e - The edible to update.
     */
    public void updateEdible(Edible e){
        DAS.updateDB(databaseType, e);
    }


    /*
     * removeEdible
     * Remove an edible from the database.
     * Parameters:
     *     @param e - The edible to remove.
     */
    public void removeEdible(Edible e){
        DAS.removeFromDB(databaseType, e);
    }

}
