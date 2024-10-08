package persistence;


import model.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {

    protected void checkBuilding(String name, int income, int cost, int upgradeCost, int level, Building b) {
        assertEquals(name, b.getName());
        assertEquals(income, b.getIncome());
        assertEquals(cost, b.getCost());
        assertEquals(upgradeCost, b.getUpgradeCost());
        assertEquals(level, b.getLevel());
    }

    protected void checkRide
            (String name, int income, int cost, int upgradeCost, int level, String category, Ride r) {
        checkBuilding(name, income, cost, upgradeCost, level, r);
    }
    protected void checkBathroom
            (String name, int income, int cost,
             int upgradeCost, int level, int washroomCap, String category, Bathroom b) {
        assertEquals(washroomCap, b.getWashroomCap());
        checkBuilding(name, income, cost, upgradeCost, level, b);
    }

    protected void checkFoodStall
            (String name, int income, int cost, int upgradeCost, int level, int foodFed, String category, FoodStall f) {
        assertEquals(foodFed, f.getFoodFed());
        checkBuilding(name, income, cost, upgradeCost, level, f);
    }


}
