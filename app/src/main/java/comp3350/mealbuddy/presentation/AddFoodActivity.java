/****************************************
 * AddFoodActivity
 * front end for adding a food to day.
 ****************************************/

package comp3350.mealbuddy.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;

import comp3350.mealbuddy.R;
import comp3350.mealbuddy.business.AccessAccount;
import comp3350.mealbuddy.business.AccessEdible;
import comp3350.mealbuddy.objects.Account;
import comp3350.mealbuddy.objects.Day;
import comp3350.mealbuddy.objects.consumables.Edible;
import comp3350.mealbuddy.objects.consumables.Food;

public class AddFoodActivity extends AppCompatActivity {

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
        AccessAccount accessAccount = new AccessAccount();
        final AccessEdible accessEdible = new AccessEdible(); //needed so we can add the food to the DB
        Account account = accessAccount.getAccount(username);
        final Day day = accessAccount.getDay(account, dayOfYear);

        //obtain all the UI components to grab values from
        Button submit = findViewById(R.id.btnAddFood);
        final EditText name = findViewById(R.id.etFoodName);
        final EditText labels = findViewById(R.id.etLabels);
        final EditText protein = findViewById(R.id.etProtein);
        final EditText fat = findViewById(R.id.etFat);
        final EditText carbs = findViewById(R.id.etCarbs);
        final EditText weight = findViewById(R.id.etWeight);
        final Spinner spinner = findViewById(R.id.spnMealtimes);

        //on submit we want to create a new food
        submit.setOnClickListener ((view) -> {
            //get the labels from the label UI
            ArrayList<String> labelList = new ArrayList<>(Arrays.asList(labels.getText().toString().split(",")));
            //grab the food name and initialize with the labels
            Food food = new Food(name.getText().toString(), labelList);

            //grab the number values for weight and macros
            food.setWeight(Integer.parseInt(weight.getText().toString()));
            food.updateMacro(Edible.Macros.Protein, Integer.parseInt(protein.getText().toString()));
            food.updateMacro(Edible.Macros.Fat, Integer.parseInt(fat.getText().toString()));
            food.updateMacro(Edible.Macros.Carbohydrates, Integer.parseInt(carbs.getText().toString()));

            //get the meal time to add the food to
            Day.MealTimeType MT;
            String spnString = spinner.getSelectedItem().toString();
            if (spnString.equals("Breakfast"))
                MT = Day.MealTimeType.BREAKFAST;
            else if(spnString.equals("Lunch"))
                MT = Day.MealTimeType.LUNCH;
            else if(spnString.equals("Dinner"))
                MT = Day.MealTimeType.DINNER;
            else
                MT = Day.MealTimeType.SNACK;

            day.addToMeal(MT, food, 1); //this adds the food to the USERS day
            accessEdible.addEdible(food); //this adds the food to the foods database.

            //we must update the day in order for it to display
            accessAccount.updateDay(account, day);
            //go back to the timeline activity and pass the username
            Intent intent = new Intent(AddFoodActivity.this, TimelineActivity.class);
            intent.putExtra("username", username);
            AddFoodActivity.this.startActivity(intent);
        });
    }
}