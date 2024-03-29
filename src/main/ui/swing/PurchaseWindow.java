package ui.swing;

import model.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;


// represents a window where the user chooses which building to buy
public class PurchaseWindow extends Window {

    private JPanel shop;

    //EFFECTS: initiates window, sets layout, calls setup method
    public PurchaseWindow(SwingGame game) {
        super(game);
        setTitle("Choose building to purchase");
        setLayout(new BorderLayout());
        setup();
    }

    //EFFECTS: calls all methods required to decorate and configure the window
    @Override
    protected void setup() {
        loadShop();
        addBackButton();
    }

    //MODIFIES: this
    //EFFECTS: load the shop buttons, user clicks on buttons to buy building
    public void loadShop() {
        shop = new JPanel();
        shop.setBorder(new EmptyBorder(20,20,20,20));
        shop.setPreferredSize(new Dimension(724, 300));
        shop.setLayout(new GridLayout(3,5));
        shop.setBackground(Color.lightGray);
        for (Ride r : game.getShop().getRides()) {
            JButton button = new JButton();
            button = setupButton(r, button);
            shop.add(button);
        }
        for (Bathroom b : game.getShop().getBathrooms()) {
            JButton button = new JButton();
            button = setupButton(b, button);
            shop.add(button);
        }
        for (FoodStall f : game.getShop().getFoods()) {
            JButton button = new JButton();
            button = setupButton(f, button);
            shop.add(button);
        }
        add(shop, BorderLayout.CENTER);
    }

    //MODIFIES: this
    //EFFECTS: configure button, adds purchase building event
    public JButton setupButton(Building b, JButton button) {
        button.setLabel(b.getName() + " - $" + b.getCost());
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
