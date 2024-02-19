package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BuildingTest {

    private Building ride;
    private Building bathroom;
    private Building food;

    @BeforeEach
    void runBefore() {
        ride = new Ride("Ride", 100);
        bathroom = new Bathroom("Bathroom", 100);
        food = new FoodStall("Food", 100);
    }

    @Test
    void testConstructor() {
        assertEquals(ride.getLevel(), 0);
        assertEquals(ride.getName(), "Ride");
        assertEquals(ride.getCost(), 100);
        assertEquals(ride.getIncome(), 1);
        assertEquals(ride.getUpgradeCost(), 150);

        assertEquals(bathroom.getLevel(), 0);
        assertEquals(bathroom.getName(), "Bathroom");
        assertEquals(bathroom.getCost(), 100);
        assertEquals(bathroom.getIncome(), 0);
        assertEquals(bathroom.getUpgradeCost(), 150);

        assertEquals(food.getLevel(), 0);
        assertEquals(food.getName(), "Food");
        assertEquals(food.getCost(), 100);
        assertEquals(food.getIncome(), 1);
        assertEquals(food.getUpgradeCost(), 150);
    }

    @Test
    void testUpgrade() {
        ride.upgrade();
        bathroom.upgrade();
        food.upgrade();

        assertEquals(ride.getLevel(), 1);
        assertEquals(ride.getIncome(), 1);
        assertEquals(ride.getUpgradeCost(), 200);

        assertEquals(bathroom.getLevel(), 1);
        assertEquals(bathroom.getIncome(), 0);
        assertEquals(bathroom.getUpgradeCost(), 200);

        assertEquals(food.getLevel(), 1);
        assertEquals(food.getIncome(), 1);
        assertEquals(food.getUpgradeCost(), 200);
    }

    @Test
    void testToString() {
        assertEquals(ride.toString(),
                "Ride - Level 0 ($100) [$1 per second]");
        assertEquals(bathroom.toString(),
                "Bathroom - Level 0 ($100) [$0 per second]");
        assertEquals(food.toString(),
                "Food - Level 0 ($100) [$1 per second]");
    }
}
