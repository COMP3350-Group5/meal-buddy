package comp3350.mealbuddy.objects;

import java.util.ArrayList;

import java.time.LocalDate;
import java.time.LocalTime;

public class Day {
    public enum MealTime_t {
        BREAKFAST, LUNCH, DINNER, SNACK
    }
    public LocalDate LD; //the local date: e.g. 2020-07-04
    //lists of food TIMESTAMPED by local time: (e.g., 10:15:20 -> Banana)
    public ArrayList<Edible> breakfast;
    public ArrayList<Edible> lunch;
    public ArrayList<Edible> dinner;
    public ArrayList<Edible> snack;

    public Day (LocalDate LD) {
        this.LD = LD;
        this.breakfast = new HashMap<>();
        this.lunch = new HashMap<>();
        this.dinner = new HashMap<>();
        this.snack = new HashMap<>();
    }

    //keys are unique, so adding also updates... I think.
    public void addFood(MealTime_t MT, LocalTime LT, Edible e){
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

    public void removeFood(MealTime_t MT, LocalTime LT){
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
