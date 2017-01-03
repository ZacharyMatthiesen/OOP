package com.cstaley.JavaOO.CodeWorlds.V2.release;

/* An Animal moves in a straight line within |range|, at a constant velocity.
 * If this would take it out of range, it instead stays in place and turns 90
 * degrees to the right.
 */

public abstract class Animal extends Brick {
   protected static Rectangle range;  // All Animals have a common range.
   public static void setRange(Rectangle val) {range = val;}
   
   protected Vector vlc;      // Velocity (per time unit)
   protected Vector loc;      // Location (will be a MarginVector in fact)
   
   public Animal(Vector loc, Vector vlc) {
      this.loc = loc;
      this.vlc = vlc;
   }

   @Override
   public Vector getLoc()  {return loc;}
   public void setLoc(Vector loc) {this.loc = loc;}
   
   @Override
   public Vector getVlc()         {return vlc;}
   public void setVlc(Vector vlc) {this.vlc = vlc;}

   // Take a step resulting from the passage of one time unit
   public void step() {
      Vector newLoc = new Vector(loc.getX(), loc.getY()).plusBy(vlc);
      
      if (range == null || range.inRect(newLoc))
         loc = newLoc;
      else
         vlc = vlc.turn90(false);  // Hang a right if out of bounds
   }

   protected String getLabel() {return "?";}
   
   @Override
   public String toString() {return getLabel();}
}