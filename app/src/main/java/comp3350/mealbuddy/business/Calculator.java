package comp3350.mealbuddy.business;

import comp3350.mealbuddy.objects.Day;
import comp3350.mealbuddy.objects.Edible;

public class Calculator {

    public Day day;
    public Calculator(Day day) {
        this.day = day;
    }

    public int getTotalCalories() {
        return 5;
    }

    public int getLabelCalories(String label) {
        return 4;
    }

    public int getMacroCalories(Edible.Macros macro) {
        return 3;
    }

    public int getMicroAmount(Edible.Micros micro) {
        return 2;
    }

}
