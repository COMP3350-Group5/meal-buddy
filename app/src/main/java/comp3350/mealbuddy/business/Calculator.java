/***************************************
 Calculator
 Object for performing various calculations on a users eaten foods
 ***************************************/
package comp3350.mealbuddy.business;

import comp3350.mealbuddy.objects.Day;
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
     *     The total amount of calories eaten.
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
     *     The total amount of calories obtained from foods in label.
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
     *     The total amount of calories obtained related to the macro.
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
     *     The total amount of mgs of the micro eaten.
     */
    public int getMicroMgs(Edible.Micros micro) {
        int totalMgs = 0;
        totalMgs += getListMicroMgs(day.breakfast, micro);
        totalMgs += getListMicroMgs(day.lunch, micro);
        totalMgs += getListMicroMgs(day.dinner, micro);
        totalMgs += getListMicroMgs(day.snack , micro);
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
        return getListCalories(day.getMealTimeList(mealTime));
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
        return getMealMacroCalories(day.getMealTimeList(mealTime), macro);
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
        return getMealLabelCalories(day.getMealTimeList(mealTime), label);
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
        return getListMicroMgs(day.getMealTimeList(mealTime), micro);
    }

    /*
     * getListCalories
     * Get the total amount of calories in a list of edibles.
     * Parameters:
     *     @param foodList - The list of foods
     * Return:
     *     Total calories in the list.
     */
    private int getListCalories(Meal foodList) {
        int totalCalories = 0;
        for (Edible edible : foodList) {
            totalCalories += getEdibleCalories(edible);
        }
        return totalCalories;
    }

    /*
     * getEdibleCalories
     * Get the amount of calories in a food.
     * Parameters:
     *     @param edible - The food
     * Return:
     *     The calories in the food.
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
     * getListMacroCalories
     * Get the total amount of calories from a specific Macro in a list of edibles.
     * Parameters:
     *     @param foodList - The list of foods
     * Return:
     *     Total calories in the list, from the macro given.
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
     * getListMicroMgs
     * Get the total mgs of a Micro from a food list
     * Parameters:
     *     @param foodList - The list of foods
     *     @param micro - The micro
     * Return:
     *     The total mgs of a micro in the food list
     */
    private int getListMicroMgs(Meal foodList, Edible.Micros micro) {
        int totalMg = 0;
        for (Edible edible : foodList) {
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

}
