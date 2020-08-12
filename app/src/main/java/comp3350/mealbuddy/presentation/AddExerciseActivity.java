/****************************************
 * AddExerciseActivity
 * the exercise activity
 ****************************************/
package comp3350.mealbuddy.presentation;

import android.app.Dialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import comp3350.mealbuddy.R;
import comp3350.mealbuddy.business.AccessAccount;
import comp3350.mealbuddy.objects.Day;
import comp3350.mealbuddy.objects.Exercise;

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
        final EditText name = findViewById(R.id.etExerciseName);
        Spinner spinner = findViewById(R.id.spnIntensity);
        final EditText duration = findViewById(R.id.etDuration);


        //on submit we want to create a new exercise
        submit.setOnClickListener((view) -> {
            if (checkInputs(name, duration)){
                String spnString = spinner.getSelectedItem().toString();
                Exercise.Intensity IT = getIntensity(spnString);
                Exercise exercise = new Exercise(name.getText().toString(), Double.parseDouble(duration.getText().toString()), IT);
                day.addExercise(exercise);
                accessAccount.updateDay(username, day);

                //go back to the timeline activity and pass the username
                ChangeActivityHelper.changeActivity(AddExerciseActivity.this, TimelineActivity.class, username, dayOfYear);
            }
        });
    }

    /*
     * getIntensity
     * returns the intensity value from the passed string
     */
    private Exercise.Intensity getIntensity(String value) {
        Exercise.Intensity IT;
        if (value.equals("Low"))
            IT = Exercise.Intensity.Low;
        else if (value.equals("Medium"))
            IT = Exercise.Intensity.Medium;
        else
            IT = Exercise.Intensity.High;
        return IT;
    }

    /*
     * Check if the input fields are valid.
     */
    private boolean checkInputs(EditText name, EditText duration) {
        boolean inputsValid = true;

        if (TextUtils.isEmpty(name.getText())) {
            name.setError("Name is required");
            inputsValid = false;
        }

        if (TextUtils.isEmpty(duration.getText())) {
            duration.setError("Duration is required");
            inputsValid = false;
        }

        return inputsValid;
    }

}