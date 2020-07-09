package comp3350.mealbuddy.objects.goals;

import androidx.annotation.NonNull;

public class LabelGoal extends Goal {

    public String id;

    public LabelGoal(int lowerBound, int upperBound, GoalType goalType, String id) {
        super(lowerBound, upperBound, goalType);
        if(id == null) throw new IllegalArgumentException("Label id cant be null");
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
