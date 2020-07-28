package comp3350.mealbuddy.presentation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import comp3350.mealbuddy.R;
import comp3350.mealbuddy.business.AccessEdible;
import comp3350.mealbuddy.objects.consumables.Edible;

public class SearchFoodActivity extends AppCompatActivity {

    //initializing the variables
    ArrayList<String> foodNames = new ArrayList<>();
    ArrayAdapter<String> stringArrayAdapter;
    ListView listview;
    AccessEdible accessEdible;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_food_acitivity);

        //get the passed values from the previous activity
        int dayOfYear = this.getIntent().getIntExtra("dayOfYear", -1);
        final String username = this.getIntent().getStringExtra("username");

        accessEdible = new AccessEdible();
        listview = findViewById(R.id.lvSearchbar);

        for (Edible e: accessEdible.getEdibles()){
            foodNames.add(e.name);
        }

        stringArrayAdapter = new ArrayAdapter<>(SearchFoodActivity.this, android.R.layout.simple_list_item_1, foodNames);

        listview.setAdapter(stringArrayAdapter);

        listview.setOnItemClickListener((parent, view, pos, id) -> {
            System.err.println("clicked" + stringArrayAdapter.getItem(pos));

        });

    }

    public 
}