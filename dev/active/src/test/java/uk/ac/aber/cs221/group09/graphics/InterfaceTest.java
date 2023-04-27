package uk.ac.aber.cs221.group09.graphics;

import java.util.ArrayList;

public class InterfaceTest {
   private PlayScreen playScreen;

   /**
    * Intended to send the coordinates of the clicked tile to the backend. Currently the playground to test chessboard
    * features.
    * @param column
    * @param row
    */
   public void click(int column, int row) {
      ArrayList<Integer> validTileOne = new ArrayList<>();

      //Interface matches with the notation when clicking on top left of board
      if(column == 0 && row == 0) playScreen.updatePlayScreen("r1b1k1nr/p2p1pNp/n2B4/1p1NP2P/6P1/3P1Q2/P1P1K3/q5b1 w KQkq - 0 1");

      //Interface matches with the notation when clicking on top right of board
      else if(column == 0 && row == 7) playScreen.updatePlayScreen("8/8/8/4p1K1/2k1P3/8/8/8 b - - 0 1");

      //Interface matches with the notation when clicking on bottom left of board
      else if(column == 7 && row == 0) playScreen.updatePlayScreen("4k2r/6r1/8/8/8/8/3R4/R3K3 w Qk - 0 1");

      //Interface matches with the notation when clicking on bottom right of board
      else if(column == 7 && row == 7) playScreen.updatePlayScreen("rnbqkbnr/pppppppp/8/8/4P3/8/PPPP1PPP/RNBQKBNR b KQkq e3 0 1");

      //Interface matches with the notation when clicking on any of the tiles except these four corners on the board
      else playScreen.updatePlayScreen("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");



      System.out.println(column);
      System.out.println(row);
   }

}

