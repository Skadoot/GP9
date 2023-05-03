/*
 * @(GP9) Log.java 1.1 2023/05/02
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
 * <p>
 * This class is used to read/write the FEN strings that are played in each turn.
 *
 * @author Jack Thompson
 * @version 1.1 (Release)
 * @see uk.ac.aber.cs221.group09.logic.Main
 */
public class Log {
   private String fileName;
   private int numberOfLines = 0; //to keep track of the number of lines in the file
   private String nameOfFolderToHoldUnfinishedGames = "./unfinishedGames";
   private String nameOfFolderToHoldFinishedGames = "./finishedGames";


   /**
    * Constructor for Log class.
    * <p>
    * If load is set to false, it makes a new text file to record FEN strings after each turn.
    * If the file name already exists, it will be overwritten.
    * If load is set to true then an existing text file is used.
    * If a new file is made then the FEN string for an initial board state is also added.
    *
    * @param fileName the name of the file to be made or loaded
    */
   public Log(String fileName) {
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
      updateLog("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1"); // Append the initial board state to the new log file
   }

   /**
    * Simple constructor.
    * <p>
    * Used in loading games where the log class needs to be instantiated before filename is set.
    */
   public Log() {
   }

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
      File dir = new File(nameOfFolderToHoldUnfinishedGames);
      try {
         FileWriter fileWriter = new FileWriter(new File(dir, this.fileName), true);
         fileWriter.append(FEN).append("\n");
         fileWriter.close();
         numberOfLines++;
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
    * @param lineNumber The line number of the file to be returned
    * @return The string at the requested line number or null if an exception occurs.
    */
   public String readLog(int lineNumber) {
      //initialise variable with null in case of IO Exception
      String fenAtLineNumber = null;
      try {
         //assign variable the string at the requested line number of the file
         fenAtLineNumber = Files.readAllLines(Paths.get(nameOfFolderToHoldUnfinishedGames, fileName)).get(lineNumber);
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
         this.numberOfLines = Files.readAllLines(Paths.get(nameOfFolderToHoldUnfinishedGames, fileName)).size();
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
      // Check all the files to see in the path to see if they are .txt files

      File currentFolder = new File("./unfinishedGames"); // The relative file path to where the files are saved
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
    * Replaces the line in the file. Designed to be used in comparison with the Log field number of The lines. Numbering
    * in the file starts at 0. The Log field number of lines counts the lines. So, to replace the last line use
    * Log.numberOfLines - 1 as parameter.
    * @param lineNumber the line in the file to be replaced. File line numbering starts at 0.
    * @param replacementLine the line to replace the current one
    */
   public void replaceLine(int lineNumber, String replacementLine){
      //declare a string to hold the path to where the log file is
      String pathToCurrentFile = nameOfFolderToHoldUnfinishedGames+"/"+fileName;
      Path path = Paths.get(pathToCurrentFile);

      //make sure the line number is in the bounds of the file otherwise do not try
      if (lineNumber < this.numberOfLines && lineNumber > -1){
         try{
            //add all the lines in the file to a list, each item in the list is a line
            List<String> logLines = Files.readAllLines(path, StandardCharsets.UTF_8);
            //remove the specified line
            logLines.remove(lineNumber);
            //add the new line
            logLines.add(lineNumber, replacementLine);
            //write back to file
            Files.write(path, logLines, StandardCharsets.UTF_8);
         } catch (IOException e){
            System.out.println("IO Error");
         }
      } else {
         System.out.println("line not in file");
      }

   }
}
