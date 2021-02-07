import java.awt.geom.Ellipse2D;
import java.util.Random;

/**
 *                            Coordinate Class
 * The Coordinate class represents the location/Coordinate of the hex in the hex board
 * the location is represented by x,y,z by the cube-coordinated board method
 *
 */
public class Coordinate {

    private int x,y,z;

    private boolean is_visited;

    /**
     * Empty HexNode Constructor
     */
    public Coordinate() {
        is_visited = false;
    }
    /**
     * HexNode Constructor
     * @param x represents x coordinate
     * @param y represents y coordinate
     * @param z represents z coordinate
     */
    public Coordinate(int x,int y,int z) {
        this.x = x;
        this.y = y;
        this.z = z;
        is_visited = false;
    }

    public Coordinate(Coordinate coordinate) {
        this.x = coordinate.x;
        this.y = coordinate.y;
        this.z = coordinate.z;
        is_visited = false;
    }

    /**
     *
     */
    public Ellipse2D getEllipse() {

        double dx = this.x;
        double dy = this.y;
        double dz = this.z;
        double px = -1*(dz-dx)/2;
        double py = dy;

        return new Ellipse2D.Double(px*App.HEX_DIAMETER + App.SCREEN_SIZE/2,
                py*(App.HEX_DIAMETER) + App.SCREEN_SIZE/2,
                App.PIECE_DIAMETER, App.PIECE_DIAMETER);
    }

    public static Coordinate Get_Coordinate(int x,int y)
    {
        int cor_z = -1*(2*x-y)/2;
        int cor_x = 2*x +cor_z;
        int cor_y = y;

        return new Coordinate(cor_x,cor_y,cor_z);
    }

    @Override
    public boolean equals(Object obj) {
        Coordinate coordinate = (Coordinate)obj;
        return x == coordinate.getX() && y == coordinate.getY() && z == coordinate.getZ();
    }

//    @Override
//    public int hashCode() {
//        Random rnd = new Random();
//        int result = 2;
////        int x = (int)(Math.pow(this.x,(this.x << 2)));
////        int y = (int)(Math.pow(this.y,(this.y << 2)));
////        int z = (int)(Math.pow(this.z,(this.z << 2)));
//        result = 31 * result + Integer.hashCode(x);
//        result = (31 * result * Integer.hashCode(y))/result;
//        result = 31 * result * Integer.hashCode(z);
////        result = result + rnd.nextInt(1000);
//        return result;
////        return (Integer.hashCode(x)*Integer.hashCode(y)*Integer.hashCode(z))%(x+Integer.hashCode(y)/Integer.hashCode(z));
//    }

    public double Distance(Coordinate coordinate)
    {
        return Math.sqrt(Math.pow(x-coordinate.x,2)+Math.pow(y-coordinate.y,2)+Math.pow(z-coordinate.z,2));
    }

    public void Add_Coordinate(Coordinate coordinate)
    {
        this.x += coordinate.x;
        this.y+= coordinate.y;
        this.z += coordinate.z;
    }

    public direction get_relative_direction_of_cor(Coordinate coordinate)
    {
        if (this.x == coordinate.x)
        {
            if (this.y > coordinate.y)
            {
                return direction.UPPER_LEFT;
            }
            if (this.y < coordinate.y)
            {
                return direction.DOWN_RIGHT;
            }

        }
        if (this.y == coordinate.y)
        {
            if (this.x > coordinate.x)
            {
                return direction.LEFT;
            }
            if (this.x < coordinate.x)
            {
                return direction.RIGHT;
            }
        }
        if (this.z == coordinate.z)
        {
            if (this.x > coordinate.x)
            {
                return direction.DOWN_LEFT;
            }
            if (this.x < coordinate.x)
            {
                return direction.UPPER_RIGHT;
            }
        }
        return direction.NONE;

    }


    /**
     * Getters and Setters
     */
    public int getX() {
        return x;
    }
    public void setX(int x) {
        this.x = x;
    }
    public int getY() {
        return y;
    }
    public void setY(int y) {
        this.y = y;
    }
    public int getZ() {
        return z;
    }
    public void setZ(int z) {
        this.z = z;
    }
    public boolean IsVisited()
    {return is_visited;}
    public void SetAsVisited()
    {this.is_visited = true;}
    public void clearIsVisited()
    {this.is_visited = false;}


}
