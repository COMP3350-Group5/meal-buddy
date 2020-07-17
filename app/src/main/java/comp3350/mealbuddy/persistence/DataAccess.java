package comp3350.mealbuddy.persistence;

import comp3350.mealbuddy.objects.Account;
import comp3350.mealbuddy.objects.Day;
import comp3350.mealbuddy.objects.Exercise;
import comp3350.mealbuddy.objects.consumables.Edible;


public interface DataAccess {

    enum DBType {HSQLDB, Stub,}

    void open(String string);

    void close();

    void addAccount(Account account);

    void updateAccount(Account account);

    void removeAccount(Account account);

    Account getAccount(String username);

    Account validateLogin(String username, String password);

    void addEdible(Edible edible);

    void updateEdible(Edible edible);

    void removeEdible(Edible edible);

    void getEdible(Edible edible);

    void addExercise(String userName, int dayOfYear, Exercise exercise);

    void updateExercise(String userName, int dayOfYear, Exercise exercise);

    void removeExercise(String userName, int dayOfYear, Exercise exercise);


    Day getDay(String userName, int dayOfYear);

    Day updateDay(String userName, int dayOfYear);


}
