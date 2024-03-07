package model;


import org.json.JSONObject;

// represents a ride at an amusement park
public class Ride extends Building {

    //EFFECTS: Instantiates a new ride by calling superclass method to building
    public Ride(String name, int cost) {
        super(name, cost);
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = super.toJson();
        json.put("category", "ride");
        return json;
    }
}
