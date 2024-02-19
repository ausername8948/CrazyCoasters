package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FoodTest {

    private FoodStall food;

    @BeforeEach
    void runBefore() {
        food = new FoodStall("Food", 100);
    }

    @Test
    void testConstructor() {
        assertEquals(food.getName(), "Food");
        assertEquals(food.getCost(), 100);
        assertEquals(food.getIncome(), 1);
        assertEquals(food.getFoodFed(), 1);
    }
}
