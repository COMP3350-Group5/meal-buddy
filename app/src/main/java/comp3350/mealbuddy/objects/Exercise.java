package comp3350.mealbuddy.objects;

public class Exercise {

    public enum Intensity {
        Low, Medium, High,
    }

    public String name;
    public double duration;

    public Exercise(String name) {
        this.name = name;
    }
}
