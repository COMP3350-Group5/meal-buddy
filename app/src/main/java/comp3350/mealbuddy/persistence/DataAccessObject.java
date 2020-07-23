package comp3350.mealbuddy.persistence;

import java.sql.SQLWarning;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import comp3350.mealbuddy.objects.Account;
import comp3350.mealbuddy.objects.Day;
import comp3350.mealbuddy.objects.Exercise;
import comp3350.mealbuddy.objects.UserInfo;
import comp3350.mealbuddy.objects.consumables.Edible;
import comp3350.mealbuddy.objects.consumables.EdibleIntPair;
import comp3350.mealbuddy.objects.consumables.Food;
import comp3350.mealbuddy.objects.consumables.Meal;
import comp3350.mealbuddy.objects.goals.Goal;

import static comp3350.mealbuddy.objects.consumables.Edible.Macros.Fat;
import static comp3350.mealbuddy.objects.consumables.Edible.Macros.Protein;
import static comp3350.mealbuddy.objects.consumables.Edible.Macros.Carbohydrates;
import static comp3350.mealbuddy.objects.consumables.Edible.Macros.Alcohol;
import static comp3350.mealbuddy.objects.consumables.Edible.Micros.Iron;
import static comp3350.mealbuddy.objects.consumables.Edible.Micros.Zinc;
import static comp3350.mealbuddy.objects.consumables.Edible.Micros.VitaminA;
import static comp3350.mealbuddy.objects.consumables.Edible.Micros.VitaminB12;
import static comp3350.mealbuddy.objects.consumables.Edible.Micros.VitaminC;
import static comp3350.mealbuddy.objects.consumables.Edible.Micros.VitaminE;
import static comp3350.mealbuddy.objects.consumables.Edible.Micros.Calcium;
import static comp3350.mealbuddy.objects.consumables.Edible.Micros.Choline;
import static comp3350.mealbuddy.objects.consumables.Edible.Micros.Magnesium;
import static comp3350.mealbuddy.objects.consumables.Edible.Micros.Sodium;
import static comp3350.mealbuddy.objects.consumables.Edible.Micros.Potassium;
import static comp3350.mealbuddy.objects.consumables.Edible.Micros.Niacin;


public class DataAccessObject implements DataAccess {

    private Statement st1, st2, st3;
    private Connection c1;
    private ResultSet rs2, rs3, rs4, rs5;

    private String dbName;
    private String dbType;

    private ArrayList<Account> accounts;
    private ArrayList<Edible> edible;
    private ArrayList<Exercise> exercise;

    private String cmdString;
    private int updateCount;
    private String result;
    private static String EOF = "  ";

    public DataAccessObject(String dbName) {
        this.dbName = dbName;
    }

    @Override
    public void open(String dbPath) {
        String url;
        try {
            // Setup for HSQL
            dbType = "HSQL";
            Class.forName("org.hsqldb.jdbcDriver").newInstance();
            url = "jdbc:hsqldb:file:" + dbPath; // stored on disk mode
            System.out.println("**********************************");
            System.out.println(url);
            c1 = DriverManager.getConnection(url, "SA", "");
            st1 = c1.createStatement();
            st2 = c1.createStatement();
            st3 = c1.createStatement();
        } catch (Exception e) {
            processSQLError(e);
        }
        System.out.println("Opened" + dbType + "database " + dbPath);
    }

    @Override
    public void close() {
        try {    // commit all changes to the database
            cmdString = "shutdown compact";
            rs2 = st1.executeQuery(cmdString);
            c1.close();
        } catch (Exception e) {
            processSQLError(e);
        }
        System.out.println("Closed " + dbType + " database " + dbName);
    }

    public String checkWarning(Statement st, int updateCount) {
        String result;

        result = null;
        try {
            SQLWarning warning = st.getWarnings();
            if (warning != null) {
                result = warning.getMessage();
            }
        } catch (Exception e) {
            result = processSQLError(e);
        }
        if (updateCount != 1) {
            result = "Tuple not inserted correctly.";
        }
        return result;
    }

    public String processSQLError(Exception e) {
        String result = "*** SQL Error: " + e.getMessage();

        // Remember, this will NOT be seen by the user!
        e.printStackTrace();

        return result;
    }

    @Override
    public void addAccount(Account account) {
        UserInfo ui = account.user;
        String query = "INSERT INTO ACCOUNTS VALUES(" +
                "'" + ui.username + "'," +
                "'" + ui.password + "'," +
                "'" + ui.fullname + "'," +
                "'" + ui.weight + "'," +
                "'" + ui.height + "'," +
                "'" + ui.sex.ordinal() + "'," +
                "'" + ui.activityLevel.ordinal() + "'," +
                "'" + ui.age + "')";
    }

    @Override
    public void updateAccount(String usernameToUpdate, Account account) {
        UserInfo ui = account.user;
        String query = "UPDATE ACCOUNTS SET " +
                "USERNAME ='" + ui.username + "'," +
                "ACCOUNT_PASSWORD = '" + ui.password + "'," +
                "FULL_NAME ='" + ui.fullname + "'," +
                "USER_WEIGHT =" + ui.weight + "," +
                "HEIGHT =" + ui.height + "," +
                "SEX =" + ui.sex.ordinal() + "," +
                "ACTIVITY_LEVEL =" + ui.activityLevel.ordinal() + "," +
                "AGE =" + ui.age +
                " WHERE USERNAME = '" + usernameToUpdate + "';";
    }

    @Override
    public void removeAccount(String userName) {
        String query = "DELETE FROM ACCOUNTS WHERE USER_NAME = " + userName;
    }

    @Override
    public Account getAccount(String username) {
        Account account;
        try {
            cmdString = "SELECT * FROM ACCOUNTS WHERE USERNAME='" + username + "'";
            rs3 = st1.executeQuery(cmdString);
            // ResultSetMetaData md2 = rs3.getMetaData();
            while (rs3.next()) {
                System.out.println(rs3.getString("USERNAME"));
            }
            rs3.close();
        } catch (Exception e) {
            processSQLError(e);
        }
        return null;
    }

    @Override
    public void addEdible(Edible edible) {
        String edibleLabelQuery = addEdibleLableQuery(edible);
        String edibleQuery = (edible instanceof Food) ? addFoodQuery((Food) edible) : addMealQuery((Meal) edible);
    }

    private String addEdibleLableQuery(Edible edible) {
        String query = "INSERT INTO LABELS VALUES ";
        for (String label : edible.labels) {
            query += "('" + label + "'," + "'" + edible.name + "'), ";
        }
        query = query.substring(0, query.lastIndexOf(','));
        return query;
    }

    private String addFoodQuery(Food food) {
        String query = "INSERT INTO EDIBLES( " +
                "EDIBLE_NAME, IS_MEAL, WEIGHT ," +
                "FAT        ,CARBOHYDRATES ," +
                "PROTEIN    ,ALCOHOL," +
                "IRON       ,ZINC ," +
                "VITAMIN_A  ,VITAMIN_B12 ," +
                "VITAMIN_C  ,VITAMIN_E ," +
                "CALCIUM    ,CHOLINE ," +
                "MAGNESIUM  ,SODIUM ," +
                "POTASSIUM  ,NIACIN) " +
                "VALUES(  food.name  ,  0  ,  food.weight,"
                + "," + food.getMacroGrams(Fat) + "," + food.getMacroGrams(Carbohydrates)
                + "," + food.getMacroGrams(Protein) + "," + food.getMacroGrams(Alcohol)
                + "," + food.getMicroGrams(Iron) + "," + food.getMicroGrams(Zinc)
                + "," + food.getMicroGrams(VitaminA) + "," + food.getMicroGrams(VitaminB12)
                + "," + food.getMicroGrams(VitaminC) + "," + food.getMicroGrams(VitaminE)
                + "," + food.getMicroGrams(Calcium) + "," + food.getMicroGrams(Choline)
                + "," + food.getMicroGrams(Magnesium) + "," + food.getMicroGrams(Sodium)
                + "," + food.getMicroGrams(Potassium) + "," + food.getMicroGrams(Niacin)
                + ");";
        return query;
    }

    private String addMealQuery(Meal meal) {
        String mealAdd = "INSERT INTO EDIBLES( " +
                "EDIBLE_NAME_NAME, IS_MEAL) " +
                "VALUES('" + meal.name + "'," + 1 + ");";
        String ediblePairAdd = getEdibleIntPairInsertion(meal);
        return mealAdd + ediblePairAdd;
    }

    private String getEdibleIntPairInsertion(Meal meal) {
        String query = "INSERT INTO EDIBLE_INT_PAIRS(" +
                "PARENT_MEAL, EDIBLE_NAME, QUANTITY) VALUES";
        EdibleIntPair eip;
        Iterator<EdibleIntPair> iterator = meal.getEdibleIntPairIterator();
        while (iterator.hasNext()) {
            eip = iterator.next();
            query += "('" + meal.name + "'," + "'" + eip.edible.name + "'," + eip.quantity + "),";
        }
        query = query.substring(0, query.lastIndexOf(','));

        System.out.println(query);
        return query;
    }


    @Override
    public void updateEdible(String edibleToUpdate, Edible edible) {
        String labelUpdateQuery = getEdibleLabelUpdateQuery(edibleToUpdate, edible);
        String ediblePairQuery = getUpdateEdibleIntPairQuery(edibleToUpdate, edible.name);
        String edibleQuery = (edible instanceof Food)
                ? getUpdateFoodQuery(edibleToUpdate, (Food) edible)
                : getUpdateMealQuery(edibleToUpdate, (Meal) edible);
    }

    private String getEdibleLabelUpdateQuery(String edibleToUpdate, Edible edible) {
        //remove the labels of the food currently stored and add the new labels
        String query = "DELETE FROM EDIBLE_LABELS WHERE EDIBLE_NAME='" + edible.name + "';" +
                addEdibleLableQuery(edible);
        return query;
    }

    private String getUpdateEdibleIntPairQuery(String oldName, String newName) {
        return "UPDATE EDIBLE_INT_PAIRS " +
                "SET EDIBLE_NAME='" + newName + "' " +
                "WHERE EDIBLE_NAME='" + oldName + "';";
    }

    private String getUpdateFoodQuery(String foodToUpdate, Food food) {
        String query = "UPDATE EDIBLES SET " +
                "EDIBLE_NAME='" + food.name + "'," +
                "WEIGHT=" + food.weight + "," +
                "FAT=" + food.getMacroGrams(Fat) + "," +
                "CARBOHYDRATES=" + food.getMacroGrams(Carbohydrates) + " ," +
                "PROTEIN=" + food.getMacroGrams(Protein) + "," +
                "ALCOHOL=" + food.getMacroGrams(Alcohol) + "," +
                "IRON  =" + food.getMicroGrams(Iron) + "," +
                "ZINC=" + food.getMicroGrams(Zinc) + "," +
                "VITAMIN_A =" + food.getMicroGrams(VitaminA) + " ," +
                "VITAMIN_B12=" + food.getMicroGrams(VitaminB12) + " ," +
                "VITAMIN_C =" + food.getMicroGrams(VitaminC) + " ," +
                "VITAMIN_E =" + food.getMicroGrams(VitaminE) + "," +
                "CALCIUM =" + food.getMicroGrams(Calcium) + "   ," +
                "CHOLINE=" + food.getMicroGrams(Choline) + " ," +
                "MAGNESIUM =" + food.getMicroGrams(Magnesium) + " ," +
                "SODIUM =" + food.getMicroGrams(Sodium) + "," +
                "POTASSIUM =" + food.getMicroGrams(Potassium) + " ," +
                "NIACIN=" + food.getMicroGrams(Niacin) +
                " WHERE EDIBLE_NAME='" + foodToUpdate + "';";
        return query;
    }


    private String getUpdateMealQuery(String edibleToUpdate, Meal meal) {
        return "UPDATE EDIBLES SET EDIBLE_NAME = '" + meal.name + "'" +
                " WHERE EDIBLE_NAME='" + edibleToUpdate + "';";
    }


    @Override
    public void removeEdible(String name) {
        String query = "DELETE FROM EDIBLE_INT_PAIRS WHERE EDIBLE_NAME='" + name + "';" +
                " DELETE FROM EDIBLES WHERE EDIBLE_NAME = '" + name + "'";

    }


    @Override
    public List<Edible> getEdibles() {
        String query = "SELECT * FROM EDIBLES;";
        return null;
        //convert to either food or meal
    }

    @Override
    public List<Food> getFoods() {
        String query = "SELECT * FROM EDIBLES WHERE IS_MEAL=" + 0 + ";";
        return null;
    }

    @Override
    public List<Meal> getMeals() {
        String query = "SELECT * FROM EDIBLES WHERE IS_MEAL=" + 1 + ";";
        List<String> meals = new ArrayList<>();
        for (String mealName : meals) {
            getEntireMeal(mealName);
        }
        return null;
    }

    private Meal getEntireMeal(String mealName) {
        String q = "SELECT * FROM EDIBLE_INT_PAIRS WHERE EDIBLE_NAME='" + mealName + "';";
        //for each if food convert to food if meal call this funciton again
        return null;
    }


    @Override
    public void addLabel(String label) {
        String addLabelQuery = "INSERT INTO LABELS VALUES ('" + label + "')";
    }


    @Override
    public void updateLabel(String oldLabel, String newLabel) {
        String query = "UPDATE LABELS SET LABEL='" + newLabel + "' WHERE LABEL='" + oldLabel + "';";
    }


    @Override
    public void removeLabel(String label) {
        String query = "DELETE FROM LABELS WHERE LABEL ='" + label + "';";
    }


    @Override
    public List<String> getLabels() {
        String query = "SELECT * FROM LABELS";
        return null;
    }


    @Override
    public void addDay(String userName, int dayOfYear) {
        String query = "INSERT INTO DAYS " +
                "VALUES ('" + userName + "'," + dayOfYear + ");";
    }


    @Override
    public Day getDay(String userName, int dayOfYear) {
        String query = "SELECT * FROM DAYS " +
                "WHERE USERNAME='" + userName + "' AND DAY_OF_YEAR=" + dayOfYear + ";";
        //if null
        addDay(userName, dayOfYear);
        return null;
    }

    @Override
    public List<Day> getDays(String userName) {
        String query = "SELECT * FROM DAYS ";
        return null;
    }

    @Override
    public void updateDay(String userName, Day day) {
        String updateGoalQuery = getUpdateGoalQuery(userName, day);
        String updateExerciseQuery = getUpdateExerciseQuery(userName, day);
        //update day edibles
    }


    private String getUpdateGoalQuery(String userName, Day day) {
        String query = "DELETE FROM GOALS " +
                "WHERE USERNAME='" + userName + "' AND DAY_OF_YEAR=" + day.dayOfYear + "; " +
                "INSERT INTO GOALS VALUES";
        for (Goal goal : day.goals) {
            query += "('" + userName + "'," + day.dayOfYear + ",'" + goal.getClass() + "'," + goal.goalType.ordinal() +
                    ",'" + goal.id + "'," + goal.lowerBound + "," + goal.upperBound + "), ";
        }
        query = query.substring(0, query.lastIndexOf(','));
        return null;
    }

    private String getUpdateExerciseQuery(String userName, Day day) {
        String query = "DELETE FROM EXERCISE " +
                "WHERE USERNAME='" + userName + "' AND DAY_OF_YEAR=" + day.dayOfYear + "; " +
                "INSERT INTO EXERCISE VALUES";
        for (Exercise e : day.exercises) {
            query += "('" + userName + "'," + day.dayOfYear + ",'" + e.name + "', " + e.intensity.ordinal() + "," + e.duration +
                    "), ";
        }
        query = query.substring(0, query.lastIndexOf(','));
        return null;
    }

}