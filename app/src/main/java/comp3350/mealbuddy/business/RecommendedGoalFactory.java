package comp3350.mealbuddy.business;

import java.util.ArrayList;
import java.util.List;

import comp3350.mealbuddy.objects.UserInfo;
import comp3350.mealbuddy.objects.consumables.Edible;
import comp3350.mealbuddy.objects.goals.CalorieGoal;
import comp3350.mealbuddy.objects.goals.Goal;
import comp3350.mealbuddy.objects.goals.MacroGoal;
import comp3350.mealbuddy.objects.goals.MicroGoal;

import static comp3350.mealbuddy.objects.consumables.Edible.Macros.Carbohydrates;
import static comp3350.mealbuddy.objects.consumables.Edible.Macros.Fat;
import static comp3350.mealbuddy.objects.consumables.Edible.Macros.Protein;


public class RecommendedGoalFactory {

    public static List<Goal> makeGoals(UserInfo userInfo) {
        validateDate(userInfo);
        List<Goal> goalList = new ArrayList<>();
        addTotalCalorieGoal(userInfo, goalList);
        addMacroGoals(userInfo, goalList);
        addMicroGoals(userInfo, goalList);
        return goalList;
    }

    private static void addTotalCalorieGoal(UserInfo userInfo, List<Goal> goalList) {
        int recCalAmnt = RecommendedCalorieCalculator.getTotalRecommendedCalories(userInfo);
        int goalBuffer = 200;   //set the goal acheived if plus or minus 200 from the recomended amount
        goalList.add(new CalorieGoal(recCalAmnt - goalBuffer, recCalAmnt + goalBuffer));
    }

    private static void validateDate(UserInfo user) {
        if (user.activityLevel == null || user.sex == null) {
            throw new IllegalArgumentException("Null value passed in");
        }
        if (user.height <= 0 || user.weight <= 0 || user.age <= 0) {
            throw new IllegalArgumentException("Height and weight and age must be greater than 0");
        }
    }

    protected static void addMacroGoals(UserInfo userInfo, List<Goal> goalList) {
        int MacroVariance = 5;
        int fatPercent = 25;
        int proteinPercent = 25;
        int carbPercent = 50;
        goalList.add(new MacroGoal(fatPercent - MacroVariance, fatPercent + MacroVariance, Goal.GoalType.RATIO, Fat));
        goalList.add(new MacroGoal(proteinPercent - MacroVariance, proteinPercent + MacroVariance, Goal.GoalType.RATIO, Protein));
        goalList.add(new MacroGoal(carbPercent - MacroVariance, carbPercent + MacroVariance, Goal.GoalType.RATIO, Carbohydrates));
    }

    protected static void addMicroGoals(UserInfo userInfo, List<Goal> goalList) {
        Object[][] goals = {
                {1, 4, Edible.Micros.VitaminA},
                {10, 50, Edible.Micros.VitaminB12},
                {10, 50, Edible.Micros.VitaminC},
                {15, 100, Edible.Micros.VitaminE},
                {8, 45, Edible.Micros.Iron},
                {10, 40, Edible.Micros.Zinc},
                {1000, 2000, Edible.Micros.Calcium},
                {500, 3500, Edible.Micros.Choline},
                {310, 3000, Edible.Micros.Magnesium},
                {1200, 2300, Edible.Micros.Sodium},
                {3500, 4700, Edible.Micros.Potassium},
                {10, 35, Edible.Micros.Niacin}
        };

        for (Object[] goal : goals) {
            goalList.add(new MicroGoal((int) goal[0], (int) goal[1], (Edible.Micros) goal[2]));
        }
    }

}