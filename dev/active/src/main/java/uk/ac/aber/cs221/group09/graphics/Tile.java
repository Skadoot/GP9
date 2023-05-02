/*
 * @(GP9) Tile.java 0.9 2023/04/27
 *
 * Copyright (c) 2021 Aberystywth University
 * All rights reserved
 *
 */

package uk.ac.aber.cs221.group09.graphics;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

/**
 * Tile - This class is an abstraction of a tile on the graphical chessboard.
 *
 * This class is used to store the actionable button graphic and tile data for use in the application.
 *
 * @author Gwion Hughes
 * @version 0.9 draft
 * @see Chessboard
 */
public class Tile {
    private int row, column;
    private Button button;
    private Chessboard chessboard;
    private boolean isWhite;

    /**
     * Tile constructor.
     * @param row - The rank or horizontal row of the tile.
     * @param column - The file or vertical column of the pressed tile.
     * @param isWhite - Whether the tile is a white or black tile.
     * @param chessboard - The chessboard instance that owns the tile.
     */
    public Tile(int row, int column, boolean isWhite, Chessboard chessboard) {
        this.chessboard = chessboard;

        this.row = row;
        this.column = column;
        this.isWhite = isWhite;

        this.button = new Button();
        //Set alignment of button contents to center
        this.button.setAlignment(Pos.CENTER);
        //Set size of button to 60x60
        this.button.setPrefSize(60, 60);
        //Set action of button to send its coordinates upstream
        this.button.setPadding(Insets.EMPTY);

        //Set the styleclass of the button to white or black
        if (isWhite) button.getStyleClass().add("white-tile");
        else this.button.getStyleClass().add("black-tile");

        //Sets the action of the button to tell the chessboard the row and column of button pressed on the chessboard.
        this.button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                chessboard.click(column, row);
            }
        });
    }

    /**
     * Getter for the Tile's FX Button. Called to place on the chessboard.
     * @return - FX Button.
     */
    public Button getButton() {
        return button;
    }

    /**
     * Passes and imageview to the class to display over the button.
     * @param iv -  Imageview of piece graphic
     */
    public void setGraphics(ImageView iv) {
        button.setGraphic(iv);
    }

    /**
     * Clear the tile of any styleclass or imageviews. Also reset its styleclass to be a white black tile.
     */
    public void clearTile() {
        this.button.setGraphic(null);
        this.button.getStyleClass().clear();
        if (isWhite) button.getStyleClass().add("white-tile");
        else button.getStyleClass().add("black-tile");

    }

    /**
     * Change the Tile's button to use a new styleclass. Called usually to display valid tiles or positions in check and
     * their attacking pieces.
     * @param styleClass
     */
    public void setStyleClass(String styleClass) {
        this.button.getStyleClass().clear();
        this.button.getStyleClass().add(styleClass);
    }

    public void switchButton(boolean b) {
        button.setDisable(b);
    }
}
