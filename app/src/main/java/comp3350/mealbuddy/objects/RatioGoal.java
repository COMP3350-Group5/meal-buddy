package comp3350.mealbuddy.objects;

public class RatioGoal extends Goal {
    private int totalCalories;

    public RatioGoal(int lowerBound, int upperBound, String nameOfTracked) {
        super(lowerBound, upperBound, nameOfTracked);
        if(lowerBound == upperBound){
            throw new IllegalArgumentException("A perent goal must have at least 1 percantage of buffer");
        }
    }

}
