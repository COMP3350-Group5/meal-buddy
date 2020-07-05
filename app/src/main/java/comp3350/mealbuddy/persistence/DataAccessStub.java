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
        for (Edible edible:edibles) {
            day.addFood(Day.MealTime_t.BREAKFAST, edible);
        }
        for (Edible edible:edibles) {
            day.addFood(Day.MealTime_t.LUNCH, edible);
        }
        for (Edible edible:edibles) {
            day.addFood(Day.MealTime_t.DINNER, edible);
        }
        for (Edible edible:edibles) {
            day.addFood(Day.MealTime_t.SNACK, edible);
        }


    }

    private void initAccounts(){
        Account acc;
        UserInfo userInfo;
        //format of a user:
            //Full Name | username | password | weight (in lbs) | height (in cm) | Activity Level | Sex
        Object[][] users = {
            {"Ned Stark", "ned_stark123", "starksrule", 200.0, 195.0, UserInfo.ActivityLevel.MEDIUM, UserInfo.Sex.MALE},
            {"Jamie Lannister", "k1ngsl4yer", "alwayspaymydebts", 230.0, 200.0, UserInfo.ActivityLevel.HIGH, UserInfo.Sex.MALE},
            {"Daenerys Targaryen", "motherOfDragzz", "123123", 150.0, 160.0, UserInfo.ActivityLevel.MEDIUM, UserInfo.Sex.FEMALE},
            {"Catelyn Stark", "stoneheart", "909090", 154.4, 160.0, UserInfo.ActivityLevel.LOW, UserInfo.Sex.FEMALE},
            {"Richard Hendricks", "piedpiper", "alwaysblue", 100.0, 180.0, UserInfo.ActivityLevel.LOW, UserInfo.Sex.MALE},
            {"Admin", "admin", "group5", 14.2, 400.3, UserInfo.ActivityLevel.LOW, UserInfo.Sex.MALE}
        };

        for (Object[] user : users){
            userInfo = new UserInfo(
                    (String)user[0],
                    (String)user[1],
                    (String)user[2],
                    (double)user[3],
                    (double)user[4],
                    (UserInfo.ActivityLevel)user[5],
                    (UserInfo.Sex)user[6]
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
