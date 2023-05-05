/*
 * @(GP9) Coordinate.java 1.0 2023-05-02
 *
 * Copyright (c) 2023 Aberystwyth University.
 * All rights reserved.
 */

package uk.ac.aber.cs221.group09.util;

/**
 * Coordinate - A class to represent a set of board coordinates.
 * <p>
 * Contains the x and y coordinates for the board position.
 *
 * @author Shaun Royle
 * @version 1.0 (Release)
 * @see uk.ac.aber.cs221.group09.logic.pieces.Piece
 */
public class Coordinate {
   // Board notation.
   private final char[] boardNotation = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h'};
   // X coordinate for the board.
   public int x;
   // Y coordinate for the board.
   public int y;

   /**
    * A default constructor for the Coordinate class.
    */
   public Coordinate() {
      this.x = 0;
      this.y = 0;
   }

   /**
    * A simple constructor for the Coordinate class.
    *
    * @param x the x coordinate for the board.
    * @param y the y coordinate for the board.
    */
   public Coordinate(int x, int y) {
      this.x = x;
      this.y = y;
   }

   /**
    * Returns the position represented by this Coordinate as a FEN string chess board notation.
    *
    * @return String containing the chess board position as a FEN String.
    */
   public String getCoordinateAsBoardNotation() {
      return boardNotation[x] + Integer.toString(y + 1);
   }

   @Override
   public boolean equals(Object o) {
      if (!(o instanceof Coordinate)) {
         return false;
      }
      Coordinate compare = (Coordinate) o;
      return this.x == compare.x && this.y == compare.y;
   }
}