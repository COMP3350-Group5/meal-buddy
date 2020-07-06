package comp3350.mealbuddy.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

import comp3350.mealbuddy.R;
import comp3350.mealbuddy.business.AccessAccount;
import comp3350.mealbuddy.business.Calculator;
import comp3350.mealbuddy.objects.Day;


public class TimelineActivity extends AppCompatActivity {

    private AccessAccount accessAccount;
    private Day day;
    private Date today;
    private LocalDate localDate;
    private Calculator calculator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);
        final String username = this.getIntent().getStringExtra("username");
        Calendar calendar = Calendar.getInstance();
        accessAccount = new AccessAccount();
        day = accessAccount.getDay(accessAccount.getAccount(username), calendar.get(Calendar.DAY_OF_YEAR));
        System.err.println(day.dayOfYear);
        calculator = new Calculator(day);
        initializeCards();
        FloatingActionButton addFood = (FloatingActionButton) findViewById(R.id.btnAddFood);
        addFood.setOnClickListener((view) -> {
                Intent intent = new Intent(TimelineActivity.this, AddFoodActivity.class);
                intent.putExtra("dayOfYear", day.dayOfYear);
                intent.putExtra("username", username);
                TimelineActivity.this.startActivity(intent);
        });
    }



    private void initializeCards(){
        initializeTotals();
        initializeBreakfast();
        initializeLunch();
        initializeDinner();
        initializeSnacks();
    }

    private void initializeSnacks() {
        CardView snacks = findViewById(R.id.cardSnacks);
        RelativeLayout snacksLayout = (RelativeLayout) snacks.getChildAt(0);
        TextView cardSnacksTitle = (TextView) snacksLayout.getChildAt(0);
        cardSnacksTitle.setText("Snacks");
        TextView txtSnacks = (TextView) snacksLayout.getChildAt(1);
        txtSnacks.setText(day.getMeal(Day.MealTime_t.SNACK));
        TextView txtSnackCals = (TextView) snacksLayout.getChildAt(2);
        txtSnackCals.setText(calculator.getMealTimeCalories(Day.MealTime_t.SNACK) + " Cals");
    }

    private void initializeDinner() {
        CardView dinner = findViewById(R.id.cardDinner);
        RelativeLayout dinnerLayout = (RelativeLayout) dinner.getChildAt(0);
        TextView cardDinnerTitle = (TextView) dinnerLayout.getChildAt(0);
        cardDinnerTitle.setText("Dinner");
        TextView txtDinner = (TextView) dinnerLayout.getChildAt(1);
        txtDinner.setText(day.getMeal(Day.MealTime_t.DINNER));
        TextView txtDinnerCals = (TextView) dinnerLayout.getChildAt(2);
        txtDinnerCals.setText(calculator.getMealTimeCalories(Day.MealTime_t.DINNER) + " Cals");
    }

    private void initializeLunch() {
        CardView lunch = findViewById(R.id.cardLunch);
        RelativeLayout lunchLayout = (RelativeLayout) lunch.getChildAt(0);
        TextView cardLunchTitle = (TextView) lunchLayout.getChildAt(0);
        cardLunchTitle.setText("Lunch");
        TextView txtLunch = (TextView) lunchLayout.getChildAt(1);
        txtLunch.setText(day.getMeal(Day.MealTime_t.LUNCH));
        TextView txtLunchCals = (TextView) lunchLayout.getChildAt(2);
        txtLunchCals.setText(calculator.getMealTimeCalories(Day.MealTime_t.LUNCH) + " Cals");
    }

    private void initializeBreakfast() {
        CardView breakfast = findViewById(R.id.cardBreakfast);
        RelativeLayout breakfastLayout = (RelativeLayout) breakfast.getChildAt(0);
        TextView cardBreakfastTitle = (TextView) breakfastLayout.getChildAt(0);
        cardBreakfastTitle.setText("Breakfast");
        TextView txtBreakfast = (TextView) breakfastLayout.getChildAt(1);
        txtBreakfast.setText(day.getMeal(Day.MealTime_t.BREAKFAST));
        TextView txtBreakfastCals = (TextView) breakfastLayout.getChildAt(2);
        txtBreakfastCals.setText(calculator.getMealTimeCalories(Day.MealTime_t.BREAKFAST) + " Cals");
    }

    private void initializeTotals() {
        CardView totals = findViewById(R.id.cardTotals);
        RelativeLayout totalsLayout = (RelativeLayout) totals.getChildAt(0);
        TextView cardTotalsTitle = (TextView) totalsLayout.getChildAt(0);
        cardTotalsTitle.setText("Totals");
        TextView totalsCals = (TextView) totalsLayout.getChildAt(1);
        totalsCals.setText(calculator.getTotalCalories() + " Cals");
    }
}