package comp3350.mealbuddy.objects;

public class UserInfo {

    public enum Sex {
      MALE, FEMALE,
    }

    public enum ActivityLevel {
        LOW, MEDIUM, HIGH,
    }

    public String fullname;
    public String username;
    public String password; //hash it in the future
    public double weight; //in lbs
    public double height; //in cm
    public ActivityLevel activityLevel;
    public Sex sex;
    public int age;

    public UserInfo(String fullname, String username, String password, double weight, double height, ActivityLevel activityLevel, Sex sex, int age) {
        this.fullname = fullname;
        this.username = username;
        this.password = password;
        this.weight = weight;
        this.height = height;
        this.activityLevel = activityLevel;
        this.sex = sex;
        this.age = age;
    }
}
