package comp3350.mealbuddy.objects;

import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import java.util.Objects;

public class LabelGoal extends Goal {

    public String id;

    public LabelGoal(int lowerBound, int upperBound, GoalType goalType, String id) {
        super(lowerBound, upperBound, goalType);
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
        boolean isEquals = false;
        if( o instanceof String)
            isEquals = o.equals(id);
        else if( o instanceof LabelGoal) {
            LabelGoal labelGoal = (LabelGoal)o;
            isEquals = labelGoal.id.equals(this.id);
        }
        return isEquals;
    }

}
