package comp3350.mealbuddy.tests.integration;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import comp3350.mealbuddy.application.Main;
import comp3350.mealbuddy.application.Services;
import comp3350.mealbuddy.business.AccessAccount;
import comp3350.mealbuddy.business.AccessEdible;
import comp3350.mealbuddy.business.AccessLabel;
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

public class BusinessPersistenceSeamTest {


    public static final String ACCOUNT_USERNAME = "TESTMuskyBoi";
    public static final String UPDATED_ACC_NAME = "uCantCMe";
    public static final String DURIAN_NAME = "TESTDurian";
    public static final String TACO_NAME = "TESTTaco";
    public static final String CHEERIOS_NAME = "TESTCheerios";
    public static final String QUINOA_NAME = "TESTQuinoa";
    public static final String BACON_NAME = "TESTBacon";
    public static final String EGG_NAME = "TESTEgg";
    public static final String MILK_NAME = "TESTMilk";
    public static final String NESTED_MEAL_NAME = "TESTNestedMeal";
    public static final String CEREAL_NAME = "TESTCereal";
    public static final String KETO_LABEL = "TESTKeto";
    public static final String VEGAN_LABEL = "TESTVegan";
    public static final String LOW_PROTEIN = "TESTLow protein";
    public static final String HIGH_PROTEIN = "TESTHigh protein";
    public static Food durian;
    public static Food quinoa;
    public static Food bacon;
    public static Food egg;
    public static Food milk;
    public static Food cheerios;
    public static Meal nestedMeal;
    public static Meal cereal;
    public static Account account;
    //private static DataAccess database = Services.createDataAccess(new DataAccessStub("StubDB"));
    private static DataAccess database = Services.createDataAccess(Main.DATABASE_NAME);  //HQSQLDB

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

    @After
    public void clean() {
        database.removeEdible(NESTED_MEAL_NAME);
        database.removeEdible(BACON_NAME);
        database.removeEdible(TACO_NAME);
        database.removeEdible(CEREAL_NAME);
        database.removeEdible(EGG_NAME);
        database.removeEdible(MILK_NAME);
        database.removeEdible(CHEERIOS_NAME);
        database.removeEdible(QUINOA_NAME);
        database.removeEdible(DURIAN_NAME);
        database.removeLabel(KETO_LABEL);
        database.removeLabel(VEGAN_LABEL);
        database.removeLabel(HIGH_PROTEIN);
        database.removeLabel(LOW_PROTEIN);
        database.removeAccount(ACCOUNT_USERNAME);
        database.removeAccount(UPDATED_ACC_NAME);
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

    @Test
    public void testAccessAccount() {
        AccessAccount aa = new AccessAccount();
        //accont added in before method
        Assert.assertNull(aa.validateLogin("FAKE", "FAKE"));
        Assert.assertEquals(account, aa.validateLogin(ACCOUNT_USERNAME, account.user.password));
        Assert.assertEquals(account, aa.getAccount(ACCOUNT_USERNAME));


        Day newDay = aa.getDay(ACCOUNT_USERNAME, 1);
        Assert.assertEquals(1, newDay.dayOfYear);

        CalorieGoal calorieGoal = new CalorieGoal(1, 100);
        MicroGoal microGoal = new MicroGoal(1, 100, Edible.Micros.Zinc);
        LabelGoal labelGoal = new LabelGoal(1, 100, Goal.GoalType.RATIO, KETO_LABEL);
        MacroGoal macroGoal = new MacroGoal(1, 100, Goal.GoalType.QUANTITY, Edible.Macros.Alcohol);
        newDay.addGoal(labelGoal);
        newDay.addGoal(calorieGoal);
        newDay.addGoal(microGoal);
        newDay.addGoal(macroGoal);

        Exercise exercise = new Exercise("Running", 30, Exercise.Intensity.High);
        newDay.addExercise(exercise);

        newDay.breakfast.add(nestedMeal);
        newDay.snack.add(durian);
        newDay.snack.add(quinoa);

        aa.updateDay(ACCOUNT_USERNAME, newDay);
        Day updatedDay = aa.getDay(ACCOUNT_USERNAME, 1);
        Exercise updatedExercise = updatedDay.getExercise(exercise);
        Assert.assertEquals("Running", updatedExercise.name);
        Assert.assertEquals(30.0, updatedExercise.duration, 0.1);
        Assert.assertEquals(Exercise.Intensity.High, updatedExercise.intensity);

        Iterator<Goal> goalIterator = updatedDay.getGoals();
        List<Goal> updatedGoals = new ArrayList<>();
        while (goalIterator.hasNext()) {
            updatedGoals.add(goalIterator.next());
        }
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

        Account updatedAccount = new Account(new UserInfo("John Cena", UPDATED_ACC_NAME, "alwaysblue", 100.0, 180.0, UserInfo.ActivityLevel.LOW, UserInfo.Sex.MALE, 35));
        aa.updateAccount(ACCOUNT_USERNAME, updatedAccount);
        Assert.assertEquals(updatedAccount.user.username, aa.getAccount(UPDATED_ACC_NAME).user.username);
        aa.removeAccount(UPDATED_ACC_NAME);
        Assert.assertNull(aa.getAccount(UPDATED_ACC_NAME));
    }

    @Test
    public void testAccessEdible() {
        AccessEdible ae = new AccessEdible();

        List<Edible> addedEdibles = ae.getEdibles();
        Assert.assertTrue(addedEdibles.contains(durian));
        Assert.assertTrue(addedEdibles.contains(quinoa));
        Assert.assertTrue(addedEdibles.contains(nestedMeal));
        Assert.assertTrue(addedEdibles.contains(cereal));
        Assert.assertFalse(addedEdibles.contains("FAKE"));

        Assert.assertEquals(durian, ae.getEdible(durian.name));
        Assert.assertNull(ae.getEdible("FAKE"));

        Food updatedFood = new Food(TACO_NAME);
        updatedFood.labels.add(KETO_LABEL);
        List<Edible> edibles;
        //Check if the food was added
        edibles = ae.getEdibles();
        Assert.assertNotEquals(-1, edibles.indexOf(durian));
        //Check if the food was updated
        ae.updateEdible(durian.name, updatedFood);
        Assert.assertEquals(TACO_NAME, ae.getEdible(updatedFood.name).name);
        Assert.assertTrue(ae.getEdible(updatedFood.name).labels.contains(KETO_LABEL));
        Assert.assertFalse(ae.getEdible(updatedFood.name).labels.contains(VEGAN_LABEL));
        //Check if the food was removed
        ae.removeEdible(updatedFood.name);
        edibles = ae.getEdibles();
        Assert.assertEquals(-1, edibles.indexOf(updatedFood));
        Assert.assertEquals(-1, edibles.indexOf(durian));
    }

    @Test
    public void testAccessLabel() {
        AccessLabel al = new AccessLabel();

        al.addLabel(HIGH_PROTEIN);
        List<String> labels = al.getLabels();
        Assert.assertTrue(labels.contains(HIGH_PROTEIN));

        al.updateLabel(HIGH_PROTEIN, LOW_PROTEIN);
        labels = al.getLabels();
        Assert.assertTrue(labels.contains(LOW_PROTEIN));
        Assert.assertFalse(labels.contains(HIGH_PROTEIN));

        al.removeLabel(LOW_PROTEIN);
        Assert.assertFalse(al.getLabels().contains(LOW_PROTEIN));
    }

}
