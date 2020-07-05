package comp3350.mealbuddy.objects;

import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MicroGoal)) return false;
        MicroGoal microGoal = (MicroGoal) o;
        return id == microGoal.id;
    }

}
