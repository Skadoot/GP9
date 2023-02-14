package com.example.screenstuff;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;

import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.text.Text;

/**
 * @author Gwion Hughes
 * @version 0.1
 */
public class PlayScreen {
    private final Interface anInterface;
    private GridPane chessboard;
    public PlayScreen(Interface anInterface) {
        this.anInterface = anInterface;
    }

    public Scene constructPlayScreen() {
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
        layout.add(chessboard, 5, 0, 13, 8);

        VBox controls = new VBox();
        Text controlsFill = new Text("Controls");
        controls.setStyle("-fx-border-color: BLACK");
        controls.getChildren().add(controlsFill);

        layout.add(controls, 0, 0, 4,8);

        HBox log = new HBox();
        Text logFill = new Text("Log");
        log.setStyle("-fx-border-color: RED");
        log.getChildren().add(logFill);

        layout.add(log, 2, 9, 14, 9);

        VBox extraLayout = new VBox();
        Text extraLayoutFill = new Text("Extra Stuff");
        extraLayout.setStyle("-fx-border-color: GREEN");
        extraLayout.getChildren().add(extraLayoutFill);

        layout.add(extraLayout, 14, 0, 18, 8);

        layout.setStyle("-fx-background-color: DAE9F3;");

        layout.setAlignment(Pos.CENTER);

        //Makes the gridlines visible in layout. Useful for debugging
        layout.setGridLinesVisible(true);


        Scene playScreen = new Scene(layout, 1200, 700);
        //playScreen.getStylesheets().add("./PlayScreenStyleSheet.css");

        return playScreen;
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

                    button.setId("blacktile");
                } else {

                    button.setId("whitetile");
                }

                chessboard.add(button, row, column);
            }
        }

        this.chessboard = chessboard;
        return chessboard;
    }

    public void switchButton(int column, int row) {
        ObservableList<Node> buttons = chessboard.getChildren();
        for (Node button : buttons) {
            if (GridPane.getColumnIndex(button) == column && GridPane.getRowIndex(button) == row) {
                button.setDisable(true);
            }
        }
    }
}
