package comp3350.mealbuddy.objects;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

public class Account {

    public UserInfo user;
    private ArrayList<Goal> goals;
    private ArrayList<Day> daysTracked;
    public Account(UserInfo user) {
        this.user = user;
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

    public void addDay(LocalDate LD){
        addDay(new Day(LD));
    }

    public void removeDay(Day d){
        daysTracked.remove(d);
    }

    public Day getDay(LocalDate LD) {
        for (Day d : daysTracked){
            if (d.LD.equals(LD))
                return d;
        }
        //create a day in the daysTracked list if we request it so the timeline doesnt crash on unrecorded days
        Day newDay = new Day(LD);
        daysTracked.add(newDay);
        return newDay;
    }
}
