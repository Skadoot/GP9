package com.example.screenstuff;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Interface extends Application {
    private Stage primaryStage;
    private PlayerNameScreen playerNameScreen;
    private PlayScreen playScreen;
    private StartScreen startScreen;
    private LoadScreen loadScreen;

    @Override
    public void start(Stage stage) throws IOException {
        primaryStage = stage;

        //Create instances of the screen handling classes and attach this interface instance to those classes.
        playerNameScreen = new PlayerNameScreen(this);
        playScreen = new PlayScreen(this);
        startScreen = new StartScreen(this);
        loadScreen = new LoadScreen(this);


        stage.setTitle("Gorpu Chess!");
        stage.setScene(startScreen.getStartScreen());
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    public Stage getStage() {
        return primaryStage;
    }

    public void click(int column, int row) {
        // Pass to chessboard the piece that was pressed.
        // Chessboard does a thing and returns a vector map of possible moves(if any).
        // Backend will handle the logic like if the player wants to move a different piece.
        System.out.println(column);
        System.out.println(row);
    }

    public void toMenu() {
        primaryStage.setScene(startScreen.getStartScreen());
    }

    public void toPNScreen() {
        primaryStage.setScene(playerNameScreen.getScene());
    }

    public void toChessboard() {
        primaryStage.setScene(playScreen.getScene());
    }

    public void toNewChessboard(String whiteName, String blackName) {
        playScreen.setWhitePlayerName(whiteName);
        playScreen.setBlackPlayerName(blackName);
        primaryStage.setScene(playScreen.getScene());
    }

    /**
     * Switch scene displayed to a scene that displays all the finished games to view through.
     */
    public void loadFGames() {
        //loadSceen.populateButtonBar(list of finished games);
        loadScreen.setLabel("Finished Games:");
        primaryStage.setScene(loadScreen.getScene());
    }

    /**
     * Switch the scene displayed to a scene that displays all the unfinished games to pick back up from.
     */
    public void loadUFGames() {
        //loadScreen.populateButtonBar(list of unfinished games);
        loadScreen.setLabel("Unfinished Games:");
        primaryStage.setScene(loadScreen.getScene());
    }
}