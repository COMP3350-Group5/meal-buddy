package comp3350.mealbuddy.objects;

import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import java.util.Objects;

import comp3350.mealbuddy.objects.Edible.Micros;

public class MicroGoal extends Goal {

    public Micros id;

    public MicroGoal(int lowerBound, int upperBound, Micros id) {
        super(lowerBound, upperBound, GoalType.QUANTITY);
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
        boolean isEquals = false;
        if( o instanceof Edible.Micros)
            isEquals = (Micros)o == id;
        else if( o instanceof MicroGoal) {
            MicroGoal microGoal = (MicroGoal)o;
            isEquals = microGoal.id == this.id;
        }
        return isEquals;
    }

}
