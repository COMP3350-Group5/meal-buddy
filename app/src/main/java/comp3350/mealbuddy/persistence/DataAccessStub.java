package comp3350.mealbuddy.persistence;

import comp3350.mealbuddy.objects.Account;
import comp3350.mealbuddy.objects.Day;
import comp3350.mealbuddy.objects.Edible;
import comp3350.mealbuddy.objects.Exercise;
import comp3350.mealbuddy.objects.Food;
import comp3350.mealbuddy.objects.Meal;
import comp3350.mealbuddy.objects.UserInfo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

public class DataAccessStub {
    public enum Database_t {
        EDIBLES, ACCOUNTS, EXERCISES
    }
    public String name;

    //the "databases"
    private ArrayList<Edible> edibles;
    private ArrayList<Account> accounts;
    private ArrayList<Exercise> exercises;
    private Day day;

    public DataAccessStub(String name){
        this.name = name;
    }

    public void open() {
        //initialize the arrays
        edibles = new ArrayList<>();
        accounts = new ArrayList<>();
        exercises = new ArrayList<>();

        //initialize the data
        initEdibles();
        initDay();
        initAccounts();
        initExercises();
        System.out.println("Opened Database " + name);
    }

    public void close(){
        System.out.println("Closed database " + name);
    }

    private void initEdibles(){
        Food edible;
        Meal meal = new Meal("Burger", new ArrayList<String>());
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
            meal.updateFood(edible, 1);
        }
        edibles.add(meal);
    }

    private void initDay() {
        Calendar calendar = Calendar.getInstance();
        day = new Day(calendar.get(Calendar.DAY_OF_YEAR));

        Object[][] breakfast = {
            {"Egg", new ArrayList<>(Arrays.asList("vegetarian", "protein")), 50, Edible.Macros.Omega3, 45},
            {"Bagel", new ArrayList<>(Arrays.asList("vegetarian")), 100, Edible.Macros.Carbohydrates, 6},
        };

        Object[][] lunch = {
            {"Beef Patty", new ArrayList<>(Arrays.asList("protein")), 50, Edible.Macros.Protein, 5},
            {"Cheese", new ArrayList<>(Arrays.asList("dairy", "vegetarian")), 33, Edible.Macros.Fat, 4},
        };

        Object[][] dinner = {
            {"Chicken", new ArrayList<>(Arrays.asList("protein")), 23, Edible.Macros.Saturated, 14},
            {"Rice", new ArrayList<>(Arrays.asList("dairy", "vegetarian")), 44, Edible.Macros.Trans, 10},
        };

        Object[][] snack = {
            {"Chocolate Bar", new ArrayList<>(Arrays.asList("bad foods")), 13, Edible.Macros.Sugar, 33},
            {"Nutri-grain", new ArrayList<>(Arrays.asList("multi-grain")), 12, Edible.Macros.Omega6, 5},
        };
        //we have to do snack differently :( since its a micro

        for (Object[] food : breakfast) {
            Food edible = new Food(
                    (String) food[0],
                    (ArrayList) food[1]
            );
            edible.setWeight((int)food[2]);
            edible.addMacro((Edible.Macros) food[3], (int) food[4]);
            day.addFood(Day.MealTime_t.BREAKFAST, edible);
        }

        for (Object[] food : lunch) {
            Food edible = new Food(
                    (String) food[0],
                    (ArrayList) food[1]
            );
            edible.setWeight((int)food[2]);
            edible.addMacro((Edible.Macros) food[3], (int) food[4]);
            day.addFood(Day.MealTime_t.LUNCH, edible);
        }

        for (Object[] food : dinner) {
            Food edible = new Food(
                    (String) food[0],
                    (ArrayList) food[1]
            );
            edible.setWeight((int)food[2]);
            edible.addMacro((Edible.Macros) food[3], (int) food[4]);
            day.addFood(Day.MealTime_t.DINNER, edible);
        }

        for (Object[] food : snack) {
            Food edible = new Food(
                    (String) food[0],
                    (ArrayList) food[1]
            );
            edible.setWeight((int)food[2]);
            edible.addMacro((Edible.Macros) food[3], (int) food[4]);
            day.addFood(Day.MealTime_t.SNACK, edible);
        }

    }

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

    private void initExercises(){
        String[] exerciseList = {
            "Outdoor Run", "Bench Press", "Push Ups", "Sit Ups"
        };

        for (String e : exerciseList){
            exercises.add(new Exercise(e));
        }
    }

    public void addToDB(Database_t DT, Object o){
        switch (DT) {
            case ACCOUNTS:
                accounts.add((Account)o);
                break;
            case EDIBLES:
                edibles.add((Edible)o);
                break;
            case EXERCISES:
                exercises.add((Exercise)o);
                break;
        }
    }
    public void updateDB(Database_t DT, Object o){
        int index;
        switch (DT) {
            case ACCOUNTS:
                if( (index = accounts.indexOf((Account)o)) >= 0)
                    accounts.set(index, (Account)o);
                break;
            case EDIBLES:
                if( (index = edibles.indexOf((Edible) o)) >= 0)
                    edibles.set(index, (Edible)o);
                break;
            case EXERCISES:
                if( (index = exercises.indexOf((Exercise) o)) >= 0)
                    exercises.set(index, (Exercise)o);
                break;
        }
    }
    public void removeFromDB(Database_t DT, Object o){
        switch (DT) {
            case ACCOUNTS:
                accounts.remove((Account)o);
                break;
            case EDIBLES:
                edibles.remove((Edible)o);
                break;
            case EXERCISES:
                exercises.remove((Exercise)o);
                break;
        }
    }

    public Account validateLogin(String username, String password){
        for (Account a : accounts){
            if (a.user.username.equalsIgnoreCase(username) && a.user.password.equals(password))
                return a;
        }
        return null;
    }

    public Day getDay(Account a, int day){
        return a.getDay(day);
    }

    public Account getAccount(String username){
        for (Account a : accounts){
            if (a.user.username.equalsIgnoreCase(username))
                return a;
        }
        return null; //this should never happen as long as you validate the login first.
    }


}
