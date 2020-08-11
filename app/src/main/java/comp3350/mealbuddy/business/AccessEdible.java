/****************************************
 * AccessEdible
 * Business Objects for managing edibles
 ****************************************/
package comp3350.mealbuddy.business;

import java.util.List;

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
    public AccessEdible() {
        DAS = Services.getDataAccess(Main.DATABASE_NAME);
    }

    /*
     * addEdible
     * Add an edible to the database.
     * Parameters:
     *     @param e - The edible to add to the database.
     */
    public void addEdible(Edible e) {
        if (e == null)
            throw new NullPointerException("Edible can't be null");
        if (getEdible(e.name) != null)
            throw new IllegalArgumentException("Edible already exists");
        DAS.addEdible(e);
    }


    /*
     * updateEdible
     * Update an edible in the database.
     * Parameters:
     *     @param edibleToUpdate - The edible to update.
     *     @param e - The edible to update to.
     */
    public void updateEdible(String edibleToUpdate, Edible e) {
        if (edibleToUpdate == null)
            throw new NullPointerException("Edible name can't be null");
        if (e == null)
            throw new NullPointerException("Edible can't be null");
        if (getEdible(edibleToUpdate) == null)
            throw new IllegalArgumentException("Edible being updated doesn't exist");

        DAS.updateEdible(edibleToUpdate, e);
    }

    /*
     * removeEdible
     * Remove an edible from the database.
     * Parameters:
     *     @param e - The name of the edible to remove.
     */
    public void removeEdible(String e) {
        if (e == null)
            throw new NullPointerException("Edible name can't be null");
        DAS.removeEdible(e);
    }

    /*
     * getEdibles
     * returns a list of all food in the database
     * Return:
     *     the list of food.
     */
    public List<Edible> getEdibles() {
        return DAS.getEdibles();
    }

    /*
     * getEdible
     * returns a edible from the string name
     * Return:
     *     the edible or null.
     */
    public Edible getEdible(String name) {
        if (name == null)
            throw new NullPointerException("Edible name can't be null");
        List<Edible> edibleList = getEdibles();
        for (Edible edible : edibleList) {
            if (edible.name.equals(name))
                return edible;
        }
        return null;
    }


}
