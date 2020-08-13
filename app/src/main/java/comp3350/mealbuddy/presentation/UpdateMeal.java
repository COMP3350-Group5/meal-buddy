package comp3350.mealbuddy.presentation;

import android.app.Dialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.SparseBooleanArray;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import comp3350.mealbuddy.R;
import comp3350.mealbuddy.business.AccessEdible;
import comp3350.mealbuddy.business.AccessLabel;
import comp3350.mealbuddy.objects.consumables.Edible;
import comp3350.mealbuddy.objects.consumables.Meal;

public class UpdateMeal extends AppCompatActivity {

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
        setContentView(R.layout.activity_update_meal);

        AccessLabel accessLabel = new AccessLabel();


        dialog = new Dialog(this);

        //get the items passed
        int dayOfYear = this.getIntent().getIntExtra("dayOfYear", -1);
        final String username = this.getIntent().getStringExtra("username");
        final String oldMealName = this.getIntent().getStringExtra("mealName");

        //Get the objects on the screen
        TextView mealNameTitle = findViewById(R.id.txtBuilder);
        mealNameTitle.setText("Updating: " + oldMealName);
        EditText mealTitle = findViewById(R.id.newMealName);
        EditText labels = findViewById(R.id.meatUpdatingLabels);
        ListView foodList = findViewById(R.id.foodListUpdating);
        Button addMeal = findViewById(R.id.btnUpdateMeal);

        //Fill the list with edibles
        AccessEdible accessEdible = new AccessEdible();
        List<Edible> edibles = accessEdible.getEdibles();
        ArrayList<String> ediblesString = new ArrayList<>();
        for (Edible e : edibles) {
            ediblesString.add(e.name);
        }

        int[] edibleQuantites = new int[edibles.size()];

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_multiple_choice, ediblesString);
        foodList.setAdapter(adapter);

        foodList.setOnItemClickListener((parent, view, pos, id) -> {
            if (foodList.isItemChecked(pos))
                showPopUp(pos, edibleQuantites);
        });

        //OnSubmit
        addMeal.setOnClickListener((view) -> {
            String newMealName = mealTitle.getText().toString();
            if (accessEdible.edibleExists(newMealName)) {
                mealTitle.setError("An edible with this name already exists!");
            } else {
                ArrayList<String> labelList = new ArrayList<>(Arrays.asList(labels.getText().toString().split(",")));
                for (String label : labelList) {
                    accessLabel.addLabel(label.trim());
                }

                Meal newMeal = new Meal(newMealName, labelList);
                SparseBooleanArray checkedItems = foodList.getCheckedItemPositions();
                for (int i = 0; i < checkedItems.size(); i++) {
                    int ediblePosition = checkedItems.keyAt(i);
                    int edibleQuantity = edibleQuantites[ediblePosition];
                    newMeal.add(edibles.get(ediblePosition), edibleQuantity);
                }
                accessEdible.updateEdible(oldMealName, newMeal);
                ChangeActivityHelper.changeActivity(this, SearchFoodActivity.class, username, dayOfYear);
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
    public void showPopUp(int pos, int[] quantities) {
        dialog.setContentView(R.layout.choose_quantites);

        EditText quantity = dialog.findViewById(R.id.edibleQuantity);
        Button confirmBtn = dialog.findViewById(R.id.confirmBtn);

        confirmBtn.setOnClickListener((view) -> {
            if (TextUtils.isEmpty(quantity.getText())) {
                quantity.setError("Quantity is required.");
            } else if (Integer.parseInt(quantity.getText().toString()) == 0) {
                quantity.setError("Quantity cannot be 0.");
            } else {
                quantities[pos] = Integer.parseInt(quantity.getText().toString());
                dialog.dismiss();
            }
        });
        dialog.show();
    }

}