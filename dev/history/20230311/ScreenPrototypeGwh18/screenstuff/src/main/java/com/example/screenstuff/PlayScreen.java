package com.example.screenstuff;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author Gwion Hughes
 * @version 0.1
 */
public class PlayScreen {
    private final Interface anInterface;
    private Scene scene;
    private GridPane chessboard;
    private Button[][] tiles;
    private Text whitePlayerName, blackPlayerName;
    private HashMap<Character, Image> graphicsMap;

    /**
     * Constructor method for the Play Screen.
     * @param anInterface The GUI handler class
     */
    public PlayScreen(Interface anInterface) {
        this.anInterface = anInterface;
        setGraphics();
        constructPlayScreen();
    }

    /**
     * Function called in the constructor to create the screen
     */
    private void constructPlayScreen() {
        GridPane layout = new GridPane();
        layout.setPadding(new Insets(10,10,10,10));

        //Setting up column and row width for the layout gridpane. Currently an 18x12 grid.
        for(int counter = 0; counter < 18; counter++) {
            ColumnConstraints cConstraint = new ColumnConstraints(60);
            layout.getColumnConstraints().add(cConstraint);

            if(counter < 12) {
                RowConstraints rConstraints = new RowConstraints(60);
                layout.getRowConstraints().add(rConstraints);
            }
        }

        //Adding the chessboard to size 5-13x0-8
        chessBoard();
        layout.add(chessboard, 5, 1, 8, 8);

        //Adding container to display white player's name
        Label whiteP = new Label("White: ");
        Text whiteName = new Text("Joe Bloggs");
        this.whitePlayerName = whiteName;
        HBox whiteContainer = new HBox(whiteP, whiteName);
        whiteContainer.setAlignment(Pos.CENTER);
        layout.add(whiteContainer, 5, 9, 8, 1);


        //Adding container to display Black player's name
        Label blackP = new Label("Black: ");
        Text blackName = new Text("Mary Lavender");
        this.blackPlayerName = blackName;
        HBox blackContainer = new HBox(blackP, blackName);
        blackContainer.setAlignment(Pos.CENTER);
        layout.add(blackContainer, 5, 0, 8, 1);

/*
        VBox controls = new VBox();
        Text controlsFill = new Text("Controls");
        controls.setStyle("-fx-border-color: BLACK");
        controls.getChildren().add(controlsFill);

        layout.add(controls, 0, 0, 4,8);
*/


        HBox log = new HBox();

        ScrollPane logContainer = new ScrollPane(log);

        layout.add(logContainer, 2, 10, 14, 1);

/*
        VBox extraLayout = new VBox();
        Text extraLayoutFill = new Text("Extra Stuff");
        extraLayout.setStyle("-fx-border-color: GREEN");
        extraLayout.getChildren().add(extraLayoutFill);

        layout.add(extraLayout, 14, 0, 18, 8);
*/

        Button quitB = new Button("Quit.");
        quitB.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                //Request to be taken back to menu and clear that chessboard
                anInterface.toMenu();
                //Request game be saved. Chessboard doesn't need to be cleared. It will be updated when new game begins.
            }
        });

        layout.setAlignment(Pos.CENTER);

        layout.add(quitB, 14, 0, 2, 1);

        //Makes the gridlines visible in layout. Useful for debugging
        //layout.setGridLinesVisible(true);


        Scene playScreen = new Scene(layout, 1280, 720);
        playScreen.getStylesheets().add(getClass().getResource("PlayScreenStyleSheet.css").toExternalForm());


        this.scene = playScreen;
    }

    /**
     * Creates an empty 8x8 GridPane of rectangles representing an empty chessboard
     * @return Returns an empty chessboard
     */
    public void chessBoard() {
        //Initialise a gridpane that will be a grid containing out buttons that behave as tiles
        chessboard = new GridPane();
        //Make sure the gridpane has no padding so it doesn't repel other objects
        chessboard.setPadding(new Insets(0,0,0,0));
        //Initialise our 2d array of buttons that will contain pointers it shares with the gridpane for ease of index
        tiles = new Button[8][8];

        //A simple numerical variable used to itterate black and white tile placement one after another later in the function
        int check = 0;

        for(int row = 0; row < 8; row++) {

            //Every row should start on an alternating colour
            check++;
            for(int column = 0; column < 8; column++) {

                //Create a button for every row and column of the gridpane
                Button button = new Button();
                //Set the prefered size of the button to the column and row constraint of our layout gripane which is the parent node to our chessboard
                button.setPrefSize(60,60);
                //Make sure out buttons have no padding so they hug eachother tightly.
                button.setPadding(Insets.EMPTY);

                //Set a unique action event triggered by the button being clicked that passes the buttons column and row onwards to the stagehandler
                button.setOnAction(new EventHandler<ActionEvent>() {

                    @Override
                    public void handle(ActionEvent actionEvent) {
                        int column, row;
                        //get the metadata of the ActionEvent
                        actionEvent.getSource();

                        //Extract the column and row of the tile clicked from the metadata
                        column = (int) ((Button) actionEvent.getSource()).getProperties().get("gridpane-column");
                        row = (int) ((Button) actionEvent.getSource()).getProperties().get("gridpane-row");

                        //Pass column and row of button clicked to the interface stagehandler.
                        anInterface.click(column, row);

                        //switchButton(column, row);
                    }
                });

                //Alternate colour placement
                check++;
                if(check %2 == 1) {

                    button.getStyleClass().add("black-tile");
                } else {

                    button.getStyleClass().add("white-tile");
                }

                //add the button to the row and column of the chessboard and 2d tile array.
                chessboard.add(button, row, column);
                tiles[row][column] = button;
            }
        }
    }


    /**
     * Individually flip a tile. Supposed to be helper function. Set to private.
     * @param row
     * @param column
     * @param tileStyle
     */
    public void switchButton(int row, int column, String tileStyle) {
        //Replace the style class of the button with the new specified tileStyle
        tiles[row][column].getStyleClass().clear();
        tiles[row][column].getStyleClass().add(tileStyle);
    }

    public void setTiles(ArrayList<Integer> coords, String tileStyle) {
        //Run through an arraylist of coordinates and specify a tileStyle for each.
        for (int counter = 0; counter < coords.size(); counter = counter + 2) {
            switchButton(coords.get(counter), coords.get(counter+1), tileStyle);
        }
    }

    public void setWhitePlayerName(String name) {
        whitePlayerName.setText(name);
    }

    public void setBlackPlayerName(String name) {
        blackPlayerName.setText(name);
    }

    /**
     * Clears the chessboard of piece graphics and highlighted tiles.
     */
    private void clearChessBoard() {
        int check = 0;
        for (int row = 0; row < 8; row++) {
            check++;
            for(int column = 0; column < 8; column++) {

                //Iterate over EVERY button in our 2d array and set their graphics to null
                tiles[row][column].setGraphic(null);
                tiles[row][column].getStyleClass().clear();

                //Iterate over EVERY button in our 2d array and set their css styleclass back to black or white.
                check++;
                if(check %2 == 1) {
                    tiles[row][column].getStyleClass().add("black-tile");
                } else {

                    tiles[row][column].getStyleClass().add("white-tile");
                }
            }
        }
    }

    /**
     * Private function that sets initialises the fields to the correct graphics inside the imageView objects of
     * the PlayScreen's fields. The graphics should be contained in a package in their own folder at ...(to be added)
     */
    private void setGraphics() {
        //Initialise the HashMap.
        graphicsMap = new HashMap<Character, Image>();

        //Add an image to every char that represents a piece in the Forsyth Edwards Notation
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


    /**
     * A helper function to a graphic on an individual tile.
     * @param tile A button on the chessboard
     * @param pieceType A char that is used as a key to retrieve an image from the graphicsMap.
     */
    private void placePiece(Button tile, char pieceType) {

        //Create a Image container containing an image returned from the graphics HashMap using the function's char paramater as a key.
        ImageView graphic = new ImageView(graphicsMap.get(pieceType));

        //Sets the ImageView to fit the image in a 60x60 pixel bound.
        graphic.setFitWidth(60);
        graphic.setFitHeight(60);

        //This sets the button to use the image container as a graphic over its background
        tile.setGraphic(graphic);
    }

    public void updateBoard(String boardNotation) {
        //Clear of the board of any graphics first
        clearChessBoard();

        //sets starting positions to access the array from.
        int column = 0;
        int row = 0;

        //iterate through the Forsyth Edwards Notation string.
        for (int readHead = 0; readHead < boardNotation.length(); readHead++) {
            //if we have arrived at a space then we no longer need to read from the string, as the information from this point on is not relevant to this method
            if (boardNotation.charAt(readHead) == ' ') {return;}

            //if we have arrived at a '/', this is the marker for going down a rank, so we decrement the rank and reset the file to the first file.
            if (boardNotation.charAt(readHead) == '/') {column = 0; row++; continue;}

            //if we have arrived at a digit this is the marker for n amount of empty squares on the rank in a row before we find a piece. so we add this digit to out file variable.
            if (Character.isDigit(boardNotation.charAt(readHead))) { column += Character.getNumericValue(boardNotation.charAt(readHead)); continue;}


            //if the character representing the piece is upper case then it is a white piece, else it is a black piece.
            placePiece(tiles[column][row], boardNotation.charAt(readHead));
            //increment the file for the next position on the board.
            column++;
        }
    }


    /**
     * A getter for playscreen scene.
     * @return the playscreen scene.
     */
    public Scene getScene() {
        return scene;
    }
}
