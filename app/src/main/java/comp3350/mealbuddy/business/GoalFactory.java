package comp3350.mealbuddy.business;

import java.util.List;

import comp3350.mealbuddy.objects.Goal;



public abstract class GoalFactory {
    abstract List<Goal> makeGoals();
}
