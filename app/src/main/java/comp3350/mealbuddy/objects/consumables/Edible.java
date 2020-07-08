package comp3350.mealbuddy.objects.consumables;


import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public abstract class Edible {

    public String name;
    public List<String> labels;
    public Map<Micros, Integer> micros;
    public Map<Macros, Integer> macros;

    public enum Macros{
        Fat, Carbohydrates, Protein, Alcohol
    }

    public enum Micros{
        Iron, Zinc, VitaminA, VitaminB12, VitaminC,  VitaminE,
        Calcium, Choline, Magnesium, Sodium, Potassium, Niacin
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

    public void updateMacro(Macros macro, int amount){
        if(amount < 0 )
            amount = 0;
        macros.put(macro, amount);
    }

    public void updateMicro(Micros micro, int amount){
        if(amount < 0 )
            amount = 0;
        micros.put(micro, amount);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (! (o instanceof Edible)) return false;
        Edible edible = (Edible) o;
        return name.equals(edible.name) ;
    }
}
