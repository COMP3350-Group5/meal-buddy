package comp3350.mealbuddy.objects;


import java.util.List;
import java.util.Map;

import comp3350.mealbuddy.objects.MacroFactory.Macros;
import comp3350.mealbuddy.objects.MicroFactory.Micros;

public abstract class Edible {

    public String name;
    public List<String> labels;
    public Map<Micros, Integer> micros;
    public Map<Macros, Integer> macros;

    public Edible(String name, List<String> labels) {
        this.name = name;
        this.labels = labels;
        micros = MicroFactory.makeMicros();
        macros = MacroFactory.makeMacros();
    }

}
