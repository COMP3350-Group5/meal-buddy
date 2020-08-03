/****************************************
 * DataAccessStub
 * Database stub
 ****************************************/
package comp3350.mealbuddy.tests.persistence;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import comp3350.mealbuddy.objects.Account;
import comp3350.mealbuddy.objects.Day;
import comp3350.mealbuddy.objects.UserInfo;
import comp3350.mealbuddy.objects.consumables.Edible;
import comp3350.mealbuddy.objects.consumables.Edible.Macros;
import comp3350.mealbuddy.objects.consumables.Edible.Micros;
import comp3350.mealbuddy.objects.consumables.Food;
import comp3350.mealbuddy.objects.consumables.Meal;
import comp3350.mealbuddy.objects.goals.Goal;
import comp3350.mealbuddy.objects.goals.LabelGoal;
import comp3350.mealbuddy.persistence.DataAccess;

public class DataAccessStub implements DataAccess {

    public String name;

    //the "databases"
    private ArrayList<Edible> edibles;
    private ArrayList<Account> accounts;
    private ArrayList<String> labels;


    /*
     * Constructor
     * Creates the database
     * Parameters:
     *     @param name - the name of the database
     */
    public DataAccessStub(String name) {
        this.name = name;
    }

    /*
     * open
     * Initialize the databases
     * Parameters:
     *     @param name - The name of the database
     */
    @Override
    public void open(String name) {
        //initialize the arrays
        edibles = new ArrayList<>();
        accounts = new ArrayList<>();
        labels = new ArrayList<>();

        //initialize the data
        //initDay();
        initEdibles();
        initAccounts();

        System.out.println("Opened Database " + name);
    }

    /*
     * initEdibles
     * initializes the edibles data set.
     */
    private void initEdibles() {
        Food edible;
        Object[][] foods = {
                {"Bacon", 100, 37, 0, 14, 0, 0, 1, 0, 1, 0, 0, 6, 48, 13, 751, 201, 4},
                {"Corn", 100, 1, 19, 3, 0, 1, 0, 1, 0, 7, 0, 2, 0, 37, 15, 270, 2},
                {"Ham", 100, 21, 2, 16, 0, 1, 2, 0, 1, 0, 0, 10, 69, 16, 1245, 311, 4},
                {"Spaghetti", 100, 1, 26, 5, 0, 1, 1, 0, 0, 0, 0, 30, 0, 62, 14, 58, 2},
                {"Milk", 100, 7, 5, 6, 0, 0, 1, 44, 1, 4, 0, 193, 0, 18, 44, 137, 0},
                {"Honey", 100, 0, 82, 0, 0, 0, 0, 0, 0, 1, 0, 6, 2, 2, 4, 52, 0},
                {"Yogurt", 100, 2, 7, 5, 0, 0, 1, 0, 1, 1, 0, 183, 15, 17, 70, 234, 0},
                {"Beans", 100, 5, 22, 6, 0, 2, 1, 0, 0, 1, 0, 61, 0, 0, 422, 358, 0},
                {"Steak", 100, 7, 0, 29, 0, 3, 5, 0, 3, 0, 0, 6, 0, 24, 349, 341, 5},
                {"Lobster", 100, 1, 0, 19, 0, 0, 4, 0, 1, 0, 1, 96, 81, 43, 486, 230, 2},
                {"White wine", 100, 0, 3, 0, 10, 0, 0, 0, 0, 0, 0, 9, 4, 10, 5, 71, 0},
                {"Spinach", 100, 0, 4, 3, 0, 3, 1, 1, 0, 28, 2, 99, 19, 79, 79, 558, 1},
                {"Orange", 100, 0, 10, 1, 0, 0, 0, 0, 0, 50, 0, 11, 6, 11, 1, 200, 0},
                {"Salmon", 100, 4, 0, 21, 0, 0, 0, 0, 4, 0, 0, 7, 95, 27, 75, 366, 8},
                {"Cabbage", 100, 0, 6, 1, 0, 1, 0, 0, 0, 37, 0, 40, 11, 12, 18, 170, 0},
                {"Cheese", 100, 28, 1, 21, 0, 1, 2, 0, 2, 0, 0, 184, 15, 20, 629, 152, 0},
                {"Pork", 100, 21, 0, 26, 0, 1, 3, 0, 1, 1, 0, 22, 88, 24, 73, 362, 4},
                {"Almonds", 100, 50, 22, 21, 0, 4, 3, 0, 0, 0, 26, 269, 52, 270, 1, 733, 4},
                {"Chicken breast", 100, 8, 2, 15, 0, 0, 1, 0, 0, 0, 0, 6, 0, 17, 883, 324, 7},
                {"Broccoli", 100, 0, 7, 3, 0, 1, 0, 0, 0, 89, 1, 47, 19, 21, 33, 316, 1},
                {"Shrimp", 100, 1, 0, 20, 0, 1, 1, 0, 0, 0, 0, 64, 0, 35, 119, 264, 0},
                {"Cucumber", 100, 0, 2, 1, 0, 0, 0, 0, 0, 3, 0, 14, 6, 12, 2, 136, 0},
                {"Sweet potato", 100, 0, 20, 2, 0, 1, 0, 7, 0, 3, 0, 14, 6, 12, 2, 136, 0},
                {"Banana", 100, 0, 23, 1, 0, 0, 0, 7, 0, 9, 0, 5, 10, 27, 1, 358, 1},
                {"Rice", 100, 1, 26, 3, 0, 1, 0, 0, 0, 0, 0, 3, 0, 0, 4, 86, 0},
        };
        for (Object[] food : foods) {
            edible = new Food(
                    (String) food[0],
                    new ArrayList<>());
            edible.weight = (int) food[2];
            edible.updateMacro(Macros.Fat, (int) food[3]);
            edible.updateMacro(Macros.Carbohydrates, (int) food[4]);
            edible.updateMacro(Macros.Protein, (int) food[5]);
            edible.updateMacro(Macros.Alcohol, (int) food[6]);
            edible.updateMicro(Micros.Iron, (int) food[7]);
            edible.updateMicro(Micros.Zinc, (int) food[8]);
            edible.updateMicro(Micros.VitaminA, (int) food[8]);
            edible.updateMicro(Micros.VitaminB12, (int) food[9]);
            edible.updateMicro(Micros.VitaminC, (int) food[10]);
            edible.updateMicro(Micros.VitaminE, (int) food[11]);
            edible.updateMicro(Micros.Calcium, (int) food[12]);
            edible.updateMicro(Micros.Choline, (int) food[13]);
            edible.updateMicro(Micros.Magnesium, (int) food[14]);
            edible.updateMicro(Micros.Sodium, (int) food[15]);
            edible.updateMicro(Micros.Potassium, (int) food[16]);
            edible.updateMicro(Micros.Niacin, (int) food[17]);

            edibles.add(edible);
        }

    }

    /*
     * initAccounts
     * initializes the accounts data set.
     */
    private void initAccounts() {
        Account acc;
        UserInfo userInfo;
        //format of a user:
        //Full Name | username | password | weight (in lbs) | height (in cm) | Activity Level | Sex | Age
        Object[][] users = {
                {"Add Min", "admin", "group5", 150.0, 169.0, UserInfo.ActivityLevel.HIGH, UserInfo.Sex.MALE, 42}
        };

        for (Object[] user : users) {
            userInfo = new UserInfo(
                    (String) user[0],
                    (String) user[1],
                    (String) user[2],
                    (double) user[3],
                    (double) user[4],
                    (UserInfo.ActivityLevel) user[5],
                    (UserInfo.Sex) user[6],
                    (int) user[7]
            );
            acc = new Account(userInfo);
            acc.addDay(new Day(1));
            accounts.add(acc);
        }
    }

    /*
     * close
     * Does nothing for the stub.
     */
    @Override
    public void close() {
        System.out.println("Closed database " + name);
    }

    /*
     * addAccount
     * inserts an account to the db
     * Parameters:
     *     @param account - the account to insert
     */
    @Override
    public String addAccount(Account account) {
        accounts.add(account);
        return "";
    }

    /*
     * updateUserInfo
     * Updates an account already present
     * in the db
     * Parameters:
     *     @param userNameToUpdate - the username of the account to update
     *     @param account - the UserInfo holding the new info to update
     *                      the old info to
     */
    @Override
    public String updateUserInfo(String userNameToUpdate, UserInfo userInfo) {
        removeAccount(userNameToUpdate);
        addAccount(new Account(userInfo));
        return "";
    }

    /*
     * removeAccount
     * removes an account from the db
     * Parameters:
     *     @param userName - the username of the account to remove
     */
    @Override
    public String removeAccount(String userName) {
        for (Account account : accounts) {
            if (account.user.username.equals(userName)) {
                accounts.remove(account);
                break;
            }
        }
        return "";
    }

    /*
     * getUserInfo
     * gets the account associated with the username.
     * returns null if not in db
     * Parameters:
     *     @param userName - username associated with the userInfo to get
     */
    @Override
    public UserInfo getUserInfo(String userName) {
        UserInfo infoToReturn = null;
        for (Account account : accounts) {
            if (account.user.username.equals(userName)) {
                infoToReturn = new UserInfo(account.user);
            }
        }
        return infoToReturn;
    }


    /*
     * getAccount
     * gets the account associated with the username.
     * returns null if not in db
     * Parameters:
     *     @param userName - username associated with the account to get
     */
    private Account getAccount(String userName) {
        Account result = null;
        for (Account account : accounts) {
            if (account.user.username.equals(userName)) {
                result = new Account(account);
            }
        }
        return result;
    }

    /*
     * addEdible
     * Adds an edible to the db
     * Parameters:
     *     @param edible - the edible to add
     */
    @Override
    public String addEdible(Edible edible) {
        edibles.add(edible);
        return "";
    }

    /*
     * updateEdible
     * Updates an edible in the db
     * Parameters:
     *     @param edible - the edible to update
     */
    @Override
    public String updateEdible(String edibleToUpdate, Edible edible) {
        removeEdible(edibleToUpdate);
        addEdible(edible);
        return "";
    }

    /*
     * removeEdible
     * removes the edible from the db
     * Parameters:
     *     @param name - the name of the edible to remove
     */
    @Override
    public String removeEdible(String name) {
        Iterator<Edible> iter = edibles.iterator();
        while (iter.hasNext()) {
            Edible edible = iter.next();
            if (edible.name.equals(name)) {
                iter.remove();
            }
        }
        return "";
    }

    /*
     * getEdibles
     * gets all the edibles in the db
     */
    @Override
    public List<Edible> getEdibles() {
        ArrayList<Edible> result = new ArrayList<>();
        for (Edible edible : edibles) {
            result.add(Edible.copyEdible(edible));
        }
        return result;
    }

    /*
     * getFoods
     * gets all the food in the db
     */
    @Override
    public List<Food> getFoods() {
        ArrayList<Food> result = new ArrayList<>();
        for (Edible edible : edibles) {
            if (edible instanceof Food) {
                result.add((Food) Edible.copyEdible(edible));
            }
        }
        return result;
    }

    /*
     * getMeals
     * gets all the meals in the db
     */
    @Override
    public List<Meal> getMeals() {
        ArrayList<Meal> result = new ArrayList<>();
        for (Edible edible : edibles) {
            if (edible instanceof Meal) {
                result.add((Meal) Edible.copyEdible(edible));
            }
        }
        return result;
    }

    /*
     * addLabel
     * adds the label to the db
     * Parameters:
     *     @param label - the name label to add
     */
    @Override
    public String addLabel(String label) {
        labels.add(label);
        return "";
    }

    /*
     * updateLabel
     * updates a label in the db
     * Parameters:
     *     @param oldLabel - the name of the label to update
     *     @param newLabel - the name to update to
     */
    @Override
    public String updateLabel(String oldLabel, String newLabel) {
        updateLabelGoals(oldLabel, newLabel);
        removeLabel(oldLabel);
        addLabel(newLabel);
        return "";
    }


    /*
     * updateLabelGoals
     * updates the label goals associated with the label
     * Parameters:
     *     @param oldLabel - the name of the label to update
     *     @param newLabel - the name to update to
     */
    private void updateLabelGoals(String oldLabel, String newLabel) {
        Iterator<Day> dayIterator;
        Day day;
        Goal labelGoalToChange = new LabelGoal(0, 0, Goal.GoalType.QUANTITY, oldLabel);
        Goal goal;
        for (Account account : accounts) {
            dayIterator = account.getDayIterator();
            while (dayIterator.hasNext()) {
                goal = dayIterator.next().getGoal(labelGoalToChange);
                if (goal != null)
                    goal.id = newLabel;
            }
        }
    }

    /*
     * removeLabel
     * removes a label from the db
     * Parameters:
     *     @param name - the name of the label to remove
     */
    @Override
    public String removeLabel(String label) {
        Iterator<String> iter = labels.iterator();
        while (iter.hasNext()) {
            String nextLabel = iter.next();
            if (nextLabel.equals(label)) {
                iter.remove();
            }
        }
        removeLabelGoals(label);
        return "";
    }

    /*
     * removeLabelGoals
     * updates the label goals associated with the label
     * Parameters:
     *     @param oldLabel - the name of the label to remove
     */
    private void removeLabelGoals(String labelToRemove) {
        Iterator<Day> dayIterator;
        Goal labelGoalToRemove = new LabelGoal(0, 0, Goal.GoalType.QUANTITY, labelToRemove);
        Day day;
        for (Account account : accounts) {
            dayIterator = account.getDayIterator();
            while (dayIterator.hasNext()) {
                day = dayIterator.next();
                if (day.containsGoal(labelGoalToRemove))
                    day.removeGoal(labelGoalToRemove);
            }
        }
    }

    /*
     * getLabels
     * gets all labels from the db
     */
    @Override
    public List<String> getLabels() {
        ArrayList<String> result = new ArrayList<>();
        result.addAll(labels);
        return result;
    }

    /*
     * addDay
     * adds a new day to the db. Will always be an empty day
     * Parameters:
     *     @param userName - the account to add the day under
     *     @param dayOfYear - the day of year to add the day to
     */
    @Override
    public String addDay(String userName, int dayOfYear) {
        Account account = getAccount(userName);
        account.addDay(new Day(dayOfYear));
        updateUserInfo(userName, account.user);
        return "";
    }

    /*
     * getDay
     * gets the day from the associated account username
     * Parameters:
     *     @param userName - the userName of the account
     *     @param dayOfYear - the day of the year to get
     */
    @Override
    public Day getDay(String userName, int dayOfYear) {
        Account account = getAccount(userName);
        return new Day(account.getDay(dayOfYear));
    }

    /*
     * getDay
     * gets all days from the associated account username
     * Parameters:
     *     @param userName - the userName of the account
     */
    public List<Day> getDays(String userName) {
        Account account = getAccount(userName);
        List<Day> days = account.getDaysTracked();
        List<Day> result = new ArrayList<>();
        for (Day d : days) {
            result.add(new Day(d));
        }
        return result;
    }

    /*
     * updateDay
     * updates a certain day in an account.  Updates all info associated with the day
     * including exercise, meals, and goals.  Note the dayOfYear in a day
     * can not be updated.
     * Parameters:
     *     @param userName - the userName of the account
     *     @param dayOfYear - the day to update
     */
    public String updateDay(String userName, Day day) {
        Account account = getAccount(userName);
        int dayToUpdate = day.dayOfYear;
        account.removeDay(account.getDay(dayToUpdate));
        account.addDay(day);
        updateUserInfo(userName, account.user);
        return "";
    }

    /*
     * isDayTracked
     * Check if a day is being tracked by the user
     * Parameters:
     *     @param userName - the userName of the account
     *     @param dayOfYear - the day of the year to check
     */
    public boolean isDayTracked(String userName, int dayOfYear) {
        boolean isTracked = false;
        Account account = getAccount(userName);
        List<Day> daysTracked = account.getDaysTracked();
        for (Day d : daysTracked) {
            if (d.dayOfYear == dayOfYear) {
                isTracked = true;
                break;
            }
        }
        return isTracked;
    }

}
