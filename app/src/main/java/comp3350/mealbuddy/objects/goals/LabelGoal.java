/****************************************
 * LabelGoal
 * A goal to reach a certain amount of total calories within a certain label
 ****************************************/
package comp3350.mealbuddy.objects.goals;

import androidx.annotation.NonNull;

public class LabelGoal extends Goal {

    public String id;

    /*
     * Constructor
     * Creates a LabelGoal
     * Parameters:
     *     @param lowerBound - The lower bound for the goal.
     *     @param upperBound - The upper bound for the goal.
     *     @param id - The Label associated with the goal.
     *
     */
    public LabelGoal(int lowerBound, int upperBound, GoalType goalType, String id) {
        super(lowerBound, upperBound, goalType, id);
        if(id == null) throw new IllegalArgumentException("Label id cant be null");
        this.id = id;
    }

    /*
     * toString
     * Override for the toString method.
     * Returns string representation of the goal.
     */
    @Override
    @NonNull
    public String toString() {
        return "LabelGoal{" +
                "id='" + id + '\'' +
                ", lowerBound=" + lowerBound +
                ", upperBound=" + upperBound +
                '}';
    }


}
