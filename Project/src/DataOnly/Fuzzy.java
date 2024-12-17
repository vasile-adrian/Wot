package DataOnly;

import java.io.Serializable;

public class Fuzzy implements Cloneable, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Fuzzy clone() throws CloneNotSupportedException {
		return (Fuzzy) super.clone();
	}

	public FuzzyVector Vector;
	public Float Value;

	public Fuzzy(FuzzyVector vector, float value) {
		this.Vector = vector;
		this.Value = value;
		GenerateFuzzyToken(this.Value);
	}

	public Fuzzy(float value) {
		this.Vector = new FuzzyVector(0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
		this.Value = value;
		GenerateFuzzyToken(this.Value);
	}

	public String toString() {
		return "(" + Value.toString() + ": " + Vector.toString() + ")";
	}

	public void GenerateFuzzyToken(Float v) {
		Vector.GenerateFuzzyToken(v);
		Value = v;
	}
}