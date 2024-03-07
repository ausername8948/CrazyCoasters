package model;

import org.json.JSONObject;

// represents a FoodStall at the amusement park, with "foodFed" representing the amount of people it feeds
public class FoodStall extends Building {
    private int foodFed;

    //EFFECTS: instantiates a new FoodStall, with "String name" name and "int cost" cost, foodFed is set to
    // (Math.log10(cost) - 1)
    public FoodStall(String name, int cost) {
        super(name, cost);
        foodFed = (int) (Math.log10(cost) - 1);
    }

    public int getFoodFed() {
        return foodFed;
    }

    public void setFoodFed(int foodFed) {
        this.foodFed = foodFed;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = super.toJson();
        json.put("foodFed", foodFed);
        json.put("category", "food");
        return json;
    }
}
