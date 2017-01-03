package com.cstaley.JavaOO.CodeWorlds.V2.release;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.Iterator;

// Fundamental unit of display.  A Brick is one cell in the display.
public abstract class Brick implements Body, Displayable {

	private int x, y, width, height;

	/*public Brick(int x, int y, int width, int height){
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}*/

	// Create an image of color |bg|, with |fg| dots at random location, with
	// one |fg| dot out of every |skip| dots.
	static public Image makeStippleImage(int size, Color bg, Color fg,
			int skip, int subSkip) {
		Graphics2D grp;
		int dotSize = Math.max(1, size/16);
		int cols = size/dotSize, numDots = cols * size/dotSize;
		BufferedImage img;

		img = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
		grp = img.createGraphics();

		grp.setColor(bg);
		grp.fillRect(0, 0, size, size);
		grp.setColor(fg);
		for (int dot = 0; dot < numDots; dot ++)
			if (dot % skip == 0 || dot % subSkip == 0)
				grp.fillRect(dot % cols * dotSize, dot/cols * dotSize, dotSize,
						dotSize);

		grp.dispose();

		return img;      
	}

	@Override
	public Iterator<Brick> iterator() {return null;}

	@Override
	public Rectangle getBounds() {
		Vector vcr = this.getLoc();
		return new Rectangle(vcr.getX(), vcr.getY(), 1, 1);
	}

	@Override
	public Body clone(Vector offset) {
		if (this instanceof Cow)
			return new Cow(this.getLoc().plus(offset));
		else if (this instanceof Horse) {
			return new Horse(this.getLoc().plus(offset));
		}
		else if (this instanceof Sloth)
			return new Sloth(this.getLoc().plus(offset));
		else if (this instanceof Ore)
			return new Ore(this.getLoc().plus(offset));
		else if (this instanceof Stone)
			return new Stone(this.getLoc().plus(offset));
		else if (this instanceof Tree)
			return new Tree(this.getLoc().plus(offset));
		else if (this instanceof Water)
			return new Water(this.getLoc().plus(offset));
		else 
			return null; //throw new CWSException("Can only clone Bricks!");
	}
	@Override
	abstract public Vector getLoc();
	@Override
	public Vector getVlc() {return new Vector();} // this.something?
}
