/****************************************
* AccessAccount
 * Business Objects for managing accounts
 ****************************************/
package comp3350.mealbuddy.business;

import comp3350.mealbuddy.application.Main;
import comp3350.mealbuddy.application.Services;
import comp3350.mealbuddy.objects.Account;
import comp3350.mealbuddy.objects.Day;
import comp3350.mealbuddy.objects.UserInfo;
import comp3350.mealbuddy.persistence.DataAccess;

public class AccessAccount {
    private DataAccess DAS;

    /*
     * Constructor
     * Create an object representing accounts in the database
     */
    public AccessAccount(){
        DAS = Services.getDataAccess(Main.DATABASE_NAME);
    }

    /*
     * addAccount
     * Add an account to the database.
     * Parameters:
     *     @param a - The account to be added.
     */
    public void addAccount(Account a){
        Account check = DAS.getAccount(a.user.username);
        if (check == null) {
            DAS.addAccount(a);
        }
    }

    /*
     * addAccount
     * Create a new account, and add it to the database.
     * Parameters:
     *     @param u - The user to be added.
     */
    public void addAccount(UserInfo u){
        addAccount(new Account(u));
    }

    /*
     * updateAccount
     * Update an account in the database.
     * Parameters:
     *     @param usernameToUpdate - The user to update.
     *     @param a - The account to update to.
     */
    public void updateAccount(String usernameToUpdate, Account a){
        DAS.updateAccount(usernameToUpdate, a);
    }

    /*
     * removeAccount
     * Remove an account from the database.
     * Parameters:
     *     @param a - The account to be removed.
     */
    public void removeAccount(String userName){
        DAS.removeAccount(userName);
    }

    /*
     * validateLogin
     * Validates if the username and password result in a successful login.
     * Parameters:
     *     @param username - The username for the user.
     *     @param password - The password for the user.
     * Return:
     *     The account if valid, null if not valid.
     */
    public Account validateLogin(String username, String password){
        Account acc = getAccount(username);
        if(acc != null && password.equals(acc.user.password))
            return acc;
        return null;
    }

    /*
     * getDay
     * Get a day object for an account
     * Parameters:
     *     @param a - The account to retrieve a day for
     *     @param day - The day to retrieve
     * Return:
     *     The day object requested.
     */
    public Day getDay(String userName, int day){
        if (!DAS.isDayTracked(userName, day))
            DAS.addDay(userName, day);
        return DAS.getDay(userName, day);
    }

    /*
     * getAccount
     * Get the account for a username.
     * Parameters:
     *     @param username - The username for the requested account
     * Return:
     *     The account for the given username.
     */
    public Account getAccount(String username) { return DAS.getAccount(username); }


}
