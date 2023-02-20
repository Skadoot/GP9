package com.example.screenstuff;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Interface extends Application {
    private Stage primaryStage;

    @Override
    public void start(Stage stage) throws IOException {
        primaryStage = stage;

/*        PlayScreen playScreen = new PlayScreen(this);
        Scene scenePlay = playScreen.constructPlayScreen();

        StartScreen startScreen = new StartScreen(this);
        startScreen.setPlayScreen(scenePlay);
        Scene sceneStart = startScreen.constructScreen();*/

        PlayerNameScreen playNameScreen = new PlayerNameScreen();
        Scene pnScreen = playNameScreen.createScreen();

        stage.setTitle("Gorpu Chess!");
        stage.setScene(pnScreen);
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
}