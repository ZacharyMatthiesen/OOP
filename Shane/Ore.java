package com.cstaley.JavaOO.CodeWorlds.V2.release;

import java.awt.Color;
import java.awt.Image;

public class Ore extends Brick {
   private static int imgSize;
   private static Image img;

   private Vector loc;

   public Ore(Vector loc) {this.loc = loc;}

   @Override
   public Vector getLoc()  {return loc;}
   
   @Override
   public String toString() {return "Stone";}
   
   @Override
   public Image getImage(int size) {
      if (size != imgSize)
         img = makeStippleImage(size, new Color(168, 80, 80),
          new Color(100, 50, 50), 11, 7);

      return img;      
   }
}