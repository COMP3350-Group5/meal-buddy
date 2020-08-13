/****************************************
 * AddFoodActivity
 * front end for adding a food to day.
 ****************************************/

package comp3350.mealbuddy.presentation;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;

import comp3350.mealbuddy.R;
import comp3350.mealbuddy.business.AccessEdible;
import comp3350.mealbuddy.business.AccessLabel;
import comp3350.mealbuddy.objects.consumables.Edible;
import comp3350.mealbuddy.objects.consumables.Food;

public class AddFoodActivity extends AppCompatActivity {

    private AccessEdible accessEdible;
    private AccessLabel accessLabel;
    private EditText name;
    private EditText labels;
    private EditText protein;
    private EditText fat;
    private EditText carbs;
    private EditText weight;

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
        name = findViewById(R.id.etFoodName);
        labels = findViewById(R.id.etLabels);
        protein = findViewById(R.id.etProtein);
        fat = findViewById(R.id.etFat);
        carbs = findViewById(R.id.etCarbs);
        weight = findViewById(R.id.etWeight);

        iron = findViewById(R.id.etIron);
        zinc = findViewById(R.id.etZinc);
        vitA = findViewById(R.id.etVitA);
        vitB12 = findViewById(R.id.etVitB12);
        vitC = findViewById(R.id.etVitC);
        vitE = findViewById(R.id.etVitE);
        calcium = findViewById(R.id.etCalcium);
        choline = findViewById(R.id.etCholine);
        magnesium = findViewById(R.id.etMagnesium);
        sodium = findViewById(R.id.etSodium);
        potassium = findViewById(R.id.etPotassium);
        niacin = findViewById(R.id.etNiacin);

        //on submit we want to create a new food
        submit.setOnClickListener((view) -> {
         if (validateInput(name, protein, fat, carbs, weight)) {
                //get the labels from the label UI
                ArrayList<String> labelList = new ArrayList<>(Arrays.asList(labels.getText().toString().split(",")));
                for (String label : labelList) {
                    if (!accessLabel.labelExists(label)) {
                        accessLabel.addLabel(label.trim());
                    }
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
     * Check if the input fields are valid.
     */
  private boolean validateInput(EditText name, EditText protein, EditText fat, EditText carbs, EditText weight) {
        boolean inputsValid = true;

      if (TextUtils.isEmpty(name.getText())) {
          name.setError("Name is required");
          inputsValid = false;
      }

      if (accessEdible.edibleExists(name.getText().toString())) {
          name.setError("Edible already exists choose a different name");
          inputsValid = false;
      }

      if (TextUtils.isEmpty(protein.getText())) {
          protein.setError("Protein is required");
          inputsValid = false;
      }

      if (TextUtils.isEmpty(fat.getText())) {
          fat.setError("Fat is required");
          inputsValid = false;
      }

      if (TextUtils.isEmpty(carbs.getText())) {
          carbs.setError("Carbs are required");
          inputsValid = false;
      }

      if (TextUtils.isEmpty(weight.getText())) {
          weight.setError("Weight is required");
          inputsValid = false;
      }

      return inputsValid;
  }

}