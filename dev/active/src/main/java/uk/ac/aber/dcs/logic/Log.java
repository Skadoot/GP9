package uk.ac.aber.dcs.logic;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Log {
    private String fileName;
    private int numberOfLines = 0; //to keep track of the number of lines in the file

    //need to test this to see what happens when a filename is entered that does not exist when load is set to true.

    /**
     * constructor for Log class. If load is set to false then makes a new text file for the FEN strings to be recorded in each turn.
     * If the file name is the same as a file that already exists then it will be overwritten. If load is set to true then
     * an existing text file is used.
     *
     * @param fileName the name of the file to be made
     */
    public Log(String fileName, boolean load) {
        if (!load) {
            //make log for new game
            this.fileName = fileName + ".txt"; //add .txt to make it a txt file
            //make a new file called 'fileName'.
            try {
                FileWriter fileWriter = new FileWriter(this.fileName);
                fileWriter.close();
            } catch (IOException e) {
                System.out.println("IO Error");
            }
        } else {
            //make log for load game
            this.fileName = fileName;
        }
    }

    /**
     * updates the text file with the FEN string that is passed as a parameter. It will append the FEN string to the
     * next line in the file.
     *
     * @param FEN the Forsyth Edwards Notation representing the board state. This is what gets appended to the txt file.
     */
    public void updateLog(String FEN) {
        try {
            FileWriter fileWriter = new FileWriter(fileName, true);
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
            fenAtLineNumber = Files.readAllLines(Paths.get(fileName)).get(lineNumber);
        } catch (IOException e) {
            System.out.println("IO Error");
        } catch (IndexOutOfBoundsException er) {
            System.out.println("line not in file");
        }
        return fenAtLineNumber;
    }
}
