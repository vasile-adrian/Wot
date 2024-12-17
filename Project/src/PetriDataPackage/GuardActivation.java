package PetriDataPackage;

import java.io.Serializable;
import java.util.ArrayList;

import Enumerations.TransitionOperation;

public class GuardActivation implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String InputPlaceName;
	public ArrayList<String> InputPlaceNames;
	public String OutputPlaceName;
	public ArrayList<String> OutputPlaceNames;
	public TransitionOperation Operation;

	public GuardActivation() {
	}
	
	public GuardActivation(String InputPlaceName, TransitionOperation Condition, String OutputPlaceName) {
		this.InputPlaceName = InputPlaceName;
		this.OutputPlaceName = OutputPlaceName;
		this.Operation = Condition;
	}

	public GuardActivation(ArrayList<String> InputPlaceNames, TransitionOperation Condition,
			String OutputPlaceName) {
		this.InputPlaceNames = InputPlaceNames;
		this.OutputPlaceName = OutputPlaceName;
		this.Operation = Condition;
	}

	public GuardActivation(String InputPlaceName, TransitionOperation Condition,
			ArrayList<String> OutputPlaceNames) {
		this.InputPlaceName = InputPlaceName;
		this.OutputPlaceNames = OutputPlaceNames;
		this.Operation = Condition;
	}
}
