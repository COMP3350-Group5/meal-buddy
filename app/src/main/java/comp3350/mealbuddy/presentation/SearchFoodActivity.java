/****************************************
 * SearchFoodActivity
 * the search food activity
 ****************************************/
package comp3350.mealbuddy.presentation;

import android.app.Dialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import comp3350.mealbuddy.R;
import comp3350.mealbuddy.business.AccessAccount;
import comp3350.mealbuddy.business.AccessEdible;
import comp3350.mealbuddy.objects.Day;
import comp3350.mealbuddy.objects.consumables.Edible;

public class SearchFoodActivity extends AppCompatActivity {


    //initializing the variables
    Dialog dialog;
    ArrayList<String> foodNames = new ArrayList<>();
    ArrayAdapter<String> stringArrayAdapter;
    ListView listview;
    AccessEdible accessEdible;
    AccessAccount accessAccount;
    FloatingActionButton fabAdd;
    FloatingActionButton fabMeal;
    FloatingActionButton fabFood;
    boolean isFabOpen = false;

    /*
     * onCreate
     * called when the activity is initially created
     * Parameters:
     *     @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_food_activity);
        dialog = new Dialog(this);

        //get the passed values from the previous activity
        int dayOfYear = this.getIntent().getIntExtra("dayOfYear", -1);
        final String username = this.getIntent().getStringExtra("username");

        //set up the access methods
        accessEdible = new AccessEdible();
        accessAccount = new AccessAccount();

        fabAdd = findViewById(R.id.fabAdd);
        fabFood = findViewById(R.id.fabFood);
        fabMeal = findViewById(R.id.fabMeal);

        setListAdapterToDbEdibles();
        //call back for clicking a list item
        listview.setOnItemClickListener((parent, view, pos, id) -> showPopUp(accessEdible.getEdible(stringArrayAdapter.getItem(pos).trim()), username, dayOfYear));

        fabAdd.setOnClickListener((view) -> {
            if (!isFabOpen) showFABMenu();
            else closeFABMenu();
        });

        fabFood.setOnClickListener((view) -> ChangeActivityHelper.changeActivity(SearchFoodActivity.this, AddFoodActivity.class, username, dayOfYear));

        fabMeal.setOnClickListener((view) -> ChangeActivityHelper.changeActivity(SearchFoodActivity.this, CreateMealActivity.class, username, dayOfYear));
    }

    /*
     * createEdibleList
     * sets the list view's adapter to the list of edible names in db
     */
    private void setListAdapterToDbEdibles() {
        List<Edible> allEdibles = accessEdible.getEdibles();
        listview = findViewById(R.id.lvSearchbar);
        for (Edible e : allEdibles)
            foodNames.add(e.name);
        Collections.sort(foodNames);

        //set up the adapter
        stringArrayAdapter = new ArrayAdapter<>(SearchFoodActivity.this, android.R.layout.simple_list_item_1, foodNames);
        listview.setAdapter(stringArrayAdapter);
    }


    /*
     * onCreateOptionsMenu
     * overrides the default menu creation options. adds a search bar to it.
     * Parameters:
     *     @param menu - the menu
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        addSearchBar(menu);
        return super.onCreateOptionsMenu(menu);
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
        Button confirm = dialog.findViewById(R.id.btnConfirm);
        EditText editText = dialog.findViewById(R.id.etQuantity);
        Button removeEdible = dialog.findViewById(R.id.removeEdibleFromDb);

        confirm.setOnClickListener((view) -> {
            if (TextUtils.isEmpty(editText.getText())) {
                editText.setError("Quantity is required.");
            }
            else if (Integer.parseInt(editText.getText().toString()) == 0) {
                editText.setError("Quantity cannot be 0.");
            }else {
                //add the food
                String spnString = spinner.getSelectedItem().toString();
                //find the meal time
                Day.MealTimeType MT = getMealTime(spnString);

                Day day = accessAccount.getDay(username, dayOfYear);

                //add the meal to the day and update the day in the user
                day.addToMeal(MT, edible, Integer.parseInt(editText.getText().toString()));
                accessAccount.updateDay(username, day);

                //go back to the timeline activity and pass the username
                ChangeActivityHelper.changeActivity(SearchFoodActivity.this, TimelineActivity.class, username, dayOfYear);
            }
        });

        removeEdible.setOnClickListener((view) -> {
            accessEdible.removeEdible(edible.name);
            stringArrayAdapter.remove(edible.name);
            dialog.dismiss();
        });

        titleText.setText("Adding Food: " + edible.name);
        dialog.show();
    }

    /*
     * addSearchBar
     * adds the search bar to the menu
     * Parameters:
     *      @param menu - menu
     */
    private void addSearchBar(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.search_menu, menu);

        MenuItem menuItem = menu.findItem(R.id.sbFood);
        @SuppressWarnings("deprecation") SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                //filter the adapter by the string
                stringArrayAdapter.getFilter().filter(s);
                return false;
            }
        });
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

    private void showFABMenu() {
        isFabOpen = true;
        fabFood.setVisibility(View.VISIBLE);
        fabMeal.setVisibility(View.VISIBLE);
        fabFood.animate().translationY(-getResources().getDimension(R.dimen.standard_65));
        fabMeal.animate().translationY(-getResources().getDimension(R.dimen.standard_130));
    }

    private void closeFABMenu() {
        isFabOpen = false;
        fabFood.animate().translationY(0);
        fabMeal.animate().translationY(0);
        fabFood.postDelayed(() -> fabFood.setVisibility(View.INVISIBLE), 300);
        fabMeal.postDelayed(() -> fabMeal.setVisibility(View.INVISIBLE), 300);

    }

}