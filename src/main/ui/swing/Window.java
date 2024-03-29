package ui.swing;

import model.*;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

// represents a window, extension of JFrame but with a few extra general methods
public abstract class Window extends JFrame {

    protected SwingGame game;
    protected AmusementPark park;

    protected GraphicsEnvironment environment;

    //EFFECTS: constructs the window, sets the title,
    // sets size, calls methods, makes window visible, centers on screen, loads custom font
    public Window(SwingGame game) {
        super("Crazy Coasters");
        this.game = game;
        this.park = game.getPark();
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
    }

    //abstract setup method
    protected abstract void setup();


    //MODIFIES: this
    //EFFECTS: centers the window on the screen
    protected void centreOnScreen() {
        Dimension scrn = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((scrn.width - getWidth()) / 2, (scrn.height - getHeight()) / 2);
    }


}
