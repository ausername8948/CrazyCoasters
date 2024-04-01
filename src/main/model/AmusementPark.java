package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;
import ui.Game;

import java.util.ArrayList;

//represents an amusement park with various buildings, users can add buildings and view statistics
//customerHungry represents the need for FoodStalls, washroomNeed represents the need for bathrooms
public class AmusementPark implements Writable {


    private String parkName;

    private int money;
    private int customerHungry;
    private int washroomNeed;

    private ArrayList<Building> buildings;
    private ArrayList<Ride> rides;
    private ArrayList<Bathroom> bathrooms;
    private ArrayList<FoodStall> foods;



    //EFFECTS: creates new amusement park with no buildings, "String name" name, set money to 100,
    //set customerHungry, washroomNeed to 0
    public AmusementPark(String name) {
        parkName = name;
        money = 200;
        customerHungry = 0;
        washroomNeed = 0;

        buildings = new ArrayList<Building>();
        rides = new ArrayList<Ride>();
        bathrooms = new ArrayList<Bathroom>();
        foods = new ArrayList<FoodStall>();
        EventLog.getInstance().logEvent(new Event("Amusement park \"" + name + "\" has been created!"));

    }

    //MODIFIES: this
    //EFFECTS: adds a ride, sets the level to 1, subtract ride cost from money
    //adds ride to building and ride lists
    public void addRide(Ride ride) {
        ride.setLevel(1);
        money -= ride.getCost();
        rides.add(ride);
        buildings.add(ride);
        EventLog.getInstance().logEvent(new Event(
                "Ride \"" + ride.getName() + "\" has been added to " + getParkName() + "!"));

    }

    //MODIFIES: this
    //EFFECTS: adds a bathroom, sets the level to 1, subtract bathroom cost from money
    //adds bathroom to building and bathroom lists
    public void addBathroom(Bathroom bathroom) {
        bathroom.setLevel(1);
        money -= bathroom.getCost();
        bathrooms.add(bathroom);
        buildings.add(bathroom);
        EventLog.getInstance().logEvent(new Event(
                "Bathroom \"" + bathroom.getName() + "\" has been added to " + getParkName() + "!"));
    }

    //MODIFIES: this
    //EFFECTS: adds a FoodStall, sets the level to 1, subtract FoodStall cost from money
    //adds FoodStall to building and FoodStall lists
    public void addFood(FoodStall food) {
        food.setLevel(1);
        money -= food.getCost();
        foods.add(food);
        buildings.add(food);
        EventLog.getInstance().logEvent(new Event(
                "FoodStall \"" + food.getName() + "\" has been added to " + getParkName() + "!"));
    }

    //REQUIRES: Ride is in rides
    //MODIFIES: this
    //EFFECTS: sells a ride, adds ride cost to money, removes from building and ride lists
    public void sellRide(Ride ride) {
        money += ride.getCost();
        rides.remove(ride);
        buildings.remove(ride);
        EventLog.getInstance().logEvent(new Event(
                "Ride \"" + ride.getName() + "\" has been sold from " + getParkName() + "!"));
    }

    //REQUIRES: Bathroom is in bathrooms
    //MODIFIES: this
    //EFFECTS: sells a bathroom, adds bathroom cost to money, removes from building and bathroom lists
    public void sellBathroom(Bathroom bathroom) {
        money += bathroom.getCost();
        bathrooms.remove(bathroom);
        buildings.remove(bathroom);
        EventLog.getInstance().logEvent(new Event(
                "Bathroom \"" + bathroom.getName() + "\" has been sold from " + getParkName() + "!"));
    }

    //REQUIRES: FoodStall is in foods
    //MODIFIES: this
    //EFFECTS: sells a FoodStall, adds FoodStall cost to money, removes from building and FoodStall lists
    public void sellFood(FoodStall food) {
        money += food.getCost();
        foods.remove(food);
        buildings.remove(food);
        EventLog.getInstance().logEvent(new Event(
                "FoodStall \"" + food.getName() + "\" has been sold from " + getParkName() + "!"));
    }

    //MODIFIES: this
    //EFFECTS: updates the game; adds income generated to money
    // adjusts customerHungry and washroomNeed (customers need food and need washrooms)
    public void update() {
        int income = totalFoodIncome() + totalRideIncome();
        int foodFed = totalFoodFed();
        int customerRelief = washroomUsage();
        boolean paid = false;
        handleBathroomAndHunger(paid, income);
        if (washroomNeed > 0) {
            washroomNeed -= customerRelief;
        }

        if (customerHungry > 0) {
            customerHungry -= foodFed;
        }
        if (washroomNeed <= 0) {
            washroomNeed = 0;
        }
        if (customerHungry <= 0) {
            customerHungry = 0;
        }

    }

    //!!! needs tests
    //MODIFIES: this
    //EFFECTS: handles boundary cases for customerHungry and washroomNeed
    public void handleBathroomAndHunger(boolean paid, int income) {

        if (customerHungry >= 100) {
//            System.out.println("Your customers are starving! Get them more food!");
            customerHungry = 100;
            money += (int) Math.ceil((double) income / 2);
            paid = true;
        }

        if (washroomNeed >= 100) {
//            System.out.println("Your customers need to go to the bathroom! Purchase a bathroom!");
            washroomNeed = 100;
            if (!paid) {
                money += (int) Math.ceil((double) income / 2);
                paid = true;
            }
        }
        if (!paid) {
            money += income;
            // washroomNeed and customerHungry goes up by log_10 (money) - 1 per second
            washroomNeed += (int) (Math.log10(money) - 1);
            customerHungry += (int) (Math.log10(money) - 1);
        }
    }

    //EFFECTS: calculates "washroom usage" to be subtracted from washroomNeed by looping through bathrooms list
    public int washroomUsage() {
        int washroomUsage = 0;
        for (Bathroom b : bathrooms) {
            washroomUsage += b.getWashroomCap();
        }

        return washroomUsage;
    }

    //EFFECTS: calculates "total food fed" to be subtracted from customerHungry by looping through FoodStall list
    public int totalFoodFed() {
        int foodFed = 0;
        for (FoodStall f : foods) {
            foodFed += f.getFoodFed();
        }

        return foodFed;
    }

    //EFFECTS: calculates total income generated by all rides
    public int totalRideIncome() {
        int income = 0;
        for (Ride r : rides) {
            income += r.getIncome();
        }

        return income;
    }

    //EFFECTS: calculates total income generated by all FoodStall
    public int totalFoodIncome() {
        int income = 0;
        for (FoodStall f : foods) {
            income += f.getIncome();
        }

        return income;
    }

    //EFFECTS: A string of a list of statistics for the amusement park, including money and a list of all buildings
    @Override
    public String toString() {
        String out = "Current Statistics for " + parkName + ": ";

        out += "\nMoney: $" + this.money;
        out += "\nCustomer Hunger: " + this.customerHungry;
        out += "\nCustomer Bathroom Need: " + this.washroomNeed;
        try {
            out += ridesToString();
        } catch (Game.NoSuchBuildingException e) {
            out += "";
        }
        try {
            out += bathroomsToString();
        } catch (Game.NoSuchBuildingException e) {
            out += "";
        }
        try {
            out += foodToString();
        } catch (Game.NoSuchBuildingException e) {
            out += "";
        }

        return out;
    }


    //EFFECTS: A string listing all rides
    public String ridesToString() throws Game.NoSuchBuildingException {

        StringBuilder toPrint = new StringBuilder();

        if (rides.isEmpty()) {
            throw new Game.NoSuchBuildingException();
        }

        for (int i = 0; i < rides.size(); i++) {
            toPrint.append("\n[").append(i + 1).append("] ").append(rides.get(i).toString());
        }

        return toPrint.toString();

    }

    //EFFECTS: A string listing all bathrooms
    public String bathroomsToString() throws Game.NoSuchBuildingException {

        StringBuilder toPrint = new StringBuilder();

        if (bathrooms.isEmpty()) {
            throw new Game.NoSuchBuildingException();
        }

        for (int i = 0; i < bathrooms.size(); i++) {
            toPrint.append("\n[").append(i + 1).append("] ").append(bathrooms.get(i).toString());
        }

        return toPrint.toString();
    }

    //EFFECTS: A string listing all FoodStalls
    public String foodToString() throws Game.NoSuchBuildingException {

        StringBuilder toPrint = new StringBuilder();

        if (foods.isEmpty()) {
            throw new Game.NoSuchBuildingException();
        }

        for (int i = 0; i < foods.size(); i++) {
            toPrint.append("\n[").append(i + 1).append("] ").append(foods.get(i).toString());
        }

        return toPrint.toString();
    }



    //Setters

    public void setMoney(int money) {
        this.money = money;
    }

    public void setCustomerHungry(int hungry) {
        this.customerHungry = hungry;
    }

    public void setWashroomNeed(int washroom) {
        this.washroomNeed = washroom;
    }

    //Getters


    public String getParkName() {
        return parkName;
    }

    public int getMoney() {
        return this.money;
    }

    public int getCustomerHungry() {
        return this.customerHungry;
    }

    public int getWashroomNeed() {
        return this.washroomNeed;
    }



    public ArrayList<Building> getBuildings() {
        return this.buildings;
    }

    public ArrayList<Ride> getRides() {
        return this.rides;
    }

    public ArrayList<FoodStall> getFoods() {
        return this.foods;
    }

    public ArrayList<Bathroom> getBathrooms() {
        return this.bathrooms;
    }


    //EFFECTS: Converts amusement park fields into a JSON object
    @Override
    public JSONObject toJson() {
        JSONObject jsonString = new JSONObject();
        jsonString.put("name", parkName);
        jsonString.put("money", money);
        jsonString.put("hungry", customerHungry);
        jsonString.put("bathroom", washroomNeed);
        jsonString.put("buildings", buildingsToJson());
        return jsonString;
    }

    //EFFECTS: returns buildings in amusement park as a JSONArray
    private JSONArray buildingsToJson() {
        JSONArray buildingsArray = new JSONArray();

        for (Bathroom b : bathrooms) {
            buildingsArray.put(b.toJson());
        }

        for (FoodStall f : foods) {
            buildingsArray.put(f.toJson());
        }

        for (Ride r : rides) {
            buildingsArray.put(r.toJson());
        }

        return buildingsArray;
    }


    // yo make a random event log with customer randomly getting mad and stuff lol

}
