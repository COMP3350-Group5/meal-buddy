/****************************************
 * TimelineActivity
 * front end for showing a day
 ****************************************/
package comp3350.mealbuddy.presentation;

import android.app.Dialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;

import comp3350.mealbuddy.R;
import comp3350.mealbuddy.business.AccessAccount;
import comp3350.mealbuddy.business.Calculator;
import comp3350.mealbuddy.objects.Account;
import comp3350.mealbuddy.objects.Day;
import comp3350.mealbuddy.objects.UserInfo;
import comp3350.mealbuddy.objects.consumables.Edible;
import comp3350.mealbuddy.objects.consumables.EdibleIntPair;
import comp3350.mealbuddy.objects.consumables.Meal;


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

        //initialize the access methods and day based on the given values
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
                newCalendar.set(y, m, d);
                int newDayOfYear = newCalendar.get(Calendar.DAY_OF_YEAR);
                ChangeActivityHelper.changeActivity(TimelineActivity.this, TimelineActivity.class, username, dayOfYear);
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
            if (!isFabOpen) showFABMenu();
            else closeFABMenu();
        });

        fabFood.setOnClickListener((view) -> ChangeActivityHelper.changeActivity(TimelineActivity.this, SearchFoodActivity.class, username, dayOfYear));

        fabExercise.setOnClickListener((view) -> ChangeActivityHelper.changeActivity(TimelineActivity.this, AddExerciseActivity.class, username, dayOfYear));

        // Sets the current day as the default for new days
        Button setDefaultDay = findViewById(R.id.defaultDay);

        setDefaultDay.setOnClickListener((view) -> {
            UserInfo userInfo = accessAccount.getUserInfo(username);
            Day newDefault = new Day(day);
            newDefault.dayOfYear = Account.DEFAULT_DAY_NUM;
            accessAccount.updateDay(userInfo.username, newDefault);
        });

        // resets the default day to empty day
        Button resetDefaultDay = findViewById(R.id.resetDefault);

        resetDefaultDay.setOnClickListener((view) -> {
            UserInfo userInfo = accessAccount.getUserInfo(username);
            accessAccount.updateDay(userInfo.username, new Day(Account.DEFAULT_DAY_NUM));
        });

        // goes to label activity
        Button gotoLabels = findViewById(R.id.gotoLabels);

        gotoLabels.setOnClickListener((view) -> {
            ChangeActivityHelper.changeActivity(TimelineActivity.this, LabelActivity.class);
        });

        BottomNavigationView nav = findViewById(R.id.bottom_navigation);
        Menu menu = nav.getMenu();
        menu.getItem(ChangeActivityHelper.TIMELINE).setChecked(true);
        menu.getItem(ChangeActivityHelper.TIMELINE).setCheckable(false);

    }

    /*
     * initializeCards
     * initializes the UI cards
     */
    private void initializeCards() {
        initializeTotals();
        initializePopOutCards();
        initializeExerciseCard();
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
        netCals.setText(calculator.getNetCalories(accessAccount.getUserInfo(username)) + " net Cals");
    }

    private void initializePopOutCards(){
        Object[][] mealtime = {
            {R.id.cardBreakfast, R.id.txtBreakfastCals, Day.MealTimeType.BREAKFAST},
            {R.id.cardLunch, R.id.txtLunchCals, Day.MealTimeType.LUNCH},
            {R.id.cardDinner, R.id.txtDinnerCals, Day.MealTimeType.DINNER},
            {R.id.cardSnacks, R.id.txtSnacksCals, Day.MealTimeType.SNACK},
        };

        for(Object[] mt : mealtime){
            CardView cvMeal = findViewById((int)mt[0]);
            cvMeal.setOnClickListener((view) -> showFoodPopUp(day.getMealTime((Day.MealTimeType)mt[2])));
            TextView tvMeal = findViewById((int)mt[1]);
            String toDisplay = String.format("%d%s", calculator.getMealTimeCalories((Day.MealTimeType)mt[2]), " Cals");
            tvMeal.setText(toDisplay);
        }
    }

    private void initializeExerciseCard(){
        TextView exercise = findViewById(R.id.txtExercisesCals);
        String toDisplay = String.format("%s%d%s", "Burned ", calculator.getTotalExerciseCalories(accessAccount.getUserInfo(username)), " Cals");
        exercise.setText(toDisplay);
    }

    private void showFoodPopUp(Meal mealtime) {
        dialog.setContentView(R.layout.pop_up_display_food);
        //set up the array adapter
        ArrayList<String> foodNames = new ArrayList<>();
        for (Iterator<EdibleIntPair> it = mealtime.getEdibleIntPairIterator(); it.hasNext(); ) {
            EdibleIntPair e = it.next();
            String macroString = String.format("Fat: %dg\t\t|\t\tProtein: %dg\t\t|\t\tCarbs: %dg\t",
                    e.edible.getMacroGrams(Edible.Macros.Fat),
                    e.edible.getMacroGrams(Edible.Macros.Protein),
                    e.edible.getMacroGrams(Edible.Macros.Carbohydrates));

            String toAdd = String.format("%d%s%s\t\t\t\t\t%s", e.quantity, "x ", e.edible.name, macroString);
            foodNames.add(toAdd);
        }
        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<>(TimelineActivity.this, android.R.layout.simple_list_item_1, foodNames);
        //set the title
        TextView title = dialog.findViewById(R.id.tvViewFood);
        title.setText(mealtime.name);
        //set up the list view
        ListView lv = dialog.findViewById(R.id.lvViewFood);
        lv.setAdapter(stringArrayAdapter);
        dialog.show();
    }

    /*
     * showFABMenu
     * shows the floating action button menu
     */
    private void showFABMenu() {
        isFabOpen = true;
        fabFood.setVisibility(View.VISIBLE);
        fabExercise.setVisibility(View.VISIBLE);
        fabFood.animate().translationY(-getResources().getDimension(R.dimen.standard_65));
        fabExercise.animate().translationY(-getResources().getDimension(R.dimen.standard_130));
    }

    /*
     * closeFABMenu
     * closes the floating action button menu
     */
    private void closeFABMenu() {
        isFabOpen = false;
        fabFood.animate().translationY(0);
        fabExercise.animate().translationY(0);
        fabExercise.postDelayed(() -> fabExercise.setVisibility(View.INVISIBLE), 300);
        fabFood.postDelayed(() -> fabFood.setVisibility(View.INVISIBLE), 300);

    }

    /*
     * onBackPressed
     * disable back button
     */
    @Override
    public void onBackPressed() {
        // empty to disable back button
    }
}