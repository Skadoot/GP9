/*
 * @(GP9) StartScreen.java 0.9 2023/04/27
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
import javafx.scene.layout.VBox;

/**
 * StartScreen - This class contains the first scene the user is greeted by upon launching the application.
 *
 * This class contains the menu scene and all the scenes of application are navigated to from or back to this scene.
 *
 * @author Gwion Hughes
 * @version 0.9 draft
 * @see Interface
 */
public class StartScreen {
    private final Interface anInterface;
    private Scene startScreen;

    /**
     * Getter for the menu scene.
     * @return - Scene containing main menu.
     */
    public Scene getStartScreen() {
        return startScreen;
    }

    /**
     * Constructor
     * @param anInterface - Class containing the application window.
     */
    public StartScreen(Interface anInterface) {
        this.anInterface = anInterface;
        constructScreen();
    }

    /**
     * Creates a scene with a vertical box as its root with three buttons for navigating to loading finished games, unfinished games,
     * or starting a new game.
     */
    private void constructScreen() {
        VBox btnSelection = new VBox();
        Button ngButton = new Button();
        Button conButton = new Button();
        Button vgButton = new Button();

        //This button takes the user to the PlayerName scene.
        ngButton.setText("Start New Game");
        ngButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                requestNewGame();
            }

        });

        //This button takes the user to the LoadGame scene with a selection of unfinished games to pick back up
        conButton.setText("Load Unfinished Game");
        conButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                requestToUnfinishedGames();
            }
        });

        //This button takes the user to the LoagGame scene with a selection of finished games to view.
        vgButton.setText("View Finished Game");
        vgButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                requestToViewFinishedGames();
            }
        });

        //The below code places the buttons.
        btnSelection.setPadding(new Insets(5, 5, 5, 5));
        btnSelection.setSpacing(12);

        btnSelection.getChildren().add(ngButton);
        btnSelection.getChildren().add(conButton);
        btnSelection.getChildren().add(vgButton);

        btnSelection.setAlignment(Pos.CENTER);

        Scene menu = new Scene(btnSelection, 1280, 720);
        menu.getStylesheets().add(StartScreen.class.getResource("/css/StartScreenStyleSheet.css").toExternalForm());

        this.startScreen = menu;
    }

    private void requestNewGame() {
        anInterface.toPNScreen();
    }

    private void requestToViewFinishedGames() {
        anInterface.loadFGames();
    }

    private void requestToUnfinishedGames() {
        anInterface.loadUFGames();
    }
}
