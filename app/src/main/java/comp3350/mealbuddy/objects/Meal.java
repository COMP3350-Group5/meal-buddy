package comp3350.mealbuddy.objects;

import java.util.ArrayList;

public class Meal {

    private String name;
    private ArrayList<Food>food;


    public Meal(String name) {
        this.name = name;
        food = new ArrayList<Food>();
    }

    public void add(Food foods) {
        if (!food.contains(foods)) {
            food.add(foods);
        }
    }
}
