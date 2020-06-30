package comp3350.mealbuddy.business;

import comp3350.mealbuddy.persistence.IFoodDb;

public class RatioTracker extends Trackable {
    private int totalCalories;

    public RatioTracker(int targetAmount, int variance, String name, int totalCalories, IFoodDb foodDb) {
        super(targetAmount, variance, name, foodDb);
        this.totalCalories = totalCalories;
    }

    public boolean goalAchieved(){
        return targetAmount == foodDb.getAmount();
        ///todo unhappy paths
    }

}
