package DataOnly;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

import Enumerations.FZ;

public class FLRS implements Cloneable, Serializable {
	public ArrayList<FLRSPart> Parts;

	public FLRS(FV... flrsValues) {
		Parts = new ArrayList<FLRSPart>();
		if (flrsValues.length == 5) {
			int index = 0;
			for (FZ zone : FZ.values()) {
				if (zone != FZ.FF) {
					Parts.add(new FLRSPart(zone, flrsValues[index]));
					++index;
				}
			}
		}

		if (flrsValues.length == 25) {
			int index = 0;
			for (FZ zone1 : FZ.values()) {
				if (zone1 != FZ.FF)
					for (FZ zone2 : FZ.values()) {
						if (zone2 != FZ.FF) {
							Parts.add(new FLRSPart(zone1, zone2, flrsValues[index]));
							++index;
						}
					}
			}
		}
	}

	public void Print() {
		StringBuilder header = new StringBuilder();
		StringBuilder line = new StringBuilder();

		if (Parts.size() == 5) {
			header.append("(X1)");
			line.append("___");
			int index = 0;
			for (FZ zone : FZ.values()) {
				if (zone != FZ.FF) {
					header.append("[" + zone + "]");
					line.append("[" + Parts.get(index++).ToString() + "]");
				}
			}
			System.out.println(header.toString());
			System.out.println(line.toString());
		}

		if (Parts.size() == 25) {
			header.append("(X1/X2)");
			boolean headerPrinted = false;
			int index = 0;
			for (FZ zone1 : FZ.values()) {
				if (zone1 != FZ.FF) {
					line.append(zone1 + "::");
					for (FZ zone2 : FZ.values()) {
						if (zone2 != FZ.FF) {
							header.append("[" + zone2 + "]");
							line.append("[" + Parts.get(index++).ToString() + "]");
						}
					}
					if (!headerPrinted) {
						System.out.println(header.toString());
						headerPrinted = true;
					}
					System.out.println(line.toString());
					line = new StringBuilder();
				}
			}
		}

	}
}
