package pieces;

import vector.vector2;
import java.util.ArrayList;

public class king extends a_piece
{
    //constructor
    public king(String color, vector2 position)
    {
        super(color, position);

        //setting up the pieces.pawn should indicate that it has not moved.
        super.set_has_moved(false);

        //setting the type of the pieces.piece for notation
        super.set_type("k");

        //calculate the possible moves.
        calculate_possible_moves();
    }

    //calculating the moves for a pieces.king.
    @Override public void calculate_possible_moves()
    {
        ArrayList<vector2> possible_moves = new ArrayList<>();

        //diagonal king moves.
        possible_moves.add(new vector2(super.get_position().x + 1, super.get_position().y + 1));
        possible_moves.add(new vector2(super.get_position().x - 1, super.get_position().y - 1));
        possible_moves.add(new vector2(super.get_position().x + 1, super.get_position().y - 1));
        possible_moves.add(new vector2(super.get_position().x - 1, super.get_position().y + 1));

        //horizontal king moves.
        possible_moves.add(new vector2(super.get_position().x + 1, super.get_position().y));
        possible_moves.add(new vector2(super.get_position().x - 1, super.get_position().y));

        //vertical king moves.
        possible_moves.add(new vector2(super.get_position().x, super.get_position().y + 1));
        possible_moves.add(new vector2(super.get_position().x, super.get_position().y - 1));
    }
}