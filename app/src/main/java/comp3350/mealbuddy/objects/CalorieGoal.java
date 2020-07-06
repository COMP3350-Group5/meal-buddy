package comp3350.mealbuddy.objects;

import androidx.annotation.Nullable;

public class CalorieGoal extends Goal {

    public CalorieGoal(int lowerBound, int upperBound) {
        super(lowerBound, upperBound, GoalType.QUANTITY);
    }

    @Override
    //Can only have one total calorie goal.
    public boolean equals(@Nullable Object obj) {
        return obj instanceof CalorieGoal;
    }

}
