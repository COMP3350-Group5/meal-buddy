/****************************************
 * DataAccessStub
 * Database stub
 ****************************************/
package comp3350.mealbuddy.tests.persistence;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import comp3350.mealbuddy.objects.Account;
import comp3350.mealbuddy.objects.Day;
import comp3350.mealbuddy.objects.Exercise;
import comp3350.mealbuddy.objects.Exercise.Intensity;
import comp3350.mealbuddy.objects.UserInfo;
import comp3350.mealbuddy.objects.consumables.Edible;
import comp3350.mealbuddy.objects.consumables.Food;
import comp3350.mealbuddy.objects.consumables.Meal;
import comp3350.mealbuddy.persistence.DataAccess;

public class DataAccessStub implements DataAccess {

    public String name;

    //the "databases"
    private ArrayList<Edible> edibles;
    private ArrayList<Account> accounts;
    private ArrayList<Exercise> exercises;
    private ArrayList<String> labels;

    private Day day;

    /*
     * Constructor
     * Creates the database
     * Parameters:
     *     @param name - the name of the database
     */
    public DataAccessStub(String name){
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
        exercises = new ArrayList<>();
        labels = new ArrayList<>();

        //initialize the data
        initDay();
        initEdibles();
        initAccounts();
        initExercises();
        initLabels();
        System.out.println("Opened Database " + name);
    }

    /*
     * initEdibles
     * initializes the edibles data set.
     */
    private void initEdibles(){
        Food edible;
        Meal meal = new Meal("Burger", new ArrayList<>());
        Object[][] foods = {
                {"Beef Patty", new ArrayList<String>()},
                {"Cheese", new ArrayList<>(Arrays.asList("dairy", "vegetarian"))},
                {"Burger Bun", new ArrayList<>(Arrays.asList("vegetarian", "vegan"))},
        };
        for (Object[] food : foods){
            edible = new Food(
                    (String)food[0],
                    (ArrayList)food[1]
            );
            edibles.add(edible);
            meal.add(edible, 1);
        }
        edibles.add(meal);
    }

    /*
     * initDay
     * initializes a day
     */
    private void initDay() {
        Calendar calendar = Calendar.getInstance();
        day = new Day(calendar.get(Calendar.DAY_OF_YEAR));

        Object[][] breakfast = {
                {"Egg", new ArrayList<>(Arrays.asList("vegetarian", "protein")), 50, Edible.Macros.Protein, 45},
                {"Bagel", new ArrayList<>(Arrays.asList("vegetarian")), 100, Edible.Macros.Carbohydrates, 6},
        };

        Object[][] lunch = {
                {"Beef Patty", new ArrayList<>(Arrays.asList("protein")), 50, Edible.Macros.Protein, 5},
                {"Cheese", new ArrayList<>(Arrays.asList("dairy", "vegetarian")), 33, Edible.Macros.Fat, 4},
        };

        Object[][] dinner = {
                {"Chicken", new ArrayList<>(Arrays.asList("protein")), 23, Edible.Macros.Fat, 14},
                {"Rice", new ArrayList<>(Arrays.asList("dairy", "vegetarian")), 44, Edible.Macros.Fat, 10},
        };

        Object[][] snack = {
                {"Chocolate Bar", new ArrayList<>(Arrays.asList("bad foods")), 13, Edible.Macros.Fat, 33},
                {"Nutri-grain", new ArrayList<>(Arrays.asList("multi-grain")), 12, Edible.Macros.Fat, 5},
        };

        for (Object[] food : breakfast) {
            Food edible = new Food(
                    (String) food[0],
                    (ArrayList) food[1]
            );
            edible.setWeight((int)food[2]);
            edible.updateMacro((Edible.Macros) food[3], (int) food[4]);
            day.getMealTime(Day.MealTimeType.BREAKFAST).add(edible);
        }

        for (Object[] food : lunch) {
            Food edible = new Food(
                    (String) food[0],
                    (ArrayList) food[1]
            );
            edible.setWeight((int)food[2]);
            edible.updateMacro((Edible.Macros) food[3], (int) food[4]);
            day.getMealTime(Day.MealTimeType.LUNCH).add(edible);
        }

        for (Object[] food : dinner) {
            Food edible = new Food(
                    (String) food[0],
                    (ArrayList) food[1]
            );
            edible.setWeight((int)food[2]);
            edible.updateMacro((Edible.Macros) food[3], (int) food[4]);
            day.getMealTime(Day.MealTimeType.DINNER).add(edible);
        }

        for (Object[] food : snack) {
            Food edible = new Food(
                    (String) food[0],
                    (ArrayList) food[1]
            );
            edible.setWeight((int)food[2]);
            edible.updateMacro((Edible.Macros) food[3], (int) food[4]);
            day.getMealTime(Day.MealTimeType.SNACK).add(edible);
        }
    }

    /*
     * initAccounts
     * initializes the accounts data set.
     */
    private void initAccounts(){
        Account acc;
        UserInfo userInfo;
        //format of a user:
        //Full Name | username | password | weight (in lbs) | height (in cm) | Activity Level | Sex
        Object[][] users = {
                {"Ned Stark", "ned_stark123", "starksrule", 200.0, 195.0, UserInfo.ActivityLevel.MEDIUM, UserInfo.Sex.MALE, 42},
                {"Jamie Lannister", "k1ngsl4yer", "alwayspaymydebts", 230.0, 200.0, UserInfo.ActivityLevel.HIGH, UserInfo.Sex.MALE, 40},
                {"Daenerys Targaryen", "motherOfDragzz", "123123", 150.0, 160.0, UserInfo.ActivityLevel.MEDIUM, UserInfo.Sex.FEMALE, 13},
                {"Catelyn Stark", "stoneheart", "909090", 154.4, 160.0, UserInfo.ActivityLevel.LOW, UserInfo.Sex.FEMALE, 50},
                {"Richard Hendricks", "piedpiper", "alwaysblue", 100.0, 180.0, UserInfo.ActivityLevel.LOW, UserInfo.Sex.MALE, 35},
                {"Admin", "admin", "group5", 14.2, 400.3, UserInfo.ActivityLevel.LOW, UserInfo.Sex.MALE, 100}
        };

        for (Object[] user : users){
            userInfo = new UserInfo(
                    (String)user[0],
                    (String)user[1],
                    (String)user[2],
                    (double)user[3],
                    (double)user[4],
                    (UserInfo.ActivityLevel)user[5],
                    (UserInfo.Sex)user[6],
                    (int)user[7]
            );
            acc = new Account(userInfo);
            acc.addDay(day);
            accounts.add(acc);
        }
    }

    /*
     * initExercises
     * initializes the exercises data set.
     */
    private void initExercises(){
        String[] exerciseList = {
                "Outdoor Run", "Bench Press", "Push Ups", "Sit Ups"
        };

        for (String e : exerciseList){
            exercises.add(new Exercise(e, 20, Intensity.Medium));
        }
    }

    /*
     * initExercises
     * initializes the labels
     */
    private void initLabels() {
        String[] labelList = {
                "Vegetarian", "Vegan", "Fat Free", "Gluten Free", "Low Sugar", "Keto"
        };

        for (String l : labelList) {
            labels.add(l);
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
     * updateAccount
     * Updates an account already present
     * in the db
     * Parameters:
     *     @param userNameToUpdate - the username of the account to update
     *     @param account - the account holding the new info to update
     *                      the old info to
     */
    @Override
    public String updateAccount(String userNameToUpdate, Account account) {
        removeAccount(userNameToUpdate);
        addAccount(account);
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
     * getAccount
     * gets the account associated with the username.
     * returns null if not in db
     * Parameters:
     *     @param userName - username associated with the account to get
     */
    @Override
    public Account getAccount(String userName) {
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
                result.add((Food)Edible.copyEdible(edible));
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
                result.add((Meal)Edible.copyEdible(edible));
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
        removeLabel(oldLabel);
        addLabel(newLabel);
        return "";
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
        return "";
    }

    /*
     * getLabels
     * gets all labels from the db
     */
    @Override
    public List<String> getLabels() {
        ArrayList<String> result = new ArrayList<>();
        for (String l : labels) {
                result.add(l);
        }
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
        updateAccount(userName, account);
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
        updateAccount(userName, account);
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
            }
        }
        return isTracked;
    }

}
