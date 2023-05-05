/*
 * @(GP9) Piece.java 0.1 2023-02-23
 *
 * Copyright (c) 2023 Aberystwyth University.
 * All rights reserved.
 */

package uk.ac.aber.cs221.group09.logic.pieces;

import uk.ac.aber.cs221.group09.util.Coordinate;

import java.util.ArrayList;

/**
 * Piece - A class to represent a chess piece.
 * <p>
 * The piece class is used to represent a chess piece, and holds data relating to
 * the position of the piece, the color of the piece, and the type of the piece.
 *
 * @author Shaun Royle
 * @version 1.0 (Release)
 * @see Coordinate
 */
public class Piece {
   // Storing the pieces color.
   private final char color;
   // The possible moves for a pieces.piece, ignoring if the moves are legal or not.
   private final ArrayList<Coordinate> possibleMoves;
   // Storing the type of the pieces.piece for notation.
   private char type;
   // The pieces position.
   private Coordinate position;
   // To keep track if the piece has moved during the course of the game.
   private boolean hasMoved;

   /**
    * Simple constructor for piece.
    *
    * @param color    the color of the piece: 'w' for white, 'b' for black.
    * @param position the position of the piece: a Vector2.
    * @param type     the type of the piece: 'p' for pawn, 'n' for knight, 'r' for rook, 'b' for bishop, 'q' for queen, 'k' for king.
    */
   public Piece(char color, Coordinate position, char type) {
      // Initializing the possible moves arrayList.
      possibleMoves = new ArrayList<>();

      this.color = color;
      this.position = position;
      this.type = type;
   }

   /**
    * Returns the color of the piece.
    *
    * @return the color of the piece: 'w' for white, 'b' for black.
    */
   public char getColor() {
      return color;
   }

   /**
    * Returns the position of the piece.
    *
    * @return the Vector2 object which stores its position.
    */
   public Coordinate getPosition() {
      return position;
   }

   /**
    * Sets the position of the piece.
    *
    * @param position the new position to set the piece's position to.
    */
   public void setPosition(Coordinate position) {
      this.position = position;
   }

   /**
    * Returns the legal moves of the piece.
    *
    * @return the ArrayList of legal moves.
    */
   public ArrayList<Coordinate> getPossibleMoves() {
      return possibleMoves;
   }

   /**
    * Adds a possible move to the list of the piece's legal moves.
    *
    * @param position the position of the move.
    */
   public void addMove(Coordinate position) {
      if (possibleMoves.contains(position)) {
         return;
      }
      possibleMoves.add(position);
   }

   /**
    * Returns the type of the piece object.
    *
    * @return a Char denoting the piece type: 'p' for pawn, 'n' for knight, 'r' for rook, 'b' for bishop, 'q' for queen, 'k' for king.
    */
   public char getType() {
      return type;
   }

   /**
    * Sets the type of the piece object.
    *
    * @param type Char, the type that you want to set the piece to: 'p' for pawn, 'n' for knight, 'r' for rook, 'b' for bishop, 'q' for queen, 'k' for king.
    */
   public void setType(char type) {
      this.type = type;
   }

   /**
    * Returns a boolean whether the piece has moved during the course of the game.
    *
    * @return true if the piece has moved, false if it has not moved.
    */
   public boolean hasMoved() {
      return hasMoved;
   }

   /**
    * Sets the hasMoved boolean. Used for En Passant, pawn double-step, castling, etc.
    *
    * @param hasMoved the new value for hasMoved.
    */
   public void setHasMoved(boolean hasMoved) {
      this.hasMoved = hasMoved;
   }

   public void clearMoves() {
      possibleMoves.clear();
   }
}