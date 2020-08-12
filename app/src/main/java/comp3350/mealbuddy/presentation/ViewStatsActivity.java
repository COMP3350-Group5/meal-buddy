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

        //set the components
        setComponentValues();

    }

    /*
     * setTotalValues
     * sets each of the total values for the components to use
     */
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

    /*
     * setComponentValues
     * sets the components to the corresponding values
     */
    private void setComponentValues(){
        //call helper methods to set the components
        setComponentTotalValues();
        setComponentAverageValues();
        setComponentPercentValues();
        setComponentDayValues();
    }

    /*
     * setComponentTotalValues
     * sets the components to the values. Handles the 'total' components.
     */
    private void setComponentTotalValues(){
        Object[][] totalComponents = {
            {R.id.tvStatsCaloriesValue, totalCalories},
            {R.id.tvStatsCaloriesBurnedValue, totalCaloriesBurned},
            {R.id.tvStatsGoalsAchievedValue, totalGoalsAchieved},
            {R.id.tvStatsDaysTrackedValue, totalDaysTracked},
            {R.id.tvStatsMacroCarbsValue, totalCarbCalories},
            {R.id.tvStatsMacroProteinValue, totalProteinCalories},
            {R.id.tvStatsMacroFatValue, totalFatCalories},
        };
        for (Object[] o : totalComponents){
            TextView tv = findViewById((int)o[0]);
            tv.setText(String.valueOf(o[1]));
        }
    }

    /*
     * setComponentAverageValues
     * sets the components to the values. Handles the 'average' components.
     */
    private void setComponentAverageValues() {
        Object[][] averagesComponents = {
            {R.id.tvStatsCaloriesValueAvg, totalCalories, (double)totalDaysTracked},
            {R.id.tvStatsCaloriesBurnedValueAvg, totalCaloriesBurned, (double)totalDaysTracked},
            {R.id.tvStatsGoalsAchievedValueAvg, totalGoalsAchieved, (double)totalDaysTracked},
        };
        for (Object[] o : averagesComponents) {
            TextView tv = findViewById((int) o[0]);
            double val = Math.round(((int) o[1] / (double) o[2]) * 100) / 100.0;
            tv.setText(String.format("%.02f per day", val));
        }
    }

    /*
     * setComponentPercentValues
     * sets the components to the values. Handles the 'percentage' components.
     */
    private void setComponentPercentValues(){
        Object[][] percentageComponents = {
            {R.id.tvStatsMacroCarbsValueAvg, totalCarbCalories, (double)totalCalories},
            {R.id.tvStatsMacroProteinValueAvg, totalProteinCalories, (double)totalCalories},
            {R.id.tvStatsMacroFatValueAvg, totalFatCalories, (double)totalCalories},
        };
        for (Object[] o : percentageComponents){
            TextView tv = findViewById((int)o[0]);
            double val = Math.round(((int)o[1]/(double)o[2]) * 100);
            tv.setText(String.format("%.02f% of total cal", val));
        }
    }

    /*
     * setComponentPercentValues
     * sets the components to the values. Handles the 'percentage' components.
     */
    private void setComponentDayValues(){
        TextView tv = findViewById(R.id.tvStatsDaysTrackedValueAvg);
        double val = Math.round((totalDaysTracked/365.0) * 100);
        tv.setText(String.format("%.02f% of year tracked", val));

    }
}