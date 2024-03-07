package model;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ui.Game;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class AmusementParkTest {

    private AmusementPark park;
    private Ride rideOne;
    private Ride rideTwo;
    private Bathroom bathOne;
    private Bathroom bathTwo;
    private FoodStall foodOne;
    private FoodStall foodTwo;

    @BeforeEach
    void runBefore() {
        park = new AmusementPark("Adi's Amusement Park");
        park.setMoney(10000000);
        rideOne = new Ride("CoolCoaster", 100);
        rideTwo = new Ride("Kingda Ka", 1000000);
        bathOne = new Bathroom("Bath1", 100);
        bathTwo = new Bathroom("Bath2", 1000000);
        foodOne = new FoodStall("Food1", 100);
        foodTwo = new FoodStall("Food2", 1000000);
    }

    @Test
    void testConstructor() {
        assertEquals("Adi's Amusement Park", park.getParkName());
        assertEquals(10000000, park.getMoney());
        assertEquals(0, park.getCustomerHungry());
        assertEquals(0, park.getWashroomNeed());
        assertEquals(0, park.getBuildings().size());
        assertEquals(0, park.getRides().size());
        assertEquals(0, park.getBathrooms().size());
        assertEquals(0, park.getFoods().size());
    }

    @Test
    void testAddRide() {
        park.addRide(rideOne);
        assertEquals(rideOne.getLevel(), 1);
        assertEquals(park.getMoney(), 9999900);
        assertEquals(1, park.getRides().size());
        assertEquals(1, park.getBuildings().size());

        park.addRide(rideTwo);
        assertEquals(rideTwo.getLevel(), 1);
        assertEquals(park.getMoney(), 9999900-1000000);
        assertEquals(2, park.getRides().size());
        assertEquals(2, park.getBuildings().size());
    }

    @Test
    void testAddBathroom() {
        park.addBathroom(bathOne);
        assertEquals(bathOne.getLevel(), 1);
        assertEquals(park.getMoney(), 9999900);
        assertEquals(1, park.getBathrooms().size());
        assertEquals(1, park.getBuildings().size());

        park.addBathroom(bathTwo);
        assertEquals(bathTwo.getLevel(), 1);
        assertEquals(park.getMoney(), 9999900-1000000);
        assertEquals(2, park.getBathrooms().size());
        assertEquals(2, park.getBuildings().size());
    }

    @Test
    void testAddFood() {
        park.addFood(foodOne);
        assertEquals(foodOne.getLevel(), 1);
        assertEquals(park.getMoney(), 9999900);
        assertEquals(1, park.getFoods().size());
        assertEquals(1, park.getBuildings().size());

        park.addFood(foodTwo);
        assertEquals(foodTwo.getLevel(), 1);
        assertEquals(park.getMoney(), 9999900-1000000);
        assertEquals(2, park.getFoods().size());
        assertEquals(2, park.getBuildings().size());
    }

    @Test
    void testSellRide() {
        park.addRide(rideOne);
        park.addRide(rideTwo);

        park.sellRide(rideOne);
        assertEquals(park.getMoney(), 9000000);
        assertEquals(1, park.getRides().size());
        assertEquals(1, park.getBuildings().size());

        park.sellRide(rideTwo);
        assertEquals(park.getMoney(), 10000000);
        assertEquals(0, park.getRides().size());
        assertEquals(0, park.getBuildings().size());
    }

    @Test
    void testSellBathroom() {
        park.addBathroom(bathOne);
        park.addBathroom(bathTwo);

        park.sellBathroom(bathOne);
        assertEquals(park.getMoney(), 9000000);
        assertEquals(1, park.getBathrooms().size());
        assertEquals(1, park.getBuildings().size());

        park.sellBathroom(bathTwo);
        assertEquals(park.getMoney(), 10000000);
        assertEquals(0, park.getBathrooms().size());
        assertEquals(0, park.getBuildings().size());
    }

    @Test
    void testSellFood() {
        park.addFood(foodOne);
        park.addFood(foodTwo);

        park.sellFood(foodOne);
        assertEquals(park.getMoney(), 9000000);
        assertEquals(1, park.getFoods().size());
        assertEquals(1, park.getBuildings().size());

        park.sellFood(foodTwo);
        assertEquals(park.getMoney(), 10000000);
        assertEquals(0, park.getFoods().size());
        assertEquals(0, park.getBuildings().size());
    }

    @Test
    void testUpdate() {
        park.addRide(rideOne);

        park.update();

        assertEquals(park.getMoney(), 9999901);
        assertEquals(park.getCustomerHungry(),5);
        assertEquals(park.getWashroomNeed(),5);

        park.addFood(foodOne);
        park.update();
        assertEquals(park.getMoney(), 9999803);
        assertEquals(park.getCustomerHungry(),9);
        assertEquals(park.getWashroomNeed(),10);

        park.addBathroom(bathOne);
        park.update();
        assertEquals(park.getMoney(), 9999705);
        assertEquals(park.getCustomerHungry(),13);
        assertEquals(park.getWashroomNeed(),14);

        park.setCustomerHungry(100);
        park.setWashroomNeed(100);
        park.update();
        assertEquals(park.getMoney(), 9999706);
        assertEquals(park.getCustomerHungry(), 99);
        assertEquals(park.getWashroomNeed(),99);
        park.setCustomerHungry(-3);
        park.setWashroomNeed(-4);
        park.update();
        assertEquals(1, park.getCustomerHungry());
        assertEquals(0, park.getWashroomNeed());
        assertEquals(park.getMoney(), 9999707);
    }



    @Test
    void testWashroomUsage() {
        park.addBathroom(bathOne);
        assertEquals(park.washroomUsage(), 1);

        park.addBathroom(bathTwo);
        assertEquals(park.washroomUsage(), 6);
    }

    @Test
    void testFoodFed() {
        park.addFood(foodOne);
        assertEquals(park.totalFoodFed(), 1);

        park.addFood(foodTwo);
        assertEquals(park.totalFoodFed(), 6);
    }

    @Test
    void totalRideIncome() {
        park.addRide(rideOne);
        assertEquals(park.totalRideIncome(), 1);
        park.addRide(rideTwo);
        assertEquals(park.totalRideIncome(), 10001);
    }

    @Test
    void totalFoodIncome() {
        park.addFood(foodOne);
        assertEquals(park.totalFoodIncome(), 1);
        park.addFood(foodTwo);
        assertEquals(park.totalFoodIncome(), 10001);
    }

    @Test
    void toStringTest() {
        assertEquals(park.toString(),
                "Current Statistics for Adi's Amusement Park: \n" +
                        "Money: $10000000\n" +
                        "Customer Hunger: 0\n" +
                        "Customer Bathroom Need: 0");

        park.addRide(rideOne);
        park.addBathroom(bathOne);
        park.addFood(foodOne);

        assertEquals(park.toString(),
                "Current Statistics for Adi's Amusement Park: \n" +
                        "Money: $9999700\n" +
                        "Customer Hunger: 0\n" +
                        "Customer Bathroom Need: 0\n" +
                        "[1] CoolCoaster - Level 1 ($150 to upgrade) [$1 per second]\n" +
                        "[1] Bath1 - Level 1 ($150 to upgrade) [$0 per second]\n" +
                        "[1] Food1 - Level 1 ($150 to upgrade) [$1 per second]");
    }

    @Test
    void rideToStringTest() throws Game.NoSuchBuildingException {
        try {
            park.ridesToString();
        } catch (Game.NoSuchBuildingException e) {
            assertEquals(e.getMessage(), null);
        }

        park.addRide(rideOne);
        park.addRide(rideTwo);

        assertEquals(park.ridesToString(),
                "\n[1] CoolCoaster - Level 1 ($150 to upgrade) [$1 per second]\n" +
                        "[2] Kingda Ka - Level 1 ($1500000 to upgrade) [$10000 per second]");
    }

    @Test
    void bathroomsToStringTest() throws Game.NoSuchBuildingException {
        try {
            park.bathroomsToString();
        } catch (Game.NoSuchBuildingException e) {
            assertEquals(e.getMessage(), null);
        }

        park.addBathroom(bathOne);
        park.addBathroom(bathTwo);

        assertEquals(park.bathroomsToString(),
                "\n[1] Bath1 - Level 1 ($150 to upgrade) [$0 per second]\n" +
                        "[2] Bath2 - Level 1 ($1500000 to upgrade) [$0 per second]");
    }

    @Test
    void foodToStringTest() throws Game.NoSuchBuildingException {
        try {
            park.foodToString();
        } catch (Game.NoSuchBuildingException e) {
            assertEquals(e.getMessage(), null);
        }

        park.addFood(foodOne);
        park.addFood(foodTwo);

        assertEquals(park.foodToString(),
                "\n" +
                        "[1] Food1 - Level 1 ($150 to upgrade) [$1 per second]\n" +
                        "[2] Food2 - Level 1 ($1500000 to upgrade) [$10000 per second]");
    }



}
