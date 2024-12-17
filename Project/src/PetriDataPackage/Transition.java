package PetriDataPackage;

import java.io.Serializable;
import java.util.ArrayList;

public class Transition implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String Name;
	public int Delay;
	public ArrayList<String> InputPlaces;
	public ArrayList<Guard> Guards;

	public Transition() {
		InputPlaces = new ArrayList<>();
		Guards = new ArrayList<>();
	}
}
