package persistence;


import model.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

//Code based on JsonReader from JsonSerializationDemo

// Represents a reader that reads workroom from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads List of AmusementPark from file and returns it;
    // throws IOException if an error occurs reading data from file
    public List<AmusementPark> read() throws IOException {
        String jsonData = readFile(source);
        JSONArray jsonArray = new JSONArray(jsonData);
        return parseAmusementParks(jsonArray);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses AmusementPark from JSON object and returns it
    private List<AmusementPark> parseAmusementParks(JSONArray jsonArray) {
        List<AmusementPark> parks = new ArrayList<>();
        for (Object json : jsonArray) {
            JSONObject nextPark = (JSONObject) json;
            parks.add(parseAmusementPark(nextPark));
        }
        return parks;
    }

    private AmusementPark parseAmusementPark(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        AmusementPark park = new AmusementPark(name);
        park.setMoney(jsonObject.getInt("money"));
        park.setCustomerHungry(jsonObject.getInt("hungry"));
        park.setWashroomNeed(jsonObject.getInt("bathroom"));
        addBuildings(park, jsonObject);
        return park;
    }


    // MODIFIES: park
    // EFFECTS: parses buildings from JSON object and adds them to AmusementPark
    private void addBuildings(AmusementPark park, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("buildings");
        for (Object json : jsonArray) {
            JSONObject nextBuilding = (JSONObject) json;
            addBuilding(park, nextBuilding);
        }
    }

    // MODIFIES: park
    // EFFECTS: parses building from JSON object and adds it to AmusementPark
    private void addBuilding(AmusementPark park, JSONObject jsonObject) {
        String category = jsonObject.getString("category");
        if (category.equals("bathroom")) {
            addBathroom(park, jsonObject);
        } else if (category.equals("food")) {
            addFood(park, jsonObject);
        } else {
            addRide(park, jsonObject);
        }
    }


    // MODIFIES: park
    // EFFECTS: parses bathroom from JSON object and adds it to AmusementPark
    private void addBathroom(AmusementPark park, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        int level = jsonObject.getInt("level");
        int upgradeCost = jsonObject.getInt("upgradeCost");
        int cost = jsonObject.getInt("cost");
        int income = jsonObject.getInt("income");
        int washroomCap = jsonObject.getInt("washroomCap");
        Bathroom b = new Bathroom(name, cost);
        b.setLevel(level);
        b.setUpgradeCost(upgradeCost);
        b.setIncome(income);
        b.setWashroomCap(washroomCap);
        park.addBathroom(b);
        park.setMoney(park.getMoney() + b.getCost());
    }

    // MODIFIES: park
    // EFFECTS: parses FoodStall from JSON object and adds it to AmusementPark
    private void addFood(AmusementPark park, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        int level = jsonObject.getInt("level");
        int upgradeCost = jsonObject.getInt("upgradeCost");
        int cost = jsonObject.getInt("cost");
        int income = jsonObject.getInt("income");
        int foodFed = jsonObject.getInt("foodFed");
        FoodStall f = new FoodStall(name, cost);
        f.setLevel(level);
        f.setUpgradeCost(upgradeCost);
        f.setIncome(income);
        f.setFoodFed(foodFed);
        park.addFood(f);
        park.setMoney(park.getMoney() + f.getCost());
    }

    // MODIFIES: park
    // EFFECTS: parses ride from JSON object and adds it to AmusementPark
    private void addRide(AmusementPark park, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        int level = jsonObject.getInt("level");
        int upgradeCost = jsonObject.getInt("upgradeCost");
        int cost = jsonObject.getInt("cost");
        int income = jsonObject.getInt("income");
        Ride r = new Ride(name, cost);
        r.setLevel(level);
        r.setUpgradeCost(upgradeCost);
        r.setIncome(income);
        park.addRide(r);
        park.setMoney(park.getMoney() + r.getCost());
    }
}
