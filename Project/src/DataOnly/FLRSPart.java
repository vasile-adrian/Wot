package DataOnly;

import Enumerations.FZ;

public class FLRSPart {
	public FZ Input1;
	public FZ Input2;
	public FV Value;

	public FLRSPart(FZ input1, FZ input2, FV value) {
		Input1 = input1;
		Input2 = input2;
		Value = value;
	}

	public FLRSPart(FZ input1, FV value) {
		Input1 = input1;
		Value = value;
	}
	
	public String ToString()
	{
		return Value.ToString();
	}
}
