package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ShopTest {

    private Shop shop;


    @BeforeEach
    void runBefore() {
        shop = new Shop();
    }

    @Test
    void testConstructor() {
        assertEquals(shop.getRides().size(), 5);
        assertEquals(shop.getBathrooms().size(), 5);
        assertEquals(shop.getFoods().size(), 5);
    }

    @Test
    void testToString() {
        assertEquals(shop.toString("ride"),
                "[1] Ride1 - Level 0 ($100) [$1 per second]\n" +
                        "[2] Ride2 - Level 0 ($1000) [$10 per second]\n" +
                        "[3] Ride3 - Level 0 ($10000) [$100 per second]\n" +
                        "[4] Ride4 - Level 0 ($100000) [$1000 per second]\n" +
                        "[5] Ride5 - Level 0 ($1000000) [$10000 per second]\n");
        assertEquals(shop.toString("bathroom"),
                "[1] Bath1 - Level 0 ($100) [$0 per second]\n" +
                        "[2] Bath2 - Level 0 ($1000) [$0 per second]\n" +
                        "[3] Bath3 - Level 0 ($10000) [$0 per second]\n" +
                        "[4] Bath4 - Level 0 ($100000) [$0 per second]\n" +
                        "[5] Bath5 - Level 0 ($1000000) [$0 per second]\n");
        assertEquals(shop.toString("food"),
                "[1] Food1 - Level 0 ($100) [$1 per second]\n" +
                        "[2] Food2 - Level 0 ($1000) [$10 per second]\n" +
                        "[3] Food3 - Level 0 ($10000) [$100 per second]\n" +
                        "[4] Food4 - Level 0 ($100000) [$1000 per second]\n" +
                        "[5] Food5 - Level 0 ($1000000) [$10000 per second]\n");
    }

    @Test
    void testArrayListToString() {
        assertEquals(shop.arrayListToString(shop.getRides()),
                "[1] Ride1 - Level 0 ($100) [$1 per second]\n" +
                        "[2] Ride2 - Level 0 ($1000) [$10 per second]\n" +
                        "[3] Ride3 - Level 0 ($10000) [$100 per second]\n" +
                        "[4] Ride4 - Level 0 ($100000) [$1000 per second]\n" +
                        "[5] Ride5 - Level 0 ($1000000) [$10000 per second]\n");
        assertEquals(shop.arrayListToString(shop.getBathrooms()),
                "[1] Bath1 - Level 0 ($100) [$0 per second]\n" +
                        "[2] Bath2 - Level 0 ($1000) [$0 per second]\n" +
                        "[3] Bath3 - Level 0 ($10000) [$0 per second]\n" +
                        "[4] Bath4 - Level 0 ($100000) [$0 per second]\n" +
                        "[5] Bath5 - Level 0 ($1000000) [$0 per second]\n");
        assertEquals(shop.arrayListToString(shop.getFoods()),
                "[1] Food1 - Level 0 ($100) [$1 per second]\n" +
                        "[2] Food2 - Level 0 ($1000) [$10 per second]\n" +
                        "[3] Food3 - Level 0 ($10000) [$100 per second]\n" +
                        "[4] Food4 - Level 0 ($100000) [$1000 per second]\n" +
                        "[5] Food5 - Level 0 ($1000000) [$10000 per second]\n");
    }
}
