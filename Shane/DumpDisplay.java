package com.cstaley.JavaOO.CodeWorlds.V2.release;

import java.util.LinkedList;
import java.util.List;

/* Display keeps track of a list of Displayables, and produces, on each
 * |update| call, a textual 2-D display of their location, showing the toString
 * value of each Displayable at its current location, with X location ascending
 * across columns, and Y location *down* rows. (Computations are easier that
 * way, and an upside-down display looks as good.)  Each row and column 
 * represent a step in location-space of some size, not necessarily 1.0.  This
 * allows larger location-space spans to be shown in a limited number of rows
 * and columns, at the expense of some loss of resolution.
 * 
 * If any Displayable falls outside the allowed index range, simply drop it
 * from the display.
 */
public class DumpDisplay implements Display {
   private List<Displayable> dsps = new LinkedList<Displayable>();
  
   @Override
   public void addDisplayable(Displayable dsp) {dsps.add(dsp);}
   
   @Override
   public void redraw(int time) {
      System.out.printf("\nAt time %d:\n", time);
      for (Displayable dsp: dsps)
         System.out.println(dsp.getLoc());
   }
}