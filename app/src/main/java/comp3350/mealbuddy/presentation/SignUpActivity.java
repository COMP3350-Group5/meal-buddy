/****************************************
 * SignUPActivity
 * front end for signing up. Iteration 2 will expand on this.
 ****************************************/
package comp3350.mealbuddy.presentation;

import comp3350.mealbuddy.R;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class SignUpActivity extends AppCompatActivity {

    /*
     * onCreate
     * called when the activity is initially created
     * Parameters:
     *     @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
    }
}