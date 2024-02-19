package ui;

import model.*;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class Game {
    private final Scanner scanner;
    private ArrayList<AmusementPark> parks;

    private int currentPark;

    private Shop shop;

    //EFFECTS: creates a new game, instantiates scanner, shop, parks
    public Game() {
        scanner = new Scanner(System.in);
        scanner.useDelimiter("\n");
        shop = new Shop();
        parks = new ArrayList<AmusementPark>();
        newPark(); // will be gone later when we have the option to load saved parks
    }

    //MODIFIES: this
    //EFFECTS: creates a new amusement park
    public void newPark() {
        System.out.println("Please enter the name of your new amusement park: ");
        String name = scanner.nextLine();

        AmusementPark park = new AmusementPark(name);
        parks.add(park);
        currentPark = parks.size() - 1;

        Ride firstRide = new Ride("The Wooden Coaster", 100);
        park.addRide(firstRide);
        System.out.println("Your new amusement park, " + name + " has been created! "
                + "\nYour amusement park currently has one ride: " + firstRide.getName() + "!"
                + "\nYou currently have $" + getPark().getMoney() + "!");
        addTimer();
        menu();
    }

    /*-----------------------------
    |        MENU HANDLING        |
     -----------------------------*/

    //EFFECTS: A game menu, users can select the action they wish to perform
    //displays main menu, asks for input, processes input
    private void menu() {

        String option;

        do {
            System.out.println("Choose from the following actions: ");
            System.out.println("[1] Purchase new building");
            System.out.println("[2] Upgrade building");
            System.out.println("[3] View statistics");
            System.out.println("[4] Sell building");
            option = scanner.next();

        } while (!option.matches("[1-4]"));

        handleMenu(Integer.valueOf(option));
    }


    //EFFECTS: handles user input from the menu, runs the appropriate function corresponding to user's choice
    private void handleMenu(int option) {
        switch (option) {
            case 1:
                purchaseBuilding();
                break;
            case 2:
                upgradeBuilding();
                break;
            case 3:
                viewStats();
                break;
            case 4:
                sellBuilding();
                break;
        }
    }

    //EFFECTS: shows the statistics of the current amusement park
    public void viewStats() {
        AmusementPark park = parks.get(currentPark);
        System.out.println(park.toString());
        System.out.println();
        menu();
    }

    /*-----------------------------
    |   BUYING THE BUILDINGS       |
     -----------------------------*/


    //EFFECTS: Lets the user choose a type of building to purchase
    private void purchaseBuilding() {

        String option;
        do {
            System.out.println("Choose type of building to purchase: ");
            System.out.println("[1] Ride");
            System.out.println("[2] Bathroom");
            System.out.println("[3] Food Stall");
            option = scanner.next();
        } while (!option.matches("[1-3]"));

        handleBuildingPurchase(Integer.valueOf(option));
    }

    //EFFECTS: handle user input for building purchase, run corresponding function
    public void handleBuildingPurchase(int option) {
        switch (option) {
            case 1:
                showShop("ride");
                break;
            case 2:
                showShop("bathroom");
                break;
            case 3:
                showShop("food");
                break;
        }
    }

    //EFFECTS: show list of available buildings for user to purchase
    private void showShop(String thingToBuy) {
        String output = "";
        String option;
        do {
            switch (thingToBuy) {
                case "ride":
                    output = "Rides available for purchase: \n"
                        + shop.arrayListToString(shop.getRides());
                    break;
                case "bathroom":
                    output = "Bathrooms available for purchase: \n"
                        + shop.arrayListToString(shop.getBathrooms());
                    break;
                case "food":
                    output = "Food Stalls available for purchase: \n"
                        + shop.arrayListToString(shop.getFoods());
                    break;
            }
            System.out.println("Current money: $" + getPark().getMoney());
            System.out.println(output);

            option = scanner.next();

        } while (!option.matches("[1-5]"));
        handlePurchase(Integer.valueOf(option), thingToBuy);
    }

    //EFFECTS: checks if user has enough money to buy selected purchase
    private void handlePurchase(int option, String thingToBuy) {
        int userMoney = getPark().getMoney();
        int cost = (int) Math.pow(10, (option + 1));
        try {
            isPoor(userMoney, cost);
            buyPurchase(option, thingToBuy);
        } catch (NotEnoughMoneyException e) {
            System.out.println("You don't have enough money!");
        }
        menu();
    }

    //MODIFIES: this
    //EFFECTS: Purchases the user's purchase and adds it to their amusement park!
    private void buyPurchase(int option, String thingToBuy) {
        switch (thingToBuy) {
            case "ride":
                Ride r = shop.getRides().get(option - 1);
                Ride rideToBuy = new Ride(r.getName(), r.getCost());
                getPark().addRide(rideToBuy);
                System.out.println(rideToBuy.getName() + " has been successfully bought!");
                break;
            case "bathroom":
                Bathroom b = shop.getBathrooms().get(option - 1);
                Bathroom bathToBuy = new Bathroom(b.getName(), b.getCost());
                getPark().addBathroom(bathToBuy);
                System.out.println(bathToBuy.getName() + " has been successfully bought!");
                break;
            case "food":
                FoodStall f = shop.getFoods().get(option - 1);
                FoodStall foodToBuy = new FoodStall(f.getName(), f.getCost());
                getPark().addFood(foodToBuy);
                System.out.println(foodToBuy.getName() + " has been successfully bought!");
                break;
            default:
                break;
        }
    }


    /*-----------------------------
    |   UPGRADING THE BUILDINGS    |
     -----------------------------*/


    //EFFECTS: Takes user input for which type of building they wish to upgrade
    public void upgradeBuilding() {
        String option;
        do {
            System.out.println("Choose type of building to upgrade: ");
            System.out.println("[1] Ride");
            System.out.println("[2] Bathroom");
            System.out.println("[3] Food Stall");
            option = scanner.next();
        } while (!option.matches("[1-3]"));

        handleBuildingUpgrading(Integer.valueOf(option));
    }

    //EFFECTS: handle user input for building upgrade, run corresponding function
    public void handleBuildingUpgrading(int option) {
        System.out.println("Choose from the following buildings to upgrade: ");
        System.out.println("Current money: $" + getPark().getMoney());
        String thingToUp = "";
        String toUp;
        do {
            switch (option) {
                case 1:
                    thingToUp = "ride";
                    break;
                case 2:
                    thingToUp = "bathroom";
                    break;
                case 3:
                    thingToUp = "food";
                    break;
            }
            showBuildings(option);
            toUp = scanner.next();
        } while (!toUp.matches("[1-3]"));

        handleUpgradingOption(Integer.valueOf(toUp), thingToUp);
    }



    //EFFECTS: handle user input if invalid input is inputted for upgrading buildings
    private void handleUpgradingOption(int option, String thingToUp) {

        try {
            checkSelectedExistingBuildingUpgrade(option, thingToUp);
        } catch (NoSuchBuildingException e) {
            System.out.println("That's not a valid option!");
            upgradeBuilding();
        }

    }

    //EFFECTS: checks if user input points to existing building and upgrades if building selected is valid
    private void checkSelectedExistingBuildingUpgrade(int option, String thingToUp)
            throws NoSuchBuildingException {
        switch (thingToUp) {
            case "ride":
                if (option > 0 || option < getPark().getRides().size()) {
                    checkUpgrade(option, thingToUp);
                } else {
                    throw new NoSuchBuildingException();
                }
                break;
            case "bathroom":
                if (option > 0 || option < getPark().getBathrooms().size()) {
                    checkUpgrade(option, thingToUp);
                } else {
                    throw new NoSuchBuildingException();
                }
                break;
            case "food":
                if (option > 0 || option < getPark().getFoods().size()) {
                    checkUpgrade(option, thingToUp);
                } else {
                    throw new NoSuchBuildingException();
                }
                break;
        }
    }

    //MODIFIES: this
    //EFFECTS: gets the ride to upgrade and passes it to the upgrade function
    private void checkUpgrade(int option, String thingToUp) {
        switch (thingToUp) {
            case "ride":
                Ride rideToUp = getPark().getRides().get(option - 1);
                checkUpgradeHelper(option, rideToUp.getUpgradeCost(), thingToUp);
                break;
            case "bathroom":
                Bathroom bathToUp = getPark().getBathrooms().get(option - 1);
                checkUpgradeHelper(option, bathToUp.getUpgradeCost(), thingToUp);
                break;
            case "food":
                FoodStall foodToUp = getPark().getFoods().get(option - 1);
                checkUpgradeHelper(option, foodToUp.getUpgradeCost(), thingToUp);
                break;
            default:
                break;
        }
        System.out.println();
        menu();
    }

    //EFFECTS: checks if user has enough money to buy the selected upgrade
    private void checkUpgradeHelper(int option, int cost, String thingToUp) {
        int userMoney = getPark().getMoney();
        try {
            isPoor(userMoney, cost);
            upgrade(option, thingToUp);
        } catch (NotEnoughMoneyException e) {
            System.out.println("You don't have enough money!");
        }

    }

    //MODIFIES: this
    //EFFECTS: Upgrades the building selected by the user
    private void upgrade(int option, String thingToUp) {
        switch (thingToUp) {
            case "ride":
                Ride rideToUp = getPark().getRides().get(option - 1);
                rideToUp.upgrade();
                System.out.println(rideToUp.getName() + " has been successfully upgraded!");
                break;
            case "bathroom":
                Bathroom bathToUp = getPark().getBathrooms().get(option - 1);
                bathToUp.upgrade();
                System.out.println(bathToUp.getName() + " has been successfully upgraded!");
                break;
            case "food":
                FoodStall foodToUp = getPark().getFoods().get(option - 1);
                foodToUp.upgrade();
                System.out.println(foodToUp.getName() + " has been successfully upgraded!");
                break;
            default:
                break;
        }
    }

    /*-----------------------------
    |   SELLING THE BUILDINGS      |
     -----------------------------*/

    //MODIFIES: this
    //EFFECTS: menu for user to select what type of building to sell, calls helper
    public void sellBuilding() {
        String option;
        do {
            System.out.println("Choose type of building to sell: ");
            System.out.println("[1] Ride");
            System.out.println("[2] Bathroom");
            System.out.println("[3] Food Stall");

            option = scanner.next();
        } while (!option.matches("[1-3]"));
        handleBuildingSelling(Integer.valueOf(option));
    }

    //EFFECTS: handle user input for building purchase, run corresponding function
    // for type of building being sold
    public void handleBuildingSelling(int option) {
        System.out.println("Choose from the following buildings to sell: ");
        System.out.println("Disclaimer: Buildings are sold for their original cost!");
        System.out.println("Current money: $" + getPark().getMoney());
        String thingToSell = "";
        switch (option) {
            case 1:
                thingToSell += "ride";
                break;
            case 2:
                thingToSell += "bathroom";
                break;
            case 3:
                thingToSell += "food";
                break;
        }
        showBuildings(option);
        String userChoice = scanner.next();
        handleBuildingSellingHelperHelper(userChoice, thingToSell);

    }


    //EFFECTS: checks if user input for selling is valid (number)
    public void handleBuildingSellingHelperHelper(String num, String thingToSell) {
        if (!num.matches("[0-9]+")) {
            System.out.println("Invalid input!");
            menu();
        } else {
            handleSellingOption(Integer.valueOf(num), thingToSell);
        }
    }



    //EFFECTS: handle user input if invalid input is inputted for selling buildings
    // (building exists in user's buildings)
    private void handleSellingOption(int option, String thingToSell) {
        try {
            checkSelectedExistingBuildingSell(option, thingToSell);
        } catch (NoSuchBuildingException e) {
            System.out.println("That's not a valid option!");
            sellBuilding();
        }

    }

    //EFFECTS: checks if user input points to existing building and sells if building selected exists
    private void checkSelectedExistingBuildingSell(int option, String thingToSell)
            throws NoSuchBuildingException {
        switch (thingToSell) {
            case "ride":
                if (option > 0 || option < getPark().getRides().size()) {
                    sell(option, thingToSell);
                } else {
                    throw new NoSuchBuildingException();
                }
                break;
            case "bathroom":
                if (option > 0 || option < getPark().getBathrooms().size()) {
                    sell(option, thingToSell);
                } else {
                    throw new NoSuchBuildingException();
                }
                break;
            case "food":
                if (option > 0 || option < getPark().getFoods().size()) {
                    sell(option, thingToSell);
                } else {
                    throw new NoSuchBuildingException();
                }
                break;
        }
    }

    //MODIFIES: this
    //EFFECTS: Sells the building selected by the user
    private void sell(int option, String thingToSell) {
        switch (thingToSell) {
            case "ride":
                Ride rideToSell = getPark().getRides().get(option - 1);
                getPark().sellRide(rideToSell);
                System.out.println(rideToSell.getName() + " has been successfully sold!");
                break;
            case "bathroom":
                Bathroom bathToSell = getPark().getBathrooms().get(option - 1);
                getPark().sellBathroom(bathToSell);
                System.out.println(bathToSell.getName() + " has been successfully sold!");
                break;
            case "food":
                FoodStall foodToSell = getPark().getFoods().get(option - 1);
                getPark().sellFood(foodToSell);
                System.out.println(foodToSell.getName() + " has been successfully sold!");
                break;
            default:
                break;
        }
        System.gc();
        System.out.println();
        menu();
    }

    /*-----------------------------
    |      USEFUL FUNCTIONS        |
     -----------------------------*/


    //EFFECTS: Shows the user a listing of their buildings
    public void showBuildings(int toShow) {
        switch (toShow) {
            case 1: //Ride
                try {
                    System.out.println(getPark().ridesToString());
                } catch (NoSuchBuildingException e) {
                    menu();
                }
                break;
            case 2: //Bathroom
                try {
                    System.out.println(getPark().bathroomsToString());
                } catch (NoSuchBuildingException e) {
                    menu();
                }
                break;
            case 3: //FoodStall
                try {
                    System.out.println(getPark().foodToString());
                } catch (NoSuchBuildingException e) {
                    menu();
                }
                break;
        }
    }

    // MODIFIES: this
    // EFFECTS: timer updates game every updateInterval milliseconds
    // (set to 1 second)
    private void addTimer() {
        Timer t = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                getPark().update();

            }
        };
        TimerTask warnUser = new TimerTask() {
            @Override
            public void run() {
                if (getPark().getCustomerHungry() == 100) {
                    System.out.println("Your customers are starving! Get them more food!");
                }

                if (getPark().getWashroomNeed() == 100) {
                    System.out.println("Your customers need to go to the bathroom! Purchase a bathroom!");
                }
            }
        };

        t.scheduleAtFixedRate(warnUser, 0,5000);
        t.scheduleAtFixedRate(task, 0, 1000);
    }


    // ends and closes the game
    private void endGame() {
        System.exit(0);
    }

    public AmusementPark getPark() {
        return parks.get(currentPark);
    }

    /*-----------------------------
    |          EXCEPTIONS          |
     -----------------------------*/

    //EFFECTS: throws exception when user is too poor
    public void isPoor(int userMoney, int cost) throws NotEnoughMoneyException {
        if (userMoney < cost) {
            throw new NotEnoughMoneyException();
        }
    }


    // when user doesn't have enough money to do an action this is thrown
    public static class NotEnoughMoneyException extends Exception {

        public NotEnoughMoneyException() {
            super();
        }
    }

    // when user selects a non-existing building this is thrown
    public static class NoSuchBuildingException extends Exception {

        public NoSuchBuildingException() {
            super();
        }
    }


}
