package comp3350.mealbuddy.presentation;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import comp3350.mealbuddy.R;
import comp3350.mealbuddy.business.AccessEdible;
import comp3350.mealbuddy.objects.consumables.Edible;

public class CreateMeal extends AppCompatActivity {

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
        setContentView(R.layout.activity_create_meal);


        dialog = new Dialog(this);

        //get the items passed
        int dayOfYear = this.getIntent().getIntExtra("dayOfYear", -1);
        final String username = this.getIntent().getStringExtra("username");

        //Get the objects on the screen
        EditText mealTitle = findViewById(R.id.mealName);
        ListView foodList = findViewById(R.id.foodList);
        Button addMeal = findViewById(R.id.btnAdd);

        //Fill the list with edibles
        AccessEdible accessEdible = new AccessEdible();
        List<Edible> edibles = accessEdible.getEdibles();
        ArrayList<String> ediblesString = new ArrayList<>();
        for (Edible e : edibles) {
            ediblesString.add(e.name);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_multiple_choice, ediblesString);
        foodList.setAdapter(adapter);

        foodList.setOnItemClickListener((parent, view, pos, id) -> showPopUp());

        //OnSubmit
        addMeal.setOnClickListener((view) -> {
//            String mealName = mealTitle.getText().toString();
//            Meal newMeal = new Meal(mealName);
//            SparseBooleanArray checkedItems = foodList.getCheckedItemPositions();
//            for (int i = 0; i < checkedItems.size(); i++) {
//                newMeal.add(edibles.get(checkedItems.keyAt(i)));
//            }
//            accessEdible.addEdible(newMeal);
//            ChangeActivityHelper.changeActivity(CreateMealActivity.this, SearchFoodActivity.class, username, dayOfYear);
            showPopUp();
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
    public void showPopUp() {
        dialog.setContentView(R.layout.choose_quantites);

        EditText quantity = dialog.findViewById(R.id.edibleQuantity);
        Button confirmBtn = dialog.findViewById(R.id.confirmBtn);

        confirmBtn.setOnClickListener((dialog) -> {
            quantity.setError("Error");
            ChangeActivityHelper.changeActivity(CreateMeal.this, CreateMeal.class);
        });
        dialog.show();
    }
}