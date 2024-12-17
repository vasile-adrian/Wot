package DataOnly;

import java.io.Serializable;

import Enumerations.FZ;

public class FV implements Cloneable, Serializable {
	public FZ Value1;
	public FZ Value2;

	public FV(FZ... fuzzyZones) {
		if (fuzzyZones.length >= 1) {
			Value1 = fuzzyZones[0];
		}
		if (fuzzyZones.length >= 2) {
			Value2 = fuzzyZones[1];
		}
	}

	public String ToString() {
		StringBuilder toPrint = new StringBuilder();

		if (Value1 != null)
			toPrint.append(Value1.toString());
		if (Value2 != null)
			toPrint.append("," + Value2.toString());

		return toPrint.toString();
	}
}
