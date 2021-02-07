import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.*;
import java.util.List;

public class Model {


    GameBoard board;
    AiBot AiBot;

    /** List of the current possible moves */
    ArrayList<Coordinate> Possible_Moves;

    public Model() {

        initBoard();
        Possible_Moves = new ArrayList<>();

        AiBot = new AiBot(this);


    }
    public boolean Select_Tool(Coordinate Selected_Coordinate)
    {

        int current_player_index = board.getCurrentPlayer().ordinal();
        Player current_player = board.getBoard_players().get(current_player_index);

        /* Get the selected tool coordinate, if no tool was selected it will be null */
//        Coordinate Selected_Coordinate = GetSelectedCor(mouseX,mouseY);
//        System.out.format("Mouse Clicked X:%f , Y:%f \n ",mouseX,mouseY);

        if(Selected_Coordinate != null) {
            current_player.setSelected_tool(Selected_Coordinate);
//            current_player.Print_Selected_tool();
//            System.out.format("Tool Clicked X:%f , Y:%f \n ",Selected_Coordinate.getEllipse().getX(),
//                    Selected_Coordinate.getEllipse().getY());
            Update_Possible_Moves();
            return true;

        }
        else
        {
            current_player.setSelected_tool(null);
            System.out.format("None selected\n");
            Update_Possible_Moves();
            return false;

        }




    }

    public void Make_Turn(Coordinate Selected_Coordinate)
    {
        class distance {
            /* calculates the distance of the given points coordinates */
            public double calculate_distance(double point_x,double point_y,double clicked_x ,double clicked_y)
            {
                return Math.sqrt(Math.pow(clicked_y-point_y,2)+Math.pow(clicked_x-point_x,2));
            };
        }

        int current_player_index = board.getCurrentPlayer().ordinal();
        Player current_player = board.getBoard_players().get(current_player_index);

        Coordinate Selected_tool = current_player.getSelected_tool();

        /* when a tool is already selected,check if a possible move was chosen */
        ArrayList<Coordinate> Possible_Moves = Get_Possible_Moves();
        distance distance = new distance();
        for (Coordinate temp : Possible_Moves) {


            if (temp.equals(Selected_Coordinate)) {
                /* when one of the possible moves was chosen,update the selected tool to the new place coordinates */
                Selected_tool.setX(temp.getX());
                Selected_tool.setY(temp.getY());
                Selected_tool.setZ(temp.getZ());

                current_player.setSelected_tool(null);

                Update_Possible_Moves();
                board.Switch_player();
                break;



            }

        }
//        /* Get the selected tool coordinate, if no tool was selected it will be null */
//        Coordinate Selected_Coordinate = GetSelectedCor(mouseX,mouseY);
////        System.out.format("Mouse Clicked X:%f , Y:%f \n ",mouseX,mouseY);
//
//
//
//        if(Selected_Coordinate != null) {
//            current_player.setSelected_tool(Selected_Coordinate);
//            current_player.Print_Selected_tool();
//            System.out.format("Tool Clicked X:%f , Y:%f \n ",Selected_Coordinate.getEllipse().getX(),
//                    Selected_Coordinate.getEllipse().getY());
//
//        }
//        else
//        {
//            current_player.setSelected_tool(null);
//            System.out.format("None selected\n");
//
//        }
//
//        Update_Possible_Moves();
////        return false;

    }


    /**
     *  GetSelectedCor function gets coordinates of the screen clicked position and returns a Coordinate of the
     *  clicked tool , if None of the players tools was selected it returns null
     */
    public Coordinate GetSelectedCor(double clicked_x , double clicked_y)
    {

        class distance {
            /* calculates the distance of the given points coordinates */
            public double calculate_distance(double point_x,double point_y,double clicked_x ,double clicked_y)
            {
                return Math.sqrt(Math.pow(clicked_y-point_y,2)+Math.pow(clicked_x-point_x,2));
            };
        }

        HashMap<Coordinate,HexNode> nodes;
        Iterator it;
        distance distance = new distance();

        if(this.board.getCurrentPlayer() == Players.RED_PLAYER)
        {
            nodes = this.board.Get_Red_Player().getPlayer_tools();
        }
        else {
            nodes = this.board.Get_Green_Player().getPlayer_tools();
        }

        it = nodes.entrySet().iterator();
        /* Iterate over the current player's tools and search if any was selected */
        while(it.hasNext()){
            /* Current tool  */
            Map.Entry mapElement = (Map.Entry)it.next();
            Ellipse2D e = ((Coordinate)mapElement.getKey()).getEllipse();
            if(distance.calculate_distance(e.getX()+App.PIECE_RADIUS,e.getY()+App.PIECE_RADIUS,clicked_x,clicked_y) <= App.PIECE_RADIUS)
            {
                return (Coordinate)mapElement.getKey();
            }
        }
        /* In case no player tool was selected  */
        return null;
    }

    public Coordinate GetSelectedPossibleCor(double Mouse_X , double Mouse_Y)
    {

        class distance {
            /* calculates the distance of the given points coordinates */
            public double calculate_distance(double point_x,double point_y,double clicked_x ,double clicked_y)
            {
                return Math.sqrt(Math.pow(clicked_y-point_y,2)+Math.pow(clicked_x-point_x,2));
            };
        }

        List<Coordinate> nodes;
        Iterator it;
        distance distance = new distance();

        nodes = Get_Possible_Moves();

        it = nodes.iterator();
        /* Iterate over the current player's tools and search if any was selected */
        while(it.hasNext()){
            /* Current tool  */
            Coordinate coordinate = (Coordinate) it.next();
            Ellipse2D e = coordinate.getEllipse();
            if(distance.calculate_distance(e.getX()+App.PIECE_RADIUS,e.getY()+App.PIECE_RADIUS,Mouse_X,Mouse_Y) <= App.PIECE_RADIUS)
            {
                return coordinate;
            }
        }
        /* In case no player tool was selected  */
        return null;
    }






    public void Update_Possible_Moves()
    {

        Player Current_player = board.Get_Current_Player_Player();
        Current_player.Clear_Visited_tools();
        Clear_Possible_Moves();
        Coordinate Selected_tool = Current_player.getSelected_tool();

        /* if none of the tools are selected, clear the possible moves  */
        if(Selected_tool == null)
        {
            Possible_Moves.clear();
        }
        else
        {
            Selected_tool.SetAsVisited();

            int coordinateX = Selected_tool.getX();
            int coordinateY = Selected_tool.getY();
            int coordinateZ = Selected_tool.getZ();
            int max_number_of_neighbors = 6;

            List<List<Coordinate>> directions_passes = new ArrayList<List<Coordinate>>();

            List<Coordinate> neighbors = new ArrayList<Coordinate>();
            Coordinate down_left = new Coordinate(coordinateX-1,coordinateY+1,coordinateZ);
            Coordinate left = new Coordinate(coordinateX-1,coordinateY,coordinateZ+1);
            Coordinate upper_left = new Coordinate(coordinateX,coordinateY-1,coordinateZ+1);
            Coordinate upper_right = new Coordinate(coordinateX+1,coordinateY-1,coordinateZ);
            Coordinate right = new Coordinate(coordinateX+1,coordinateY,coordinateZ-1);
            Coordinate down_right = new Coordinate(coordinateX,coordinateY+1,coordinateZ-1);

            neighbors.add(down_left);
            neighbors.add(left);
            neighbors.add(upper_left);
            neighbors.add(upper_right);
            neighbors.add(right);
            neighbors.add(down_right);

            for(int i=0;i<max_number_of_neighbors;i++)
            {
                directions_passes.add(new ArrayList<Coordinate>());
            }

            for(int neighbor_index = 0; neighbor_index < max_number_of_neighbors; neighbor_index++)
            {
                if(Is_Legal_Move(neighbors.get(neighbor_index)) )
                {
                    Possible_Moves.add(neighbors.get(neighbor_index));
                }
            }

            for(int directions_passes_index = 0; directions_passes_index < directions_passes.size(); directions_passes_index++)
            {
                set_possible_pass(directions_passes.get(directions_passes_index),Selected_tool,true,1);
            }

            for(int directions_passes_index = 0; directions_passes_index < directions_passes.size(); directions_passes_index++)
            {
                Possible_Moves.addAll(directions_passes.get(directions_passes_index));
            }
            System.out.println("");


        }
    }

    /**
     *  gets a coordinate and returns a list consisting all the possible moves from the coordinate's position
     */
    public void set_possible_pass(List<Coordinate> poss_coordinates,Coordinate possible_move,boolean first_time,double jump_number)
    {
        Player Red_player = board.Get_Red_Player();
        Player Green_Player = board.Get_Green_Player();

        Iterator iterator = Red_player.getPlayer_tools().entrySet().iterator();
        boolean red = true;
        /* Iterate and calculate the distance of each red tool , if the distance shows that its in the same line,it will
         * take a possible move accordingly  */
        while (iterator.hasNext())
        {
            Map.Entry mapElement = (Map.Entry)iterator.next();

            if (red)
                Red_player.Clear_Visited_tools();
            else {
                Green_Player.Clear_Visited_tools();

            }
            if(!iterator.hasNext() && red)
            {
                Red_player.Clear_Visited_tools();
                iterator = Green_Player.getPlayer_tools().entrySet().iterator();
                red = false;
            }

            Coordinate current_cor = (Coordinate)mapElement.getKey();

            if (current_cor.equals(possible_move))
            {
                /* set the current tool as visited in the tools set  */
                current_cor.SetAsVisited();
            }
            if(!current_cor.IsVisited())
            {
                double number_of_tools_distance = possible_move.Distance(current_cor)/App.TOOLS_DISTANCE;

                /* if the two coordinates are on a same axis  */
                if (Math.abs(Math.round(number_of_tools_distance) - number_of_tools_distance) <= 0.0001 &&
                        (Math.round(number_of_tools_distance) == Math.round(jump_number) || first_time))
                {
                    /* set the current tool as visited   */
                    current_cor.SetAsVisited();
                    /* get where is the coordinate relative to the tool   */
                    direction relative_direction = possible_move.get_relative_direction_of_cor(current_cor);
                    if(relative_direction != direction.NONE && Is_Closest_In_Axis(possible_move,current_cor,relative_direction))
                    {
                        /* build coordinate number_of_tools_distance from the tool at the same direction   */
                        Coordinate possible_coordinate = new Coordinate(current_cor);
                        for(int i=0;i<number_of_tools_distance;i++)
                        {
                            possible_coordinate.Add_Coordinate(board.Get_Directions().get_direction(relative_direction));
                        }

                        /* check if the move is valid, if it does add to the possible moves pass list (coordinates)  */
                        if(Is_Legal_Move(possible_coordinate) && Is_Closest_In_Axis(current_cor,possible_coordinate,relative_direction)
                                && !poss_coordinates.contains(possible_coordinate))
                        {
                            /* add coordinate to the list of coordinates  */
                            poss_coordinates.add(possible_coordinate);
                            set_possible_pass(poss_coordinates,possible_coordinate,false,number_of_tools_distance);
                            /* set all of the tools to unvisited  */
                            if (red)
                                Red_player.Clear_Visited_tools();
                            else {
                                Green_Player.Clear_Visited_tools();

                            }
                        }

                    }


                }
            }


        }


    }


    public boolean Is_Legal_Move(Coordinate possible_move)
    {
        /* checks whether the given move is already filled with other tool */
        if (board.Get_Red_Player().Contains_Tool(possible_move) || board.Get_Green_Player().Contains_Tool(possible_move))
        {
            return false;
        }
        /* checks whether the given move is in the game bounds */
        return board.Is_In_Board_Bounds(possible_move);

    }

    /**
     *  activated for two tools that are on the same axis
     *  gets selected tool , coordinate, and it's relative direction -> if the is a tool standing in between those
     *  two tools the functions will return false
     */
    public boolean Is_Closest_In_Axis(Coordinate selected_tool,Coordinate coordinate,direction relative_direction)
    {
        Player Red_player = board.Get_Red_Player();
        Player Green_Player = board.Get_Green_Player();

        Iterator iterator = Red_player.getPlayer_tools().entrySet().iterator();
        Iterator iterator2 = Green_Player.getPlayer_tools().entrySet().iterator();

        /* Iterate and calculate the distance of each red tool , if the distance shows that its in the same line,it will
         * take a possible move accordingly  */
        while (iterator.hasNext() && iterator2.hasNext())
        {
            Map.Entry mapElement = (Map.Entry) iterator.next();
            Coordinate red_current_cor = (Coordinate) mapElement.getKey();

            Map.Entry mapElement2 = (Map.Entry) iterator2.next();
            Coordinate green_current_cor = (Coordinate) mapElement2.getKey();

            switch (relative_direction)
            {
                case DOWN_LEFT:
                    if(red_current_cor.getZ() == coordinate.getZ())
                    {
                        if (red_current_cor.getX() > coordinate.getX() && red_current_cor.getX() < selected_tool.getX())
                            return false;

                    }
                    if (green_current_cor.getZ() == coordinate.getZ())
                    {
                        if (green_current_cor.getX() > coordinate.getX() && green_current_cor.getX() < selected_tool.getX())
                            return false;
                    }

                    break;
                case LEFT:
                    if(red_current_cor.getY() == coordinate.getY() )
                    {
                        if (red_current_cor.getX() > coordinate.getX() && red_current_cor.getX() < selected_tool.getX())
                            return false;

                    }
                    if(green_current_cor.getY() == coordinate.getY())
                    {
                        if (green_current_cor.getX() > coordinate.getX() && green_current_cor.getX() < selected_tool.getX())
                            return false;
                    }

                    break;
                case UPPER_LEFT:
                    if(red_current_cor.getX() == coordinate.getX() )
                    {
                        if (red_current_cor.getY() > coordinate.getY() && red_current_cor.getY() < selected_tool.getY())
                            return false;

                    }
                    if(green_current_cor.getX() == coordinate.getX())
                    {
                        if (green_current_cor.getY() > coordinate.getY() && green_current_cor.getY() < selected_tool.getY())
                            return false;
                    }

                    break;
                case UPPER_RIGHT:
                    if(red_current_cor.getZ() == coordinate.getZ() )
                    {
                        if (red_current_cor.getY() > coordinate.getY() && red_current_cor.getY() < selected_tool.getY())
                            return false;

                    }
                    if(green_current_cor.getZ() == coordinate.getZ())
                    {
                        if (green_current_cor.getY() > coordinate.getY() && green_current_cor.getY() < selected_tool.getY())
                            return false;
                    }

                    break;
                case RIGHT:
                    if(red_current_cor.getY() == coordinate.getY())
                    {
                        if (red_current_cor.getX() < coordinate.getX() && red_current_cor.getX() > selected_tool.getX())
                            return false;

                    }
                    if(green_current_cor.getY() == coordinate.getY())
                    {
                        if (green_current_cor.getX() < coordinate.getX() && green_current_cor.getX() > selected_tool.getX())
                            return false;
                    }

                    break;
                case DOWN_RIGHT:
                    if(red_current_cor.getX() == coordinate.getX())
                    {
                        if (red_current_cor.getY() < coordinate.getY() && red_current_cor.getY() > selected_tool.getY())
                            return false;

                    }
                    if(green_current_cor.getX() == coordinate.getX())
                    {
                        if (green_current_cor.getY() < coordinate.getY() && green_current_cor.getY() > selected_tool.getY())
                            return false;
                    }

                    break;







            }

        }
        return true;

    }


    public boolean Check_For_Win()
    {
        Players Current_Player = board.getCurrentPlayer();
        //add initial nodes
        if(Current_Player == Players.RED_PLAYER)
        {
            Player Red_Player = board.Get_Red_Player();

            for (int z = 4; z > 0; z--) {
                for (int x = 4; x > (4 - z); x--) {
                    Coordinate coordinate = new Coordinate(x, -(z + x), z);
                    if (!Red_Player.Contains_Tool(coordinate))
                    {
                        return false;
                    }
                }
            }
        }
        else if(Current_Player == Players.GREEN_PLAYER)
        {
            Player Green_Player = board.Get_Green_Player();
            for (int z = -4; z < 0; z++) {
                for (int x = -4; x < -(4 + z); x++) {
                    Coordinate coordinate = new Coordinate(x, -(z + x), z);
                    if (!Green_Player.Contains_Tool(coordinate))
                    {
                        return false;
                    }
                }
            }
        }
        return true;



    }



    private void initBoard() {
        this.board = new GameBoard();
    }

    public GameBoard Get_Board()
    {
        return this.board;
    }

    public void Clear_Possible_Moves()
    {
        this.Possible_Moves.clear();
    }

    public ArrayList<Coordinate> Get_Possible_Moves()
    {
        return this.Possible_Moves;
    }

}
