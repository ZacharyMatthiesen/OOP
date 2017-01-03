package com.cstaley.JavaOO.CodeWorlds.V2.release;

import java.util.Iterator;
import java.util.List;
import java.util.LinkedList;

public class CompositeBody implements Body {
	
	public List<Body> children = new LinkedList<Body>(); // will need in the future? -- when CompositeBody has multiple levels of hierarchy?
	//private Body child;
	
	public CompositeBody(CompositeBody childBody) {
		children.add(this);
		// iterate through compositeBody children, adding to children list
	}
	
	public CompositeBody(List<Body> childList) {
		children.addAll(childList); // will addAll add to an empty list?
	}
	
	@Override
	public Iterator<Brick> iterator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle(child.getBounds());
	}

	@Override
	public Body clone(Vector offset) {
		return child.clone(offset);
	}
	
	/* public void addChild(Body childBody) {
		child.add(childBody);
	} */

}
