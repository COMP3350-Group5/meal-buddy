package comp3350.mealbuddy.objects.consumables;


import java.util.EnumMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public abstract class Edible {

    public String name;
    public List<String> labels;
    private Map<Micros, Integer> micros;
    private Map<Macros, Integer> macros;

    public enum Macros{
        Fat, Carbohydrates, Protein, Alcohol
    }

    public enum Micros{
        Iron, Zinc, VitaminA, VitaminB12, VitaminC,  VitaminE,
        Calcium, Choline, Magnesium, Sodium, Potassium, Niacin
    }

    public Edible(String name, List<String> labels) {
        if(name == null || labels == null)
            throw new NullPointerException("Name and labels can't be null");
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

    public int getMicroAmount(Micros foodMicro) {
        int microAmount = 0;
        if (micros.containsKey(foodMicro)) {
            microAmount = micros.get(foodMicro);
        }
        return microAmount;
    }

    public int getMacroAmount(Macros foodMacro) {
        int macroAmount = 0;
        if (macros.containsKey(foodMacro)) {
            macroAmount = macros.get(foodMacro);
        }
        return macroAmount;
    }

    public void addMacroAmount(Macros foodMacro, int quantity) {
        if (macros.containsKey(foodMacro)) {
            int oldMacroAmount = micros.get(foodMacro);
            int newMacroAmount = oldMacroAmount + quantity;
            macros.remove(foodMacro);
            updateMacro(foodMacro, newMacroAmount);
        }
    }


    public void addMicroAmount(Micros foodMicro, int quantity) {
        if (micros.containsKey(foodMicro)) {
            int oldMicroAmount = micros.get(foodMicro);
            int newMicroAmount = oldMicroAmount + quantity;
            micros.remove(foodMicro);
            updateMicro(foodMicro, newMicroAmount);
        }
    }

    public void removeMacroAmount(Macros foodMacro, int quantity) {
        if (macros.containsKey(foodMacro)) {
            int oldMacroAmount = micros.get(foodMacro);
            int newMacroAmount = oldMacroAmount - quantity;
            macros.remove(foodMacro);
            updateMacro(foodMacro, newMacroAmount);
        }
    }

    public void removeMicroAmount(Micros foodMicro, int quantity) {
        if (micros.containsKey(foodMicro)) {
            int oldMacroAmount = micros.get(foodMicro);
            int newMacroAmount = oldMacroAmount - quantity;
            micros.remove(foodMicro);
            updateMicro(foodMicro, newMacroAmount);
        }
    }

    public boolean containsKey(Micros micro) {
        if (!micros.containsKey(micro)) {
            return false;
        }
        return true;
    }

    public boolean containsKey(Macros macro) {
        if (!macros.containsKey(macro)) {
            return false;
        }
        return true;
    }
}
