package DataOnly;

import java.io.Serializable;

public class FloatFloat implements Cloneable, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public FloatFloat clone() throws CloneNotSupportedException {
		return (FloatFloat) super.clone();
	}

	public float V1;
	public float V2;

	public FloatFloat(float V1, float V2) {
		this.V1 = V1;
		this.V2 = V2;
	}

	public String toString() {
		return "V1=" + V1 + " V2=" + V2;
	}
}
