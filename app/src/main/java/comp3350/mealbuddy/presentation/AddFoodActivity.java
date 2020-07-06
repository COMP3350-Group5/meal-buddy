package comp3350.mealbuddy.presentation;

import androidx.appcompat.app.AppCompatActivity;
import comp3350.mealbuddy.R;
import comp3350.mealbuddy.business.AccessAccount;
import comp3350.mealbuddy.business.AccessEdible;
import comp3350.mealbuddy.objects.Account;
import comp3350.mealbuddy.objects.Day;
import comp3350.mealbuddy.objects.Edible;
import comp3350.mealbuddy.objects.Food;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Arrays;

public class AddFoodActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food);

        int dayOfYear = this.getIntent().getIntExtra("dayOfYear", -1);
        final String username = this.getIntent().getStringExtra("username");

        AccessAccount accessAccount = new AccessAccount();
        final AccessEdible accessEdible = new AccessEdible(); //needed so we can add the food to the DB
        Account account = accessAccount.getAccount(username);
        final Day day = accessAccount.getDay(account, dayOfYear);

        Button submit = (Button) findViewById(R.id.btnAddFood);
        final EditText name = (EditText) findViewById(R.id.etFoodName);
        final EditText labels = (EditText) findViewById(R.id.etLabels);
        final EditText protein = (EditText) findViewById(R.id.etProtein);
        final EditText fat = (EditText) findViewById(R.id.etFat);
        final EditText carbs = (EditText) findViewById(R.id.etCarbs);
        final EditText weight = (EditText) findViewById(R.id.etWeight);

        final Spinner spinner = (Spinner) findViewById(R.id.spnMealtimes);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<String> labelList = new ArrayList<>(Arrays.asList(labels.getText().toString().split(",")));
                Food food = new Food(name.getText().toString(), labelList);
                food.setWeight(Integer.parseInt(weight.getText().toString()));

                food.addMacro(Edible.Macros.Protein, Integer.parseInt(protein.getText().toString()));
                food.addMacro(Edible.Macros.Fat, Integer.parseInt(fat.getText().toString()));
                food.addMacro(Edible.Macros.Carbohydrates, Integer.parseInt(carbs.getText().toString()));

                Day.MealTime_t MT;
                String spnString = spinner.getSelectedItem().toString();;
                if (spnString.equals("Breakfast"))
                    MT = Day.MealTime_t.BREAKFAST;
                else if(spnString.equals("Lunch"))
                    MT = Day.MealTime_t.LUNCH;
                else if(spnString.equals("Dinner"))
                    MT = Day.MealTime_t.DINNER;
                else
                    MT = Day.MealTime_t.SNACK;

                day.addFood(MT, food); //this adds the food to the USERS day
                accessEdible.addEdible(food); //this adds the food to the foods database.

                Intent intent = new Intent(AddFoodActivity.this, TimelineActivity.class);
                intent.putExtra("username", username);
                AddFoodActivity.this.startActivity(intent);

            }
        });
    }
}