package comp3350.mealbuddy.presentation;

import comp3350.mealbuddy.R;
import comp3350.mealbuddy.business.AccessAccount;
import comp3350.mealbuddy.objects.Day;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.time.LocalDate;
import java.time.Month;
import java.util.Calendar;
import java.util.Date;


public class TimelineActivity extends AppCompatActivity {

    private AccessAccount accessAccount;
    private Day day;
    private Date today;
    private LocalDate localDate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);
        String username = this.getIntent().getStringExtra("username");
        Calendar calendar = Calendar.getInstance();
        accessAccount = new AccessAccount();
        day = accessAccount.getDay(accessAccount.getAccount(username), calendar.get(Calendar.DAY_OF_YEAR));
        System.err.println(day.dayOfYear);
        initializeCards();
    }

    private void initializeCards(){
        CardView totals = (CardView) findViewById(R.id.cardTotals);
        RelativeLayout totalsLayout = (RelativeLayout) totals.getChildAt(0);
        TextView cardTotalsTitle = (TextView) totalsLayout.getChildAt(0);
        cardTotalsTitle.setText("Totals");

        CardView breakfast = (CardView) findViewById(R.id.cardBreakfast);
        RelativeLayout breakfastLayout = (RelativeLayout) breakfast.getChildAt(0);
        TextView cardBreakfastTitle = (TextView) breakfastLayout.getChildAt(0);
        cardBreakfastTitle.setText("Breakfast");
        TextView txtBreakfast = (TextView) breakfastLayout.getChildAt(1);
        txtBreakfast.setText(day.getMeal(Day.MealTime_t.BREAKFAST));

        CardView lunch = (CardView) findViewById(R.id.cardLunch);
        RelativeLayout lunchLayout = (RelativeLayout) lunch.getChildAt(0);
        TextView cardLunchTitle = (TextView) lunchLayout.getChildAt(0);
        cardLunchTitle.setText("Lunch");
        TextView txtLunch = (TextView) lunchLayout.getChildAt(1);
        txtLunch.setText(day.getMeal(Day.MealTime_t.LUNCH));

        CardView dinner = (CardView) findViewById(R.id.cardDinner);
        RelativeLayout dinnerLayout = (RelativeLayout) dinner.getChildAt(0);
        TextView cardDinnerTitle = (TextView) dinnerLayout.getChildAt(0);
        cardDinnerTitle.setText("Dinner");
        TextView txtDinner = (TextView) dinnerLayout.getChildAt(1);
        txtDinner.setText(day.getMeal(Day.MealTime_t.DINNER));

        CardView snacks = (CardView) findViewById(R.id.cardSnacks);
        RelativeLayout snacksLayout = (RelativeLayout) snacks.getChildAt(0);
        TextView cardSnacksTitle = (TextView) snacksLayout.getChildAt(0);
        cardSnacksTitle.setText("Snacks");
        TextView txtSnacks = (TextView) snacksLayout.getChildAt(1);
        txtSnacks.setText(day.getMeal(Day.MealTime_t.SNACK));
    }}