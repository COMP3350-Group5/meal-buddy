package comp3350.mealbuddy.objects;

import java.util.ArrayList;

public class Account {

    public UserInfo user;
    private ArrayList<Goal> goals;
    public Account(UserInfo user) {
        this.user = user;
    }

    public void addGoal(Goal g) {
        if(!goals.contains(g))
            goals.add(g);
    }

    public void removeGoal(Goal g){
        if(goals.contains(g))
            goals.remove(g);
    }
}
