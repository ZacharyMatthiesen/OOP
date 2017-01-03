package com.cstaley.JavaOO.CodeWorlds.V2.release;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.Iterator;

public class Cow extends HerdAnimal {
   private static int imgSize;
   private static BufferedImage img;
   
   public Cow(Vector loc) {super(loc, new Vector());}

   @Override
   public String getLabel() {return "Cow";}  // C for Cow
   
   @Override
   boolean isGoodLeader(HerdAnimal ldr) {
      return ldr instanceof Cow && ldr.loc.equals(loc);
   }
   
   @Override
   public Image getImage(int size) {
      Graphics2D grp;
      Color bodyClr = new Color(210, 210, 210), hornClr = Color.DARK_GRAY,
       hoofClr = new Color(80, 60, 40), spotClr = Color.BLACK;
      
      if (size != imgSize) {
         img = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
         grp = img.createGraphics();
         
         // Body and head
         grp.setColor(bodyClr);         
         grp.fillRoundRect(3*size/10, 3*size/10, 6*size/10, 4*size/10, size/10, 
          size/10);
         grp.fillRoundRect(size/10,  2*size/10,  3*size/10,  2*size/10,
          size/10, size/10);
         
         // Legs
         grp.fillPolygon(
          new int[] {size*4/10, size*5/10, size*4/10, size*5/20},
          new int[] {size/2, size/2, size*17/20, size*17/20}, 4);
         grp.fillPolygon(
          new int[] {size*7/10, size*8/10, size*9/10, size*15/20},
          new int[] {size/2, size/2, size*17/20, size*17/20}, 4);
         
         // Hooves
         grp.setColor(hoofClr);
         grp.fillPolygon(
          new int[] {size*5/20, size*4/10, size*9/20, size*2/10},
          new int[] {size*17/20, size*17/20, size*19/20, size*19/20}, 4);
         grp.fillPolygon(
          new int[] {size*15/20, size*9/10, size*19/20, size*7/10},
          new int[] {size*17/20, size*17/20, size*19/20, size*19/20}, 4);

         // Spots and eye
         grp.setColor(spotClr);
         grp.fillOval(4*size/10,  4*size/10,  size/10,  size/10);
         grp.fillOval(6*size/10,  5*size/10,  size/5,  size/8);
         grp.fillOval(3*size/10, 5*size/20, size/40, size/40);
        
         // Horn
         grp.setColor(hornClr);
         grp.fillPolygon(
          new int[] {3*size/10, 7*size/20, 5*size/20},
          new int[] {2*size/10, 2*size/10, size/10}, 3);
         
         Color greenish = new Color(230, 255, 230);
         grp.fillOval(10,  20, 5, 5);
         
         grp.dispose();
      }
      
      return img;      
   }

@Override
public Rectangle getBounds() {
	// TODO Auto-generated method stub
	return null;
}

@Override
public Body clone(Vector offset) {
	// TODO Auto-generated method stub
	return null;
}

@Override
public Iterator<Brick> iterator() {
	// TODO Auto-generated method stub
	return null;
}
}
