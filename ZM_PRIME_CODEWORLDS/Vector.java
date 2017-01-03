package com.cstaley.JavaOO.CodeWorlds.V2.release;

public class Vector {
   public final static double kEpsilon = .000001;
   public final static Vector kZero = new Vector();
   
   protected int x;
   protected int y;

   public Vector() {}
   
   public Vector(int x, int y) {
      this.x = x;
      this.y = y;
   }
   
   public final int getX() {return this.x;}
   public final int getY() {return y;}

   protected static int sqr(int val) {return val*val;}
   
   public Vector plus(Vector other) {
      return new Vector(x + other.x, y + other.y);  
   }

   public Vector minus(Vector other) {
      return new Vector(x - other.x, y - other.y);  
   }

   public Vector scale(double scale) {
      return new Vector((int)Math.round(this.x * scale),
       (int)Math.round(this.y * scale));
   }
   
   public Vector turn90(boolean left) {
      return left ? new Vector(-y, x) : new Vector(y, -x);
   }
   
   public Vector scaleBy(double scale) {
      this.x *= scale;
      this.y *= scale;
      
      return this;
   }

   public Vector plusBy(Vector other) {
      x += other.x;
      y += other.y;
      
      return this;
   }
   
   public Vector minusBy(Vector other) {
      x -= other.x;
      y -= other.y;
      
      return this;
   }
   
   public Vector turn90By(boolean left) {
      x = left ? -y : y;
      y = left ? x : -x;
      
      return this;
   }

   public double length() {return Math.sqrt(sqr(x) + sqr(y));}

   @Override
   public boolean equals(Object other) {
      Vector reallyAVector = (Vector) other;
      
      return this.x == reallyAVector.x && this.y == reallyAVector.y;
   }
   
   @Override
   public String toString() {
      return String.format("[%d, %d]", x, y);
   }
}