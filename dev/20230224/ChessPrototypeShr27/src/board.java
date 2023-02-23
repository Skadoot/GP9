import pieces.piece;
import vector.vector2;
import java.util.HashMap;
import java.util.Map;

public class board {
    //the logical representation of the board.
    private final piece[][] board;
    //forsyth edwards notation of the board for saving and loading board states.
    private String board_state;
    //both king positions.
    private vector2 w_king_position;
    private vector2 b_king_position;
    //constructor for the board.
    public board(String initial_board_state) {
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
                        System.out.println("i : " + i);
                        //System.out.println(board_state.charAt());
                        System.out.println(board_state.charAt(i));
                        //System.out.println(file);
                        //System.out.println(board_state.charAt(i));
                        file += Character.getNumericValue(board_state.charAt(i));
                        //System.out.println(board_state.charAt(i));
                        System.out.println(file);
                    } else {
                        vector2 board_position = new vector2(file, rank);
                        if (!Character.isLowerCase(board_state.charAt(i))) {
                            System.out.println(board_state.charAt(i));
                            System.out.println("adding white piece");
                            board[file][rank] = new piece('w', board_position, Character.toLowerCase(board_state.charAt(i)));
                            if(Character.toLowerCase(board_state.charAt(i)) == 'k'){
                                System.out.println("adding white king");
                                w_king_position = board_position;
                            }
                        } else {
                            System.out.println("adding black piece");
                            System.out.println(board_state.charAt(i));
                            board[file][rank] = new piece('b', board_position, board_state.charAt(i));
                            if(board_state.charAt(i) == 'k'){
                                System.out.println("adding black king");
                                b_king_position = board_position;
                            }
                        }
                        System.out.println("added piece to the board");
                        System.out.println(board[file][rank].get_type());
                        file++;
                    }
                }
            } else {
                return;
            }
        }
    }

    //return a piece on the board.
    public piece get_piece(vector2 coordinate) {
        //return a piece on the board.
        return board[coordinate.x][coordinate.y];
    }

    //move selected piece.
    public void move_piece(piece selected_piece, vector2 coordinate) {
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
    private void update_board_state() {
        //initialising the new board state string.
        String new_board_state;
        //read the board and update the string to represent the board.
        new_board_state = "t";
        //set the bard state.
        board_state = new_board_state;
    }

    //return the current state of the board.
    public String get_board_state() {
        //returns the notation for the state of the board
        return board_state;
    }

    //filtering for legal moves relative to the current board state and current player, called at the start of each turn.
    public void find_legal_moves(char attacking_player) {
        //loop over each piece that is of a particular color
        for (int rank = 0; rank < 8; rank++) {
            System.out.print("\n");
            for (int file = 0; file < 8; file++) {
                vector2 piece_position = new vector2(file, rank);
                piece p = get_piece(piece_position);
                if(!(p == null)) {
                    System.out.print("  " + p.get_type() + "  ");
                } else {
                    System.out.print("  /  ");
                }
            }
            System.out.print("\n");
        }
        for (int rank = 0; rank < 8; rank++) {
            for (int file = 0; file < 8; file++) {
                vector2 piece_position = new vector2(file, rank);
                piece piece = get_piece(piece_position);

                if (piece == null) {continue;}
                if (piece.get_color() != attacking_player) {continue;}

                System.out.println("looking for piece legal moves (type : " + piece.get_type() + ", color : " + ", (file : " + file + ", rank : " + rank + ")),");
                switch (piece.get_type()) {
                    case 'p':
                        System.out.println("looking for pawn moves");
                        find_pawn_legal_moves(piece, false);
                        break;
                    case 'n':
                        System.out.println("looking for knight moves");
                        find_knight_legal_moves(piece, false);
                        break;
                    case 'b':
                        System.out.println("looking for bishop moves");
                        find_bishop_legal_moves(piece, false);
                        break;
                    case 'r':
                        System.out.println("looking for rook moves");
                        find_rook_legal_moves(piece, false);
                        break;
                    case 'q':
                        System.out.println("looking for queen moves");
                        find_queen_legal_moves(piece, false);
                        break;
                    case 'k':
                        System.out.println("looking for king moves");
                        find_king_legal_moves(piece, false);
                        break;
                }
            }
        }
        System.out.println(is_in_check('w'));
    }

    /*
        private method find_legal_pawn_moves, to find the legal moves for a pawn.
     */
    private void find_pawn_legal_moves(piece p, boolean check_map) {
        vector2 move = new vector2(p.get_position().x, ((p.get_color() == 'w') ? p.get_position().y + 1 : p.get_position().y - 1));
        vector2 double_advance = new vector2(p.get_position().x, ((p.get_color() == 'w') ? move.y + 1 : move.y - 1));
        vector2 left_attack = new vector2(move.x - 1, move.y);
        vector2 right_attack = new vector2(move.x + 1, move.y);

        if (move.x <= 7 && move.y <= 7 && move.x >= 0 && move.y >= 0) {
            if (get_piece(move) == null) {
                add_piece_legal_moves(p, move, check_map);
                if (get_piece(double_advance) == null && !p.has_moved()) {
                    add_piece_legal_moves(p, move, check_map);
                }
            }
        }
        if (left_attack.x >= 0 && left_attack.x <= 7) {
            if (get_piece(left_attack) == null) {
                add_piece_legal_moves(p, move, check_map);
            }
        }
        if (right_attack.x >= 0 && right_attack.x <= 7) {
            if (get_piece(right_attack) == null) {
                add_piece_legal_moves(p, move, check_map);
            }
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
            for (int n = 1; n < 8; n++) {
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
        vector2[] rook_directions = {new vector2(0, 1),  new vector2(0, -1), new vector2(1, 0), new vector2(-1, 0)};

        for (vector2 d : rook_directions) {
            for (int n = 1; n < 8; n++) {
                vector2 move = new vector2(p.get_position().x + (d.x * n), p.get_position().y + (d.y * n));

                if (move.x > 7 || move.y > 7 || move.x < 0 || move.y < 0) {break;}
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
        vector2[] queen_directions = {new vector2(0, 1),  new vector2(0, -1), new vector2(1, 0), new vector2(-1, 0), new vector2(1, 1), new vector2(-1, -1), new vector2(1, -1), new vector2(-1, 1)};

        for (vector2 d : queen_directions) {
            for (int n = 1; n < 8; n++) {
                vector2 move = new vector2(p.get_position().x + (d.x * n), p.get_position().y + (d.y * n));

                if (move.x > 7 || move.y > 7 || move.x < 0 || move.y < 0) {break;}
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
        vector2[] king_directions = {new vector2(0, 1),  new vector2(0, -1), new vector2(1, 0), new vector2(-1, 0), new vector2(1, 1), new vector2(-1, -1), new vector2(1, -1), new vector2(-1, 1)};

        for (vector2 d : king_directions) {
            vector2 move = new vector2(p.get_position().x + (d.x), p.get_position().y + (d.y));

            if (move.x > 7 || move.y > 7 || move.x < 0 || move.y < 0) {continue;}
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
        } else if (is_move_safe(p, move)) {
            p.add_move(move);
        }
    }

    private boolean is_move_safe(piece p, vector2 move) {
        board b = new board(this.get_board_state());
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
    private Map<vector2, vector2> create_check_map(char player) {
        //initialising the map.
        Map<vector2, vector2> check_map = new HashMap<>();
        //create the check map.
        for (int file = 0; file < 8; file++) {
            for (int rank = 0; rank < 8; rank++) {
                if (get_piece(new vector2(file, rank)) == null) {continue;}
                piece piece = get_piece(new vector2(file, rank));
                if (piece.get_color() == player) {continue;}
                System.out.println("looking for piece attacking squares (" + piece.get_type() + ").");
                switch (piece.get_type()) {
                    case 'p' :
                        find_pawn_legal_moves(piece, true);
                        break;
                    case 'n' :
                        find_knight_legal_moves(piece, true);
                        break;
                    case 'b' :
                        find_bishop_legal_moves(piece, true);
                        break;
                    case 'r' :
                        find_rook_legal_moves(piece, true);
                        break;
                    case 'q' :
                        find_queen_legal_moves(piece, true);
                        break;
                    case 'k' :
                        find_king_legal_moves(piece, true);
                }
                //add the piece's attacked squares to the check map.
                for (vector2 attacked_square : piece.get_possible_moves()) {
                    check_map.put(attacked_square, attacked_square);
                }
            }
        }
        //update the threatened squares.
        return check_map;
    }
}