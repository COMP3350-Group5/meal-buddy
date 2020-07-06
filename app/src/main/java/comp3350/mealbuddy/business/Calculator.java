package comp3350.mealbuddy.business;

import androidx.core.util.Pair;

import java.util.ArrayList;
import java.util.Map;

import comp3350.mealbuddy.objects.Day;
import comp3350.mealbuddy.objects.Edible;
import comp3350.mealbuddy.objects.Food;
import comp3350.mealbuddy.objects.FoodIntPair;
import comp3350.mealbuddy.objects.Meal;

import static comp3350.mealbuddy.objects.Edible.Macros.*;

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
        int totalCalories = 0;
        totalCalories += getListCalories(day.breakfast);
        totalCalories += getListCalories(day.lunch);
        totalCalories += getListCalories(day.dinner);
        totalCalories += getListCalories(day.snack);
        return totalCalories;
    }

    public int getLabelCalories(String label) {
        int totalCalories = 0;
        totalCalories += getListLabelCalories(day.breakfast, label);
        totalCalories += getListLabelCalories(day.lunch, label);
        totalCalories += getListLabelCalories(day.dinner, label);
        totalCalories += getListLabelCalories(day.snack, label);
        return totalCalories;
    }

    public int getMacroCalories(Edible.Macros macro) {
        int totalCalories = 0;
        totalCalories += getListMacroCalories(day.breakfast, macro);
        totalCalories += getListMacroCalories(day.lunch, macro);
        totalCalories += getListMacroCalories(day.dinner, macro);
        totalCalories += getListMacroCalories(day.snack, macro);
        return totalCalories;
    }

    public int getMicroMgs(Edible.Micros micro) {
        int totalMgs = 0;
        totalMgs += getListMicroMgs(day.breakfast, micro);
        totalMgs += getListMicroMgs(day.lunch, micro);
        totalMgs += getListMicroMgs(day.dinner, micro);
        totalMgs += getListMicroMgs(day.snack , micro);
        return totalMgs;
    }

    public int getMealTimeCalories(Day.MealTime_t mealTime) {
        return getListCalories(day.getMealTimeList(mealTime));
    }

    public int getMealTimeMacros(Day.MealTime_t mealTime, Edible.Macros macro) {
        int totalCalories = 0;
        switch(mealTime) {
            case BREAKFAST:
                totalCalories = getListMacroCalories(day.breakfast, macro);
                break;
            case LUNCH:
                totalCalories = getListMacroCalories(day.lunch, macro);
                break;
            case DINNER:
                totalCalories = getListMacroCalories(day.dinner, macro);
                break;
            case SNACK:
                totalCalories = getListMacroCalories(day.snack, macro);
        }
        return totalCalories;
    }

    public int getMealTimeLabelCalories(Day.MealTime_t mealTime, String label) {
        return getListLabelCalories(day.getMealTimeList(mealTime), label);
    }

    public int getMealTimeMicroMgs(Day.MealTime_t mealTime, Edible.Micros micro) {
        int totalMgs = 0;
        switch(mealTime) {
            case BREAKFAST:
                totalMgs = getListMicroMgs(day.breakfast, micro);
                break;
            case LUNCH:
                totalMgs = getListMicroMgs(day.lunch, micro);
                break;
            case DINNER:
                totalMgs = getListMicroMgs(day.dinner, micro);
                break;
            case SNACK:
                totalMgs = getListMicroMgs(day.snack, micro);
        }
        return totalMgs;
    }

    private int getListCalories(ArrayList<Edible> foodList) {
        int totalCalories = 0;
        for (Edible edible: foodList) {
            if (edible instanceof Food) {
                totalCalories += getFoodCalories((Food)edible);
            }
            else {
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
        for (FoodIntPair foodQuantityPair: meal.foodInMeal) {
            int foodQuantity = foodQuantityPair.quantity;
            Food food = foodQuantityPair.food;
            totalCalories += foodQuantity * getFoodCalories(food);
        }
        return totalCalories;
    }

    private int getListMacroCalories(ArrayList<Edible> foodList, Edible.Macros macro) {
        int totalCalories = 0;
        for (Edible edible: foodList) {
            if (edible instanceof Food) {
                totalCalories += getFoodMacroCalories((Food)edible, macro);
            }
            else {
                totalCalories += getMealMacroCalories((Meal)edible, macro);
            }
        }
        return totalCalories;
    }

    private int getFoodMacroCalories(Food food, Edible.Macros macro) {
        int conversionFactor = getConversionFactor(macro);
        return conversionFactor * food.macros.get(macro);
    }

    private int getMealMacroCalories(Meal meal, Edible.Macros macro) {
        int totalCalories = 0;
        for (FoodIntPair foodQuantityPair: meal.foodInMeal) {
            int foodQuantity = foodQuantityPair.quantity;
            Food food = foodQuantityPair.food;
            totalCalories += foodQuantity * getFoodMacroCalories(food, macro);
        }
        return totalCalories;
    }

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

    private int getListMicroMgs(ArrayList<Edible> foodList, Edible.Micros micro) {
        int totalMg = 0;
        for (Edible edible: foodList) {
            if (edible instanceof Food) {
                totalMg += ((Food)edible).micros.get(micro);
            }
            else {
                totalMg += getMealMicroMgs((Meal)edible, micro);
            }
        }
        return totalMg;
    }

    private int getMealMicroMgs (Meal meal, Edible.Micros micro) {
        int totalMg = 0;
        for (FoodIntPair foodQuantityPair: meal.foodInMeal) {
            int foodQuantity = foodQuantityPair.quantity;
            int microQuantity = foodQuantityPair.food.micros.get(micro);
            totalMg += foodQuantity * microQuantity;
        }
        return totalMg;
    }

    private int getListLabelCalories(ArrayList<Edible> foodList, String label) {
        int totalCalories = 0;
        for (Edible edible: foodList) {
            if (edible instanceof Food) {
                totalCalories += getFoodLabelCalories((Food)edible, label);
            }
            else {
                totalCalories += getMealLabelCalories((Meal)edible, label);
            }
        }
        return totalCalories;
    }

    private int getFoodLabelCalories(Food food, String label) {
        int totalCalories = 0;
        if (food.labels.contains(label)) {
            totalCalories += getFoodCalories(food);
        }
        return totalCalories;
    }

    private int getMealLabelCalories(Meal meal, String label) {
        int totalCalories = 0;
        for (FoodIntPair foodQuantityPair: meal.foodInMeal) {
            Food food = foodQuantityPair.food;
            int foodQuantity = foodQuantityPair.quantity;
            totalCalories += foodQuantity * getFoodLabelCalories(food, label);
        }
        return totalCalories;
    }
}
