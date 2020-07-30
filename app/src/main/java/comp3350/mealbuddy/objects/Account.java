/****************************************
 * Account
 * Used for each user to store their own information (days tracked, goals, info, etc.)
 ****************************************/

package comp3350.mealbuddy.objects;

import java.util.ArrayList;
import java.util.List;

import comp3350.mealbuddy.objects.goals.Goal;

public class Account {

    public UserInfo user;
    private ArrayList<Goal> goals;
    private ArrayList<Day> daysTracked;

    /*
     * Constructor
     * Initializes the values for the account
     * Parameters:
     *     @param user - the user
     */
    public Account(UserInfo user) {
        if (user == null) {
            throw new IllegalArgumentException("UserInfo can not be null");
        }
        this.user = user;
        daysTracked = new ArrayList<>();
        goals = new ArrayList<>();
    }

    /*
     * Copy Constructor
     * Initializes the values for the account, from an existing account
     * Parameters:
     *     @param original - the account to copy.
     */
    public Account(Account original) {
        this.user = new UserInfo(original.user);
        goals = new ArrayList<>();
        for (Goal g : original.goals) {
            this.goals.add(Goal.copyGoal(g));
        }
        daysTracked = new ArrayList<>();
        for (Day d : original.daysTracked) {
            this.daysTracked.add(new Day(d));
        }
    }

    /*
     * AddGoal
     * Adds a goal to the users list, only adds unique goals.
     * Parameters:
     *     @param g - goal being added
     */
    public void addGoal(Goal g) {
        if (!goals.contains(g))
            goals.add(g);
    }

    /*
     * containsGoal
     * Check if the goal list contains a goal
     * Parameters:
     *     @param g - The goal to check for
     * Return:
     *     A boolean stating if the goal list contains the goal.
     */
    public boolean containsGoal(Goal g) {
        return goals.contains(g);
    }

    /*
     * getGoalSize
     * Get the size of the goal list
     * Return:
     *     The size of the goal list.
     */
    public int getGoalSize() {
        return goals.size();
    }

    /*
     * removeGoal
     * removes goal from the list, if it exists.
     * Parameters:
     *     @param g - remove this goal
     */
    public void removeGoal(Goal g) {
        int index = goals.indexOf(g);
        if (index != -1)
            goals.remove(g);
    }

    /*
     * addDay
     * adds a day to the accounts tracked days.
     * Parameters:
     *     @param d - the day being added. (DAY OBJECT)
     */
    public void addDay(Day d) {
        if (!daysTracked.contains(d))
            daysTracked.add(d);
    }

    /*
     * addDay
     * adds a day to the accounts tracked days.
     * Parameters:
     *     @param d - the day being added. (takes an integer day, representing dayOfYear [1-365])
     */
    public void addDay(int day) {
        addDay(new Day(day));
    }

    /*
     * removeDay
     * removes day from the tracked list, if it exists.
     * Parameters:
     *     @param d - remove this day
     */
    public void removeDay(Day d) {
        int index = daysTracked.indexOf(d);
        if (index != -1)
            daysTracked.remove(d);
    }

    /*
     * getDay
     * gets a day from the users tracked list, if the day doesn't exist it creates a new day from the ID.
     * Parameters:
     *     @param day : an integer representing the day you want, 1-365
     * Return:
     *     returns the day if found, else creates a new day on the id.
     */
    public Day getDay(int day) {
        if (day < 1 || day > 365)
            throw new IllegalArgumentException("Invalid day passed.");

        for (Day d : daysTracked) {
            if (d.dayOfYear == day)
                return d;
        }
        //create a day in the daysTracked list if we request it so the timeline doesnt crash on unrecorded days
        Day newDay = new Day(day);
        daysTracked.add(newDay);
        return newDay;
    }

    /*
     * getDaysTracked
     * Returns all the days tracked for a user.
     */
    public List<Day> getDaysTracked() {
        return daysTracked;
    }

    /*
     * isDayTracked
     * Check if a day is being tracked by the user.
     */
    public boolean isDayTracked(int dayOfYear) {
        boolean result = false;
        for (Day d : daysTracked) {
            if (d.dayOfYear == dayOfYear) {
                result = true;
            }
        }
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return user.equals(account.user);
    }

}
