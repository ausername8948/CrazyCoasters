package persistence;


import model.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            List<AmusementPark> parks = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderNoParks() {
        JsonReader reader = new JsonReader("./data/testReaderNoParks.json");
        try {
            List<AmusementPark> parks = reader.read();
            assertEquals(0, parks.size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralWorkRoom() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralPark.json");
        try {
           List<AmusementPark> parks = reader.read();
           AmusementPark park = parks.get(0);
            assertEquals("bad amusement park", park.getParkName());
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
            checkRide(
                    "The Wooden Coaster",
                    1,
                    100,
                    150,
                    1,
                    "ride",
                    (Ride) buildings.get(1));
            checkFoodStall(
                    "Food1",
                    1,
                    100,
                    150,
                    1,
                    1,
                    "food",
                    (FoodStall) buildings.get(2));

        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

}
