/*
 * @(GP9) Tile.java 1.0 2023/04/27
 *
 * Copyright (c) 2021 Aberystywth University
 * All rights reserved
 */

package uk.ac.aber.cs221.group09.graphics;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

/**
 * Tile - This class is an abstraction of a tile on the graphical chessboard.
 * <p>
 * This class is used to store the actionable button graphic and tile data for use in the application.
 *
 * @author Gwion Hughes
 * @version 1.0 (Release)
 * @see Chessboard
 */
public class Tile {
   private final int row;
   private final int column;
   private final Button button;
   private final Chessboard chessboard;
   private final boolean isWhite;

   /**
    * Default Tile constructor.
    *
    * @param row        The rank or horizontal row of the tile.
    * @param column     The file or vertical column of the pressed tile.
    * @param isWhite    Whether the tile is a white or black tile.
    * @param chessboard The chessboard instance that owns the tile.
    */
   public Tile(int row, int column, boolean isWhite, Chessboard chessboard) {
      this.chessboard = chessboard;

      this.row = row;
      this.column = column;
      this.isWhite = isWhite;

      this.button = new Button();
      // Set alignment of button contents to center
      this.button.setAlignment(Pos.CENTER);
      // Set size of button to 60x60
      this.button.setPrefSize(60, 60);
      // Set action of button to send its coordinates upstream
      this.button.setPadding(Insets.EMPTY);

      // Set the StyleClass of the button to white or black
      if (isWhite) button.getStyleClass().add("white-tile");
      else this.button.getStyleClass().add("black-tile");

      // Sets the action of the button to tell the chessboard the row and column of button pressed on the chessboard.
      this.button.setOnAction(actionEvent -> chessboard.click(column, row));
   }

   public Button getButton() {
      return button;
   }

   /**
    * Sets the graphic of the button object to the passed ImageView.
    *
    * @param iv -  Imageview of piece graphic
    */
   public void setGraphics(ImageView iv) {
      button.setGraphic(iv);
   }

   /**
    * Clear the tile of any StyleClass or ImageViews.
    * Reset its StyleClass to be a white black tile.
    */
   public void clearTile() {
      this.button.setGraphic(null);
      this.button.getStyleClass().clear();
      if (isWhite) button.getStyleClass().add("white-tile");
      else button.getStyleClass().add("black-tile");

   }

   /**
    * Change the Tile's button to use a new StyleClass.
    * Called usually to display valid tiles or positions in check and their attacking pieces.
    *
    * @param styleClass the new style class of the button.
    */
   public void setStyleClass(String styleClass) {
      this.button.getStyleClass().clear();
      this.button.getStyleClass().add(styleClass);
   }

   public void switchButton(boolean b) {
      button.setDisable(b);
   }
}
