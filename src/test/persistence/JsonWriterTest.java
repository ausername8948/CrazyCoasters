package persistence;

import model.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


class JsonWriterTest extends JsonTest {
    //NOTE TO CPSC 210 STUDENTS: the strategy in designing tests for the JsonWriter is to
    //write data to a file and then use the reader to read it back in and check that we
    //read in a copy of what was written out.

    @Test
    void testWriterInvalidFile() {
        try {
            AmusementPark park = new AmusementPark("MyPark");
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterNoParks() {
        try {
            List<AmusementPark> parks = new ArrayList<>();
            JsonWriter writer = new JsonWriter("./data/testWriterNoParks.json");
            writer.open();
            writer.write(parks);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterNoParks.json");
            parks = reader.read();
            assertEquals(0, parks.size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralPark() {
        try {
            List<AmusementPark> parks = new ArrayList<>();
            AmusementPark park = new AmusementPark("MyPark");
            park.setMoney(1000000);
            Bathroom b = new Bathroom("Bath1", 100);
            Ride r = new Ride("The Wooden Coaster", 100);
            FoodStall f = new FoodStall("Food1", 100);
            park.addBathroom(b);
            park.addRide(r);
            park.addFood(f);


            parks.add(park);

            JsonWriter writer = new JsonWriter("./data/testWriterGeneralPark.json");
            writer.open();
            writer.write(parks);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralPark.json");
            parks = reader.read();
            park = parks.get(0);
            assertEquals("MyPark", park.getParkName());
            List<Building> buildings = park.getBuildings();
            assertEquals(3, buildings.size());
            checkBathroom(
                    "Bath1",
                    0,
                    100,
                    150,
                    1,
                    1,
                    "bathroom",
                    (Bathroom) buildings.get(0));
            checkFoodStall(
                    "Food1",
                    1,
                    100,
                    150,
                    1,
                    1,
                    "food",
                    (FoodStall) buildings.get(1));
            checkRide(
                    "The Wooden Coaster",
                    1,
                    100,
                    150,
                    1,
                    "ride",
                    (Ride) (buildings.get(2)));



        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}