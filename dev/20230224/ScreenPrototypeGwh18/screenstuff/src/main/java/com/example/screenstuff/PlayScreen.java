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
import javafx.scene.layout.*;
import javafx.scene.text.Text;

/**
 * @author Gwion Hughes
 * @version 0.1
 */
public class PlayScreen {
    private final Interface anInterface;
    private Scene scene;
    private GridPane chessboard;
    private Text whitePlayerName;
    private Text blackPlayerName;
    public PlayScreen(Interface anInterface) {
        this.anInterface = anInterface;
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
            ColumnConstraints cConstraint = new ColumnConstraints(50);
            layout.getColumnConstraints().add(cConstraint);

            if(counter < 12) {
                RowConstraints rConstraints = new RowConstraints(50);
                layout.getRowConstraints().add(rConstraints);
            }
        }


        GridPane chessboard = chessBoard();
        //Adding the chessboard to size 5-13x0-8
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

        layout.add(logContainer, 2, 11, 14, 1);

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
                anInterface.toMenu();
                //Make sure to clear chessboard and request game be saved
            }
        });

        layout.setAlignment(Pos.CENTER);

        layout.add(quitB, 14, 0, 2, 1);

        //Makes the gridlines visible in layout. Useful for debugging
        //layout.setGridLinesVisible(true);


        Scene playScreen = new Scene(layout, 1200, 700);
        playScreen.getStylesheets().add(PlayScreen.class.getResource("PlayScreenStyleSheet.css").toExternalForm());


        this.scene = playScreen;
    }

    /**
     * Creates an empty 8x8 GridPane of rectangles representing an empty chessboard
     * @return Returns an empty chessboard
     */
    public GridPane chessBoard() {
        GridPane chessboard = new GridPane();
        chessboard.setPadding(new Insets(0,0,0,0));
        int check = 0;

        for(int row = 0; row < 8; row++) {

            check++;
            for(int column = 0; column < 8; column++) {

                Button button = new Button();
                button.setPrefSize(50,50);
                button.setOnAction(new EventHandler<ActionEvent>() {

                    @Override
                    public void handle(ActionEvent actionEvent) {
                        int column, row;
                        actionEvent.getSource();
                        column = (int) ((Button) actionEvent.getSource()).getProperties().get("gridpane-column");
                        row = (int) ((Button) actionEvent.getSource()).getProperties().get("gridpane-row");
                        anInterface.click(column, row);
                        switchButton(column, row);
                    }
                });

                check++;
                if(check %2 == 1) {

                    button.getStyleClass().add("black-tile");
                } else {

                    button.getStyleClass().add("white-tile");
                }

                chessboard.add(button, row, column);
            }
        }

        this.chessboard = chessboard;
        return chessboard;
    }


    /**
     * Function for switching a button at given coordinates off.
     * @param column
     * @param row
     */
    public void switchButton(int column, int row) {
        ObservableList<Node> buttons = chessboard.getChildren();
        for (Node button : buttons) {
            if (GridPane.getColumnIndex(button) == column && GridPane.getRowIndex(button) == row) {
                button.setDisable(true);
            }
        }
    }

    public void setWhitePlayerName(String name) {
        whitePlayerName.setText(name);
    }

    public void setBlackPlayerName(String name) {
        blackPlayerName.setText(name);
    }

    public Scene getScene() {
        return scene;
    }
}
