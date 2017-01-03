package com.cstaley.JavaOO.CodeWorlds.V2.release;

import java.io.InputStream;
import java.util.Map;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Scanner;

public class InputStreamWorldFactory implements WorldFactory {

	Map<String, Body> modelTable = new HashMap<String, Body>();
	Scanner in;
	LinkedList<Body> cloneList = new LinkedList<Body>();
	
	public InputStreamWorldFactory(InputStream e) {
		in = new Scanner(e);
		modelTable.put("Cow", new Cow(new Vector(0, 0)));
		modelTable.put("Horse", new Horse(new Vector(0, 0)));
		modelTable.put("Sloth", new Sloth(new Vector(0, 0)));
		modelTable.put("Ore", new Ore(new Vector(0, 0)));
		modelTable.put("Stone", new Stone(new Vector(0, 0)));
		modelTable.put("Tree", new Tree(new Vector(0, 0)));
		modelTable.put("Water", new Water(new Vector(0, 0)));
	}

	@Override
	public Body getWorld() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public WorldFactory build() throws CWSException {
		while (in.hasNextLine()) {
			String[] line = in.nextLine().split("\\s");
			// Add a new cloned object to the cloneList by getting the model object from the list and specifying the offset from the next two 
			// String elements.
			cloneList.add(modelTable.get(line[0]).clone(new Vector(Integer.parseInt(line[1]),Integer.parseInt(line[2]))));
		}
		return null;
	}
}
