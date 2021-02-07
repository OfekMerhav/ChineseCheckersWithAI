import java.util.ArrayList;
import java.util.List;
public class Directions {

    private direction directions;

    private List<Coordinate> Directions_Values;

    public Directions()
    {
        Directions_Values = new ArrayList<Coordinate>();
        Directions_Values.add(new Coordinate(-1,1,0));
        Directions_Values.add(new Coordinate(-1,0,1));
        Directions_Values.add(new Coordinate(0,-1,1));
        Directions_Values.add(new Coordinate(1,-1,0));
        Directions_Values.add(new Coordinate(1,0,-1));
        Directions_Values.add(new Coordinate(0,1,-1));

    }


    public Coordinate get_direction(direction direction)
    {
        return Directions_Values.get(direction.ordinal());
    }


}
