package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BathroomTest {

    private Bathroom bathroom;

    @BeforeEach
    void runBefore() {
        bathroom = new Bathroom("Bath1", 100);

    }

    @Test
    void testConstructor() {
        assertEquals(bathroom.getName(), "Bath1");
        assertEquals(bathroom.getCost(), 100);
        assertEquals(bathroom.getIncome(), 0);
    }

    @Test
    void testUpgrade() {
        bathroom.upgrade();
        assertEquals(bathroom.getLevel(), 1);
        assertEquals(bathroom.getIncome(), 0);
        assertEquals(bathroom.getUpgradeCost(), 200);
        assertEquals(bathroom.getWashroomCap(), 2);
    }
}
