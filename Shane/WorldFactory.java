package com.cstaley.JavaOO.CodeWorlds.V2.release;

public interface WorldFactory {
   public Body getWorld();               // Retrieve the World w/o rebuilding
   public WorldFactory build() throws CWSException;     // Build the World
}
