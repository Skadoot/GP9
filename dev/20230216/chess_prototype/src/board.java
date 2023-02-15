import pieces.*;
import vector.vector2;

import java.util.HashMap;
import java.util.Map;

public class board
{
    //to keep track of which move it is.
    private int move_count;

    //to keep track of which half of the turn it is.
    private int half_of_move;

    //this is the current piece that is selected and to be highlighted if it is not null.
    private a_piece selected_piece;

    //the logical representation of the board.
    private a_piece[][] board;

    //edward forsyth notation of the board for saving and loading board states.
    private String board_state;

    //this is the player taking the current move.
    private player attacking_player;

    //this is the player waiting for the other players next move.
    private player defending_player;

    //map of squares threatened by defending player, updated at the start of each turn.
    Map<vector2, vector2> threatened_squares;

    //constructor for the board.
    public board(String board_state)
    {
        //set the string of the board state to the one given.
        this.board_state = board_state;

        //parse the string to find out which move it is.

        //parse the string to find out which half of the move it is.
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

        //update half of move after the move is done.
        if (half_of_move == 1)
        {
            half_of_move = 2;
        }
        else if (half_of_move == 2)
        {
            half_of_move = 1;
        }

        //get the new board state string.
        update_board_state();
    }

    //update the notation for the board state.
    private void update_board_state()
    {
        //initialising the new board state string.
        String new_board_state = "";

        //read the board and update the string to represent the board.
        new_board_state = "t";

        //set the bard state.
        board_state = new_board_state;
    }

    //return the current state of the board.
    public String get_board_state()
    {
        //returns the notation for the state of the board
        return board_state;
    }

    //for saving the state of the board
    public void save_board_state()
    {
        //code for saving the state of the board.
    }

    //filtering for legal moves relative to the current board state and current player, called at the start of each turn.
    public void filter_legal_moves()
    {
        //determine the player making the move this turn.
        determine_current_player();

        //create a map of threatened squares, so we can filter out differently if we are in check or are going to be in check.
        create_check_map();

        //loop over each piece that is of a particular color
        for (int i = 0; i < 8; i++)
        {
            for (int j = 0; j < 8; j++)
            {
                if (board[i][j].get_color().equals(attacking_player.get_color()))
                {
                    board[i][j].calculate_possible_moves();
                    filter_piece_legal_moves(board[i][j]);
                }
            }
        }
    }

    //filter the legal moves for a given piece
    private void filter_piece_legal_moves(a_piece piece)
    {
        for (int i = 0; i < piece.get_possible_moves().size(); i++)
        {
            //first filter squares which have pieces of its own color on already.

            //second filter out squares that are blocked (knight excluded).

            //filter out moves that would leave the player in check.
        }
    }

    //find out which player is moving.
    private void determine_current_player()
    {
        //if it's the first half of the move the attacking player is white.
        if (half_of_move == 1)
        {
            attacking_player = new player("white");
            defending_player = new player("black");
        }
        //if it's the second half of the move the attacking player is black.
        else if (half_of_move == 2)
        {
            attacking_player = new player("black");
            defending_player = new player("white");
        }
    }

    //create the map of 'threatened' squares to determine if they are in check.
    public void create_check_map()
    {
        //initialising the map.
        Map<vector2, vector2> threatened_squares = new HashMap<>();

        //here we would create the check map.
        for (int i = 0; i < 8; i++)
        {
            for (int j = 0; j < 8; j++)
            {
                if (board[i][j].get_color().equals(defending_player.get_color()))
                {
                    //first filter squares which have pieces of its own color on already.

                    //second filter out squares that are blocked (knight excluded).

                    //add the vectors to the map.
                }
            }
        }

        //update the threatened squares.
        this.threatened_squares = threatened_squares;
    }
}