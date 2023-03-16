package com.example.screenstuff;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.HashMap;

/**
 * The ImageLoader is a class to handle the loading images from the resource folder
 */
public class TileGraphicsLoader {
    private HashMap<Character, Image> graphicsMap;

    public TileGraphicsLoader() {
        //Initialise the map containing all of the graphics
        setGraphics();
    }

    private void setGraphics() {
        //Initialise the HashMap.
        graphicsMap = new HashMap<Character, Image>();

        //Add an image to every char that represents a piece in the Forsyth Edwards Notation
        //The path is the distance from /resources file.
        graphicsMap.put('p', new Image("/BlackPawn.png"));
        graphicsMap.put('r', new Image("/BlackRook.png"));
        graphicsMap.put('n', new Image("/BlackKnight.png"));
        graphicsMap.put('b', new Image("/BlackBishop.png"));
        graphicsMap.put('q', new Image("/BlackQueen.png"));
        graphicsMap.put('k', new Image("/BlackKing.png"));
        graphicsMap.put('P', new Image("/WhitePawn.png"));
        graphicsMap.put('R', new Image("/WhiteRook.png"));
        graphicsMap.put('N', new Image("/WhiteKnight.png"));
        graphicsMap.put('B', new Image("/WhiteBishop.png"));
        graphicsMap.put('Q', new Image("/WhiteQueen.png"));
        graphicsMap.put('K', new Image("/WhiteKing.png"));
    }

    public ImageView fetchTilePieceGraphic(char symbol) {
        //Return a new ImageView object for displaying the graphics in JavaFX
        ImageView graphic = new ImageView(graphicsMap.get(symbol));
        //Set to fit in 50x50 pixel space
        graphic.setFitHeight(50);
        graphic.setFitWidth(50);
        return graphic;
    }
}
