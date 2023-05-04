/*
 * @(GP9) PlayScreenGraphicsLoader.java 1.0 2023/04/27
 *
 * Copyright (c) 2021 Aberystywth University
 * All rights reserved
 */

package uk.ac.aber.cs221.group09.graphics;

import javafx.scene.image.Image;

import java.util.HashMap;

/**
 * PlayScreenGraphicsLoader - Initializes the graphics for play screen.
 * <p>
 * Stores the graphics in a HashMap and returns the images when requested.
 *
 * @author Gwion Hughes
 * @version 1.0 (Release)
 */
public class PlayScreenGraphicsLoader {
   private HashMap<Character, Image> graphicsMap;

   /**
    * Simple constructor.
    */
   public PlayScreenGraphicsLoader() {
      setGraphics();
   }

   /**
    * Retrieves the graphics from the specified file and stores it in a HashMap.
    */
   private void setGraphics() {
      graphicsMap = new HashMap<>();

      graphicsMap.put('W', new Image("/images/playscenepieces/WhiteKing.png"));
      graphicsMap.put('B', new Image("/images/playscenepieces/BlackKing.png"));
      graphicsMap.put('D', new Image("/images/playscenepieces/DrawKing.png"));
      graphicsMap.put('q', new Image("/images/playscenepieces/WhiteQueen.png"));
      graphicsMap.put('r', new Image("/images/playscenepieces/BlackRook.png"));
      graphicsMap.put('b', new Image("/images/playscenepieces/WhiteBishop.png"));
      graphicsMap.put('k', new Image("/images/playscenepieces/BlackKnight.png"));
   }

   /**
    * Returns the image for the specified character.
    */
   public Image getImage(char c) {
      return graphicsMap.get(c);
   }
}