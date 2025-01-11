package DataOnly;

import java.io.Serializable;
import java.util.ArrayList;

import DataObjects.DataCar;
import DataObjects.DataString;

public class CarQueue implements Cloneable, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Overriding clone() method of Object class
	public CarQueue clone() throws CloneNotSupportedException {
		return (CarQueue) super.clone();
	}

	public ArrayList<DataCar> Cars = new ArrayList<DataCar>();
	public Integer Size = 5;

	public CarQueue() {

	}

	public boolean AddCar(DataCar car) {
		if (Cars.size() < Size) {
			Cars.add(car);
			return true;
		} else {
			for (int i = 0; i < Cars.size(); i++) {
				if (Cars.get(i) == null) {
					Cars.set(i, car);
					return true;
				}
			}
		}
		return false;
	}

	public boolean CanAddCar() {
		if (Cars.size() < Size) {
			return true;
		} else {
			for (int i = 0; i < Cars.size(); i++) {
				if (Cars.get(i) == null) {
					return true;
				}
			}
		}
		return false;
	}
	
	public boolean CanNotAddCar() {
		if (Cars.size() >= Size) {
			return true;
		} else {
			for (int i = 0; i < Cars.size(); i++) {
				if (Cars.get(i) == null) {
					return false;
				}
			}
		}
		return false;
	}

	public DataCar PopCar(String target) {
		Integer index = -1;
		for (int i = 0; i < Cars.size(); i++) {
			if (Cars.get(i) != null && Cars.get(i).Value != null)
				if (Cars.get(i).Value.Targets.contains(target)) {
					index = i;
					break;
				}
		}

		if (index == -1)
			return null;
		if (Cars.get(index) != null) {
			try {
				DataCar temp = (DataCar) Cars.get(index).clone();
				Cars.set(index, null);
				return temp;

			} catch (CloneNotSupportedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}

	public DataCar PopCartWithoutTarget() {
		Integer index = -1;
		for (int i = 0; i < Cars.size(); i++) {
			if (Cars.get(i) != null && Cars.get(i).Value != null) {
				index = i;
				break;
			}
		}

		if (index == -1)
			return null;
		if (Cars.get(index) != null) {
			try {
				DataCar temp = (DataCar) Cars.get(index).clone();
				Cars.set(index, null);
				return temp;

			} catch (CloneNotSupportedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}

	public String toString() {
		ArrayList<String> temp1 = new ArrayList<String>();
		for (DataCar car : Cars) {
			if (car == null)
				temp1.add("NULL");
			else
				temp1.add(car.toString());
		}

		return "(" + String.join(",", temp1) + ")";
	}

	public DataCar PopTaxi(DataString req) {
		String[] usrq;
		usrq = req.Value.split(",");
		ArrayList usrqArray = new ArrayList<String>();
		for (int i = 0; i < usrq.length; i++) {
			usrqArray.add(usrq[i]);
		}
		Integer index = -1;
		for (int i = 0; i < Cars.size(); i++) {
			if (Cars.get(i) != null && Cars.get(i).Value != null) {
				index = i;
				break;
			}
		}

		if(index == -1)
			return null;

		Cars.get(index).Value.Targets = usrqArray;

		if (Cars.get(index) != null) {
			try {
				DataCar temp = (DataCar) Cars.get(index).clone();
				Cars.set(index, null);
				return temp;
			} catch (CloneNotSupportedException e) {
				e.printStackTrace();
			}
		}
		return null;
	}



};
