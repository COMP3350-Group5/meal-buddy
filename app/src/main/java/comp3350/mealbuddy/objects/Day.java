/****************************************
 * Day
 * stores meals tracked by meal time (breakfast, lunch, dinner, snack) per day.
 ****************************************/

package comp3350.mealbuddy.objects;

import java.util.ArrayList;

import comp3350.mealbuddy.objects.consumables.Edible;
import comp3350.mealbuddy.objects.consumables.EdibleIntPair;
import comp3350.mealbuddy.objects.consumables.Meal;
import comp3350.mealbuddy.objects.goals.Goal;

public class Day {
    public enum MealTimeType {
        BREAKFAST, LUNCH, DINNER, SNACK
    }

    public int dayOfYear; // represented as day-of-year (1-365)
    public Meal breakfast;
    public Meal lunch;
    public Meal dinner;
    public Meal snack;
    private ArrayList<Goal> goals;
    private ArrayList<Exercise> exercises;
    public static final String BREAKFAST_NAME = "Breakfast";
    public static final String LUNCH_NAME = "Lunch";
    public static final String DINNER_NAME = "Dinner";
    public static final String SNACK_NAME = "Snack";

    /*
     * Constructor
     * initializes the day object from a dayOfYear parameter
     * Parameters:
     *     @param dayofYear - The day of year.
     */
    public Day(int dayOfYear) {
        if (dayOfYear < 1 || dayOfYear > 365)
            throw new IllegalArgumentException("Must pass valid date");
        this.dayOfYear = dayOfYear;
        this.breakfast = new Meal(BREAKFAST_NAME);
        this.lunch = new Meal(LUNCH_NAME);
        this.dinner = new Meal(DINNER_NAME);
        this.snack = new Meal(SNACK_NAME);
        this.goals = new ArrayList<>();
        this.exercises = new ArrayList<>();
    }

    /*
     * Copy Constructor
     * initializes a day object from an existing day
     * Parameters:
     *     @param original - The day to copy.
     */
    public Day(Day original) {
        this.dayOfYear = original.dayOfYear;
        this.breakfast = new Meal(original.breakfast);
        this.lunch = new Meal(original.lunch);
        this.dinner = new Meal(original.dinner);
        this.snack = new Meal(original.snack);
        this.goals = new ArrayList<>();
        for (Goal g : original.goals) {
            this.goals.add(Goal.copyGoal(g));
        }
        this.exercises = new ArrayList<>();
        for (Exercise e : original.exercises) {
            this.exercises.add(new Exercise(e));
        }
    }

    /*
     * getMealTime
     * takes a mealtime enum and returns the corresponding list.
     * Parameters:
     *     @param MT - a mealtime enum value. {BREAKFAST, LUNCH, DINNER, SNACK}
     * Return:
     *     returns the corresponding Meal
     */
    public Meal getMealTime(MealTimeType MT) {
        switch (MT) {
            case BREAKFAST:
                return breakfast;
            case LUNCH:
                return lunch;
            case DINNER:
                return dinner;
            case SNACK:
                return snack;
        }
        return null;
    }


    /*
     * getMealString
     * returns string interpretation of the meal.
     * Parameters:
     *     @param MT - a mealtime enum value. {BREAKFAST, LUNCH, DINNER, SNACK}
     * Return:
     *     returns the string interpretation.
     */
    public String getMealString(MealTimeType MT) {
        return getMealTime(MT).toString();
    }

    /*
     * getExerciseString
     * returns string interpretation of the exercises.
     * Return:
     *     returns the string interpretation.
     */
    public String getExerciseString() {
        String exerciseString= "";
        for (Exercise exercise : exercises) {
            exerciseString += exercise.toString() + "\n";
        }
        return exerciseString;
    }

    public void addToMeal(MealTimeType MT, Edible edible, int quantity){
        getMealTime(MT).add(edible, quantity);
    }

    /*
     * addExercise
     * add an exercise to the day
     * Parameters:
     *     @param exercise - the exercise to add
     */
    public void addExercise(Exercise exercise) {
        if (exercises.contains(exercise)) {
            double oldDuration = exercises.get(exercises.indexOf(exercise)).duration;
            exercises.remove(exercise);
            exercises.add(new Exercise(exercise.name, exercise.duration + oldDuration, exercise.intensity ));
        } else {
            exercises.add(exercise);
        }
    }

    /*
     * removeExercise
     * remove an exercise from the day
     * Parameters:
     *     @param exercise - the exercise to remove
     */
    public void removeExercise(Exercise exercise) {
        exercises.remove(exercise);
    }

    /*
     * addGoal
     * add a goal to the day
     * Parameters:
     *     @param goal - the goal to add
     */
    public void addGoal(Goal goal) {
        if (!goals.contains(goal)) {
            goals.add(goal);
        }
    }

    /*
     * removeGoal
     * remove a goal from the day
     * Parameters:
     *     @param goal - the goal to remove
     */
    public void removeGoal(Goal goal) {
        goals.remove(goal);
    }

    /*
     * getExercises
     * get the exercises in a day.
     * Return:
     *     the exercise list
     */
    public ArrayList<Exercise> getExercises() {
        return exercises;
    }
    
    /*
     * getGoals
     * get the goals in a day.
     * Return:
     *     the goal list
     */
    public ArrayList<Goal> getGoals() {
        return goals;
    }


}
