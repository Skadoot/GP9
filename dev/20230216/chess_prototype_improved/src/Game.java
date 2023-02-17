import vector.vector2;

import java.util.ArrayList;

public class game
{
    //this is the order of operations that will happen every turn
    private board game_board;

    //this is the player taking the current move.
    private char attacking_player;

    //keep track of which move it is.
    private int move_count;

    public game(String board_state)
    {
        game_board = new board(board_state);
    }

    public void move()
    {
        //determine the player making the current move.
        determine_current_player();

        //filters the moves for every piece of the current player.
        game_board.find_legal_moves(attacking_player);

        //wait for the UI to give us a selected piece, here we would set it to be the coordinate that the ui passes back to us.
        vector2 selected_piece_coordinate = new vector2();
        while (game_board.get_piece(selected_piece_coordinate) == null || game_board.get_piece(selected_piece_coordinate).get_color() != attacking_player)
        {
            selected_piece_coordinate = new vector2();
        }

        //if a piece is selected return its legal moves to the UI. then the UI draws its legal moves.
        ArrayList<vector2> selected_piece_legal_moves = game_board.get_piece(selected_piece_coordinate).get_possible_moves();

        //UI waits for the player to select a move square.
        vector2 move_square = new vector2();
        boolean legal_move_made = false;

        while (!legal_move_made)
        {
            move_square = new vector2();

            if (game_board.get_piece(selected_piece_coordinate).get_possible_moves().contains(move_square))
            {
                legal_move_made = true;
                game_board.move_piece(game_board.get_piece(selected_piece_coordinate), move_square);
                move_count += (attacking_player == 'b') ? 1 : 0;
            }
            else if (game_board.get_piece(move_square).get_color() == attacking_player)
            {
                selected_piece_coordinate = game_board.get_piece(move_square).get_position();
            }
        }
    }

    //find out which player is moving.
    private void determine_current_player()
    {
        String board_state = game_board.get_board_state();
        //check the board state string to find which player's turn it is.
        for (int i = 0; i < board_state.length(); i++)
        {
            if (board_state.charAt(i) == ' ')
            {
                attacking_player = board_state.charAt(i + 1);
                return;
            }
        }
    }
}
