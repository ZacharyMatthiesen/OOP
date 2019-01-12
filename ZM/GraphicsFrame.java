package com.cstaley.JavaOO.CodeWorlds.V2.release;

import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.Iterator;

import javax.swing.JFrame;
import javax.swing.WindowConstants;
import javax.swing.border.BevelBorder;

public class GraphicsFrame extends JFrame {
   GraphicsPanel pnl;
   public GraphicsFrame(int xDim, int yDim) throws CWSException {
      super("GridWorlds V2");
      Container cp = getContentPane();

      pnl = new GraphicsPanel(xDim, yDim);
      pnl.setBorder(new BevelBorder(BevelBorder.LOWERED));
      cp.setLayout(new FlowLayout());
      cp.add(pnl);
      setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
      pack();
      setVisible(true);
   }
   
   public GraphicsPanel getPnl() {
      return pnl;
   }

   private static class SampleDsp extends Brick {
      private static int imgSize;
      private static BufferedImage img;
      
      public SampleDsp(Vector loc, Vector vlc) {this.loc = loc; this.vlc = vlc;}

      private Vector loc;
      private Vector vlc;
      
      @Override
      public Vector getLoc() {return loc;}

      @Override
      public Vector getVlc() {return null;}

      @Override
      public Image getImage(int size) {
         Graphics2D grp;
         
         if (imgSize != size) {
            img = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
            imgSize = size;
            grp = img.createGraphics();
            
            // Draw green cross
            grp.setColor(Color.GREEN.darker());
            grp.fillRect(3*size/8, 0, size/4, size);
            grp.fillRect(0, 3*size/8, size, size/4);
            
            grp.dispose();  // Don't forget to do this!
         }
         return img;
      }

      @Override
      public Body clone(Vector offset) {return null;}

	@Override
	public Rectangle getBounds() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterator<Brick> iterator() {
		// TODO Auto-generated method stub
		return null;
	}
   }
   
   public static void main(String[] args) throws CWSException {
      GraphicsFrame frame = new GraphicsFrame(100, 100);
      SampleDsp dsps[] = new SampleDsp[10];
      int i;

      frame.getPnl().addDisplayable(new Tree(new Vector(5, 8)));
      frame.getPnl().addDisplayable(new Ore(new Vector(6, 8)));
      frame.getPnl().addDisplayable(new Cow(new Vector(7, 8)));
      frame.getPnl().addDisplayable(new Sloth(new Vector(8, 8)));

      for (i = 0; i < 10; i++) {
         dsps[i] = new SampleDsp(new Vector(5 + i*10, 5 + i*10 + i%2),
          new Vector(i%2*2 - 1, 1 - i%2*2));
         frame.getPnl().addDisplayable(dsps[i]);
      }
   }   
}
