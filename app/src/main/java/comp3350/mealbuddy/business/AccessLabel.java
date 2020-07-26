package comp3350.mealbuddy.business;

import comp3350.mealbuddy.application.Main;
import comp3350.mealbuddy.application.Services;
import comp3350.mealbuddy.objects.Label;
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
     * addAccount
     * Add an account to the database.
     * Parameters:
     *     @param a - The account to be added.
     */
    public void addAccount(Account a){
        DAS.addLabel(l);
    }
}
