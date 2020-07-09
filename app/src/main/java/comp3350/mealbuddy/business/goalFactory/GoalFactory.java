/****************************************
 * GoalFactory
 * Abstract Class that creates and maintains the list of goals for a user.
 ****************************************/
package comp3350.mealbuddy.business.goalFactory;

import java.util.ArrayList;
import java.util.List;

import comp3350.mealbuddy.objects.goals.CalorieGoal;
import comp3350.mealbuddy.objects.goals.Goal;
import comp3350.mealbuddy.objects.UserInfo;


public abstract class GoalFactory {

    protected UserInfo userInfo;

    /*
     * Constructor
     * Create a new goal factory for the given user.
     * Parameters:
     *     @param userInfo - The user we are creating a goal factory for
     */
    public GoalFactory(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    /*
     * makeGoals
     * Creates and returns new goals for the user.
     * Return:
     *     The list of goals.
     */
    public List<Goal> makeGoals(){
        validateData(userInfo);
        List<Goal> goalList = new ArrayList<>();
        addTotalCalorieGoal(userInfo, goalList);
        addMacroGoals(userInfo, goalList);
        addMicroGoals(userInfo, goalList);
        addLabelGoals(userInfo, goalList);
        return goalList;
    }

    /*
     * add_____Goals
     * Abstract methods for creating new goals and adding them to a the goal list.
     * Parameters:
     *     @param userInfo - The user
     *     @param goalList - The goal list to add the goals to.
     */
    protected abstract void addMacroGoals(UserInfo userInfo, List<Goal> goalList);
    protected abstract void addMicroGoals(UserInfo userInfo, List<Goal> goalList);
    protected abstract void addLabelGoals(UserInfo userInfo, List<Goal> goalList);

    /*
     * addTotalCalorieGoal
     * Create a new calorie goal based on the users recommended calories.
     * Parameters:
     *     @param userInfo - The user
     *     @param goalList - The goal list to add the goals to.
     */
    private void addTotalCalorieGoal(UserInfo userInfo, List<Goal> goalList){
        int recCalAmnt = RecommendedCalorieCalculator.getTotalRecommendedCalories(userInfo);
        int goalBuffer = 200;   //set the goal achieved if plus or minus 200 from the recommended amount
        goalList.add(new CalorieGoal(recCalAmnt-goalBuffer, recCalAmnt+goalBuffer));
    }

    /*
     * validateData
     * Make sure a user's data is valid.
     * Parameters:
     *     @param user - The user to validate
     */
    private void validateData(UserInfo user){
        if(user.activityLevel == null || user.sex == null){
            throw new IllegalArgumentException("Null value passed in");
        }
        if(user.height <= 0 || user.weight <= 0 || user.age <=0 ){
            throw new IllegalArgumentException("Height and weight and age must be greater than 0");
        }
    }

}
