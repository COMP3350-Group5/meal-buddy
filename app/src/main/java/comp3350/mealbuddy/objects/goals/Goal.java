/****************************************
 * Goal
 * Abstract class for a goal
 ****************************************/
package comp3350.mealbuddy.objects.goals;

public abstract class Goal {
    public enum GoalType {
        RATIO, QUANTITY
    }
    public int lowerBound;
    public int upperBound;
    public GoalType goalType;

    /*
     * Constructor
     * Creates a goal with a lower and upper bound, and a type.
     */
    public Goal(int lowerBound, int upperBound, GoalType goalType) {
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
        this.goalType = goalType;
    }
}
