/****************************************
 * AccessAccount
 * Business Objects for managing accounts
 ****************************************/
package comp3350.mealbuddy.business;

import java.util.List;

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
    public AccessAccount() {
        DAS = Services.getDataAccess(Main.DATABASE_NAME);
    }

    /*
     * addAccount
     * Add an account to the database. Also sets the default day for that account to an empty day
     * Parameters:
     *     @param userInfo - The user info to be added to a new account.
     */
    public void addAccount(UserInfo userInfo) {
        if (userInfo == null)
            throw new IllegalArgumentException("Account cannot be null");
        if (accountExists(userInfo.username))
            throw new IllegalArgumentException("Username already taken for this account");
        DAS.addAccount(userInfo);
    }

    /*
     * accountExists
     * checks if account exists in db
     * Returns: true if account exists
     */
    public boolean accountExists(String userName) {
        return !(getUserInfo(userName) == null);
    }

    /*
     * getUserInfo
     * Get the UserInfo for a username.
     * Parameters:
     *     @param username - The username for the requested account
     * Return:
     *     The UserInfo for the given username.
     */
    public UserInfo getUserInfo(String username) {
        return DAS.getUserInfo(username);
    }

    /*
     * updateUserInfo
     * Update an account in the database.
     * Parameters:
     *     @param usernameToUpdate - The user to update.
     *     @param userInfo - The info to update to.
     */
    public void updateUserInfo(String usernameToUpdate, UserInfo userInfo) {
        if (usernameToUpdate == null || getUserInfo(usernameToUpdate) == null)
            throw new NullPointerException("Username being updated doesn't exist in the database.");
        if (userInfo == null)
            throw new IllegalArgumentException("Account cannot be null");
        //if the account that is being updated is being updated with a username that already is in the db
        if (!userInfo.username.equals(usernameToUpdate) && getUserInfo(userInfo.username) != null)
            throw new IllegalArgumentException("Username already exists. Account cannot be updated");
        DAS.updateUserInfo(usernameToUpdate, userInfo);
    }

    /*
     * removeAccount
     * Remove an account from the database.
     * Parameters:
     *     @param a - The account to be removed.
     */
    public void removeAccount(String userName) {
        if (userName == null)
            throw new NullPointerException("Username being removed is null.");
        DAS.removeAccount(userName);
    }

    /*
     * validateLogin
     * Validates if the username and password result in a successful login.
     * Parameters:
     *     @param username - The username for the user.
     *     @param password - The password for the user.
     * Return:
     *     The UserInfo of account if valid, null if not valid.
     */
    public UserInfo validateLogin(String username, String password) {
        UserInfo userInfo = getUserInfo(username);
        if (userInfo != null && userInfo.password.equals(password))
            return userInfo;
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
    public Day getDay(String userName, int day) {
        if (!DAS.isDayTracked(userName, day)) {
            setDayToDefault(userName, day);
        }
        return DAS.getDay(userName, day);
    }

    /*
     * getDays
     * Get all the days in the account
     * Parameters:
     *     @param userName - The account to retrieve the days for
     * Return:
     *     The list of days
     */
    public List<Day> getDays(String userName) {
        if (userName == null)
            throw new NullPointerException("Username cannot be null");
        return DAS.getDays(userName);
    }


    /*
     * setDayToDefault
     * set the default value for the day specified. If default day does not
     * exist then create it
     * Parameters:
     *     @param userName - The account to retrieve a day for
     *     @param day - The day of year to set the default for
     */
    private void setDayToDefault(String userName, int day) {
        DAS.addDay(userName, day);
        if (!defaultDayExists(userName))
            DAS.addDay(userName, Account.DEFAULT_DAY_NUM);
        Day defaultDayCopy = DAS.getDay(userName, Account.DEFAULT_DAY_NUM);
        defaultDayCopy.dayOfYear = day;
        DAS.updateDay(userName, defaultDayCopy);
    }

    /*
     * defaultDayExists
     * Parameters
     *      @param userName - The username of the account to check
     * Returns: true if the default day is tracked for the account tied to the username
     */
    private boolean defaultDayExists(String userName) {
        return DAS.isDayTracked(userName, Account.DEFAULT_DAY_NUM);
    }

    /*
     * updateDay
     * Update a day for an account
     * Parameters:
     *     @param username - The account to update a day for
     *     @param day - The day to update
     */
    public void updateDay(String userName, Day day) {
        if (userName == null)
            throw new IllegalArgumentException("Username cannot be null");
        if (day == null)
            throw new IllegalArgumentException("Day cannot be null to update");
        if (!DAS.isDayTracked(userName, day.dayOfYear))
            DAS.addDay(userName, day.dayOfYear);
        DAS.updateDay(userName, day);
    }


}
