package Components;

import java.io.Serializable;
import java.util.ArrayList;

public class GuardMapping  implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public Condition condition;
	public ArrayList<Activation> Activations;
	public GuardMapping() {
		Activations= new ArrayList<Activation>();
	}
	public void Activate() throws CloneNotSupportedException
	{
		for (Activation activation : Activations) {
			activation.Activate();
		}
	}
}
