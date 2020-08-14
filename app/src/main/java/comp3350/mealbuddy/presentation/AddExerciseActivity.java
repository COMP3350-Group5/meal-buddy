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

    private AccessAccount accessAccount;
    private Dialog dialog;
    private Day day;
    private int dayOfYear;
    private String username;
    private Button submit;
    private Spinner intensitySpinner;
    private EditText exerciseName;
    private EditText duration;

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
        dayOfYear = this.getIntent().getIntExtra("dayOfYear", -1);
        username = this.getIntent().getStringExtra("username");

        //get the day to display
        accessAccount = new AccessAccount();
        day = accessAccount.getDay(username, dayOfYear);

        //obtain all the UI components to grab values from
        submit = findViewById(R.id.btnAddExercise);
        exerciseName = findViewById(R.id.etExerciseName);
        intensitySpinner = findViewById(R.id.spnIntensity);
        duration = findViewById(R.id.etDuration);


        //on submit we want to create a new exercise
        submit.setOnClickListener((view) -> {
            if (validateInput()) {
                String spnString = intensitySpinner.getSelectedItem().toString();
                Exercise.Intensity intensity = getIntensity(spnString);
                Exercise exercise = new Exercise(exerciseName.getText().toString(), Double.parseDouble(duration.getText().toString()), intensity);
                day.addExercise(exercise);
                accessAccount.updateDay(username, day);

                //go back to the timeline activity and pass the username and day of year
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
    private boolean validateInput() {
        boolean inputsValid = true;

        if (TextUtils.isEmpty(exerciseName.getText())) {
            exerciseName.setError("An exercise name is required");
            inputsValid = false;
        }


        if (TextUtils.isEmpty(duration.getText())) {
            duration.setError("A duration is required");
            inputsValid = false;
        } else if (Double.parseDouble(duration.getText().toString()) == 0.0) {
            duration.setError("Duration must be larger than zero.");
            inputsValid = false;
        }

        return inputsValid;
    }

}