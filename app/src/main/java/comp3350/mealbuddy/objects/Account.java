package comp3350.mealbuddy.objects;

import java.util.ArrayList;

import comp3350.mealbuddy.objects.goals.Goal;

public class Account {

    public UserInfo user;
    private ArrayList<Goal> goals;
    private ArrayList<Day> daysTracked;
    public Account(UserInfo user) {
        if (user == null) {
            throw new IllegalArgumentException("UserInfo can not be null");
        }
        this.user = user;
        daysTracked = new ArrayList<Day>();
        goals = new ArrayList<Goal>();
    }

    public void addGoal(Goal g) {
        if(!goals.contains(g))
            goals.add(g);
    }

    public void removeGoal(Goal g){
        goals.remove(g);
    }

    public void addDay(Day d) {
        if(!daysTracked.contains(d))
            daysTracked.add(d);
    }

    public void addDay(int day){
        addDay(new Day(day));
    }

    public void removeDay(Day d){
        daysTracked.remove(d);
    }

    public Day getDay(int day) {
        for (Day d : daysTracked){
            if (d.dayOfYear == day)
                return d;
        }
        //create a day in the daysTracked list if we request it so the timeline doesnt crash on unrecorded days
        Day newDay = new Day(day);
        daysTracked.add(newDay);
        return newDay;
    }
}
