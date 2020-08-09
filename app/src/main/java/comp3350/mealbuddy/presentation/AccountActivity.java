/****************************************
 * AccountActivity
 * the account page
 ****************************************/
package comp3350.mealbuddy.presentation;

import android.app.Dialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import comp3350.mealbuddy.R;
import comp3350.mealbuddy.business.AccessAccount;
import comp3350.mealbuddy.objects.UserInfo;

public class AccountActivity extends AppCompatActivity {

    private AccessAccount accessAccount;
    private String username;
    private Dialog dialog;
    private EditText etNum;
    private EditText etText;
    private Spinner spnActivityLevel;
    private TextView userInfoTxt;
    private TextView tvSpnTitle;

    /*
     * onCreate
     * called when the activity is initially created
     * Parameters:
     *     @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        dialog = new Dialog(this);

        FloatingActionButton fabEdit = findViewById(R.id.fabEdit);

        //get the passed values from the previous activity
        accessAccount = new AccessAccount();
        int dayOfYear = this.getIntent().getIntExtra("dayOfYear", -1);
        username = this.getIntent().getStringExtra("username");

        userInfoTxt = findViewById(R.id.txtUserInfo);

        // display the user info
        updateUserInfo();

        //Add Edit Button
        fabEdit = findViewById(R.id.fabEdit);
        fabEdit.setOnClickListener((view) -> showPopUp());


        Button setRecommendedGoals = findViewById(R.id.logOut);
        setRecommendedGoals.setOnClickListener((view) -> ChangeActivityHelper.changeActivity(AccountActivity.this, HomeActivity.class));
    }

    /*
     * updateUserInfo
     * updates the user info
     */
    private void updateUserInfo() {
        userInfoTxt.setText(accessAccount.getUserInfo(username).toString());
    }

    /*
     * showPopup
     * Show the popup to edit info
     */
    public void showPopUp() {
        dialog.setContentView(R.layout.pop_up_account);
        etNum = dialog.findViewById(R.id.etNumber);
        etText = dialog.findViewById(R.id.etText);
        spnActivityLevel = dialog.findViewById(R.id.spnActivityLevel);
        tvSpnTitle = dialog.findViewById(R.id.tvSpnTitle);

        Button btnUpdate = dialog.findViewById(R.id.btnUpdate);
        Spinner spinner = dialog.findViewById(R.id.spnUserInfo);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0)
                    showName();
                else if (i == 1)
                    showWeight();
                else
                    showActivityLevel();
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
                return;
            }
        });

        btnUpdate.setOnClickListener((view) -> {
            String spnString = spinner.getSelectedItem().toString();
            if (spnString.equals("Activity Level"))
                updateActivityLevel();
            else if (spnString.equals("Weight"))
                updateWeight();
            else
                updateName();
        });

        dialog.show();
    }

    /*
     * hidePopup
     * hide pop up and updates the user info
     */
    private void hidePopUp() {
        dialog.hide();
        updateUserInfo();
    }

    /*
     * updateWeight
     * updates the weight
     */
    private void updateWeight() {
        if (TextUtils.isEmpty(etNum.getText())) {
            etNum.setError("Weight required");
        } else {
            UserInfo newUserInfo = accessAccount.getUserInfo(username);
            newUserInfo.weight = Double.parseDouble(etNum.getText().toString());
            accessAccount.updateUserInfo(username, newUserInfo);
            hidePopUp();
        }
    }

    /*
     * updateActivityLevel
     * updates the activity level
     */
    private void updateActivityLevel() {
        UserInfo newUserInfo = accessAccount.getUserInfo(username);
        String activityLevelValue = spnActivityLevel.getSelectedItem().toString();
        UserInfo.ActivityLevel ALT;
        if (activityLevelValue.equals("Low"))
            ALT = UserInfo.ActivityLevel.LOW;
        else if (activityLevelValue.equals("Medium"))
            ALT = UserInfo.ActivityLevel.MEDIUM;
        else
            ALT = UserInfo.ActivityLevel.HIGH;
        newUserInfo.activityLevel = ALT;
        accessAccount.updateUserInfo(username, newUserInfo);
        hidePopUp();
    }

    /*
     * updateName
     * updates the name
     */
    private void updateName() {
        if (TextUtils.isEmpty(etText.getText())) {
            etText.setError("Name required");
        } else {
            UserInfo newUserInfo = accessAccount.getUserInfo(username);
            newUserInfo.fullname = etText.getText().toString();
            accessAccount.updateUserInfo(username, newUserInfo);
            hidePopUp();
        }
    }

    /*
     * showName
     * shows the name
     */
    private void showName() {
        etNum.setVisibility(View.INVISIBLE);
        spnActivityLevel.setVisibility(View.INVISIBLE);
        etText.setVisibility(View.VISIBLE);
        tvSpnTitle.setVisibility(View.INVISIBLE);
        etText.setHint("Name");
    }

    /*
     * showWeight
     * shows the weight
     */
    private void showWeight() {
        etText.setVisibility(View.INVISIBLE);
        spnActivityLevel.setVisibility(View.INVISIBLE);
        tvSpnTitle.setVisibility(View.INVISIBLE);
        etNum.setVisibility(View.VISIBLE);
        etNum.setHint("Weight (lbs)");
    }

    /*
     * showWeight
     * shows the activity level
     */
    private void showActivityLevel() {
        etNum.setVisibility(View.INVISIBLE);
        etText.setVisibility(View.INVISIBLE);
        spnActivityLevel.setVisibility(View.VISIBLE);
        tvSpnTitle.setVisibility(View.VISIBLE);
    }

}