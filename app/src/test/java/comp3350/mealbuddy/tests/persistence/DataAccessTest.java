package comp3350.mealbuddy.tests.persistence;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
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

import static comp3350.mealbuddy.objects.consumables.Edible.Macros.Fat;
import static comp3350.mealbuddy.objects.consumables.Edible.Micros.Zinc;

public class DataAccessTest {

    private static DataAccess database = Services.openDAS(Main.DATABASE_NAME);  //HQSQLDB
    //private static DataAccess database = Services.createDataAccess(new DataAccessStub("StubDB"));         //STUBDB

    public static Food durian;
    public static Food quinoa;
    public static Food bacon;
    public static Food egg;
    public static Food milk;
    public static Food cheerios;
    public static Meal nestedMeal;
    public static Meal cereal;
    public static Account account;
    public static final String ACCOUNT_USERNAME = "TESTMuskyBoi";
    public static final String DURIAN_NAME = "TESTDurian";
    public static final String CHEERIOS_NAME = "TESTCheerios";
    public static final String QUINOA_NAME = "TESTQuinoa";
    public static final String BACON_NAME = "TESTBacon";
    public static final String EGG_NAME = "TESTEgg";
    public static final String MILK_NAME = "TESTMilk";
    public static final String NESTED_MEAL_NAME = "TESTNestedMeal";
    public static final String CEREAL_NAME = "TESTCereal";
    public static final String KETO_LABEL = "TESTKeto";
    public static final String VEGAN_LABEL = "TESTVegan";

    @Before
    public void init() {
        clean();
        database.addLabel(KETO_LABEL);
        database.addLabel(VEGAN_LABEL);
        nestedMeal = makeNestedMeal();
        durian = new Food(DURIAN_NAME);
        durian.labels.add(KETO_LABEL);
        durian.labels.add(VEGAN_LABEL);
        database.addEdible(durian);
        quinoa = new Food(QUINOA_NAME);
        database.addEdible(quinoa);
        account = new Account(new UserInfo("Elon Musk", ACCOUNT_USERNAME, "T3sla", 280.0, 170.5, UserInfo.ActivityLevel.LOW, UserInfo.Sex.MALE, 40));
        database.addAccount(account);
    }
    @Test
    public void addRemoveUpdateAccount() {
        //accont added in before method
        Assert.assertEquals(account.user.username, database.getAccount(ACCOUNT_USERNAME).user.username);
        Account updatedAccount = new Account(new UserInfo("John Cena", "uCantCMe", "alwaysblue", 100.0, 180.0, UserInfo.ActivityLevel.LOW, UserInfo.Sex.MALE, 35));
        database.updateAccount(ACCOUNT_USERNAME, updatedAccount);
        Assert.assertEquals(updatedAccount.user.username, database.getAccount("uCantCMe").user.username);
        database.removeAccount("uCantCMe");
        Assert.assertNull(database.getAccount("uCantCMe"));
    }
    @Test
    public void addRemoveUpdateFood() {
        // Arrange
        Food updatedFood = new Food("TESTTaco", new ArrayList<>(Arrays.asList("TESTKeto")));
        List<Edible> edibles;
        //Check if the food was added
        edibles = database.getEdibles();
        Assert.assertNotEquals(-1, edibles.indexOf(durian));
        //Check if the food was updated
        database.updateEdible(durian.name, updatedFood);
        edibles = database.getEdibles();
        Assert.assertNotEquals(-1, edibles.indexOf(updatedFood));
        //Check if the food was removed
        database.removeEdible(updatedFood.name);
        edibles = database.getEdibles();
        Assert.assertEquals(-1, edibles.indexOf(updatedFood));
        Assert.assertEquals(-1, edibles.indexOf(durian));
    }
    @Test
    public void getEdibles() {
        List<Food> addedFoods = database.getFoods();
        List<Meal> addedMeals = database.getMeals();
        List<Edible> addedEdibles = database.getEdibles();
        Assert.assertTrue(addedFoods.contains(durian));
        Assert.assertTrue(addedFoods.contains(quinoa));
        Assert.assertTrue(addedMeals.contains(nestedMeal));
        Assert.assertTrue(addedMeals.contains(cereal));
        Assert.assertTrue(addedEdibles.containsAll(addedFoods));
        Assert.assertTrue(addedEdibles.containsAll(addedMeals));
    }
    @Test
    public void labelTest() {
        String highProtein = "TESTHigh Protein";
        String lowProtein = "TESTLow Protein";
        database.addLabel(highProtein);
        List<String> labels = database.getLabels();
        Assert.assertTrue(labels.contains(highProtein));
        database.updateLabel(highProtein, lowProtein);
        labels = database.getLabels();
        Assert.assertTrue(labels.contains(lowProtein));
        Assert.assertFalse(labels.contains(highProtein));
        database.removeLabel(lowProtein);
        labels = database.getLabels();
        Assert.assertFalse(labels.contains(lowProtein));
    }
    @Test
    public void daysTest() {
        Assert.assertFalse(database.isDayTracked(ACCOUNT_USERNAME, 1));
        database.addDay(ACCOUNT_USERNAME, 1);
        database.addDay(ACCOUNT_USERNAME, 2);
        Assert.assertTrue(database.isDayTracked(ACCOUNT_USERNAME, 1));
        Day newDay = database.getDay(ACCOUNT_USERNAME, 1);
        Assert.assertEquals(1, newDay.dayOfYear);
        List<Day> days = database.getDays(ACCOUNT_USERNAME);
        Assert.assertEquals(2, days.size());
        newDay = new Day(1);
        CalorieGoal calorieGoal = new CalorieGoal(1, 100);
        MicroGoal microGoal = new MicroGoal(1, 100, Edible.Micros.Zinc);
        LabelGoal labelGoal = new LabelGoal(1, 100, Goal.GoalType.RATIO, KETO_LABEL);
        MacroGoal macroGoal = new MacroGoal(1, 100, Goal.GoalType.QUANTITY, Edible.Macros.Alcohol);
        newDay.goals.add(labelGoal);
        newDay.goals.add(calorieGoal);
        newDay.goals.add(microGoal);
        newDay.goals.add(macroGoal);
        newDay.exercises.add(new Exercise("Running", 30, Exercise.Intensity.High));
        newDay.breakfast.add(nestedMeal);
        newDay.snack.add(durian);
        newDay.snack.add(quinoa);
        database.updateDay(ACCOUNT_USERNAME, newDay);
        Day updatedDay = database.getDay(ACCOUNT_USERNAME, 1);
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
        Meal updatedNestedMeal = (Meal) updatedBreakfast.getEdibleIntPair(nestedMeal).edible;
        Assert.assertTrue(updatedNestedMeal.containsEdible(EGG_NAME));
        Assert.assertTrue(updatedNestedMeal.containsEdible(BACON_NAME));
        Assert.assertTrue(updatedNestedMeal.containsEdible(CEREAL_NAME));
        Assert.assertEquals(2, updatedNestedMeal.getQuantity(CEREAL_NAME));
        Meal updatedCereal = (Meal) nestedMeal.getEdibleIntPair(cereal).edible;
        Assert.assertTrue(updatedCereal.containsEdible(MILK_NAME));
        Assert.assertTrue(updatedCereal.containsEdible(CHEERIOS_NAME));
    }
    @After
    public void clean() {
        database.removeEdible(NESTED_MEAL_NAME);
        database.removeEdible(BACON_NAME);
        database.removeEdible(CEREAL_NAME);
        database.removeEdible(EGG_NAME);
        database.removeEdible(MILK_NAME);
        database.removeEdible(CHEERIOS_NAME);
        database.removeEdible(QUINOA_NAME);
        database.removeEdible(DURIAN_NAME);
        database.removeLabel(KETO_LABEL);
        database.removeLabel(VEGAN_LABEL);
        database.removeAccount(ACCOUNT_USERNAME);
    }
    private Meal makeNestedMeal() {
        egg = new Food(EGG_NAME);
        egg.updateMacro(Fat, 10);
        egg.updateMicro(Zinc, 10);
        database.addEdible(egg);
        bacon = new Food(BACON_NAME);
        bacon.updateMacro(Fat, 10);
        bacon.updateMicro(Zinc, 10);
        database.addEdible(bacon);
        cereal = makeCerealMeal();
        nestedMeal = new Meal(NESTED_MEAL_NAME);
        nestedMeal.add(egg, 1);
        nestedMeal.add(bacon, 2);
        nestedMeal.add(cereal, 2);
        database.addEdible(nestedMeal);
        return nestedMeal;
    }
    private Meal makeCerealMeal() {
        milk = new Food(MILK_NAME);
        milk.updateMacro(Fat, 10);
        milk.updateMicro(Zinc, 10);
        database.addEdible(milk);
        cheerios = new Food(CHEERIOS_NAME);
        cheerios.updateMacro(Fat, 10);
        cheerios.updateMicro(Zinc, 10);
        database.addEdible(cheerios);
        cereal = new Meal(CEREAL_NAME);
        cereal.add(milk, 2);
        cereal.add(cheerios, 1);
        database.addEdible(cereal);
        return cereal;
    }
}