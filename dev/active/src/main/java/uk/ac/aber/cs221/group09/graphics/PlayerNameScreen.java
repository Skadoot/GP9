/*
 * @(GP9) PlayerNameScreen.java 0.5 2023/04/27
 *
 * Copyright (c) 2021 Aberystywth University
 * All rights reserved
 *
 */

package uk.ac.aber.cs221.group09.graphics;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * PlayerNameScreen - A class for displaying the scene to enter player names before a new game
 *
 * This class is switched to when the user requests a new game. Users will be given the opportunity to enter names
 * for the white and black player before proceeding to a new chessboard.
 *
 * @author Gwion Hughes
 * @version 0.5 draft
 * @see PlayScreen
 */
public class PlayerNameScreen {
    private Interface anInterface;
    private Scene scene;

    /**
     * Getter for the PlayerNameScreen scene.
     * @return
     */
    public Scene getScene() {
        return scene;
    }

    /**
     * Constructor
     * @param anInterface - Interface containing the application window for this class' scene.
     */
    public PlayerNameScreen(Interface anInterface) {
        this.anInterface = anInterface;
        createScreen();
    }

    /**
     * Scene constructor. the layour of the scene has a vertical box as its root with horizontal boxes with labels and
     * textfields for entering the players names. There is also a back and start game button for navigating forwards
     * or backwards through the chess tutour screens.
     */
    private void createScreen() {
        VBox panel = new VBox();
        panel.setAlignment(Pos.CENTER);

        //FX containers for containing the player and file names.
        HBox playerWhite = new HBox();
        playerWhite.getStyleClass().add("hbox");
        HBox playerBlack = new HBox();
        playerBlack.getStyleClass().add("hbox");
        HBox buttons = new HBox();
        buttons.getStyleClass().add("hbox");
        HBox fileName = new HBox(); //jat add
        fileName.getStyleClass().add("hbox");

        //Text fields to enter names.
        TextField textFieldWhite = new TextField("White Player");
        TextField textFieldBlack = new TextField("Black Player");
        TextField textFieldFile = new TextField("File Name");

        //Labels to place alongside text fields indicating which colour is being named.
        Label labelWhite = new Label("White:");
        Label labelBlack = new Label("Black:");
        Label labelFile = new Label("Name of File");

        //Create a button that on click calls interface to swap scene to chessboard
        Button startGame = new Button("Start Game");
        startGame.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                forwardsToNewGame(textFieldBlack.getText(), textFieldWhite.getText(), textFieldFile.getText());
            }
        });
        Button back = new Button("Back");

        //Create a button that on click calls the interface to backtrack to thee start screen
        back.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                backToMenu();
            }
        });

        playerWhite.getChildren().addAll(labelWhite, textFieldWhite);
        playerBlack.getChildren().addAll(labelBlack, textFieldBlack);
        fileName.getChildren().addAll(labelFile, textFieldFile);
        buttons.getChildren().addAll(back, startGame);

        panel.getChildren().addAll(playerWhite, playerBlack, fileName, buttons);

        Scene scene = new Scene(panel, 1280, 720);
        scene.getStylesheets().add(PlayerNameScreen.class.getResource("/css/PlayerNameScreen.css").toExternalForm());
        this.scene = scene;
    }

    private void backToMenu() {
        anInterface.toMenu();
    }

    //might be worth asking user for the name of the game in here as well. Will need this for logfile - jat92
    private void forwardsToNewGame(String blackN, String whiteN, String fileName) {
        anInterface.toNewChessboard(whiteN, blackN, fileName);
    }
}
