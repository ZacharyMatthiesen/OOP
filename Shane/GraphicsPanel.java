package com.cstaley.JavaOO.CodeWorlds.V2.release;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

public class GraphicsPanel extends JPanel implements Display {
   static final int kMaxDim = 1024;
   static final Color kBackground = new Color(200, 200, 255);
   
   private Displayable[][] dsps; // Grid of displayables
   private int imgX;              // Image X/Y of current upper left corner
   private int imgY;
   private int imgWidth;         // Image dimensions
   private int imgHeight;
   private int pixPerCell;        // Pixels per cell (>= 1, must be power of 2)
   private int minPxPerCell;     // Lowest reasonable value for pxPerCell
   BufferedImage img;            // Current zoomed image

   // Create a GraphicsDisplay for a world of the indicated dimensions.
   public GraphicsPanel(int xDim, int yDim) throws CWSException {
      int minCellSize; // Size of cell that just fits in kMaxDim, w/ no zoom

      dsps = new Brick[yDim][xDim];
      minCellSize = (int)Math.floor(kMaxDim / Math.max(xDim, yDim));
      
      if (minCellSize < 1)
         throw new CWSException(
          String.format("Display dimension is over %d", kMaxDim));

      // Nearest power of two <= minCellSize
      for (pixPerCell = 1; minCellSize > 1; minCellSize >>= 1)
         pixPerCell *= 2;
      minPxPerCell = pixPerCell;
      
      imgWidth = xDim * pixPerCell;
      imgHeight = yDim * pixPerCell;
      img = new BufferedImage(imgWidth, imgHeight, BufferedImage.TYPE_INT_ARGB);
      buildImage();

      addMouseListener(new MouseAdapter() {
         @Override
         public void mousePressed(MouseEvent evt) {
            if (!evt.isControlDown())
               recenter(evt.getX(), evt.getY());
            else
               zoom(evt.getButton() == MouseEvent.BUTTON1 ? 1 : -1);
         }
      });
   }
   
   @Override
   public Dimension getMinimumSize() {return getPreferredSize();}
   
   @Override
   public Dimension getPreferredSize() {
      return new Dimension(imgWidth, imgHeight);
   }

   public void zoom(int steps) {
      int oldPPC = pixPerCell;
      
      pixPerCell = steps > 0 ? pixPerCell << steps : pixPerCell >> -steps;
      pixPerCell = Math.max(minPxPerCell, pixPerCell);

      // Recenter on new global image coordinates of the centerpoint.
      makeCenter((imgX + imgWidth/2) * pixPerCell/oldPPC,
       (imgY + imgHeight/2) * pixPerCell/oldPPC);

      buildImage();
      repaint();
   }

   // Make the specified GLOBAL IMAGE coordinates the centerpoint
   public void makeCenter(int x, int y) {
      int xLimit = dsps[0].length*pixPerCell - imgWidth;
      int yLimit = dsps.length*pixPerCell - imgHeight;
      imgX = Math.max(0, Math.min(xLimit, x - imgWidth/2));
      imgY = Math.max(0, Math.min(yLimit, y - imgHeight/2));
   }
   
   // Recenter on the specified DISPLAYED WINDOW coordinates
   public void recenter(int x, int y) {
      makeCenter(imgX + x, imgY + y);
      buildImage();
      repaint();
   }

   public void buildImage() {
      Graphics2D grp;
      int row, col;
      
      grp = img.createGraphics();
      grp.setColor(kBackground);
      grp.fillRect(0, 0, imgWidth, imgHeight);
      
      for (row = imgY/pixPerCell; row <= (imgY+imgHeight - 1)/pixPerCell; row++)
         for (col = imgX/pixPerCell; col <= (imgX+imgWidth - 1)/pixPerCell; col++)
            if (dsps[row][col] != null)
               grp.drawImage(dsps[row][col].getImage(pixPerCell), 
                col*pixPerCell - imgX, row*pixPerCell - imgY, null);
      grp.dispose();
   }

   public boolean inRange(Vector loc) {
      return loc.getX() >= 0 && loc.getX() < dsps[0].length && loc.getY() >= 0
       && loc.getY() < dsps.length;
   }
   
   // Does |loc|'s cell overlap the currently visible image?
   public boolean inImage(Vector loc) {
      return loc.getX() > imgX - pixPerCell && loc.getX() < imgX + imgWidth
      && loc.getY() > imgY - pixPerCell && loc.getY() < imgY + imgHeight;
   }
   
   public void update(Displayable dsp, Object lc) {
      Vector oldLoc, newLoc = dsp.getLoc(), oldPxLoc, newPxLoc;
      Graphics2D imgGrp = img.createGraphics();
      
      if (lc != null) {
         oldLoc = (Vector)lc; 
         oldPxLoc = oldLoc.scale(pixPerCell);
         if (inRange(oldLoc)) {
            dsps[oldLoc.getY()][oldLoc.getX()] = null;
            if (inImage(oldPxLoc)) {
               imgGrp.setColor(kBackground);
               imgGrp.fillRect(oldPxLoc.getX() - imgX, oldPxLoc.getY() - imgY,
                pixPerCell, pixPerCell);
            }
         }
      }
      
      if (newLoc != null) {
         newPxLoc = newLoc.scale(pixPerCell);
         if (inRange(newLoc)) {
            dsps[newLoc.getY()][newLoc.getX()] = dsp;
            if (inImage(newPxLoc)) {
               imgGrp.drawImage(dsp.getImage(pixPerCell), newPxLoc.getX() - imgX,
                newPxLoc.getY() - imgY, null);
            }
         }
      }
      
      imgGrp.dispose();
      repaint();
   }

   @Override
   public void addDisplayable(Displayable dsp) {
      dsps[dsp.getLoc().getY()][dsp.getLoc().getX()] = dsp;
      update(dsp, null);
   }

   @Override
   public void redraw(int time) {repaint();}
   
   @Override
   public void paintComponent(Graphics g) {
      super.paintComponent(g);
      g.drawImage(img, 0, 0, null);
   }
}