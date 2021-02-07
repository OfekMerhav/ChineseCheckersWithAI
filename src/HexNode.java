
/**
 *                  HexNode Class
 * The HexNode class represents a single hex node in the board
 * Each player has 10 hex nodes in any time of the game
 *
 *
 */
public class HexNode {

    private Piece type;

    /**
     * HexNode Constructor
     * @param type is the type of the hexNode
     */
    public HexNode(Piece type) {
        this.type = type;
    }

    /**
     * getter and setter function
     */
    public Piece getType() {
        return type;
    }
    public void setType(Piece type) {
        this.type = type;
    }
}
