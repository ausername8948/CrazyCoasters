package ui.swing;

import model.Bathroom;
import model.Building;
import model.FoodStall;
import model.Ride;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;


// represents a window where the user chooses which building to buy
public class PurchaseWindow extends Window {

    private JPanel mainPanel;
    private JPanel rides;
    private JPanel bathrooms;
    private JPanel foods;

    //EFFECTS: initiates window, sets layout, calls setup method
    public PurchaseWindow(SwingGame game) {
        super(game);
        setTitle("Choose building to purchase");
        setup();
    }

    //EFFECTS: calls all methods required to decorate and configure the window
    @Override
    protected void setup() {
        setMainBackground();
        setLayout(new BorderLayout());
        mainPanel = new JPanel();
        setupPanels();
        mainPanel.setBackground(new Color(128,173,173,100));
        mainPanel.setOpaque(true);
        add(mainPanel, BorderLayout.CENTER);
        addBackButton();
    }

    //EFFECTS: paints the background with this cool image I found
    public void setMainBackground() {
        Image image;
        try {
            image = ImageIO.read(new File("data/pictures/parkBackground.jpg"))
                    .getScaledInstance(1024,600,Image.SCALE_DEFAULT);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        setContentPane(new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(image, 0, 0, null);
            }
        });
    }

    //MODIFIES: this
    //EFFECTS: load the shop buttons, user clicks on buttons to buy building
    public void setupPanels() {
        mainPanel.setLayout(new GridLayout(1,3));
        rides = new JPanel();
        bathrooms = new JPanel();
        foods = new JPanel();

        JLabel rideLabel = new JLabel("Rides");
        JLabel bathLabel = new JLabel("Bathrooms");
        JLabel foodLabel = new JLabel("FoodStalls");

        rideLabel.setFont(new Font("Monocraft", Font.PLAIN,30));
        bathLabel.setFont(new Font("Monocraft", Font.PLAIN,30));
        foodLabel.setFont(new Font("Monocraft", Font.PLAIN,30));

        rides.add(rideLabel);
        bathrooms.add(bathLabel);
        foods.add(foodLabel);

        loadBuildings();

        mainPanel.add(rides);
        mainPanel.add(bathrooms);
        mainPanel.add(foods);
    }

    //MODIFIES: this
    //EFFECTS: loads all buildings for current park to upgrade
    public void loadBuildings() {
        rides.setBackground(new Color(128,173,173,100));
        bathrooms.setBackground(new Color(128,173,173,100));
        foods.setBackground(new Color(128,173,173,100));
        for (Ride r : game.getShop().getRides()) {
            JButton button = new JButton();
            button = setupButton(r, button);
            rides.add(button);
        }
        for (Bathroom b : game.getShop().getBathrooms()) {
            JButton button = new JButton();
            button = setupButton(b, button);
            bathrooms.add(button);
        }
        for (FoodStall f : game.getShop().getFoods()) {
            JButton button = new JButton();
            button = setupButton(f, button);
            foods.add(button);
        }
    }

    //MODIFIES: this
    //EFFECTS: configure button, adds purchase building event
    public JButton setupButton(Building b, JButton button) {
        button.setLabel(b.getName() + " - $" + b.getCost());
        button.setPreferredSize(new Dimension(250,90));
        button.addActionListener(e -> {
            JFrame f = new JFrame();
            JLabel l = new JLabel();
            if (park.getMoney() < b.getCost()) {
                l.setText("You don't have enough money!");
            } else {
                game.purchase(b);
                l.setText("You purchased " + b.getName() + "!");
            }
            f.add(l);
            f.setLocationRelativeTo(this);
            f.setSize(200, 100);
            f.setVisible(true);
            dispose();
        });
        return button;
    }

    /*
     * MODIFIES: this
     * EFFECTS: adds a back button to confirmPanel that leads user to previous page if clicked on
     */
    private void addBackButton() {
        JButton backBtn = new JButton("Back");
        backBtn.addActionListener(e -> {
            dispose();
        });
        add(backBtn, BorderLayout.PAGE_END);
    }


}
