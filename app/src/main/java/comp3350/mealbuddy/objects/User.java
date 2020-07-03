package comp3350.mealbuddy.objects;

public class User {

    private String name;
    private int height;

    public enum Sex{
        MALE, FEMALE;
    }

    public enum ActivityLevel{
        LOW, MEDIUM, HIGH;
    }

    public User(String name, int height) {
        this.name = name;
        this.height = height;
    }
}
