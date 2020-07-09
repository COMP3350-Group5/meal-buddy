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
     */
    public LabelGoal(int lowerBound, int upperBound, GoalType goalType, String id) {
        super(lowerBound, upperBound, goalType);
        this.id = id;
    }

    /*
     * toString
     * Override for the toString method.
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

    /*
     * equals
     * Override for the equals method.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LabelGoal)) return false;
        LabelGoal labelGoal = (LabelGoal) o;
        return id.equals(labelGoal.id);
    }


}
