package uk.ac.aber.cs221.group09.graphics;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

/**
 * This is class to abstract a chessboard tile.
 */
public class Tile {
    private int row, column;
    private Button button;
    private Chessboard chessboard;
    private boolean isWhite;

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

        if (isWhite) button.getStyleClass().add("white-tile");
        else this.button.getStyleClass().add("black-tile");
        this.button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                chessboard.click(column, row);
            }
        });
    }

    //Function to set graphic of button
    public void setGraphics(ImageView iv) {
        button.setGraphic(iv);
    }

    public void clearTile() {
        this.button.setGraphic(null);
        this.button.getStyleClass().clear();
        if (isWhite) button.getStyleClass().add("white-tile");
        else button.getStyleClass().add("black-tile");

    }

    public void setStyleClass(String styleClass) {
        this.button.getStyleClass().clear();
        this.button.getStyleClass().add(styleClass);
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public Button getButton() {
        return button;
    }
}
