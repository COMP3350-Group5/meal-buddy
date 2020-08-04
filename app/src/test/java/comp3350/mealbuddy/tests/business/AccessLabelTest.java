package comp3350.mealbuddy.tests.business;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import comp3350.mealbuddy.application.Services;
import comp3350.mealbuddy.business.AccessLabel;
import comp3350.mealbuddy.tests.persistence.DataAccessStub;

public class AccessLabelTest {
    private static final String LABEL = "labeltest123";
    private static AccessLabel accessLabel;

    @Before
    public void initAccessLabelTests() {
        Services.createDataAccess(new DataAccessStub("Stub"));  //stub
        //Services.createDataAccess(Main.DATABASE_NAME);    //hsql
        accessLabel = new AccessLabel();
        accessLabel.addLabel(LABEL);
    }

    @Test
    public void addLabel_nullLabel_throwException() {
        try {
            accessLabel.addLabel(null);
            Assert.fail();
        } catch (NullPointerException npe) {
            Assert.assertTrue(true);
        }
    }


    @Test
    public void removeLabel_nullLabel_throwException() {
        try {
            accessLabel.removeLabel(null);
            Assert.fail();
        } catch (NullPointerException npe) {
            Assert.assertTrue(true);
        }
    }

    @Test
    public void removeLabel_labelDoesntExist_throwException() {
        try {
            accessLabel.removeLabel("label doesnt exist");
            Assert.fail();
        } catch (IllegalArgumentException npe) {
            Assert.assertTrue(true);
        }
    }

    @Test
    public void updateAccount_labelAlreadyExists_throwException() {
        String s = "new label";
        accessLabel.addLabel(s);
        try {
            accessLabel.updateLabel(s, LABEL);
            Assert.fail();
        } catch (IllegalArgumentException IAE) {
            Assert.assertTrue(true);
        }
    }


    @Test
    public void updateLabel_nullValues_throwException() {
        try {
            accessLabel.updateLabel(null, null);
            Assert.fail();
        } catch (NullPointerException iae) {
            Assert.assertTrue(true);
        }
        try {
            accessLabel.updateLabel(null, "null");
            Assert.fail();
        } catch (NullPointerException iae) {
            Assert.assertTrue(true);
        }
        try {
            accessLabel.updateLabel("null", null);
            Assert.fail();
        } catch (NullPointerException iae) {
            Assert.assertTrue(true);
        }
    }


    @After
    public void clean() {
        try {
            accessLabel.removeLabel(LABEL);
        } catch (IllegalArgumentException iae) {
        }
        Services.closeDAS();
    }

}
