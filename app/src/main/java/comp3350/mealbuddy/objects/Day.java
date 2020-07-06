package comp3350.mealbuddy.objects;

import java.util.ArrayList;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class Day {
    public enum MealTime_t {
        BREAKFAST, LUNCH, DINNER, SNACK
    }
    public LocalDate LD; //the local date: e.g. 2020-07-04

    public ArrayList<Edible> breakfast;
    public ArrayList<Edible> lunch;
    public ArrayList<Edible> dinner;
    public ArrayList<Edible> snack;

    public Day (LocalDate LD) {
        this.LD = LD;
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

    public void removeFood(MealTime_t MT, Edible e){
        switch(MT) {
            case BREAKFAST:
                breakfast.remove(e);
                break;
            case LUNCH:
                lunch.remove(e);
                break;
            case DINNER:
                dinner.remove(e);
                break;
            case SNACK:
                snack.remove(e);
                break;
        }
    }

    public ArrayList<Edible> getMealTimeList(MealTime_t MT){
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
