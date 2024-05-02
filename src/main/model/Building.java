package model;


import org.json.JSONObject;
import persistence.Writable;

// represents a building can be one of: Ride, FoodStall, Bathroom
public abstract class Building implements Writable {
    protected int level;
    protected int upgradeCost;
    protected int cost;

    protected int income;

    protected String name;


    //EFFECTS: creates a new building, with "String name" name and "int cost" cost
    // sets the level to 0, income to cost/100, and upgrade cost to cost + cost/2
    public Building(String name, int cost) {
        this.level = 0;
        this.name = name;
        this.cost = cost;
        this.income = cost / 10;
        this.upgradeCost = cost + (this.cost / 2);
    }

    //MODIFIES: this
    //EFFECTS: upgrades the building, increasing its level by one, multiplying its income by its level,
    // and raising its upgrade cost by cost/2
    public void upgrade() {
        this.level++;
        this.income *= level;
        this.upgradeCost += (this.cost / 2);

    }

    //EFFECTS: A string of the details of the building, including its name, level, cost, income, and upgrade cost
    @Override
    public String toString() {
        String stats;
        if (level == 0) {
            stats = this.name + " - Level " + this.level
                    + " ($" + this.cost + ")" + " [$" + this.income + " per second]";
        } else {
            stats = this.name + " - Level " + this.level + " ($" + this.upgradeCost + " to upgrade)"
                    + " [$" + this.income + " per second]";
        }
        return stats;
    }

    //Getters
    public int getCost() {
        return this.cost;
    }

    public int getUpgradeCost() {
        return this.upgradeCost;
    }

    public String getName() {
        return this.name;
    }

    public int getIncome() {
        return this.income;
    }

    public int getLevel() {
        return this.level;
    }

    //Setter(s)
    public void setLevel(int level) {
        this.level = level;
    }

    public void setUpgradeCost(int upgradeCost) {
        this.upgradeCost = upgradeCost;
    }

    public void setIncome(int income) {
        this.income = income;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("level", level);
        json.put("upgradeCost", upgradeCost);
        json.put("cost", cost);
        json.put("income", income);
        json.put("name", name);
        return json;
    }

}
