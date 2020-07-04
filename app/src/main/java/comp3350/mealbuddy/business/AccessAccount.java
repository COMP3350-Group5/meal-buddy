package comp3350.mealbuddy.business;

import comp3350.mealbuddy.application.Main;
import comp3350.mealbuddy.application.Services;
import comp3350.mealbuddy.objects.Account;
import comp3350.mealbuddy.objects.UserInfo;
import comp3350.mealbuddy.persistence.DataAccessStub;

public class AccessAccount {
    private DataAccessStub DAS;
    private DataAccessStub.Database_t database_t;

    public AccessAccount(){
        DAS = Services.openDAS(Main.DATABASE_NAME);
        database_t = DataAccessStub.Database_t.ACCOUNTS;
    }

    public void addAccount(Account a){
        DAS.addToDB(database_t, a);
    }

    //in case you want to build an account from user info
    public void addAccount(UserInfo u){
        addAccount(new Account(u));
    }

    public void updateAccount(Account a){
        DAS.updateDB(database_t, a);
    }

    public void removeAccount(Account a){
        DAS.removeFromDB(database_t, a);
    }

    public Account validateLogin(String username, String password){
        return DAS.validateLogin(username, password);
    }

}
