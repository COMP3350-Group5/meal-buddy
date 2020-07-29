package comp3350.mealbuddy.business;

import java.util.List;

import comp3350.mealbuddy.application.Main;
import comp3350.mealbuddy.application.Services;
import comp3350.mealbuddy.objects.consumables.Edible;
import comp3350.mealbuddy.persistence.DataAccess;

public class AccessLabel {
    private DataAccess DAS;

    /*
     * Constructor
     * Creates an object representing exercises in the database.
     */
    public AccessLabel(){
        DAS = Services.getDataAccess(Main.DATABASE_NAME);
    }

    /*
     * addLabel
     * Add a label to the database.
     * Parameters:
     *     @param l - The label to be added.
     */
    public void addLabel(String l){
        if (l == null)
            throw new NullPointerException("Label cannot be null");
        if (getLabel(l) != null)
            return;
        DAS.addLabel(l);
    }

    /*
     * removeLabel
     * Remove a label from the database.
     * Parameters:
     *     @param l - The label to remove.
     */
    public void removeLabel(String l) { DAS.removeLabel(l); }

    /*
     * updateLabel
     * Update a label in the database.
     * Parameters:
     *     @param oldLabel - The label to remove.
     *     @param newLabel - The label to be added.
     */
    public void updateLabel(String oldLabel, String newLabel) { DAS.updateLabel(oldLabel, newLabel); }

    /*
     * getLabels
     * Get all the labels from the database
     */
    public List<String> getLabels() { return DAS.getLabels(); }

    /*
     * getEdible
     * returns a edible from the string name
     * Return:
     *     the edible or null.
     */
    public String getLabel(String name){
        if (name == null)
            throw new NullPointerException("Label name can't be null");
        List<String> l = getLabels();
        for (String in : l){
            if (in.equals(name))
                return in;
        }
        return null;
    }
}