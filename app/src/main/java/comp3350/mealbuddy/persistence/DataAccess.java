package comp3350.mealbuddy.persistence;

import java.util.List;

import comp3350.mealbuddy.objects.Account;
import comp3350.mealbuddy.objects.Day;
import comp3350.mealbuddy.objects.consumables.Edible;
import comp3350.mealbuddy.objects.consumables.Food;
import comp3350.mealbuddy.objects.consumables.Meal;

public interface DataAccess {

    void open(String string);

    void close();

    void addAccount(Account account);

    void updateAccount(String usernameToUpdate, Account account);

    void removeAccount(String userName);

    Account getAccount(String username);

    void addEdible(Edible edible);

    void updateEdible(String edibleToUpdate, Edible edible);

    void removeEdible(String name);

    List<Edible> getEdibles();

    List<Food> getFoods();

    List<Meal> getMeals();

    void addLabel(String label);

    void updateLabel(String oldLabel, String newLabel);

    void removeLabel(String label);

    List<String> getLabels();

    void addDay(String userName, int dayOfYear);

    Day getDay(String userName, int dayOfYear);

    List<Day> getDays(String userName);

    void updateDay(String userName, Day day);

}
