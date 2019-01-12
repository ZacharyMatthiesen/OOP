package com.cstaley.JavaOO.CodeWorlds.V2.release;

import java.awt.Image;

// Anything that can be displayed must have a location and a current velocity.
// It's also expected to have a toString suitable for display
public interface Displayable {
   public Vector getLoc();
   public Vector getVlc();
   public Image getImage(int size);  // Return image of size x size dimension
}
