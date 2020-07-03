package comp3350.mealbuddy.objects;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.EnumMap;
import java.util.Enumeration;
import java.util.Hashtable;

public class Food {


    private String name;

    private ArrayList<String>labels;

    private EnumMap macroDictionary;

    private EnumMap microDictionary;

    public Food (String name) {
        this.name = name;
        labels = new ArrayList<String>();
        macroDictionary = Macros.makeMacros();
        microDictionary = Micros.makeMicros();

    }

    public void addLabel(String label) throws Exception {
        if (Labels.getLabel().contains(label)) {
            labels.add(label);
        } else {
            throw new Exception("Label is not in the list of Labels");
        }
    }


    public void removeLabel(String label) throws Exception {
        if (Labels.getLabel().contains(label)) {
            labels.remove(label);
        } else {
            throw new Exception("Label is not in the list of Labels");
        }
    }

    public String getName() {
        return name;
    }
}
