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
import comp3350.mealbuddy.objects.consumables.Food;
import comp3350.mealbuddy.objects.consumables.Meal;

public class UpdateMealActivity extends AppCompatActivity {

    Dialog dialog;

    //items passed
    int dayOfYear;
    String username;
    String oldMealName;

    //buttons on screen
    TextView mealNameTitle;
    EditText newMealNameTitle;
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
        setContentView(R.layout.activity_update_meal);

        accessLabel = new AccessLabel();


        dialog = new Dialog(this);

        //get the items passed
        dayOfYear = this.getIntent().getIntExtra("dayOfYear", -1);
        username = this.getIntent().getStringExtra("username");
        oldMealName = this.getIntent().getStringExtra("mealName");

        //Get the objects on the screen
        mealNameTitle = findViewById(R.id.txtBuilder);
        mealNameTitle.setText("Updating: " + oldMealName);
        newMealNameTitle = findViewById(R.id.newMealName);
        labels = findViewById(R.id.mealUpdatingLabels);
        foodList = findViewById(R.id.foodListUpdating);
        addMeal = findViewById(R.id.btnUpdateMeal);

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
                showSelectQuantityPopup(pos);
        });

        //OnSubmit
        addMeal.setOnClickListener((view) -> {
            String newMealName = newMealNameTitle.getText().toString();
            if (isValidInputs(newMealName)) {
                List<String> labelList = getLabels();
                Meal newMeal = createUpdatedMeal(newMealName, labelList);
                accessEdible.updateEdible(oldMealName, newMeal);
                ChangeActivityHelper.changeActivity(this, SearchFoodActivity.class, username, dayOfYear);
            }
        });

    }

    /*
     * getLabels
     * gets the labels inputed by user.  These will only be the labesl the updated meal has
     * Return: list of labels
     */
    private List<String> getLabels() {
        ArrayList<String> labelList = new ArrayList<>(Arrays.asList(labels.getText().toString().split(",")));
        for (String label : labelList) {
            if (!accessLabel.labelExists(label)) {
                accessLabel.addLabel(label.trim());
            }
        }
        return labelList;
    }

    /*
     * isValidInputs
     * checks if the inputs for the updated meal are valid. else it displays the error
     * Parameters:
     *      @param newMealName - the updated meal name
     * Return: True if the inputs are valid
     */
    private boolean isValidInputs(String newMealName) {
        boolean isValid = true;
        if (TextUtils.isEmpty(newMealNameTitle.getText())) {
            newMealNameTitle.setError("Meal must be given a name to update to");
            isValid = false;
        } else if (newMealName == null) {
            newMealNameTitle.setError("Meal must be given a name to update to");
            isValid = false;
        } else if (accessEdible.edibleExists(newMealName) && !newMealName.equals(oldMealName)) {
            newMealNameTitle.setError("An edible with this name already exists!");
            isValid = false;
        } else if (containsItself(oldMealName)) {
            newMealNameTitle.setError("A Meal may not contain itself!");
            isValid = false;
        }
        return isValid;
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
     * containsItself
     * checks if a meal conatins itself
     * Parameters:
     *      @param newMealName - the updated meal name
     * Return: True if we are trying to add a meal to itself
     */
    private boolean containsItself(String oldMealName) {
        List<Edible> selectedEdibles = getEdiblesSelected();
        return selectedEdibles.contains(new Food(oldMealName));
    }

    /*
     * createUpdatedMeal
     * creates the meal to update the old one to
     * Parameters:
     *      @param newMealName - new meal name
     *      @param labelList - list of labels to add to meal
     */
    private Meal createUpdatedMeal(String newMealName, List<String> labelList) {
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
     * showPopUp
     * shows the pop up with the edible to select quantity
     * Parameters:
     *      @param pos - pos of edible we selected in list
     */
    public void showSelectQuantityPopup(int pos) {
        dialog.setContentView(R.layout.choose_quantites);

        EditText quantity = dialog.findViewById(R.id.edibleQuantity);
        Button confirmBtn = dialog.findViewById(R.id.confirmBtn);

        confirmBtn.setOnClickListener((view) -> {
            if (TextUtils.isEmpty(quantity.getText())) {
                quantity.setError("Quantity is required.");
            } else if (Integer.parseInt(quantity.getText().toString()) == 0) {
                quantity.setError("Quantity cannot be 0.");
            } else {
                edibleQuantites[pos] = Integer.parseInt(quantity.getText().toString());
                dialog.dismiss();
            }
        });
        dialog.show();
    }

}