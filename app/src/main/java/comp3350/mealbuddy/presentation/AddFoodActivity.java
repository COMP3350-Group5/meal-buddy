/****************************************
 * AddFoodActivity
 * front end for adding a food to day.
 ****************************************/

package comp3350.mealbuddy.presentation;

import android.app.Dialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;

import comp3350.mealbuddy.R;
import comp3350.mealbuddy.business.AccessAccount;
import comp3350.mealbuddy.business.AccessEdible;
import comp3350.mealbuddy.business.AccessLabel;
import comp3350.mealbuddy.objects.Day;
import comp3350.mealbuddy.objects.consumables.Edible;
import comp3350.mealbuddy.objects.consumables.Food;

public class AddFoodActivity extends AppCompatActivity {

    AccessAccount accessAccount;
    AccessEdible accessEdible;
    AccessLabel accessLabel;
    Dialog dialog;

    /*
     * onCreate
     * called when the activity is initially created
     * Parameters:
     *     @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food);
        dialog = new Dialog(this);

        //get the passed values from the previous activity
        int dayOfYear = this.getIntent().getIntExtra("dayOfYear", -1);
        final String username = this.getIntent().getStringExtra("username");

        //get the day to display
        accessAccount = new AccessAccount();
        accessEdible = new AccessEdible(); //needed so we can add the food to the DB
        accessLabel = new AccessLabel();
        final Day day = accessAccount.getDay(username, dayOfYear);

        //obtain all the UI components to grab values from
        Button submit = findViewById(R.id.btnAddFood);
        final EditText name = findViewById(R.id.etFoodName);
        final EditText labels = findViewById(R.id.etLabels);
        final EditText protein = findViewById(R.id.etProtein);
        final EditText fat = findViewById(R.id.etFat);
        final EditText carbs = findViewById(R.id.etCarbs);
        final EditText weight = findViewById(R.id.etWeight);

        //on submit we want to create a new food
        submit.setOnClickListener((view) -> {
            if (TextUtils.isEmpty(name.getText())) {
                name.setError("Name is required");
            } else if (TextUtils.isEmpty(protein.getText())) {
                protein.setError("Protein is required");
            } else if (TextUtils.isEmpty(fat.getText())) {
                fat.setError("Fat is required");
            } else if (TextUtils.isEmpty(carbs.getText())) {
                carbs.setError("Carbs are required");
            } else if (TextUtils.isEmpty(weight.getText())) {
                weight.setError("Weight is required");
            } else {
                //get the labels from the label UI
                ArrayList<String> labelList = new ArrayList<>(Arrays.asList(labels.getText().toString().split(",")));
                for (String label : labelList) {
                    accessLabel.addLabel(label.trim());
                }
                //grab the food name and initialize with the labels
                Food food = new Food(name.getText().toString(), labelList);

                //grab the number values for weight and macros
                food.setWeight(Integer.parseInt(weight.getText().toString()));
                food.updateMacro(Edible.Macros.Protein, Integer.parseInt(protein.getText().toString()));
                food.updateMacro(Edible.Macros.Fat, Integer.parseInt(fat.getText().toString()));
                food.updateMacro(Edible.Macros.Carbohydrates, Integer.parseInt(carbs.getText().toString()));

                showPopUp(food, username, dayOfYear);
            }
        });
    }

    /*
     * showPopUp
     * shows the pop up with the corresponding information.
     * Parameters:
     *      @param edible - the edible to show
     *      @param username - the account to add to
     *      @param dayOfYear - the day to add to
     */
    public void showPopUp(Edible edible, String username, int dayOfYear) {
        dialog.setContentView(R.layout.pop_up_food);
        //initialize dialog components
        TextView titleText = dialog.findViewById(R.id.tvPopUpTitle);
        Spinner spinner = dialog.findViewById(R.id.spnMealtimes);
        Button btn = dialog.findViewById(R.id.btnConfirm);
        EditText editText = dialog.findViewById(R.id.etQuantity);

        btn.setOnClickListener((view) -> {
            if (TextUtils.isEmpty(editText.getText())) {
                editText.setError("Quantity is required");
            } else {
                //add the food
                String spnString = spinner.getSelectedItem().toString();
                //find the meal time
                Day.MealTimeType MT = getMealTime(spnString);
                Day day = accessAccount.getDay(username, dayOfYear);

                //add the meal to the day and update the day in the user
                day.addToMeal(MT, edible, Integer.parseInt(editText.getText().toString()));
                accessAccount.updateDay(username, day);
                accessEdible.addEdible(edible); //this adds the food to the foods database.

                //go back to the timeline activity and pass the username
                ChangeActivityHelper.changeActivity(AddFoodActivity.this, TimelineActivity.class, username, dayOfYear);
            }
        });

        titleText.setText("Adding Food: " + edible.name);
        dialog.show();
    }

    /*
     * getMealTime
     * returns the mealtime type from the string value
     */
    private Day.MealTimeType getMealTime(String value) {
        Day.MealTimeType MT;
        switch (value) {
            case "Breakfast":
                MT = Day.MealTimeType.BREAKFAST;
                break;
            case "Lunch":
                MT = Day.MealTimeType.LUNCH;
                break;
            case "Dinner":
                MT = Day.MealTimeType.DINNER;
                break;
            default:
                MT = Day.MealTimeType.SNACK;
                break;
        }
        return MT;
    }
}