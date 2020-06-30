package comp3350.mealbuddy.business;

import comp3350.mealbuddy.persistence.IFoodDb;

public abstract class Trackable {
    protected int targetAmount;
    protected int variance;
    protected String name;
    protected int currentAmount;
    protected IFoodDb foodDb;

    public abstract boolean goalAchieved();


    public Trackable(int targetAmount, int variance, String name, IFoodDb foodDb) {
        validateInput(targetAmount, variance, name);

        this.targetAmount = targetAmount;
        this.variance = variance;
        this.name = name;
        this.foodDb = foodDb;
        ///todo integrate wiht actual stub
    }

    private void validateInput(int amount, int variance, String name){
        if(amount<0){
            throw new IllegalArgumentException("Amount can not be "+amount);
        }
        if(variance<0){
            throw new IllegalArgumentException("Amount can not be "+amount);
        }
        if(name == null){
            throw new NullPointerException("Name can not be null for trackable item");
        }
        if(variance > amount){
            throw new IllegalArgumentException("Variance can not exceed target amount");
        }
        ///todo test for null db
    }



}
