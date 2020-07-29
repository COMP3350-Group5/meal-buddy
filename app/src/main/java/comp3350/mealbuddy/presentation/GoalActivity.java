/****************************************
 * GoalActivity
 * Front end for showing goals
 ****************************************/
package comp3350.mealbuddy.presentation;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import java.lang.reflect.Array;
import java.util.List;

import comp3350.mealbuddy.R;
import comp3350.mealbuddy.business.AccessAccount;
import comp3350.mealbuddy.business.AccessLabel;
import comp3350.mealbuddy.business.Calculator;
import comp3350.mealbuddy.business.GoalTracker;
import comp3350.mealbuddy.objects.Day;
import comp3350.mealbuddy.objects.consumables.Edible;
import comp3350.mealbuddy.objects.goals.CalorieGoal;
import comp3350.mealbuddy.objects.goals.Goal;
import comp3350.mealbuddy.objects.goals.LabelGoal;
import comp3350.mealbuddy.objects.goals.MacroGoal;
import comp3350.mealbuddy.objects.goals.MicroGoal;

public class GoalActivity extends AppCompatActivity {

    private AccessAccount accessAccount;
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
        setContentView(R.layout.activity_goals);
        dialog = new Dialog(this);

        //get the passed values from the previous activity
        accessAccount = new AccessAccount();
        int dayOfYear = this.getIntent().getIntExtra("dayOfYear", -1);
        final String username = this.getIntent().getStringExtra("username");

        //Get the day, create a calculator
        AccessAccount accessAccount = new AccessAccount();
        final Day day = accessAccount.getDay(username, dayOfYear);
        Calculator calculator = new Calculator(day);

        //Update Goals Card
        TextView goalText = findViewById(R.id.txtGoalsTitle);
        goalText.setText("Goals for Day " + day.dayOfYear);

        //Update Goals body
        TextView goalBody = findViewById(R.id.txtGoals);
        goalBody.setText(day.getGoalString());

        //Update Goals Passed Title
        TextView passedGoalsText = findViewById(R.id.txtPassedGoalsTitle);
        passedGoalsText.setText("Goals Passed for Day " + day.dayOfYear);

        //Update Goals Passed Body
        TextView passedGoalsBody = findViewById(R.id.txtPassedGoals);
        String passedGoalsString = "";
        List<Goal> passedGoals = GoalTracker.getPassedGoals(calculator, day.goals);
        for (Goal g : passedGoals) {
            passedGoalsString += g;
        }
        passedGoalsBody.setText(passedGoalsString);

        //Add Goal Button
        Button addGoal = findViewById(R.id.btnAddGoal);
        addGoal.setOnClickListener((view) -> {
            showPopUp(username, dayOfYear);
        });

        //Back to timeline
        Button backButton = findViewById(R.id.btnTimeline);
        backButton.setOnClickListener((view) -> {
            Intent intent = new Intent(GoalActivity.this, TimelineActivity.class);
            intent.putExtra("username", username);
            GoalActivity.this.startActivity(intent);
        });


    }

    public void showPopUp(String userName, int dayOfYear) {
        dialog.setContentView(R.layout.pop_up_goal);

        Button btnContinue = dialog.findViewById(R.id.btnContinue);
        Spinner spinner = dialog.findViewById(R.id.spnGoalType);
        btnContinue.setOnClickListener((view) -> {
            String spnString = spinner.getSelectedItem().toString();
            //find the right goal popup
            if (spnString.equals("Calorie Goal"))
                showCaloriePopUp(userName, dayOfYear);
            else if (spnString.equals("Label Goal"))
                showLabelPopUp(userName, dayOfYear);
            else if (spnString.equals("Macro Goal"))
                showMacroPopUp(userName, dayOfYear);
            else
                showMicroPopUp(userName, dayOfYear);
        });

        dialog.show();
    }

    public void showCaloriePopUp(String userName, int dayOfYear) {
        dialog.setContentView(R.layout.add_calorie_goal);

        EditText lowerBound = dialog.findViewById(R.id.lowerBound);
        EditText upperBound = dialog.findViewById(R.id.upperBound);
        Button btnContinue = dialog.findViewById(R.id.btnContinue);
        btnContinue.setOnClickListener((view) -> {
            Day day = accessAccount.getDay(userName, dayOfYear);
            day.goals.add(new CalorieGoal(Integer.parseInt(lowerBound.getText().toString()), Integer.parseInt(upperBound.getText().toString())));
            accessAccount.updateDay(userName, day);

            //Go back to the goal activity
            Intent intent = new Intent(GoalActivity.this, GoalActivity.class);
            intent.putExtra("dayOfYear", dayOfYear);
            intent.putExtra("username", userName);
            GoalActivity.this.startActivity(intent);
        });

        dialog.show();
    }

    public void showLabelPopUp(String userName, int dayOfYear) {
        dialog.setContentView(R.layout.add_label_goal);

        //Set up the objects on screen
        EditText lowerBound = dialog.findViewById(R.id.lowerBound);
        EditText upperBound = dialog.findViewById(R.id.upperBound);
        Spinner goalType = dialog.findViewById(R.id.spnType);
        Spinner labelSpinner = dialog.findViewById(R.id.label);

        //Populate the labels
        AccessLabel accessLabel = new AccessLabel();
        List<String> labels = accessLabel.getLabels();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, labels);
        labelSpinner.setAdapter(adapter);

        //Submit the goal
        Button btnContinue = dialog.findViewById(R.id.btnContinue);
        btnContinue.setOnClickListener((view) -> {
            Day day = accessAccount.getDay(userName, dayOfYear);
            day.goals.add(new LabelGoal(Integer.parseInt(lowerBound.getText().toString()), Integer.parseInt(upperBound.getText().toString()), Goal.GoalType.valueOf(goalType.getSelectedItem().toString()), labelSpinner.getSelectedItem().toString()));
            accessAccount.updateDay(userName, day);

            //Go back to the goal activity
            Intent intent = new Intent(GoalActivity.this, GoalActivity.class);
            intent.putExtra("dayOfYear", dayOfYear);
            intent.putExtra("username", userName);
            GoalActivity.this.startActivity(intent);
        });

        dialog.show();
    }

    public void showMacroPopUp(String userName, int dayOfYear) {
        dialog.setContentView(R.layout.add_macro_goal);

        EditText lowerBound = dialog.findViewById(R.id.lowerBound);
        EditText upperBound = dialog.findViewById(R.id.upperBound);
        Spinner goalType = dialog.findViewById(R.id.spnType);
        Spinner selectedMacro = dialog.findViewById(R.id.spnMacro);

        Button btnContinue = dialog.findViewById(R.id.btnContinue);
        btnContinue.setOnClickListener((view) -> {
            Day day = accessAccount.getDay(userName, dayOfYear);
            day.goals.add(new MacroGoal(Integer.parseInt(lowerBound.getText().toString()), Integer.parseInt(upperBound.getText().toString()), Goal.GoalType.valueOf(goalType.getSelectedItem().toString()), Edible.Macros.valueOf(selectedMacro.getSelectedItem().toString())));
            accessAccount.updateDay(userName, day);

            //Go back to the goal activity
            Intent intent = new Intent(GoalActivity.this, GoalActivity.class);
            intent.putExtra("dayOfYear", dayOfYear);
            intent.putExtra("username", userName);
            GoalActivity.this.startActivity(intent);
        });

        dialog.show();
    }

    public void showMicroPopUp(String userName, int dayOfYear) {
        dialog.setContentView(R.layout.add_micro_goal);

        EditText lowerBound = dialog.findViewById(R.id.lowerBound);
        EditText upperBound = dialog.findViewById(R.id.upperBound);
        Spinner spinner = dialog.findViewById(R.id.spnMicro);;

        Button btnContinue = dialog.findViewById(R.id.btnContinue);
        btnContinue.setOnClickListener((view) -> {
            Day day = accessAccount.getDay(userName, dayOfYear);
            day.goals.add(new MicroGoal(Integer.parseInt(lowerBound.getText().toString()), Integer.parseInt(upperBound.getText().toString()), Edible.Micros.valueOf(spinner.getSelectedItem().toString())));
            accessAccount.updateDay(userName, day);

            //Go back to the goal activity
            Intent intent = new Intent(GoalActivity.this, GoalActivity.class);
            intent.putExtra("dayOfYear", dayOfYear);
            intent.putExtra("username", userName);
            GoalActivity.this.startActivity(intent);
        });

        dialog.show();
    }

}
