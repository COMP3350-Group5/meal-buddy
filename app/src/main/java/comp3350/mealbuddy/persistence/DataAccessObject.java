package comp3350.mealbuddy.persistence;

import android.provider.ContactsContract;

import java.sql.SQLWarning;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.util.ArrayList;

import comp3350.mealbuddy.objects.Account;
import comp3350.mealbuddy.objects.Day;
import comp3350.mealbuddy.objects.Exercise;
import comp3350.mealbuddy.objects.consumables.Edible;

public class DataAccessObject implements DataAccess {

    private Statement st1, st2, st3;
    private Connection c1;
    private ResultSet rs2, rs3, rs4, rs5;

    private String dbName;
    private DBType dbType;

    private ArrayList<Account> accounts;
    private ArrayList<Edible> edible;
    private ArrayList<Exercise> exercise;

    private String cmdString;
    private int updateCount;
    private String result;
    private static String EOF = "  ";

    public DataAccessObject(String dbName){
        this.dbName = dbName;
        dbType = DBType.HSQLDB;
    }

    @Override
    public void open(String dbPath) {
        String url;
        try {
            // Setup for HSQL
            Class.forName("org.hsqldb.jdbcDriver").newInstance();
            url = "jdbc:hsqldb:file:" + dbPath; // stored on disk mode
            System.out.println("**********************************");
            System.out.println(url);
            c1 = DriverManager.getConnection(url, "SA", "");
            st1 = c1.createStatement();
            st2 = c1.createStatement();
            st3 = c1.createStatement();

        }
        catch (Exception e)
        {
            processSQLError(e);
        }
        System.out.println("Opened " +dbType +" database " +dbPath);
    }

    @Override
    public void close() {
        try
        {	// commit all changes to the database
            cmdString = "shutdown compact";
            rs2 = st1.executeQuery(cmdString);
            c1.close();
        }
        catch (Exception e)
        {
            processSQLError(e);
        }
        System.out.println("Closed " +dbType +" database " +dbName);
    }

    @Override
    public String insertStudent()
    {
        String values;

        result = null;
        try
        {
            values = 24242424 +", '" + "ADFASDF"
                    +"', '" + "SDFASDFADSFASDFA"
                    +"'";
            cmdString = "Insert into Students " +" Values(" +values +")";
            //System.out.println(cmdString);
            updateCount = st1.executeUpdate(cmdString);
            result = checkWarning(st1, updateCount);
        }
        catch (Exception e)
        {
            result = processSQLError(e);
        }
        return result;
    }

    @Override
    public void addAccount(Account account) {

    }

    @Override
    public void updateAccount(Account account) {

    }

    @Override
    public void removeAccount(Account account) {

    }

    @Override
    public Account getAccount(String username) {
        return null;
    }

    @Override
    public Account validateLogin(String username, String password) {
        return null;
    }

    @Override
    public void addEdible(Edible edible) {

    }

    @Override
    public void updateEdible(Edible edible) {

    }

    @Override
    public void removeEdible(Edible edible) {

    }

    @Override
    public void getEdible(Edible edible) {

    }

    @Override
    public void addExercise(String userName, int dayOfYear, Exercise exercise) {

    }

    @Override
    public void updateExercise(String userName, int dayOfYear, Exercise exercise) {

    }

    @Override
    public void removeExercise(String userName, int dayOfYear, Exercise exercise) {

    }

    @Override
    public Day getDay(String userName, int dayOfYear) {
        return null;
    }

    @Override
    public Day updateDay(String userName, int dayOfYear) {
        return null;
    }


    public String checkWarning(Statement st, int updateCount)
    {
        String result;

        result = null;
        try
        {
            SQLWarning warning = st.getWarnings();
            if (warning != null)
            {
                result = warning.getMessage();
            }
        }
        catch (Exception e)
        {
            result = processSQLError(e);
        }
        if (updateCount != 1)
        {
            result = "Tuple not inserted correctly.";
        }
        return result;
    }
    public String processSQLError(Exception e)
    {
        String result = "*** SQL Error: " + e.getMessage();

        // Remember, this will NOT be seen by the user!
        e.printStackTrace();

        return result;
    }
}