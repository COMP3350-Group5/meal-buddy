/***************************************
 Calculator
 Object for performing various calculations on a users eaten foods
 ***************************************/
package comp3350.mealbuddy.business;

import java.util.Iterator;

import comp3350.mealbuddy.objects.Day;
import comp3350.mealbuddy.objects.Exercise;
import comp3350.mealbuddy.objects.UserInfo;
import comp3350.mealbuddy.objects.consumables.Edible;

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

    private final static double KG_CONVERSION = 2.205;
    private final static double MET_MULTIPLIER = 3.5;
    private final static double MET_DIVISOR = 200.0;
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
        totalCalories += getEdibleLabelCalories(day.breakfast, label);
        totalCalories += getEdibleLabelCalories(day.lunch, label);
        totalCalories += getEdibleLabelCalories(day.dinner, label);
        totalCalories += getEdibleLabelCalories(day.snack, label);
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
        totalCalories += getEdibleMacroCalories(day.breakfast, macro);
        totalCalories += getEdibleMacroCalories(day.lunch, macro);
        totalCalories += getEdibleMacroCalories(day.dinner, macro);
        totalCalories += getEdibleMacroCalories(day.snack, macro);
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
        totalMgs += getEdibleMicroMgs(day.breakfast, micro);
        totalMgs += getEdibleMicroMgs(day.lunch, micro);
        totalMgs += getEdibleMicroMgs(day.dinner, micro);
        totalMgs += getEdibleMicroMgs(day.snack, micro);
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
        return getEdibleCalories(day.getMealTime(mealTime));
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
        return getEdibleMacroCalories(day.getMealTime(mealTime), macro);
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
        return getEdibleLabelCalories(day.getMealTime(mealTime), label);
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
        return getEdibleMicroMgs(day.getMealTime(mealTime), micro);
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
     * getEdibleMacroCalories
     * Get the total amount of calories from a specific Macro in a list of edibles.
     * Parameters:
     *     @param edible - The edible to get the macros of
     * Return:
     *     Total calories in the meal, from the macro given.
     */
    private int getEdibleMacroCalories(Edible edible, Edible.Macros macro) {
        int totalCalories = 0;
        for (Edible e : edible) {
            int conversionFactor = getConversionFactor(macro);
            totalCalories += e.getMacroGrams(macro) * conversionFactor;
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
        switch (macro) {
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
     * getEdibleMicroMgs
     * Get the total mgs of a Micro from a meal
     * Parameters:
     *     @param meal - The meal of edibles
     *     @param micro - The micro
     * Return:
     *     The total mgs of a micro in the meal
     */
    private int getEdibleMicroMgs(Edible edible, Edible.Micros micro) {
        int totalMg = 0;
        for (Edible e : edible) {
            totalMg += e.getMicroGrams(micro);
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
    private int getEdibleLabelCalories(Edible edible, String label) {
        int totalCalories = 0;
        for (Edible e : edible) {
            if (e.containsLabel(label))
                totalCalories += getEdibleCalories(e);
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
        switch (exercise.intensity) {
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
        int met = getMets(exercise);
        return (int) (((userInfo.weight / KG_CONVERSION) * MET_MULTIPLIER * met * exercise.duration) / MET_DIVISOR);
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
        Iterator<Exercise> dayExercises = day.getExercises();
        Exercise exercise;
        while (dayExercises.hasNext()) {
            exercise = dayExercises.next();
            totalCalories += getExerciseCalories(exercise, userInfo);
        }
        return totalCalories;
    }

    /*
     * getNetCalories
     * Get the net total calories. (i.e., total calories - total burned calories)
     * Parameters:
     *     @param userInfo - The userInfo of the user performing the exercises
     * Return:
     *     The total calories
     */
    public int getNetCalories(UserInfo userInfo) {
        return getTotalCalories() - getTotalExerciseCalories(userInfo);
    }

}
