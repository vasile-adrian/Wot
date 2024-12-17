package MetricsClasses;

import java.util.ArrayList;

public class Metrics {

	public Float StructuralComplexity;
	public Float InputArcs;
	public Float OutputArcs;
	public Float LevelNumber;
	public ArrayList<PlaceTransition> Pieces;
	
	public Metrics()
	{
		Pieces = new ArrayList<PlaceTransition>();
	}
	
	public String toString() {
		return String.format("StructuralComplexity [%s]  InputArcs [%s] OutputArcs [%s] LevelNumber [%s]",
				StructuralComplexity, InputArcs, OutputArcs,LevelNumber);
	}
}
