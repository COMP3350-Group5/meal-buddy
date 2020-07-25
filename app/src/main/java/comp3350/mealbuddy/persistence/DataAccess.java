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

    String addAccount(Account account);

    String updateAccount(String usernameToUpdate, Account account);

    String removeAccount(String userName);

    Account getAccount(String username);

    String addEdible(Edible edible);

    String updateEdible(String edibleToUpdate, Edible edible);

    String removeEdible(String name);

    List<Edible> getEdibles();

    List<Food> getFoods();

    List<Meal> getMeals();

    String addLabel(String label);

    String updateLabel(String oldLabel, String newLabel);

    String removeLabel(String label);

    List<String> getLabels();

    String addDay(String userName, int dayOfYear);

    List<Day> getDays(String userName);

    Day getDay(String account, int dayOfYear);

    String updateDay(String userName, Day day);

}
