package comp3350.mealbuddy.presentation;

import android.os.Bundle;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import comp3350.mealbuddy.R;
import comp3350.mealbuddy.business.AccessLabel;

public class LabelActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_label);

        AccessLabel accessLabel = new AccessLabel();
        ArrayList<String> labels = accessLabel.getLabels();

        ListView labelListView = findViewById(R.id.labelListView);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_single_choice, labels);
        labelListView.setAdapter(adapter);
        labelListView.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);

        // goes to label activity
        Button addLabel = findViewById(R.id.labelBtnAdd);

        addLabel.setOnClickListener((view) -> {
            TextView test = findViewById(R.id.testtest);
            String l = labelListView.getItemAtPosition(labelListView.getCheckedItemPosition()).toString();
            test.setText(l);
        });

    }
}