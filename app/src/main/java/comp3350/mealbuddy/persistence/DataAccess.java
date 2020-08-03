/****************************************
 * DataAccess
 * Interface for interacting with a data base
 * or data base stub
 ****************************************/

package comp3350.mealbuddy.persistence;

import java.util.List;

import comp3350.mealbuddy.objects.Account;
import comp3350.mealbuddy.objects.Day;
import comp3350.mealbuddy.objects.UserInfo;
import comp3350.mealbuddy.objects.consumables.Edible;

public interface DataAccess {

    /*
     * open
     * Opens the db for use
     * Parameters:
     *     @param name - The path to the db
     */
    void open(String string);

    /*
     * close
     * Commits changes to the db and closes it
     */
    void close();

    /*
     * addAccount
     * inserts an account to the db
     * Parameters:
     *     @param account - the account to insert
     */
    String addAccount(Account account);

    /*
     * updateUserInfo
     * Updates an account already present
     * in the db
     * Parameters:
     *     @param userNameToUpdate - the username of the account to update
     *     @param userInfo - the UserInfo holding the new info to update
     *                      the old info to
     */
    String updateUserInfo(String usernameToUpdate, UserInfo userInfo);

    /*
     * removeAccount
     * removes an account from the db
     * Parameters:
     *     @param userName - the username of the account to remove
     */
    String removeAccount(String userName);

    /*
     * getUserInfo
     * gets the UserInfo associated with the username.
     * returns null if not in db
     * Parameters:
     *     @param userName - username associated with the info to get
     */
    UserInfo getUserInfo(String username);

    /*
     * addEdible
     * Adds an edible to the db
     * Parameters:
     *     @param edible - the edible to add
     */
    String addEdible(Edible edible);

    /*
     * updateEdible
     * Updates an edible in the db
     * Parameters:
     *     @param edible - the edible to update
     */
    String updateEdible(String edibleToUpdate, Edible edible);

    /*
     * removeEdible
     * removes the edible from the db
     * Parameters:
     *     @param name - the name of the edible to remove
     */
    String removeEdible(String name);

    /*
     * getEdibles
     * gets all the edibles in the db
     */
    List<Edible> getEdibles();

    /*
     * addLabel
     * adds the label to the db
     * Parameters:
     *     @param label - the name label to add
     */
    String addLabel(String label);

    /*
     * updateLabel
     * updates a label in the db
     * Parameters:
     *     @param oldLabel - the name of the label to update
     *     @param newLabel - the name to update to
     */
    String updateLabel(String oldLabel, String newLabel);

    /*
     * removeLabel
     * removes a label from the db
     * Parameters:
     *     @param name - the name of the label to remove
     */
    String removeLabel(String label);

    /*
     * getLabels
     * gets all labels from the db
     */
    List<String> getLabels();

    /*
     * addDay
     * adds a new day to the db. Will always be an empty day
     * Parameters:
     *     @param userName - the account to add the day under
     *     @param dayOfYear - the day of year to add the day to
     */
    String addDay(String userName, int dayOfYear);

    /*
     * getDay
     * gets all days from the associated account username
     * Parameters:
     *     @param userName - the userName of the account
     */
    List<Day> getDays(String userName);

    /*
     * getDay
     * gets the day from the associated account username
     * Parameters:
     *     @param userName - the userName of the account
     *     @param dayOfYear - the day of the year to get
     */
    Day getDay(String account, int dayOfYear);

    /*
     * updateDay
     * updates a certain day in an account.  Updates all info associated with the day
     * including exercise, meals, and goals.  Note the dayOfYear in a day
     * can not be updated.
     * Parameters:
     *     @param userName - the userName of the account
     *     @param dayOfYear - the day to update
     */
    String updateDay(String userName, Day day);

    /*
     * isDayTracked
     * Check if a day is being tracked by the user
     * Parameters:
     *     @param userName - the userName of the account
     *     @param dayOfYear - the day of the year to check
     */
    boolean isDayTracked(String userName, int dayOfYear);
}
