package comp3350.mealbuddy.business;

import comp3350.mealbuddy.persistence.IFoodDb;

public class QuantityTracker extends Trackable{

    public QuantityTracker(int targetAmount, int variance, String name, IFoodDb foodDb) {
        super(targetAmount, variance, name, foodDb);
    }

    public boolean goalAchieved(){
        return false;
        ///todo };
    }

}
