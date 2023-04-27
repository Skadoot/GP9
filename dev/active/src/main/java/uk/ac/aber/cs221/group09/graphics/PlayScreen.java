/*
 * @(GP9) PlayScreen.java 0.9 2023/04/27
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
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Text;

/**
 * PlayScreen - A class for displaying the chessboard and associated functions.
 *
 * This class contains the scene with the chessboard and is used to navigate a game.
 *
 * @author Gwion Hughes, Ciaran Smith
 * @version 0.9 draft
 * @see Chessboard
 */
public class PlayScreen {
    private final Interface anInterface;
    private Scene scene;
    private Chessboard chessboard;

    private Text whitePlayerName, blackPlayerName;

    /**
     * A getter for playscreen scene.
     *
     * @return the playscreen scene.
     */
    public Scene getScene() {
        return scene;
    }

    /**
     * Constructor method for the Play Screen.
     *
     * @param anInterface The GUI handler class
     */
    public PlayScreen(Interface anInterface) {
        this.chessboard = new Chessboard(this);
        this.anInterface = anInterface;
        constructPlayScreen();
    }

    /**
     * Setter for the white player name on the scene.
     * @param name - String, name of white player.
     */
    public void setWhitePlayerName(String name) {
        whitePlayerName.setText(name);
    }

    /**
     * Setter for the black player name on the scene
     * @param name - String, name of black player
     */
    public void setBlackPlayerName(String name) {
        blackPlayerName.setText(name);
    }

    /**
     * Function called in the constructor to create the screen
     */
    private void constructPlayScreen() {
        GridPane layout = new GridPane();
        layout.setPadding(new Insets(10, 10, 10, 10));

        //Setting up column and row width for the layout gridpane. Currently an 18x12 grid.
        for (int counter = 0; counter < 18; counter++) {
            ColumnConstraints cConstraint = new ColumnConstraints(60);
            layout.getColumnConstraints().add(cConstraint);

            if (counter < 12) {
                RowConstraints rConstraints = new RowConstraints(60);
                layout.getRowConstraints().add(rConstraints);
            }
        }

        //Adding the chessboard to size 5-13x0-8

        layout.add(this.chessboard.getChessBoard(), 5, 1, 8, 8);

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

        //The below virtual box will be  used to track the game such as material captured.
/*
        VBox extraLayout = new VBox();
        Text extraLayoutFill = new Text("Extra Stuff");
        extraLayout.setStyle("-fx-border-color: GREEN");
        extraLayout.getChildren().add(extraLayoutFill);

        layout.add(extraLayout, 14, 0, 18, 8);
*/

        //The quit button for exiting the game.
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
        playScreen.getStylesheets().add(getClass().getResource("/css/PlayScreenStyleSheet.css").toExternalForm());


        this.scene = playScreen;
    }

    /**
     * Pass the coordinates of the button pressed up to the backend-frontend interface class.
     * @param column - The file or vertical column of the pressed tile.
     * @param row - The rank or horizontal row of the pressed tile.
     */
    public void alertPressedTile(int column, int row) {
        anInterface.click(column, row);
    }

    /**
     * Function to pass a FEN string of the backend board state to the Chessboard to update the graphical board.
     * @param boardNotation - String, Forsyth Edwards Notation representing state of board.
     */
    public void updatePlayScreen(String boardNotation) {
        chessboard.updateBoard(boardNotation);
    }

    /**
     * Function fired when a draw is offered. A screen will show offering a draw to the next player. Game will end in
     * a draw if accepted.
     */
    public void offerDraw() {
        //create a box telling the player to pass to the next player to agree or not to a draw
        //It will have two buttons for yah or nah
    }

    /**
     * Function is called when the player presses the resign button. A container will show over the chessboard asking
     * the player if they would really like to resign. If they press yes, game ends with the next players winning.
     */
    public void resign() {
        //create a box with text asking the player if they are sure. They will not be able to play further moves
        //A button for yah or nah
    }

    /**
     * Call to backend to show the next move in the log. Activated by pressing a log button. Deactivate chess buttons
     */
    public void incrementThroughLog() {
        //a function triggered by a button. Push the next board state forwards
    }

    /**
     * Call to backend to show the previous move in the log. Activated by pressing a log button. Deactivate chess buttons.
     */
    public void decrementThroughLog() {
        //A function triggered by a button. View the past through a spooky crystal ball ooooo
    }

    /**
     * Called to create container over the chessboard to ask the player are they sure they'd like to quit..
     */
    public void areYouSure() {
        //when the player wants to quit, crate a box that double checks.
        //Make aware that the game will be saved and will be playable again
    }
}
