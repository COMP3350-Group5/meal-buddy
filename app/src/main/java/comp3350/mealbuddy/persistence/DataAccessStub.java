/****************************************
 * DataAccessStub
 * Database stub
 ****************************************/
package comp3350.mealbuddy.persistence;

import comp3350.mealbuddy.objects.Account;
import comp3350.mealbuddy.objects.Day;
import comp3350.mealbuddy.objects.Exercise.Intensity;
import comp3350.mealbuddy.objects.consumables.Edible;
import comp3350.mealbuddy.objects.consumables.Food;
import comp3350.mealbuddy.objects.consumables.Meal;
import comp3350.mealbuddy.objects.Exercise;
import comp3350.mealbuddy.objects.UserInfo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class DataAccessStub implements DataAccess  {

    public String name;

    //the "databases"
    private ArrayList<Edible> edibles;
    private ArrayList<Account> accounts;
    private ArrayList<Exercise> exercises;
    private ArrayList<String> labels;

    private Day day;

    /*
     * Constructor
     * Initializes the values for the database
     * Parameters:
     *     @param name - the name of the database
     */
    public DataAccessStub(String name){
        this.name = name;
    }

    public void open(String string) {
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
                "Vegetarian", "Vegan", "Fat Free", "Gluten Free", "Low Sugar"
        };

        for (String l : labelList) {
            labels.add(l);
        }
    }

    public void close() {
        System.out.println("Closed database " + name);
    }

    public String addAccount(Account account) {
        accounts.add(account);
        return "";
    }

    public String updateAccount(String usernameToUpdate, Account account) {
        removeAccount(usernameToUpdate);
        addAccount(account);
        return "";
    }

    public String removeAccount(String userName) {
        for (Account account : accounts) {
            if (account.user.username == userName) {
                accounts.remove(account);
                break;
            }
        }
        return "";
    }

    public Account getAccount(String userName) {
        Account result = null;
        for (Account account : accounts) {
            if (account.user.username.equals(userName)) {
                result = account;
            }
        }
        return result;
    }

    public Account validateLogin(String username, String password) {
        Account result = null;
        for (Account account : accounts) {
            if (account.user.username.equals(username) && account.user.password.equals(password)) {
                result = account;
            }
        }
        return result;
    }

    public String addEdible(Edible edible) {
        edibles.add(edible);
        return "";
    }

    public String updateEdible(String edibleToUpdate, Edible edible) {
        removeEdible(edibleToUpdate);
        addEdible(edible);
        return "";
    }

    public String removeEdible(String name) {
        for (Edible edible : edibles) {
            if (edible.name.equals(name)) {
                edibles.remove(edible);
            }
        }
        return "";
    }

    public Edible getEdible(String name) {
        Edible result = null;
        for (Edible edible : edibles) {
            if (edible.name.equals(name)) {
                result = edible;
            }
        }
        return result;
    }

    public List<Edible> getEdibles() {
        ArrayList<Edible> result = new ArrayList<>();
        for (Edible edible : edibles) {
            if (edible instanceof Food) {
                result.add(new Food((Food)edible));
            }
            else {
                result.add(new Meal((Meal)edible));
            }
        }
        return result;
    }

    public List<Food> getFoods() {
        ArrayList<Food> result = new ArrayList<>();
        for (Edible edible : edibles) {
            if (edible instanceof Food) {
                result.add(new Food((Food)edible));
            }
        }
        return result;
    }

    public List<Meal> getMeals() {
        ArrayList<Meal> result = new ArrayList<>();
        for (Edible edible : edibles) {
            if (edible instanceof Meal) {
                result.add(new Meal((Meal)edible));
            }
        }
        return result;
    }

    public String addLabel(String label) {
        labels.add(label);
        return "";
    }

    public String updateLabel(String oldLabel, String newLabel) {
        removeLabel(oldLabel);
        addLabel(newLabel);
        return "";
    }

    public String removeLabel(String label) {
        for (String l : labels) {
            if (l.equals(label)) {
                labels.remove(l);
            }
        }
        return "";
    }

    public List<String> getLabels() {
        ArrayList<String> result = new ArrayList<>();
        for (String l : labels) {
                result.add(l);
        }
        return result;
    }

    public String addDay(String userName, int dayOfYear) {
        Account account = getAccount(userName);
        account.addDay(new Day(dayOfYear));
        return "";
    }

    public String removeDay(String userName, int dayOfYear) {
        Account account = getAccount(userName);
        Day day = account.getDay(dayOfYear);
        account.removeDay(day);
        return "";
    }

    public Day getDay(String userName, int dayOfYear) {
        Account account = getAccount(userName);
        return account.getDay(dayOfYear);
    }

    public List<Day> getDays(String userName) {
        Account account = getAccount(userName);
        List<Day> days = account.getDaysTracked();
        List<Day> result = new ArrayList<>();
        for (Day d : days) {
            result.add(new Day(d));
        }
        return result;
    }

    public String updateDay(String userName, Day day) {
        Account account = getAccount(userName);
        int dayToUpdate = day.dayOfYear;
        removeDay(userName, dayToUpdate);
        account.addDay(day);
        return "";
    }

}
