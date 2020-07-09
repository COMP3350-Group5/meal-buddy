/****************************************
 * MicroGoal
 * A goal to reach a certain amount of a Micro in a day
 ****************************************/
package comp3350.mealbuddy.objects.goals;

import androidx.annotation.NonNull;

import comp3350.mealbuddy.objects.consumables.Edible.Micros;

public class MicroGoal extends Goal {

    public Micros id;

    /*
     * Constructor
     * Creates a MicroGoal
     */
    public MicroGoal(int lowerBound, int upperBound, Micros id) {
        super(lowerBound, upperBound, GoalType.QUANTITY);
        this.id = id;
    }

    /*
     * toString
     * Override for the toString method.
     */
    @Override
    @NonNull
    public String toString() {
        return "MicroGoal{" +
                "id=" + id +"mg"+
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
        if (!(o instanceof MicroGoal)) return false;
        MicroGoal microGoal = (MicroGoal) o;
        return id == microGoal.id;
    }

}
