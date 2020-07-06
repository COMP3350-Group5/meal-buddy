package comp3350.mealbuddy.business.GoalFactory;

import java.util.ArrayList;
import java.util.List;

import comp3350.mealbuddy.objects.CalorieGoal;
import comp3350.mealbuddy.objects.Goal.GoalType;
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
        int recCalAmnt = RecommendedCalorieCalculator.getTotalRecCalories(
                userInfo.weight, userInfo.height, userInfo.activityLevel, userInfo.sex, userInfo.age
        );
        int goalBuffer = 200;   //set the goal acheived if plus or minus 200 from the recomended amount
        goalList.add(new CalorieGoal(recCalAmnt-goalBuffer, recCalAmnt+goalBuffer, GoalType.QUANTITY));
    }

}
