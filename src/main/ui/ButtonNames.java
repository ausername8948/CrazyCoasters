package ui;

// enum for button names
public enum ButtonNames {
    NEW("New Park"),
    LOAD("Load Park"),
    QUIT("Quit Game"),
    PURCHASE("Purchase building"),
    UPGRADE("Upgrade building"), 
    SELL("Sell building"),
    SAVE("Save park to file");


    private final String name;

    ButtonNames(String name) {
        this.name = name;
    }

    //EFFECTS: returns name value of this button
    public String getName() {
        return name;
    }
}
