package comp3350.mealbuddy.objects.goals;

public abstract class Goal {
    public enum GoalType {
        RATIO, QUANTITY
    }
    public int lowerBound;
    public int upperBound;
    public GoalType goalType;

    public Goal(int lowerBound, int upperBound, GoalType goalType) {
        validateData(lowerBound, upperBound, goalType);
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
        this.goalType = goalType;
    }

    private void validateData(int lowerBound, int upperBound, GoalType goalType) {
        if(goalType==null)
            throw new IllegalArgumentException("must specify a goal type");
        else if(lowerBound>upperBound || lowerBound<0)
            throw new IllegalArgumentException("invalid upper and lower bound values");
        else if(goalType==GoalType.RATIO && upperBound>100)
            throw new IllegalArgumentException("upper Bound can not exceed 100%");
    }

}
