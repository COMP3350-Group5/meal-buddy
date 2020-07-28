/***************************************
 Calculator
 Object for performing various calculations on a users eaten foods
 ***************************************/
package comp3350.mealbuddy.business;

import comp3350.mealbuddy.objects.Day;
import comp3350.mealbuddy.objects.Exercise;
import comp3350.mealbuddy.objects.UserInfo;
import comp3350.mealbuddy.objects.consumables.Edible;
import comp3350.mealbuddy.objects.consumables.Meal;

import static comp3350.mealbuddy.objects.consumables.Edible.Macros.Alcohol;
import static comp3350.mealbuddy.objects.consumables.Edible.Macros.Carbohydrates;
import static comp3350.mealbuddy.objects.consumables.Edible.Macros.Fat;
import static comp3350.mealbuddy.objects.consumables.Edible.Macros.Protein;

public class Calculator {

    //Calories per gram of the Macro
    private final static int ALCOHOL_CONVERSION = 7;
    private final static int CARBOHYDRATE_CONVERSION = 4;
    private final static int FAT_CONVERSION = 9;
    private final static int PROTEIN_CONVERSION = 4;

    // MET values for exercise intensities
    private final static int LOW_MET = 3;
    private final static int MEDIUM_MET = 5;
    private final static int HIGH_MET = 10;

    public Day day;

    /*
     * Constructor
     * Create a new calculator for the given day.
     * Parameters:
     *     @param day - The day to create a calculator for
     */
    public Calculator(Day day) {
        if (day == null) {
            throw new NullPointerException("Day must not be null");
        }
        this.day = day;
    }

    /*
     * getTotalCalories
     * Get the total amount of calories eaten.
     * Return:
     *     The total amount of calories eaten in a day.
     */
    public int getTotalCalories() {
        int totalCalories = 0;
        totalCalories += getEdibleCalories(day.breakfast);
        totalCalories += getEdibleCalories(day.lunch);
        totalCalories += getEdibleCalories(day.dinner);
        totalCalories += getEdibleCalories(day.snack);
        return totalCalories;
    }

    /*
     * getLabelCalories
     * Get the total amount of calories obtained from foods in a specific label.
     * Parameters:
     *     @param label - The label
     * Return:
     *     The total amount of calories obtained from foods in label in a day.
     */
    public int getLabelCalories(String label) {
        int totalCalories = 0;
        totalCalories += getMealLabelCalories(day.breakfast, label);
        totalCalories += getMealLabelCalories(day.lunch, label);
        totalCalories += getMealLabelCalories(day.dinner, label);
        totalCalories += getMealLabelCalories(day.snack, label);
        return totalCalories;
    }

    /*
     * getMacroCalories
     * Get the total amount of calories obtained related to a specific macro.
     * Parameters:
     *     @param macro - The macro
     * Return:
     *     The total amount of calories obtained related to the macro in a day.
     */
    public int getMacroCalories(Edible.Macros macro) {
        int totalCalories = 0;
        totalCalories += getMealMacroCalories(day.breakfast, macro);
        totalCalories += getMealMacroCalories(day.lunch, macro);
        totalCalories += getMealMacroCalories(day.dinner, macro);
        totalCalories += getMealMacroCalories(day.snack, macro);
        return totalCalories;
    }

    /*
     * getMicroMgs
     * Get the total amount of mgs of a micro eaten.
     * Parameters:
     *     @param micro - The micro
     * Return:
     *     The total amount of mgs of the micro eaten in day.
     */
    public int getMicroMgs(Edible.Micros micro) {
        int totalMgs = 0;
        totalMgs += getMealMicroMgs(day.breakfast, micro);
        totalMgs += getMealMicroMgs(day.lunch, micro);
        totalMgs += getMealMicroMgs(day.dinner, micro);
        totalMgs += getMealMicroMgs(day.snack, micro);
        return totalMgs;
    }

    /*
     * getMealTimeCalories
     * Get the total amount of calories eaten for a specific mealtime.
     * Parameters:
     *     @param - mealTime - The mealtime to calculate calories for.
     * Return:
     *     The total amount of calories eaten for the mealtime.
     */
    public int getMealTimeCalories(Day.MealTimeType mealTime) {
        return getMealCalories(day.getMealTime(mealTime));
    }

    /*
     * getMealTimeMacroCalories
     * Get the total amount of calories obtained related to a specific macro for a specific mealtime.
     * Parameters:
     *     @param mealTIme - The mealtime to calculate calories for.
     *     @param macro - The macro.
     * Return:
     *     The total amount of calories eaten for the mealtime from the macro.
     */
    public int getMealTimeMacroCalories(Day.MealTimeType mealTime, Edible.Macros macro) {
        return getMealMacroCalories(day.getMealTime(mealTime), macro);
    }

    /*
     * getMealTimeLabelCalories
     * Get the total amount of calories obtained from foods in a specific label for a specific mealtime.
     * Parameters:
     *     @param mealTime - The mealtime to calculate calories for.
     *     @param label - The label
     * Return:
     *     The total amount of calories obtained for the mealtime from foods in label
     */
    public int getMealTimeLabelCalories(Day.MealTimeType mealTime, String label) {
        return getMealLabelCalories(day.getMealTime(mealTime), label);
    }

    /*
     * geMealTimeMicroMgs
     * Get the total amount of mgs of a micro eaten for a specific mealtime.
     * Parameters:
     *     @param mealTime - The mealtime to calculate calories for.
     *     @param micro - The micro
     * Return:
     *     The total amount of mgs of the micro eaten for the mealtime.
     */
    public int getMealTimeMicroMgs(Day.MealTimeType mealTime, Edible.Micros micro) {
        return getMealMicroMgs(day.getMealTime(mealTime), micro);
    }

    /*
     * getListCalories
     * Get the total amount of calories in a meal.
     * Parameters:
     *     @param meal - the meal to get the calories of
     * Return:
     *     Total calories in the list.
     */
    private int getMealCalories(Meal meal) {
        int totalCalories = 0;
        for (Edible edible : meal) {
            totalCalories += getEdibleCalories(edible);
        }
        return totalCalories;
    }

    /*
     * getEdibleCalories
     * Get the amount of calories in a food.
     * Parameters:
     *     @param edible - The edible to get the calories of
     * Return:
     *     The calories in the edible.
     */
    private int getEdibleCalories(Edible edible) {
        int totalCalories = 0;
        totalCalories += PROTEIN_CONVERSION * edible.getMacroGrams(Protein);
        totalCalories += CARBOHYDRATE_CONVERSION * edible.getMacroGrams(Carbohydrates);
        totalCalories += FAT_CONVERSION * edible.getMacroGrams(Fat);
        totalCalories += ALCOHOL_CONVERSION * edible.getMacroGrams(Alcohol);
        return totalCalories;
    }

    /*
     * getMealMacroCalories
     * Get the total amount of calories from a specific Macro in a list of edibles.
     * Parameters:
     *     @param meal - The meal to get the macros of
     * Return:
     *     Total calories in the meal, from the macro given.
     */
    private int getMealMacroCalories(Meal meal, Edible.Macros macro) {
        int totalCalories = 0;
        for (Edible edible : meal) {
            int conversionFactor = getConversionFactor(macro);
            totalCalories += edible.getMacroGrams(macro) * conversionFactor;
        }
        return totalCalories;
    }


    /*
     * getConversionFactor
     * Get the grams to calories conversion factor for a macro
     * Parameters:
     *     @param macro - The macro
     * Return:
     *     The conversion factor for the macro
     */
    private int getConversionFactor(Edible.Macros macro) {
        int conversionFactor = -1;
        switch(macro) {
            case Fat:
                conversionFactor = FAT_CONVERSION;
                break;
            case Protein:
                conversionFactor = PROTEIN_CONVERSION;
                break;
            case Carbohydrates:
                conversionFactor = CARBOHYDRATE_CONVERSION;
                break;
            case Alcohol:
                conversionFactor = ALCOHOL_CONVERSION;
        }
        return conversionFactor;
    }

    /*
     * getMealMicroMgs
     * Get the total mgs of a Micro from a meal
     * Parameters:
     *     @param meal - The meal of edibles
     *     @param micro - The micro
     * Return:
     *     The total mgs of a micro in the meal
     */
    private int getMealMicroMgs(Meal meal, Edible.Micros micro) {
        int totalMg = 0;
        for (Edible edible : meal) {
            totalMg += edible.getMicroGrams(micro);
        }
        return totalMg;
    }


    /*
     * getMealLabelCalories
     * Get the total calories from edibles with a certain label in a food list.
     * Parameters:
     *     @param foodList - The Meal holding the foods of a meal time
     *     @param label - The label
     * Return:
     *     The total calories of a label in the food list
     */
    private int getMealLabelCalories(Meal foodList, String label) {
        int totalCalories = 0;
        for (Edible edible : foodList) {
            if (edible.containsLabel(label))
                totalCalories += getEdibleCalories(edible);
        }
        return totalCalories;
    }

    /*
     * getMets
     * Get the MET value associated with an exercise intensity
     * Parameters:
     *     @param exercise - The exercise
     * Return:
     *     The met value for the exercise
     */
    private int getMets(Exercise exercise) {
        int mets = -1;
        switch(exercise.intensity) {
            case Low:
                mets = LOW_MET;
                break;
            case Medium:
                mets = MEDIUM_MET;
                break;
            case High:
                mets = HIGH_MET;
                break;
        }
        return mets;
    }
    /*
     * getExerciseCalories
     * Get the calories burned in an exercise.
     * Parameters:
     *     @param exercise - The exercise
     *     @param userInfo - The userInfo of the user performing the exercise
     * Return:
     *     The calories burned in the exercise
     */
    public int getExerciseCalories(Exercise exercise, UserInfo userInfo) {
        int calories = 0;
        int met = getMets(exercise);
        return (int)(((userInfo.weight / 2.205) * 3.5 * met * exercise.duration) / 200);
    }

    /*
     * getTotalExerciseCalories
     * Get the total calories burned in all the exercises in a day.
     * Parameters:
     *     @param userInfo - The userInfo of the user performing the exercises
     * Return:
     *     The total calories burned in the exercises
     */
    public int getTotalExerciseCalories(UserInfo userInfo) {
        int totalCalories = 0;
        for (Exercise exercise : day.exercises) {
            totalCalories += getExerciseCalories(exercise, userInfo);
        }
        return totalCalories;
    }

}
