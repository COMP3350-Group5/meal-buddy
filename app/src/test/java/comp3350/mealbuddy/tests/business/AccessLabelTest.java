package comp3350.mealbuddy.tests.business;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import comp3350.mealbuddy.application.Main;
import comp3350.mealbuddy.application.Services;
import comp3350.mealbuddy.business.AccessLabel;
import comp3350.mealbuddy.objects.Account;
import comp3350.mealbuddy.objects.UserInfo;

public class AccessLabelTest {
    private static AccessLabel accessLabel;
    private static final String LABEL = "labeltest123";
    @Before
    public void initAccessLabelTests(){
        Services.initializeDB(Main.DATABASE_NAME);
        accessLabel = new AccessLabel();
        accessLabel.addLabel(LABEL);
    }

    @Test
    public void addLabel_nullLabel_throwException(){
        try {
            accessLabel.addLabel(null);
            Assert.fail();
        } catch (NullPointerException npe){
            Assert.assertTrue(true);
        }
    }

    @Test
    public void addLabel_multipleLabels_dontAdd(){
        Assert.assertNotNull(accessLabel.getLabel(LABEL));
        for (int x = 0; x < 100; x++)
            accessLabel.addLabel(LABEL);
        Assert.assertNotNull(accessLabel.getLabel(LABEL));
    }

    @Test
    public void removeLabel_nullLabel_throwException(){
        try {
            accessLabel.removeLabel(null);
            Assert.fail();
        } catch (NullPointerException npe){
            Assert.assertTrue(true);
        }
    }

    @Test
    public void removeLabel_labelDoesntExist_throwException(){
        try {
            accessLabel.removeLabel("label doesnt exist");
            Assert.fail();
        } catch (IllegalArgumentException npe){
            Assert.assertTrue(true);
        }
    }

    @Test
    public void updateAccount_labelAlreadyExists_throwException(){
        String s = "new label";
        accessLabel.addLabel(s);
        try {
            accessLabel.updateLabel(s, LABEL);
            Assert.fail();
        } catch(IllegalArgumentException IAE){
            Assert.assertTrue(true);
        }
    }

    @Test
    public void updateLabel_labelDoesntExist_throwException(){
        try {
            //confirm that it doesnt exist
            Assert.assertNull(accessLabel.getLabel("Idontexist"));
            //trying to update an account that doesnt exist
            accessLabel.updateLabel("Idontexist", "newLabel");
            Assert.fail();
        } catch(IllegalArgumentException iae){
            //confirm that it wasnt added
            Assert.assertNull(accessLabel.getLabel("Idontexist"));
        }
    }

    @Test
    public void updateLabel_nullValues_throwException(){
        try {
            accessLabel.updateLabel(null, null);
            Assert.fail();
        } catch(NullPointerException iae){
            Assert.assertTrue(true);
        }
        try {
            accessLabel.updateLabel(null, "null");
            Assert.fail();
        } catch(NullPointerException iae){
            Assert.assertTrue(true);
        }
        try {
            accessLabel.updateLabel("null", null);
            Assert.fail();
        } catch(NullPointerException iae){
            Assert.assertTrue(true);
        }
    }

    @Test
    public void getLabel_nullValue_throwException(){
        try {
            accessLabel.getLabel(null);
            Assert.fail();
        } catch(NullPointerException iae){
            Assert.assertTrue(true);
        }
    }

}
