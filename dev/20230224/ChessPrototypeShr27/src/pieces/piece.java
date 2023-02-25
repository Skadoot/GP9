package pieces;

import vector.vector2;
import java.util.ArrayList;

public class piece
{
    //storing the pieces color.
    private final char color;

    //storing the type of the pieces.piece for notation.
    private char type = ' ';

    //the pieces position.
    private vector2 position;

    //the possible moves for a pieces.piece, ignoring if the moves are legal or not.
    private ArrayList<vector2> possibleMoves;

    //to keep track if the pieces.a_piece has moved during the course of the game.
    private boolean hasMoved;

    /**
     *
     * @param color
     * @param position
     * @param type
     */
    public piece(char color, vector2 position, char type) {
        //initializing the possible moves arrayList.
        possibleMoves = new ArrayList<>();

        this.color = color;
        this.position = position;
        this.type = type;
    }

    /**
     *
     * @return
     */
    public char getColor() {
        return color;
    }

    /**
     *
     * @return
     */
    public vector2 getPosition() {
        return position;
    }

    /**
     *
     * @param position
     */
    public void setPosition(vector2 position) {
        this.position = position;
    }

    /**
     *
     * @return
     */
    public ArrayList<vector2> getPossibleMoves() {
        return possibleMoves;
    }

    /**
     *
     * @param possibleMoves
     */
    public void setPossibleMoves(ArrayList<vector2> possibleMoves) {
        this.possibleMoves = possibleMoves;
    }

    /**
     *
     * @param position
     */
    public void add_move(vector2 position){
        possibleMoves.add(position);
    }

    /**
     *
     * @return
     */
    public char getType() {
        return type;
    }

    /**
     *
     * @param type
     */
    public void setType(char type) {
        this.type = type;
    }

    /**
     *
     * @return
     */
    public boolean has_moved() {
        return hasMoved;
    }

    /**
     *
     * @param has_moved
     */
    public void setHasMoved(boolean has_moved) {
        this.hasMoved = has_moved;
    }
}