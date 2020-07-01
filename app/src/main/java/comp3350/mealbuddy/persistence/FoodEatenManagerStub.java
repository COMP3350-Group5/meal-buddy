package comp3350.mealbuddy.persistence;

public class FoodEatenManagerStub implements IFoodEatenManager {
    int amount;
int total;

    public FoodEatenManagerStub(int amount, int total) {
        this.amount = amount;
        this.total = total;
    }
    public FoodEatenManagerStub(int amount) {
        this.amount = amount;
        this.total = 0;
    }

    @Override
    public int getAmount(String name) {
        return amount;
    }

    @Override
    public int getTotalAmount() {
        return total;
    }


}
