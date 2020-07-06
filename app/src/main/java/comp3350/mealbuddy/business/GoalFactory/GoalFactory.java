package comp3350.mealbuddy.business.GoalFactory;

import java.util.ArrayList;
import java.util.List;

import comp3350.mealbuddy.objects.CalorieGoal;
import comp3350.mealbuddy.objects.Goal;
import comp3350.mealbuddy.objects.UserInfo;

import static comp3350.mealbuddy.objects.UserInfo.Sex.MALE;


public abstract class GoalFactory {

    protected UserInfo userInfo;

    public GoalFactory(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public List<Goal> makeGoals(){
        List<Goal> goalList = new ArrayList<>();
        addTotalCalorieGoal(userInfo, goalList);
        addMacroGoals(userInfo, goalList);
        addMicroGoals(userInfo, goalList);
        addLabelGoals(userInfo, goalList);
        return goalList;
    }

    protected abstract void addMacroGoals(UserInfo userInfo, List<Goal> goalList);
    protected abstract void addMicroGoals(UserInfo userInfo, List<Goal> goalList);
    protected abstract void addLabelGoals(UserInfo userInfo, List<Goal> goalList);

    private void addTotalCalorieGoal(UserInfo userInfo, List<Goal> goalList){
        int recCalAmount;
        int calVariance = 200;
        if(userInfo.sex == MALE){
            recCalAmount = getMaleTotalCalorieRecAmnt(userInfo);
        }else{
            recCalAmount = getFemaleTotalCalorieRecAmnt(userInfo);
        }
        goalList.add(new CalorieGoal(recCalAmount-calVariance, recCalAmount+calVariance, Goal.GoalType.QUANTITY));
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

    private double getActivityCoefficient(UserInfo.ActivityLevel activityLevel) {
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
