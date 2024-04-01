package ui.swing;

import model.AmusementPark;
import model.Ride;
import ui.ButtonNames;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.io.File;
import java.io.IOException;


// represents the main menu of the game, first screen the user sees
public class MainMenu extends JFrame {

    private JPanel buttons;
    private JPanel titlePanel;

    private JFrame dialog;

    private JLabel dialogMessage;
    private JTextField input;

    private JButton newPark;
    private JButton loadPark;
    private JButton quit;

    private String parkName;
    private final SwingGame game;

    //EFFECTS: initializes variables and sets up window
    public MainMenu(SwingGame game) {
        super("Crazy Coasters");
        this.game = game;
        setResizable(false);
        setMinimumSize(new Dimension(1024, 600));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setup();
        centreOnScreen();
        setVisible(true);
    }


    //EFFECTS: runs all starting methods to setup the window
    public void setup() {
        setBackground();
        getRootPane().setBorder(new MatteBorder(10,10,10,10,
                new Color(73,200,204)));
        setLogo();
        buttons = new JPanel();
        buttons.setPreferredSize(new Dimension(342, 600));
        loadButtons();
        buttons.setOpaque(false);
        add(buttons);
    }

    //EFFECTS: paints the background with background image
    public void setBackground() {
        BufferedImage image;
        RescaleOp op;
        try {
            image = ImageIO.read(new File("data/pictures/background.jpg"));
            op  = new RescaleOp(.5f, 0,null);
            image = op.filter(image, null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Image finalImage = image.getScaledInstance(1024,600,Image.SCALE_DEFAULT);
        setContentPane(new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(finalImage, 0, 0, null);
            }
        });

    }

    //MODIFIES: this
    //EFFECTS: sets up CRAZY COASTERS! logo and decorates it
    public void setLogo() {
        GraphicsEnvironment environment = GraphicsEnvironment.getLocalGraphicsEnvironment();
        try {
            environment.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("data/Monocraft.ttf")));
        } catch (FontFormatException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        JLabel pic = new JLabel("CRAZY COASTERS!");
        pic.setFont(new Font("Monocraft", Font.PLAIN, 60));
        pic.setForeground(new Color(3,230,198));
        pic.setBackground(new Color(0,0,0,100));
        pic.setOpaque(false);
        pic.setBorder(new EmptyBorder(60, 150, 100, 150));


        add(pic);
    }

    //EFFECTS: Calls setup methods for JButtons
    public void loadButtons() {
        setupButtons();
        add(buttons);
    }

    //MODIFIES: this
    //EFFECTS: Sets up JButtons
    public void setupButtons() {
        newPark = new JButton(ButtonNames.NEW.getName());
        quit = new JButton(ButtonNames.QUIT.getName());
        loadPark = new JButton(ButtonNames.LOAD.getName());
        setupButtonsHelper();

        newPark.addActionListener(e -> {
            addNewPark();
        });

        quit.addActionListener(e -> {
            game.quit();
        });

        loadPark.addActionListener(e -> {
            LoadParkWindow loadWindow = new LoadParkWindow(game);
            dispose();
        });

        buttons.add(newPark);
        buttons.add(loadPark);
        buttons.add(quit);
    }

    //MODIFIES: this
    //EFFECTS: same as setupButtons but with more buttons
    public void setupButtonsHelper() {
        newPark.setFont(new Font("Monocraft", Font.PLAIN,16));
        loadPark.setFont(new Font("Monocraft", Font.PLAIN,16));
        quit.setFont(new Font("Monocraft", Font.PLAIN,16));

        newPark.setPreferredSize(new Dimension(200, 40));
        quit.setPreferredSize(new Dimension(200, 40));
        loadPark.setPreferredSize(new Dimension(200, 40));
    }

    //EFFECTS: configures event in which "New Park" button is clicked
    // creates confirm button after name is inputted
    // opens ParkWindow
    public void addNewPark() {
        JButton confirm = new JButton("Confirm");
        dialog = new JFrame("Enter park name: ");
        input = new JTextField();

        dialog.setLocationRelativeTo(this);
        dialog.setResizable(false);
        dialog.add(input, BorderLayout.CENTER);
        dialog.add(confirm, BorderLayout.SOUTH);
        dialog.setSize(300, 150);
        dialog.setVisible(true);

        confirm.addActionListener(e -> {
            parkName = input.getText();
            Ride firstRide = new Ride("The Wooden Coaster", 100);
            AmusementPark park = new AmusementPark(parkName);
            park.addRide(firstRide);
            try {
                game.newPark(park);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            ParkWindow parkWindow = new ParkWindow(game);
            dialog.dispose();
            dispose();
        });
    }



    // MODIFIES: this
    // EFFECTS:  frame is centred on desktop
    private void centreOnScreen() {
        Dimension scrn = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((scrn.width - getWidth()) / 2, (scrn.height - getHeight()) / 2);
    }









}