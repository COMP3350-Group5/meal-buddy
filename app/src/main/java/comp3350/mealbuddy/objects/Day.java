/****************************************
 * Day
 * stores meals tracked by meal time (breakfast, lunch, dinner, snack) per day.
 ****************************************/

package comp3350.mealbuddy.objects;

import java.util.ArrayList;

import comp3350.mealbuddy.objects.consumables.Edible;
import comp3350.mealbuddy.objects.goals.Goal;

public class Day {
    public enum MealTimeType {
        BREAKFAST, LUNCH, DINNER, SNACK
    }
    public int dayOfYear; // represented as day-of-year (1-365)
    public ArrayList<Edible> breakfast;
    public ArrayList<Edible> lunch;
    public ArrayList<Edible> dinner;
    public ArrayList<Edible> snack;

    public ArrayList<Goal> goals;
    public ArrayList<Exercise> exercises;

    /*
     * Constructor
     * initializes the day object from a dayOfYear parameter
     * Parameters:
     *     @param dayofYear - The day of year.
     */
    public Day (int dayOfYear) {
        if (dayOfYear < 1 || dayOfYear > 365)
            throw new IllegalArgumentException("Must pass valid date");
        this.dayOfYear = dayOfYear;
        this.breakfast = new ArrayList<>();
        this.lunch = new ArrayList<>();
        this.dinner = new ArrayList<>();
        this.snack = new ArrayList<>();
        this.goals = new ArrayList<>();
        this.exercises = new ArrayList<>();
    }

    /*
     * getMealTimeList
     * takes a mealtime enum and returns the corresponding list.
     * Parameters:
     *     @param MT - a mealtime enum value. {BREAKFAST, LUNCH, DINNER, SNACK}
     * Return:
     *     returns the corresponding list from the enum
     */
    public ArrayList<Edible> getMealTimeList(MealTimeType MT){
        switch(MT){
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
     * addFood
     * adds an edible (Food, Meal) to the corresponding mealtime
     * Parameters:
     *     @param MT - a mealtime enum value. {BREAKFAST, LUNCH, DINNER, SNACK}
     *     @param e - the edible to add
     */
    public void addFood(MealTimeType MT, Edible e){
        if (MT == null || e == null)
            return;
        getMealTimeList(MT).add(e);
    }

    /*
     * removeFood
     * removes a food from the meal time list
     * Parameters:
     *     @param MT - a mealtime enum value. {BREAKFAST, LUNCH, DINNER, SNACK}
     *     @param e - the edible to remove
     */
    public void removeFood(MealTimeType MT, Edible edible){
        getMealTimeList(MT).remove(edible);
    }

    /*
     * getMeal
     * returns string interpretation of the meal.
     * Parameters:
     *     @param MT - a mealtime enum value. {BREAKFAST, LUNCH, DINNER, SNACK}
     * Return:
     *     returns the string interpretation.
     */
    public String getMeal(MealTimeType MT) {
        String meal = "";
        switch(MT) {
            case BREAKFAST:
                for (Edible edible :breakfast) {
                    meal += edible.toString() + "\n";
                }
                break;
            case LUNCH:
                for (Edible edible :lunch) {
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
