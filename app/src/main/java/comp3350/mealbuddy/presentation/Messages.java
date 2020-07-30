/****************************************
 * Messages
 * deal with messages
 ****************************************/
package comp3350.mealbuddy.presentation;

import android.app.Activity;
import android.app.AlertDialog;

public class Messages {
    /*
     * warning
     * used to display warnings
     * Parameters:
     *      @param owner - the owner activity
     *      @param message - the message to show
     */
    public static void warning(Activity owner, String message) {
        AlertDialog alertDialog = new AlertDialog.Builder(owner).create();
        alertDialog.setTitle("Warning");
        alertDialog.setMessage(message);
        alertDialog.show();
    }
}