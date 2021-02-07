import sun.java2d.SurfaceData;

import java.util.ArrayList;

public class Presenter implements IPresenter{

    Model Game_Model;
    Surface surface;

    public Presenter(Surface surface) {
        Game_Model = new Model();
        this.surface = surface;
    }

    public void Make_Turn(double mouseX,double mouseY)
    {
        Coordinate Selected_Coordinate ;

        if(Game_Model.board.Get_Current_Player_Player().getSelected_tool() == null) {
            Selected_Coordinate = Game_Model.GetSelectedCor(mouseX, mouseY);
            Game_Model.Select_Tool(Selected_Coordinate);
        }
        else {
            Selected_Coordinate = Game_Model.GetSelectedPossibleCor(mouseX,mouseY);
            if (Selected_Coordinate != null)
            {
                Game_Model.Make_Turn(Selected_Coordinate);
                Activate_Bot();
            }
            else
            {
                Game_Model.Select_Tool(Selected_Coordinate);
            }

        }



        if(Game_Model.Check_For_Win())
        {
            System.out.format("Player: "+Game_Model.board.getCurrentPlayer() + "WON !!");
        }

    }

    public void Activate_Bot()
    {
        if(Game_Model.board.getCurrentPlayer() == Players.GREEN_PLAYER)
        {
            CoordinateMove coordinateMove = Game_Model.AiBot.startAi(1,0);
            Game_Model.Select_Tool(coordinateMove.start_move_Coordinate);
            Game_Model.Make_Turn(coordinateMove.To_move_Coordinate);
        }

    }

    public GameBoard Get_Board()
    {
        return this.Game_Model.Get_Board();
    }

    public ArrayList<Coordinate> Get_Possible_Moves()
    {
        return this.Game_Model.Get_Possible_Moves();
    }

}
