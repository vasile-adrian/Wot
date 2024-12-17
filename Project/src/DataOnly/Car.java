package DataOnly;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Car implements Cloneable, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Overriding clone() method of Object class
	public Car clone() throws CloneNotSupportedException {
		return (Car) super.clone();
	}
	public String Model;
	public String Number;
	public List<String> Targets;
	public Boolean isPriority = false;
	public Boolean isBus = false;
	public Boolean isTaxi = false;

	public Car(String Model, String Number, ArrayList<String> Targets) {
		this.Model = Model;
		this.Number = Number;
		this.Targets = new ArrayList<String>();
		this.Targets.addAll(Targets);
	}
	
	public Car(String Model, String Number, String[] Targets) {
		this.Model = Model;
		this.Number = Number;
		this.Targets = new ArrayList<String>();
		for (String string : Targets) {
			this.Targets.add(string);
		}
	}

	public void setType(String type){
		switch (type) {
			case "Bus": this.isBus = true; break;
			case "Taxi": this.isTaxi = true; break;
			case "Priority": this.isPriority = true; break;
			default: break;
		}
	}

	public String toString() {
		return Model + "-" + Number;
	}
};

