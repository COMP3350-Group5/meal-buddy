/****************************************
 * DataAccessObject
 * The code for interacting with the hsql database
 ****************************************/

package comp3350.mealbuddy.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Statement;
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
import comp3350.mealbuddy.objects.goals.CalorieGoal;
import comp3350.mealbuddy.objects.goals.Goal;
import comp3350.mealbuddy.objects.goals.LabelGoal;
import comp3350.mealbuddy.objects.goals.MacroGoal;
import comp3350.mealbuddy.objects.goals.MicroGoal;

import static comp3350.mealbuddy.objects.Day.MealTimeType.BREAKFAST;
import static comp3350.mealbuddy.objects.Day.MealTimeType.DINNER;
import static comp3350.mealbuddy.objects.Day.MealTimeType.LUNCH;
import static comp3350.mealbuddy.objects.Day.MealTimeType.SNACK;
import static comp3350.mealbuddy.objects.consumables.Edible.Macros.Alcohol;
import static comp3350.mealbuddy.objects.consumables.Edible.Macros.Carbohydrates;
import static comp3350.mealbuddy.objects.consumables.Edible.Macros.Fat;
import static comp3350.mealbuddy.objects.consumables.Edible.Macros.Protein;
import static comp3350.mealbuddy.objects.consumables.Edible.Micros.Calcium;
import static comp3350.mealbuddy.objects.consumables.Edible.Micros.Choline;
import static comp3350.mealbuddy.objects.consumables.Edible.Micros.Iron;
import static comp3350.mealbuddy.objects.consumables.Edible.Micros.Magnesium;
import static comp3350.mealbuddy.objects.consumables.Edible.Micros.Niacin;
import static comp3350.mealbuddy.objects.consumables.Edible.Micros.Potassium;
import static comp3350.mealbuddy.objects.consumables.Edible.Micros.Sodium;
import static comp3350.mealbuddy.objects.consumables.Edible.Micros.VitaminA;
import static comp3350.mealbuddy.objects.consumables.Edible.Micros.VitaminB12;
import static comp3350.mealbuddy.objects.consumables.Edible.Micros.VitaminC;
import static comp3350.mealbuddy.objects.consumables.Edible.Micros.VitaminE;
import static comp3350.mealbuddy.objects.consumables.Edible.Micros.Zinc;


public class DataAccessObject implements DataAccess {

    private Statement st;
    private Connection c1;

    private String dbName;
    private String dbType;

    private String cmdString;
    private int updateCount;
    private String result;

    private final String CALORIE_GOAL_CLASS = "Calorie";
    private final String MACRO_GOAL_CLASS = "Macro";
    private final String MICRO_GOAL_CLASS = "Micro";
    private final String LABEL_GOAL_CLASS = "Label";

    /*
     * Constructor
     * Creates a DataAccessObject
     * Parameters:
     *     @param name - The name of the database
     */
    public DataAccessObject(String dbName) {
        this.dbName = dbName;
    }

    /*
     * open
     * Creates the connection with the db
     * Parameters:
     *     @param name - The path to the db
     */
    @Override
    public void open(String dbPath) {
        String url;
        try {
            // Setup for HSQL
            dbType = "HSQL";
            Class.forName("org.hsqldb.jdbcDriver").newInstance();
            url = "jdbc:hsqldb:file:" + dbPath; // stored on disk mode
            System.out.println(url);
            c1 = DriverManager.getConnection(url, "SA", "");
            st = c1.createStatement();
        } catch (Exception e) {
            processSQLError(e);
        }
        System.out.println("Opened" + dbType + "database " + dbPath);
    }

    /*
     * close
     * Commits changes to the db and closes it
     */
    @Override
    public void close() {
        try {
            cmdString = "shutdown compact";
            ResultSet rs2 = st.executeQuery(cmdString);
            c1.close();
        } catch (Exception e) {
            processSQLError(e);
        }
        System.out.println("Closed " + dbType + " database " + dbName);
    }

    /*
     * checkWarning
     * Checks the warning message of a sql query
     * If there is a warning it will return the warning
     * in string format
     * Parameters:
     *     @param st - The Statement that was executed
     *     @param updateCount - how many rows were updated
     */
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

    /*
     * processSQLError
     * processes and logs an error that occurred during
     * sql execution
     * Parameters:
     *     @param e - The Exception that occured
     */
    public String processSQLError(Exception e) {
        String result = "*** SQL Error: " + e.getMessage();

        // Remember, this will NOT be seen by the user!
        e.printStackTrace();

        return result;
    }

    /*
     * addAccount
     * inserts an account to the db
     * Parameters:
     *     @param account - the account to insert
     */
    @Override
    public String addAccount(Account account) {
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
        result = null;
        try {
            cmdString = query;
            updateCount = st.executeUpdate(cmdString);
            result = checkWarning(st, updateCount);
        } catch (Exception e) {
            result = processSQLError(e);
        }
        return result;
    }

    /*
     * updateAccount
     * Updates an account already present
     * in the db
     * Parameters:
     *     @param userNameToUpdate - the username of the account to update
     *     @param account - the account holding the new info to update
     *                      the old info to
     */
    @Override
    public String updateAccount(String userNameToUpdate, Account account) {
        UserInfo ui = account.user;
        String query = "UPDATE ACCOUNTS SET " +
                "USERNAME ='" + ui.username + "'," +
                "ACCOUNT_PASSWORD = '" + ui.password + "'," +
                "FULL_NAME ='" + ui.fullname + "'," +
                "WEIGHT =" + ui.weight + "," +
                "HEIGHT =" + ui.height + "," +
                "SEX =" + ui.sex.ordinal() + "," +
                "ACTIVITY_LEVEL =" + ui.activityLevel.ordinal() + "," +
                "AGE =" + ui.age +
                " WHERE USERNAME = '" + userNameToUpdate + "';";
        result = null;
        try {
            cmdString = query;
            updateCount = st.executeUpdate(cmdString);
            result = checkWarning(st, updateCount);
        } catch (Exception e) {
            result = processSQLError(e);
        }
        return result;
    }

    /*
     * removeAccount
     * removes an account from the db
     * Parameters:
     *     @param userName - the username of the account to remove
     */
    @Override
    public String removeAccount(String userName) {
        String query = "DELETE FROM ACCOUNTS WHERE USERNAME = '" + userName + "'";
        result = null;
        try {
            cmdString = query;
            updateCount = st.executeUpdate(cmdString);
            result = checkWarning(st, updateCount);
        } catch (Exception e) {
            result = processSQLError(e);
        }
        return result;
    }

    /*
     * getAccount
     * gets the account associated with the username.
     * returns null if not in db
     * Parameters:
     *     @param userName - username associated with the account to get
     */
    @Override
    public Account getAccount(String userName) {
        Account account = null;
        String password, fullname;
        int weight, height, age;
        UserInfo.ActivityLevel al;
        UserInfo.Sex sex;
        try {
            cmdString = "SELECT * FROM ACCOUNTS WHERE USERNAME='" + userName + "'";
            ResultSet rs = st.executeQuery(cmdString);
            while (rs.next()) {
                password = rs.getString("ACCOUNT_PASSWORD");
                fullname = rs.getString("FULL_NAME");
                weight = rs.getInt("WEIGHT");
                height = rs.getInt("HEIGHT");
                age = rs.getInt("AGE");
                al = UserInfo.ActivityLevel.values()[rs.getInt("ACTIVITY_LEVEL")];
                sex = UserInfo.Sex.values()[rs.getInt("SEX")];
                UserInfo ui = new UserInfo(fullname, userName, password, weight, height, al, sex, age);
                account = new Account(ui);
            }
            rs.close();
        } catch (Exception e) {
            processSQLError(e);
        }
        return account;
    }

    /*
     * addEdible
     * Adds an edible to the db
     * Parameters:
     *     @param edible - the edible to add
     */
    @Override
    public String addEdible(Edible edible) {
        result = null;
        try {
            if (edible instanceof Food)
                insertFood((Food) edible);
            else
                insertMeal((Meal) edible);
            insertEdibleLabels(edible);
        } catch (Exception e) {
            result = processSQLError(e);
        }
        return result;
    }

    /*
     * insertEdibleLabels
     * inserts labels associated with an edible into the db
     * Parameters:
     *     @param edible - the edible that we are adding
     */
    private void insertEdibleLabels(Edible edible) throws SQLException {
        String query = "";
        for (String label : edible.labels) {
            query = "INSERT INTO EDIBLE_LABELS VALUES " +
                    "('" + label + "'," + "'" + edible.name + "')";
            cmdString = query;
            updateCount = st.executeUpdate(cmdString);
            result = checkWarning(st, updateCount);
        }
    }

    /*
     * insertFood
     * inserts the food into the db
     * Parameters:
     *     @param food - the food we are adding to the db
     */
    private void insertFood(Food food) throws SQLException {
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
                "VALUES(  '" + food.name + "'  , FALSE ,  " + food.weight
                + "," + food.getMacroGrams(Fat) + "," + food.getMacroGrams(Carbohydrates)
                + "," + food.getMacroGrams(Protein) + "," + food.getMacroGrams(Alcohol)
                + "," + food.getMicroGrams(Iron) + "," + food.getMicroGrams(Zinc)
                + "," + food.getMicroGrams(VitaminA) + "," + food.getMicroGrams(VitaminB12)
                + "," + food.getMicroGrams(VitaminC) + "," + food.getMicroGrams(VitaminE)
                + "," + food.getMicroGrams(Calcium) + "," + food.getMicroGrams(Choline)
                + "," + food.getMicroGrams(Magnesium) + "," + food.getMicroGrams(Sodium)
                + "," + food.getMicroGrams(Potassium) + "," + food.getMicroGrams(Niacin)
                + ");";
        cmdString = query;
        updateCount = st.executeUpdate(cmdString);
        checkWarning(st, updateCount);
    }

    /*
     * insertMeal
     * inserts a meal to the db
     * Parameters:
     *     @param meal - the meal to insert
     */
    private void insertMeal(Meal meal) throws SQLException {
        String mealAdd = "INSERT INTO EDIBLES( " +
                "EDIBLE_NAME, IS_MEAL) " +
                "VALUES('" + meal.name + "', TRUE);";
        cmdString = mealAdd;
        updateCount = st.executeUpdate(cmdString);
        checkWarning(st, updateCount);

        insertEdibleIntPairs(meal);
    }

    /*
     * getAddEdibleIntPairQuery
     * Gets the query that adds all the edible int pairs
     * associated with a meal to the db
     * Parameters:
     *     @param meal - the meal to get the edible int pairs from
     */
    private void insertEdibleIntPairs(Meal meal) throws SQLException {
        Iterator<EdibleIntPair> iterator = meal.getEdibleIntPairIterator();
        if (!iterator.hasNext())
            return;
        String query;
        EdibleIntPair eip;
        while (iterator.hasNext()) {
            query = "INSERT INTO EDIBLE_INT_PAIRS(" +
                    "PARENT_MEAL, EDIBLE_NAME, QUANTITY) VALUES";
            eip = iterator.next();
            query += "('" + meal.name + "'," + "'" + eip.edible.name + "'," + eip.quantity + ")";
            cmdString = query;
            updateCount = st.executeUpdate(cmdString);
            checkWarning(st, updateCount);
        }
    }

    /*
     * updateEdible
     * Updates an edible in the db
     * Parameters:
     *     @param edible - the edible to update
     */
    @Override
    public String updateEdible(String edibleToUpdate, Edible edible) {

        result = null;
        try {
            deleteEdibleLabels(edibleToUpdate);
            if (edible instanceof Food)
                updateFood(edibleToUpdate, (Food) edible);
            else
                updateMeal(edibleToUpdate, (Meal) edible);
            insertEdibleLabels(edible);
        } catch (Exception e) {
            result = processSQLError(e);
        }
        return result;
    }


    /*
     * deleteEdibleLabels
     * deletes the labels associated
     * with an edible from the db
     * Parameters:
     *     @param edibleToUpdate - name of the edible to update
     */
    private void deleteEdibleLabels(String edibleToUpdate) throws SQLException {
        String query = "DELETE FROM EDIBLE_LABELS WHERE EDIBLE_NAME='" + edibleToUpdate + "';";
        cmdString = query;
        updateCount = st.executeUpdate(cmdString);
        checkWarning(st, updateCount);
    }

    /*
     * updateFood
     * updates the food in the db
     * Parameters:
     *     @param foodToUpdate - the name of the food to update
     *     @param food - holds the new info we are updating to
     */
    private void updateFood(String foodToUpdate, Food food) throws SQLException {
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
        cmdString = query;
        updateCount = st.executeUpdate(cmdString);
        checkWarning(st, updateCount);
    }


    /*
     * UpdateMeal
     * updates the meal in the db
     * Parameters:
     *     @param mealToUpdate - the name of the meal to update
     *     @param meal - holds the new info we are updating to
     */
    private void updateMeal(String mealToUpdate, Meal meal) throws SQLException {
        String updateMeal = "" +
                "DELETE FROM EDIBLE_INT_PAIRS " +
                "WHERE PARENT_MEAL ='" + mealToUpdate + "'; " +
                "UPDATE EDIBLES SET EDIBLE_NAME = '" + meal.name + "' " +
                "WHERE EDIBLE_NAME='" + mealToUpdate + "';";
        cmdString = updateMeal;
        updateCount = st.executeUpdate(cmdString);
        checkWarning(st, updateCount);

        insertEdibleIntPairs(meal);
    }


    /*
     * removeEdible
     * removes the edible from the db
     * Parameters:
     *     @param name - the name of the edible to remove
     */
    @Override
    public String removeEdible(String name) {
        String query = "DELETE FROM EDIBLE_INT_PAIRS WHERE PARENT_MEAL='" + name + "';";
        result = null;
        try {
            cmdString = query;
            updateCount = st.executeUpdate(cmdString);
            result = checkWarning(st, updateCount);

            cmdString = " DELETE FROM EDIBLES WHERE EDIBLE_NAME = '" + name + "'";
            updateCount = st.executeUpdate(cmdString);
            result = checkWarning(st, updateCount);
        } catch (Exception e) {
            result = processSQLError(e);
        }
        return result;
    }

    /*
     * getEdibles
     * gets all the edibles in the db
     */
    @Override
    public List<Edible> getEdibles() {
        String query = "SELECT * FROM EDIBLES;";
        ArrayList<Edible> edibles = new ArrayList<>();
        boolean isMeal;
        String name;
        Edible edible;
        try {
            cmdString = query;
            ResultSet results = st.executeQuery(cmdString);
            while (results.next()) {
                isMeal = results.getBoolean("IS_MEAL");
                name = results.getString("EDIBLE_NAME");
                edible = isMeal ? getEntireMeal(name) : getFoodFromRs(results);
                edibles.add(edible);
            }
            results.close();
        } catch (Exception e) {
            processSQLError(e);
        }
        return edibles;
    }

    /*
     * getFoods
     * gets all the food in the db
     */
    @Override
    public List<Food> getFoods() {
        String query = "SELECT * FROM EDIBLES WHERE IS_MEAL=FALSE;";
        List<Food> foods = new ArrayList<>();
        try {
            cmdString = query;
            ResultSet rs = st.executeQuery(cmdString);
            // ResultSetMetaData md5 = rs.getMetaData();
            while (rs.next()) {
                foods.add(getFoodFromRs(rs));
            }
            rs.close();
        } catch (Exception e) {
            processSQLError(e);
        }
        return foods;
    }

    /*
     * getFoodFromRs
     * creates a food object from a result set (query result)
     * Parameters:
     *     @param ers - the result set that has queried all the info
     *                  necessary to make a food
     */
    private Food getFoodFromRs(ResultSet ers) throws SQLException {
        String name = ers.getString("EDIBLE_NAME");
        ArrayList<String> labels = getEdibleLabels(name);
        Food food = new Food(name, labels);
        food.weight = ers.getInt("WEIGHT");
        food.updateMacro(Fat, ers.getInt("FAT"));
        food.updateMacro(Carbohydrates, ers.getInt("CARBOHYDRATES"));
        food.updateMacro(Protein, ers.getInt("PROTEIN"));
        food.updateMacro(Alcohol, ers.getInt("ALCOHOL"));
        food.updateMicro(Iron, ers.getInt("IRON"));
        food.updateMicro(Zinc, ers.getInt("ZINC"));
        food.updateMicro(VitaminA, ers.getInt("VITAMIN_A"));
        food.updateMicro(VitaminB12, ers.getInt("VITAMIN_B12"));
        food.updateMicro(VitaminC, ers.getInt("VITAMIN_C"));
        food.updateMicro(VitaminE, ers.getInt("VITAMIN_E"));
        food.updateMicro(Calcium, ers.getInt("CALCIUM"));
        food.updateMicro(Choline, ers.getInt("CHOLINE"));
        food.updateMicro(Magnesium, ers.getInt("MAGNESIUM"));
        food.updateMicro(Sodium, ers.getInt("SODIUM"));
        food.updateMicro(Potassium, ers.getInt("POTASSIUM"));
        food.updateMicro(Niacin, ers.getInt("NIACIN"));
        return food;
    }

    /*
     * getEdibleLabels
     * gets the labels associated with an edible
     * Parameters:
     *     @param name - the name of the edible to get the labels of
     */
    private ArrayList<String> getEdibleLabels(String name) throws SQLException {
        String query = "SELECT LABEL FROM EDIBLE_LABELS WHERE EDIBLE_NAME='" + name + "';";
        ArrayList<String> labels = new ArrayList<>();
        result = null;
        cmdString = query;
        ResultSet results = st.executeQuery(cmdString);
        while (results.next()) {
            labels.add(results.getString("LABEL"));
        }
        results.close();

        return labels;
    }

    /*
     * getMeals
     * gets all the meals in the db
     */
    @Override
    public List<Meal> getMeals() {
        String query = "SELECT * FROM EDIBLES WHERE IS_MEAL=TRUE;";
        Meal meal;
        String name;
        List<Meal> meals = new ArrayList<>();
        ArrayList<String> labels = new ArrayList<>();
        try {
            cmdString = query;
            ResultSet results = st.executeQuery(cmdString);
            // ResultSetMetaData md5 = rs.getMetaData();
            while (results.next()) {
                name = results.getString("EDIBLE_NAME");
                meal = getEntireMeal(name);
                meals.add(meal);
            }
            results.close();
        } catch (Exception e) {
            processSQLError(e);
        }
        return meals;
    }

    /*
     * getEntireMeal
     * gets the meal associated with the name from the db.
     * This will get all levels of a nested meal
     * Parameters:
     *     @param mealName - the name of the meal to get
     */
    private Meal getEntireMeal(String mealName) throws SQLException {
        String query = "SELECT * FROM EDIBLE_INT_PAIRS AS EIP, EDIBLES AS E " +
                "WHERE EIP.EDIBLE_NAME=E.EDIBLE_NAME AND" +
                " E.EDIBLE_NAME IN " +
                "(SELECT EDIBLE_NAME FROM EDIBLE_INT_PAIRS " +
                "WHERE PARENT_MEAL = '" + mealName + "');";
        ArrayList<String> labels = getEdibleLabels(mealName);
        Meal meal = new Meal(mealName, labels);
        int quantity;
        String edibleName;
        boolean isMeal;
        Edible edibleInMeal;
        cmdString = query;
        ResultSet resultSet = st.executeQuery(cmdString);
        while (resultSet.next()) {
            edibleName = resultSet.getString("EDIBLE_NAME");
            quantity = resultSet.getInt("QUANTITY");
            isMeal = resultSet.getBoolean("IS_MEAL");
            edibleInMeal = isMeal ? getEntireMeal(edibleName) : getFoodFromRs(resultSet);
            meal.add(edibleInMeal, quantity);
        }
        resultSet.close();
        return meal;
    }

    /*
     * addLabel
     * adds the label to the db
     * Parameters:
     *     @param label - the name label to add
     */
    @Override
    public String addLabel(String label) {
        String query = "INSERT INTO LABELS VALUES ('" + label + "')";
        result = null;
        try {
            cmdString = query;
            updateCount = st.executeUpdate(cmdString);
            result = checkWarning(st, updateCount);
        } catch (Exception e) {
            result = processSQLError(e);
        }
        return result;
    }


    /*
     * updateLabel
     * updates a label in the db
     * Parameters:
     *     @param oldLabel - the name of the label to update
     *     @param newLabel - the name to update to
     */
    @Override
    public String updateLabel(String oldLabel, String newLabel) {
        String query = "UPDATE LABELS SET LABEL='" + newLabel + "' WHERE LABEL='" + oldLabel + "';";
        result = null;
        try {
            cmdString = query;
            updateCount = st.executeUpdate(cmdString);
            result = checkWarning(st, updateCount);
        } catch (Exception e) {
            result = processSQLError(e);
        }
        return result;
    }

    /*
     * removeLabel
     * removes a label from the db
     * Parameters:
     *     @param name - the name of the label to remove
     */
    @Override
    public String removeLabel(String label) {
        String query = "DELETE FROM LABELS WHERE LABEL ='" + label + "';";
        result = null;
        try {
            cmdString = query;
            updateCount = st.executeUpdate(cmdString);
            result = checkWarning(st, updateCount);
        } catch (Exception e) {
            result = processSQLError(e);
        }
        return result;
    }

    /*
     * getLabels
     * gets all labels from the db
     */
    @Override
    public List<String> getLabels() {
        String query = "SELECT LABEL FROM LABELS";
        List<String> labels = new ArrayList<>();
        try {
            cmdString = query;
            ResultSet rs = st.executeQuery(cmdString);
            while (rs.next()) {
                labels.add(rs.getString("LABEL"));
            }
            rs.close();
        } catch (Exception e) {
            processSQLError(e);
        }
        return labels;
    }

    /*
     * addDay
     * adds a new day to the db. Will always be an empty day
     * Parameters:
     *     @param userName - the account to add the day under
     *     @param dayOfYear - the day of year to add the day to
     */
    @Override
    public String addDay(String userName, int dayOfYear) {
        String query = "INSERT INTO DAYS " +
                "VALUES ('" + userName + "'," + dayOfYear + ");";
        result = null;
        try {
            cmdString = query;
            updateCount = st.executeUpdate(cmdString);
            result = checkWarning(st, updateCount);
        } catch (Exception e) {
            result = processSQLError(e);
        }
        return result;
    }

    /*
     * getDay
     * gets the day from the associated account username
     * Parameters:
     *     @param userName - the userName of the account
     *     @param dayOfYear - the day of the year to get
     */
    @Override
    public Day getDay(String userName, int dayOfYear) {
        Day day = new Day(dayOfYear);
        try {
            for (Exercise exercise : getDayExercises(userName, dayOfYear)) {
                day.addExercise(exercise);
            }
            for (Goal goal : getDayGoals(userName, dayOfYear)) {
                day.addGoal(goal);
            }
            day.breakfast = getMealTimeMeal(BREAKFAST, userName, dayOfYear);
            day.lunch = getMealTimeMeal(LUNCH, userName, dayOfYear);
            day.dinner = getMealTimeMeal(DINNER, userName, dayOfYear);
            day.snack = getMealTimeMeal(SNACK, userName, dayOfYear);
        } catch (Exception e) {
            processSQLError(e);
        }
        return day;
    }


    /*
     * getDay
     * gets all days from the associated account username
     * Parameters:
     *     @param userName - the userName of the account
     */
    @Override
    public List<Day> getDays(String userName) {
        String query = "SELECT * FROM DAYS " +
                "WHERE USERNAME = '" + userName + "'";
        List<Day> days = new ArrayList<>();
        int dayOfYear;
        try {
            cmdString = query;
            ResultSet resultSet = st.executeQuery(cmdString);
            while (resultSet.next()) {
                dayOfYear = resultSet.getInt("DAY_OF_YEAR");
                days.add(getDay(userName, dayOfYear));
            }
            resultSet.close();
        } catch (Exception e) {
            processSQLError(e);
        }
        return days;
    }

    /*
     * getMealTimeList
     * gets the meal of edibles from a meal time in a day
     * Parameters:
     *     @param userName - the userName of the account
     *     @param dayOfYear - the day to get the list of
     *     @param mealTime - which meal to get
     */
    private Meal getMealTimeMeal(Day.MealTimeType mealTime, String userName, int dayOfYear) throws SQLException {
        String query = "SELECT * " +
                "FROM DAY_EDIBLES AS DE, EDIBLES AS E " +
                "WHERE USERNAME='" + userName + "' AND DAY_OF_YEAR=" + dayOfYear +
                " AND MEAL_TIME=" + mealTime.ordinal() + " AND DE.EDIBLE_NAME = E.EDIBLE_NAME";
        Meal meal = new Meal(getMealTimeName(mealTime));
        String edibleInMealName;
        Edible edibleInMeal;
        boolean isMeal;
        cmdString = query;
        ResultSet resultSet = st.executeQuery(cmdString);
        while (resultSet.next()) {
            isMeal = resultSet.getBoolean("IS_MEAL");
            edibleInMealName = resultSet.getString("EDIBLE_NAME");
            edibleInMeal = isMeal ? getEntireMeal(edibleInMealName) : getFoodFromRs(resultSet);
            meal.add(edibleInMeal);
        }
        resultSet.close();
        return meal;
    }

    private String getMealTimeName(Day.MealTimeType mealTIme) {
        String name;
        switch (mealTIme) {
            case BREAKFAST:
                name = Day.BREAKFAST_NAME;
                break;
            case LUNCH:
                name = Day.LUNCH_NAME;
                break;
            case DINNER:
                name = Day.DINNER_NAME;
                break;
            default:
                name = Day.SNACK_NAME;
                break;
        }
        return name;
    }


    /*
     * getDayGoals
     * gets the list of goals in a day
     * Parameters:
     *     @param userName - the userName of the account
     *     @param dayOfYear - the day to get the list of
     */
    private ArrayList<Goal> getDayGoals(String userName, int dayOfYear) throws SQLException {
        String query = "SELECT * FROM GOALS " +
                "WHERE USERNAME='" + userName + "' AND DAY_OF_YEAR=" + dayOfYear;
        ArrayList<Goal> goals = new ArrayList<>();
        String goalClass, goalId;
        int lowerBound, upperBound;
        Goal.GoalType goalType;
        cmdString = query;
        ResultSet rs = st.executeQuery(cmdString);
        while (rs.next()) {
            goalClass = rs.getString("GOAL_CLASS");
            goalId = rs.getString("GOAL_ID");
            goalType = Goal.GoalType.values()[rs.getInt("GOAL_TYPE")];
            lowerBound = rs.getInt("LOWER_BOUND");
            upperBound = rs.getInt("UPPER_BOUND");
            goals.add(makeGoal(goalClass, goalId, goalType, lowerBound, upperBound));
        }
        rs.close();
        return goals;
    }

    /*
     * makeGoal
     * Creates a new goal
     * Parameters:
     *     @param clas - which subclass the goal is
     *     @param id - the goal id
     *     @param type - which type of goal (ratio/quantity)
     *     @param lower - the lower bound of a goal
     *     @param upper - the upper bound of a goal
     */
    private Goal makeGoal(String clas, String id, Goal.GoalType type, int lower, int upper) {

        Goal goal;
        switch (clas) {
            case CALORIE_GOAL_CLASS:
                goal = new CalorieGoal(lower, upper);
                break;
            case MACRO_GOAL_CLASS:
                goal = new MacroGoal(lower, upper, type, getMacroFromString(id));
                break;
            case MICRO_GOAL_CLASS:
                goal = new MicroGoal(lower, upper, getMicroFromString(id));
                break;
            default:
                goal = new LabelGoal(lower, upper, type, id);
                break;
        }
        return goal;
    }

    /*
     * getMacroFromString
     * gets Macro from its string representation
     * Parameters:
     *     @param macro - the macro to get
     */
    private Edible.Macros getMacroFromString(String macro) {
        switch (macro) {
            case "Fat":
                return Fat;
            case "Carbohydrates":
                return Carbohydrates;
            case "Protein":
                return Protein;
            default:
                return Alcohol;
        }
    }

    /*
     * getMicroFromString
     * gets Micro from its string representation
     * Parameters:
     *     @param micro - the micro to get
     */
    private Edible.Micros getMicroFromString(String micro) {
        switch (micro) {
            case "Iron":
                return Iron;
            case "Zinc":
                return Zinc;
            case "VitaminA":
                return VitaminA;
            case "VitaminB12":
                return VitaminB12;
            case "VitaminC":
                return VitaminC;
            case "VitaminE":
                return VitaminE;
            case "Calcium":
                return Calcium;
            case "Magnesium":
                return Magnesium;
            case "Sodium":
                return Sodium;
            case "Potassium":
                return Potassium;
            default:
                return Niacin;
        }
    }


    /*
     * getExerciseGoals
     * gets the list of exercise in a day
     * Parameters:
     *     @param userName - the userName of the account
     *     @param dayOfYear - the day to get the list of
     */
    private ArrayList<Exercise> getDayExercises(String userName, int dayOfYear) throws SQLException {
        String query = "SELECT * FROM EXERCISE " +
                "WHERE USERNAME='" + userName + "' AND DAY_OF_YEAR=" + dayOfYear;
        ArrayList<Exercise> exercises = new ArrayList<>();
        String name;
        int duration;
        Exercise.Intensity intensity;
        cmdString = query;
        ResultSet rs = st.executeQuery(cmdString);
        while (rs.next()) {
            name = rs.getString("EXERCISE_NAME");
            intensity = Exercise.Intensity.values()[rs.getInt("INTENSITY")];
            duration = rs.getInt("DURATION");
            exercises.add(new Exercise(name, duration, intensity));
        }
        rs.close();
        return exercises;
    }

    /*
     * updateDay
     * updates a certain day in an account.  Updates all info associated with the day
     * including exercise, meals, and goals.  Will update by first removing all current
     * info of the day and then adding the new info. Note the day of the year
     * in a day can not be updated.
     * Parameters:
     *     @param userName - the userName of the account
     *     @param dayOfYear - the day to update
     */
    @Override
    public String updateDay(String userName, Day day) {
        result = null;
        try {
            updateGoals(userName, day);
            updateExercise(userName, day);
            updateDayEdibles(userName, day);
        } catch (Exception e) {
            result = processSQLError(e);
        }
        return result;
    }

    /*
     * updateDayEdibles
     * updates all edibles in a day
     * Parameters:
     *     @param userName - the userName of the account
     *     @param dayOfYear - the day to update
     */
    private void updateDayEdibles(String userName, Day day) throws SQLException {
        String deleteOldFood = "DELETE FROM DAY_EDIBLES " +
                "WHERE USERNAME='" + userName + "' AND DAY_OF_YEAR=" + day.dayOfYear + "; ";

        cmdString = deleteOldFood;
        updateCount = st.executeUpdate(cmdString);
        checkWarning(st, updateCount);

        insertNewMealTimeEdibles(userName, BREAKFAST, day);
        insertNewMealTimeEdibles(userName, LUNCH, day);
        insertNewMealTimeEdibles(userName, DINNER, day);
        insertNewMealTimeEdibles(userName, SNACK, day);
    }

    /*
     * insertNewMealTimeEdibles
     * gets the query that adds all edibles in a day in a mealtime
     * Parameters:
     *     @param userName - the userName of the account
     *     @param mealTime - the meal in a day we are updating
     *     @param day - the day we are updating
     */
    private void insertNewMealTimeEdibles(String userName, Day.MealTimeType mealTime, Day day) throws SQLException {
        Meal meal = day.getMealTime(mealTime);
        String query = "";
        for (Edible e : meal) {
            query = "INSERT INTO DAY_EDIBLES VALUES";
            query += "( '" + e.name + "', '" + userName + "'," + day.dayOfYear + "," +
                    "" + mealTime.ordinal() + ", " + meal.getQuantity(e) + ") ";
            cmdString = query;
            updateCount = st.executeUpdate(cmdString);
            checkWarning(st, updateCount);
        }
    }

    /*
     * updateGoals
     * updates all goals in a day
     * Parameters:
     *     @param userName - the userName of the account
     *     @param dayOfYear - the day to update
     */
    private void updateGoals(String userName, Day day) throws SQLException {
        String deleteOldGoals = "DELETE FROM GOALS " +
                "WHERE USERNAME='" + userName + "' AND DAY_OF_YEAR=" + day.dayOfYear + "; ";

        cmdString = deleteOldGoals;
        updateCount = st.executeUpdate(cmdString);
        checkWarning(st, updateCount);

        String insertNewGoals;
        for (Goal goal : day.getGoals()) {
            insertNewGoals = "INSERT INTO GOALS VALUES";
            insertNewGoals += "('" + userName + "'," + day.dayOfYear + ",'" +
                    "" + getGoalClass(goal) + "'," + goal.goalType.ordinal() +
                    ",'" + goal.id.toString() + "'," + goal.lowerBound + "," + goal.upperBound + ") ";
            cmdString = insertNewGoals;
            updateCount = st.executeUpdate(cmdString);
            checkWarning(st, updateCount);
        }
    }

    private String getGoalClass(Goal goal) {
        if (goal instanceof MacroGoal)
            return MACRO_GOAL_CLASS;
        else if (goal instanceof CalorieGoal)
            return CALORIE_GOAL_CLASS;
        else if (goal instanceof MicroGoal)
            return MICRO_GOAL_CLASS;
        else
            return LABEL_GOAL_CLASS;
    }


    /*
     * getUpdateExerciseQuery
     * updates all exercise in a day
     * Parameters:
     *     @param userName - the userName of the account
     *     @param dayOfYear - the day to update
     */
    private void updateExercise(String userName, Day day) throws SQLException {
        String deleteOldEx = "DELETE FROM EXERCISE " +
                "WHERE USERNAME='" + userName + "' AND DAY_OF_YEAR=" + day.dayOfYear + "; ";

        cmdString = deleteOldEx;
        updateCount = st.executeUpdate(cmdString);
        checkWarning(st, updateCount);

        String insertNewEx;
        for (Exercise e : day.getExercises()) {
            insertNewEx = "INSERT INTO EXERCISE VALUES";
            insertNewEx += "('" + userName + "'," + day.dayOfYear + ",'" + e.name + "', " +
                    "" + e.intensity.ordinal() + "," + e.duration + ") ";
            cmdString = insertNewEx;
            updateCount = st.executeUpdate(cmdString);
            checkWarning(st, updateCount);
        }
    }

    /*
     * isDayTracked
     * returns true if the day is tracked
     *     @param userName - the account to check the day under
     *     @param dayOfYear - the day of year to check the day of
     */
    @Override
    public boolean isDayTracked(String userName, int dayOfYear) {
        boolean isTracked = false;
        String query = "SELECT * FROM DAYS " +
                "WHERE USERNAME='" + userName + "' AND DAY_OF_YEAR=" + dayOfYear;
        List<String> labels = new ArrayList<>();
        try {
            cmdString = query;
            ResultSet rs = st.executeQuery(cmdString);
            isTracked = rs.next();
            rs.close();
        } catch (Exception e) {
            processSQLError(e);
        }
        return isTracked;
    }

}