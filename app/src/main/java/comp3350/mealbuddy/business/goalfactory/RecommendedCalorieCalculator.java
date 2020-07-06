package comp3350.mealbuddy.business.goalfactory;

import comp3350.mealbuddy.objects.UserInfo;
import comp3350.mealbuddy.objects.UserInfo.ActivityLevel;

import static comp3350.mealbuddy.objects.UserInfo.Sex.MALE;

public class RecommendedCalorieCalculator {

    public static int getTotalRecommendedCalories(UserInfo info){
        validateDate(info);
        return  (info.sex == MALE)
                ?   getMaleTotalCalorieRecAmnt(info.weight, info.height, info.activityLevel, info.age)
                :    getFemaleTotalCalorieRecAmnt(info.weight, info.height, info.activityLevel, info.age);
    }

    private static int getMaleTotalCalorieRecAmnt(double weight, double height, ActivityLevel activityLevel, int age){
        double mBaseAmount = 66.47;
        double mWeightCoefficient = 6.24;
        double mHeightCoefficient = 12.7;
        double mAgeCoefficient = 6.755;
        double activityCoefficient = getActivityCoefficient(activityLevel);
        int recommendedCalories = (int) (mBaseAmount + (mWeightCoefficient*weight) +
                (mHeightCoefficient*height) - (mAgeCoefficient*age));
        recommendedCalories *= activityCoefficient;
        return (int) recommendedCalories;
    }

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

    private static void validateDate(UserInfo user){
        if(user.activityLevel == null || user.sex == null){
            throw new IllegalArgumentException("Null value passed in");
        }
        if(user.height <= 0 || user.weight <= 0 || user.age <=0 ){
            throw new IllegalArgumentException("Height and weight must be greater than 0");
        }
    }


}
