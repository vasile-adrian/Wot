package Components;

import java.io.Serializable;
import java.util.ArrayList;

import DataObjects.DataCar;
import DataOnly.CarQueue;
import DataOnly.FloatFloat;
import DataOnly.RELQueue;
import DataOnly.SubPetri;
import Enumerations.LogicConnector;
import Enumerations.PetriNetState;
import Enumerations.PetriObjectType;
import Enumerations.TransitionCondition;
import Interfaces.PetriObject;
import Utilities.Functions;

public class Condition implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PetriTransition Parent;

	public PetriObject Value1;
	public PetriObject Value2;

	public String PlaceName1;
	public String PlaceName2;

	public TransitionCondition condition;

	public Condition NextCondition;
	public LogicConnector Connector = LogicConnector.AND;
	public Functions util;

	public Condition() {
		util = new Functions();
	}

	public Condition(PetriTransition Parent, String PlaceName1, TransitionCondition condition, String PlaceName2) {
		util = new Functions();
		this.Parent = Parent;
		this.PlaceName1 = PlaceName1;
		this.PlaceName2 = PlaceName2;
		this.condition = condition;
	}

	public Condition(PetriTransition Parent, String PlaceName1, TransitionCondition condition) {
		util = new Functions();
		this.Parent = Parent;
		this.PlaceName1 = PlaceName1;
		this.condition = condition;
	}

	public void refreshData() {
		this.Value1 = util.GetPetriObjectByName(PlaceName1, Parent.Parent.PlaceList);
		if (Value1 == null) {
			Integer indx = util.GetIndexByName(PlaceName1, Parent.Parent.ConstantPlaceList);
			if (indx > -1)
				this.Value1 = util.GetPetriObjectByName(PlaceName1, Parent.Parent.ConstantPlaceList);
		}
		this.Value2 = util.GetPetriObjectByName(PlaceName2, Parent.Parent.PlaceList);
		if (Value2 == null) {
			Integer indx = util.GetIndexByName(PlaceName2, Parent.Parent.ConstantPlaceList);
			if (indx > -1)
				this.Value2 = util.GetPetriObjectByName(PlaceName2, Parent.Parent.ConstantPlaceList);
		}
	}

	public void SetNextCondition(LogicConnector Connector, Condition NextCondition) {
		this.NextCondition = NextCondition;
		this.Connector = Connector;
	}



	boolean Check() {
		refreshData();
		switch (condition) {
		case NotNull:
			if (Value1 != null && Value1.GetValue() != null)
				return true;
			break;
		case IsNull:
			if (Value1 == null || Value1.GetValue() == null)
				return true;
			break;
		case NotEqual:
			if (Value1 == null || Value2 == null)
				return false;
			if (Value1.GetValue() == null || Value2.GetValue() == null)
				return false;
			if (!Value1.GetValue().equals(Value2.GetValue()))
				return true;
			break;
		case Equal:
			if (Value1 == null || Value2 == null)
				return false;
			if (Value1.GetValue() == null || Value2.GetValue() == null)
				return false;
			if (Value1.GetValue().equals(Value2.GetValue()))
				return true;
			break;
		case MoreThanOrEqual: {
			if (Value1 == null || Value2 == null)
				return false;
			if (Value1.GetValue() == null || Value2.GetValue() == null)
				return false;
			switch (Value1.GetType()) {
			case DataInteger:
				if ((Integer) Value1.GetValue() >= (Integer) Value2.GetValue())
					return true;
				break;
			case DataFloat:
				if ((Float) Value1.GetValue() >= (Float) Value2.GetValue())
					return true;
				break;
			default:
				break;
			}
			break;
		}
		case LessThanOrEqual: {
			if (Value1 == null || Value2 == null)
				return false;
			if (Value1.GetValue() == null || Value2.GetValue() == null)
				return false;
			switch (Value1.GetType()) {
			case DataInteger:
				if ((Integer) Value1.GetValue() <= (Integer) Value2.GetValue())
					return true;
				break;
			case DataFloat:
				if ((Float) Value1.GetValue() <= (Float) Value2.GetValue())
					return true;
				break;
			default:
				break;
			}
			break;
		}
		case MoreThan: {
			if (Value1 == null || Value2 == null)
				return false;
			if (Value1.GetValue() == null || Value2.GetValue() == null)
				return false;
			switch (Value1.GetType()) {
			case DataInteger:
				if ((Integer) Value1.GetValue() > (Integer) Value2.GetValue())
					return true;
				break;
			case DataFloat:
				if ((Float) Value1.GetValue() > (Float) Value2.GetValue())
					return true;
				break;
			default:
				break;
			}
			break;
		}
		case LessThan: {
			if (Value1 == null || Value2 == null)
				return false;
			if (Value1.GetValue() == null || Value2.GetValue() == null)
				return false;
			switch (Value1.GetType()) {
			case DataInteger:
				if ((Integer) Value1.GetValue() < (Integer) Value2.GetValue())
					return true;
				break;
			case DataFloat:
				if ((Float) Value1.GetValue() < (Float) Value2.GetValue())
					return true;
				break;
			default:
				break;
			}
			break;
		}
		case Contains: {
			if (Value1 == null || Value2 == null)
				return false;
			if (Value1.GetValue() == null || Value2.GetValue() == null)
				return false;
			if (Value1.GetType() == PetriObjectType.DataString) {
				if (((String) Value1.GetValue()).contains((String) Value2.GetValue()))
					return true;
			}
			break;
		}
		case NotContains: {
			if (Value1 == null || Value2 == null)
				return false;
			if (Value1.GetValue() == null || Value2.GetValue() == null)
				return false;
			if (Value1.GetType() == PetriObjectType.DataString) {
				if (!((String) Value1.GetValue()).contains((String) Value2.GetValue()))
					return true;
			}
			break;
		}
		case HaveCarForMe: {
			if (Value1 == null)
				return false;
			if (Value1.GetValue() == null)
				return false;
			if (Value1.GetType() == PetriObjectType.DataCarQueue) {
				if (util.HaveCarForMe(Parent, ((CarQueue) Value1.GetValue()).Cars)) {
					return true;
				}
			}
			break;
		}
		case CanAddCars: {
			if (Value1 == null)
				return false;
			if (Value1.GetValue() == null)
				return false;
			if (Value1.GetType() == PetriObjectType.DataCarQueue) {
				if (((CarQueue) Value1.GetValue()).CanAddCar()) {
					return true;
				}
			}
			break;
		}
		case CanNotAddCars: {
			if (Value1 == null)
				return false;
			if (Value1.GetValue() == null)
				return false;
			if (Value1.GetType() == PetriObjectType.DataCarQueue) {
				if (((CarQueue) Value1.GetValue()).CanNotAddCar()) {
					return true;
				}
			}
			break;
		}
		case HaveREL: {
			if (Value1 == null)
				return false;
			if (Value1.GetValue() == null)
				return false;
			if (Value1.GetType() == PetriObjectType.DataRELQueue) {
				if (util.HaveREL(((RELQueue) Value1.GetValue()).RELs)) {
					return true;
				}
			}
			break;
		}
		case HaveCar: {
			if (Value1 == null)
				return false;
			if (Value1.GetValue() == null)
				return false;
			if (Value1.GetType() == PetriObjectType.DataCarQueue) {
				if (util.HaveCar(((CarQueue) Value1.GetValue()).Cars)) {
					return true;
				}
			}
			break;
		}
		case SubPetriStopped: {
			if (Value1 == null)
				return false;
			if (Value1.GetValue() == null)
				return false;
			if (Value1.GetType() == PetriObjectType.DataSubPetri) {
				if (((SubPetri) Value1.GetValue()).Petri.PetriState == PetriNetState.Stopped) {
					return true;
				}
			}
			break;
		}

		// ---------------New Conditions for FloatFloat---------------------
		case Equal_FloatFloat: {
			if (Value1 == null || Value2 == null)
				return false;
			if (Value1.GetValue() == null || Value2.GetValue() == null)
				return false;
			if (((FloatFloat) Value1.GetValue()).V1 == ((FloatFloat) Value2.GetValue()).V1
					&& ((FloatFloat) Value1.GetValue()).V2 == ((FloatFloat) Value2.GetValue()).V2)
				return true;
			break;
		}
		case MoreThan_FloatFloat: {
			if (Value1 == null || Value2 == null)
				return false;
			if (Value1.GetValue() == null || Value2.GetValue() == null)
				return false;
			if (((FloatFloat) Value1.GetValue()).V1 > ((FloatFloat) Value2.GetValue()).V1
					&& ((FloatFloat) Value1.GetValue()).V2 > ((FloatFloat) Value2.GetValue()).V2)
				return true;
			break;
		}
		case MoreThanOrEqual_FloatFloat: {
			if (Value1 == null || Value2 == null)
				return false;
			if (Value1.GetValue() == null || Value2.GetValue() == null)
				return false;
			if (((FloatFloat) Value1.GetValue()).V1 >= ((FloatFloat) Value2.GetValue()).V1
					&& ((FloatFloat) Value1.GetValue()).V2 >= ((FloatFloat) Value2.GetValue()).V2)
				return true;
			break;
		}
		case LessThan_FloatFloat: {
			if (Value1 == null || Value2 == null)
				return false;
			if (Value1.GetValue() == null || Value2.GetValue() == null)
				return false;
			if (((FloatFloat) Value1.GetValue()).V1 < ((FloatFloat) Value2.GetValue()).V1
					&& ((FloatFloat) Value1.GetValue()).V2 < ((FloatFloat) Value2.GetValue()).V2)
				return true;
			break;
		}
		case LessThanOrEqual_FloatFloat: {
			if (Value1 == null || Value2 == null)
				return false;
			if (Value1.GetValue() == null || Value2.GetValue() == null)
				return false;
			if (((FloatFloat) Value1.GetValue()).V1 <= ((FloatFloat) Value2.GetValue()).V1
					&& ((FloatFloat) Value1.GetValue()).V2 <= ((FloatFloat) Value2.GetValue()).V2)
				return true;
			break;
		}

		case HavePriorityCar:  //added
			if (Value1 == null)
				return false;
			if (Value1.GetValue() == null)
				return false;
			if (Value1.GetType() == PetriObjectType.DataCarQueue) {
				if (util.HavePriority(Parent, ((CarQueue) Value1.GetValue()).Cars)) {
					return true;
				}
			}
			break;

			// ---------------------- Priority Cases ----------------------
		case isPriorityCar:
			if (Value1 == null)
				return false;
			if (Value1.GetValue() == null)
				return false;
			if (Value1.GetType() == PetriObjectType.DataCar) {
				if (util.isPriority(Parent, ((DataCar) Value1))) {
					return true;
				}
			}
			break;

			// ---------------------- Bus Cases ----------------------
		case HaveBus:  // added
			if (Value1 == null)
				return false;
			if (Value1.GetValue() == null)
				return false;
			if (Value1.GetType() == PetriObjectType.DataCarQueue) {
				if (util.HaveBus(Parent, ((CarQueue) Value1.GetValue()).Cars)) {
					return true;
				}
			}
			break;

		case isBus:
			if (Value1 == null)
				return false;
			if (Value1.GetValue() == null)
				return false;
			if (Value1.GetType() == PetriObjectType.DataCar) {
				if (util.isBus(Parent, ((DataCar) Value1))) {
					return true;
				}
			}
			break;

			// ---------------------- Taxi Cases ----------------------
		case HaveTaxi:  // added
			if (Value1 == null)
				return false;
			if (Value1.GetValue() == null)
				return false;
			if (Value1.GetType() == PetriObjectType.DataCarQueue) {
				if (util.HaveTaxi(Parent, ((CarQueue) Value1.GetValue()).Cars)) {
					return true;
				}
			}
			break;

		case isTaxi:
			if (Value1 == null)
				return false;
			if (Value1.GetValue() == null)
				return false;
			if (Value1.GetType() == PetriObjectType.DataCar) {
				if (util.isTaxi(Parent, ((DataCar) Value1))) {
					return true;
				}
			}
			break;
		// ---------------------End of Modification-------------------------------

		default:
			break;
		}
		return false;
	}

	public ArrayList<Condition> conditionList;

	public boolean CheckCondition() {
		refreshData();
		conditionList = new ArrayList<Condition>();
		FullList(this);

		boolean andCondition = true;
		for (Condition condition : conditionList) {
			if (condition.Connector == LogicConnector.AND) {
				if (!condition.Check()) {
					andCondition = false;
					break;
				}
			}
		}

		if (andCondition)
			return true;

		for (Condition condition : conditionList) {
			if (condition.Connector == LogicConnector.OR) {
				if (condition.Check()) {
					return true;
				}
			}
		}

		return false;
	}

	void FullList(Condition obj) {
		if (obj == null)
			return;
		conditionList.add(obj);
		FullList(obj.NextCondition);
	}
}
