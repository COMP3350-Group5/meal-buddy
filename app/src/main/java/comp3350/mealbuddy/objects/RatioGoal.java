package comp3350.mealbuddy.objects;

public class RatioGoal extends Goal {
    private int totalCalories;

    public RatioGoal(int targetAmount, int variance, String nameOfTracked) {
        super(targetAmount, variance, nameOfTracked);
        if(variance < 1){
            this.variance = 1;  //To avoid floating point inaccuracies
        }
    }

}
