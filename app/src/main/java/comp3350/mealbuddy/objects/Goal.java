package comp3350.mealbuddy.objects;

public abstract class Goal {
    public int targetAmount;
    public int variance;
    public String nameOfTracked;

    public Goal(int targetAmount, int variance, String nameOfTracked) {
        validateInput(targetAmount, variance, nameOfTracked);
        this.targetAmount = targetAmount;
        this.variance = variance;
        this.nameOfTracked = nameOfTracked;
    }

    private void validateInput(int amount, int variance, String name) {
        if (amount < 0) {
            throw new IllegalArgumentException("Amount can not be " + amount);
        }
        if (variance < 0) {
            throw new IllegalArgumentException("Amount can not be " + amount);
        }
        if (name == null) {
            throw new NullPointerException("Name can not be null for trackable item");
        }
        if (name.isEmpty()) {
            throw new IllegalArgumentException("The Goal must be given a name");
        }
        if (variance > amount) {
            throw new IllegalArgumentException("Variance can not exceed target amount");
        }

    }


}
