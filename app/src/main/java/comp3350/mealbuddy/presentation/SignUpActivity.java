/****************************************
 * SignUPActivity
 * front end for signing up. Iteration 2 will expand on this.
 ****************************************/
package comp3350.mealbuddy.presentation;

import comp3350.mealbuddy.R;
import comp3350.mealbuddy.business.AccessAccount;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class SignUpActivity extends AppCompatActivity {
    private AccessAccount accessAccount = new AccessAccount();
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

        EditText username = findViewById(R.id.etUsername);
        EditText password = findViewById(R.id.etPassword);
        EditText fullName = findViewById(R.id.etFullName);
        EditText age = findViewById(R.id.etAge);
        EditText height = findViewById(R.id.etHeight);
        EditText weight = findViewById(R.id.etWeight);
        Spinner activityLevel = findViewById(R.id.spnActivityLevel);
        Spinner sex = findViewById(R.id.spnSex);
        Button createAccount = findViewById(R.id.btnCreateAccount);

        //link the on click listeners
        createAccount.setOnClickListener((view) -> {
            Intent intent = new Intent(SignUpActivity.this, TimelineActivity.class);
            SignUpActivity.this.startActivity(intent);
        });
    }
}