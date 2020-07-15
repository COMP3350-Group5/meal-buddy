package comp3350.mealbuddy.objects.consumables;

import java.util.Iterator;

public class FoodIterator implements Iterator<Edible> {

    private boolean finishedIteration = false;
    private Food food;

    public FoodIterator(Food food) {
        this.food = food;
    }

    @Override
    public boolean hasNext() {
        return !finishedIteration;
    }

    @Override
    public Edible next() {
        if (!finishedIteration) {
            finishedIteration = true;
            return food;
        }
        return null;
    }

}
