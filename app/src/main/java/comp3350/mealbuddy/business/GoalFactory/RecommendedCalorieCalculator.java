package comp3350.mealbuddy.business.GoalFactory;

import java.util.List;

import comp3350.mealbuddy.objects.CalorieGoal;
import comp3350.mealbuddy.objects.Goal;
import comp3350.mealbuddy.objects.UserInfo;
import comp3350.mealbuddy.objects.UserInfo.ActivityLevel;
import comp3350.mealbuddy.objects.UserInfo.Sex;

import static comp3350.mealbuddy.objects.UserInfo.Sex.MALE;

public class RecommendedCalorieCalculator {

    static public int getTotalRecCalories(double weight, double height, ActivityLevel activityLevel, Sex sex, int age){
        return  (sex == MALE)
                ?   getMaleTotalCalorieRecAmnt(weight, height, activityLevel, sex, age)
                :    getFemaleTotalCalorieRecAmnt(weight, height, activityLevel, sex, age);
    }

    static private int getMaleTotalCalorieRecAmnt(double weight, double height, ActivityLevel activityLevel, Sex sex, int age){
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

    static private int getFemaleTotalCalorieRecAmnt(double weight, double height, ActivityLevel activityLevel, Sex sex, int age){
        double mBaseAmount = 655.1;
        double mWeightCoefficient = 4.35;
        double mHeightCoefficient = 4.7;
        double mAgeCoefficient = 4.7;
        double activityCoefficient = getActivityCoefficient(activityLevel);
        double recommendedCalories = mBaseAmount + (mWeightCoefficient*weight) +
                (mHeightCoefficient*height) - (mAgeCoefficient*age);
        recommendedCalories *= activityCoefficient;
        return (int)recommendedCalories;
    }

    static private double getActivityCoefficient(UserInfo.ActivityLevel activityLevel) {
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


}
