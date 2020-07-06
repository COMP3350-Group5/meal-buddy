package comp3350.mealbuddy.objects;
import android.os.Build;

import comp3350.mealbuddy.objects.Edible.Macros;


import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import java.util.Objects;

import javax.crypto.Mac;

public class MacroGoal extends Goal {

    public Macros id;

    public MacroGoal(int lowerBound, int upperBound, GoalType goalType, Macros id) {
        super(lowerBound, upperBound, goalType);
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MacroGoal)) return false;
        MacroGoal macroGoal = (MacroGoal) o;
        return id == macroGoal.id;
    }

}
