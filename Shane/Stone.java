package com.cstaley.JavaOO.CodeWorlds.V2.release;

import java.awt.Color;
import java.awt.Image;

public class Stone extends Brick {
   private static int imgSize;
   private static Image img;

   private Vector loc;

   public Stone(Vector loc) {this.loc = loc;}

   @Override
   public Vector getLoc()  {return loc;}
   
   @Override
   public String toString() {return "Stone";}
   
   @Override
   public Image getImage(int size) {
      if (size != imgSize)
         img = makeStippleImage(size, new Color(128, 110, 90),
          new Color(64, 55, 45), 13, 11);

      return img;
   }
}