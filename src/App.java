import javafx.scene.layout.BackgroundImage;

import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;
import java.math.RoundingMode;


public class App extends JFrame {
    /** SCREEN AND BOARD INFORMATION */
    public static final int SCREEN_SIZE = 900;
    public static final int BOARD_RADIUS = 5;
    public static final int HEX_DIAMETER = (SCREEN_SIZE*2/3)/(BOARD_RADIUS + 2*(BOARD_RADIUS-1));
    //public static final int HEX_DIAMETER = 35;
    public static final int PIECE_DIAMETER = HEX_DIAMETER*4/5;
    public static final int PIECE_RADIUS = PIECE_DIAMETER/2;
    public static final double Y_OFFSET = ((double) HEX_DIAMETER/2)*(2-Math.sqrt(3));
    public static final double VISUAL_OFFSET = ((double) HEX_DIAMETER/2) * Math.sqrt(2)/9;

    /** GAME INFORMATION */
    public static final double TOOLS_DISTANCE= Math.sqrt(2);
//    public static final double X_Z_AXIS_TOOLS_DISTANCE = 46;
//    public static final double Y_Z_AXIS_TOOLS_DISTANCE = 23*Math.sqrt(5);
//    public static final double X_Y_AXIS_TOOLS_DISTANCE = 23*Math.sqrt(5);

    public static final boolean MOVE_ASSISTANCE = true;
    public static final int NUM_HUMAN_PLAYERS = 1;
    public static final Color[] PLAYERS = {Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW, Color.PINK, Color.GRAY};
    public static final String[] PLAYER_NAMES = {"Red", "Blue", "Green", "Yellow", "Pink", "Gray"};


    /**
     * Constructs application by initializing the board and the screen
     */
    public App() {
        initScreen();
    }


    /**
     * Creates the screen and adds a surface to it to draw on
     */
    private void initScreen() {
        add(new Surface());
        pack();
        setTitle("Chinese Checkers");
        setSize(SCREEN_SIZE, SCREEN_SIZE);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    public static void main(String[] args) {
        App app = new App();
        app.setVisible(true);
    }
}
