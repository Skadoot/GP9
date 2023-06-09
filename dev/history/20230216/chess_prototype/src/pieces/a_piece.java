package pieces;

import vector.vector2;
import java.util.ArrayList;

public class a_piece
{
    //storing the pieces color.
    private final String color;

    //storing the type of the pieces.piece for notation.
    private String type;

    //the pieces position.
    private vector2 position;

    //the possible moves for a pieces.piece, ignoring if the moves are legal or not.
    private ArrayList<vector2> possible_moves;

    //to keep track if the pieces.a_piece has moved during the course of the game.
    private boolean has_moved;

    //constructor for pieces.
    public a_piece(String color, vector2 position)
    {
        this.color = color;
        this.position = position;
    }

    //for updating the pieces.piece's coordinates on the board.
    public void move_piece(vector2 new_position)
    {
        //setting the old position to the new position.
        set_position(new_position);

        //if we move the pieces.piece then we need to update the has_moved value.
        if(!has_moved)
        {
            has_moved = true;
        }
    }

    //getter for the color, returns color
    public String get_color()
    {
        return color;
    }

    //getter for the position vector.vector2, returns the position.
    public vector2 get_position()
    {
        return position;
    }

    //setter for the position.
    public void set_position(vector2 position)
    {
        this.position = position;
    }

    //getter for the possible moves, returns an arrayList of vector2s.
    public ArrayList<vector2> get_possible_moves()
    {
        return possible_moves;
    }

    //setter for the possible moves.
    public void set_possible_moves(ArrayList<vector2> possible_moves)
    {
        this.possible_moves = possible_moves;
    }

    //getter for the type, returns type.
    public String get_type()
    {
        return type;
    }

    //setter for the type.
    public void set_type(String type)
    {
        this.type = type;
    }

    //for checking if the pieces.piece has moved.
    public boolean has_moved()
    {
        return has_moved;
    }

    //for updating whether the pieces.piece has moved.
    public void set_has_moved(boolean has_moved)
    {
        this.has_moved = has_moved;
    }

    public void calculate_possible_moves()
    {

    }

    public void remove_possible_move(int index)
    {
        possible_moves.remove(index);
    }

}