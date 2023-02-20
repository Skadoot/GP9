import pieces.piece;
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

    //both king positions.
    private vector2 w_king_position;
    private vector2 b_king_position;

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
                        vector2 board_position = new vector2(file, rank);
                        if (!Character.isLowerCase(board_state.charAt(i))) {
                            board[file][rank] = new piece('w', board_position, board_state.charAt(i));
                            if(board_state.charAt(i) == 'k'){
                                w_king_position = board_position;
                            }
                        } else {
                            board[file][rank] = new piece('b', board_position, board_state.charAt(i));
                            if(board_state.charAt(i) == 'k'){
                                b_king_position = board_position;
                            }
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

        if(selected_piece.get_type() == 'k') {
            if(selected_piece.get_color() == 'w') {
                w_king_position = coordinate;
            } else {
                b_king_position = coordinate;
            }
        }

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
        //loop over each piece that is of a particular color
        for (int file = 0; file < 8; file++) {
            for (int rank = 0; rank < 8; rank++) {

                if (get_piece(new vector2(file, rank)) == null) {continue;}

                piece p = board[file][rank];

                if (p.get_color() != attacking_player) { continue; }

                switch (p.get_type()) {
                    case 'p' :
                        p.set_possible_moves(find_pawn_legal_moves(p, false));
                        break;
                    case 'n' :
                        p.set_possible_moves(find_knight_legal_moves(p, false));
                        break;
                    case 'b' :
                        p.set_possible_moves(find_bishop_legal_moves(p, false));
                        break;
                    case 'r' :
                        p.set_possible_moves(find_rook_legal_moves(p, false));
                        break;
                    case 'q' :
                        p.set_possible_moves(find_queen_legal_moves(p, false));
                        break;
                    case 'k' :
                        p.set_possible_moves(find_king_legal_moves(p));
                        break;
                }
            }
        }
    }

    private ArrayList<vector2> find_pawn_legal_moves(piece p, boolean check_map) {
        ArrayList<vector2> pawn_legal_moves = new ArrayList<>();

        vector2 move = new vector2();
        move.y = (p.get_color() == 'w') ? 1 : -1;

        vector2 double_advance = new vector2(move.x, move.y * 2);

        vector2 left_attack = new vector2(move.x--, move.y);
        vector2 right_attack = new vector2(move.x++, move.y);

        if (get_piece(move) == null) {
            if (check_map) {
                pawn_legal_moves.add(move);
            } else {
                if (is_move_safe(p, move)) {
                    pawn_legal_moves.add(move);
                }
            }
            if (get_piece(double_advance) == null && !p.has_moved()) {
                if (check_map) {
                    pawn_legal_moves.add(double_advance);
                } else {
                    if (is_move_safe(p, double_advance)) {
                        pawn_legal_moves.add(double_advance);
                    }
                }
            }
        }
        if (get_piece(left_attack) == null) {
            if (check_map) {
                pawn_legal_moves.add(left_attack);
            } else {
                if (is_move_safe(p, left_attack)) {
                    pawn_legal_moves.add(left_attack);
                }
            }
        }
        if (get_piece(right_attack) == null) {
            if (check_map) {
                pawn_legal_moves.add(right_attack);
            } else {
                if (is_move_safe(p, right_attack)) {
                    pawn_legal_moves.add(right_attack);
                }
            }
        }
        return pawn_legal_moves;
    }

    private ArrayList<vector2> find_knight_legal_moves(piece p, boolean check_map) {
        vector2[] knight_directions = { new vector2(2, 1),  new vector2(1, 2), new vector2(-2, 1), new vector2(-1, 2), new vector2(2, -1), new vector2(1, -2), new vector2(-2, -1), new vector2(-1, -2)};
        ArrayList<vector2> knight_legal_moves = new ArrayList<>();

        for (vector2 move_direction : knight_directions) {
            vector2 move = new vector2();

            move.x = p.get_position().x + move_direction.x;
            move.y = p.get_position().y + move_direction.y;

            if (move.x > 7 || move.y > 7 || move.x < 0 || move.y < 0) {continue;}

            if (get_piece(move) == null) {
                if (check_map) {
                    knight_legal_moves.add(move);
                } else {
                    if (is_move_safe(p, move)) {
                        knight_legal_moves.add(move);
                    }
                }
            } else if (get_piece(move).get_color() != p.get_color()) {
                if (check_map) {
                    knight_legal_moves.add(move);
                } else {
                    if (is_move_safe(p, move)) {
                        knight_legal_moves.add(move);
                    }
                }
            }
        }
        return knight_legal_moves;
    }

    private ArrayList<vector2> find_bishop_legal_moves(piece p, boolean check_map) {
        vector2[] bishop_directions = { new vector2(1, 1), new vector2(-1, -1), new vector2(1, -1), new vector2(-1, 1)};
        ArrayList<vector2> bishop_legal_moves = new ArrayList<>();

        for (vector2 move_direction : bishop_directions) {
            for (int n = 0; n < 7; n++) {
                vector2 move = new vector2();
                move.x = p.get_position().x + (move_direction.x * n);
                move.y = p.get_position().y + (move_direction.y * n);

                if (move.x > 7 || move.y > 7 || move.x < 0 || move.y < 0) { break; }

                if (get_piece(move) == null) {
                    if (check_map) {
                        bishop_legal_moves.add(move);
                    } else if (is_move_safe(p, move)) {
                        bishop_legal_moves.add(move);
                    }
                } else if (!(get_piece(move).get_color() == p.get_color())) {
                    if (check_map) {
                        bishop_legal_moves.add(move);
                    }  else if (is_move_safe(p, move)) {
                        bishop_legal_moves.add(move);
                    }
                    break;
                } else if ((get_piece(move).get_color() == p.get_color())) {
                    break;
                }
            }
        }
        return bishop_legal_moves;
    }

    private ArrayList<vector2> find_rook_legal_moves(piece p, boolean check_map) {
        vector2[] rook_directions = { new vector2(0, 1),  new vector2(0, -1), new vector2(1, 0), new vector2(-1, 0)};
        ArrayList<vector2> rook_legal_moves = new ArrayList<>();

        for (vector2 move_direction : rook_directions) {
            for (int n = 0; n < 7; n++) {

                vector2 move = new vector2();
                move.x = p.get_position().x + (move_direction.x * n);
                move.y = p.get_position().y + (move_direction.y * n);

                if (move.x > 7 || move.y > 7 || move.x < 0 || move.y < 0) { break; }

                if (get_piece(move) == null) {
                    if (check_map) {
                        rook_legal_moves.add(move);
                    } else if (is_move_safe(p, move)) {
                        rook_legal_moves.add(move);
                    }
                } else if (!(get_piece(move).get_color() == p.get_color())) {
                    if (check_map) {
                        rook_legal_moves.add(move);
                    } else if (is_move_safe(p, move)) {
                        rook_legal_moves.add(move);
                    }
                    break;
                } else if ((get_piece(move).get_color() == p.get_color())) {
                    break;
                }
            }
        }
        return rook_legal_moves;
    }

    private ArrayList<vector2> find_queen_legal_moves(piece p, boolean check_map) {
        vector2[] queen_directions = { new vector2(0, 1),  new vector2(0, -1), new vector2(1, 0), new vector2(-1, 0), new vector2(1, 1), new vector2(-1, -1), new vector2(1, -1), new vector2(-1, 1)};
        ArrayList<vector2> queen_legal_moves = new ArrayList<>();

        for (vector2 move_direction : queen_directions) {
            for (int n = 0; n < 7; n++) {

                vector2 move = new vector2();
                move.x = p.get_position().x + (move_direction.x * n);
                move.y = p.get_position().y + (move_direction.y * n);

                if (move.x > 7 || move.y > 7 || move.x < 0 || move.y < 0) { break; }

                if (get_piece(move) == null) {
                    if (check_map) {
                        queen_legal_moves.add(move);
                    } else if (is_move_safe(p, move)) {
                        queen_legal_moves.add(move);
                    }
                } else if (!(get_piece(move).get_color() == p.get_color())) {
                    if (check_map) {
                        queen_legal_moves.add(move);
                    } else if (is_move_safe(p, move)) {
                        queen_legal_moves.add(move);
                    }
                    break;
                } else if ((get_piece(move).get_color() == p.get_color())) {
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

            if (move.x > 7 || move.y > 7 || move.x < 0 || move.y < 0) { continue; }
            if (get_piece(move) == null) {
                king_legal_moves.add(move);
            } else if (!(get_piece(move).get_color() == p.get_color())) {
                king_legal_moves.add(move);
            }
        }
        return king_legal_moves;
    }

    private boolean is_move_safe(piece p, vector2 move) {
        board b = this;
        b.move_piece(b.get_piece(p.get_position()), move);
        return !b.is_in_check(p.get_color());
    }

    private boolean is_in_check(char player) {
        Map<vector2, vector2> check_map = create_check_map(player);
        if (player == 'w' && check_map.containsValue(w_king_position)) {
            return true;
        } else return player == 'b' && check_map.containsValue(b_king_position);
    }

    //create the map of 'threatened' squares to determine if they are in check.
    private Map<vector2, vector2> create_check_map(char player)
    {
        //initialising the map.
        Map<vector2, vector2> check_map = new HashMap<>();
        //here we would create the check map.
        for (int file = 0; file < 8; file++) {
            for (int rank = 0; rank < 8; rank++) {
                if (get_piece(new vector2(file, rank)) == null) {continue;}
                piece p = get_piece(new vector2(file, rank));
                if (p.get_color() == player) {continue; }
                ArrayList<vector2> attacked_squares = new ArrayList<>();
                switch (p.get_type()) {
                    case 'p' :
                        attacked_squares = find_pawn_legal_moves(p, true);
                        break;
                    case 'n' :
                        attacked_squares = find_knight_legal_moves(p, true);
                        break;
                    case 'b' :
                        attacked_squares = find_bishop_legal_moves(p, true);
                        break;
                    case 'r' :
                        attacked_squares = find_rook_legal_moves(p, true);
                        break;
                    case 'q' :
                        attacked_squares = find_queen_legal_moves(p, true);
                        break;
                }
                //add the piece's attacked squares to the check map.
                for (vector2 attacked_square : attacked_squares) {
                    check_map.put(attacked_square, attacked_square);
                }
            }
        }
        //update the threatened squares.
        return check_map;
    }
}