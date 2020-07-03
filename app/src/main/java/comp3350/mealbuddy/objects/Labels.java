package comp3350.mealbuddy.objects;

import java.util.ArrayList;

public class Labels {
    private static final ArrayList<String>label = new ArrayList<String>();

    private Labels() {
        throw new UnsupportedOperationException();
    }

    public static void defaultLabel() {
        label.add("vegetarian");
        label.add("dairy");
        label.add("keto");
        label.add("vegan");
    }


    public void add(String theLabel) {
        if (!label.contains(theLabel)) {
            label.add(theLabel);
        }
    }

    public void remove(String theLabel) {
        if (label.contains(theLabel)) {
            label.remove(theLabel);
        }
    }

    public static ArrayList<String>getLabel() {
        return label;
    }
}
