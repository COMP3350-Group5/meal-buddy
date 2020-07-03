package comp3350.mealbuddy.objects;

import androidx.annotation.NonNull;

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
}
