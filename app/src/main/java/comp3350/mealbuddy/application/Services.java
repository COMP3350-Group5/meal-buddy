package comp3350.mealbuddy.application;

import comp3350.mealbuddy.persistence.DataAccess;
import comp3350.mealbuddy.persistence.DataAccessObject;

public class Services {
    private static DataAccess DAS = null;

    public static DataAccess createDataAccess(String databaseName){
        if (DAS == null){
            DAS = new DataAccessObject(databaseName);
            DAS.open(databaseName);
        }
        return DAS;
    }

    public static DataAccess createDataAccess(DataAccess alternateDataAccessService)
    {
        if (DAS == null)
        {
            DAS = alternateDataAccessService;
            DAS.open(Main.getDBPathName());
        }
        return DAS;
    }

    public static DataAccess getDataAccess(String databaseName){
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
