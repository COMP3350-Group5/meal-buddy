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

    private Button removeLabelBtn;
    private Button addLabelBtn;
    private Button updateLabelBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_label);

        AccessLabel accessLabel = new AccessLabel();
        ArrayList<String> labels = accessLabel.getLabels();

        ListView labelListView = findViewById(R.id.labelListView);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.select_dialog_singlechoice, labels);
        adapter.sort(String::compareTo);
        labelListView.setAdapter(adapter);
        labelListView.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);

        addLabelBtn = findViewById(R.id.labelBtnAdd);
        removeLabelBtn = findViewById(R.id.labelBtnRemove);
        updateLabelBtn = findViewById(R.id.labelBtnUpdate);


        labelListView.setOnItemClickListener((parent, view, pos, id) -> {
            enableButtons();
        });

        addLabelBtn.setOnClickListener((view) -> {
            TextView addText = findViewById(R.id.labelAddLabelText);
            String newLabel = addText.getText().toString();
            if (adapter.getPosition(newLabel) < 0) {    //make sure it doesnt already exist
                if (adapter.getCount() == 0) {
                    enableButtons();
                }
                accessLabel.addLabel(newLabel);
                adapter.add(newLabel);
                adapter.sort(String::compareTo);
                addText.setText("");
            } else {
                addText.setError("This Label Already Exists!");
            }
        });

        removeLabelBtn.setOnClickListener((view) -> {
            String labelToRemove = labelListView.getItemAtPosition(labelListView.getCheckedItemPosition()).toString();
            accessLabel.removeLabel(labelToRemove);
            adapter.remove(labelToRemove);
            if (adapter.getCount() == 0) {
                disableButtons();
            }
        });

        updateLabelBtn.setOnClickListener((view) -> {
            TextView updateText = findViewById(R.id.labelUpdateLabelText);
            String oldName = labelListView.getItemAtPosition(labelListView.getCheckedItemPosition()).toString();
            String newName = updateText.getText().toString();
            if (adapter.getPosition(newName) < 0) {

                accessLabel.updateLabel(oldName, newName);
                adapter.remove(oldName);
                adapter.add(newName);
                adapter.sort(String::compareTo);
                updateText.setText("");
            } else {
                updateText.setError("The Name To Be Updated To Is Already In Use!");
            }
        });
    }


    /*
     * disableButtons
     * disables buttons that require a label selected
     */
    private void disableButtons() {
        updateLabelBtn.setEnabled(false);
        removeLabelBtn.setEnabled(false);
    }


    /*
     * disableButtons
     * disables buttons that require a label selected
     */
    private void enableButtons() {
        updateLabelBtn.setEnabled(true);
        removeLabelBtn.setEnabled(true);
    }

}