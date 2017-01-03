package com.cstaley.JavaOO.CodeWorlds.V2.release;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

public class Sloth extends Animal {
   private static int imgSize;
   private static BufferedImage img;

   public Sloth(Vector loc) {super(loc, new Vector(0,0));}

   @Override
   public String getLabel() {return "Sloth";}
   
   @Override
   public void step() {
      step();
      
      vlc.scaleBy(.5);  // Sloths just move more and more slowly
   }
   
   @Override
   public Image getImage(int size) {
      Graphics2D grp;
      
      if (size != imgSize) {
         img = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
         grp = img.createGraphics();

         grp.setColor(Color.DARK_GRAY);
         grp.fillOval(3*size/10, 3*size/10, size/5, size/5);
         
         grp.dispose();
      }
      
      return img;
   }
}