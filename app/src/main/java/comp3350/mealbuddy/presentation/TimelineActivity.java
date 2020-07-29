/****************************************
 * TimelineActivity
 * front end for showing a day
 ****************************************/
package comp3350.mealbuddy.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Calendar;

import comp3350.mealbuddy.R;
import comp3350.mealbuddy.business.AccessAccount;
import comp3350.mealbuddy.business.Calculator;
import comp3350.mealbuddy.objects.Day;


public class TimelineActivity extends AppCompatActivity {

    private AccessAccount accessAccount;
    private Day day;
    private Calculator calculator;
    private String username;

    /*
     * onCreate
     * called when the activity is initially created
     * Parameters:
     *     @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);

        //get username from previous activity
        username = this.getIntent().getStringExtra("username");

        //get the day to display
        Calendar calendar = Calendar.getInstance();
        accessAccount = new AccessAccount();
        day = accessAccount.getDay(username, calendar.get(Calendar.DAY_OF_YEAR));
        System.err.println(day.dayOfYear);
        calculator = new Calculator(day);

        //update the timeline text
        TextView timelineText = findViewById(R.id.txtTimeline);
        timelineText.setText("Day: " + day.dayOfYear);
        initializeCards();
        // the "addFood" button to go to the AddFoodActivity
        FloatingActionButton addFood = findViewById(R.id.btnAddFood);
        addFood.setOnClickListener((view) -> {
                Intent intent = new Intent(TimelineActivity.this, SearchFoodActivity.class);
                intent.putExtra("dayOfYear", day.dayOfYear);
                intent.putExtra("username", username);
                TimelineActivity.this.startActivity(intent);
        });
    }

    /*
     * initializeCards
     * initializes the UI cards
     */
    private void initializeCards() {
        initializeBreakfast();
        initializeLunch();
        initializeDinner();
        initializeSnacks();
        initializeTotals();
        initializeExercise();
    }

    /*
     * initializeSnacks
     * initializes the the snack card UI
     */
    private void initializeSnacks() {
        CardView snacks = findViewById(R.id.cardSnacks);
        RelativeLayout snacksLayout = (RelativeLayout) snacks.getChildAt(0);
        TextView cardSnacksTitle = (TextView) snacksLayout.getChildAt(0);
        cardSnacksTitle.setText("Snacks");
        TextView txtSnacks = (TextView) snacksLayout.getChildAt(1);
        txtSnacks.setText(day.getMealString(Day.MealTimeType.SNACK));
        TextView txtSnackCals = (TextView) snacksLayout.getChildAt(2);
        txtSnackCals.setText(calculator.getMealTimeCalories(Day.MealTimeType.SNACK) + " Cals");
    }

    /*
     * initializeDinner
     * initializes the dinner card UI
     */
    private void initializeDinner() {
        CardView dinner = findViewById(R.id.cardDinner);
        RelativeLayout dinnerLayout = (RelativeLayout) dinner.getChildAt(0);
        TextView cardDinnerTitle = (TextView) dinnerLayout.getChildAt(0);
        cardDinnerTitle.setText("Dinner");
        TextView txtDinner = (TextView) dinnerLayout.getChildAt(1);
        txtDinner.setText(day.getMealString(Day.MealTimeType.DINNER));
        TextView txtDinnerCals = (TextView) dinnerLayout.getChildAt(2);
        txtDinnerCals.setText(calculator.getMealTimeCalories(Day.MealTimeType.DINNER) + " Cals");
    }

    /*
     * initializeLunch
     * initializes the lunch card UI
     */
    private void initializeLunch() {
        CardView lunch = findViewById(R.id.cardLunch);
        RelativeLayout lunchLayout = (RelativeLayout) lunch.getChildAt(0);
        TextView cardLunchTitle = (TextView) lunchLayout.getChildAt(0);
        cardLunchTitle.setText("Lunch");
        TextView txtLunch = (TextView) lunchLayout.getChildAt(1);
        txtLunch.setText(day.getMealString(Day.MealTimeType.LUNCH));
        TextView txtLunchCals = (TextView) lunchLayout.getChildAt(2);
        txtLunchCals.setText(calculator.getMealTimeCalories(Day.MealTimeType.LUNCH) + " Cals");
    }

    /*
     * initializeBreakfast
     * initializes the breakfast card UI
     */
    private void initializeBreakfast() {
        CardView breakfast = findViewById(R.id.cardBreakfast);
        RelativeLayout breakfastLayout = (RelativeLayout) breakfast.getChildAt(0);
        TextView cardBreakfastTitle = (TextView) breakfastLayout.getChildAt(0);
        cardBreakfastTitle.setText("Breakfast");
        TextView txtBreakfast = (TextView) breakfastLayout.getChildAt(1);
        txtBreakfast.setText(day.getMealString(Day.MealTimeType.BREAKFAST));
        TextView txtBreakfastCals = (TextView) breakfastLayout.getChildAt(2);
        txtBreakfastCals.setText(calculator.getMealTimeCalories(Day.MealTimeType.BREAKFAST) + " Cals");
    }

    /*
     * initializeTotals
     * initializes the totals
     */
    private void initializeTotals() {
        CardView totals = findViewById(R.id.cardTotals);
        RelativeLayout totalsLayout = (RelativeLayout) totals.getChildAt(0);
        TextView cardTotalsTitle = (TextView) totalsLayout.getChildAt(0);
        cardTotalsTitle.setText("Totals");
        TextView totalsCals = (TextView) totalsLayout.getChildAt(1);
        totalsCals.setText(calculator.getTotalCalories() + " Cals");
    }

    /*
     * initializeTotals
     * initializes the exercise card
     */
    private void initializeExercise() {
        CardView exercise = findViewById(R.id.cardExercise);
        RelativeLayout exerciseLayout = (RelativeLayout) exercise.getChildAt(0);
        TextView cardExerciseTitle = (TextView) exerciseLayout.getChildAt(0);
        cardExerciseTitle.setText("Exercise");
        TextView txtExercise = (TextView) exerciseLayout.getChildAt(1);
        txtExercise.setText(day.getExerciseString());
        TextView exerciseCals = (TextView) exerciseLayout.getChildAt(2);
        exerciseCals.setText(calculator.getTotalExerciseCalories(accessAccount.getAccount(username).user) + " Cals");
    }
}