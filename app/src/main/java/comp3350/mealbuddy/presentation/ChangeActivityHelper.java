/****************************************
 * ChangeActivityHelper
 * helper for changing activities
 ****************************************/
package comp3350.mealbuddy.presentation;

import android.content.Context;
import android.content.Intent;

public class ChangeActivityHelper {

    public static final int TIMELINE = 0;
    public static final int GOALS = 1;
    public static final int STATS = 2;
    public static final int ACCOUNT = 3;
    /*
     * changeActivity
     * changes the activity to the destination class from the src context. passing the given parameters
     * Parameters:
     *      @param username - the username to be passed
     *      @param day - the day to be passed as day of year
     *      @param mealName - the meal to be passed to be updated
     */
    public static void changeActivity(Context src, Class dest, String username, int day, String mealName) {
        Intent intent = new Intent(src, dest);
        intent.putExtra("dayOfYear", day);
        intent.putExtra("username", username);
        intent.putExtra("mealName", mealName);
        src.startActivity(intent);
    }

    /*
     * changeActivity
     * changes the activity to the destination class from the src context. passing the given parameters
     * Parameters:
     *      @param username - the username to be passed
     *      @param day - the day to be passed as day of year
     */
    public static void changeActivity(Context src, Class dest, String username, int day) {
        Intent intent = new Intent(src, dest);
        intent.putExtra("dayOfYear", day);
        intent.putExtra("username", username);
        src.startActivity(intent);
    }

    /*
     * changeActivity
     * changes the activity to the destination class from the src context. passing the given parameters
     * Parameters:
     *      @param username - the username to be passed
     */
    public static void changeActivity(Context src, Class dest, String username) {
        Intent intent = new Intent(src, dest);
        intent.putExtra("username", username);
        src.startActivity(intent);
    }

    /*
     * changeActivity
     * changes the activity to the destination class from the src context.
     */
    public static void changeActivity(Context src, Class dest) {
        Intent intent = new Intent(src, dest);
        src.startActivity(intent);
    }
}
