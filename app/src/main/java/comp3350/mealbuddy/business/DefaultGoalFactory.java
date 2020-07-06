package comp3350.mealbuddy.business;

import java.util.ArrayList;
import java.util.List;

import comp3350.mealbuddy.objects.CalorieGoal;
import comp3350.mealbuddy.objects.Edible;
import comp3350.mealbuddy.objects.Edible.Macros;
import comp3350.mealbuddy.objects.Edible.Micros;
import comp3350.mealbuddy.objects.Goal;
import comp3350.mealbuddy.objects.Goal.GoalType;
import comp3350.mealbuddy.objects.MacroGoal;
import comp3350.mealbuddy.objects.UserInfo;
import comp3350.mealbuddy.objects.UserInfo.ActivityLevel;

import static comp3350.mealbuddy.objects.Edible.Macros.Fat;
import static comp3350.mealbuddy.objects.Edible.Macros.Carbohydrates;
import static comp3350.mealbuddy.objects.Edible.Macros.Protein;
import static comp3350.mealbuddy.objects.UserInfo.Sex.MALE;

public class DefaultGoalFactory extends GoalFactory {

    private UserInfo userInfo;

    public DefaultGoalFactory(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    @Override
    public List<Goal> makeGoals() {
        List<Goal> list = new ArrayList<>();

        return list;
    }

    private void generateTotalCalorieGoal(UserInfo userInfo, List<Goal> goalList){
        int recCalAmount;
        int calVariance = 200;
        if(userInfo.sex == MALE){
            recCalAmount = getMaleTotalCalorieRecAmnt(userInfo);
        }else{
            recCalAmount = getFemaleTotalCalorieRecAmnt(userInfo);
        }
        goalList.add(new CalorieGoal(recCalAmount-calVariance, recCalAmount+calVariance, QUANTITY));
    }

    private int getMaleTotalCalorieRecAmnt(UserInfo userInfo){
        double mBaseAmount = 66.47;
        double mWeightCoefficient = 6.24;
        double mHeightCoefficient = 12.7;
        double mAgeCoefficient = 6.755;
        double activityCoefficient = getActivityCoefficient(userInfo.activityLevel);
        int recommendedCalories = (int) (mBaseAmount + (mWeightCoefficient*userInfo.weight) +
                (mHeightCoefficient*userInfo.height) - (mAgeCoefficient*userInfo.age));
        recommendedCalories *= activityCoefficient;
        return (int) recommendedCalories;
    }

    private int getFemaleTotalCalorieRecAmnt(UserInfo userInfo){
        double mBaseAmount = 66.47;
        double mWeightCoefficient = 6.24;
        double mHeightCoefficient = 12.7;
        double mAgeCoefficient = 6.755;
        double activityCoefficient = getActivityCoefficient(userInfo.activityLevel);
        double recommendedCalories = mBaseAmount + (mWeightCoefficient*userInfo.weight) +
                (mHeightCoefficient*userInfo.height) - (mAgeCoefficient*userInfo.age);
        recommendedCalories *= activityCoefficient;
        return (int)recommendedCalories;
    }

    private double getActivityCoefficient(ActivityLevel activityLevel) {
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

    private void generateMacroGoals(UserInfo userInfo, List<Goal> goalList){
        int MacroVariance = 5;
        int fatPercent = 25;
        int proteinPercent = 25;
        int carbPercent = 50;
        GoalType gType = GoalType.RATIO;
        goalList.add(new MacroGoal(fatPercent-MacroVariance, fatPercent+MacroVariance, gType, Fat));
        goalList.add(new MacroGoal(proteinPercent-MacroVariance, proteinPercent+MacroVariance, gType, Protein));
        goalList.add(new MacroGoal(carbPercent-MacroVariance, carbPercent+MacroVariance, gType, Carbohydrates));
    }

    private void generateMicroGoals(UserInfo userInfo, List<Goal> goalList){

    }







}
