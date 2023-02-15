import pieces.*;
import vector.vector2;

import java.util.HashMap;
import java.util.Map;

public class board
{
    //this is the current piece that is selected and to be highlighted if it is not null.
    private a_piece selected_piece;

    //the logical representation of the board.
    private a_piece[][] board;

    //edward forsyth notation of the board for saving and loading board states.
    private String board_state;

    //map of threatened squares, updated at the start of each turn.
    Map<vector2, vector2> threatened_squares;

    //constructor for the board.
    public board(String board_state)
    {
        this.board_state = board_state;
        initialise_board();
    }

    //to set up the board either when starting a new game or when loading a saved game.
    private void initialise_board()
    {
        //initialise the board with the board_state, need to parse the string here.
        for (int i = 0; i < 8; i++)
        {
            for (int j = 0; j < 8; j++)
            {
                board[i][j] = null;
            }
        }
    }

    //update the notation for the board state.
    private void update_board_state()
    {
        //initialising the new board state string.
        String new_board_state = "";

        //read the board and create a new board state string.
        board_state = new_board_state;
    }

    //return the current state of the board.
    private String get_board_state()
    {
        return board_state;
    }

    //method to select a piece on a particular coordinate.
    public void select_piece_on_board(vector2 coordinate)
    {
        //setting the current selected piece.
        selected_piece = board[coordinate.x][coordinate.y];
    }

    //return the current selected piece.
    public a_piece get_selected_piece()
    {
        //we can use this higher up to check if the piece is null or not or if it's the wrong color.
        return selected_piece;
    }

    //move selected piece.
    public void move_selected_piece(vector2 coordinate)
    {
        //sets the square that the piece is currently on to null.
        board[selected_piece.get_position().x][selected_piece.get_position().y] = null;

        //set the new square to be the piece.
        board[coordinate.x][coordinate.y] = selected_piece;

        //update the piece's position.
        selected_piece.move_piece(coordinate);
    }

    //filtering for legal moves relative to the current board state and current player, called at the start of each turn.
    public void filter_legal_moves()
    {
        //create a map of threatened squares, so we can filter out differently if we are in check or are going to be in check.
        create_check_map();

        //loop over each piece that is of a particular color
        for (int i = 0; i < 8; i++)
        {
            for (int j = 0; j < 8; j++)
            {
                //filter out its legal moves relative to the current board state.

                //first filter squares which have pieces of its own color on already.

                //second filter out squares that are blocked (knight excluded).

                //filter out moves that would leave the player in check.

                System.out.println(board[i][j].get_type());
            }
        }
    }

    //create the map of 'threatened' squares to determine if they are in check.
    public void create_check_map()
    {
        //initialising the map.
        Map<vector2, vector2> threatened_squares = new HashMap<>();

        //here we would create the check map.

        //update the threatened squares.
        this.threatened_squares = threatened_squares;
    }
}
