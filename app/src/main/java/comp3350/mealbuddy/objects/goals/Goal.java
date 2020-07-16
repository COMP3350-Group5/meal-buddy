/****************************************
 * Goal
 * Abstract class for a goal
 ****************************************/
package comp3350.mealbuddy.objects.goals;

import java.util.Objects;

public abstract class Goal {
    public enum GoalType {
        RATIO, QUANTITY
    }

    public int lowerBound;
    public int upperBound;
    public GoalType goalType;
    public Object id;

    /*
     * Constructor
     * Creates a goal with a lower and upper bound, and a type.
     * Parameters:
     *     @param lowerBound - The lower bound for the goal.
     *     @param upperBound - The upper bound for the goal.
     *     @param goalType - The type of goal.
     * */
    public Goal(int lowerBound, int upperBound, GoalType goalType, Object id) {
        validateData(lowerBound, upperBound, goalType);
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
        this.goalType = goalType;
        this.id = id;
    }

    private void validateData(int lowerBound, int upperBound, GoalType goalType) {
        if (goalType == null)
            throw new IllegalArgumentException("must specify a goal type");
        else if (lowerBound > upperBound || lowerBound < 0)
            throw new IllegalArgumentException("invalid upper and lower bound values");
        else if (goalType == GoalType.RATIO && upperBound > 100)
            throw new IllegalArgumentException("upper Bound can not exceed 100%");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Goal)) return false;
        Goal goal = (Goal) o;
        return id.equals(goal.id);
    }

}
