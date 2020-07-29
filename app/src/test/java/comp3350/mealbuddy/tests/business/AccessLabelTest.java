package comp3350.mealbuddy.tests.business;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import comp3350.mealbuddy.application.Main;
import comp3350.mealbuddy.application.Services;
import comp3350.mealbuddy.business.AccessLabel;

public class AccessLabelTest {
    private static AccessLabel accessLabel;
    private static final String LABEL = "labeltest123";
    @Before
    public void initAccessLabelTests(){
        Services.initializeDB(Main.DATABASE_NAME);
        accessLabel = new AccessLabel();
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

    
}
