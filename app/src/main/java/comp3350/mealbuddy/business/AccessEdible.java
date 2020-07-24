/****************************************
 * AccessAccount
 * Business Objects for managing edibles
 ****************************************/
package comp3350.mealbuddy.business;

import comp3350.mealbuddy.application.Main;
import comp3350.mealbuddy.application.Services;
import comp3350.mealbuddy.objects.consumables.Edible;
import comp3350.mealbuddy.persistence.DataAccess;


public class AccessEdible {
    private DataAccess DAS;

    /*
     * Constructor
     * Creates an object representing edibles in the database.
     */
    public AccessEdible(){
        DAS = Services.getDataAccess(Main.DATABASE_NAME);
    }


    /*
     * addEdible
     * Add an edible to the database.
     * Parameters:
     *     @param e - The edible to add to the database.
     */
    public void addEdible(Edible e){
        DAS.addEdible(e);
    }

    /*
     * updateEdible
     * Update an edible in the database.
     * Parameters:
     *     @param e - The edible to update.
     */
    public void updateEdible(Edible e){
        DAS.updateEdible(e);
    }


    /*
     * removeEdible
     * Remove an edible from the database.
     * Parameters:
     *     @param e - The edible to remove.
     */
    public void removeEdible(Edible e){
        DAS.removeEdible(e);
    }

}