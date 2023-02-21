import pieces.piece;
import vector.vector2;
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
        //if we move a king we need to update the position of the king to track its position.
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
        String new_board_state;
        //read the board and update the string to represent the board.
        new_board_state = "t";
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
                vector2 piece_position = new vector2(file, rank);
                if (get_piece(piece_position) == null) {continue;}
                piece p = get_piece(piece_position);
                if (p.get_color() != attacking_player) { continue; }
                switch (p.get_type()) {
                    case 'p' :
                        find_pawn_legal_moves(p, false);
                        break;
                    case 'n' :
                        find_knight_legal_moves(p, false);
                        break;
                    case 'b' :
                        find_bishop_legal_moves(p, false);
                        break;
                    case 'r' :
                        find_rook_legal_moves(p, false);
                        break;
                    case 'q' :
                        find_queen_legal_moves(p, false);
                        break;
                    case 'k' :
                        find_king_legal_moves(p, false);
                        break;
                }
            }
        }
    }

    /*
        private method find_legal_pawn_moves, to find the legal moves for a pawn.
     */
    private void find_pawn_legal_moves(piece p, boolean check_map) {
        vector2 move = new vector2();
        move.y = (p.get_color() == 'w') ? 1 : -1;

        vector2 double_advance = new vector2(move.x, move.y * 2);

        vector2 left_attack = new vector2(move.x--, move.y);
        vector2 right_attack = new vector2(move.x++, move.y);

        if (get_piece(move) == null) {
            add_piece_legal_moves(p, move, check_map);
            if (get_piece(double_advance) == null && !p.has_moved()) {
                add_piece_legal_moves(p, move, check_map);
            }
        }
        if (get_piece(left_attack) == null) {
            add_piece_legal_moves(p, move, check_map);
        }
        if (get_piece(right_attack) == null) {
            add_piece_legal_moves(p, move, check_map);
        }
    }

    private void find_knight_legal_moves(piece p, boolean check_map) {
        vector2[] knight_directions = { new vector2(2, 1),  new vector2(1, 2), new vector2(-2, 1), new vector2(-1, 2), new vector2(2, -1), new vector2(1, -2), new vector2(-2, -1), new vector2(-1, -2)};

        for (vector2 d : knight_directions) {
            vector2 move = new vector2(p.get_position().x + d.x, p.get_position().y + d.y);

            if (move.x > 7 || move.y > 7 || move.x < 0 || move.y < 0) {continue;}

            if (get_piece(move) == null) {
                add_piece_legal_moves(p, move, check_map);
            } else if (get_piece(move).get_color() != p.get_color()) {
                add_piece_legal_moves(p, move, check_map);
            }
        }
    }

    private void find_bishop_legal_moves(piece p, boolean check_map) {
        vector2[] bishop_directions = { new vector2(1, 1), new vector2(-1, -1), new vector2(1, -1), new vector2(-1, 1)};

        for (vector2 d : bishop_directions) {
            for (int n = 0; n < 7; n++) {
                vector2 move = new vector2(p.get_position().x + (d.x * n), p.get_position().y + (d.y * n) );

                if (move.x > 7 || move.y > 7 || move.x < 0 || move.y < 0) { break; }

                if (get_piece(move) == null) {
                    add_piece_legal_moves(p, move, check_map);
                } else if (!(get_piece(move).get_color() == p.get_color())) {
                    add_piece_legal_moves(p, move, check_map);
                    break;
                } else if ((get_piece(move).get_color() == p.get_color())) {
                    break;
                }
            }
        }
    }

    private void find_rook_legal_moves(piece p, boolean check_map) {
        vector2[] rook_directions = { new vector2(0, 1),  new vector2(0, -1), new vector2(1, 0), new vector2(-1, 0)};

        for (vector2 d : rook_directions) {
            for (int n = 0; n < 7; n++) {
                vector2 move = new vector2(p.get_position().x + (d.x * n), p.get_position().y + (d.y * n));

                if (move.x > 7 || move.y > 7 || move.x < 0 || move.y < 0) { break; }

                if (get_piece(move) == null) {
                    add_piece_legal_moves(p, move, check_map);
                } else if (!(get_piece(move).get_color() == p.get_color())) {
                    add_piece_legal_moves(p, move, check_map);
                    break;
                } else if ((get_piece(move).get_color() == p.get_color())) {
                    break;
                }
            }
        }
    }

    private void find_queen_legal_moves(piece p, boolean check_map) {
        vector2[] queen_directions = { new vector2(0, 1),  new vector2(0, -1), new vector2(1, 0), new vector2(-1, 0), new vector2(1, 1), new vector2(-1, -1), new vector2(1, -1), new vector2(-1, 1)};

        for (vector2 d : queen_directions) {
            for (int n = 0; n < 7; n++) {
                vector2 move = new vector2(p.get_position().x + (d.x * n), p.get_position().y + (d.y * n));

                if (move.x > 7 || move.y > 7 || move.x < 0 || move.y < 0) { break; }

                if (get_piece(move) == null) {
                    add_piece_legal_moves(p, move, check_map);
                } else if (!(get_piece(move).get_color() == p.get_color())) {
                    add_piece_legal_moves(p, move, check_map);
                    break;
                } else if ((get_piece(move).get_color() == p.get_color())) {
                    break;
                }
            }
        }
    }

    private void find_king_legal_moves(piece p, boolean check_map) {
        vector2[] king_directions = { new vector2(0, 1),  new vector2(0, -1), new vector2(1, 0), new vector2(-1, 0), new vector2(1, 1), new vector2(-1, -1), new vector2(1, -1), new vector2(-1, 1)};

        for (vector2 d : king_directions) {
            vector2 move = new vector2(p.get_position().x + (d.x), p.get_position().y + (d.y));

            if (move.x > 7 || move.y > 7 || move.x < 0 || move.y < 0) { continue; }
            if (get_piece(move) == null) {
                add_piece_legal_moves(p, move, check_map);
            } else if (!(get_piece(move).get_color() == p.get_color())) {
                add_piece_legal_moves(p, move, check_map);
            }
        }
    }

    private void add_piece_legal_moves(piece p, vector2 move, boolean check_map) {
        if (check_map) {
            p.add_move(move);
        } else if (is_move_safe(p, move)){
            p.add_move(move);
        }
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
        //create the check map.
        for (int file = 0; file < 8; file++) {
            for (int rank = 0; rank < 8; rank++) {
                if (get_piece(new vector2(file, rank)) == null) {continue;}
                piece p = get_piece(new vector2(file, rank));
                if (p.get_color() == player) {continue;}
                switch (p.get_type()) {
                    case 'p' :
                        find_pawn_legal_moves(p, true);
                        break;
                    case 'n' :
                        find_knight_legal_moves(p, true);
                        break;
                    case 'b' :
                        find_bishop_legal_moves(p, true);
                        break;
                    case 'r' :
                        find_rook_legal_moves(p, true);
                        break;
                    case 'q' :
                        find_queen_legal_moves(p, true);
                        break;
                    case 'k' :
                        find_king_legal_moves(p, true);
                }
                //add the piece's attacked squares to the check map.
                for (vector2 attacked_square : p.get_possible_moves()) {
                    check_map.put(attacked_square, attacked_square);
                }
            }
        }
        //update the threatened squares.
        return check_map;
    }
}