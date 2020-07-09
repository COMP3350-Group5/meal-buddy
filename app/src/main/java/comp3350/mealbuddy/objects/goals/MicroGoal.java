package comp3350.mealbuddy.objects.goals;

import androidx.annotation.NonNull;

import comp3350.mealbuddy.objects.consumables.Edible.Micros;

public class MicroGoal extends Goal {

    public Micros id;

    public MicroGoal(int lowerBound, int upperBound, Micros id) {
        super(lowerBound, upperBound, GoalType.QUANTITY);
        if(id ==null)
            throw new IllegalArgumentException("Micro must be specified");
        this.id = id;
    }

    @Override
    @NonNull
    public String toString() {
        return "MicroGoal{" +
                "id=" + id +"mg"+
                ", lowerBound=" + lowerBound +
                ", upperBound=" + upperBound +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MicroGoal)) return false;
        MicroGoal microGoal = (MicroGoal) o;
        return id == microGoal.id;
    }

}
