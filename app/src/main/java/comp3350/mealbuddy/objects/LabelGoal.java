package comp3350.mealbuddy.objects;

import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import java.util.Objects;

public class LabelGoal extends Goal {

    String id;

    public LabelGoal(int lowerBound, int upperBound, String id) {
        super(lowerBound, upperBound);
        this.id = id;
    }

    @Override
    @NonNull
    public String toString() {
        return "LabelGoal{" +
                "id='" + id + '\'' +
                ", lowerBound=" + lowerBound +
                ", upperBound=" + upperBound +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LabelGoal)) return false;
        LabelGoal labelGoal = (LabelGoal) o;
        return id.equals(labelGoal.id);
    }

}
