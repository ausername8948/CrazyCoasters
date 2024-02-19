package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RideTest {

    private Ride ride;

    @BeforeEach
    void runBefore() {
        ride = new Ride("Ride", 100);
    }

    @Test
    void testConstructor() {
        assertEquals(ride.getName(), "Ride");
        assertEquals(ride.getCost(), 100);
        assertEquals(ride.getIncome(), 1);
    }
}
