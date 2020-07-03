package comp3350.mealbuddy.objects;


import java.util.EnumMap;

public class Macros {


    private static EnumMap<nutrients, Integer>dictionary;

    public enum nutrients{
        OMEGA3, OMEGA6, SATURATEDFAT, PROTEIN, CARBOHYDRATES;
    }

    public static EnumMap<nutrients, Integer> makeMacros() {
        dictionary = new EnumMap<nutrients, Integer>(nutrients.class);
        return dictionary;
    }
}
