package model;


import java.util.ArrayList;

// represents a shop, with everything the user can buy
public class Shop {

    private final ArrayList<Ride> rides = new ArrayList<>();
    private final ArrayList<Bathroom> bathrooms = new ArrayList<>();
    private final ArrayList<FoodStall> foods = new ArrayList<>();

    private final Ride rideOne = new Ride("Ride1", 100);
    private final Ride rideTwo = new Ride("Ride2", 1000);
    private final Ride rideThree = new Ride("Ride3", 10000);
    private final Ride rideFour = new Ride("Ride4", 100000);
    private final Ride rideFive = new Ride("Ride5", 1000000);

    private final Bathroom bathOne = new Bathroom("Bath1", 100);
    private final Bathroom bathTwo = new Bathroom("Bath2", 1000);
    private final Bathroom bathThree = new Bathroom("Bath3", 10000);
    private final Bathroom bathFour = new Bathroom("Bath4", 100000);
    private final Bathroom bathFive = new Bathroom("Bath5", 1000000);

    private final FoodStall foodOne = new FoodStall("Food1", 100);
    private final FoodStall foodTwo = new FoodStall("Food2", 1000);
    private final FoodStall foodThree = new FoodStall("Food3", 10000);
    private final FoodStall foodFour = new FoodStall("Food4", 100000);
    private final FoodStall foodFive = new FoodStall("Food5", 1000000);

    //EFFECTS: Instantiates the shop by adding all rides, bathrooms, and foods to the lists
    public Shop() {

        rides.add(rideOne);
        rides.add(rideTwo);
        rides.add(rideThree);
        rides.add(rideFour);
        rides.add(rideFive);

        bathrooms.add(bathOne);
        bathrooms.add(bathTwo);
        bathrooms.add(bathThree);
        bathrooms.add(bathFour);
        bathrooms.add(bathFive);

        foods.add(foodOne);
        foods.add(foodTwo);
        foods.add(foodThree);
        foods.add(foodFour);
        foods.add(foodFive);


    }

    //EFFECTS: A string of a list of the thingToPrint (rides, bathrooms, or foods)
    public String toString(String thingToPrint) {

        String output = "";

        switch (thingToPrint) {
            case "ride":
                output = arrayListToString(rides);
                break;
            case "bathroom":
                output = arrayListToString(bathrooms);
                break;
            case "food":
                output = arrayListToString(foods);
                break;
        }

        return output;


    }

    //EFFECTS: A string of a list of the things in the provided arrayList
    public String arrayListToString(ArrayList arrayList) {
        StringBuilder output = new StringBuilder();

        for (int i = 0; i < arrayList.size(); i++) {
            output.append("[").append(i + 1).append("] ").append(arrayList.get(i).toString()).append("\n");
        }

        return output.toString();
    }

    //Getters

    public ArrayList<Ride> getRides() {
        return rides;
    }

    public ArrayList<Bathroom> getBathrooms() {
        return bathrooms;
    }

    public ArrayList<FoodStall> getFoods() {
        return foods;
    }
}
