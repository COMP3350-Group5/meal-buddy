package comp3350.mealbuddy.objects;

public abstract class Goal {
    public enum GoalType {
        RATIO, QUANTITY
    }
    public int lowerBound;
    public int upperBound;
    public GoalType goalType;

    public Goal(int lowerBound, int upperBound, GoalType goalType) {
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
        this.goalType = goalType;
    }
}
