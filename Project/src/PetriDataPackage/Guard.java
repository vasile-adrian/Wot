package PetriDataPackage;

import java.io.Serializable;
import java.util.ArrayList;

public class Guard implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public GuardCondition GuardCondition;
	public ArrayList<GuardActivation> GuardActivations;

	public Guard() {
		GuardActivations = new ArrayList<>();
	}
}