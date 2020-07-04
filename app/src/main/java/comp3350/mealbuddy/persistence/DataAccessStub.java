package comp3350.mealbuddy.persistence;

import comp3350.mealbuddy.objects.*;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class DataAccessStub {
    private enum Databases {
        Edible,Account,Exercise,
    }
    public String name;

    //the "databases"
    private ArrayList<Edible> edibles;
    private ArrayList<Account> accounts;
    private ArrayList<Exercise> exercises;

    public DataAccessStub(String name){
        this.name = name;
    }

    public void createStub() {
        //initialize the arrays
        edibles = new ArrayList<>();
        accounts = new ArrayList<>();
        exercises = new ArrayList<>();

        //initialize the data
        initAccounts();
        initEdibles();
        initExercises();
    }

    private void initEdibles(){
        Food edible;
        Meal meal = new Meal("Burger", new ArrayList<String>());
        Object[][] foods = {
            {"Beef Burger", new ArrayList<String>()},
            {"Cheese", new ArrayList<String>(Arrays.asList("dairy", "vegetarian"))},
            {"Burger Bun", new ArrayList<String>(Arrays.asList("vegetarian", "vegan"))},
        };
        for (Object[] food : foods){
            edible = new Food(
                    (String)food[0],
                    (ArrayList)food[1]
            );
            edibles.add(edible);
            meal.updateFood(edible, 1);
        }
    }

    private void initAccounts(){
        Account acc;
        UserInfo userInfo;
        Object[][] users = {
            {"Ned Stark", "ned_stark123", "starksrule", 200, 195, UserInfo.ActivityLevel.MEDIUM, UserInfo.Sex.MALE},
            {"Jamie Lannister", "k1ngsl4yer", "alwayspaymydebts", 230, 200, UserInfo.ActivityLevel.HIGH, UserInfo.Sex.MALE},
            {"Daenerys Targaryen", "motherOfDragzz", "123123", 150, 160, UserInfo.ActivityLevel.MEDIUM, UserInfo.Sex.FEMALE},
            {"Catelyn Stark", "stoneheart", "909090", 154.4, 160, UserInfo.ActivityLevel.LOW, UserInfo.Sex.FEMALE},
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


}
