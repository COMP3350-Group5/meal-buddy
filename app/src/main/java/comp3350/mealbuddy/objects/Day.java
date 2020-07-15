package comp3350.mealbuddy.objects;

import java.util.ArrayList;

import comp3350.mealbuddy.objects.consumables.Edible;

public class Day {
    public enum MealTimeType {
        BREAKFAST, LUNCH, DINNER, SNACK
    }
    public int dayOfYear; // represented as day-of-year (1-365)
    private ArrayList<Edible> breakfast;
    private ArrayList<Edible> lunch;
    private ArrayList<Edible> dinner;
    private ArrayList<Edible> snack;

    public Day (int dayOfYear) {
        if (dayOfYear > 0 && dayOfYear < 367)
            this.dayOfYear = dayOfYear;
        else
            throw new IllegalArgumentException("Must pass valid date");

        this.breakfast = new ArrayList<>();
        this.lunch = new ArrayList<>();
        this.dinner = new ArrayList<>();
        this.snack = new ArrayList<>();
    }

    //keys are unique, so adding also updates... I think.
    public void addFood(MealTimeType MT, Edible e){
        if (MT == null || e == null)
            return;

        switch(MT) {
            case BREAKFAST:
                breakfast.add(e);
                break;
            case LUNCH:
                lunch.add(e);
                break;
            case DINNER:
                dinner.add(e);
                break;
            case SNACK:
                snack.add(e);
                break;
            default:
                break;
        }
    }

    public void removeFood(MealTimeType MT, Edible edible){
        switch(MT) {
            case BREAKFAST:
                breakfast.remove(edible);
                break;
            case LUNCH:
                lunch.remove(edible);
                break;
            case DINNER:
                dinner.remove(edible);
                break;
            case SNACK:
                snack.remove(edible);
                break;
        }
    }

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

}
