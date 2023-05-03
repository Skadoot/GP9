/*
 * @(#) Log.java 0.1 2023/03/16
 *
 * Copyright (c) 2023 Aberystwyth University
 * All rights reserved.
 */

package uk.ac.aber.cs221.group09.logic;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Main - Temporary main class to test the game logic.
 * <p>
 * Temporary testing class
 *
 * @author Shaun Royle, Jack Thompson
 * @version 0.1 (draft)
 * @see uk.ac.aber.cs221.group09.logic.Log
 */
public class Main {
   private static Game game = null;

   public static void main(String[] args) {
      //run main menu

//        //new game
//        game = new Game("r1b1k1nr/p2p1pNp/n2B4/1p1NP2P/6P1/3P1Q2/P1P1K3/q5b1 w KQkq - 0 1", "testReplace", false);
//        game.log.updateLog("FEN1");
//      game.log.updateLog("FEN2");
//      game.log.updateLog("FEN3");
//      game.log.replaceLine(0, "replacement");
//    }

   //I don't really understand the point of this method(below) in here? jat92
   // I think I now understand it but might be better to have a class between main and game, like GameController? jat92
   //or return a game object... jat92
//    private static void create_game() {
//        //game = new game("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");
//        game = new game("r1b1k1nr/p2p1pNp/n2B4/1p1NP2P/6P1/3P1Q2/P1P1K3/q5b1 w KQkq - 0 1", fileName);
//        //game = new game("8/8/8/4p1K1/2k1P3/8/8/8 b - - 0 1");
//        //game = new game("4k2r/6r1/8/8/8/8/3R4/R3K3 w Qk - 0 1");
//        //game = new game("rnbqkbnr/pppppppp/8/8/4P3/8/PPPP1PPP/RNBQKBNR b KQkq e3 0 1");
    }


   /**
    * Searches the file that is being used to load the game and returns the last line in that file so that the board
    * state can be loaded with the most recent state. Returns null if the file does not exist.
    *
    * @param fileName the name of an existing file that the FEN will be searched for in.
    * @return the last line in 'fileName'. This will be the most recent board state of the game to be loaded in.
    */
   private static String getFenForLoadGame(String fileName) {
      String lastLine = null;
      try {
         Path pathToFile = Paths.get(fileName); //Create path object to hold path to the file
         int numOfLines = Files.readAllLines(pathToFile).size() - 1; //-1 because file lines start at 0 but .size counts list items starting at 1
         lastLine = Files.readAllLines(pathToFile).get(numOfLines);
      } catch (IOException e) {
         System.out.println("IO Error");
      }
      return lastLine;
   }

   /**
    * Loads a previous game. Takes a filename and uses that to generate the board state of the previous game. If the
    * file does not exist then an error message is printed to the console (this could be sent back to the front end).
    *
    * @param fileName the name of the file that will be used to load the game
    */
   private static void loadGame(String fileName) {
      //if the file does not exist
      if (getFenForLoadGame(fileName) == null) {
         System.out.println("file does not exist");
      } else { //if file does exist
         game = new Game(getFenForLoadGame(fileName), fileName, true);
      }
   }
}
