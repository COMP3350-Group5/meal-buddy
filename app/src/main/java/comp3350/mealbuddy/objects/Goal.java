package comp3350.mealbuddy.objects;

import java.util.List;

import comp3350.mealbuddy.business.Trackable;

public class Goal {

    List<Trackable> foodGroupGoals;
    List<Trackable> macroNutrientGoals;
    List<Trackable> microNutrientGoals;

    public Goal(List<Trackable> foodGroupGoals, List<Trackable> macroNutrientGoals, List<Trackable> microNutrientGoals) {
        this.foodGroupGoals = foodGroupGoals;
        this.macroNutrientGoals = macroNutrientGoals;
        this.microNutrientGoals = microNutrientGoals;
    }
}
