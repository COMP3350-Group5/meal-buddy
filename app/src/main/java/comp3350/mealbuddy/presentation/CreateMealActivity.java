package comp3350.mealbuddy.presentation;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import comp3350.mealbuddy.R;
import comp3350.mealbuddy.business.AccessEdible;
import comp3350.mealbuddy.business.AccessLabel;
import comp3350.mealbuddy.objects.consumables.Edible;

public class CreateMealActivity extends AppCompatActivity {

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

        //Get the listview
        ListView foodList = findViewById(R.id.foodList);

        AccessEdible accessEdible = new AccessEdible();
        List<Edible> edibles = accessEdible.getEdibles();
        List<String> ediblesString = new ArrayList();
        for (Edible e : edibles) {
            ediblesString.add(e.name);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_multiple_choice, ediblesString);
        foodList.setAdapter(adapter);


    }


}
