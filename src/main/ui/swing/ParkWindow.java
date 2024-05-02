package ui.swing;

import model.Bathroom;
import model.FoodStall;
import model.Ride;
import ui.ButtonNames;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;


//represents the amusement park graphical interface, users view their park
public class ParkWindow extends Window {

    private JPanel sidebar;
    private JPanel topBar;
    private JPanel mainWindow;
    private JPanel rides;
    private JPanel bathrooms;
    private JPanel foods;

    private JLabel money;
    private JLabel hungry;
    private JLabel bathroom;
    private JLabel bannerText;

    private JButton purchase;
    private JButton upgrade;
    private JButton sell;
    private JButton save;
    private JButton quit;
    private JButton main;


    //EFFECTS: initiates the park window and calls setup method
    public ParkWindow(SwingGame game) {
        super(game);
        setup();
    }

    //EFFECTS: calls methods to configure window
    @Override
    protected void setup() {
        setMainBackground();
        setLayout(new BorderLayout());
        createSidebar();
        createTopBar();
        createMainWindow();
        createBanner();
        addTimer();

    }


    //EFFECTS: paints the background
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
    //EFFECTS: setup the mainWindow, the area where the buildings are shown
    // call methods to show building labels
    public void createMainWindow() {
        mainWindow = new JPanel();
        mainWindow.setBorder(new EmptyBorder(30,50,20,0));
        mainWindow.setPreferredSize(new Dimension(724, 300));
        mainWindow.setBackground(new Color(128,173,173,150));
        mainWindow.setLayout(new GridLayout(3,1));

        setupPanels();
        updateBuildingSections();


        mainWindow.add(rides);
        mainWindow.add(bathrooms);
        mainWindow.add(foods);
        mainWindow.setOpaque(true);
        this.add(mainWindow, BorderLayout.LINE_START);
    }

    //MODIFIES: this
    //EFFECTS: sets up panels where building labels are shown
    public void setupPanels() {
        rides = new JPanel();
        rides.setForeground(Color.white);
        rides.setFont(new Font("Monocraft", Font.PLAIN,12));

        bathrooms = new JPanel();
        bathrooms.setForeground(Color.white);
        bathrooms.setFont(new Font("Monocraft", Font.PLAIN,12));

        foods = new JPanel();
        foods.setForeground(Color.white);
        foods.setFont(new Font("Monocraft", Font.PLAIN,12));

        rides.setOpaque(false);
        bathrooms.setOpaque(false);
        foods.setOpaque(false);

        rides.setLayout(new BoxLayout(rides, BoxLayout.Y_AXIS));
        bathrooms.setLayout(new BoxLayout(bathrooms, BoxLayout.Y_AXIS));
        foods.setLayout(new BoxLayout(foods, BoxLayout.Y_AXIS));
    }


    //MODIFIES: this
    //EFFECTS: updates building labels in accordance with the game
    public void updateBuildingSections() {
        rides.removeAll();
        bathrooms.removeAll();
        foods.removeAll();
        for (Ride r : park.getRides()) {
            JLabel l = new JLabel(r.toString());
           // l.setOpaque(true);
            rides.add(l);
        }
        for (Bathroom b : park.getBathrooms()) {
            JLabel l = new JLabel(b.toString());
           // l.setOpaque(true);
            bathrooms.add(l);
            if (park.getWashroomNeed() == 100) {
                JLabel warning = new JLabel("You need bathrooms!");
                bathrooms.add(warning);
            }
        }
        for (FoodStall f : park.getFoods()) {
            JLabel l = new JLabel(f.toString());
           // l.setOpaque(true);
            foods.add(l);
            if (park.getCustomerHungry() == 100) {
                JLabel warning = new JLabel("You need food stalls!");
                foods.add(warning);
            }
        }


    }

    //MODIFIES: this
    //EFFECTS: creates top bar where money, hunger, and bathroom need are shown
    public void createTopBar() {
        topBar = new JPanel();
        money = new JLabel();
        hungry = new JLabel();
        bathroom = new JLabel();

        money.setForeground(Color.white);
        hungry.setForeground(Color.white);
        bathroom.setForeground(Color.white);
        money.setFont(new Font("Monocraft", Font.PLAIN, 20));
        hungry.setFont(new Font("Monocraft", Font.PLAIN, 20));
        bathroom.setFont(new Font("Monocraft", Font.PLAIN, 20));
        money.setBorder(new EmptyBorder(0,20,0,0));
        hungry.setBorder(new EmptyBorder(0,50,0,0));
        bathroom.setBorder(new EmptyBorder(0,50,0,0));

        topBar.setBorder(new EmptyBorder(10,10,10,10));
        topBar.setPreferredSize(new Dimension(this.getWidth(), 50));
        topBar.setLayout(new BoxLayout(topBar, BoxLayout.X_AXIS));
        topBar.setBackground(new Color(10,140,163,255));
        loadLabels();
        topBar.add(money);
        topBar.add(hungry);
        topBar.add(bathroom);
        this.add(topBar, BorderLayout.PAGE_START);
    }

    //MODIFIES: this
    //EFFECTS: updates money, hunger, bathroom labels
    public void loadLabels() {
        money.setText("Money: " + Integer.toString(park.getMoney()));
        hungry.setText("Customer Hunger: " + Integer.toString(park.getCustomerHungry()));
        bathroom.setText("Customer Bathroom Need: " + Integer.toString(park.getWashroomNeed()));
    }

    //MODIFIES: this
    //EFFECTS: creates sidebar where buttons are shown
    public void createSidebar() {
        sidebar = new JPanel();
        sidebar.setBorder(new EmptyBorder(120,20,20,20));
        sidebar.setPreferredSize(new Dimension(300, 300));
        sidebar.setBackground(new Color(34,105,117,200));
        sidebar.setOpaque(true);
        loadButtons();
        loadMoreButtons();
        loadAnotherButton();
        this.add(sidebar, BorderLayout.LINE_END);
    }

    //MODIFIES: this
    //EFFECTS: create and decorate purchase and upgrade buttons
    public void loadButtons() {
        purchase = new JButton(ButtonNames.PURCHASE.getName());
        upgrade = new JButton(ButtonNames.UPGRADE.getName());

        purchase.setPreferredSize(new Dimension(200, 40));
        upgrade.setPreferredSize(new Dimension(200, 40));

        purchase.setFont(new Font("Monocraft", Font.PLAIN,12));
        upgrade.setFont(new Font("Monocraft", Font.PLAIN,12));


        purchase.addActionListener(e -> {
            PurchaseWindow purchaseWindow = new PurchaseWindow(game);
        });
        upgrade.addActionListener(e -> {
            UpgradeWindow upgradeWindow = new UpgradeWindow(game);
        });

        sidebar.add(purchase);
        sidebar.add(upgrade);


    }

    //EFFECTS: same as loadButtons() but there was a line limit
    // sets up and decorates save, quit, sell buttons
    public void loadMoreButtons() {
        save = new JButton(ButtonNames.SAVE.getName());
        quit = new JButton(ButtonNames.QUIT.getName());
        sell = new JButton(ButtonNames.SELL.getName());

        save.setPreferredSize(new Dimension(200, 40));
        quit.setPreferredSize(new Dimension(200, 40));
        sell.setPreferredSize(new Dimension(200, 40));

        save.setFont(new Font("Monocraft", Font.PLAIN,12));
        quit.setFont(new Font("Monocraft", Font.PLAIN,12));
        sell.setFont(new Font("Monocraft", Font.PLAIN,12));

        save.addActionListener(e -> {
            saveGame();
        });
        quit.addActionListener(e -> {
            quitGame(); //confirm quitting
        });
        sell.addActionListener(e -> {
            SellWindow sellWindow = new SellWindow(game);
        });
        sidebar.add(sell);
        sidebar.add(save);
        sidebar.add(quit);
    }

    //MODIFIES: this
    //EFFECTS: line limit :(
    public void loadAnotherButton() {
        main = new JButton("Back to menu");
        main.setPreferredSize(new Dimension(200, 40));
        main.setFont(new Font("Monocraft", Font.PLAIN,12));
        main.addActionListener(e -> {
            MainMenu main = new MainMenu(game);
            dispose();
        });
        sidebar.add(main);
    }

    //MODIFIES: this
    //EFFECTS: creates bottom bar where park name is shown
    public void createBanner() {
        bannerText = new JLabel();
        if (park.getParkName().length() == 0) {
            bannerText.setText("A park whose owner was too lazy to set a name..");
        } else {
            bannerText.setText("A park named " + park.getParkName());
        }
        bannerText.setPreferredSize(new Dimension(this.getPreferredSize().width, 50));
        bannerText.setForeground(Color.white);
        bannerText.setFont(new Font("Monocraft", Font.PLAIN, 30));
        bannerText.setBackground(new Color(84,182,199,255));
        bannerText.setOpaque(true);
        this.add(bannerText, BorderLayout.PAGE_END);
    }

    //MODIFIES: this
    //EFFECTS: method for saving button, saves the game to file
    public void saveGame() {
        JFrame dialog = new JFrame();
        JLabel label = new JLabel("Game saved.");
        try {
            game.saveParks();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        dialog.add(label);
        dialog.setLocationRelativeTo(this);
        dialog.setSize(200, 100);
        dialog.setVisible(true);
    }

    //MODIFIES: this
    //EFFECTS: quits the game, extra dialog is shown in case of misclick
    public void quitGame() {
        JFrame dialog = new JFrame("Are you sure?");
        dialog.setLocationRelativeTo(this);
        dialog.setSize(300, 100);
        dialog.setVisible(true);
        dialog.setLayout(new GridBagLayout());

        JButton yes = new JButton("Yes");
        JButton no = new JButton("No");
        yes.addActionListener(e -> {
            System.out.println("Thanks for playing!");
            game.quit();
        });
        no.addActionListener(e -> {
            dialog.dispose();
        });
        dialog.add(yes);
        dialog.add(no);

    }

    //EFFECTS: centers the window on the screen
    @Override
    protected void centreOnScreen() {
        super.centreOnScreen();
    }

    // MODIFIES: this
    // EFFECTS: timer updates game every updateInterval milliseconds
    // (set to 1 second)
    private void addTimer() {
        java.util.Timer t = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                park.update();
                loadLabels();
                updateBuildingSections();
            }
        };

        t.scheduleAtFixedRate(task, 0, 1000);
    }


}
