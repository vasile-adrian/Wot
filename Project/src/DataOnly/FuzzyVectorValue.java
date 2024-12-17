package DataOnly;

import java.io.Serializable;

import Enumerations.FZ;

public class FuzzyVectorValue implements Serializable {

	public Float Value;
	public FZ Zone;

	public FuzzyVectorValue(FZ zone, Float value) {
		this.Value = value;
		this.Zone = zone;
	}
	
	public String toString() {
		return Zone.toString()+":"+ Value.toString();
	}
}
