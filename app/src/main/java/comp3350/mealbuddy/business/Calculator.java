/****************************************
 * Calculator
 * Object for performing various calculations on a users eaten foods
 ****************************************/
package comp3350.mealbuddy.business;

import java.util.ArrayList;
import java.util.Calendar;

import comp3350.mealbuddy.objects.Day;
import comp3350.mealbuddy.objects.consumables.Edible;
import comp3350.mealbuddy.objects.consumables.Food;
import comp3350.mealbuddy.objects.consumables.FoodIntPair;
import comp3350.mealbuddy.objects.consumables.Meal;

import static comp3350.mealbuddy.objects.consumables.Edible.Macros.*;

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
        totalCalories += getListCalories(day.breakfast);
        totalCalories += getListCalories(day.lunch);
        totalCalories += getListCalories(day.dinner);
        totalCalories += getListCalories(day.snack);
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
        totalCalories += getListLabelCalories(day.breakfast, label);
        totalCalories += getListLabelCalories(day.lunch, label);
        totalCalories += getListLabelCalories(day.dinner, label);
        totalCalories += getListLabelCalories(day.snack, label);
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
        totalCalories += getListMacroCalories(day.breakfast, macro);
        totalCalories += getListMacroCalories(day.lunch, macro);
        totalCalories += getListMacroCalories(day.dinner, macro);
        totalCalories += getListMacroCalories(day.snack, macro);
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
        return getListMacroCalories(day.getMealTimeList(mealTime), macro);
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
        return getListLabelCalories(day.getMealTimeList(mealTime), label);
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
    private int getListCalories(ArrayList<Edible> foodList) {
        int totalCalories = 0;
        for (Edible edible : foodList) {
            if (edible instanceof Food) {
                totalCalories += getFoodCalories((Food) edible);
            }
            else {
                totalCalories += getMealCalories((Meal) edible);
            }
        }
        return totalCalories;
    }

    /*
     * getFoodCalories
     * Get the amount of calories in a food.
     * Parameters:
     *     @param food - The food
     * Return:
     *     The calories in the food.
     */
    private int getFoodCalories(Food food) {
        int totalCalories = 0;
        totalCalories += PROTEIN_CONVERSION * food.macros.get(Protein);
        totalCalories += CARBOHYDRATE_CONVERSION * food.macros.get(Carbohydrates);
        totalCalories += FAT_CONVERSION * food.macros.get(Fat);
        totalCalories += ALCOHOL_CONVERSION * food.macros.get(Alcohol);
        return totalCalories;
    }

    /*
     * getMealCalories
     * Get the amount of calories in a meal.
     * Parameters:
     *     @param meal - The meal
     * Return:
     *     The calories in the meal.
     */
    private int getMealCalories(Meal meal) {
        int totalCalories = 0;
        for (FoodIntPair foodQuantityPair: meal.ediblesInMeal) {
            int foodQuantity = foodQuantityPair.quantity;
            Food food = foodQuantityPair.food;
            totalCalories += foodQuantity * getFoodCalories(food);
        }
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
    private int getListMacroCalories(ArrayList<Edible> foodList, Edible.Macros macro) {
        int totalCalories = 0;
        for (Edible edible : foodList) {
            if (edible instanceof Food) {
                totalCalories += getFoodMacroCalories((Food) edible, macro);
            }
            else {
                totalCalories += getMealMacroCalories((Meal) edible, macro);
            }
        }
        return totalCalories;
    }

    /*
     * getFoodMacroCalories
     * Get the amount of calories in a food from a given macro.
     * Parameters:
     *     @param food - The food to calculate calories for
     *     @param macro - The macro to calculate
     * Return:
     *     The calories in the food from the macro.
     */
    private int getFoodMacroCalories(Food food, Edible.Macros macro) {
        int conversionFactor = getConversionFactor(macro);
        return conversionFactor * food.macros.get(macro);
    }

    /*
     * getFoodMacroCalories
     * Get the amount of calories in a meal from a given macro.
     * Parameters:
     *     @param meal - The meal to calculate calories for
     *     @param macro - The macro to calculate
     * Return:
     *     The calories in the food from the macro.
     */
    private int getMealMacroCalories(Meal meal, Edible.Macros macro) {
        int totalCalories = 0;
        for (FoodIntPair foodQuantityPair: meal.ediblesInMeal) {
            int foodQuantity = foodQuantityPair.quantity;
            Food food = foodQuantityPair.food;
            totalCalories += foodQuantity * getFoodMacroCalories(food, macro);
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
    private int getListMicroMgs(ArrayList<Edible> foodList, Edible.Micros micro) {
        int totalMg = 0;
        for (Edible edible : foodList) {
            if (edible instanceof Food) {
                totalMg += ((Food) edible).micros.get(micro);
            }
            else {
                totalMg += getMealMicroMgs((Meal) edible, micro);
            }
        }
        return totalMg;
    }

    /*
     * getMealMicroMgs
     * Get the total mgs of a Micro in a list of edibles
     * Parameters:
     *     @param foodList - The list of edibles
     *     @param micro - The micro
     * Return:
     *     The total mgs of a Micro in the list
     */
    private int getMealMicroMgs (Meal meal, Edible.Micros micro) {
        int totalMg = 0;
        for (FoodIntPair foodQuantityPair: meal.ediblesInMeal) {
            int foodQuantity = foodQuantityPair.quantity;
            int microQuantity = foodQuantityPair.food.micros.get(micro);
            totalMg += foodQuantity * microQuantity;
        }
        return totalMg;
    }

    /*
     * getListLabelCalories
     * Get the total calories from edibles with a certain label in a food list.
     * Parameters:
     *     @param foodList - The list of foods
     *     @param label - The label
     * Return:
     *     The total calories of a label in the food list
     */
    private int getListLabelCalories(ArrayList<Edible> foodList, String label) {
        int totalCalories = 0;
        for (Edible edible : foodList) {
            if (edible instanceof Food) {
                totalCalories += getFoodLabelCalories((Food) edible, label);
            }
            else {
                totalCalories += getMealLabelCalories((Meal) edible, label);
            }
        }
        return totalCalories;
    }

    /*
     * getFoodLabelCalories
     * Get the total calories from a food if it contains the label.
     * Parameters:
     *     @param food - The food
     *     @param label - The label
     * Return:
     *     The total calories of the food if it contains the label.
     */
    private int getFoodLabelCalories(Food food, String label) {
        int totalCalories = 0;
        if (food.labels.contains(label)) {
            totalCalories += getFoodCalories(food);
        }
        return totalCalories;
    }

    /*
     * getMealLabelCalories
     * Get the total calories from a meal if it contains the label.
     * Parameters:
     *     @param meal - The meal
     *     @param label - The label
     * Return:
     *     The total calories coming from foods with the label in the meal.
     */
    private int getMealLabelCalories(Meal meal, String label) {
        int totalCalories = 0;
        for (FoodIntPair foodQuantityPair: meal.ediblesInMeal) {
            Food food = foodQuantityPair.food;
            int foodQuantity = foodQuantityPair.quantity;
            totalCalories += foodQuantity * getFoodLabelCalories(food, label);
        }
        return totalCalories;
    }
}
