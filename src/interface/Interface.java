package com.example.screenstuff;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Interface extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        //FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        //Scene scene = new Scene(fxmlLoader.load(), 320, 240);

        PlayScreen playScreen = new PlayScreen(stage);
        Scene scenePlay = playScreen.constructPlayScreen();

        StartScreen startScreen = new StartScreen(stage);
        startScreen.setPlayScreen(scenePlay);
        Scene sceneStart = startScreen.constructScreen();

        stage.setTitle("Gorpu Chess!");
        stage.setScene(sceneStart);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}