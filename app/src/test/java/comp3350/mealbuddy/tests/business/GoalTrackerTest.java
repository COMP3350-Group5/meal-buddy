package comp3350.mealbuddy.tests.business;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import comp3350.mealbuddy.business.Calculator;
import comp3350.mealbuddy.business.GoalTracker;
import comp3350.mealbuddy.objects.Day;
import comp3350.mealbuddy.objects.consumables.Food;
import comp3350.mealbuddy.objects.goals.Goal;
import comp3350.mealbuddy.objects.goals.MacroGoal;

import static comp3350.mealbuddy.objects.consumables.Edible.Macros.Fat;
import static comp3350.mealbuddy.objects.consumables.Edible.Macros.Protein;
import static comp3350.mealbuddy.objects.consumables.Edible.Micros.Sodium;
import static comp3350.mealbuddy.objects.goals.Goal.GoalType.QUANTITY;
import static comp3350.mealbuddy.objects.goals.Goal.GoalType.RATIO;

public class GoalTrackerTest {

    private static Calculator calculator;

    @Before
    public void createDefaultDay() {
        int dayOfYear = 23;
        Day newDay = new Day(dayOfYear);

        Food bigMac = makeBigMac();
        newDay.lunch.add(bigMac);

        calculator = new Calculator(newDay);
    }

    @Test
    public void getPassedGoals_noGoals_returnEmptyList() {
        //arrange
        List<Goal> emptyList = new ArrayList<>();

        //act
        List<Goal> passedGoals = GoalTracker.getPassedGoals(calculator, emptyList);

        //assert
        Assert.assertTrue(passedGoals.isEmpty());
    }

    @Test
    public void getPassedGoals_nullGoalList_throwException() {
        //arrange
        List<Goal> nullList = null;
        //act
        try {
            List<Goal> passedGoals = GoalTracker.getPassedGoals(calculator, nullList);
            Assert.fail();
        } catch (NullPointerException npe) {
            //Nice
            //assert
            Assert.assertTrue(true);
        }

    }

    @Test
    public void getPassedGoals_nullCalculator_throwException() {
        //arrange
        List<Goal> goals = makeQuantityGoalList();
        Calculator nullCalculator = null;
        //act
        try {
            List<Goal> passedGoals = GoalTracker.getPassedGoals(nullCalculator, goals);
            Assert.fail();
        } catch (NullPointerException npe) {
            //Nice
            //assert
            Assert.assertTrue(true);
        }

    }

    @Test
    public void getPassedGoals_quantityGoals_onePassedGoal() {
        //arrange
        List<Goal> quantityGoals = makeQuantityGoalList();
        int expectedPasses = 1;
        int expectedLowerBound = 500;
        int expectedUpperBound = 1000;

        //act
        List<Goal> passedGoals = GoalTracker.getPassedGoals(calculator, quantityGoals);

        //assert
        Assert.assertEquals(expectedPasses, passedGoals.size());

        Goal passedGoal = passedGoals.get(0);
        Assert.assertEquals(expectedLowerBound, passedGoal.lowerBound);
        Assert.assertEquals(expectedUpperBound, passedGoal.upperBound);
    }

    @Test
    public void getPassedGoals_quantityGoals_boundaryPassedGoal() {
        //arrange
        List<Goal> quantityGoals = new ArrayList<>();
        quantityGoals.add(new MacroGoal(800, 900, QUANTITY, Fat));
        quantityGoals.add(new MacroGoal(900, 1000, QUANTITY, Fat));
        int expectedNumPassedGoals = 2;

        //act
        List<Goal> passedGoals = GoalTracker.getPassedGoals(calculator, quantityGoals);

        //assert
        Assert.assertEquals(expectedNumPassedGoals, passedGoals.size());
    }

    @Test
    public void getPassedGoals_ratioGoals_onePassedGoal() {
        //arrange
        List<Goal> ratioGoals = makeRatioGoalList();
        int expectedPasses = 1;
        int expectedLowerBound = 60;
        int expectedUpperBound = 80;

        //act
        List<Goal> passedGoals = GoalTracker.getPassedGoals(calculator, ratioGoals);

        //assert
        Assert.assertEquals(expectedPasses, passedGoals.size());

        Goal passedGoal = passedGoals.get(0);
        Assert.assertEquals(expectedLowerBound, passedGoal.lowerBound);
        Assert.assertEquals(expectedUpperBound, passedGoal.upperBound);
    }

    @Test
    public void getPassedGoals_ratioGoals_boundaryPassedGoal() {
        //arrange
        List<Goal> ratioGoals = new ArrayList<>();
        ratioGoals.add(new MacroGoal(60, 69, RATIO, Fat));
        ratioGoals.add(new MacroGoal(69, 85, RATIO, Fat));
        int expectedNumPassedGoals = 2;

        //act
        List<Goal> passedGoals = GoalTracker.getPassedGoals(calculator, ratioGoals);

        //assert
        Assert.assertEquals(expectedNumPassedGoals, passedGoals.size());
    }

    @Test
    public void getPassedGoals_quantityGoals_multipleMacroGoals() {
        //arrange
        List<Goal> quantityGoals = new ArrayList<>();
        quantityGoals.add(new MacroGoal(0, 900, QUANTITY, Fat));
        quantityGoals.add(new MacroGoal(0, 400, QUANTITY, Protein));
        int expectedNumPassedGoals = 2;

        //act
        List<Goal> passedGoals = GoalTracker.getPassedGoals(calculator, quantityGoals);

        //assert
        Assert.assertEquals(expectedNumPassedGoals, passedGoals.size());
    }

    private List<Goal> makeQuantityGoalList() {
        ArrayList<Goal> goals = new ArrayList<>();
        goals.add(new MacroGoal(0, 500, QUANTITY, Fat));
        goals.add(new MacroGoal(500, 1000, QUANTITY, Fat));
        goals.add(new MacroGoal(1000, 2000, QUANTITY, Fat));
        return goals;
    }

    private List<Goal> makeRatioGoalList() {
        ArrayList<Goal> goals = new ArrayList<>();
        goals.add(new MacroGoal(0, 25, RATIO, Fat));
        goals.add(new MacroGoal(60, 80, RATIO, Fat));
        goals.add(new MacroGoal(80, 100, RATIO, Fat));
        return goals;
    }

    private Food makeBigMac() {
        ArrayList<String> labels = new ArrayList<>();
        labels.add("Super Size Me");
        Food bigMac = new Food("Big Mac Meal", labels);
        bigMac.updateMacro(Fat, 100);
        bigMac.updateMacro(Protein, 100);
        bigMac.updateMicro(Sodium, 200);
        return bigMac;
    }
}
