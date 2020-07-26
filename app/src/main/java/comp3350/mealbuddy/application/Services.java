package comp3350.mealbuddy.application;

import comp3350.mealbuddy.persistence.DataAccessStub;

public class Services {
    private static DataAccessStub DAS = null;

    public static DataAccessStub openDAS(String databaseName){
        if (DAS == null){
            DAS = new DataAccessStub(databaseName);
            DAS.open();
        }
        return DAS;
    }

    public static DataAccessStub getDataAccess(String databaseName){
        if (DAS == null){
            System.out.println("Database not instantiated.");
            System.exit(1);
        }
        return DAS;
    }

    public static void closeDAS() {
        if (DAS != null)
            DAS.close();
        DAS = null;
    }


}
