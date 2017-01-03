package com.cstaley.JavaOO.CodeWorlds.V2.release;

public class Rectangle {
   protected int left; // x-coord. of left corner
   protected int top; // y-coord. of left corner
   protected int width;
   protected int height;
   
   public Rectangle(Rectangle r) {this (r.left, r.top, r.width, r.height);}
   
   public Rectangle(int left, int top, int width, int height) {
      this.left = left;
      this.top = top;
      this.width = width;
      this.height = height;
   }

   public Rectangle(Vector upLeft, int width, int height) {
      this(upLeft.getX(), upLeft.getY(), width, height);
   }

   public Vector getTopLeft() {return new Vector(left, top);}

   public int getLeft() {return left;}
   public int getTop() {return top;}
   public int getWidth() {return width;}
   public int getHeight() {return height;}
   public int getRight() {return left + width;}
   public int getBottom() {return top + height;}
   
   // Return a new Rectangle that encloses both this and |other|
   public Rectangle union(Rectangle other) {
      return new Rectangle(this).unionBy(other);
   }
   
   public Rectangle expand(int margin) {
      return new Rectangle(this).expandBy(margin);
   }
   
   // Expand this Rectangle so that it includes |r|.
   public Rectangle unionBy(Rectangle r) {
      int right = Math.max(r.left + r.width, left + width);
      int bottom = Math.max(r.top + r.height, top + height);
      
      left = Math.min(left, r.left);
      top = Math.min(top, r.top);
      width = right - left;
      height = bottom - top;
      
      return this;
   }
   
   // Increase this Rectangle's size, on all boundaries, by |margin|
   public Rectangle expandBy(int margin) {
      left -= margin;
      top -= margin;
      width += 2*margin;
      height += 2*margin;
      
      return this;
   }
   
   // Is |loc| within this rectangle?
   public boolean inRect(Vector loc) {
      return left <= loc.getX() && loc.getX() <= left + width
       && top <= loc.getY() && loc.getY() <= top + height;
   }

   @Override
   public String toString() {
      return String.format("%d %d %d %d", left, top, width, height);
   }
   
   public static void main(String[] args) {
      // TODO Auto-generated method stub

   }

}
