package comp3350.mealbuddy.business;

import comp3350.mealbuddy.objects.QuantityGoal;
import comp3350.mealbuddy.persistence.IFoodEatenManager;

public class QuantityGoalTracker implements GoalTracker{

    private QuantityGoal goal;
    private IFoodEatenManager foodEatenManager;

    public QuantityGoalTracker(QuantityGoal quantityGoal, IFoodEatenManager foodEatenManager) {
        this.foodEatenManager = foodEatenManager;
        this.goal = quantityGoal;
    }

    @Override
    public boolean isAchieved() {
        int amount = foodEatenManager.getAmount(goal.nameOfTracked);
        int lowerBound = goal.targetAmount - goal.variance;
        int upperBound = goal.targetAmount + goal.variance;
        return (amount>=lowerBound && amount<=upperBound);
    }

}
