/****************************************
 * Day
 * stores meals tracked by meal time (breakfast, lunch, dinner, snack) per day.
 ****************************************/

package comp3350.mealbuddy.objects;

import java.util.ArrayList;

import comp3350.mealbuddy.objects.consumables.Edible;
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
    public ArrayList<Goal> goals;
    public ArrayList<Exercise> exercises;
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
        this.breakfast = original.breakfast;
        this.lunch = original.lunch;
        this.dinner = original.dinner;
        this.snack = original.snack;
        this.goals = original.goals;
        this.exercises = original.exercises;
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
        String meal = "";
        switch (MT) {
            case BREAKFAST:
                for (Edible edible : breakfast) {
                    meal += edible.toString() + "\n";
                }
                break;
            case LUNCH:
                for (Edible edible : lunch) {
                    meal += edible.toString() + "\n";
                }
                break;
            case DINNER:
                for (Edible edible :dinner) {
                    meal += edible.toString() + "\n";
                }
                break;
            case SNACK:
                for (Edible edible :snack) {
                    meal += edible.toString() + "\n";
                }
                break;
        }
        return meal;
    }



}
