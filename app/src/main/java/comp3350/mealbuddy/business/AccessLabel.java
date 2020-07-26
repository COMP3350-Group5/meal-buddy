package comp3350.mealbuddy.business;

import comp3350.mealbuddy.application.Main;
import comp3350.mealbuddy.application.Services;
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
     * getLabels
     * Get all the labels from the database
     */
    public void getLabels() { DAS.getLabels(); }


}