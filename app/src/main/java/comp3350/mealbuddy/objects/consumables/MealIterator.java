package comp3350.mealbuddy.objects.consumables;

import java.util.ArrayList;
import java.util.Iterator;

public class MealIterator implements Iterator<Edible> {

    private Iterator<EdibleIntPair> edibleIntPairIterator;

    public MealIterator(ArrayList<EdibleIntPair> list) {
        edibleIntPairIterator = list.listIterator();
    }

    @Override
    public boolean hasNext() {
        return edibleIntPairIterator.hasNext();
    }

    @Override
    public Edible next() {
        Edible edible = null;
        if (edibleIntPairIterator.hasNext()) {
            edible = edibleIntPairIterator.next().edible;
        }
        return edible;
    }

}
