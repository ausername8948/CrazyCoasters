package ui.swing;

import model.*;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


//represents the CrazyCoasters game in swing
public class SwingGame {
    private static final String saveFile = "./data/parks.json";
    private List<AmusementPark> parks;
    private JsonReader reader;
    private JsonWriter writer;
    private int currentPark;


    private Shop shop;

    //EFFECTS: initiates the game with empty list of parks, new shop, new JsonReader
    public SwingGame() {
        reader = new JsonReader(saveFile);
        parks = new ArrayList<>();
        shop = new Shop();

        try {
            parks = reader.read();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //MODIFIES: this
    //EFFECTS: creates a new park, sets current park to new park
    public void newPark(AmusementPark park) throws IOException {
        parks.add(park);
        currentPark = parks.size() - 1;
    }


    //MODIFIES: this
    //EFFECTS: load parks from save file
    public List<AmusementPark> loadParks() throws IOException {
        return parks;
    }


    //MODIFIES: this
    //EFFECTS: saves parks to file
    public void saveParks() throws FileNotFoundException {
        writer = new JsonWriter(saveFile);
        writer.open();
        writer.write(parks);
        writer.close();
    }

    //MODIFIES: this
    //EFFECTS: purchases given building for the current park
    public void purchase(Building b) {
        AmusementPark park = parks.get(currentPark);
        if (b instanceof Ride) {
            park.addRide((Ride) b);
        } else if (b instanceof Bathroom) {
            park.addBathroom((Bathroom) b);
        } else if (b instanceof FoodStall) {
            park.addFood((FoodStall) b);
        }
    }

    //REQUIRES: building is owned by current park
    //MODIFIES: this
    //EFFECTS: upgrades given building for the current park
    public void upgrade(Building b) {
        AmusementPark park = parks.get(currentPark);
        park.setMoney(park.getMoney() - b.getUpgradeCost());
        b.upgrade();
    }

    //REQUIRES: Building is owned by current park
    //MODIFIES: this
    //EFFECTS: sells given building for the current park
    public void sell(Building b) {
        AmusementPark park = parks.get(currentPark);
        if (b instanceof Ride) {
            park.sellRide((Ride) b);
        } else if (b instanceof Bathroom) {
            park.sellBathroom((Bathroom) b);
        } else if (b instanceof FoodStall) {
            park.sellFood((FoodStall) b);
        }
    }

    public void quit() {
        for (Event next : EventLog.getInstance()) {
            System.out.println(next.toString());
        }
        System.exit(0);
    }

    //MODIFIES: this
    //EFFECTS: sets current park to given index
    public void setCurrentPark(int index) {
        currentPark = index;
    }

    //MODIFIES: this
    //EFFECTS: returns current park
    public AmusementPark getPark() {
        return parks.get(currentPark);
    }

    //MODIFIES: this
    //EFFECTS: returns shop
    public Shop getShop() {
        return shop;
    }


}
