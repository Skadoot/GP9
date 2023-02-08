package com.example.screenstuff;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;

import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * @author Gwion Hughes
 * @version 0.1
 */
public class PlayScreen {
    private final Stage stage;

    public PlayScreen(Stage stage) {
        this.stage = stage;
    }

    public Scene constructPlayScreen() {
        GridPane layout = new GridPane();
        layout.setPadding(new Insets(10,10,10,10));
        for(int counter = 0; counter < 18; counter++) {
            ColumnConstraints cConstraint = new ColumnConstraints(50);
            layout.getColumnConstraints().add(cConstraint);

            if(counter < 12) {
                RowConstraints rConstraints = new RowConstraints(50);
                layout.getRowConstraints().add(rConstraints);
            }
        }


        GridPane chessboard = chessBoard();
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
        //layout.setGridLinesVisible(true);


        Scene playScreen = new Scene(layout, 1200, 700);

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
                Rectangle square = new Rectangle(50,50,50,50);
                check++;
                if(check %2 == 1) {
                    square.setFill(Color.FIREBRICK);
                } else {
                    square.setFill(Color.WHITE);
                }
                chessboard.add(square, row, column);
            }
        }
        return chessboard;
    }
}
