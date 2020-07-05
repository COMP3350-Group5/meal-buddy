package comp3350.mealbuddy.objects;


import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public abstract class Edible {

    public String name;
    public List<String> labels;
    public Map<Micros, Integer> micros;
    public Map<Macros, Integer> macros;

    public enum Macros{
        Fat, Omega6, Omega3, Saturated, Trans, Carbohydrates, Sugar, Fibre, Protein,
    }

    public enum Micros{
        Iron, Zinc, VitaminA, VitaminB12, VitaminC, Choline,
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || ! (o instanceof Edible)) return false;
        Edible edible = (Edible) o;
        return name == edible.name ;
    }
}
