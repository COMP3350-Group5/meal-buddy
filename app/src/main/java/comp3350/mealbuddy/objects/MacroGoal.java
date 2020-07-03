package comp3350.mealbuddy.objects;

import androidx.annotation.NonNull;

import comp3350.mealbuddy.objects.MacroFactory.Macros;

public class MacroGoal extends Goal {

    Macros id;

    public MacroGoal(int lowerBound, int upperBound, Macros id) {
        super(lowerBound, upperBound);
        this.id = id;
    }

    @Override
    @NonNull
    public String toString() {
        return "MacroGoal{" +
                "id=" + id + "g"+
                ", lowerBound=" + lowerBound +
                ", upperBound=" + upperBound +
                '}';
    }
}
