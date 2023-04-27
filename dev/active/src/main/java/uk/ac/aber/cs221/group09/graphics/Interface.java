/*
 * @(GP9) Interface.java 0.5 2023/04/27
 *
 * Copyright (c) 2021 Aberystywth University
 * All rights reserved
 *
 */

package uk.ac.aber.cs221.group09.graphics;

import javafx.application.Application;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Interface - The class contains the primary stage for displaying the chess tutor application
 *
 * The class is used to initialise the application. It's the top level of the system and messages information
 * to the backend. Information from the backend is then sent through to the GUI through this interface class.
 *
 * @author Gwion Hughes, Ciaran Smith
 * @version 0.9 draft
 * @see StartScreen
 */
public class Interface extends Application {
    private Stage primaryStage;
    private PlayerNameScreen playerNameScreen;
    private PlayScreen playScreen;
    private StartScreen startScreen;
    private LoadScreen loadScreen;

    @Override
    public void start(Stage stage) throws IOException {
        //This is application window
        primaryStage = stage;

        //Create instances of the screen handling classes and attach this interface instance to those classes.
        playerNameScreen = new PlayerNameScreen(this);
        playScreen = new PlayScreen(this);
        startScreen = new StartScreen(this);
        loadScreen = new LoadScreen(this);

        //This sets the application window to display the start screen and then show the application window.
        stage.setTitle("Gorpu Chess!");
        stage.setScene(startScreen.getStartScreen());
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    /**
     * Getter for the applicaiton window.
     * @return - Stage(Application window)
     */
    public Stage getStage() {
        return primaryStage;
    }

    /**
     * Function to message the column and row of the tile clicked on the chessboard to the backend. Any FEN string
     * returned from the back end will be sent back to the playscreen.
     *
     * At the moment, shows how the click function would work without backend. Currently sends a different FEN string
     * to the playscreen depending on which corner pressed.
     * @param column - The file or vertical column of the pressed tile.
     * @param row - The rank or horizontal row of the pressed tile.
     */
    public void click(int column, int row) {
        ArrayList<Integer> validTileOne = new ArrayList<>();

        if(column == 0 && row == 0) playScreen.updatePlayScreen("r1b1k1nr/p2p1pNp/n2B4/1p1NP2P/6P1/3P1Q2/P1P1K3/q5b1 w KQkq - 0 1");

        else if(column == 0 && row == 7) playScreen.updatePlayScreen("8/8/8/4p1K1/2k1P3/8/8/8 b - - 0 1");

        else if(column == 7 && row == 0) playScreen.updatePlayScreen("4k2r/6r1/8/8/8/8/3R4/R3K3 w Qk - 0 1");

        else if(column == 7 && row == 7) playScreen.updatePlayScreen("rnbqkbnr/pppppppp/8/8/4P3/8/PPPP1PPP/RNBQKBNR b KQkq e3 0 1");

        else playScreen.updatePlayScreen("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");



        System.out.println(column);
        System.out.println(row);
    }

    /**
     * Function to switch the application window to show the start menu.
     */
    public void toMenu() {
        primaryStage.setScene(startScreen.getStartScreen());
    }

    /**
     * Function to switch the application window to show the screen to enter player names.
     */
    public void toPNScreen() {
        primaryStage.setScene(playerNameScreen.getScene());
    }

    /**
     * Function to switch the application window to show the play screen containing the chessboard.
     */
    public void toChessboard() {
        primaryStage.setScene(playScreen.getScene());
    }

    /**
     * Function to switch the application window from the scene to enter player names to the chessboard scene.
     * Will take the names entered by the users for black and white to display on a new chessboard with a fresh
     * default start.
     *
     * Should also instantiate a new game in the backend to log in a text file.
     * @param whiteName - A string array of the white player's name.
     * @param blackName - A string array of the black player's name.
     */
    public void toNewChessboard(String whiteName, String blackName) {
        playScreen.setWhitePlayerName(whiteName);
        playScreen.setBlackPlayerName(blackName);
        //FEN setting the playscreen scene chessboard to default start position.
        playScreen.updatePlayScreen("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");
        primaryStage.setScene(playScreen.getScene());
    }

    /**
     * Switch scene displayed to a scene that displays all the finished games to view through.
     */
    public void loadFGames() {
        //loadScreen.populateButtonBar(list of finished games);
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