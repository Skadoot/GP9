/*
 * @(GP9) TileGraphicsLoader.java 1.0 2023/04/27
 *
 * Copyright (c) 2023 Aberystwyth University
 * All rights reserved.
 */

package uk.ac.aber.cs221.group09.graphics;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.HashMap;

/**
 * TileGraphicsLoader - This class provides the graphics for chessboard pieces.
 * <p>
 * This class loads images in from the resource folder of piece graphics and packages these images in ImageView
 * containers to display on Tile buttons on the graphical chessboard. Images are only loaded in once from the resource
 * folder this way.
 *
 * @author Gwion Hughes
 * @version 1.0 (Release)
 * @see uk.ac.aber.cs221.group09.graphics.Tile
 */
public class TileGraphicsLoader {
   private HashMap<Character, Image> graphicsMap;

   /**
    * Simple constructor. Creates and initializes a new TileGraphicsLoader with preset graphics.
    */
   public TileGraphicsLoader() {
      // Initialise the map containing all the graphics
      setGraphics();
   }

   /**
    * This function takes a character usually from a FEN string representing a single piece on a board location and
    * returns an imageview of the graphic representing that piece from a map of images mapped to those characters to
    * display on the board.
    *
    * @param symbol Char representing a piece in a FEN string
    * @return ImageView container with piece graphic.
    */
   public ImageView fetchTilePieceGraphic(char symbol) {
      // Return a new ImageView object for displaying the graphics in JavaFX
      ImageView graphic = new ImageView(graphicsMap.get(symbol));
      // Set to fit in 50x50 pixel space
      graphic.setFitHeight(50);
      graphic.setFitWidth(50);
      return graphic;
   }

   private void setGraphics() {
      // Initialise the HashMap.
      graphicsMap = new HashMap<>();

      // Add an image to every char that represents a piece in the Forsyth Edwards Notation
      // The path is the distance from /resources file.
      graphicsMap.put('p', new Image("/images/BlackPawn.png"));
      graphicsMap.put('r', new Image("/images/BlackRook.png"));
      graphicsMap.put('n', new Image("/images/BlackKnight.png"));
      graphicsMap.put('b', new Image("/images/BlackBishop.png"));
      graphicsMap.put('q', new Image("/images/BlackQueen.png"));
      graphicsMap.put('k', new Image("/images/BlackKing.png"));
      graphicsMap.put('P', new Image("/images/WhitePawn.png"));
      graphicsMap.put('R', new Image("/images/WhiteRook.png"));
      graphicsMap.put('N', new Image("/images/WhiteKnight.png"));
      graphicsMap.put('B', new Image("/images/WhiteBishop.png"));
      graphicsMap.put('Q', new Image("/images/WhiteQueen.png"));
      graphicsMap.put('K', new Image("/images/WhiteKing.png"));
   }
}
