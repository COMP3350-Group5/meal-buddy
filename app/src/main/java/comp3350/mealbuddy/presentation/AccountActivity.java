package comp3350.mealbuddy.presentation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import comp3350.mealbuddy.R;
import comp3350.mealbuddy.business.AccessAccount;

public class AccountActivity extends AppCompatActivity {

    private AccessAccount accessAccount;
    private int dayOfYear;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        //get the passed values from the previous activity
        accessAccount = new AccessAccount();
        dayOfYear = this.getIntent().getIntExtra("dayOfYear", -1);
        username = this.getIntent().getStringExtra("username");

        TextView userInfoTxt = findViewById(R.id.txtUserInfo);
        userInfoTxt.setText(accessAccount.getAccount(username).user.toString());
    }
}