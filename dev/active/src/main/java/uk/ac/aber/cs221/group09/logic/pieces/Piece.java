/*
 * @(#) Piece.java 0.1 2023-02-23
 *
 * Copyright (c) 2023 Aberystwyth University.
 * All rights reserved.
 */

package uk.ac.aber.cs221.group09.logic.pieces;

import uk.ac.aber.cs221.group09.logic.vector.Vector2;

import java.util.ArrayList;

/**
 * Piece - A class to represent a chess piece.
 * <p>
 * How the class is used
 *
 * @author Shaun Royle
 * @version 0.1 (draft)
 * @see uk.ac.aber.cs221.group09.logic.vector.Vector2
 */
public class Piece {
    //storing the pieces color.
    private final char color;

    //storing the type of the pieces.piece for notation.
    private char type;

    //the pieces position.
    private Vector2 position;

    //the possible moves for a pieces.piece, ignoring if the moves are legal or not.
    private final ArrayList<Vector2> possibleMoves;

    //to keep track if the piece has moved during the course of the game.
    private boolean hasMoved;

    /**
     * Constructor for piece.
     *
     * @param color    the color of the piece: 'w' for white, 'b' for black.
     * @param position the position of the piece: a vector2.
     * @param type     the type of the piece 'p' for pawn, 'n' for knight, 'r' for rook, 'b' for bishop, 'q' for queen, 'k' for king.
     */
    public Piece(char color, Vector2 position, char type) {
        //initializing the possible moves arrayList.
        possibleMoves = new ArrayList<>();

        this.color = color;
        this.position = position;
        this.type = type;
    }

    /**
     * A method to return the color of the piece.
     *
     * @return the color of the piece: 'w' for white, 'b' for black.
     */
    public char getColor() {
        return color;
    }

    /**
     * A method to return the position of the piece.
     *
     * @return the vector2 object which stores its position.
     */
    public Vector2 getPosition() {
        return position;
    }

    /**
     * A method to set the position of the piece.
     *
     * @param position the new position to set the piece's position to.
     */
    public void setPosition(Vector2 position) {
        this.position = position;
    }

    /**
     * A method to return the legal moves of the piece.
     *
     * @return the ArrayList of legal moves.
     */
    public ArrayList<Vector2> getPossibleMoves() {
        return possibleMoves;
    }

    /**
     * A method to add a move to the list of the piece's legal moves.
     *
     * @param position the position of the move.
     */
    public void addMove(Vector2 position) {
        possibleMoves.add(position);
    }

    /**
     * A method to return the type of the piece
     *
     * @return the type of the piece: 'p' for pawn, 'n' for knight, 'r' for rook, 'b' for bishop, 'q' for queen, 'k' for king.
     */
    public char getType() {
        return type;
    }

    /**
     * A method to set the type of piece.
     *
     * @param type the type that you want to set the piece to: 'p' for pawn, 'n' for knight, 'r' for rook, 'b' for bishop, 'q' for queen, 'k' for king.
     */
    public void setType(char type) {
        this.type = type;
    }

    /**
     * A method which returns a boolean to determine if the piece has moved during the course of the game.
     *
     * @return true if the piece has moved, false if it has not moved.
     */
    public boolean hasMoved() {
        return hasMoved;
    }

    /**
     * A method which sets the hasMoved boolean.
     *
     * @param hasMoved the new value for hasMoved.
     */
    public void setHasMoved(boolean hasMoved) {
        this.hasMoved = hasMoved;
    }
}