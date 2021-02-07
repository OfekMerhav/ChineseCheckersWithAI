import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;
import javax.imageio.ImageIO;
import javax.swing.*;


public class Surface extends JPanel implements MouseListener,ISurface{

    public static final String BOARD_BACKGROUND = "BOARDBackGround.png";

    private BufferedImage image;

    private final IPresenter presenter;

    /**
     * Game Board
     */
     GameBoard board;

     ArrayList<Coordinate> possible_moves;


    /********************FUNCTIONS************************/


    public Surface() {
        presenter = new Presenter(this);
        board = presenter.Get_Board();
        possible_moves = presenter.Get_Possible_Moves();

        try {
            image = ImageIO.read(new File(BOARD_BACKGROUND));
        }
        catch(IOException ex){
            System.out.println("not found");
        }

        super.addMouseListener(this);
    }

    /**
     * Helper method. Draws game to screen
     * @param g graphics object
     */
    private void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(image,0,0,this);

        g2d.drawString("Current player:"+board.getCurrentPlayer(),0,20);

        HashMap<Coordinate,HexNode> nodes;
        Iterator it;

        /* Print player1(RED) tools  */
        nodes = this.board.getBoard_players().get(Players.RED_PLAYER.ordinal()).getPlayer_tools();
        it = nodes.entrySet().iterator();
        while(it.hasNext()){

           /* Current node  */
           Map.Entry mapElement = (Map.Entry)it.next();
//           System.out.println("Red player's hash code:   "+((Coordinate)mapElement.getKey()).hashCode());
           g2d.setColor(((HexNode)mapElement.getValue()).getType().getPlayer_color());
           Ellipse2D e = ((Coordinate)mapElement.getKey()).getEllipse();
           g2d.fill(e);
           g2d.draw(e);

       }
        /* Print player2(GREEN) tools  */
        nodes = this.board.getBoard_players().get(Players.GREEN_PLAYER.ordinal()).getPlayer_tools();
        it = nodes.entrySet().iterator();
        while(it.hasNext()) {

            Map.Entry mapElement = (Map.Entry) it.next();
//            System.out.println("Green player's hash code:   "+((Coordinate)mapElement.getKey()).hashCode());
            g2d.setColor(((HexNode) mapElement.getValue()).getType().getPlayer_color());
            Ellipse2D e = ((Coordinate)mapElement.getKey()).getEllipse();
            g2d.fill(e);
            g2d.draw(e);

        }

        /* Print PossibleMoves  */
        ArrayList<Coordinate> Possible_moves = this.possible_moves;
        for(int i=0;i<Possible_moves.size();i++)
        {
            g2d.setColor(Color.MAGENTA);
            Ellipse2D e = Possible_moves.get(i).getEllipse();
            g2d.fill(e);
            g2d.draw(e);
        }


    }

    @Override
    /**
     * Paints to screen. Calls draw method to draw game
     * @param g graphics object
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Font font = new Font("David", Font.BOLD, 20);
        g.setFont(font);
        draw(g);
    }

    @Override
    public void mouseClicked(MouseEvent e) {

        double mouseX = e.getX();
        double mouseY = e.getY();


        presenter.Make_Turn(mouseX,mouseY);
        board = presenter.Get_Board();
        possible_moves = presenter.Get_Possible_Moves();
        presenter.Activate_Bot();
        repaint();



    }


    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
