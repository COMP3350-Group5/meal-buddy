package comp3350.mealbuddy.objects;

public abstract class Goal {
    public int lowerBound;
    public int upperBound;
    public String nameOfTracked;

    public Goal(int lowerBound, int upperBound, String nameOfTracked) {
        validateInput(lowerBound, upperBound, nameOfTracked);
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
        this.nameOfTracked = nameOfTracked;
    }

    private void validateInput(int lowerBound, int upperBound, String nameOfTracked) {
        if (lowerBound < 0 || upperBound < 0) {
            throw new IllegalArgumentException("target goals cannot be negative");
        }
        if (nameOfTracked == null) {
            throw new NullPointerException("Name can not be null for trackable item");
        }
        if (nameOfTracked.isEmpty()) {
            throw new IllegalArgumentException("The Goal must be given a name of an item to track");
        }
        if (lowerBound > upperBound) {
            throw new IllegalArgumentException("Lower bound can not exceed upper bound");
        }

    }


}
