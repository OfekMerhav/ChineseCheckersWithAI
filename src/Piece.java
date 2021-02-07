import java.awt.Color;

/**
 *                 Piece Class
 * The Piece class represents the type of a hexNode
 *
 */
public class Piece {

private Color player_color;
private boolean IsSelected;

    /**
     * Piece Constructor
     * @param player_color represents the player's color in the game
     */
    public Piece(Color player_color) {
        this.player_color = player_color;
        IsSelected = false;
    }



    /**
     *   Getter and Setters
     */
    public Color getPlayer_color() {
        return player_color;
    }
    public void setPlayer_color(Color player_color) {
        this.player_color = player_color;
    }
    public boolean isSelected() {
        return IsSelected;
    }
    public void setSelected(boolean selected) {
        IsSelected = selected;
    }
}
