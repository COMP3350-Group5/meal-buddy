package comp3350.mealbuddy.objects;

import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;

public class Day {
    public enum MealTime_t {
        BREAKFAST, LUNCH, DINNER, SNACK
    }
    public int dayOfYear; // represented as day-of-year (1-365)
    //lists of food TIMESTAMPED by local time: (e.g., 10:15:20 -> Banana)
    private ArrayList<Edible> breakfast;
    private ArrayList<Edible> lunch;
    private ArrayList<Edible> dinner;
    private ArrayList<Edible> snack;

    public Day (int dayOfYear) {
        this.dayOfYear = dayOfYear;
        this.breakfast = new ArrayList<>();
        this.lunch = new ArrayList<>();
        this.dinner = new ArrayList<>();
        this.snack = new ArrayList<>();
    }

    //keys are unique, so adding also updates... I think.
    public void addFood(MealTime_t MT, Edible e){
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
        }
    }

    public void removeFood(MealTime_t MT, Edible edible){
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

    public String getMeal(MealTime_t MT) {
        String meal = "";
        switch(MT) {
            case BREAKFAST:
                for (Edible edible:breakfast) {
                    meal += edible.toString() + "\n";
                }
                break;
            case LUNCH:
                for (Edible edible:lunch) {
                    meal += edible.toString() + "\n";
                }
                break;
            case DINNER:
                for (Edible edible:dinner) {
                    meal += edible.toString() + "\n";
                }
                break;
            case SNACK:
                for (Edible edible:snack) {
                    meal += edible.toString() + "\n";
                }
                break;
        }
        return meal;
    }


}
