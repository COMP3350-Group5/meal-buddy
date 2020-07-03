package comp3350.mealbuddy.objects;

import java.util.EnumMap;
import java.util.Map;

import javax.crypto.Mac;

public class MacroFactory {


    public enum Macros{
        Fat, Omega6, Omega3, Saturated, Trans, Carbs, Sugar, Fibre, Protein
    }

    private MacroFactory(){}    //dont want to instantiate

    public static Map< Macros, Integer> makeMacros(){
        Map<Macros, Integer> macroMap = new EnumMap<>(Macros.class);
        Macros[] microArr = Macros.values();
        for (Macros macro:microArr) {
            macroMap.put(macro, 0);
        }
        return macroMap;
    }

}
