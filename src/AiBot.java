import javax.jws.WebParam;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class AiBot {

    Model Game_Model;
    GameBoard board;


    public AiBot(Model Game_Model) {
        this.Game_Model = Game_Model;
        this.board = Game_Model.Get_Board();
    }

    public CoordinateMove startAi(int Depth,int TurnCount)
    {

        if(Game_Model.Check_For_Win() || Depth == 0)
        {
            double evaluation_score = EvaluateBoard();
            board.Switch_player();
            evaluation_score = evaluation_score+EvaluateBoard();
            board.Switch_player();
            return new CoordinateMove(evaluation_score);
        }

        HashMap<Coordinate,HexNode> nodes;
        Iterator it;

        nodes = this.board.Get_Current_Player_Player().getPlayer_tools();
        it = nodes.entrySet().iterator();

        ArrayList<Coordinate> Possible_Moves ;
        Players CurrentPlayer = board.getCurrentPlayer();

        Coordinate Outer_Best_Move = new Coordinate();
        double Outer_best_move_score ;
        Coordinate Outer_Inner_Best_Move = new Coordinate();
        double Outer_Inner_best_move_score ;

        if(CurrentPlayer == Players.GREEN_PLAYER)
        {
            Outer_best_move_score = -10000;
        }
        else
        {
            Outer_best_move_score = 10000;
        }



        while(it.hasNext()){

            /* Current node  */
            Map.Entry mapElement = (Map.Entry)it.next();

            Coordinate Selected_Coordinate = new Coordinate((Coordinate)mapElement.getKey());
            Game_Model.Select_Tool((Coordinate)mapElement.getKey());

            Possible_Moves = new ArrayList<>(Game_Model.Get_Possible_Moves());
//            Possible_Moves = Game_Model.Get_Possible_Moves();
            CurrentPlayer = board.getCurrentPlayer();

            Coordinate best_move = new Coordinate();
            double best_move_score ;

            if(CurrentPlayer == Players.GREEN_PLAYER)
            {
                best_move_score = -10000;
            }
            else
            {
                best_move_score = 10000;
            }

            for (Coordinate move : Possible_Moves)
            {

                /* Make_Turn switches the player after making the turn */
                Game_Model.Make_Turn(move);

                CoordinateMove coordinateMove = startAi(Depth-1,TurnCount+1);

                /* Switch back to the current player */
                board.Switch_player();

//                Possible_Moves = Game_Model.Get_Possible_Moves();
                CurrentPlayer = board.getCurrentPlayer();

                if(CurrentPlayer == Players.GREEN_PLAYER)
                {
                    if(coordinateMove.Move_Score > best_move_score)
                    {
                        best_move_score = coordinateMove.Move_Score;
                        best_move = move;
                    }
                }
                else
                {
                    if(coordinateMove.Move_Score < best_move_score)
                    {
                        best_move_score = coordinateMove.Move_Score;
                        best_move = move;
                    }
                }

                /* UnDo move */
                Game_Model.Select_Tool(move);
                Game_Model.Make_Turn(Selected_Coordinate);
                /* Switch back to the current player */
                board.Switch_player();
                Game_Model.Select_Tool(Selected_Coordinate);


            }

            if(CurrentPlayer == Players.GREEN_PLAYER)
            {
                if(best_move_score > Outer_best_move_score)
                {
                    Outer_best_move_score = best_move_score;
                    Outer_Best_Move = Selected_Coordinate;
                    Outer_Inner_Best_Move = best_move;
                }
            }
            else
            {
                if(best_move_score < Outer_best_move_score)
                {
                    Outer_best_move_score = best_move_score;
                    Outer_Best_Move = Selected_Coordinate;
                    Outer_Inner_Best_Move = best_move;
                }
            }



        }

//        Game_Model.Select_Tool(Outer_Best_Move);
//        Game_Model.Make_Turn(Outer_Inner_Best_Move);

        return new CoordinateMove(Outer_Best_Move,Outer_Inner_Best_Move,Outer_best_move_score);

    }

    public double EvaluateBoard() {

        double Distance_Sum = 0;

        Players CurrentPlayer = board.getCurrentPlayer();
        Coordinate Current_Player_Destination;

        if (CurrentPlayer == Players.RED_PLAYER) {

            Current_Player_Destination = new Coordinate(4, -8, 4);
        } else {
            Current_Player_Destination = new Coordinate(-4, 8, -4);
        }

        Iterator iterator;


        iterator = board.Get_Current_Player_Player().getPlayer_tools().entrySet().iterator();

        /* Iterate and calculate the distance of each current player tool */
        while (iterator.hasNext()) {
            Map.Entry mapElement = (Map.Entry) iterator.next();

            Coordinate iterator_Coor = (Coordinate) mapElement.getKey();

            Distance_Sum += Current_Player_Destination.Distance(iterator_Coor);

        }

        if(board.getCurrentPlayer() == Players.GREEN_PLAYER)
            return (1000-Distance_Sum);
        return -1*(1000-Distance_Sum);



    }



}
