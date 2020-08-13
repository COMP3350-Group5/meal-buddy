/****************************************
 * SignUPActivity
 * front end for signing up. Iteration 2 will expand on this.
 ****************************************/
package comp3350.mealbuddy.presentation;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import comp3350.mealbuddy.R;
import comp3350.mealbuddy.business.AccessAccount;
import comp3350.mealbuddy.objects.UserInfo;

public class SignUpActivity extends AppCompatActivity {
    private AccessAccount accessAccount;

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
        accessAccount = new AccessAccount();

        //initialize all the variables with the correct components
        EditText username = findViewById(R.id.etUsername);
        EditText password = findViewById(R.id.etPassword);
        EditText fullName = findViewById(R.id.etFullName);
        EditText age = findViewById(R.id.etAge);
        EditText height = findViewById(R.id.etHeight);
        EditText weight = findViewById(R.id.etWeight);
        Button createAccount = findViewById(R.id.btnCreateAccount);

        //parse the users sex
        Spinner sex = findViewById(R.id.spnSex);
        String sexValue = sex.getSelectedItem().toString();
        UserInfo.Sex ST = sexValue.equals("Male") ? UserInfo.Sex.MALE : UserInfo.Sex.FEMALE;
        //parse the activity level
        Spinner activityLevel = findViewById(R.id.spnActivityLevel);
        String activityLevelValue = activityLevel.getSelectedItem().toString();
        UserInfo.ActivityLevel ALT;
        if (activityLevelValue.equals("Low"))
            ALT = UserInfo.ActivityLevel.LOW;
        else if (activityLevelValue.equals("Medium"))
            ALT = UserInfo.ActivityLevel.MEDIUM;
        else
            ALT = UserInfo.ActivityLevel.HIGH;

        //link the on click listeners
        createAccount.setOnClickListener((view) -> {
            if (validateInput(username, password, fullName, age, height, weight)) {
                UserInfo userInfo = new UserInfo(fullName.getText().toString(),
                        username.getText().toString(),
                        password.getText().toString(),
                        Double.parseDouble(height.getText().toString()),
                        Double.parseDouble(weight.getText().toString()),
                        ALT,
                        ST,
                        Integer.parseInt(age.getText().toString()));
                accessAccount.addAccount(userInfo);

                ChangeActivityHelper.changeActivity(SignUpActivity.this, TimelineActivity.class, userInfo.username);
            }
        });
    }

    private boolean validateInput(EditText username, EditText password, EditText fullName, EditText age, EditText height, EditText weight) {
        boolean inputsValid = true;

        if (TextUtils.isEmpty(username.getText())) {
            username.setError("Username is required");
            inputsValid = false;
        }

        if (accessAccount.accountExists(username.getText().toString())) {
            username.setError("This username is taken select another one");
            inputsValid = false;
        }

        if (TextUtils.isEmpty(password.getText())) {
            password.setError("Password is required");
            inputsValid = false;
        }

        if (TextUtils.isEmpty(fullName.getText())) {
            fullName.setError("Full Name is required");
            inputsValid = false;
        }
        if (TextUtils.isEmpty(age.getText())) {
            age.setError("Age is required");
            inputsValid = false;
        }

        if (TextUtils.isEmpty(height.getText())) {
            height.setError("Height is required");
            inputsValid = false;
        }

        if (TextUtils.isEmpty(weight.getText())) {
            weight.setError("Weight is required");
            inputsValid = false;
        }

        return inputsValid;
    }
}