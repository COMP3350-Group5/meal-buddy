package comp3350.mealbuddy.tests.persistence;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import comp3350.mealbuddy.application.Main;
import comp3350.mealbuddy.application.Services;
import comp3350.mealbuddy.objects.Account;
import comp3350.mealbuddy.objects.Day;
import comp3350.mealbuddy.objects.Exercise;
import comp3350.mealbuddy.objects.UserInfo;
import comp3350.mealbuddy.objects.consumables.Edible;
import comp3350.mealbuddy.objects.consumables.Food;
import comp3350.mealbuddy.objects.consumables.Meal;
import comp3350.mealbuddy.objects.goals.CalorieGoal;
import comp3350.mealbuddy.objects.goals.Goal;
import comp3350.mealbuddy.objects.goals.LabelGoal;
import comp3350.mealbuddy.objects.goals.MacroGoal;
import comp3350.mealbuddy.objects.goals.MicroGoal;
import comp3350.mealbuddy.persistence.DataAccess;
import comp3350.mealbuddy.persistence.DataAccessStub;

import static comp3350.mealbuddy.objects.consumables.Edible.Macros.Fat;
import static comp3350.mealbuddy.objects.consumables.Edible.Micros.Zinc;

public class DataAccessTest {

    //private static DataAccess database = Services.createDataAccess(Main.DATABASE_NAME);  //HQSQLDB
    private static DataAccess database = Services.createDataAccess(new DataAccessStub("StubDB"));         //STUBDB

    @Test
    public void removeAccount() {
        // Arrange
        Account removedAccount = database.getAccount("stoneheart");

        // Act
        database.removeAccount("stoneheart");

        //Assert
        Assert.assertNull(database.getAccount("stoneheart"));
        database.addAccount(removedAccount);
    }

    @Test
    public void addAccount() {
        Account newAccount = new Account(new UserInfo("Elon Musk", "MuskyBoi", "T3sla", 280.0, 170.5, UserInfo.ActivityLevel.LOW, UserInfo.Sex.MALE, 40));

        database.addAccount(newAccount);

        Assert.assertEquals(newAccount, database.getAccount("MuskyBoi"));
        database.removeAccount("MuskyBoi");
    }

    @Test
    public void updateAccount() {
        // Arrange
        Account newAccount = new Account(new UserInfo( "Richard Hendricks", "piedpiper", "alwaysblue", 100.0, 180.0, UserInfo.ActivityLevel.LOW, UserInfo.Sex.MALE, 35));
        Account oldAccount = database.getAccount("piedpiper");

        // Act
        database.updateAccount("piedpiper", newAccount);
        Account updatedAccount = database.getAccount("piedpiper");

        //Assert
        Assert.assertEquals(newAccount, updatedAccount);
        database.removeAccount("piedpiper");
        database.addAccount(oldAccount);
    }

    @Test
    public void addRemoveUpdateFood() {
        // Arrange
        Food newFood = new Food("Taco", new ArrayList<String>());
        Food updatedFood = new Food("Taco", new ArrayList<>(Arrays.asList("vegetarian")));
        List<Edible> edibles = null;

        //Check if the food was added
        database.addEdible(newFood);
        edibles = database.getEdibles();
        Assert.assertNotEquals(-1, edibles.indexOf(newFood));

        //Check if the food was updated
        database.updateEdible("Taco", updatedFood);
        edibles = database.getEdibles();
        Assert.assertNotEquals(-1, edibles.indexOf(updatedFood));

        //Check if the food was removed
        database.removeEdible("Taco");
        edibles = database.getEdibles();
        Assert.assertEquals(-1, edibles.indexOf(updatedFood));
    }

    @Test
    public void getEdibles() {
        Meal nestedMeal = makeNestedMeal();
        database.addEdible(nestedMeal);

        Food durian = new Food("Durian");
        database.addEdible(durian);

        Food quinoa = new Food("Quinoa");
        database.addEdible(quinoa);

        List<Food> addedFoods = database.getFoods();
        List<Meal> addedMeals = database.getMeals();

        Assert.assertFalse(addedFoods.contains(nestedMeal));
        Assert.assertTrue(addedFoods.contains(durian));
        Assert.assertTrue(addedFoods.contains(quinoa));

        Assert.assertTrue(addedMeals.contains(nestedMeal));
        Assert.assertFalse(addedMeals.contains(durian));
        Assert.assertFalse(addedMeals.contains(quinoa));
    }

    @Test
    public void labelTest() {
        database.addLabel("High Protein");

        List<String> labels = database.getLabels();
        Assert.assertTrue(labels.contains("High Protein"));

        database.updateLabel("High Protein", "Low Fat");
        labels = database.getLabels();
        Assert.assertTrue(labels.contains("Low Fat"));
        Assert.assertFalse(labels.contains("High Protein"));

        database.removeLabel("Low Fat");
        labels = database.getLabels();
        Assert.assertFalse(labels.contains("Low Fat"));

    }

    @Test
    public void daysTest() {
        Account newAccount = new Account(new UserInfo("Elon Musk", "MuskyBoi", "T3sla", 280.0, 170.5, UserInfo.ActivityLevel.LOW, UserInfo.Sex.MALE, 40));
        database.addAccount(newAccount);
        Assert.assertFalse(database.isDayTracked("MuskyBoi", 1));

        database.addDay("MuskyBoi", 1);
        database.addDay("MuskyBoi", 2);
        Assert.assertTrue(database.isDayTracked("MuskyBoi", 1));

        Day newDay = database.getDay("MuskyBoi", 1);
        Assert.assertEquals(1, newDay.dayOfYear);

        List<Day> days = database.getDays("MuskyBoi");
        Assert.assertEquals(2, days.size());

        newDay = new Day(1);
        LabelGoal labelGoal = new LabelGoal(1, 100, Goal.GoalType.RATIO, "Keto");
        newDay.goals.add(labelGoal);

        CalorieGoal calorieGoal = new CalorieGoal(1, 100);
        newDay.goals.add(calorieGoal);

        MicroGoal microGoal = new MicroGoal(1, 100, Edible.Micros.Zinc);
        newDay.goals.add(microGoal);

        MacroGoal macroGoal = new MacroGoal(1, 100, Goal.GoalType.QUANTITY, Edible.Macros.Alcohol);
        newDay.goals.add(macroGoal);

        newDay.exercises.add(new Exercise("Running", 30, Exercise.Intensity.High));

        Meal nestedMeal = makeNestedMeal();
        database.addEdible(nestedMeal);
        newDay.breakfast.add(nestedMeal);

        Food durian = new Food("Durian");
        database.addEdible(durian);
        newDay.snack.add(durian);

        Food quinoa = new Food("Quinoa");
        database.addEdible(quinoa);
        newDay.snack.add(quinoa);

        database.updateDay("MuskyBoi", newDay);
        Day updatedDay = database.getDay("MuskyBoi", 1);

        Exercise updatedExercise = updatedDay.exercises.get(0);
        Assert.assertEquals("Running", updatedExercise.name);
        Assert.assertEquals(30.0, updatedExercise.duration, 0.1);
        Assert.assertEquals(Exercise.Intensity.High, updatedExercise.intensity);

        List<Goal> updatedGoals = updatedDay.goals;
        Assert.assertTrue(updatedGoals.contains(calorieGoal));
        Assert.assertTrue(updatedGoals.contains(microGoal));
        Assert.assertTrue(updatedGoals.contains(macroGoal));
        Assert.assertTrue(updatedGoals.contains(labelGoal));

        Goal updatedMacroGoal = updatedGoals.get(updatedGoals.indexOf(macroGoal));
        Assert.assertEquals(macroGoal.lowerBound, updatedMacroGoal.lowerBound);
        Assert.assertEquals(macroGoal.upperBound, updatedMacroGoal.upperBound);
        Assert.assertEquals(macroGoal.goalType, updatedMacroGoal.goalType);
        Assert.assertEquals(macroGoal.id, updatedMacroGoal.id);

        Assert.assertTrue(updatedDay.lunch.isEmpty());
        Assert.assertTrue(updatedDay.dinner.isEmpty());

        Meal updatedSnack = updatedDay.snack;
        Assert.assertTrue(updatedSnack.containsEdible(durian));
        Assert.assertTrue(updatedSnack.containsEdible(quinoa));

        Meal updatedBreakfast = updatedDay.breakfast;
        Assert.assertTrue(updatedBreakfast.containsEdible(nestedMeal));

        Meal updatedNestedMeal = (Meal)updatedBreakfast.getEdibleIntPair(nestedMeal).edible;
        Assert.assertTrue(updatedNestedMeal.containsEdible("egg"));
        Assert.assertTrue(updatedNestedMeal.containsEdible("bacon"));
        Assert.assertTrue(updatedNestedMeal.containsEdible("cereal"));
        Assert.assertEquals(2, updatedNestedMeal.getQuantity("cereal"));

        database.removeEdible("durian");
        database.removeEdible("quinoa");
        database.removeEdible(nestedMeal.name);
        database.removeAccount("MuskyBoi");
    }

    private Meal makeNestedMeal() {
        Food egg = new Food("egg");
        egg.updateMacro(Fat, 10);
        egg.updateMicro(Zinc, 10);

        Food bacon = new Food("bacon");
        bacon.updateMacro(Fat, 10);
        bacon.updateMicro(Zinc, 10);

        Meal cereal = makeCerealMeal();

        Meal nestedMeal = new Meal("nestedMeal");
        nestedMeal.add(egg, 1);
        nestedMeal.add(bacon, 2);
        nestedMeal.add(cereal, 2);

        return nestedMeal;
    }

    private Meal makeCerealMeal() {
        Food milk = new Food("milk");
        milk.updateMacro(Fat, 10);
        milk.updateMicro(Zinc, 10);

        Food cheerios = new Food("cheerios");
        cheerios.updateMacro(Fat, 10);
        cheerios.updateMicro(Zinc, 10);

        Meal cereal = new Meal("cereal");
        cereal.add(milk, 2);
        cereal.add(cheerios, 1);

        return cereal;
    }
}

