package comp3350.mealbuddy.business;

import androidx.core.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import comp3350.mealbuddy.objects.Day;
import comp3350.mealbuddy.objects.Edible;
import comp3350.mealbuddy.objects.Food;
import comp3350.mealbuddy.objects.Meal;

import static comp3350.mealbuddy.objects.Edible.Macros.Alcohol;
import static comp3350.mealbuddy.objects.Edible.Macros.Carbohydrates;
import static comp3350.mealbuddy.objects.Edible.Macros.Fat;
import static comp3350.mealbuddy.objects.Edible.Macros.Fibre;
import static comp3350.mealbuddy.objects.Edible.Macros.Omega3;
import static comp3350.mealbuddy.objects.Edible.Macros.Omega6;
import static comp3350.mealbuddy.objects.Edible.Macros.Protein;
import static comp3350.mealbuddy.objects.Edible.Macros.Saturated;
import static comp3350.mealbuddy.objects.Edible.Macros.Sugar;
import static comp3350.mealbuddy.objects.Edible.Macros.Trans;
import static comp3350.mealbuddy.objects.Edible.Macros.Unsaturated;


public class Calculator {

    private final static int ALCOHOL_CONVERSION = 7;
    private final static int CARBOHYDRATE_CONVERSION = 4;
    private final static int FAT_CONVERSION = 9;
    private final static int PROTEIN_CONVERSION = 4;

    public Day day;

    public Calculator(Day day) {
        this.day = day;
    }

    public int getTotalCalories() {
        //Assuming Macros are stored in calories
        //1g protein = 4 calories; 1g carbohydrates = 4 calories; 1g Fat = 9 calories
        int totalCalories = 0;
        totalCalories += getListCalories(day.breakfast);
        totalCalories += getListCalories(day.lunch);
        totalCalories += getListCalories(day.dinner);
        totalCalories += getListCalories(day.snack);
        return totalCalories;
    }

    public int getLabelCalories(String label) {
        //Takes a label, every food has a list of labels, so you check
        //Which foods contain this label
        return 4;
    }

    public int getMacroCalories(Edible.Macros macro) {
        int totalCalories = 0;
        for (Edible edible: ) {

        }
        return totalCalories;
    }

    public int getMicroAmount(Edible.Micros micro) {
        //Return sum
        return 2;
    }

    public int getMealTimeCalories(Day.MealTime_t mealTime) {
        int totalCalories = 0;
        switch(mealTime) {
            case BREAKFAST:
                totalCalories = getListCalories(day.breakfast);
                break;
            case LUNCH:
                totalCalories = getListCalories(day.lunch);
                break;
            case DINNER:
                totalCalories = getListCalories(day.dinner);
                break;
            case SNACK:
                totalCalories = getListCalories(day.snack);
        }
        return totalCalories;
    }

    private int getListCalories(ArrayList<Edible> foodList) {
        int totalCalories = 0;
        for (Edible edible: foodList) {
            if (edible instanceof Food) { //edible is a singular food item
                totalCalories += getFoodCalories((Food)edible);
            }
            else { //edible is a meal consisting of multiple food
                totalCalories += getMealCalories((Meal)edible);
            }
        }
        return totalCalories;
    }

    private int getFoodCalories(Food food) {
        int totalCalories = 0;
        totalCalories += PROTEIN_CONVERSION * food.macros.get(Protein);
        totalCalories += CARBOHYDRATE_CONVERSION * food.macros.get(Carbohydrates);
        totalCalories += FAT_CONVERSION * food.macros.get(Fat);
        totalCalories += ALCOHOL_CONVERSION * food.macros.get(Alcohol);
        return totalCalories;
    }

    private int getMealCalories(Meal meal) {
        int totalCalories = 0;
        for (Pair<Food, Integer> foodQuantityPair: meal.foodInMeal) {
            int quantity = foodQuantityPair.second;
            Map<Edible.Macros, Integer> macroValuePairs = foodQuantityPair.first.macros;
            totalCalories += quantity * PROTEIN_CONVERSION * macroValuePairs.get(Protein);
            totalCalories += quantity * CARBOHYDRATE_CONVERSION * macroValuePairs.get(Carbohydrates);
            totalCalories += quantity * FAT_CONVERSION * macroValuePairs.get(Fat);
            totalCalories += quantity * ALCOHOL_CONVERSION * macroValuePairs.get(Alcohol);
        }
        return totalCalories;
    }

    private int getFoodMacroCalories(Food food, Edible.Macros macro) {
        int conversionFactor = getConversionFactor(macro);
        return conversionFactor * food.macros.get(macro);
    }

    private int getConversionFactor(Edible.Macros macro) {
        int conversionFactor = -1;
        if (isCarbohydrate(macro)) {
            conversionFactor = CARBOHYDRATE_CONVERSION;
        }
        else if (isFat(macro)) {
            conversionFactor = FAT_CONVERSION;
        }
        else if (macro == Protein) {
            conversionFactor = PROTEIN_CONVERSION;
        }
        else if (macro == Alcohol) {
            conversionFactor = ALCOHOL_CONVERSION;
        }
        return conversionFactor;
    }

    private boolean isCarbohydrate(Edible.Macros macro) {
        return (macro == Carbohydrates || macro == Sugar || macro == Fibre);
    }

    private boolean isFat(Edible.Macros macro) {
        return (macro == Fat || macro == Omega3 || macro == Omega6 || macro == Saturated || macro == Trans || macro == Unsaturated);
    }
}
