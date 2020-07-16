package comp3350.mealbuddy.persistence;

import android.provider.ContactsContract;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.sql.SQLWarning;
import java.sql.DatabaseMetaData;
import java.util.ArrayList;
import java.util.List;

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
    public void addAccount(Account acc) {

    }

    @Override
    public void updateAccount(Account a) {

    }

    @Override
    public void removeAccount(Account a) {

    }

    @Override
    public void addEdible(Edible acc) {

    }

    @Override
    public void updateEdible(Edible a) {

    }

    @Override
    public void removeEdible(Edible a) {

    }

    @Override
    public void addExercise(Exercise acc) {

    }

    @Override
    public void updateExercise(Exercise a) {

    }

    @Override
    public void removeExercise(Exercise a) {

    }

    @Override
    public Account validateLogin(String username, String password) {

        return null;
    }

    @Override
    public Day getDay(Account a, int day) {
        return null;
    }

    @Override
    public Account getAccount(String username) {
        return null;
    }

    public String processSQLError(Exception e)
    {
        String result = "*** SQL Error: " + e.getMessage();

        // Remember, this will NOT be seen by the user!
        e.printStackTrace();

        return result;
    }
}