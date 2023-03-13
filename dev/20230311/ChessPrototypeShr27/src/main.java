//remember you commented out move method for testing

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class main
{
    private static game game = null;

    public static void main(String[] args) {
        //run main menu

        //new game
        game = new game("r1b1k1nr/p2p1pNp/n2B4/1p1NP2P/6P1/3P1Q2/P1P1K3/q5b1 w KQkq - 0 1", "testGameOne");
        //update the log
        game.log.updateLog("FEN1");
        game.log.updateLog("FEN2");
        game.log.updateLog("FEN3");
        //read specific line from the file
        System.out.println(game.log.readLog(2));
        System.out.println(game.log.readLog(10));

    }

    //I don't really understand the point of this method(below) in here? jat92
//    private static void create_game() {
//        //game = new game("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");
//        game = new game("r1b1k1nr/p2p1pNp/n2B4/1p1NP2P/6P1/3P1Q2/P1P1K3/q5b1 w KQkq - 0 1", fileName);
//        //game = new game("8/8/8/4p1K1/2k1P3/8/8/8 b - - 0 1");
//        //game = new game("4k2r/6r1/8/8/8/8/3R4/R3K3 w Qk - 0 1");
//        //game = new game("rnbqkbnr/pppppppp/8/8/4P3/8/PPPP1PPP/RNBQKBNR b KQkq e3 0 1");
//    }

    //this is a work in progress
    private String getFenForLoadGame(String fileName) {
        String lastLine = null;
        try{
            int numOfLines = Files.readAllLines(Paths.get(fileName)).size();
            lastLine = Files.readAllLines(Paths.get(fileName)).get(numOfLines);
        } catch (IOException e){
            System.out.println("IO Error");
        }
        return lastLine;
    }
}
