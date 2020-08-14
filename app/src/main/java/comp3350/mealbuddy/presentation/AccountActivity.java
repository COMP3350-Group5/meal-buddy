/****************************************
 * AccountActivity
 * the account page
 ****************************************/
package comp3350.mealbuddy.presentation;

import android.app.Dialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import comp3350.mealbuddy.R;
import comp3350.mealbuddy.business.AccessAccount;
import comp3350.mealbuddy.objects.UserInfo;

public class AccountActivity extends AppCompatActivity {

    private AccessAccount accessAccount;
    private String username;
    private int dayOfYear;
    private Dialog dialog;
    private EditText updatedWeight;
    private EditText updatedName;
    private FloatingActionButton btnEdit;
    private Spinner spnActivityLevel;
    private TextView userInfo;
    private TextView spinnerTitle;


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

        //get the passed values from the previous activity
        accessAccount = new AccessAccount();
        dayOfYear = this.getIntent().getIntExtra("dayOfYear", -1);
        username = this.getIntent().getStringExtra("username");
        userInfo = findViewById(R.id.txtUserInfo);

        // display the user info
        updateUserInfo();

        //Add Edit Button
        btnEdit = findViewById(R.id.fabEdit);
        btnEdit.setOnClickListener((view) -> showEditUserPopUp());

        Button logOutButton = findViewById(R.id.logOut);
        logOutButton.setOnClickListener((view) -> ChangeActivityHelper.changeActivity(AccountActivity.this, HomeActivity.class));

        BottomNavigationView nav = findViewById(R.id.bottom_navigation);
        Menu menu = nav.getMenu();
        menu.getItem(ChangeActivityHelper.ACCOUNT).setChecked(true);
        menu.getItem(ChangeActivityHelper.ACCOUNT).setCheckable(false);
    }

    /*
     * updateUserInfo
     * updates the user info
     */
    private void updateUserInfo() {
        userInfo.setText(accessAccount.getUserInfo(username).toString());
    }

    /*
     * showPopup
     * Show the popup to edit info
     */
    public void showEditUserPopUp() {
        dialog.setContentView(R.layout.pop_up_account);

        updatedWeight = dialog.findViewById(R.id.etNumber);
        updatedName = dialog.findViewById(R.id.etText);
        spnActivityLevel = dialog.findViewById(R.id.spnActivityLevel);
        spinnerTitle = dialog.findViewById(R.id.tvSpnTitle);

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
    private void hideEditInfoPopup() {
        dialog.hide();
        updateUserInfo();
    }

    /*
     * updateWeight
     * updates the weight
     */
    private void updateWeight() {
        if (TextUtils.isEmpty(updatedWeight.getText())) {
            updatedWeight.setError("Weight required");
        } else {
            UserInfo newUserInfo = accessAccount.getUserInfo(username);
            newUserInfo.weight = Double.parseDouble(updatedWeight.getText().toString());
            accessAccount.updateUserInfo(username, newUserInfo);
            hideEditInfoPopup();
        }
    }

    /*
     * updateActivityLevel
     * updates the activity level
     */
    private void updateActivityLevel() {
        UserInfo newUserInfo = accessAccount.getUserInfo(username);
        String activityLevelValue = spnActivityLevel.getSelectedItem().toString();
        UserInfo.ActivityLevel newActivityLevel;
        if (activityLevelValue.equals("Low"))
            newActivityLevel = UserInfo.ActivityLevel.LOW;
        else if (activityLevelValue.equals("Medium"))
            newActivityLevel = UserInfo.ActivityLevel.MEDIUM;
        else
            newActivityLevel = UserInfo.ActivityLevel.HIGH;
        newUserInfo.activityLevel = newActivityLevel;
        accessAccount.updateUserInfo(username, newUserInfo);
        hideEditInfoPopup();
    }

    /*
     * updateName
     * updates the name
     */
    private void updateName() {
        if (TextUtils.isEmpty(updatedName.getText())) {
            updatedName.setError("Name required");
        } else {
            UserInfo newUserInfo = accessAccount.getUserInfo(username);
            newUserInfo.fullname = updatedName.getText().toString();
            accessAccount.updateUserInfo(username, newUserInfo);
            hideEditInfoPopup();
        }
    }

    /*
     * showName
     * shows the name
     */
    private void showName() {
        updatedWeight.setVisibility(View.INVISIBLE);
        spnActivityLevel.setVisibility(View.INVISIBLE);
        updatedName.setVisibility(View.VISIBLE);
        spinnerTitle.setVisibility(View.INVISIBLE);
        updatedName.setHint("Name");
    }

    /*
     * showWeight
     * shows the weight
     */
    private void showWeight() {
        updatedName.setVisibility(View.INVISIBLE);
        spnActivityLevel.setVisibility(View.INVISIBLE);
        spinnerTitle.setVisibility(View.INVISIBLE);
        updatedWeight.setVisibility(View.VISIBLE);
        updatedWeight.setHint("Weight (lbs)");
    }

    /*
     * showWeight
     * shows the activity level
     */
    private void showActivityLevel() {
        updatedWeight.setVisibility(View.INVISIBLE);
        updatedName.setVisibility(View.INVISIBLE);
        spnActivityLevel.setVisibility(View.VISIBLE);
        spinnerTitle.setVisibility(View.VISIBLE);
    }

}