package pieces;

import vector.vector2;
import java.util.ArrayList;

public class pawn extends a_piece
{
    //constructor
    public pawn(String color, vector2 position)
    {
        super(color, position);

        //setting up the pieces.pawn should indicate that it has not moved.
        super.set_has_moved(false);

        //setting the type of the pieces.piece for notation
        super.set_type("");

        //calculate the possible moves.
        calculate_possible_moves();
    }

    //calculating the moves for a pieces.pawn.
    @Override public void calculate_possible_moves()
    {
        //create a new empty list of moves.
        ArrayList<vector2> possible_moves = new ArrayList<>();

        //capturing squares.
        possible_moves.add(new vector2(super.get_position().x - 1, super.get_position().y + 1));
        possible_moves.add(new vector2(super.get_position().x + 1, super.get_position().y + 1));

        //advancing squares.
        possible_moves.add(new vector2(super.get_position().x, super.get_position().y + 1));

        //if it has not moved then it should add the position 2 squares forward to the list.
        if (!super.has_moved())
        {
            possible_moves.add(new vector2(super.get_position().x, super.get_position().y + 2));
        }

        super.set_possible_moves(possible_moves);
    }
}