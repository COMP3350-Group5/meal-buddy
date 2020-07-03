package comp3350.mealbuddy.objects;

public abstract class Goal {
    int lowerBound;
    int upperBound;

    public Goal(int lowerBound, int upperBound) {
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
    }
}
