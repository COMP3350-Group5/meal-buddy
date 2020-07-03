package comp3350.mealbuddy.objects;

import java.util.EnumMap;

public class Micros {
    

    private static EnumMap<Micros.nutrients, Integer>dictionary;

    public enum nutrients {
        VITAMINA, VITAMINE, VITAMIND, VITAMINC, THIAMINE, RIBOFLAVIN, NIACIN,
        VITAMINB6, VITAMINB12, CHOLINE, VITAMINK, FOLATE, CALCIUM, IRON, MAGNESIUM,
        PHOSPHORUS, POTTASIUM, SODIUM, ZINC, COPPER, MANGANESE, SELENIUM;
    }

    public static EnumMap<Micros.nutrients, Integer> makeMicros() {
        dictionary = new EnumMap<nutrients, Integer>(nutrients.class);
        return dictionary;
    }
}
