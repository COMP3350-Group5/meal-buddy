package comp3350.mealbuddy.business.goalfactory;

import java.util.ArrayList;
import java.util.List;

import comp3350.mealbuddy.objects.goals.CalorieGoal;
import comp3350.mealbuddy.objects.goals.Goal;
import comp3350.mealbuddy.objects.UserInfo;


public abstract class GoalFactory {

    protected UserInfo userInfo;

    public GoalFactory(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public List<Goal> makeGoals(){
        validateDate(userInfo);
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
        int recCalAmnt = RecommendedCalorieCalculator.getTotalRecommendedCalories(userInfo);
        int goalBuffer = 200;   //set the goal acheived if plus or minus 200 from the recomended amount
        goalList.add(new CalorieGoal(recCalAmnt-goalBuffer, recCalAmnt+goalBuffer));
    }

    private void validateDate(UserInfo user){
        if(user.activityLevel == null || user.sex == null){
            throw new IllegalArgumentException("Null value passed in");
        }
        if(user.height <= 0 || user.weight <= 0 || user.age <=0 ){
            throw new IllegalArgumentException("Height and weight and age must be greater than 0");
        }
    }

}
