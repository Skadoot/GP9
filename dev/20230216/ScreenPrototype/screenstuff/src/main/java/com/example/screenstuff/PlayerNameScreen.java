package com.example.screenstuff;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class PlayerNameScreen {

    public PlayerNameScreen() {

    }

    public Scene createScreen() {
        VBox panel = new VBox();
        panel.setAlignment(Pos.CENTER);

        HBox playerWhite = new HBox();
        playerWhite.getStyleClass().add("hbox");
        HBox playerBlack = new HBox();
        playerBlack.getStyleClass().add("hbox");
        HBox buttons = new HBox();
        buttons.getStyleClass().add("hbox");

        TextField textFieldWhite = new TextField("Enter your name");
        TextField textFieldBlack = new TextField("Enter your name");

        Label labelWhite = new Label("White:");
        Label labelBlack = new Label("Black:");

        Button startGame = new Button("Start Game");
        Button back = new Button("Back");

        playerWhite.getChildren().addAll(labelWhite,textFieldWhite);
        playerBlack.getChildren().addAll(labelBlack,textFieldBlack);
        buttons.getChildren().addAll(back,startGame);

        panel.getChildren().addAll(playerWhite,playerBlack,buttons);

        Scene scene = new Scene(panel, 1200, 700);
        scene.getStylesheets().add(PlayerNameScreen.class.getResource("PlayerNameScreen.css").toExternalForm());
        return scene;
    }
}
