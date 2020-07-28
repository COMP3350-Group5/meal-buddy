/****************************************
 * GoalActivity
 * Front end for showing goals
 ****************************************/
package comp3350.mealbuddy.presentation;

import android.os.Bundle;
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

        //get the passed values from the previous activity
        int dayOfYear = this.getIntent().getIntExtra("dayOfYear", -1);
        final String username = this.getIntent().getStringExtra("username");

        //Get the day, create a calculator
        AccessAccount accessAccount = new AccessAccount();
        final Day day = accessAccount.getDay(username, dayOfYear);
        Calculator calculator = new Calculator(day);

        //Update Goal Text
        TextView goalText = findViewById(R.id.txtGoal);
        goalText.setText("Goals for Day: " + day.dayOfYear);
        initializeCards();

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

}
