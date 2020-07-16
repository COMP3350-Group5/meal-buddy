package comp3350.mealbuddy.persistence;

import comp3350.mealbuddy.objects.Account;
import comp3350.mealbuddy.objects.Day;
import comp3350.mealbuddy.objects.Exercise;
import comp3350.mealbuddy.objects.consumables.Edible;


public interface DataAccess {

    enum DBType {HSQLDB, Stub,}

    void open(String string);

    void close();

    void addAccount(Account acc);

    void updateAccount(Account a);

    void removeAccount(Account a);

    void addEdible(Edible acc);

    void updateEdible(Edible a);

    void removeEdible(Edible a);

    void addExercise(Exercise acc);

    void updateExercise(Exercise a);

    void removeExercise(Exercise a);

    Account validateLogin(String username, String password);

    Day getDay(Account a, int day);

    Account getAccount(String username);

}
