package comp3350.mealbuddy.objects;


import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public abstract class Edible {

    public String name;
    public List<String> labels;
    public Map<Micros, Integer> micros;
    public Map<Macros, Integer> macros;

    public enum Macros{
        Fat, Omega6, Omega3, Unsaturated, Saturated, Trans, Carbohydrates, Sugar, Fibre, Protein, Alcohol
    }

    public enum Micros{
        Iron, Zinc, VitaminA, VitaminB12, VitaminC, VitaminD, VitaminE, VitaminK,
        Calcium, Choline, Magnesium, Sodium, Potassium, Iodine
    }

    public Edible(String name, List<String> labels) {
        this.name = name;
        this.labels = labels;
        micros = makeMicros();
        macros = makeMacros();
    }

    private static Map<Macros, Integer> makeMacros(){
        Map<Macros, Integer> macroMap = new EnumMap<>(Macros.class);
        Macros[] microArr = Macros.values();
        for (Macros macro:microArr) {
            macroMap.put(macro, 0);
        }
        return macroMap;
    }

    private static Map<Micros, Integer> makeMicros(){
        Map<Micros, Integer> microMap = new EnumMap<>(Micros.class);
        Micros[] microArr = Micros.values();
        for (Micros micro:microArr) {
            microMap.put(micro, 0);
        }
        return microMap;
    }
}
