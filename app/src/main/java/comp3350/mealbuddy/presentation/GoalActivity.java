/****************************************
 * GoalActivity
 * Front end for showing goals
 ****************************************/
package comp3350.mealbuddy.presentation;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import comp3350.mealbuddy.R;
import comp3350.mealbuddy.business.AccessAccount;
import comp3350.mealbuddy.business.Calculator;
import comp3350.mealbuddy.objects.Day;

public class GoalActivity extends AppCompatActivity {

    private AccessAccount accessAccount;
    Dialog dialog;

    /*
     * onCreate
     * called when the activity is initially created
     * Parameters:
     *     @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goals);
        dialog = new Dialog(this);

        //get the passed values from the previous activity
        int dayOfYear = this.getIntent().getIntExtra("dayOfYear", -1);
        final String username = this.getIntent().getStringExtra("username");

        //Get the day, create a calculator
        AccessAccount accessAccount = new AccessAccount();
        final Day day = accessAccount.getDay(username, dayOfYear);
        Calculator calculator = new Calculator(day);

        //Update Goal Text
        TextView goalText = findViewById(R.id.txtGoalsTitle);
        goalText.setText("Goals for Day: " + day.dayOfYear);
        initializeCards();

        //Update Goal body
        TextView goalBody = findViewById(R.id.txtGoals);
        goalBody.setText(day.getGoalString());

        //Add Goal Button
        Button addGoal = findViewById(R.id.btnAddGoal);
        addGoal.setOnClickListener((view) -> {
            Intent intent = new Intent(GoalActivity.this, AddGoalActivity.class);
            intent.putExtra("dayOfYear", dayOfYear);
            intent.putExtra("username", username);
            showPopUp(username, dayOfYear);
        });


    }

    /*
     * initializeCards
     * initializes the UI cards
     */
    private void initializeCards(){
        initializeGoals();
    }

    /*
     * initializeGoals
     * initializes the the goal card
     */
    private void initializeGoals() {
        CardView goals = findViewById(R.id.cardGoals);
        RelativeLayout goalsLayout = (RelativeLayout) goals.getChildAt(0);
        TextView cardGoalsTitle = (TextView)  goalsLayout.getChildAt(0);
        cardGoalsTitle.setText("Goals for the Day");
    }

    public void showPopUp(String userName, int dayOfYear) {
        dialog.setContentView(R.layout.pop_up_goal);

        Button btnContinue = dialog.findViewById(R.id.btnContinue);
        btnContinue.setOnClickListener((view) -> {
            //
        });

        dialog.show();
    }

}
