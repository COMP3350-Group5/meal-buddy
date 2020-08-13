/****************************************
 * TimelineActivity
 * front end for showing a day
 ****************************************/
package comp3350.mealbuddy.presentation;

import android.app.Dialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.cardview.widget.CardView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import comp3350.mealbuddy.R;
import comp3350.mealbuddy.business.AccessAccount;
import comp3350.mealbuddy.business.Calculator;
import comp3350.mealbuddy.objects.Account;
import comp3350.mealbuddy.objects.Day;
import comp3350.mealbuddy.objects.Exercise;
import comp3350.mealbuddy.objects.UserInfo;
import comp3350.mealbuddy.objects.consumables.Edible;
import comp3350.mealbuddy.objects.consumables.EdibleIntPair;
import comp3350.mealbuddy.objects.consumables.Meal;
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
                ChangeActivityHelper.changeActivity(TimelineActivity.this, TimelineActivity.class, username, newCalendar.get(Calendar.DAY_OF_YEAR));
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

        BottomNavigationView nav = findViewById(R.id.bottom_navigation);
        Menu menu = nav.getMenu();
        menu.getItem(ChangeActivityHelper.TIMELINE).setChecked(true);
        menu.getItem(ChangeActivityHelper.TIMELINE).setCheckable(false);

        ImageButton btnMore = findViewById(R.id.btnMore);
        btnMore.setOnClickListener( (view) -> {
            showPopup(view);
        });





    }

    public void showPopup(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.timeline_menu, popup.getMenu());
        popup.setOnMenuItemClickListener((MenuItem item) -> {
            switch(item.getItemId()) {
                case R.id.action_set_default_day:
                    setDefaultDay();
                    break;
                case R.id.action_reset_default_day:
                    resetDefaultDay();
                    break;
                case R.id.action_labels:
                    ChangeActivityHelper.changeActivity(TimelineActivity.this, LabelActivity.class);
                    break;
            }
            return true;
        });
        popup.show();
    }

    private void setDefaultDay() {
        UserInfo userInfo = accessAccount.getUserInfo(username);
        Day newDefault = new Day(day);
        newDefault.dayOfYear = Account.DEFAULT_DAY_NUM;
        accessAccount.updateDay(userInfo.username, newDefault);
    }

    private void resetDefaultDay() {
        UserInfo userInfo = accessAccount.getUserInfo(username);
        accessAccount.updateDay(userInfo.username, new Day(Account.DEFAULT_DAY_NUM));
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
            String toDisplay = String.format("%d cals", calculator.getMealTimeCalories((Day.MealTimeType)mt[2]));
            tvMeal.setText(toDisplay);
        }
    }

    private void initializeExerciseCard(){
        CardView exerCard = findViewById(R.id.cardExercise);
        TextView exerciseCals = findViewById(R.id.txtExercisesCals);
        exerciseCals.setText(String.format("%s%d%s", "Burned " , calculator.getTotalExerciseCalories(accessAccount.getUserInfo(username)), " cals"));
        exerCard.setOnClickListener((view) -> showExercisePopUp());
    }

    private void showExercisePopUp(){
        dialog.setContentView(R.layout.pop_up_view_exercise);
        //set up the array adapter
        ArrayList<String> exerNames = new ArrayList<>();
        for (Iterator<Exercise> it = day.getExercises(); it.hasNext(); ) {
            Exercise exer = it.next();
            String toAdd = String.format("%s\t\t\t%smin\t\t\tburned %d cals", exer.name, exer.duration, calculator.getExerciseCalories(exer, accessAccount.getUserInfo(username)));
            exerNames.add(toAdd);
        }
        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter(TimelineActivity.this, android.R.layout.simple_list_item_1, exerNames);
        //set the title
        TextView title = dialog.findViewById(R.id.tvViewExercise);
        title.setText("Exercise");
        TextView cals = dialog.findViewById(R.id.tvExerciseCalories);
        cals.setText(String.format("%s%d", "Calories burned " , calculator.getTotalExerciseCalories(accessAccount.getUserInfo(username))));

        //set up the list view
        ListView lv = dialog.findViewById(R.id.lvViewExercise);
        lv.setAdapter(stringArrayAdapter);
        dialog.show();

        Button btnRemoveExercise = dialog.findViewById(R.id.btnRemoveExercise);
        btnRemoveExercise.setOnClickListener((dialog) -> showRemoveExercise());
    }

    /*
     * showRemoveGoal
     * Show the popup to remove an exercise
     */
    public void showRemoveExercise() {
        dialog.setContentView(R.layout.remove_exercise);
        Day day = accessAccount.getDay(username, dayOfYear);

        //Populate the spinner with the exercises
        Spinner exerciseSpinner = dialog.findViewById(R.id.spnExercises);
        List<String> exerciseList = new ArrayList<>();
        Iterator<Exercise> dayExercises = day.getExercises();
        Exercise g;
        while (dayExercises.hasNext()) {
            g = dayExercises.next();
            exerciseList.add(g.toString());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, exerciseList);
        exerciseSpinner.setAdapter(adapter);

        //Remove an exercise
        Button button = dialog.findViewById(R.id.btnContinue);
        button.setOnClickListener((view) -> {
            String selected = exerciseSpinner.getSelectedItem().toString();
            int index = exerciseList.indexOf(selected);
            exerciseList.remove(index);
            day.removeExercise(index);
            accessAccount.updateDay(username, day);

            //Back to timeline activity
            ChangeActivityHelper.changeActivity(TimelineActivity.this, TimelineActivity.class, username, dayOfYear);

        });

        dialog.show();
    }

    private void showFoodPopUp(Meal mealtime) {
        dialog.setContentView(R.layout.pop_up_display_food);
        int fatTotal = 0;
        int proteinTotal = 0;
        int carbTotal = 0;
        //set up the array adapter
        ArrayList<String> foodNames = new ArrayList<>();
        ArrayList<String> foodLabels = new ArrayList<>();


        for (Iterator<EdibleIntPair> it = mealtime.getEdibleIntPairIterator(); it.hasNext(); ) {
            EdibleIntPair e = it.next();

            //get the macros from the food and add food to the string
            String macroString = String.format("Fat: %dg\t\t|\t\tProtein: %dg\t\t|\t\tCarbs: %dg\t",
                    e.edible.getMacroGrams(Edible.Macros.Fat),
                    e.edible.getMacroGrams(Edible.Macros.Protein),
                    e.edible.getMacroGrams(Edible.Macros.Carbohydrates));
            String toAdd = String.format("%s\t\t\t%s\t\t\tx %d", e.edible.name, macroString, e.quantity);
            foodNames.add(toAdd);
            //add the labels to the string
            String labelToAdd = "";
            for(String label : e.edible.labels)
                labelToAdd += "<" + label + "> ";
            foodLabels.add(labelToAdd);
            //update the totals
            fatTotal += calculator.getMacroCalories(Edible.Macros.Fat);
            proteinTotal += calculator.getMacroCalories(Edible.Macros.Protein);
            carbTotal += calculator.getMacroCalories(Edible.Macros.Carbohydrates);
        }

        //override the adapter to display both texts
        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter(TimelineActivity.this, android.R.layout.simple_list_item_2, android.R.id.text1, foodNames){
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView text1 = view.findViewById(android.R.id.text1);
                TextView text2 = view.findViewById(android.R.id.text2);
                //set the texts to the food and its labels
                text1.setText(foodNames.get(position));
                text2.setText(foodLabels.get(position));
                return view;
            }
        };
        //set the title
        TextView title = dialog.findViewById(R.id.tvViewFood);
        title.setText(mealtime.name);
        //set the macro totals
        TextView fat = dialog.findViewById(R.id.tvViewFoodFat);
        fat.setText(String.format("Fat: %dg", fatTotal));
        TextView protein = dialog.findViewById(R.id.tvViewFoodProtein);
        protein.setText(String.format("Protein: %dg", proteinTotal));
        TextView carbs = dialog.findViewById(R.id.tvViewFoodCarbs);
        carbs.setText(String.format("Carbs: %dg", carbTotal));

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