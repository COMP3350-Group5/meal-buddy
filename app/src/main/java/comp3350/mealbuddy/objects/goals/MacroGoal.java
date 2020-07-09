package comp3350.mealbuddy.objects.goals;

import comp3350.mealbuddy.objects.consumables.Edible.Macros;


import androidx.annotation.NonNull;

public class MacroGoal extends Goal {

    public Macros id;

    public MacroGoal(int lowerBound, int upperBound, GoalType goalType, Macros id) {
        super(lowerBound, upperBound, goalType);
        if(id ==null)
            throw new IllegalArgumentException("Macro must be specified");
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
