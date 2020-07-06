package comp3350.mealbuddy.presentation;

import androidx.appcompat.app.AppCompatActivity;
import comp3350.mealbuddy.R;
import comp3350.mealbuddy.business.AccessAccount;
import comp3350.mealbuddy.objects.Account;
import comp3350.mealbuddy.objects.Day;

import android.os.Bundle;

public class AddFoodActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food);
        int dayOfYear = this.getIntent().getIntExtra("dayOfYear", -1);
        String username = this.getIntent().getStringExtra("username");
        AccessAccount accessAccount = new AccessAccount();
        Account account = accessAccount.getAccount(username);
        Day day = accessAccount.getDay(account, dayOfYear);
    }
}