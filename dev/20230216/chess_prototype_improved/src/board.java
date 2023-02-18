import pieces.piece;
import vector.vector2;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class board
{
    //the logical representation of the board.
    private final piece[][] board;

    //forsyth edwards notation of the board for saving and loading board states.
    private String board_state;

    //map of squares threatened by defending player, updated at the start of each turn.
    Map<vector2, vector2> threatened_squares;

    //move directions.
    vector2[] move_directions = { new vector2(0, 1),  new vector2(0, -1), new vector2(1, 0), new vector2(-1, 0), new vector2(1, 1), new vector2(-1, -1), new vector2(1, -1), new vector2(-1, 1)};

    //constructor for the board.
    public board(String initial_board_state)
    {
        //initialize the logical board.
        board = new piece[8][8];

        //set the string of the board state to the one given.
        this.board_state = initial_board_state;

        //initialise the state of the board with the new string.
        int file = 0, rank = 7;

        for (int i = 0; i < board_state.length(); i++) {
            if (board_state.charAt(i) != ' ') {
                if (board_state.charAt(i) == '/') {
                    file = 0; rank--;
                } else {
                    if (Character.isDigit(board_state.charAt(i))) {
                        file += board_state.charAt(i);
                    } else {
                        if (!Character.isLowerCase(board_state.charAt(i))) {
                            board[file][rank] = new piece('w', new vector2(file, rank), board_state.charAt(i));
                        } else {
                            board[file][rank] = new piece('b', new vector2(file, rank), board_state.charAt(i));
                        }
                        file++;
                    }
                }
            } else {
                return;
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
        for (int file = 0; file < 8; file++) {
            for (int rank = 0; rank < 8; rank++) {
                piece p = board[file][rank];

                if (p.get_type() == ' ') { continue; }
                if (p.get_color() != attacking_player) { continue; }

                if (p.get_type() == 'p') {
                    p.set_possible_moves(find_pawn_legal_moves(p));
                } else if (p.get_type() == 'b') {
                    p.set_possible_moves(find_bishop_legal_moves(p));
                } else if (p.get_type() == 'n') {
                    p.set_possible_moves(find_knight_legal_moves(p));
                } else if (p.get_type() == 'r') {
                    p.set_possible_moves(find_rook_legal_moves(p));
                } else if (p.get_type() == 'q') {
                    p.set_possible_moves(find_queen_legal_moves(p));
                } else if (p.get_type() == 'k') {
                    p.set_possible_moves(find_king_legal_moves(p));
                }
            }
        }
    }

    private ArrayList<vector2> find_pawn_legal_moves(piece p) {
        ArrayList<vector2> pawn_legal_moves = new ArrayList<>();
        return pawn_legal_moves;
    }

    private ArrayList<vector2> find_knight_legal_moves(piece p) {
        ArrayList<vector2> pawn_knight_moves = new ArrayList<>();
        return pawn_knight_moves;
    }

    private ArrayList<vector2> find_bishop_legal_moves(piece p) {
        vector2[] bishop_directions = { new vector2(1, 1), new vector2(-1, -1), new vector2(1, -1), new vector2(-1, 1)} ;
        ArrayList<vector2> bishop_legal_moves = new ArrayList<>();

        for (vector2 move_direction : bishop_directions) {
            for (int n = 0; n < 7; n++) {
                vector2 move = new vector2();
                move.x = p.get_position().x + (move_direction.x * n);
                move.y = p.get_position().y + (move_direction.y * n);

                if (move.x > 7 || move.y > 7 || move.x < 0 || move.y < 0) { break; }

                if (get_piece(move) == null) {
                    bishop_legal_moves.add(move);
                } else if (!(get_piece(move) == null) && !(get_piece(move).get_color() == p.get_color())) {
                    bishop_legal_moves.add(move);
                    break;
                } else if (!(get_piece(move) == null) && (get_piece(move).get_color() == p.get_color())) {
                    break;
                }
            }
        }
        return bishop_legal_moves;
    }

    private ArrayList<vector2> find_rook_legal_moves(piece p) {
        vector2[] rook_directions = { new vector2(0, 1),  new vector2(0, -1), new vector2(1, 0), new vector2(-1, 0)} ;
        ArrayList<vector2> rook_legal_moves = new ArrayList<>();

        for (vector2 move_direction : rook_directions) {
            for (int n = 0; n < 7; n++) {
                vector2 move = new vector2();
                move.x = p.get_position().x + (move_direction.x * n);
                move.y = p.get_position().y + (move_direction.y * n);

                if (move.x > 7 || move.y > 7 || move.x < 0 || move.y < 0) { break; }

                if (get_piece(move) == null) {
                    rook_legal_moves.add(move);
                } else if (!(get_piece(move) == null) && !(get_piece(move).get_color() == p.get_color())) {
                    rook_legal_moves.add(move);
                    break;
                } else if (!(get_piece(move) == null) && (get_piece(move).get_color() == p.get_color())) {
                    break;
                }
            }
        }
        return rook_legal_moves;
    }

    private ArrayList<vector2> find_queen_legal_moves(piece p) {
        vector2[] queen_directions = { new vector2(0, 1),  new vector2(0, -1), new vector2(1, 0), new vector2(-1, 0), new vector2(1, 1), new vector2(-1, -1), new vector2(1, -1), new vector2(-1, 1)};
        ArrayList<vector2> queen_legal_moves = new ArrayList<>();

        for (vector2 move_direction : queen_directions) {
            for (int n = 0; n < 7; n++) {
                vector2 move = new vector2();
                move.x = p.get_position().x + (move_direction.x * n);
                move.y = p.get_position().y + (move_direction.y * n);

                if (move.x > 7 || move.y > 7 || move.x < 0 || move.y < 0) { break; }

                if (get_piece(move) == null) {
                    queen_legal_moves.add(move);
                } else if (!(get_piece(move) == null) && !(get_piece(move).get_color() == p.get_color())) {
                    queen_legal_moves.add(move);
                    break;
                } else if (!(get_piece(move) == null) && (get_piece(move).get_color() == p.get_color())) {
                    break;
                }
            }
        }
        return queen_legal_moves;
    }

    private ArrayList<vector2> find_king_legal_moves(piece p) {
        vector2[] king_directions = { new vector2(0, 1),  new vector2(0, -1), new vector2(1, 0), new vector2(-1, 0), new vector2(1, 1), new vector2(-1, -1), new vector2(1, -1), new vector2(-1, 1)};
        ArrayList<vector2> king_legal_moves = new ArrayList<>();

        for (vector2 move_direction : king_directions) {
            vector2 move = new vector2();
            move.x = p.get_position().x + (move_direction.x);
            move.y = p.get_position().y + (move_direction.y);

            if (move.x > 7 || move.y > 7 || move.x < 0 || move.y < 0) { break; }

            if (get_piece(move) == null) {
                king_legal_moves.add(move);
            } else if (!(get_piece(move) == null) && !(get_piece(move).get_color() == p.get_color())) {
                king_legal_moves.add(move);
                break;
            } else if (!(get_piece(move) == null) && (get_piece(move).get_color() == p.get_color())) {
                break;
            }
        }
        return king_legal_moves;
    }

    //create the map of 'threatened' squares to determine if they are in check.
    private void create_check_map(char attacking_player)
    {
        //initialising the map.
        Map<vector2, vector2> threatened_squares = new HashMap<>();

        //here we would create the check map.
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (!(board[i][j].get_color() == attacking_player)) {
                    //add each of the pieces legal moves to the map. (excluding if the move would put the player in check)
                    //add the vectors to the map.
                }
            }
        }

        //update the threatened squares.
        this.threatened_squares = threatened_squares;
    }
}