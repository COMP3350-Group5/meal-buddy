package comp3350.mealbuddy.presentation;

import comp3350.mealbuddy.R;
import comp3350.mealbuddy.business.AccessAccount;
import comp3350.mealbuddy.objects.Day;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.os.Bundle;
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
        TextView cardTotalsTitle = (TextView) totals.getChildAt(0);
        cardTotalsTitle.setText("Totals");

        CardView breakfast = (CardView) findViewById(R.id.cardBreakfast);
        TextView cardBreakfastTitle = (TextView) breakfast.getChildAt(0);
        cardBreakfastTitle.setText("Breakfast");

        CardView lunch = (CardView) findViewById(R.id.cardLunch);
        TextView cardLunchTitle = (TextView) lunch.getChildAt(0);
        cardLunchTitle.setText("Lunch");

        CardView dinner = (CardView) findViewById(R.id.cardDinner);
        TextView cardDinnerTitle = (TextView) dinner.getChildAt(0);
        cardDinnerTitle.setText("Dinner");

        CardView snacks = (CardView) findViewById(R.id.cardSnacks);
        TextView cardSnacksTitle = (TextView) snacks.getChildAt(0);
        cardSnacksTitle.setText("Snacks");
    }}