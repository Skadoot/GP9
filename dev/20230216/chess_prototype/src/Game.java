import vector.vector2;

import java.util.ArrayList;

public class Game
{
    //this is the order of operations that will happen every turn
    board game_board;

    public void move()
    {
        //filters the moves for every piece of the current player.
        game_board.filter_legal_moves();

        //wait for the UI to give us a selected piece, here we would set it to be the coordinate that the ui passes back to us.
        vector2 selected_piece_coordinate = new vector2();
        game_board.select_piece_on_board(selected_piece_coordinate);

        //if a piece is selected return its legal moves to the UI. then the UI draws its legal moves.
        ArrayList<vector2> selected_piece_legal_moves = game_board.get_selected_piece().get_possible_moves();

        //pass the list to the ui to draw. the UI waits for the player to select a move square.

        //if the square is empty it's a legal square for the piece.
            //move the piece and then end the function.
            //the game board will keep track of which player is to make the next move.

        //if the square has a piece on it that is the same color as the current players pieces then we select that piece instead.
    }
}
