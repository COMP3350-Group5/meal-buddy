/****************************************
 * AddFoodActivity
 * front end for adding a food to day.
 ****************************************/

package comp3350.mealbuddy.presentation;

import android.app.Dialog;
import android.os.Bundle;
import android.text.Editable;
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

        //get the passed values from the previous activity
        int dayOfYear = this.getIntent().getIntExtra("dayOfYear", -1);
        final String username = this.getIntent().getStringExtra("username");

        //get the day to display
        accessEdible = new AccessEdible(); //needed so we can add the food to the DB
        accessLabel = new AccessLabel();

        //obtain all the UI components to grab values from
        Button submit = findViewById(R.id.btnAddFood);
        final EditText name = findViewById(R.id.etFoodName);
        final EditText labels = findViewById(R.id.etLabels);
        final EditText protein = findViewById(R.id.etProtein);
        final EditText fat = findViewById(R.id.etFat);
        final EditText carbs = findViewById(R.id.etCarbs);
        final EditText weight = findViewById(R.id.etWeight);

        final EditText iron = findViewById(R.id.etIron);
        final EditText zinc = findViewById(R.id.etZinc);
        final EditText vitA = findViewById(R.id.etVitA);
        final EditText vitB12 = findViewById(R.id.etVitB12);
        final EditText vitC = findViewById(R.id.etVitC);
        final EditText vitE = findViewById(R.id.etVitE);
        final EditText calcium = findViewById(R.id.etCalcium);
        final EditText choline = findViewById(R.id.etCholine);
        final EditText magnesium = findViewById(R.id.etMagnesium);
        final EditText sodium = findViewById(R.id.etSodium);
        final EditText potassium = findViewById(R.id.etPotassium);
        final EditText niacin = findViewById(R.id.etNiacin);

        //on submit we want to create a new food
        submit.setOnClickListener((view) -> {
            if (TextUtils.isEmpty(name.getText())) {
                name.setError("Name is required");
            } else if (accessEdible.edibleExists(name.getText().toString())) {
                name.setError("Edible already exists choose a different name");
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
                Food newFood = new Food(name.getText().toString(), labelList);

                //grab the number values for weight and macros
                newFood.setWeight(Integer.parseInt(weight.getText().toString()));
                newFood.updateMacro(Edible.Macros.Protein, Integer.parseInt(protein.getText().toString()));
                newFood.updateMacro(Edible.Macros.Fat, Integer.parseInt(fat.getText().toString()));
                newFood.updateMacro(Edible.Macros.Carbohydrates, Integer.parseInt(carbs.getText().toString()));

                //update the micros when they exist
                newFood.updateMicro(Edible.Micros.Iron, getMicroVal(iron.getText()));
                newFood.updateMicro(Edible.Micros.Zinc, getMicroVal(zinc.getText()));
                newFood.updateMicro(Edible.Micros.VitaminA, getMicroVal(vitA.getText()));
                newFood.updateMicro(Edible.Micros.VitaminB12, getMicroVal(vitB12.getText()));
                newFood.updateMicro(Edible.Micros.VitaminC, getMicroVal(vitC.getText()));
                newFood.updateMicro(Edible.Micros.VitaminE, getMicroVal(vitE.getText()));
                newFood.updateMicro(Edible.Micros.Calcium, getMicroVal(calcium.getText()));
                newFood.updateMicro(Edible.Micros.Magnesium, getMicroVal(magnesium.getText()));
                newFood.updateMicro(Edible.Micros.Choline, getMicroVal(choline.getText()));
                newFood.updateMicro(Edible.Micros.Sodium, getMicroVal(sodium.getText()));
                newFood.updateMicro(Edible.Micros.Potassium, getMicroVal(potassium.getText()));
                newFood.updateMicro(Edible.Micros.Niacin, getMicroVal(niacin.getText()));

                accessEdible.addEdible(newFood); //this adds the food to the foods database.

                ChangeActivityHelper.changeActivity(AddFoodActivity.this, SearchFoodActivity.class, username, dayOfYear);
            }
        });
    }


    private int getMicroVal(Editable text){
        return TextUtils.isEmpty(text)
                ? 0
                : Integer.parseInt(text.toString());
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