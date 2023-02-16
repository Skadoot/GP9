package pieces;

import vector.vector2;
import java.util.ArrayList;

public class rook extends a_piece
{
    //constructor
    public rook(String color, vector2 position)
    {
        super(color, position);

        //setting up the pieces.pawn should indicate that it has not moved.
        super.set_has_moved(false);

        //setting the type of the pieces.piece for notation
        super.set_type("r");

        //calculate the possible moves.
        calculate_possible_moves();
    }

    //calculating the moves for a pieces.rook.
    @Override public void calculate_possible_moves()
    {
        ArrayList<vector2> possible_moves = new ArrayList<>();

        for (int i = 1; i < 8; i++)
        {
            //add all the squares in every horizontal.
            possible_moves.add(new vector2(super.get_position().x + i, super.get_position().y));
            possible_moves.add(new vector2(super.get_position().x - i, super.get_position().y));

            //add all the squares in every vertical.
            possible_moves.add(new vector2(super.get_position().x, super.get_position().y + i));
            possible_moves.add(new vector2(super.get_position().x, super.get_position().y - i));
        }

        super.set_possible_moves(possible_moves);
    }
}
