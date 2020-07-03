package comp3350.mealbuddy.objects;

import java.io.InterruptedIOException;
import java.util.EnumMap;
import java.util.Map;

public class MicroFactory {

    public enum Micros{
        Iron, Zinc, VitaminA, VitaminB12, VitaminC, Choline
    }

    private MicroFactory(){}    //dont want to instantiate

    public static Map<Micros, Integer> makeMicros(){
        Map<Micros, Integer> microMap = new EnumMap<>(Micros.class);
        Micros[] microArr = Micros.values();
        for (Micros micro:microArr) {
            microMap.put(micro, 0);
        }
        return microMap;
    }


}
