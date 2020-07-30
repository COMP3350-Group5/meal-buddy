package comp3350.mealbuddy.application;

public class Main {

    public static final String DATABASE_NAME = "mealBuddyDB";
    private static String dbPathName = "database/mealBuddyDB";

    public static void main(String[] args) {
        // do stuff
    }

    public static void startUp() {
        Services.createDataAccess(DATABASE_NAME);
    }

    public static void shutDown() {
        Services.closeDAS();
    }


    public static String getDBPathName() {
        if (dbPathName == null)
            return DATABASE_NAME;
        else
            return dbPathName;
    }

    public static void setDBPathName(String pathName) {
        System.out.println("Setting DB path to:" + pathName);
        dbPathName = pathName;
    }

}