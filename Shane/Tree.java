package com.cstaley.JavaOO.CodeWorlds.V2.release;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

public class Tree extends Brick {
   private static int imgSize;
   private static BufferedImage img;

   private Vector loc;

   public Tree(Vector loc) {this.loc = loc;}

   @Override
   public Vector getLoc()  {return loc;}
   
   @Override
   public String toString() {return "Tree";}
   
   @Override
   public Image getImage(int size) {
      Graphics2D grp;
      Color ltBrn = new Color(150, 120, 75), dkBrn = new Color(100, 80, 50);

      if (size != imgSize) {
         img = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
         grp = img.createGraphics();
         // Draw trunk as dark rectangle with lighter stripes
         grp.setColor(dkBrn);
         grp.fillRect(3*size/8, size/2, size/4, size/2);
         grp.setColor(ltBrn);
         for (int x = 3*size/8 + 2; x < 5*size/8; x += 4)
            grp.fillRect(x, size/2, 2, size/2);
         
         // Draw green circles
         grp.setColor(Color.GREEN.darker().darker());
         grp.fillOval(0, size/4, size/2, size/2);
         grp.setColor(Color.GREEN.darker());
         grp.fillOval(size/2, size/4, size/2, size/2);
         grp.setColor(Color.GREEN);
         grp.fillOval(size/4, 0, size/2, size/2);
   
         grp.dispose();
      }
      
      return img;      
   }
}