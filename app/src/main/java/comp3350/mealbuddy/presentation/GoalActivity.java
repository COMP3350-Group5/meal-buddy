/****************************************
 * GoalActivity
 * Front end for showing goals
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
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import comp3350.mealbuddy.R;
import comp3350.mealbuddy.business.AccessAccount;
import comp3350.mealbuddy.business.AccessLabel;
import comp3350.mealbuddy.business.Calculator;
import comp3350.mealbuddy.business.GoalTracker;
import comp3350.mealbuddy.business.RecommendedGoalFactory;
import comp3350.mealbuddy.objects.Day;
import comp3350.mealbuddy.objects.consumables.Edible;
import comp3350.mealbuddy.objects.goals.CalorieGoal;
import comp3350.mealbuddy.objects.goals.Goal;
import comp3350.mealbuddy.objects.goals.LabelGoal;
import comp3350.mealbuddy.objects.goals.MacroGoal;
import comp3350.mealbuddy.objects.goals.MicroGoal;

public class GoalActivity extends AppCompatActivity {

    Dialog dialog;
    private AccessAccount accessAccount;
    private String username;
    private int dayOfYear;
    private Day day;

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
        dayOfYear = this.getIntent().getIntExtra("dayOfYear", -1);
        username = this.getIntent().getStringExtra("username");

        //Get the day, create a calculator
        AccessAccount accessAccount = new AccessAccount();
        day = accessAccount.getDay(username, dayOfYear);
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
        List<Goal> passedGoals = GoalTracker.getPassedGoals(calculator, day.getGoals());
        for (Goal g : passedGoals) {
            passedGoalsString += g;
        }
        passedGoalsBody.setText(passedGoalsString);

        //Add Goal Button
        FloatingActionButton addGoal = findViewById(R.id.fabAddGoal);
        addGoal.setOnClickListener((view) -> showPopUp());

        BottomNavigationView nav = findViewById(R.id.bottom_navigation);
        Menu menu = nav.getMenu();
        menu.getItem(ChangeActivityHelper.GOALS).setChecked(true);
        menu.getItem(ChangeActivityHelper.GOALS).setCheckable(false);

        ImageButton btnMore = findViewById(R.id.btnMore);
        btnMore.setOnClickListener( (view) -> {
            showPopup(view);
        });
    }

    private void showPopup(View view) {
        PopupMenu popup = new PopupMenu(this, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.goals_menu, popup.getMenu());
        popup.setOnMenuItemClickListener((MenuItem item) -> {
            switch(item.getItemId()) {
                case R.id.action_set_recommended_goals:
                    setRecommendedGoals();
                    break;
                case R.id.action_remove_goal:
                    showRemoveGoal();
                    break;
            }
            return true;
        });
        popup.show();
    }

    private void setRecommendedGoals() {
        day.removeAllGoals();
        List<Goal> goals = RecommendedGoalFactory.makeGoals(accessAccount.getUserInfo(username));
        for (Goal g : goals)
            day.addGoal(g);
        accessAccount.updateDay(username, day);
        ChangeActivityHelper.changeActivity(GoalActivity.this, GoalActivity.class, username, dayOfYear);
    }

    /*
     * showPopup
     * Show the popup to add a goal
     */
    public void showPopUp() {
        dialog.setContentView(R.layout.pop_up_goal);

        Button btnContinue = dialog.findViewById(R.id.btnContinue);
        Spinner spinner = dialog.findViewById(R.id.spnGoalType);

        btnContinue.setOnClickListener((view) -> {
            String spnString = spinner.getSelectedItem().toString();
            //find the right goal popup
            switch (spnString) {
                case "Calorie Goal":
                    showCaloriePopUp();
                    break;
                case "Label Goal":
                    showLabelPopUp();
                    break;
                case "Macro Goal":
                    showMacroPopUp();
                    break;
                default:
                    showMicroPopUp();
                    break;
            }
        });

        dialog.show();
    }

    /*
     * showMicroPopup
     * Show the popup to add a calorie goal
     */
    public void showCaloriePopUp() {
        dialog.setContentView(R.layout.add_calorie_goal);

        EditText lowerBound = dialog.findViewById(R.id.lowerBound);
        EditText upperBound = dialog.findViewById(R.id.upperBound);
        Button btnContinue = dialog.findViewById(R.id.btnContinue);
        btnContinue.setOnClickListener((view) -> {
            if (TextUtils.isEmpty(lowerBound.getText())) {
                lowerBound.setError("Lower bound is required");
            } else if (TextUtils.isEmpty(upperBound.getText())) {
                upperBound.setError("Upper bound is required");
            } else {
                addCalorieGoal(lowerBound, upperBound);
            }
        });

        dialog.show();
    }

    /*
     * addCalorieGoal
     * Adds the Calorie goal to the account and returns to goal activity if successful
     * Parameters
     *      @lowerBound - the Field containing the lower bound of the goal
     *      @upperBound - the Field containing the upper bound of the goal
     */
    private void addCalorieGoal(EditText lowerBound, EditText upperBound) {
        EditText errorField = dialog.findViewById(R.id.addCalGoalErrorBox);
        try {
            Day day = accessAccount.getDay(username, dayOfYear);
            CalorieGoal newGoal = new CalorieGoal(Integer.parseInt(lowerBound.getText().toString()), Integer.parseInt(upperBound.getText().toString()));
            if (day.containsGoal(newGoal)) {
                errorField.setVisibility(View.VISIBLE);
                errorField.setError("Day already contains a calorie goal");
            } else {
                day.addGoal(newGoal);
                accessAccount.updateDay(username, day);
                //Go back to the goal activity
                ChangeActivityHelper.changeActivity(GoalActivity.this, GoalActivity.class, username, dayOfYear);
            }
        } catch (IllegalArgumentException ie) {
            lowerBound.setError(ie.getMessage());
        }
    }

    /*
     * showMicroPopup
     * Show the popup to add a macro goal
     */
    public void showLabelPopUp() {
        dialog.setContentView(R.layout.add_label_goal);
        Spinner labelSpinner = dialog.findViewById(R.id.label);

        //Populate the labels
        AccessLabel accessLabel = new AccessLabel();
        List<String> labels = accessLabel.getLabels();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, labels);
        labelSpinner.setAdapter(adapter);

        //Set up the objects on screen
        EditText lowerBound = dialog.findViewById(R.id.lowerBound);
        EditText upperBound = dialog.findViewById(R.id.upperBound);


        //Submit the goal
        Button btnContinue = dialog.findViewById(R.id.btnContinue);
        btnContinue.setOnClickListener((view) -> {
            if (TextUtils.isEmpty(lowerBound.getText())) {
                lowerBound.setError("Lower bound is required");
            } else if (TextUtils.isEmpty(upperBound.getText())) {
                upperBound.setError("Upper bound is required");
            } else if (labels.size() == 0) {
                lowerBound.setError("No foods exist with a label");
            } else {
                addLabelGoal(lowerBound, upperBound, labelSpinner);
            }
        });

        dialog.show();
    }

    /*
     * addLabelGoal
     * Adds the Label goal to the account and returns to goal activity if successful
     * Parameters
     *      @lowerBound - the Field containing the lower bound of the goal
     *      @upperBound - the Field containing the upper bound of the goal
     *      @labelSpinner - the spinner holding the label
     */
    private void addLabelGoal(EditText lowerBound, EditText upperBound, Spinner labelSpinner) {
        Spinner goalType = dialog.findViewById(R.id.spnType);
        EditText errorField = dialog.findViewById(R.id.addLabelGoalErrorBox);

        try {
            Day day = accessAccount.getDay(username, dayOfYear);
            LabelGoal newGoal = new LabelGoal(Integer.parseInt(lowerBound.getText().toString()), Integer.parseInt(upperBound.getText().toString()), Goal.GoalType.valueOf(goalType.getSelectedItem().toString()), labelSpinner.getSelectedItem().toString());
            if (day.containsGoal(newGoal)) {
                errorField.setVisibility(View.VISIBLE);
                errorField.setError("This day already contains this label goal");
            } else {
                day.addGoal(newGoal);
                accessAccount.updateDay(username, day);
                //Go back to the goal activity
                ChangeActivityHelper.changeActivity(GoalActivity.this, GoalActivity.class, username, dayOfYear);
            }
        } catch (IllegalArgumentException ie) {
            lowerBound.setError(ie.getMessage());
        }
    }

    /*
     * showMicroPopup
     * Show the popup to add a macro goal
     */
    public void showMacroPopUp() {
        dialog.setContentView(R.layout.add_macro_goal);

        EditText lowerBound = dialog.findViewById(R.id.lowerBound);
        EditText upperBound = dialog.findViewById(R.id.upperBound);

        Button btnContinue = dialog.findViewById(R.id.btnContinue);
        btnContinue.setOnClickListener((view) -> {
            if (TextUtils.isEmpty(lowerBound.getText())) {
                lowerBound.setError("Lower bound is required");
            } else if (TextUtils.isEmpty(upperBound.getText())) {
                upperBound.setError("Upper bound is required");
            } else {
                addMacroGoal(lowerBound, upperBound);
            }
        });
        dialog.show();
    }

    /*
     * addMacroGoal
     * Adds the Macro goal to the account and returns to goal activity if successful
     * Parameters
     *      @lowerBound - the Field containing the lower bound of the goal
     *      @upperBound - the Field containing the upper bound of the goal
     */
    private void addMacroGoal(EditText lowerBound, EditText upperBound) {
        Spinner goalType = dialog.findViewById(R.id.spnType);
        Spinner selectedMacro = dialog.findViewById(R.id.spnMacro);
        EditText errorField = dialog.findViewById(R.id.addMacroGoalErrorBox);

        try {
            Day day = accessAccount.getDay(username, dayOfYear);
            MacroGoal newGoal = new MacroGoal(Integer.parseInt(lowerBound.getText().toString()), Integer.parseInt(upperBound.getText().toString()), Goal.GoalType.valueOf(goalType.getSelectedItem().toString()), Edible.Macros.valueOf(selectedMacro.getSelectedItem().toString()));
            if (day.containsGoal(newGoal)) {
                errorField.setVisibility(View.VISIBLE);
                errorField.setError("Day already contains this macro goal");
            } else {
                day.addGoal(newGoal);
                accessAccount.updateDay(username, day);
                //Go back to the goal activity
                ChangeActivityHelper.changeActivity(GoalActivity.this, GoalActivity.class, username, dayOfYear);
            }
        } catch (IllegalArgumentException ie) {
            lowerBound.setError(ie.getMessage());
        }
    }

    /*
     * showMicroPopup
     * Show the popup to add a micro goal
     */
    public void showMicroPopUp() {
        dialog.setContentView(R.layout.add_micro_goal);

        EditText lowerBound = dialog.findViewById(R.id.lowerBound);
        EditText upperBound = dialog.findViewById(R.id.upperBound);

        Button btnContinue = dialog.findViewById(R.id.btnContinue);
        btnContinue.setOnClickListener((view) -> {
            if (validateInput(lowerBound, upperBound)) {
                addMicroGoal(lowerBound, upperBound);
            }
        });
        dialog.show();
    }

    /*
     * addMicroGoal
     * Adds the Micro goal to the account and returns to goal activity if successful
     * Parameters
     *      @lowerBound - the Field containing the lower bound of the goal
     *      @upperBound - the Field containing the upper bound of the goal
     */
    private void addMicroGoal(EditText lowerBound, EditText upperBound) {
        Spinner spinner = dialog.findViewById(R.id.spnMicro);
        Day day = accessAccount.getDay(username, dayOfYear);
        EditText errorField = dialog.findViewById(R.id.addMicroGoalErrorBox);

        try {
            MicroGoal newGoal = new MicroGoal(Integer.parseInt(lowerBound.getText().toString()), Integer.parseInt(upperBound.getText().toString()), Edible.Micros.valueOf(spinner.getSelectedItem().toString()));
            if (day.containsGoal(newGoal)) {
                errorField.setVisibility(View.VISIBLE);
                errorField.setError("Day already contains this micro goal");
            } else {
                day.addGoal(newGoal);
                accessAccount.updateDay(username, day);
                //Go back to the goal activity
                ChangeActivityHelper.changeActivity(GoalActivity.this, GoalActivity.class, username, dayOfYear);
            }
        } catch (IllegalArgumentException ie) {
            lowerBound.setError(ie.getMessage());
        }
    }

    /*
     * showRemoveGoal
     * Show the popup to remove a goal
     */
    public void showRemoveGoal() {
        dialog.setContentView(R.layout.remove_goal);
        Day day = accessAccount.getDay(username, dayOfYear);

        //Populate the spinner with the goals
        Spinner goalSpinner = dialog.findViewById(R.id.goals);
        List<String> goalList = new ArrayList<>();
        Iterator<Goal> dayGoals = day.getGoals();
        Goal g;
        while (dayGoals.hasNext()) {
            g = dayGoals.next();
            goalList.add(g.toString());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, goalList);
        goalSpinner.setAdapter(adapter);

        //Remove a goal
        Button button = dialog.findViewById(R.id.btnContinue);
        button.setOnClickListener((view) -> {
            String selected = goalSpinner.getSelectedItem().toString();
            int index = goalList.indexOf(selected);
            goalList.remove(index);
            day.removeGoal(index);
            accessAccount.updateDay(username, day);

            //Back to goal activity
            ChangeActivityHelper.changeActivity(GoalActivity.this, GoalActivity.class, username, dayOfYear);

        });

        dialog.show();
    }

    private boolean validateInput(EditText lowerBound, EditText upperBound) {
        boolean inputsValid = true;

        if (TextUtils.isEmpty(lowerBound.getText())) {
            lowerBound.setError("Lower bound is required");
            inputsValid = false;
        }

        if (TextUtils.isEmpty(upperBound.getText())) {
            upperBound.setError("Upper bound is required");
            inputsValid = false;
        }

        return inputsValid;
    }

    /*
     * onBackPressed
     * When the back button is pressed
     */
    @Override
    public void onBackPressed() {
        ChangeActivityHelper.changeActivity(GoalActivity.this, TimelineActivity.class, username, dayOfYear);
    }
}
