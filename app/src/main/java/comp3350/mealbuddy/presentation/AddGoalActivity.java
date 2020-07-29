package comp3350.mealbuddy.presentation;

import android.app.Dialog;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import comp3350.mealbuddy.R;

public class AddGoalActivity extends AppCompatActivity {

    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_food_activity);
        dialog = new Dialog(this);

        //get the passed values from the previous activity
        int dayOfYear = this.getIntent().getIntExtra("dayOfYear", -1);
        final String username = this.getIntent().getStringExtra("username");

        //Get the elements from the UI
    }

}
