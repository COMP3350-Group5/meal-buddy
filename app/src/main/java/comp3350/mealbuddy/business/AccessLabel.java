/****************************************
 * AccessLabel
 * Business Objects for managing labels
 ****************************************/
package comp3350.mealbuddy.business;

import java.util.ArrayList;
import java.util.List;

import comp3350.mealbuddy.application.Main;
import comp3350.mealbuddy.application.Services;
import comp3350.mealbuddy.persistence.DataAccess;

public class AccessLabel {
    private DataAccess DAS;

    /*
     * Constructor
     * Creates an object representing exercises in the database.
     */
    public AccessLabel() {
        DAS = Services.getDataAccess(Main.DATABASE_NAME);
    }

    /*
     * addLabel
     * Add a label to the database.
     * Parameters:
     *     @param l - The label to be added.
     */
    public void addLabel(String l) {
        if (l == null)
            throw new NullPointerException("Label cannot be null");
        if (labelExists(l))
            throw new IllegalArgumentException("Label being added already exists in the DB.");
        DAS.addLabel(l);
    }

    /*
     * removeLabel
     * Remove a label from the database.
     * Parameters:
     *     @param l - The label to remove.
     */
    public void removeLabel(String l) {
        if (l == null)
            throw new NullPointerException("Label cannot be null");
        if (!labelExists(l))
            return; //not error squelching since this is idempotent operation
        DAS.removeLabel(l);
    }

    /*
     * updateLabel
     * Update a label in the database.
     * Parameters:
     *     @param oldLabel - The label to remove.
     *     @param newLabel - The label to be added.
     */
    public void updateLabel(String oldLabel, String newLabel) {
        if (oldLabel == null || newLabel == null)
            throw new NullPointerException("Labels cannot be null");
        if (!labelExists(oldLabel))
            throw new IllegalArgumentException("Label being updated doesn't exist in the DB.");
        if (!oldLabel.equals(newLabel) && labelExists(newLabel))
            throw new IllegalArgumentException("Label already exists. Label cannot be updated");
        DAS.updateLabel(oldLabel, newLabel);
    }

    /*
     * getLabels
     * Get all the labels from the database
     */
    public ArrayList<String> getLabels() {
        return DAS.getLabels();
    }

    /*
     * getLabels
     * Return - true if label is in db
     */
    public boolean labelExists(String label) {
        return getLabel(label) != null;
    }

    /*
     * getEdible
     * returns a edible from the string name
     * Return:
     *     the edible or null.
     */
    private String getLabel(String name) {
        if (name == null)
            throw new NullPointerException("Label name can't be null");
        List<String> l = getLabels();
        for (String in : l) {
            if (in.equals(name))
                return in;
        }
        return null;
    }
}