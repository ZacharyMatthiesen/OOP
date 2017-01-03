package com.cstaley.JavaOO.CodeWorlds.V2.release;

public interface Body extends Iterable<Brick> {
   Rectangle getBounds();
   Body clone(Vector offset);  // Make a copy, with locations offset by |offset|
}
