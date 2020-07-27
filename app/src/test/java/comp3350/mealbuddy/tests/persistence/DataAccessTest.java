package comp3350.mealbuddy.tests.persistence;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import comp3350.mealbuddy.objects.Account;
import comp3350.mealbuddy.objects.UserInfo;
import comp3350.mealbuddy.objects.consumables.Edible;
import comp3350.mealbuddy.objects.consumables.Food;
import comp3350.mealbuddy.persistence.DataAccess;
import comp3350.mealbuddy.persistence.DataAccessStub;

public class DataAccessTest {

    private static DataAccess database = new DataAccessStub("TestDB");

    @Before
    public void initializeDatabase() {
        database.open("TestDB");
    }


    @Test
    public void removeAccount() {
        // Arrange
        Account removedAccount = database.getAccount("stoneheart");

        // Act
        database.removeAccount("stoneheart");

        //Assert
        Assert.assertNull(database.getAccount("stoneheart"));
        database.addAccount(removedAccount);
    }

    @Test
    public void addAccount() {
        // Arrange
        Account newAccount = new Account(new UserInfo("Elon Musk", "MuskyBoi", "T3sla", 280.0, 170.5, UserInfo.ActivityLevel.LOW, UserInfo.Sex.MALE, 40));

        // Act
        database.addAccount(newAccount);

        //Assert
        Assert.assertEquals(newAccount, database.getAccount("MuskyBoi"));
        database.removeAccount("MuskyBoi");
    }

    @Test
    public void updateAccount() {
        // Arrange
        Account newAccount = new Account(new UserInfo( "Richard Hendricks", "piedpiper", "alwaysblue", 100.0, 180.0, UserInfo.ActivityLevel.LOW, UserInfo.Sex.MALE, 35));
        Account oldAccount = database.getAccount("piedpiper");

        // Act
        database.updateAccount("piedpiper", newAccount);
        Account updatedAccount = database.getAccount("piedpiper");

        //Assert
        Assert.assertEquals(newAccount, updatedAccount);
        database.removeAccount("piedpiper");
        database.addAccount(oldAccount);
    }

    @Test
    public void removeEdible() {
        // Arrange


        // Act

        //Assert

    }

    @Test
    public void addEdible() {
        // Arrange
        Food newFood = new Food("Taco", new ArrayList<String>());

        // Act
        database.addEdible(newFood);
        List<Edible> edibles = database.getEdibles();

        //Assert
        database.removeEdible("Taco");
        Assert.assertNotEquals(-1, edibles.indexOf(newFood));

    }

}

// Arrange

// Act

//Assert
