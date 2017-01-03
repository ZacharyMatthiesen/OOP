package com.cstaley.JavaOO.CodeWorlds.V2.release;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.Iterator;

public class Horse extends HerdAnimal {
   private static int imgSize;
   private static BufferedImage img;

   public Horse(Vector loc) {super(loc, new Vector());}

   @Override
   public String getLabel() {return "Horse";}
   
   @Override
   boolean isGoodLeader(HerdAnimal ldr) {
      return ldr instanceof Horse && ldr.loc.equals(loc)
       && ldr.vlc.length() > vlc.length(); 
   }

   @Override
   public Image getImage(int size) {
      Graphics2D grp;
      Color dkBrn = new Color(80, 60, 40);
      
      if (size != imgSize) {
         img = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
         grp = img.createGraphics();

         grp.setColor(dkBrn);
         grp.fillOval(3*size/10, 3*size/10, size/5, size/5);
         
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
