import java.util.ArrayList;

public interface IPresenter {

     GameBoard Get_Board();

     ArrayList<Coordinate> Get_Possible_Moves();

     void Make_Turn(double mouseX,double mouseY);

     void Activate_Bot();

}
