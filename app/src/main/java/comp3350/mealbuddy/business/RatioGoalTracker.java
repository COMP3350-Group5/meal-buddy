package comp3350.mealbuddy.business;

import comp3350.mealbuddy.objects.RatioGoal;
import comp3350.mealbuddy.persistence.IFoodEatenManager;

public class RatioGoalTracker implements GoalTracker {

    private RatioGoal goal;
    private IFoodEatenManager foodEatenManager;

    public RatioGoalTracker(RatioGoal ratioGoal, IFoodEatenManager foodEatenManager) {
        this.goal = ratioGoal;
        this.foodEatenManager = foodEatenManager;
    }

    @Override
    public boolean isAchieved() {
        float amount = calculatePercent();
        int lowerBound = goal.targetAmount - goal.variance;
        int upperBound = goal.targetAmount + goal.variance;
        return (amount>=lowerBound && amount<=upperBound);
    }

    //Returns in percent form (50.5 instead of .505)
    private double calculatePercent(){
        double total = foodEatenManager.getTotalAmount();
        double amount = foodEatenManager.getAmount(goal.nameOfTracked);
        return 100*amount/total;
    }

}
