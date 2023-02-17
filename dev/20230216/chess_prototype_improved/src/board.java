import pieces.*;
import vector.vector2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class board
{
    //the logical representation of the board.
    private final piece[][] board;

    //forsyth edwards notation of the board for saving and loading board states.
    private String board_state;

    //map of squares threatened by defending player, updated at the start of each turn.
    Map<vector2, vector2> threatened_squares;

    //constructor for the board.
    public board(String initial_board_state)
    {
        //initialize the logical board.
        board = new piece[8][8];

        //set the string of the board state to the one given.
        this.board_state = initial_board_state;

        //initialise the state of the board with the new string.
        int file = 0, rank = 7;

        for (int i = 0; i < board_state.length(); i++)
        {
            if (board_state.charAt(i) == '/')
            {
                file = 0;
                rank--;
            }
            else
            {
                if (Character.isDigit(board_state.charAt(i)))
                {
                    file += board_state.charAt(i);
                }
                else
                {
                    board[file][rank] = (!Character.isLowerCase(board_state.charAt(i))) ? new piece('w', new vector2(file, rank), board_state.charAt(i)) : new piece('b', new vector2(file, rank), board_state.charAt(i));
                    file++;
                }
            }
        }
    }

    //return a piece on the board.
    public piece get_piece(vector2 coordinate)
    {
        //return a piece on the board.
        return board[coordinate.x][coordinate.y];
    }

    //move selected piece.
    public void move_piece(piece selected_piece, vector2 coordinate)
    {
        //sets the square that the piece is currently on to null.
        board[selected_piece.get_position().x][selected_piece.get_position().y] = null;

        //set the new square to be the piece.
        board[coordinate.x][coordinate.y] = selected_piece;

        //update the piece's position.
        selected_piece.set_position(coordinate);

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

        //we will change the current player by changing the symbol in the string which indicates which player it is

        //set the bard state.
        board_state = new_board_state;
    }

    //return the current state of the board.
    public String get_board_state()
    {
        //returns the notation for the state of the board
        return board_state;
    }

    //filtering for legal moves relative to the current board state and current player, called at the start of each turn.
    public void find_legal_moves(char attacking_player)
    {
        //create a map of threatened squares, so we can filter out differently if we are in check or are going to be in check.
        create_check_map(attacking_player);

        //loop over each piece that is of a particular color
        for (int i = 0; i < 8; i++)
        {
            for (int j = 0; j < 8; j++)
            {
                if (board[i][j].get_color() == attacking_player)
                {
                    //set up the generated list of legal moves.
                    ArrayList<vector2> legal_moves = new ArrayList<>();

                    //create the list of legal moves.

                    //set the piece's legal moves list to the one generated.
                    board[i][j].set_possible_moves(legal_moves);
                }
            }
        }
    }

    //create the map of 'threatened' squares to determine if they are in check.
    private void create_check_map(char attacking_player)
    {
        //initialising the map.
        Map<vector2, vector2> threatened_squares = new HashMap<>();

        //here we would create the check map.
        for (int i = 0; i < 8; i++)
        {
            for (int j = 0; j < 8; j++)
            {
                if (!(board[i][j].get_color() == attacking_player))
                {
                    //add each of the pieces legal moves to the map. (excluding if the move would put the player in check)
                    //add the vectors to the map.
                }
            }
        }

        //update the threatened squares.
        this.threatened_squares = threatened_squares;
    }
}