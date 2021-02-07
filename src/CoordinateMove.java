public class CoordinateMove {

    Coordinate start_move_Coordinate;
    Coordinate To_move_Coordinate;
    double Move_Score;

    public CoordinateMove(double Score) {
        start_move_Coordinate = null;
        To_move_Coordinate = null;
        Move_Score = Score;
    }
    public CoordinateMove(Coordinate start_move_Coordinate,Coordinate To_move_Coordinate,double Score) {
        this.start_move_Coordinate = start_move_Coordinate;
        this.To_move_Coordinate = To_move_Coordinate;
        Move_Score = Score;
    }
}
