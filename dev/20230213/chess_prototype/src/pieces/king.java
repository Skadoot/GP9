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
}
