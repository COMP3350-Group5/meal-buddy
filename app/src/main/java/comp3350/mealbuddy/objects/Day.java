package comp3350.mealbuddy.objects;

import java.sql.Time;
import java.util.HashMap;

public class Day {
    public enum MealTime_t {
        BREAKFAST, LUNCH, DINNER, SNACK
    }
    public int day; // represented as day-of-year (1-365)
    //lists of food TIMESTAMPED by local time: (e.g., 10:15:20 -> Banana)
    private HashMap<Time, Edible> breakfast;
    private HashMap<Time, Edible> lunch;
    private HashMap<Time, Edible> dinner;
    private HashMap<Time, Edible> snack;

    public Day (int day) {
        this.day = day;
        this.breakfast = new HashMap<>();
        this.lunch = new HashMap<>();
        this.dinner = new HashMap<>();
        this.snack = new HashMap<>();
    }

    //keys are unique, so adding also updates... I think.
    public void addFood(MealTime_t MT, Time LT, Edible e){
        switch(MT) {
            case BREAKFAST:
                breakfast.put(LT, e);
                break;
            case LUNCH:
                lunch.put(LT, e);
                break;
            case DINNER:
                dinner.put(LT, e);
                break;
            case SNACK:
                snack.put(LT, e);
                break;
        }
    }

    public void removeFood(MealTime_t MT, Time LT){
        switch(MT) {
            case BREAKFAST:
                breakfast.remove(LT);
                break;
            case LUNCH:
                lunch.remove(LT);
                break;
            case DINNER:
                dinner.remove(LT);
                break;
            case SNACK:
                snack.remove(LT);
                break;
        }
    }


}
