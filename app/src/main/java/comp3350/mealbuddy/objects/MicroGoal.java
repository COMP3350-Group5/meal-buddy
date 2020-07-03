package comp3350.mealbuddy.objects;

import androidx.annotation.NonNull;
import comp3350.mealbuddy.objects.Edible.Micros;

public class MicroGoal extends Goal {

    Micros id;

    public MicroGoal(int lowerBound, int upperBound, Micros id) {
        super(lowerBound, upperBound);
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
}
