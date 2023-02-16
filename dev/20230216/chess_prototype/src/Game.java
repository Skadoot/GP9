public class Game
{
    //this is the order of operations that will happen every turn

    board game_board;

    public void move()
    {
        //filters the moves for every piece of the current player.
        game_board.filter_legal_moves();

        //wait for the UI to give us a selected piece.

        //if a piece is selected return its legal moves to the UI.

        //the UI draws its legal moves.

        //wait for the UI to give us a selected move square.

        //if the square is empty it's a legal square for the piece.
            //move the piece and then end the function.
            //the game board will keep track of which player is to make the next move.
        //if the square has a piece on it that is the same color as the current players pieces then we select that piece instead.
    }
}
