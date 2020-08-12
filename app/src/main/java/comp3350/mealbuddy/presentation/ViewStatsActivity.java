/****************************************
 * SearchFoodActivity
 * the search food activity
 ****************************************/
package comp3350.mealbuddy.presentation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

import comp3350.mealbuddy.R;
import comp3350.mealbuddy.business.AccessAccount;
import comp3350.mealbuddy.business.Calculator;
import comp3350.mealbuddy.business.GoalTracker;
import comp3350.mealbuddy.objects.Day;
import comp3350.mealbuddy.objects.consumables.Edible;

public class ViewStatsActivity extends AppCompatActivity {

    private AccessAccount accessAccount;
    private Calculator calculator;
    private String username;
    private int dayOfYear;

    private int totalCalories;
    private int totalProteinCalories;
    private int totalFatCalories;
    private int totalCarbCalories;
    private int totalCaloriesBurned;
    private int totalGoalsAchieved;
    private int totalDaysTracked;

    /*
     * onCreate
     * called when the activity is initially created
     * Parameters:
     *     @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_stats);

        //get the passed values from the previous activity
        dayOfYear = this.getIntent().getIntExtra("dayOfYear", -1);
        username = this.getIntent().getStringExtra("username");
        //initialize the access methods and day based on the given values
        accessAccount = new AccessAccount();

        //set all the totals
        setTotalValues();

        //set the component values properly
        ((TextView)findViewById(R.id.tvStatsCaloriesValue)).setText(totalCalories);
        ((TextView)findViewById(R.id.tvStatsCaloriesValueAvg)).setText(totalCalories/totalDaysTracked);
        ((TextView)findViewById(R.id.tvStatsCaloriesBurnedValue)).setText(totalCaloriesBurned);
        ((TextView)findViewById(R.id.tvStatsCaloriesBurnedValueAvg)).setText(totalCaloriesBurned/totalDaysTracked);
        ((TextView)findViewById(R.id.tvStatsGoalsAchievedValue)).setText(totalGoalsAchieved);
        ((TextView)findViewById(R.id.tvStatsGoalsAchievedValueAvg)).setText(totalGoalsAchieved/totalDaysTracked);
        ((TextView)findViewById(R.id.tvStatsDaysTrackedValue)).setText(totalDaysTracked);
        ((TextView)findViewById(R.id.tvStatsDaysTrackedValueAvg)).setText(totalDaysTracked/365);
        ((TextView)findViewById(R.id.tvStatsMacroCarbsValue)).setText(totalCarbCalories);
        ((TextView)findViewById(R.id.tvStatsMacroCarbsValueAvg)).setText(totalCarbCalories/totalCalories);
        ((TextView)findViewById(R.id.tvStatsMacroProteinValue)).setText(totalProteinCalories);
        ((TextView)findViewById(R.id.tvStatsMacroProteinValueAvg)).setText(totalProteinCalories/totalCalories);
        ((TextView)findViewById(R.id.tvStatsMacroFatValue)).setText(totalFatCalories);
        ((TextView)findViewById(R.id.tvStatsMacroFatValueAvg)).setText(totalFatCalories/totalCalories);
    }

    private void setTotalValues(){
        //get all the tracked days
        List<Day> listOfDays = accessAccount.getDays(username);
        int listSize = listOfDays.size();
        //total the calories, calories burned, macro calories, goals achieved
        for (Day d : listOfDays){
            calculator = new Calculator(d);
            //total the calories
            totalCalories += calculator.getTotalCalories();
            totalCarbCalories += calculator.getMacroCalories(Edible.Macros.Carbohydrates);
            totalFatCalories += calculator.getMacroCalories(Edible.Macros.Fat);
            totalProteinCalories += calculator.getMacroCalories(Edible.Macros.Protein);
            totalCaloriesBurned += calculator.getTotalExerciseCalories(accessAccount.getUserInfo(username));
            //get the goals achieved
            totalGoalsAchieved += GoalTracker.getPassedGoals(calculator, d.getGoals()).size();
        }
        totalDaysTracked = listSize == 0 ? 1 : listSize;
    }
}