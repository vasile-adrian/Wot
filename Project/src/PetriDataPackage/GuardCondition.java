package PetriDataPackage;

import java.io.Serializable;

import Enumerations.LogicConnector;
import Enumerations.TransitionCondition;
import Interfaces.PetriObject;

public class GuardCondition implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public PetriObject Value1;
	public PetriObject Value2;

	public String PlaceName1;
	public String PlaceName2;

	public TransitionCondition condition;

	public GuardCondition NextCondition;
	public LogicConnector Connector = LogicConnector.AND;

	public GuardCondition() {

	}
	
	public GuardCondition(String PlaceName1, TransitionCondition condition, String PlaceName2) {
		this.PlaceName1 = PlaceName1;
		this.PlaceName2 = PlaceName2;
		this.condition = condition;
	}

	public GuardCondition(String PlaceName1, TransitionCondition condition) {
		this.PlaceName1 = PlaceName1;
		this.condition = condition;
	}

	public void SetNextCondition(LogicConnector Connector, GuardCondition NextCondition) {
		this.NextCondition = NextCondition;
		this.Connector = Connector;
	}
}
