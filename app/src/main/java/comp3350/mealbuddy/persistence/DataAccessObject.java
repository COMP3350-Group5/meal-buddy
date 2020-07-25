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

    private Statement st1, st2, st3, st, stm;
    private Connection c1;
    private ResultSet rs2, rs3, rs4, rs5, rs, rsm;

    private String dbName;
    private String dbType;

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
            st = c1.createStatement();
            stm = c1.createStatement();
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
            updateCount = st1.executeUpdate(cmdString);
            result = checkWarning(st1, updateCount);
        } catch (Exception e) {
            result = processSQLError(e);
        }
        return result;
    }

    @Override
    public String updateAccount(String usernameToUpdate, Account account) {
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
                " WHERE USERNAME = '" + usernameToUpdate + "';";
        result = null;
        try {
            cmdString = query;
            updateCount = st1.executeUpdate(cmdString);
            result = checkWarning(st1, updateCount);
        } catch (Exception e) {
            result = processSQLError(e);
        }
        return result;
    }

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

    @Override
    public Account getAccount(String username) {
        Account account = null;
        String password = "", fullname = "";
        int weight = 0, height = 0, age = 0;
        UserInfo.ActivityLevel al = null;
        UserInfo.Sex sex = null;
        try {
            cmdString = "SELECT * FROM ACCOUNTS WHERE USERNAME='" + username + "'";
            rs = st.executeQuery(cmdString);
            // ResultSetMetaData md2 = rs3.getMetaData();
            while (rs.next()) {
                password = rs.getString("ACCOUNT_PASSWORD");
                fullname = rs.getString("FULL_NAME");
                weight = rs.getInt("WEIGHT");
                height = rs.getInt("HEIGHT");
                age = rs.getInt("AGE");
                al = UserInfo.ActivityLevel.values()[rs.getInt("ACTIVITY_LEVEL")];
                sex = UserInfo.Sex.values()[rs.getInt("SEX")];
                UserInfo ui = new UserInfo(fullname, username, password, weight, height, al, sex, age);
                account = new Account(ui);
            }
            rs.close();
        } catch (Exception e) {
            processSQLError(e);
        }
        return account;
    }

    @Override
    public String addEdible(Edible edible) {
        String edibleLabelQuery = addEdibleLableQuery(edible);
        String edibleQuery = (edible instanceof Food) ? addFoodQuery((Food) edible) : addMealQuery((Meal) edible);
        result = null;
        try {
            cmdString = edibleQuery + edibleLabelQuery;
            updateCount = st1.executeUpdate(cmdString);
            result = checkWarning(st1, updateCount);
        } catch (Exception e) {
            result = processSQLError(e);
        }
        return result;
    }

    private String addEdibleLableQuery(Edible edible) {
        String query = "";
        if (edible.labels.size() > 0) {
            query = "INSERT INTO EDIBLE_LABELS VALUES ";
            for (String label : edible.labels) {
                query += "('" + label + "'," + "'" + edible.name + "'), ";
            }
            query = query.substring(0, query.lastIndexOf(','));
        }
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
                "VALUES(  '" + food.name + "'  , True ,  " + food.weight
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
                "EDIBLE_NAME, IS_MEAL) " +
                "VALUES('" + meal.name + "', TRUE);";
        String ediblePairAdd = getEdibleIntPairInsertion(meal);
        return mealAdd + ediblePairAdd;
    }

    private String getEdibleIntPairInsertion(Meal meal) {
        Iterator<EdibleIntPair> iterator = meal.getEdibleIntPairIterator();
        if (!iterator.hasNext())
            return "";
        String query = "INSERT INTO EDIBLE_INT_PAIRS(" +
                "PARENT_MEAL, EDIBLE_NAME, QUANTITY) VALUES";
        EdibleIntPair eip;
        while (iterator.hasNext()) {
            eip = iterator.next();
            query += "('" + meal.name + "'," + "'" + eip.edible.name + "'," + eip.quantity + "),";
        }
        query = query.substring(0, query.lastIndexOf(','));
        return query;
    }


    @Override
    public String updateEdible(String edibleToUpdate, Edible edible) {
        String labelDeleteQuery = getDeleteEdibleLabelsQuery(edibleToUpdate);
        String labelInsertionQuery = addEdibleLableQuery(edible);
        String edibleQuery = (edible instanceof Food)
                ? getUpdateFoodQuery(edibleToUpdate, (Food) edible)
                : getUpdateMealQuery(edibleToUpdate, (Meal) edible);
        result = null;
        try {
            cmdString = labelDeleteQuery + edibleQuery + labelInsertionQuery;
            updateCount = st1.executeUpdate(cmdString);
            result = checkWarning(st1, updateCount);
        } catch (Exception e) {
            result = processSQLError(e);
        }
        return result;
    }


    private String getDeleteEdibleLabelsQuery(String edibleToUpdate) {
        return "DELETE FROM EDIBLE_LABELS WHERE EDIBLE_NAME='" + edibleToUpdate + "';";
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
        String updateMeal = "" +
                "DELETE FROM EDIBLE_INT_PAIRS " +
                "WHERE PARENT_MEAL ='" + edibleToUpdate + "'; " +
                "UPDATE EDIBLES SET EDIBLE_NAME = '" + meal.name + "' " +
                "WHERE EDIBLE_NAME='" + edibleToUpdate + "';";
        String ediblePairAdd = getEdibleIntPairInsertion(meal);
        return updateMeal + ediblePairAdd;
    }


    @Override
    public String removeEdible(String name) {
        String query = "DELETE FROM EDIBLE_INT_PAIRS WHERE PARENT_MEAL='" + name + "';" +
                " DELETE FROM EDIBLES WHERE EDIBLE_NAME = '" + name + "'";
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
            // ResultSetMetaData md5 = rs.getMetaData();
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

    @Override
    public List<Food> getFoods() {
        String query = "SELECT * FROM EDIBLES WHERE IS_MEAL=FALSE;";
        List<Food> foods = new ArrayList<>();
        try {
            cmdString = query;
            rs = st.executeQuery(cmdString);
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

    private ArrayList<String> getEdibleLabels(String name) throws SQLException {
        String query = "SELECT LABEL FROM EDIBLE_LABELS WHERE EDIBLE_NAME='" + name + "';";
        ArrayList<String> labels = new ArrayList<>();
        result = null;
        cmdString = query;
        ResultSet results = st3.executeQuery(cmdString);
        // ResultSetMetaData md5 = rs5.getMetaData();
        while (results.next()) {
            labels.add(results.getString("LABEL"));
        }
        results.close();

        return labels;
    }

    @Override
    public List<Meal> getMeals() {
        String query = "SELECT * FROM EDIBLES WHERE IS_MEAL=TRUE;";
        Meal meal = null;
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
            meal.setEdible(edibleInMeal, quantity);
        }
        resultSet.close();
        return meal;
    }


    @Override
    public String addLabel(String label) {
        String query = "INSERT INTO LABELS VALUES ('" + label + "')";
        result = null;
        try {
            cmdString = query;
            updateCount = st1.executeUpdate(cmdString);
            result = checkWarning(st1, updateCount);
        } catch (Exception e) {
            result = processSQLError(e);
        }
        return result;
    }


    @Override
    public String updateLabel(String oldLabel, String newLabel) {
        String query = "UPDATE LABELS SET LABEL='" + newLabel + "' WHERE LABEL='" + oldLabel + "';";
        result = null;
        try {
            cmdString = query;
            updateCount = st1.executeUpdate(cmdString);
            result = checkWarning(st1, updateCount);
        } catch (Exception e) {
            result = processSQLError(e);
        }
        return result;
    }


    @Override
    public String removeLabel(String label) {
        String query = "DELETE FROM LABELS WHERE LABEL ='" + label + "';";
        result = null;
        try {
            cmdString = query;
            updateCount = st1.executeUpdate(cmdString);
            result = checkWarning(st1, updateCount);
        } catch (Exception e) {
            result = processSQLError(e);
        }
        return result;
    }


    @Override
    public List<String> getLabels() {
        String query = "SELECT LABEL FROM LABELS";
        List<String> labels = new ArrayList<>();
        try {
            cmdString = query;
            rs = st.executeQuery(cmdString);
            while (rs.next()) {
                labels.add(rs.getString("LABEL"));
            }
            rs.close();
        } catch (Exception e) {
            processSQLError(e);
        }
        return labels;
    }


    @Override
    public String addDay(String userName, int dayOfYear) {
        String query = "INSERT INTO DAYS " +
                "VALUES ('" + userName + "'," + dayOfYear + ");";
        result = null;
        try {
            cmdString = query;
            updateCount = st1.executeUpdate(cmdString);
            result = checkWarning(st1, updateCount);
        } catch (Exception e) {
            result = processSQLError(e);
        }
        return result;
    }

    @Override
    public Day getDay(String userName, int dayOfYear) {
        Day day = new Day(dayOfYear);
        try {
            day.exercises = getDayExercises(userName, dayOfYear);
            day.goals = getDayGoals(userName, dayOfYear);
            day.breakfast = getMealTimeList(BREAKFAST.ordinal(), userName, dayOfYear);
            day.lunch = getMealTimeList(LUNCH.ordinal(), userName, dayOfYear);
            day.dinner = getMealTimeList(DINNER.ordinal(), userName, dayOfYear);
            day.snack = getMealTimeList(SNACK.ordinal(), userName, dayOfYear);
        } catch (Exception e) {
            processSQLError(e);
        }
        return day;
    }

    @Override
    public List<Day> getDays(String userName) {
        String query = "SELECT * FROM DAYS " +
                "WHERE USERNAME = '" + userName + "'";
        Day day;
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

    private ArrayList<Edible> getMealTimeList(int mealTime, String userName, int dayOfYear) throws SQLException {
        String query = "SELECT * " +
                "FROM DAY_EDIBLES AS DE, EDIBLES AS E " +
                "WHERE USERNAME='" + userName + "' AND DAY_OF_YEAR=" + dayOfYear +
                " AND MEAL_TIME=" + mealTime + " AND DE.EDIBLE_NAME = E.EDIBLE_NAME";
        ArrayList<Edible> edibles = new ArrayList<>();
        String name;
        Edible edible;
        boolean isMeal;
        cmdString = query;
        ResultSet resultSet = st.executeQuery(cmdString);
        while (resultSet.next()) {
            isMeal = resultSet.getBoolean("IS_MEAL");
            name = resultSet.getString("EDIBLE_NAME");
            edible = isMeal ? getEntireMeal(name) : getFoodFromRs(resultSet);
            edibles.add(edible);
        }
        resultSet.close();
        return edibles;
    }


    private ArrayList<Goal> getDayGoals(String userName, int dayOfYear) throws SQLException {
        String query = "SELECT * FROM GOALS " +
                "WHERE USERNAME='" + userName + "' AND DAY_OF_YEAR=" + dayOfYear;
        ArrayList<Goal> goals = new ArrayList<>();
        String goalClass, goalId;
        int lowerBound, upperBound;
        Goal.GoalType goalType;
        cmdString = query;
        rs = st.executeQuery(cmdString);
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

    private Goal makeGoal(String clas, String id, Goal.GoalType type, int lower, int upper) {
        Goal goal;
        switch (clas) {
            case "calorie":
                goal = new CalorieGoal(lower, upper);
                break;
            case "label":
                goal = new LabelGoal(lower, upper, type, id);
                break;
            case "micro":
                goal = new MicroGoal(lower, upper, getMicroFromString(id));
                break;
            default:
                goal = new MacroGoal(lower, upper, type, getMacroFromString(id));
                break;
        }
        return goal;
    }

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


    private ArrayList<Exercise> getDayExercises(String userName, int dayOfYear) throws SQLException {
        String query = "SELECT * FROM EXERCISE " +
                "WHERE USERNAME='" + userName + "' AND DAY_OF_YEAR=" + dayOfYear;
        ArrayList<Exercise> exercises = new ArrayList<>();
        String name;
        int duration;
        Exercise.Intensity intensity;
        cmdString = query;
        rs = st.executeQuery(cmdString);
        while (rs.next()) {
            name = rs.getString("EXERCISE_NAME");
            intensity = Exercise.Intensity.values()[rs.getInt("INTENSITY")];
            duration = rs.getInt("DURATION");
            exercises.add(new Exercise(name, duration, intensity));
        }
        rs.close();
        return exercises;
    }

    @Override
    public String updateDay(String userName, Day day) {

        String updateGoalQuery = getUpdateGoalQuery(userName, day);
        String updateExerciseQuery = getUpdateExerciseQuery(userName, day);
        String updateDayEdibleQuery = getUpdateDayEdiblesQuery(userName, day);
        result = null;
        try {
            cmdString = updateGoalQuery + updateExerciseQuery + updateDayEdibleQuery;
            updateCount = st1.executeUpdate(cmdString);
            result = checkWarning(st1, updateCount);
        } catch (Exception e) {
            result = processSQLError(e);
        }
        return result;
    }

    private String getUpdateDayEdiblesQuery(String userName, Day day) {
        String deleteOldFood = "DELETE FROM DAY_EDIBLES " +
                "WHERE USERNAME='" + userName + "' AND DAY_OF_YEAR=" + day.dayOfYear + "; ";
        String insertNewEdibles = getInsertNewMealTimeQuery(userName, BREAKFAST, day);
        insertNewEdibles += getInsertNewMealTimeQuery(userName, LUNCH, day);
        insertNewEdibles += getInsertNewMealTimeQuery(userName, DINNER, day);
        insertNewEdibles += getInsertNewMealTimeQuery(userName, SNACK, day);
        return deleteOldFood + insertNewEdibles;
    }

    private String getInsertNewMealTimeQuery(String userName, Day.MealTimeType mealTime, Day day) {
        List<Edible> list = day.getMealTimeList(mealTime);
        String query = "";
        if (list.size() > 0) {
            query = "INSERT INTO DAY_EDIBLES VALUES";
            for (Edible e : list) {
                query += "( '" + e.name + "', '" + userName + "'," + day.dayOfYear + "," +
                        "" + mealTime.ordinal() + ", " + 1 + "), ";
            }
            query = query.substring(0, query.lastIndexOf(',')) + "; ";
        }
        return query;
    }

    private String getUpdateGoalQuery(String userName, Day day) {
        String deleteOldGoals = "DELETE FROM GOALS " +
                "WHERE USERNAME='" + userName + "' AND DAY_OF_YEAR=" + day.dayOfYear + "; ";
        String insertNewGoals = "";
        if (day.goals.size() > 0) {
            insertNewGoals = "INSERT INTO GOALS VALUES";
            for (Goal goal : day.goals) {
                insertNewGoals += "('" + userName + "'," + day.dayOfYear + ",'" + goal.getClass() + "'," + goal.goalType.ordinal() +
                        ",'" + goal.id + "'," + goal.lowerBound + "," + goal.upperBound + "), ";
            }
            insertNewGoals = insertNewGoals.substring(0, insertNewGoals.lastIndexOf(',')) + "; ";
        }
        return deleteOldGoals + insertNewGoals;
    }

    private String getUpdateExerciseQuery(String userName, Day day) {
        String deleteOldEx = "DELETE FROM EXERCISE " +
                "WHERE USERNAME='" + userName + "' AND DAY_OF_YEAR=" + day.dayOfYear + "; ";
        String insertNewEx = "";
        if (day.exercises.size() > 0) {
            for (Exercise e : day.exercises) {
                insertNewEx = "INSERT INTO EXERCISE VALUES";
                insertNewEx += "('" + userName + "'," + day.dayOfYear + ",'" + e.name + "', " + e.intensity.ordinal() + "," + e.duration +
                        "), ";
            }
            insertNewEx = insertNewEx.substring(0, insertNewEx.lastIndexOf(',')) + "; ";
        }
        return deleteOldEx + insertNewEx;
    }

}