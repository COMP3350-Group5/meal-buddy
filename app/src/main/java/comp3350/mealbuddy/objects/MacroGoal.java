package comp3350.mealbuddy.objects;
import android.os.Build;

import comp3350.mealbuddy.objects.Edible.Macros;


import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import java.util.Objects;

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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MacroGoal)) return false;
        MacroGoal macroGoal = (MacroGoal) o;
        return id == macroGoal.id;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)  //Kitkat is api 19 marshmallow is 23
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
