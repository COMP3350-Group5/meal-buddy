package comp3350.mealbuddy.objects;

import java.util.ArrayList;
import java.util.List;

public class Meal extends Edible{

    List<Food> foodList;

    public Meal(String name, List<String> labels) {
        super(name, labels);
        foodList = new ArrayList<>();
    }

}
