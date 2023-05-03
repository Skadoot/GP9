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
   }

   public Image getImage(char c) {
      return graphicsMap.get(c);
   }
}