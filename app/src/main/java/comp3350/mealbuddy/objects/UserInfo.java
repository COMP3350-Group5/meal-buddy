/****************************************
 * UserInfo
 * holds the user info
 ****************************************/

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

    /*
     * Constructor
     * Initializes the values for the account
     * Parameters:
     *     @param fullname - the full name of the user
     *     @param username - the username of the user, no restrictions
     *     @param password - users password
     *     @param weight - the weight of the user, needed for calculations.
     *     @param height - the height of the user, needed for calculations.
     *     @param activityLevel - activity level of the user, {LOW, MEDIUM, HIGH}
     *     @param sex - the sex of the user
     *     @param age - the age of the user
     */
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
