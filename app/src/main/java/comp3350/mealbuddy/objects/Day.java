/****************************************
 * Day
 * stores meals tracked by meal time (breakfast, lunch, dinner, snack) per day.
 ****************************************/

package comp3350.mealbuddy.objects;

import java.util.ArrayList;
import java.util.Iterator;

import comp3350.mealbuddy.objects.consumables.Edible;
import comp3350.mealbuddy.objects.consumables.Meal;
import comp3350.mealbuddy.objects.goals.Goal;

public class Day {
    public static final String BREAKFAST_NAME = "Breakfast";
    public static final String LUNCH_NAME = "Lunch";
    public static final String DINNER_NAME = "Dinner";
    public static final String SNACK_NAME = "Snack";
    public int dayOfYear; // represented as day-of-year (1-365)
    public Meal breakfast;
    public Meal lunch;
    public Meal dinner;
    public Meal snack;
    private ArrayList<Goal> goals;
    private ArrayList<Exercise> exercises;

    /*
     * Constructor
     * initializes the day object from a dayOfYear parameter
     * Parameters:
     *     @param dayofYear - The day of year.
     */
    public Day(int dayOfYear) {
        if (dayOfYear < 0 || dayOfYear > 365)
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
        String exerciseString = "";
        for (Exercise exercise : exercises) {
            exerciseString += exercise.toString() + "\n";
        }
        return exerciseString;
    }

    /*
     * addToMeal
     * gets the correct list and adds the edible by quantity into the list
     * Parameters:
     *     @param MT - a mealtime enum value. {BREAKFAST, LUNCH, DINNER, SNACK}
     *     @param edible - to add
     *     @param quantity - the quantity
     */
    public void addToMeal(MealTimeType MT, Edible edible, int quantity) {
        getMealTime(MT).add(edible, quantity);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Day day = (Day) o;
        return dayOfYear == day.dayOfYear;
    }

    /*
     * addExercise
     * add an exercise to the day
     * Parameters:
     *     @param exercise - the exercise to add
     */
    public void addExercise(Exercise exercise) {
        if (exercise == null)
            throw new NullPointerException("Exercise can't be null");

        if (exercises.contains(exercise)) {
            double oldDuration = exercises.get(exercises.indexOf(exercise)).duration;
            exercises.remove(exercise);
            exercises.add(new Exercise(exercise.name, exercise.duration + oldDuration, exercise.intensity));
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
        if (exercise == null)
            throw new NullPointerException("Exercise doesn't exist");
        if (!exercises.contains(exercise))
            throw new IllegalArgumentException("Exercise doesn't exist");
        exercises.remove(exercise);
    }

    /*
     * removeExercise
     * remove an exercise from the day
     * Parameters:
     *     @param index - the index of the exercise to remove
     */
    public void removeExercise(int index) {
        if (index < 0 || index >= exercises.size())
            throw new IndexOutOfBoundsException("Index out of bounds in exercise list");
        exercises.remove(index);
    }

    /*
     * addGoal
     * add a goal to the day
     * Parameters:
     *     @param goal - the goal to add
     */
    public void addGoal(Goal goal) {
        if (goal == null)
            throw new NullPointerException("Goal can't be null");
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
        if (goal == null)
            throw new NullPointerException("Goal doesn't exist");
        if (!goals.contains(goal))
            throw new IllegalArgumentException("Goal doesn't exist");
        goals.remove(goal);
    }

    /*
     * removeGoal
     * remove a goal from the day
     * Parameters:
     *     @param string - the goal id to remove
     */
    public void removeGoal(int index) {
        if (index < 0 || index >= goals.size())
            throw new IndexOutOfBoundsException("Index out of bounds in goal list");
        goals.remove(index);
    }

    /*
     * removeAllGoal
     * remove all goals from a day
     */
    public void removeAllGoals() {
        goals = new ArrayList<>();
    }

    /*
     * containsGoal
     * check if the goal is in the list
     * Parameters:
     *     @param goal - the goal to check
     * Return:
     *      True if goal is in list
     */
    public boolean containsGoal(Goal goal) {
        return goals.contains(goal);
    }

    /*
     * isGoalsEmpty
     * sees if there are goals in a day
     * Return:
     *     true if there are no goals in the day
     */
    public boolean isGoalsEmpty() {
        return goals.isEmpty();
    }

    /*
     * isExerciseEmpty
     * sees if there are exercises in a day
     * Return:
     *     true if there are no exercises in the day
     */
    public boolean isExerciseEmpty() {
        return exercises.isEmpty();
    }

    /*
     * getExercises
     * get the exercises in a day.
     * Return:
     *     an iterator over the list of exercises
     */
    public Iterator<Exercise> getExercises() {
        return exercises.iterator();
    }

    /*
     * getGoals
     * get the goals in a day.
     * Return:
     *     an iterator over the list of goals
     */
    public Iterator<Goal> getGoals() {
        return goals.iterator();
    }

    /*
     * getGoal
     * get the goal in the day or null if not in the day
     * Parameter:
     *      @goal - the goal to retrieve form the day
     * Return:
     *     the goal
     */
    public Goal getGoal(Goal goal) {
        Goal goalInList = null;
        if (goals.contains(goal))
            goalInList = goals.get(goals.indexOf(goal));
        return goalInList;
    }


    /*
     * getExercise
     * get the exercise in the day or null if not in the day
     * Parameter:
     *      @name - the exercise to retrieve form the day
     * Return:
     *     the exercise
     */
    public Exercise getExercise(String name) {
        for (Exercise e : exercises) {
            if (e.name.equals(name))
                return e;
        }
        return null;
    }

    /* getGoalString
     * returns string interpretation of the goals
     * Return:
     *      returns the string interpretation
     */
    public String getGoalString() {
        System.err.println("Getting goals for day: " + dayOfYear);
        String result = "";
        for (Goal g : goals) {
            result += g.toString() + "\n";
        }
        return result;

    }

    public enum MealTimeType {
        BREAKFAST, LUNCH, DINNER, SNACK
    }

}
