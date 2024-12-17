package DataOnly;

import java.io.Serializable;
import java.util.ArrayList;

import DataObjects.DataREL;

public class RELQueue implements Cloneable, Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Overriding clone() method of Object class
		public RELQueue clone() throws CloneNotSupportedException {
			return (RELQueue) super.clone();
		}

		public ArrayList<DataREL> RELs = new ArrayList<DataREL>();
		public Integer Size = 5;

		public RELQueue() {

		}

		public boolean AddREL(DataREL rel) {
			if (RELs.size() < Size) {
				RELs.add(rel);
				return true;
			} else {
				for (int i = 0; i < RELs.size(); i++) {
					if (RELs.get(i) == null) {
						RELs.set(i, rel);
						return true;
					}
				}
			}
			return false;
		}

		public boolean CanAddREL() {
			if (RELs.size() < Size) {
				return true;
			} else {
				for (int i = 0; i < RELs.size(); i++) {
					if (RELs.get(i) == null) {
						return true;
					}
				}
			}
			return false;
		}

		public DataREL PopREL() {
			Integer index = -1;
			for (int i = 0; i < RELs.size(); i++) {
				if (RELs.get(i) != null && RELs.get(i).Value != null) {
					index = i;
					break;
				}
			}

			if (index == -1)
				return null;
			if (RELs.get(index) != null) {
				try {
					DataREL temp = (DataREL) RELs.get(index).clone();
					RELs.set(index, null);
					return temp;

				} catch (CloneNotSupportedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			return null;
		}
		
		public DataREL GetFirstREL() {
			Integer index = -1;
			for (int i = 0; i < RELs.size(); i++) {
				if (RELs.get(i) != null && RELs.get(i).Value != null) {
					index = i;
					break;
				}
			}

			if (index == -1)
				return null;
			if (RELs.get(index) != null) {
				try {
					DataREL temp = (DataREL) RELs.get(index).clone();
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
			for (DataREL rel : RELs) {
				if (rel == null)
					temp1.add("NULL");
				else
					temp1.add(rel.toString());
			}

			return "(" + String.join(",", temp1) + ")";
		}
}
