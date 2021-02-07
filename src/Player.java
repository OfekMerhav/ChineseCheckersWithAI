import java.awt.Color;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


/**
 *                   Player Class
 * The Player class represents a player in the game
 *
 */
public class Player {

    /** The player's color signature  */
    private Color Player_color;
    /**
     * Each player has 10 tools
     * Each tool has a unique Coordinate on the board
     */
    private HashMap<Coordinate,HexNode> Player_tools;
    /**
     * The coordinate of the player's selected tool
     * If none is selected, Selected_tool contains null
     */
    private Coordinate Selected_tool;

    /**
     * Player Constructor
     * @param player_color represents the player's color signature
     */
    public Player(Color player_color) {
        Player_color = player_color;
        Player_tools = new HashMap<Coordinate,HexNode>();
        Selected_tool = null;

        //add initial nodes
       if(player_color == Color.GREEN)
       {
           for (int z = 4; z > 0; z--) {
               for (int x = 4; x > (4 - z); x--) {
                   Player_tools.put(new Coordinate(x, -(z + x), z), new HexNode(new Piece(player_color)));
               }
           }
       }
       else if(player_color == Color.RED)
       {
           for (int z = -4; z < 0; z++) {
               for (int x = -4; x < -(4 + z); x++) {
                   Player_tools.put(new Coordinate(x, -(z + x), z), new HexNode(new Piece(player_color)));
               }
           }
       }

    }


    public void Clear_Visited_tools()
    {
        Iterator iterator = Player_tools.entrySet().iterator();

        while (iterator.hasNext()) {
            Map.Entry mapElement = (Map.Entry) iterator.next();
            Coordinate current_cor = (Coordinate) mapElement.getKey();
            current_cor.clearIsVisited();
        }

    }

    public boolean Contains_Tool(Coordinate possible_move)
    {
        Iterator iterator = Player_tools.keySet().iterator();

        while(iterator.hasNext())
        {
            Coordinate temp = (Coordinate)iterator.next();

            if(temp.equals(possible_move))
            {
                return true;
            }
        }
        return false;


    }


    public void Print_Selected_tool()
    {
        System.out.format("Coordinate Clicked X:%d , Y:%d , Z:%d \n ", getSelected_tool().getX(), getSelected_tool().getY(), getSelected_tool().getZ());
    }

    /**
     *   Getter and Setters
     */
    public Color getPlayer_color() {
        return Player_color;
    }
    public void setPlayer_color(Color player_color) {
        Player_color = player_color;
    }
    public HashMap<Coordinate, HexNode> getPlayer_tools() {
        return Player_tools;
    }
    public void setPlayer_tools(HashMap<Coordinate, HexNode> player_tools) {
        this.Player_tools = player_tools;
    }

    public Coordinate getSelected_tool() {
        return Selected_tool;
    }
    public void setSelected_tool(Coordinate selected_tool) {

        HashMap<Coordinate,HexNode> nodes;
        Iterator it;

        nodes = getPlayer_tools();
        it = nodes.entrySet().iterator();
        if (selected_tool != null) {
            while (it.hasNext()) {

                /* Current node  */
                Map.Entry mapElement = (Map.Entry) it.next();

                Coordinate Selected_Coordinate = (Coordinate) mapElement.getKey();

                if (Selected_Coordinate.equals(selected_tool)) {
                    this.Selected_tool = Selected_Coordinate;
                }

            }
        }
        else
        {
            this.Selected_tool = null;
        }


    }
}
