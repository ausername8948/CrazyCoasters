package ui.swing;

import model.AmusementPark;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.List;


// represents window for loading in parks from save file
public class LoadParkWindow extends JFrame {

    private JPanel mainPanel;
    private JScrollPane scrollPane;
    protected SwingGame game;

    protected GraphicsEnvironment environment;

    //EFFECTS: creates the window
    public LoadParkWindow(SwingGame game) {
        this.game = game;
        setResizable(false);
        setMinimumSize(new Dimension(1024, 676));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        centreOnScreen();
        setVisible(true);
        environment = GraphicsEnvironment.getLocalGraphicsEnvironment();
        try {
            environment.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("data/Monocraft.ttf")));
        } catch (FontFormatException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        setup();

    }

    //EFFECTS: calls all setup methods to set up the window
    protected void setup() {
        setMainBackground();
        setLayout(new BorderLayout());
        mainPanel = new JPanel();
        try {
            loadParks();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        scrollPane = new JScrollPane(mainPanel);
        scrollPane.setBackground(new Color(128,173,173,150));
        scrollPane.setOpaque(true);
        add(scrollPane, BorderLayout.CENTER);
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

    //EFFECTS: loads in parks from save file
    //MODIFIES: this
    public void loadParks() throws IOException {
        mainPanel.setBorder(new EmptyBorder(20,20,20,20));
        mainPanel.setPreferredSize(new Dimension(800, 300));
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(new EmptyBorder(100,300,0,0));
        mainPanel.setBackground(new Color(128,173,173,150));
        mainPanel.setOpaque(true);

        JLabel title = new JLabel("PARKS");
        title.setFont(new Font("Monocraft", Font.PLAIN,30));
        title.setForeground(Color.white);
        mainPanel.add(title);

        List<AmusementPark> parks = game.loadParks();

        int index = 0;
        for (AmusementPark p : parks) {
            JButton button = new JButton();
            setupButton(p, button, index);
            mainPanel.add(button);
            index++;
        }
    }

    //EFFECTS: configures each JButton representing a park
    //MODIFIES: this
    public JButton setupButton(AmusementPark p, JButton button, int position) {
        button.setLabel("[" + (position + 1) + "] " + p.getParkName());
        button.setFont(new Font("Monocraft", Font.PLAIN,30));
        button.setPreferredSize(new Dimension(300,100));
        button.addActionListener(e -> {
            game.setCurrentPark(position);
            ParkWindow parkWindow = new ParkWindow(game);
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
        backBtn.setPreferredSize(new Dimension(this.getPreferredSize().width, 50));
        backBtn.setFont(new Font("Monocraft", Font.PLAIN, 30));
        backBtn.setBackground(new Color(84,182,199,255));
        backBtn.addActionListener(e -> {
            MainMenu main = new MainMenu(game);
            dispose();
        });
        this.add(backBtn, BorderLayout.PAGE_END);
    }

    //EFFECTS: centers the window on the screen
    protected void centreOnScreen() {
        Dimension scrn = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((scrn.width - getWidth()) / 2, (scrn.height - getHeight()) / 2);
    }
}
