package uk.ac.aber.cs221.group09.graphics;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.HashMap;

public class PlayScreenGraphicsLoader {
   private HashMap<Character, Image> graphicsMap;
   public PlayScreenGraphicsLoader() {
      setGraphics();
   }

   private void setGraphics() {
      graphicsMap = new HashMap<Character, Image>();

      graphicsMap.put('W', new Image("/images/playscenepieces/WhiteKing.png"));
      graphicsMap.put('B', new Image("/images/playscenepieces/BlackKing.png"));
      graphicsMap.put('D', new Image("/images/playscenepieces/DrawKing.png"));
      graphicsMap.put('q', new Image("/images/playscenepieces/WhiteQueen.png"));
      graphicsMap.put('r', new Image("/images/playscenepieces/BlackRook.png"));
      graphicsMap.put('b', new Image("/images/playscenepieces/WhiteBishop.png"));
      graphicsMap.put('k', new Image("/images/playscenepieces/BlackKnight.png"));
   }

   public Image getImage(char c) {
      return graphicsMap.get(c);
   }
}