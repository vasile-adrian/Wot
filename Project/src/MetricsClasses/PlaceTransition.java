package MetricsClasses;

public class PlaceTransition {
	public String InPlaceName;
	public String TransitionName;
	public String OutPlaceName;
	
	public String toString() {
		return String.format("[%s:%s:%s]",
				InPlaceName, TransitionName, OutPlaceName);
	}
}
