/*
 * @(GP9) Log.java 1.2 2023/05/02
 *
 * Copyright (c) 2023 Aberystwyth University
 * All rights reserved.
 */

package uk.ac.aber.cs221.group09.logic;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Log - Records the progress of the game.
 * This class is used to read/write the FEN strings that are played in each turn.
 *
 * @author Jack Thompson, Gwionn
 * @version 1.1 (Release)
 */
public class Log {
   private String fileName; //the name of the file
   private int numberOfLines = 0; //to keep track of the number of lines in the file
   private String nameOfFolderToHoldUnfinishedGames = "./unfinishedGames"; //the relative path to the unfinishedGames directory
   private String nameOfFolderToHoldFinishedGames = "./finishedGames"; //the relative path to the finishedGames directory
   private String nameOfFolder; //holds either the path to the finished or unfinished games folder.


   /**
    * Constructor for Log class. Used for creating new games. Creates a new .txt file using fileName param to record
    * the game. If the file name already exists, it will be overwritten.
    * If a new file is made then the FEN string for an initial board state is also added.
    *
    * @param fileName the name of the file to be made
    */
   public Log(String fileName) {
      //By default, the log should attach itself to the unfinished games folder when first constructed
      setFinishedGame(false);
      // If the unfinished game directory does not exist, create it
      new File(nameOfFolderToHoldUnfinishedGames).mkdirs();
      File dir = new File(nameOfFolderToHoldUnfinishedGames);
      // Make log for new game
      this.fileName = fileName + ".txt"; //add .txt to make it a txt file
      // Make a new file called 'fileName' in the unfinishedGames Directory.
      try {
         FileWriter fileWriter = new FileWriter(new File(dir, this.fileName));
         fileWriter.close();
      } catch (IOException e) {
         System.out.println("IO Error");
      }
      updateLog("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1 -"); // Append the initial board state to the new log file
   }

   /**
    * Simple constructor.
    * Used in loading games where the log class needs to be instantiated before filename is set.
    */
   public Log() {
   }

   /**
    * sets the nameOfFolder field to either the path for finished or unfinished games.
    * @param finishedGame true if accessing finished games, false if accessing unfinished games
    */
   public void setFinishedGame(boolean finishedGame) {
      if(finishedGame) {
         this.nameOfFolder = nameOfFolderToHoldFinishedGames; //set name of folder field to be same as finishedGame field
      } else {
         this.nameOfFolder = nameOfFolderToHoldUnfinishedGames; //set name of folder field to be same as unfinishedGame field
      }
   }

   /**
    * sets the fileName field
    * @param fileName the String to set the fileName field
    */
   public void setFileName(String fileName) {
      this.fileName = fileName;
   }

   /**
    * Updates the text file with the FEN string that is passed as a parameter.
    * It will append the FEN string to the next line in the file.
    *
    * @param FEN the Forsyth Edwards Notation representing the board state. This is what gets appended to the txt file.
    */
   public void updateLog(String FEN) {
      File dir = new File(this.nameOfFolder); //instantiate a file object for the directory the log file is in
      try {
         //make a fileWriter with the path to the log file and append set to true, to append FEN strings to the log
         FileWriter fileWriter = new FileWriter(new File(dir, this.fileName), true);
         fileWriter.append(FEN).append("\n");
         fileWriter.close();
         numberOfLines++; //keep track of the lines in the file
      } catch (IOException e) {
         System.out.println("IO Error");
      }
   }

   /**
    * Returns the specific line from a file as a string. Uses the Files.readsAllLines method to return
    * all lines in the file as a list of strings. Then uses the list.get method to return the specified
    * line as a string. THE FIRST LINE IS 0. If an IO error occurs a null string will be returned. If the line number requested
    * is not in the file, a null string is also returned.
    *
    * @param lineNumber The line number of the file to be returned starting at 0
    * @return The string at the requested line number or null if an exception occurs.
    */
   public String readLog(int lineNumber) {
      //initialise variable with null in case of IO Exception
      String fenAtLineNumber = null;
      try {
         //assign variable the string at the requested line number of the file
         fenAtLineNumber = Files.readAllLines(Paths.get(nameOfFolder, fileName)).get(lineNumber);
      } catch (IOException e) {
         System.out.println("IO Error");
      } catch (IndexOutOfBoundsException er) {
         System.out.println("line not in file");
      }
      return fenAtLineNumber;
   }

   /**
    * Returns the number of lines in the file. Sets the numberOfLines field.
    *
    * @return integer of number of lines in the file called fileName.
    */
   public int getNumberOfLines(){
      try {
         // Assign variable the string at the requested line number of the file
         this.numberOfLines = Files.readAllLines(Paths.get(nameOfFolder, fileName)).size();
      } catch (IOException e) {
         System.out.println("IO Error");
      }
      return this.numberOfLines;
   }

   /**
    * Collates the existing game files into an arrayList. Each item in the array list is a string.
    * By providing a path to the unfinishedGames directory, the files in that directory are checked to see if
    * they are a text file. All text files in this directory are known to be records of existing game files.
    *
    * @return ArrayList of file names in String format where each file is the record of a game.
    */
   public ArrayList<String> displayExistingGameFiles() {
      // Declare new array list to hold names of existing game files
      ArrayList<String> existingGameFiles = new ArrayList<String>();

      File currentFolder = new File(nameOfFolder);

      // Check all the files to see if they are .txt files
      File[] allTheFiles = currentFolder.listFiles(); // Store all the files in the current folder in an array
      for (int i = 0; i < allTheFiles.length; i++) {
         String fileBeingChecked = allTheFiles[i].getName();
         // Check the extension of the file. If it's a .txt file, add it to the ArrayList existingGameFiles.
         int index = fileBeingChecked.lastIndexOf('.');
         if (index > 0) {
            String extension = fileBeingChecked.substring(index + 1);
            if (extension.equals("txt")) {
               existingGameFiles.add(fileBeingChecked);
            }
         }
      }
      return existingGameFiles;
   }

   /**
    * Moves the current file being used to track the log from the unfinished game to the finished game directory.
    */
   public void moveFileToFinishedGamesDir(){
      // Create a new directory to hold finished games if one does not exist already.
      new File(nameOfFolderToHoldFinishedGames).mkdirs();
      // Declare two string variables to hold the paths to where the file is and where it's going
      String pathToCurrentFile = nameOfFolderToHoldUnfinishedGames+"/"+fileName;
      String pathToFinishedGamesFile = nameOfFolderToHoldFinishedGames+"/"+fileName;
      // Move the file from the unfinished games to the finished game directory
      File currentFile = new File(pathToCurrentFile);
      currentFile.renameTo(new File(pathToFinishedGamesFile));
   }

   /**
    * Replaces the line in the file. Designed to be used in comparison with the Log field number of lines.
    * Numbering in the file starts at 0. The Log field number of lines counts the lines.
    * So, to replace the last line use Log.numberOfLines - 1 as parameter.
    *
    * @param lineNumber the line in the file to be replaced. File line numbering starts at 0.
    * @param replacementLine the line to replace the current one
    */
   public void replaceLine(int lineNumber, String replacementLine){
      // Declare a string to hold the path to where the log file is
      String pathToCurrentFile = nameOfFolder+"/"+fileName;
      Path path = Paths.get(pathToCurrentFile);

      // Make sure the line number is in the bounds of the file otherwise do not try
      if (lineNumber < this.numberOfLines && lineNumber > -1){
         try{
            // Add all the lines in the file to a list, each item in the list is a line
            List<String> logLines = Files.readAllLines(path, StandardCharsets.UTF_8);
            // Remove the specified line
            logLines.remove(lineNumber);
            // Add the new line
            logLines.add(lineNumber, replacementLine);
            // Write back to file
            Files.write(path, logLines, StandardCharsets.UTF_8);
         } catch (IOException e){
            System.out.println("IO Error");
         }
      } else {
         System.out.println("line not in file");
      }

   }

   /**
    * Deletes the current log file that is in unfinished games.
    * Called when the user wishes to exit a game and not save the file.
    */
   public void deleteFile(){
      String pathToCurrentFile = nameOfFolder+"/"+fileName;
      File currentFile = new File(pathToCurrentFile);
      currentFile.delete();
   }
}
