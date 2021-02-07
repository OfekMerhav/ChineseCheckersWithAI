import java.awt.*;
import java.util.*;
import java.util.List;


/**
 *                                        GameBoard Class
 * The GameBoard class represents the board which consist two players(each represents there tools and locations)
 * and the current player who has the turn
 *
 */
public class GameBoard {

    /** The two players of the game */
    private Player Red_player, Green_Player;
    /** List containing the players */
    List<Player> Board_players;
    /** The player who has the current turn */
    private Players currentPlayer;
    /** class with coordinates representing directions */
    private final Directions directions;
    /** List of the current possible moves */
    ArrayList<Coordinate> Possible_Moves;


    /**
     * Empty GameBoard Constructor
     */
    public GameBoard() {
        Set_Red_player(new Player(Color.RED));
        Set_Green_Player(new Player(Color.GREEN));

        Board_players = new ArrayList<Player>();
        Board_players.add(Red_player);
        Board_players.add(Green_Player);

        currentPlayer = Players.RED_PLAYER;
        directions = new Directions();
        Possible_Moves = new ArrayList<>();

//        middle nodes

//        for(int x = -1*(radius-1); x <= radius-1; x++)
//            for(int y = -1*(radius-1); y <= radius-1; y++)
//                if(-1*(x+y) >= -1*(radius-1) && -1*(x+y) <= radius-1)
//                    nodes.put(new Coordinate(x, y, -1*(x+y)),new HexNode(new Piece(players[0])));
//
//        for (int y = -4; y < 0; y++)
//            for (int z = (4-y); z > 4; z--)
//                nodes.put(new Coordinate((-y-z), y, z), new HexNode(new Piece(players[0])));
//
//        for (int y = -4; y < 0; y++)
//            for (int x = (4-y); x > 4; x--)
//                nodes.put(new Coordinate(x, y, (-x-y)), new HexNode(new Piece(players[0])));
//
//        for (int y = 4; y > 0; y--)
//            for (int x = (-4-y); x < -4; x++)
//                nodes.put(new Coordinate(x, y, (-x-y)), new HexNode(new Piece(players[0])));
//
//        for (int y = 4; y > 0; y--)
//            for (int z = (-4-y); z < -4; z++)
//                nodes.put(new Coordinate(-y-z, y, z), new HexNode(new Piece(players[0])));

    }
    public void Switch_player()
    {
        if(currentPlayer == Players.RED_PLAYER)
           currentPlayer = Players.GREEN_PLAYER;
        else
        {
            currentPlayer = Players.RED_PLAYER;
        }
    }

    public boolean Is_In_Board_Bounds(Coordinate coordinate)
    {
        int coordinateX = coordinate.getX();
        int coordinateY = coordinate.getY();
        int coordinateZ = coordinate.getZ();

        if (coordinateZ <= -5 && (coordinateY > 4 || coordinateX > 4))
            return false;
        if (coordinateY <= -5 && (coordinateZ > 4 || coordinateX > 4))
            return false;
        if (coordinateX <= - 5 && (coordinateY > 4 || coordinateZ > 4))
            return false;
        if (coordinateY >= 5 && (coordinateZ < -4 || coordinateX < -4))
            return false;
        if (coordinateZ >= 5 && (coordinateX < -4 || coordinateY < -4))
            return false;
        if (coordinateX >= 5 && (coordinateY < -4 || coordinateZ < -4))
            return false;

        return true;
    }


    public Player Get_Red_Player() {
        return Red_player;
    }

    public void Set_Red_player(Player red_player) {
        this.Red_player = red_player;

    }


    public Player Get_Green_Player() {
        return Green_Player;
    }

    public void Set_Green_Player(Player green_Player) {
        this.Green_Player = green_Player;
    }

    public Players getCurrentPlayer() {
        return currentPlayer;
    }

    public Player Get_Current_Player_Player()
    {
        return Board_players.get(currentPlayer.ordinal());
    }


    public void setCurrentPlayer(Players currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public List<Player> getBoard_players() {
        return Board_players;
    }

    public Directions Get_Directions()
    {
        return this.directions;
    }

}
