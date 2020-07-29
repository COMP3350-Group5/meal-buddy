package comp3350.mealbuddy.presentation;

import androidx.appcompat.app.AppCompatActivity;
import comp3350.mealbuddy.R;
import comp3350.mealbuddy.business.AccessAccount;
import comp3350.mealbuddy.business.AccessEdible;
import comp3350.mealbuddy.objects.Day;
import comp3350.mealbuddy.objects.Exercise;
import comp3350.mealbuddy.objects.consumables.Edible;
import comp3350.mealbuddy.objects.consumables.Food;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

public class AddExerciseActivity extends AppCompatActivity {

    AccessAccount accessAccount;
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
        setContentView(R.layout.activity_add_exercise);
        dialog = new Dialog(this);
        //get the passed values from the previous activity
        int dayOfYear = this.getIntent().getIntExtra("dayOfYear", -1);
        final String username = this.getIntent().getStringExtra("username");

        //get the day to display
        accessAccount = new AccessAccount();
        final Day day = accessAccount.getDay(username, dayOfYear);

        //obtain all the UI components to grab values from
        Button submit = findViewById(R.id.btnAddExercise);
        final EditText name = findViewById(R.id.etFoodName);
        Spinner spinner = dialog.findViewById(R.id.spnIntensity);
        final EditText duration = findViewById(R.id.etDuration);


        //on submit we want to create a new exercise
        submit.setOnClickListener ((view) -> {
            String spnString = spinner.getSelectedItem().toString();
            Exercise.Intensity IT;
            if (spnString.equals("Low"))
                IT = Exercise.Intensity.Low;
            else if(spnString.equals("Medium"))
                IT = Exercise.Intensity.Medium;
            else
                IT = Exercise.Intensity.High;

            Exercise exercise = new Exercise(name.getText().toString(), Double.parseDouble(duration.getText().toString()), IT);
            day.addExercise(exercise);
            accessAccount.updateDay(username, day);

            //go back to the timeline activity and pass the username
            Intent intent = new Intent(AddExerciseActivity.this, TimelineActivity.class);
            intent.putExtra("username", username);
            AddExerciseActivity.this.startActivity(intent);

        });
    }

}