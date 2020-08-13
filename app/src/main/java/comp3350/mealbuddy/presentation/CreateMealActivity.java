/****************************************
 * CreateMealActivity
 * the meal activity
 ****************************************/
package comp3350.mealbuddy.presentation;

import android.app.Dialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.SparseBooleanArray;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import comp3350.mealbuddy.R;
import comp3350.mealbuddy.business.AccessEdible;
import comp3350.mealbuddy.business.AccessLabel;
import comp3350.mealbuddy.objects.consumables.Edible;
import comp3350.mealbuddy.objects.consumables.Meal;

public class CreateMealActivity extends AppCompatActivity {

    Dialog dialog;

    //items passed
    int dayOfYear;
    String username;

    //buttons on screen
    EditText mealTitle;
    EditText labels;
    ListView foodList;
    Button addMeal;

    int[] edibleQuantites;
    List<Edible> edibles;
    AccessEdible accessEdible;
    AccessLabel accessLabel;
    ArrayAdapter<String> adapter;

    /*
     * onCreate
     * called when the activity is initially created
     * Parameters:
     *     @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_build_meal);
        accessLabel = new AccessLabel();


        dialog = new Dialog(this);
        accessEdible = new AccessEdible();

        //get the items passed
        dayOfYear = this.getIntent().getIntExtra("dayOfYear", -1);
        username = this.getIntent().getStringExtra("username");

        //Get the objects on the screen
        mealTitle = findViewById(R.id.mealName);
        labels = findViewById(R.id.mealLabels);
        foodList = findViewById(R.id.foodList);
        addMeal = findViewById(R.id.btnAdd);

        //Fill the list with edibles
        accessEdible = new AccessEdible();
        edibles = accessEdible.getEdibles();
        ArrayList<String> ediblesString = new ArrayList<>();
        for (Edible e : edibles) {
            ediblesString.add(e.name);
        }

        edibleQuantites = new int[edibles.size()];

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_multiple_choice, ediblesString);
        foodList.setAdapter(adapter);

        foodList.setOnItemClickListener((parent, view, pos, id) -> {
            if (foodList.isItemChecked(pos))
                showPopUp(pos, edibleQuantites);
        });

        //OnSubmit
        addMeal.setOnClickListener((view) -> {
            if (validateInput(mealTitle)) {
                String mealName = mealTitle.getText().toString();
                ArrayList<String> labelList = getLabels();
                Meal newMeal = createNewMeal(mealName, labelList);
                accessEdible.addEdible(newMeal);
                ChangeActivityHelper.changeActivity(CreateMealActivity.this, SearchFoodActivity.class, username, dayOfYear);
            }
        });
    }

    /*
     * getLabels
     * gets the labels inputed by user.  These will only be the labesl the new meal has
     * Also adds any labels not in db to db
     * Return: list of labels
     */
    private ArrayList<String> getLabels() {
        ArrayList<String> labelList = new ArrayList<>(Arrays.asList(labels.getText().toString().split(",")));
        for (String label : labelList) {
            if (!accessLabel.labelExists(label)) {
                accessLabel.addLabel(label.trim());
            }
        }
        return labelList;
    }

    /*
     * createNewMeal
     * creates the meal to add
     * Parameters:
     *      @param newMealName - new meal name
     *      @param labelList - list of labels to add to meal
     */
    private Meal createNewMeal(String newMealName, List<String> labelList) {
        Meal newMeal = new Meal(newMealName, labelList);
        int quanitty;
        List<Edible> ediblesSelected = getEdiblesSelected();
        for (Edible e : ediblesSelected) {
            quanitty = edibleQuantites[adapter.getPosition(e.name)];
            newMeal.add(e, quanitty);
        }
        return newMeal;
    }


    /*
     * getEdiblesSelected
     * Return: returns a list of all edibles selected
     */
    private ArrayList<Edible> getEdiblesSelected() {
        ArrayList<Edible> selectedEdibles = new ArrayList<>();
        Edible edible;
        SparseBooleanArray checkedItems = foodList.getCheckedItemPositions();
        if (checkedItems != null && checkedItems.size() > 0) {
            for (int i = 0; i < checkedItems.size(); i++) {
                int ediblePos = checkedItems.keyAt(i);
                edible = edibles.get(ediblePos);
                if (checkedItems.get(ediblePos))
                    selectedEdibles.add(edible);
            }
        }
        return selectedEdibles;
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
            }
            else if (Integer.parseInt(quantity.getText().toString()) == 0) {
                quantity.setError("Quantity cannot be 0.");
            }
            else {
                quantities[pos] = Integer.parseInt(quantity.getText().toString());
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private boolean validateInput(EditText name) {
        boolean inputsValid = true;

        if (TextUtils.isEmpty(name.getText())) {
            name.setError("Meal name cannot be empty.");
            inputsValid = false;
        }
        if (accessEdible.edibleExists(name.getText().toString())) {
            name.setError("An edible with this name already exists!");
            inputsValid = false;
        }

        return inputsValid;
    }


}
