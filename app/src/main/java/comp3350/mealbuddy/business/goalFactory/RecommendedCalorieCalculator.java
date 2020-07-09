/****************************************
 * RecommendedCalorieCalculator
 * Class for recommended calorie calculations
 ****************************************/

package comp3350.mealbuddy.business.goalFactory;

import comp3350.mealbuddy.objects.UserInfo;
import comp3350.mealbuddy.objects.UserInfo.ActivityLevel;

import static comp3350.mealbuddy.objects.UserInfo.Sex.MALE;

public class RecommendedCalorieCalculator {


    /*
     * getTotalRecommendedCalories
     * Get the total amount of recommended calories based on a users sex, weight, height, activity level, and age.
     * Parameters:
     *     @param info - The user
     * Return:
     *     The amount of recommended calories for the user.
     */
    public static int getTotalRecommendedCalories(UserInfo info){
        validateData(info);
        return  (info.sex == MALE)
                ?   getMaleTotalCalorieRecAmnt(info.weight, info.height, info.activityLevel, info.age)
                :    getFemaleTotalCalorieRecAmnt(info.weight, info.height, info.activityLevel, info.age);
    }


    /*
     * getMaleTotalCalorieRecAmnt
     * Get the recommended calories for a male, based on weight, height, activity level, and age.
     * Parameters:
     *     @param weight - The weight of the user in lbs.
     *     @param height - The height of the user in cm.
     *     @param activityLevel - The users activity level.
     *     @param age - The age of the user in years.
     * Return:
     *     Recommended amount of calories for the user.
     */
    private static int getMaleTotalCalorieRecAmnt(double weight, double height, ActivityLevel activityLevel, int age){
        double mBaseAmount = 66.47;
        double mWeightCoefficient = 6.24;
        double mHeightCoefficient = 12.7;
        double mAgeCoefficient = 6.755;
        double activityCoefficient = getActivityCoefficient(activityLevel);
        int recommendedCalories = (int) (mBaseAmount + (mWeightCoefficient*weight) +
                (mHeightCoefficient*height) - (mAgeCoefficient*age));
        recommendedCalories *= activityCoefficient;
        return recommendedCalories;
    }

    /*
     * getMaleTotalCalorieRecAmnt
     * Get the recommended calories for a female, based on weight, height, activity level, and age.
     * Parameters:
     *     @param weight - The weight of the user in lbs.
     *     @param height - The height of the user in cm.
     *     @param activityLevel - The users activity level.
     *     @param age - The age of the user in years.
     * Return:
     *     Recommended amount of calories for the user.
     */
    private static int getFemaleTotalCalorieRecAmnt(double weight, double height, ActivityLevel activityLevel, int age){
        double fBaseAmount = 655.1;
        double fWeightCoefficient = 4.35;
        double fHeightCoefficient = 4.7;
        double fAgeCoefficient = 4.7;
        double activityCoefficient = getActivityCoefficient(activityLevel);
        double recommendedCalories = fBaseAmount + (fWeightCoefficient*weight) +
                (fHeightCoefficient*height) - (fAgeCoefficient*age);
        recommendedCalories *= activityCoefficient;
        return (int)recommendedCalories;
    }


    /*
     * getActivityCoefficient
     * Get the coefficient for a user based on their activity level
     * Parameters:
     *     @param activityLevel - The activity level we are getting the coefficient for.
     * Return:
     *     The coefficient for the activity level.
     *
     */
    private static double getActivityCoefficient(UserInfo.ActivityLevel activityLevel) {
        double activityCoefficient = -1;
        switch (activityLevel) {
            case LOW:
                activityCoefficient = 1.2;
                break;
            case MEDIUM:
                activityCoefficient = 1.55;
                break;
            case HIGH:
                activityCoefficient = 1.9;
                break;
        }
        return activityCoefficient;
    }

    /*
     * validateData
     * Make sure a user's data is valid.
     * Parameters:
     *     @param user - The user to validate
     */
    private static void validateData(UserInfo user){
        if(user.activityLevel == null || user.sex == null){
            throw new IllegalArgumentException("Null value passed in");
        }
        if(user.height <= 0 || user.weight <= 0 || user.age <=0 ){
            throw new IllegalArgumentException("Height and weight must be greater than 0");
        }
    }


}
