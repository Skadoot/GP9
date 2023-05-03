/*
 * @(GP9) PlayScreen.java 0.9 2023/04/27
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
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import java.util.ArrayList;

/**
 * PlayScreen - A class for displaying the chessboard and associated functions.
 * <p>
 * This class contains the scene with the chessboard and is used to navigate a game.
 *
 * @author Gwion Hughes, Ciaran Smith
 * @version 0.9 draft
 * @see Chessboard
 */
public class PlayScreen {

    private final Interface anInterface;
    private Scene scene;
    private Chessboard chessboard;
    private Text whitePlayerName, blackPlayerName;

    //Player Dashboard
    private PlayScreenGraphicsLoader graphicsLoader;
    private StackPane dashboard;
    private Text turnTracker;
    private ImageView symbol;
    private int latestTurn = 0;

    /**
     * A getter for playscreen scene.
     *
     * @return the playscreen scene.
     */
    public Scene getScene() {
        return scene;
    }

    /**
     * Constructor method for the Play Screen.
     *
     * @param anInterface The GUI handler class
     */
    public PlayScreen(Interface anInterface) {
        this.chessboard = new Chessboard(this);
        this.anInterface = anInterface;
        this.graphicsLoader = new PlayScreenGraphicsLoader();
        constructPlayScreen();
    }

    /**
     * Setter for the white player name on the scene.
     * @param name - String, name of white player.
     */
    public void setWhitePlayerName(String name) {
        whitePlayerName.setText(name);
    }

    /**
     * Setter for the black player name on the scene
     * @param name - String, name of black player
     */
    public void setBlackPlayerName(String name) {
        blackPlayerName.setText(name);
    }

    /**
     * Function called in the constructor to create the screen
     */
    private void constructPlayScreen() {
        GridPane layout = new GridPane();
        StackPane root = new StackPane(layout);
        root.setAlignment(Pos.CENTER);

        layout.setPadding(new Insets(10, 10, 10, 10));

        //Setting up column and row width for the layout gridpane. Currently an 18x12 grid.
        for (int counter = 0; counter < 18; counter++) {
            ColumnConstraints cConstraint = new ColumnConstraints(60);
            layout.getColumnConstraints().add(cConstraint);

            if (counter < 12) {
                RowConstraints rConstraints = new RowConstraints(60);
                layout.getRowConstraints().add(rConstraints);
            }
        }

        //Adding the chessboard to size 5-13x0-8

        layout.add(this.chessboard.getChessBoard(), 5, 1, 8, 8);

        //Adding container to display white player's name
        Label whiteP = new Label("White: ");
        Text whiteName = new Text("Joe Bloggs");
        this.whitePlayerName = whiteName;
        HBox whiteContainer = new HBox(whiteP, whiteName);
        whiteContainer.setAlignment(Pos.CENTER);
        layout.add(whiteContainer, 5, 9, 8, 1);


        //Adding container to display Black player's name
        Label blackP = new Label("Black: ");
        Text blackName = new Text("Mary Lavender");
        this.blackPlayerName = blackName;
        HBox blackContainer = new HBox(blackP, blackName);
        blackContainer.setAlignment(Pos.CENTER);
        layout.add(blackContainer, 5, 0, 8, 1);

        //Player Dashboard. Is a StackPane.
        StackPane playerDashboard = createDashboard();
        layout.add(playerDashboard, 0, 1, 4, 6);


        //Log container.
        HBox log = new HBox();
        ScrollPane logContainer = new ScrollPane(log);
        layout.add(logContainer, 2, 10, 14, 1);

      //The below virtual box will be  used to track the game such as material captured.
/*
        VBox extraLayout = new VBox();
        Text extraLayoutFill = new Text("Extra Stuff");
        extraLayout.setStyle("-fx-border-color: GREEN");
        extraLayout.getChildren().add(extraLayoutFill);

        layout.add(extraLayout, 14, 0, 18, 8);
*/

   //adds a button for looking through previous moves
   Button previousB = new Button("<-");
      previousB.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent actionEvent) {
         decrementThroughLog();
      }
   });
      layout.add(previousB, 5, 9, 2, 1);

   //adds a button for seeking ahead through moves
   Button nextB = new Button("->");

      nextB.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent actionEvent) {
         incrementThroughLog();
      }
   });

   //looks nice but idk
      layout.add(nextB, 7, 9, 2, 1);
   //layout.add(nextB,12,9,2,1);
        //The quit button for exiting the game.
        Button quitB = new Button("Quit.");
        quitB.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                //Button action covers
                areYouSure();
            }
        });

        layout.setAlignment(Pos.CENTER);

        layout.add(quitB, 14, 0, 2, 1);

        //Makes the gridlines visible in layout. Useful for debugging
        //layout.setGridLinesVisible(true);


        Scene playScreen = new Scene(root, 1280, 720);
        playScreen.getStylesheets().add(getClass().getResource("/css/PlayScreenStyleSheet.css").toExternalForm());


        this.scene = playScreen;
    }

    public StackPane createDashboard() {
        //Player Dashboard.
        VBox playerArea = new VBox();
        playerArea.setAlignment(Pos.CENTER);
        playerArea.setSpacing(12);
        HBox imageDisplay = new HBox();
        imageDisplay.setAlignment(Pos.CENTER);
        HBox nameDisplay = new HBox();
        nameDisplay.setAlignment(Pos.CENTER);

        //Display current player's turn
        Text playerName = new Text("Player's turn");
        this.turnTracker = playerName;
        nameDisplay.getChildren().add(playerName);

        //Player Dashboard buttons
        HBox playerButtonBar = new HBox();
        playerButtonBar.setAlignment(Pos.CENTER);
        playerButtonBar.setSpacing(12);
        Button resign = new Button("Resign");
        Button offerDraw = new Button("Offer Draw");
        playerButtonBar.getChildren().addAll(resign, offerDraw);

        resign.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                resign();
            }
        });

        offerDraw.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                offerDraw();
            }
        });

        //Player Image
        ImageView icon = new ImageView();
        this.symbol = icon;
        imageDisplay.getChildren().add(this.symbol);

        playerArea.getChildren().addAll(imageDisplay, nameDisplay, playerButtonBar);
        playerArea.setStyle("-fx-border-color: BLACK");


        StackPane playerDashboard = new StackPane(playerArea);
        StackPane.setAlignment(playerArea, Pos.CENTER);

        this.dashboard = playerDashboard;

        return playerDashboard;
    }

    /**
     * Pass the coordinates of the button pressed up to the backend-frontend interface class.
     * @param column - The file or vertical column of the pressed tile.
     * @param row - The rank or horizontal row of the pressed tile.
     */
    public void alertPressedTile(int column, int row) {
        anInterface.click(column, row);
        startedViewing=false;
    }

    /**
     * Function to pass a FEN string of the backend board state to the Chessboard to update the graphical board.
     * @param boardNotation - String, Forsyth Edwards Notation representing state of board.
     */
    public void updatePlayScreen(String boardNotation) {
        //Update the chessboard with the FEN string.
        String[] sectionedNotation = boardNotation.split(" ");
        chessboard.updateBoard(sectionedNotation[0]);

        //Update the player Dashboard to show the current player's turn
        char player = sectionedNotation[1].charAt(0);
        updatePlayerDashboard(player);

        //Update the int of the latest turn. This is so we know the most up to date version of the game.
        char turn = sectionedNotation[5].charAt(0);
        if (latestTurn < Character.getNumericValue(turn)) latestTurn = Character.getNumericValue(turn);

        //Update Game state from backend. I.e is the game over?
        char gameState = sectionedNotation[6].charAt(0);
        gameOverOverlay(gameState);
    }

    public void updatePlayerDashboard (char player) {
        if (player == 'w') {
            this.symbol.setImage(graphicsLoader.getImage('W'));
            turnTracker.setText(whitePlayerName.getText());
        } else {
            this.symbol.setImage(graphicsLoader.getImage('B'));
            turnTracker.setText(blackPlayerName.getText());
        }
    }


    /**
     * Function fired when a draw is offered. A screen will show offering a draw to the next player. Game will end in
     * a draw if accepted.
     */
    public void offerDraw() {
        //Function to disable buttons on the Chessboard to disallow further play
        chessboard.disableChessboard(true);

        //The following is the containers for the buttons and text being set up.
        //Draw Window requires a background otherwise user will be able to interact with the player Dashboard
        VBox drawWindow = new VBox();
        BackgroundFill bf = new BackgroundFill(Color.valueOf("#DAE9F3"), new CornerRadii(10), new Insets(10));
        Background bg = new Background(bf);
        drawWindow.setBackground(bg);
        drawWindow.setAlignment(Pos.CENTER);
        drawWindow.setSpacing(12);
        VBox textContainer = new VBox();
        textContainer.setAlignment(Pos.CENTER);

        //Text is drawn into two different objects so they display center properly.
        Text drawTextOne = new Text("Player has offered a draw.\n");
        Text drawTextTwo = new Text("The game will be saved and viewable.\n");

        //Creating the button container and its contents.
        HBox buttonContainer = new HBox();
        buttonContainer.setAlignment(Pos.CENTER);
        buttonContainer.setSpacing(12);
        Button accept = new Button("Accept Draw");
        //Set the action of accept return to the menu and message the game has finished
        accept.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                anInterface.updateGameOver('D');
                gameOverOverlay('d');
            }
        });
        Button reject = new Button("Reject Draw");
        //Set action of reject to remove the container from the playerDashboard and re-enable the chessboard.
        reject.setOnAction(actionEvent -> {
            //continue game
            dashboard.getChildren().remove(drawWindow);
            chessboard.disableChessboard(false);
        });

        //Nest the containers
        textContainer.getChildren().addAll(drawTextOne,drawTextTwo);
        buttonContainer.getChildren().addAll(accept,reject);
        drawWindow.getChildren().addAll(textContainer, buttonContainer);

        //Place over the player dashboard.
        dashboard.getChildren().add(drawWindow);
        dashboard.setAlignment(drawWindow, Pos.CENTER);
    }

    /**
     * Function is called when the player presses the resign button. A container will show over the chessboard asking
     * the player if they would really like to resign. If they press yes, game ends with the next players winning.
     */
    public void resign() {
        //Disable chessboard
        chessboard.disableChessboard(true);

        //Initialising containers. Set the root container to have a background so the player Dashboard beneath is not
        //interactable.
        VBox resignWindow = new VBox();
        resignWindow.setAlignment(Pos.CENTER);
        BackgroundFill bf = new BackgroundFill(Color.valueOf("#DAE9F3"), new CornerRadii(10), new Insets(10));
        Background bg = new Background(bf);
        resignWindow.setBackground(bg);
        VBox textContainer = new VBox();
        textContainer.setAlignment(Pos.CENTER);

        //Text content broken into two texts so they align properly.
        Text resTextOne = new Text("Player is choosing to resign.\n");
        Text resTextTwo = new Text("The game will be saved and viewable.\n");

        //Button and button containers
        HBox buttonContainer = new HBox();
        buttonContainer.setAlignment(Pos.CENTER);
        buttonContainer.setSpacing(12);
        Button resign = new Button("Resign");
        Button cont = new Button("Continue");
        //If resign is selected, game is ended in player's defeat and saved.
        resign.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                //Resign quit game ladi da
                if(latestTurn%2 == 1) {
                    anInterface.updateGameOver('W');
                    gameOverOverlay('w');
                } else {
                    anInterface.updateGameOver('B');
                    gameOverOverlay('b');
                }
                dashboard.getChildren().remove(resignWindow);
            }
        });
        //if continue is selected, game is continued, chessboard reenabled, and window removed.
        cont.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                //continue game
                dashboard.getChildren().remove(resignWindow);
                chessboard.disableChessboard(false);
            }
        });

        //Nest the containers
        buttonContainer.getChildren().addAll(resign, cont);
        textContainer.getChildren().addAll(resTextOne,resTextTwo);
        resignWindow.getChildren().addAll(textContainer,buttonContainer);

        //Place over the player Dashboard.
        dashboard.getChildren().add(resignWindow);
        StackPane.setAlignment(resignWindow, Pos.CENTER);
    }

    /**
     * Function to called when a promotion is available for a pawn. Overlays the player Dashboard with a new container
     * containing four buttons. The chessboard is disabled until the player chooses a promotion. The button's display
     * the graphics of the available pieces to promote to. Will notify the interface of the user's selection.
     */
    public void offerPromotion(){
        //Disable the chessboard to stop play until promotion selected.
        chessboard.disableChessboard(true);

        //Root container for the promotion window.
        VBox promotionWindow = new VBox();
        promotionWindow.setSpacing(12);
        promotionWindow.setAlignment(Pos.CENTER);
        BackgroundFill bf = new BackgroundFill(Color.valueOf("#DAE9F3"), new CornerRadii(10), new Insets(10));
        Background bg = new Background(bf);
        promotionWindow.setBackground(bg);

        //Containers for the text and the buttons.
        HBox textContainer = new HBox();
        textContainer.setAlignment(Pos.CENTER);
        HBox buttonBarOne = new HBox();
        buttonBarOne.setSpacing(12);
        buttonBarOne.setAlignment(Pos.CENTER);
        HBox buttonBarTwo = new HBox();
        buttonBarTwo.setSpacing(12);
        buttonBarTwo.setAlignment(Pos.CENTER);

        //Display Text
        Text text = new Text("Pick a promotion for your pawn!");

        //Buttons and their graphics.
        Button queenPromote = new Button();
        queenPromote.setGraphic(new ImageView(graphicsLoader.getImage('q')));
        Button rookPromote = new Button();
        rookPromote.setGraphic(new ImageView(graphicsLoader.getImage('r')));
        Button bishopPromote = new Button();
        bishopPromote.setGraphic(new ImageView(graphicsLoader.getImage('b')));
        Button knightPromote = new Button();
        knightPromote.setGraphic(new ImageView(graphicsLoader.getImage('k')));

        //Adding text and buttons to their containers
        textContainer.getChildren().add(text);
        buttonBarOne.getChildren().addAll(queenPromote,rookPromote);
        buttonBarTwo.getChildren().addAll(bishopPromote,knightPromote);

        //Giving actions to buttons to call a pawn promotion request from the front end.
        queenPromote.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                dashboard.getChildren().remove(promotionWindow);
                //Request queen promotion
                anInterface.requestPromotion(0);
                //reenable chessboard
                chessboard.disableChessboard(false);
            }
        });

        rookPromote.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                dashboard.getChildren().remove(promotionWindow);
                anInterface.requestPromotion(1);
                //reenable chessboard
                chessboard.disableChessboard(false);
            }
        });

        bishopPromote.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                dashboard.getChildren().remove(promotionWindow);
                anInterface.requestPromotion(2);
                //reenable chessboard
                chessboard.disableChessboard(false);
            }
        });

        knightPromote.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                dashboard.getChildren().remove(promotionWindow);
                anInterface.requestPromotion(3);
                //reenable chessboard
                chessboard.disableChessboard(false);
            }
        });

        //Adding buttonbar and text container to root container.
        promotionWindow.getChildren().addAll(textContainer,buttonBarOne,buttonBarTwo);

        //Overlay the promotion window on the player dashboard.
        dashboard.getChildren().add(promotionWindow);
        StackPane.setAlignment(promotionWindow, Pos.CENTER);
    }

    /**
     * Call to backend to show the next move in the log. Activated by pressing a log button. Deactivate chess buttons
     */
    boolean startedViewing = false;
    int currentTurn;
    public void incrementThroughLog() {
        //a function triggered by a button. Push the next board state forwards

        if (!startedViewing) {
            currentTurn = (anInterface.getTurnNumber() - 1);
            startedViewing = true;
        }
        if (!(currentTurn > anInterface.getTurnNumber() - 1)) {
            //increases the turn we are looking at by 1
            currentTurn++;
        }
        chessboard.updateBoard(anInterface.getPreviousFEN(currentTurn));

        if (currentTurn == anInterface.getTurnNumber()) {
            chessboard.disableChessboard(false);
        } else {
            chessboard.disableChessboard(true);
        }
    }
    /**
     * Call to backend to show the previous move in the log. Activated by pressing a log button. Deactivate chess buttons.
     */
    public void decrementThroughLog() {
        //A function triggered by a button. View the past through a spooky crystal ball ooooo

        if (!startedViewing) {
            //sets the index for navigation through the played moves to be the last move played
            currentTurn = (anInterface.getTurnNumber() - 1);
            //sets the boolean to true, so we can iterate through the moves without causing errors
            startedViewing = true;
            //if we've already started looking at our played moves we just continue to iterate backwards
            // from the last turn we looked at
        } else {
            //makes sure we don't go beyond the array boundaries
            if (currentTurn > 0) {
                currentTurn--;
            }
    }
        if (currentTurn == anInterface.getTurnNumber()) {
            chessboard.disableChessboard(false);
        } else {
            chessboard.disableChessboard(true);
        }
        chessboard.updateBoard(anInterface.getPreviousFEN(currentTurn));
    }


    /**
     * Called to create container over the chessboard to ask the player are they sure they'd like to quit..
     */
    public void areYouSure() {
        //We know the stackpane is the root. However if this changes, expect problems here.
        StackPane root = (StackPane) scene.getRoot();

        BackgroundFill bf = new BackgroundFill(Color.valueOf("#DAE9F3"), new CornerRadii(10), new Insets(10));
        Background bg = new Background(bf);

        VBox exitContainer = new VBox();
        exitContainer.setSpacing(12);
        exitContainer.setAlignment(Pos.CENTER);
        exitContainer.setBackground(bg);
        VBox textContainer = new VBox();
        textContainer.setAlignment(Pos.CENTER);
        HBox buttonContainer = new HBox();
        buttonContainer.setAlignment(Pos.CENTER);
        buttonContainer.setSpacing(12);

        Text exitTextOne = new Text("Quitting without saving your progress will result in the game being deleted.\n");
        Text exitTextTwo = new Text("Press one of buttons below to continue.");

        Button noSave = new Button("Exit without saving");
        noSave.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                //Call game to be deleted
                anInterface.toMenu();
            }
        });

        Button saveQ = new Button("Save and exit");
        saveQ.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                //Game is saved in the background so just quit
                anInterface.toMenu();
            }
        });

        Button back = new Button("Back");
        back.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                //Drop the menu and continue game.
                root.getChildren().remove(root.getChildren().get(1));
            }
        });

        textContainer.getChildren().addAll(exitTextOne, exitTextTwo);
        buttonContainer.getChildren().addAll(noSave,saveQ,back);
        exitContainer.getChildren().addAll(textContainer,buttonContainer);

        root.getChildren().add(exitContainer);
        StackPane.setAlignment(exitContainer, Pos.CENTER);
    }

    /**
     * Disable the chessboard and show on the player dashboard the results of the game.
     * @param c - A character on the FEN string to indicate the victory!
     */
    public void gameOverOverlay(char c) {
        if(c == '-') {
            return;
        }

        //Disable Chessboard
        chessboard.disableChessboard(true);

        //Root container for the promotion window.
        VBox victoryWindow = new VBox();
        victoryWindow.setSpacing(12);
        victoryWindow.setAlignment(Pos.CENTER);
        BackgroundFill bf = new BackgroundFill(Color.valueOf("#DAE9F3"), new CornerRadii(10), new Insets(10));
        Background bg = new Background(bf);
        victoryWindow.setBackground(bg);

        HBox textContainer = new HBox();
        textContainer.setAlignment(Pos.CENTER);
        HBox imageContainer = new HBox();
        imageContainer.setAlignment(Pos.CENTER);

        Text victoryText = new Text();

        ImageView victoryImage = new ImageView();

        textContainer.getChildren().add(victoryText);
        imageContainer.getChildren().add(victoryImage);

        switch(c) {
            case('w'):
                victoryText.setText("White's Victory!");
                victoryImage.setImage(graphicsLoader.getImage('W'));
                break;
            case('b'):
                victoryText.setText("Black's victory!");
                victoryImage.setImage(graphicsLoader.getImage('B'));
                break;
            case('d'):
                victoryText.setText("Game ended in draw.");
                victoryImage.setImage(graphicsLoader.getImage('D'));
                break;
        }

        victoryWindow.getChildren().addAll(imageContainer,textContainer);

        //Overlay the promotion window on the player dashboard.
        dashboard.getChildren().add(victoryWindow);
        StackPane.setAlignment(victoryWindow, Pos.CENTER);
    }

    public void highlightTiles(ArrayList<int[]> vTiles, ArrayList<int[]> checkTiles) {
        chessboard.highlightTiles(vTiles, checkTiles);
    }
}
