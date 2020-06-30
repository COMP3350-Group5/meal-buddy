package comp3350.mealbuddy.persistence;

public class FoodDb implements IFoodDb {
int amount;

    public FoodDb(int amount) {
        this.amount = amount;
    }

    @Override
    public int getAmount() {
        return amount;
    }
}
