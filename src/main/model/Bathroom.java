package model;


import org.json.JSONObject;

//represents a bathroom at an amusement park, with a "level" and "washroomCap" (short for washroom capacity)
public class Bathroom extends Building {
    private int washroomCap;


    //EFFECTS: creates a new bathroom with
    // "String name" name and "int cost" cost
    public Bathroom(String name, int cost) {
        super(name, cost);
        washroomCap = (int) (Math.log10(cost) - 1);
        this.income = 0;
    }

    //MODIFIES: this
    //EFFECTS: calls superclass method, increases the cap of the washroom by 1
    public void upgrade() {
        super.upgrade();
        washroomCap++;
        EventLog.getInstance().logEvent(new Event(
                "Bathroom \"" + getName() + "\" has been upgraded to level " + getLevel() + "!"));
    }

    public int getWashroomCap() {
        return washroomCap;
    }

    public void setWashroomCap(int washroomCap) {
        this.washroomCap = washroomCap;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = super.toJson();
        json.put("washroomCap", washroomCap);
        json.put("category", "bathroom");
        return json;
    }
}
