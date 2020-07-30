/****************************************
 * TimelineActivity
 * front end for showing a day
 ****************************************/
package comp3350.mealbuddy.presentation;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.sql.Time;
import java.util.Calendar;

import comp3350.mealbuddy.R;
import comp3350.mealbuddy.business.AccessAccount;
import comp3350.mealbuddy.business.Calculator;
import comp3350.mealbuddy.objects.Day;
import comp3350.mealbuddy.objects.goals.Goal;


public class TimelineActivity extends AppCompatActivity {

    private Dialog dialog;
    private AccessAccount accessAccount;
    private Day day;
    private Calculator calculator;
    private String username;
    private int dayOfYear;

    private boolean isFabOpen = false;
    private FloatingActionButton fabFood;
    private FloatingActionButton fabExercise;

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
        dialog = new Dialog(this);
        //get username from previous activity
        Calendar calendar = Calendar.getInstance();
        dayOfYear = this.getIntent().getIntExtra("dayOfYear", calendar.get(Calendar.DAY_OF_YEAR));
        username = this.getIntent().getStringExtra("username");

        calendar.set(Calendar.DAY_OF_YEAR, dayOfYear);

        accessAccount = new AccessAccount();
        day = accessAccount.getDay(username, dayOfYear);


        calculator = new Calculator(day);

        //update the timeline text
        TextView timelineText = findViewById(R.id.txtTimeline);
        timelineText.setText("Day: " + day.dayOfYear);
        initializeCards();

        //set up the timeline button
        ImageView iv = findViewById(R.id.ivCalendar);
        iv.setOnClickListener((view) -> {
            dialog.setContentView(R.layout.pop_up_calendar);

            //get the calendar view
            CalendarView cv = dialog.findViewById(R.id.cvPopUp);
            cv.setDate(calendar.getTimeInMillis(), true, true);

            cv.setOnDateChangeListener((calendarView, y, m, d) -> {
                Calendar newCalendar = Calendar.getInstance();
                newCalendar.set(y,m,d);
                int newDayOfYear = newCalendar.get(Calendar.DAY_OF_YEAR);
                Intent intent = new Intent(TimelineActivity.this, TimelineActivity.class);
                intent.putExtra("dayOfYear", newDayOfYear);
                intent.putExtra("username", username);
                TimelineActivity.this.startActivity(intent);

            });

            dialog.show();
        });

        // the "addFood" button to go to the AddFoodActivity
        FloatingActionButton fab = findViewById(R.id.fabAdd);
        fabFood = findViewById(R.id.fabFood);
        fabExercise = findViewById(R.id.fabExercise);
        fabFood.setVisibility(View.INVISIBLE);
        fabExercise.setVisibility(View.INVISIBLE);

        fab.setOnClickListener((view) -> {
            if(!isFabOpen) showFABMenu();
            else closeFABMenu();
        });

        fabFood.setOnClickListener((view) -> {
            ChangeActivityHelper.changeActivity(TimelineActivity.this, SearchFoodActivity.class, username, dayOfYear);
        });

        fabExercise.setOnClickListener((view) -> {
            ChangeActivityHelper.changeActivity(TimelineActivity.this, AddExerciseActivity.class, username, dayOfYear);
        });

        BottomNavigationView nav = findViewById(R.id.bottom_navigation);
        nav.setOnNavigationItemSelectedListener((MenuItem item) -> {
            Intent intent;
            switch (item.getItemId()) {
                case R.id.action_goals:
                    ChangeActivityHelper.changeActivity(TimelineActivity.this, GoalActivity.class, username, dayOfYear);
                    break;
                case R.id.action_account:
                    ChangeActivityHelper.changeActivity(TimelineActivity.this, AccountActivity.class, username, dayOfYear);
                    break;
                case R.id.action_timeline:
                    // do nothing, this is where we are
                    break;
            }
            return true;
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
        TextView totalsEatenCals = (TextView) totalsLayout.getChildAt(1);
        totalsEatenCals.setText(calculator.getTotalCalories() + " Cals eaten");
        TextView netCals = (TextView) totalsLayout.getChildAt(2);
        netCals.setText(calculator.getNetCalories(accessAccount.getAccount(username).user) + " net Cals");
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

    private void showFABMenu(){
        isFabOpen=true;
        fabFood.setVisibility(View.VISIBLE);
        fabExercise.setVisibility(View.VISIBLE);
        fabFood.animate().translationY(-getResources().getDimension(R.dimen.standard_65));
        fabExercise.animate().translationY(-getResources().getDimension(R.dimen.standard_130));
    }

    private void closeFABMenu(){
        isFabOpen=false;
        fabFood.animate().translationY(0);
        fabExercise.animate().translationY(0);
        fabExercise.postDelayed(new Runnable() {
            @Override
            public void run() {
                fabExercise.setVisibility(View.INVISIBLE);
            }
        }, 300);
        fabFood.postDelayed(new Runnable() {
            @Override
            public void run() {
                fabFood.setVisibility(View.INVISIBLE);
            }
        }, 300);

    }
    @Override
    public void onBackPressed() {
       // empty to disable back button
    }
}