package Components;

import java.io.Serializable;
import java.util.ArrayList;

import DataObjects.DataBoolean;
import DataObjects.DataCar;
import DataObjects.DataCarQueue;
import DataOnly.CarQueue;
import DataOnly.FLRS;
import DataOnly.FLRSPart;
import DataOnly.FloatFloat;
import DataOnly.Fuzzy;
import DataOnly.FuzzyVectorValue;
import DataOnly.PlaceNameWithWeight;
import DataOnly.RELQueue;
import DataObjects.DataFloat;
import DataObjects.DataFloatFloat;
import DataObjects.DataFuzzy;
import DataObjects.DataInteger;
import DataObjects.DataNetworkCommand;
import DataObjects.DataREL;
import DataObjects.DataRELQueue;
import DataObjects.DataString;
import DataObjects.DataTransfer;
import DataObjects.DataSubPetriNet;
import Enumerations.FZ;
import Enumerations.TransitionOperation;
import Interfaces.PetriObject;
import Utilities.Functions;

public class Activation implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PetriTransition Parent;

	public String InputPlaceName;
	public ArrayList<String> InputPlaceNames;
	public ArrayList<PlaceNameWithWeight> InputPlaceNamesWithWeight;
	public String OutputPlaceName;
	public ArrayList<String> OutputPlaceNames;
	public TransitionOperation Operation;
	public Functions util;
	public FLRS Flrs;

	public Activation(PetriTransition Parent) {
		util = new Functions();
	}

	public Activation(PetriTransition Parent, String InputPlaceName, TransitionOperation Condition,
			String OutputPlaceName) {
		util = new Functions();
		this.Parent = Parent;
		this.InputPlaceName = InputPlaceName;
		this.OutputPlaceName = OutputPlaceName;
		this.Operation = Condition;
	}

	public Activation(PetriTransition Parent, ArrayList<String> InputPlaceNames, TransitionOperation Condition,
			String OutputPlaceName) {
		util = new Functions();
		this.Parent = Parent;
		this.InputPlaceNames = InputPlaceNames;
		this.OutputPlaceName = OutputPlaceName;
		this.Operation = Condition;
	}

	public Activation(PetriTransition Parent, FLRS flrs, ArrayList<PlaceNameWithWeight> InputPlaceNamesWithWeight,
			TransitionOperation Condition, ArrayList<String> OutputPlaceNames) {
		util = new Functions();
		this.Parent = Parent;
		this.InputPlaceNamesWithWeight = InputPlaceNamesWithWeight;
		this.OutputPlaceNames = OutputPlaceNames;
		this.Operation = Condition;
		this.Flrs = flrs;
	}

	public Activation(ArrayList<PlaceNameWithWeight> InputPlaceNamesWithWeight, TransitionOperation Condition,
			String OutputPlaceName, PetriTransition Parent) {
		util = new Functions();
		this.Parent = Parent;
		this.InputPlaceNamesWithWeight = InputPlaceNamesWithWeight;
		this.OutputPlaceName = OutputPlaceName;
		this.Operation = Condition;
	}

	public Activation(PetriTransition Parent, String InputPlaceName, TransitionOperation Condition,
			ArrayList<String> OutputPlaceNames) {
		util = new Functions();
		this.Parent = Parent;
		this.InputPlaceName = InputPlaceName;
		this.OutputPlaceNames = OutputPlaceNames;
		this.Operation = Condition;
	}

	public void Activate() throws CloneNotSupportedException {

		if (Operation == TransitionOperation.Move)
			Move();

		if (Operation == TransitionOperation.Copy)
			Copy();

		if (Operation == TransitionOperation.Add)
			Add();

		if (Operation == TransitionOperation.Prod)
			Prod();

		if (Operation == TransitionOperation.Sub)
			Sub();

		if (Operation == TransitionOperation.Div)
			Div();

		if (Operation == TransitionOperation.AddElement)
			AddElement();

		if (Operation == TransitionOperation.PopElementWithTarget)
			PopElementWithTarget();

		if (Operation == TransitionOperation.PopElement_R_E)
			PopElement_R_E();

		if (Operation == TransitionOperation.DynamicDelay)
			DynamicDelay();

		if (Operation == TransitionOperation.PopElementWithTargetToQueue)
			PopElementWithTargetToQueue();

		if (Operation == TransitionOperation.PopElementWithoutTarget)
			PopElementWithoutTarget();

		if (Operation == TransitionOperation.PopElementWithoutTargetToQueue)
			PopElementWithoutTargetToQueue();

		if (Operation == TransitionOperation.SendOverNetwork)
			SendOverNetwork();

		if (Operation == TransitionOperation.SendROverNetwork)
			SendROverNetwork();

		if (Operation == TransitionOperation.SendPetriNetOverNetwork)
			SendPetriNetOverNetwork();

		if (Operation == TransitionOperation.ActivateSubPetri)
			ActivateSubPetri();

		if (Operation == TransitionOperation.StopPetriNet)
			Parent.Parent.Stop();

		if (Operation == TransitionOperation.MakeNull)
			MakeNull();
		// -------------FloatFloat Modifications--------------------
		if (Operation == TransitionOperation.Add_FloatFlaot)
			Add_FloatFlaot();
		if (Operation == TransitionOperation.Sub_FloatFloat)
			Sub_FloatFlaot();
		if (Operation == TransitionOperation.Prod_FloatFloat)
			Prod_FloatFloat();
		if (Operation == TransitionOperation.Div_FloatFloat)
			Div_FloatFlaot();
		// ---------------------------------------------------------

		// -------------FUZZY--------------------
		if (Operation == TransitionOperation.Add_Fuzzy)
			Add_Fuzzy();
		if (Operation == TransitionOperation.Sub_Fuzzy)
			Sub_Fuzzy();
		if (Operation == TransitionOperation.Prod_Fuzzy)
			Prod_Fuzzy();
		if (Operation == TransitionOperation.Div_Fuzzy)
			Div_Fuzzy();
		// ---------------------------------------------------------
		if (Operation == TransitionOperation.FLRS)
			FLRS_Fuzzy();
		// Different Vehicles
		if (Operation == TransitionOperation.PopElementWithTarget){
			PopTaxiToQueue();
		}
	}

	private void PopTaxiToQueue() throws CloneNotSupportedException {
		Integer outputIndex = util.GetIndexByName(OutputPlaceName, Parent.Parent.PlaceList);
		Integer inputQIndex = util.GetIndexByName(InputPlaceNames.get(0), Parent.Parent.PlaceList);
		Integer inputTIndex = util.GetIndexByName(InputPlaceNames.get(1), Parent.TempMarking);

		PetriObject tempT = Parent.TempMarking.get(inputTIndex);
		PetriObject resultT = null;
		if (tempT instanceof DataString) {
			PetriObject temp = ((CarQueue) ((DataCarQueue) Parent.Parent.PlaceList.get(inputQIndex)).GetValue())
					.PopTaxi(((DataString) tempT));

			PetriObject result = null;

			if (temp == null)
				return;
			if (temp instanceof DataString) {
				result = (PetriObject) ((DataCar)temp).clone();
			}
			result.SetName(OutputPlaceName);
			result.SetValue(temp.GetValue());

			DataCarQueue out = (DataCarQueue) (Parent.Parent.PlaceList.get(outputIndex));
			out.AddElement(result);
		}
	}


	private void MakeNull() throws CloneNotSupportedException {
		PetriObject temp = util.GetFromListByName(OutputPlaceName, Parent.Parent.PlaceList);
		if (temp == null) {
			util.SetNullToListByName(OutputPlaceName, Parent.Parent.ConstantPlaceList);
		} else {
			util.SetNullToListByName(OutputPlaceName, Parent.Parent.PlaceList);
		}
	}

	private void Move() throws CloneNotSupportedException {

		PetriObject temp = util.GetFromListByName(InputPlaceName, Parent.TempMarking);
		if (temp == null) {
			temp = util.GetFromListByName(InputPlaceName, Parent.Parent.ConstantPlaceList);
		}
		PetriObject result = null;

		if (temp instanceof DataFloat) {
			result = (PetriObject) ((DataFloat) temp).clone();
		}

		if (temp instanceof DataInteger) {
			result = (PetriObject) ((DataInteger) temp).clone();
		}
		
		if (temp instanceof DataBoolean) {
			result.SetValue((PetriObject) ((DataBoolean) temp).clone());
		}

		if (temp instanceof DataString) {
			result = (PetriObject) ((DataString) temp).clone();
		}

		if (temp instanceof DataCar) {
			result = (PetriObject) ((DataCar) temp).clone();
		}

		if (temp instanceof DataSubPetriNet) {
			result = (PetriObject) ((DataSubPetriNet) temp).clone();
		}

		// --------------------DataFloatFloat modification--------------
		if (temp instanceof DataFloatFloat) {
			result = (PetriObject) ((DataFloatFloat) temp).clone();
		}
		// -------------------------------------------------------------
		if (temp instanceof DataFuzzy) {
			result = (PetriObject) ((DataFuzzy) temp).clone();
		}

		if (result == null) {
			return;
		}

		if (OutputPlaceName.contains("-")) {
			result.SetName(OutputPlaceName.split("-")[1]);
		} else {
			result.SetName(OutputPlaceName);
		}

		result.SetValue(temp.GetValue());

		util.SetToListByName(OutputPlaceName, Parent.Parent.PlaceList, result);
	}

	private void Copy() throws CloneNotSupportedException {

		PetriObject temp = util.GetFromListByName(InputPlaceName, Parent.TempMarking);
		if (temp == null) {
			temp = util.GetFromListByName(InputPlaceName, Parent.Parent.ConstantPlaceList);
		}
		PetriObject result = null;
		PetriObject resultBack = null;

		if (temp instanceof DataFloat) {
			result = (PetriObject) ((DataFloat) temp).clone();
			resultBack = (PetriObject) ((DataFloat) temp).clone();
		}

		if (temp instanceof DataInteger) {
			result = (PetriObject) ((DataInteger) temp).clone();
			resultBack = (PetriObject) ((DataInteger) temp).clone();
		}

		if (temp instanceof DataString) {
			result = (PetriObject) ((DataString) temp).clone();
			resultBack = (PetriObject) ((DataString) temp).clone();
		}

		if (temp instanceof DataCar) {
			result = (PetriObject) ((DataCar) temp).clone();
			resultBack = (PetriObject) ((DataCar) temp).clone();
		}

		if (temp instanceof DataSubPetriNet) {
			result = (PetriObject) ((DataSubPetriNet) temp).clone();
			resultBack = (PetriObject) ((DataSubPetriNet) temp).clone();
		}

		if (result == null) {
			return;
		}

		if (OutputPlaceName.contains("-")) {
			result.SetName(OutputPlaceName.split("-")[1]);
		} else {
			result.SetName(OutputPlaceName);
		}

		result.SetValue(temp.GetValue());

		util.SetToListByName(OutputPlaceName, Parent.Parent.PlaceList, result);

		if (InputPlaceName.contains("-")) {

		} else {
			util.SetToListByName(InputPlaceName, Parent.Parent.PlaceList, resultBack);
		}
	}

	private void Add() throws CloneNotSupportedException {
		Integer outputIndex = util.GetIndexByName(OutputPlaceName, Parent.Parent.PlaceList);
		PetriObject result = null;

		for (String placeName : InputPlaceNames) {
			PetriObject temp;
			Integer inputIndex = util.GetIndexByName(placeName, Parent.TempMarking);
			if (inputIndex == -1) {
				temp = util.GetFromListByName(placeName, Parent.Parent.ConstantPlaceList);
			} else {
				temp = Parent.TempMarking.get(inputIndex);
			}

			if (temp == null) {
				continue;
			}

			if (temp instanceof DataFloat) {
				if (result == null) {
					result = (PetriObject) ((DataFloat) temp).clone();
				} else {
					result.SetValue((float) result.GetValue() + (float) temp.GetValue());
				}
			}

			if (temp instanceof DataInteger) {
				if (result == null) {
					result = (PetriObject) ((DataInteger) temp).clone();
				} else {
					result.SetValue((Integer) result.GetValue() + (Integer) temp.GetValue());
				}
			}
		}
		result.SetName(OutputPlaceName);
		Parent.Parent.PlaceList.set(outputIndex, result);
	}

	private void Prod() throws CloneNotSupportedException {
		Integer outputIndex = util.GetIndexByName(OutputPlaceName, Parent.Parent.PlaceList);
		PetriObject result = null;

		for (String placeName : InputPlaceNames) {
			PetriObject temp;
			Integer inputIndex = util.GetIndexByName(placeName, Parent.TempMarking);
			if (inputIndex == -1) {
				temp = util.GetFromListByName(placeName, Parent.Parent.ConstantPlaceList);
			} else {
				temp = Parent.TempMarking.get(inputIndex);
			}

			if (temp == null) {
				continue;
			}

//			Integer inputIndex = util.GetIndexByName(placeName, Parent.TempMarking);
//			if (inputIndex == -1)
//				continue;
//
//			PetriObject temp = Parent.TempMarking.get(inputIndex);

			if (temp instanceof DataFloat) {
				if (result == null) {
					result = (PetriObject) ((DataFloat) temp).clone();
				} else {
					result.SetValue((float) result.GetValue() * (float) temp.GetValue());
				}
			}

			if (temp instanceof DataInteger) {
				if (result == null) {
					result = (PetriObject) ((DataInteger) temp).clone();
				} else {
					result.SetValue((Integer) result.GetValue() * (Integer) temp.GetValue());
				}
			}
		}
		result.SetName(OutputPlaceName);
		Parent.Parent.PlaceList.set(outputIndex, result);
	}

	private void Sub() throws CloneNotSupportedException {
		Integer outputIndex = util.GetIndexByName(OutputPlaceName, Parent.Parent.PlaceList);
		PetriObject result = null;

		for (String placeName : InputPlaceNames) {
			PetriObject temp;
			Integer inputIndex = util.GetIndexByName(placeName, Parent.TempMarking);
			if (inputIndex == -1) {
				temp = util.GetFromListByName(placeName, Parent.Parent.ConstantPlaceList);
			} else {
				temp = Parent.TempMarking.get(inputIndex);
			}

			if (temp == null) {
				continue;
			}

			if (temp instanceof DataFloat) {
				if (result == null) {
					result = (PetriObject) ((DataFloat) temp).clone();
				} else {
					result.SetValue((float) result.GetValue() - (float) temp.GetValue());
				}
			}

			if (temp instanceof DataInteger) {
				if (result == null) {
					result = (PetriObject) ((DataInteger) temp).clone();
				} else {
					result.SetValue((Integer) result.GetValue() - (Integer) temp.GetValue());
				}
			}
		}
		result.SetName(OutputPlaceName);
		Parent.Parent.PlaceList.set(outputIndex, result);
	}

	private void Div() throws CloneNotSupportedException {
		Integer outputIndex = util.GetIndexByName(OutputPlaceName, Parent.Parent.PlaceList);
		PetriObject result = null;

		for (String placeName : InputPlaceNames) {
			PetriObject temp;
			Integer inputIndex = util.GetIndexByName(placeName, Parent.TempMarking);
			if (inputIndex == -1) {
				temp = util.GetFromListByName(placeName, Parent.Parent.ConstantPlaceList);
			} else {
				temp = Parent.TempMarking.get(inputIndex);
			}

			if (temp == null) {
				continue;
			}

//			Integer inputIndex = util.GetIndexByName(placeName, Parent.TempMarking);
//			if (inputIndex == -1)
//				continue;
//
//			PetriObject temp = Parent.TempMarking.get(inputIndex);

			if (temp instanceof DataFloat) {
				if (result == null) {
					result = (PetriObject) ((DataFloat) temp).clone();
				}
				float current = (float) result.GetValue();
				if (current == 0) {
					result.SetValue((float) temp.GetValue());
				} else {
					result.SetValue((float) result.GetValue() / (float) temp.GetValue());
				}
			}

			if (temp instanceof DataInteger) {
				if (result == null) {
					result = (PetriObject) ((DataInteger) temp).clone();
				}
				Integer current = (Integer) result.GetValue();
				if (current == 0) {
					result.SetValue((Integer) temp.GetValue());
				} else {
					result.SetValue((Integer) result.GetValue() / (Integer) temp.GetValue());
				}
			}
		}
		result.SetName(OutputPlaceName);
		Parent.Parent.PlaceList.set(outputIndex, result);
	}

	private void AddElement() throws CloneNotSupportedException {
		Integer outputIndex = util.GetIndexByName(OutputPlaceName, Parent.Parent.PlaceList);
		Integer inputIndex = util.GetIndexByName(InputPlaceName, Parent.TempMarking);

		PetriObject temp = Parent.TempMarking.get(inputIndex);

		PetriObject result = null;

		if (temp instanceof DataCar) {
			result = (PetriObject) ((DataCar) temp).clone();
		}

		if (temp instanceof DataREL) {
			result = (PetriObject) ((DataREL) temp).clone();
		}

		result.SetName(OutputPlaceName);
		result.SetValue(temp.GetValue());

		Parent.Parent.PlaceList.get(outputIndex).AddElement(result);
	}

	private void PopElementWithTarget() throws CloneNotSupportedException {
		Integer outputIndex = util.GetIndexByName(OutputPlaceName, Parent.Parent.PlaceList);
		Integer inputIndex = util.GetIndexByName(InputPlaceName, Parent.Parent.PlaceList);
		PetriObject temp = ((CarQueue) ((DataCarQueue) Parent.Parent.PlaceList.get(inputIndex)).GetValue())
				.PopCar(Parent.TransitionName);

		PetriObject result = null;

		if (temp instanceof DataCar) {
			result = (PetriObject) ((DataCar) temp).clone();
		}

		result.SetName(OutputPlaceName);
		result.SetValue(temp.GetValue());

		Parent.Parent.PlaceList.set(outputIndex, result);
	}

	private void PopElement_R_E() throws CloneNotSupportedException {

		Integer outputIndexR = util.GetIndexByName(OutputPlaceNames.get(0), Parent.Parent.PlaceList);
		Integer outputIndexE = util.GetIndexByName(OutputPlaceNames.get(1), Parent.Parent.PlaceList);
		Integer inputIndex = util.GetIndexByName(InputPlaceName, Parent.Parent.PlaceList);

		DataREL temp = ((RELQueue) ((DataRELQueue) Parent.Parent.PlaceList.get(inputIndex)).GetValue()).PopREL();

		PetriObject resultR = util.GetPetriObjectByName(OutputPlaceNames.get(0), Parent.Parent.PlaceList);
		PetriObject resultE = util.GetPetriObjectByName(OutputPlaceNames.get(1), Parent.Parent.PlaceList);

		if (temp != null) {
			resultR.SetValue(temp.Value.R);
			resultE.SetValue(temp.Value.E);

			Parent.Delay = temp.Value.E;
			Parent.Parent.PlaceList.set(outputIndexR, resultR);
			Parent.Parent.PlaceList.set(outputIndexE, resultE);
		}
	}

	private void PopElementWithTargetToQueue() throws CloneNotSupportedException {

		Integer outputIndex = util.GetIndexByName(OutputPlaceName, Parent.Parent.PlaceList);
		Integer inputIndex = util.GetIndexByName(InputPlaceName, Parent.Parent.PlaceList);
		PetriObject temp = ((CarQueue) ((DataCarQueue) Parent.Parent.PlaceList.get(inputIndex)).GetValue())
				.PopCar(Parent.TransitionName);

		PetriObject result = null;

		if (temp instanceof DataCar) {
			result = (PetriObject) ((DataCar) temp).clone();
		}

		result.SetName(OutputPlaceName);
		result.SetValue(temp.GetValue());

		DataCarQueue out = (DataCarQueue) (Parent.Parent.PlaceList.get(outputIndex));
		out.AddElement(result);
	}

	private void PopElementWithoutTarget() throws CloneNotSupportedException {

		Integer outputIndex = util.GetIndexByName(OutputPlaceName, Parent.Parent.PlaceList);
		Integer inputIndex = util.GetIndexByName(InputPlaceName, Parent.Parent.PlaceList);
		PetriObject temp = ((CarQueue) ((DataCarQueue) Parent.Parent.PlaceList.get(inputIndex)).GetValue())
				.PopCartWithoutTarget();

		PetriObject result = null;

		if (temp == null)
			return;

		if (temp instanceof DataCar) {
			result = (PetriObject) ((DataCar) temp).clone();
		}

		result.SetName(OutputPlaceName);
		result.SetValue(temp.GetValue());

		Parent.Parent.PlaceList.set(outputIndex, result);
	}

	private void PopElementWithoutTargetToQueue() throws CloneNotSupportedException {
		Integer outputIndex = util.GetIndexByName(OutputPlaceName, Parent.Parent.PlaceList);
		Integer inputIndex = util.GetIndexByName(InputPlaceName, Parent.Parent.PlaceList);
		PetriObject temp = ((CarQueue) ((DataCarQueue) Parent.Parent.PlaceList.get(inputIndex)).GetValue())
				.PopCartWithoutTarget();

		PetriObject result = null;

		if (temp == null)
			return;

		if (temp instanceof DataCar) {
			result = (PetriObject) ((DataCar) temp).clone();
		}

		result.SetName(OutputPlaceName);
		result.SetValue(temp.GetValue());

		DataCarQueue out = (DataCarQueue) (Parent.Parent.PlaceList.get(outputIndex));
		out.AddElement(result);
	}

	private void SendOverNetwork() throws CloneNotSupportedException {
		PetriObject output = util.GetPetriObjectByName(OutputPlaceName, Parent.Parent.PlaceList);

		PetriObject temp;
		Integer inputIndex = util.GetIndexByName(InputPlaceName, Parent.TempMarking);
		if (inputIndex == -1) {
			temp = util.GetFromListByName(InputPlaceName, Parent.Parent.ConstantPlaceList);
		} else {
			temp = Parent.TempMarking.get(inputIndex);
		}

		if (temp == null) {
			return;
		}
		PetriObject result = null;
		if (output instanceof DataTransfer) {
			result = (PetriObject) ((DataTransfer) output).clone();
		}
//		Integer inputIndex = util.GetIndexByName(InputPlaceName, Parent.TempMarking);
//
//
//
//		if (inputIndex == -1)
//			return;
//
//		PetriObject temp = Parent.TempMarking.get(inputIndex);

		if (temp instanceof DataFloat) {
			result.SetValue((PetriObject) ((DataFloat) temp).clone());
		}

		if (temp instanceof DataInteger) {
			result.SetValue((PetriObject) ((DataInteger) temp).clone());
		}

		if (temp instanceof DataString) {
			result.SetValue((PetriObject) ((DataString) temp).clone());
		}
		
		if (temp instanceof DataBoolean) {
			result.SetValue((PetriObject) ((DataBoolean) temp).clone());
		}
		
		if (temp instanceof DataFloatFloat) {
			result.SetValue((PetriObject) ((DataFloatFloat) temp).clone());
		}

		if (temp instanceof DataCar) {
			result.SetValue((PetriObject) ((DataCar) temp).clone());
		}

		if (temp instanceof DataSubPetriNet) {
			result.SetValue((PetriObject) ((DataSubPetriNet) temp).clone());
		}
		
		if (temp instanceof DataFuzzy) {
			result.SetValue((PetriObject) ((DataFuzzy) temp).clone());
		}
		
		if (temp instanceof DataNetworkCommand) {
			result.SetValue((PetriObject) ((DataNetworkCommand) temp).clone());
		}

	}

	private void SendROverNetwork() throws CloneNotSupportedException {

		PetriObject output = util.GetPetriObjectByName(OutputPlaceName, Parent.Parent.PlaceList);
		Integer inputIndex = util.GetIndexByName(InputPlaceName, Parent.TempMarking);

		PetriObject result = null;

		if (output instanceof DataTransfer) {
			result = (PetriObject) ((DataTransfer) output).clone();
		}

		if (inputIndex == -1)
			return;

		DataRELQueue temp = (DataRELQueue) Parent.TempMarking.get(inputIndex);

		DataInteger toSend = new DataInteger();
		toSend.SetName(OutputPlaceName);
		toSend.SetValue(temp.Value.GetFirstREL().Value.R);

		if (temp.Value.GetFirstREL().Value.R != ((DataInteger) Parent.TempMarking.get(1)).Value) {
			if (toSend instanceof DataInteger) {
				result.SetValue((PetriObject) ((DataInteger) toSend).clone());
			}
		}
	}

	private void SendPetriNetOverNetwork() throws CloneNotSupportedException {

		PetriObject output = util.GetPetriObjectByName(OutputPlaceName, Parent.Parent.PlaceList);
		Integer inputIndex = util.GetIndexByName(InputPlaceName, Parent.TempMarking);

		PetriObject result = null;

		if (output instanceof DataTransfer) {
			result = (PetriObject) ((DataTransfer) output).clone();
		}

		if (inputIndex == -1)
			return;

		PetriObject temp = Parent.TempMarking.get(inputIndex);

		if (temp instanceof DataSubPetriNet) {
			PetriObject ob = ((DataSubPetriNet) temp).clone();
			DataSubPetriNet sub = (DataSubPetriNet) ob;
			result.SetValue((PetriObject) util.PetriNetToPetriData(sub.Value.Petri));
		}
	}

	private void ActivateSubPetri() throws CloneNotSupportedException {
		PetriObject temp = util.GetFromListByName(InputPlaceName, Parent.Parent.PlaceList);
		if (temp == null)
			return;

		if (temp instanceof DataSubPetriNet) {
			((DataSubPetriNet) temp).Value.StartSubPetri();
		}
	}

	// -------------FloatFloat Modifications--------------------
	private void Add_FloatFlaot() throws CloneNotSupportedException {
		Integer outputIndex = util.GetIndexByName(OutputPlaceName, Parent.Parent.PlaceList);
		PetriObject result = null;

		for (String placeName : InputPlaceNames) {
			PetriObject temp;
			Integer inputIndex = util.GetIndexByName(placeName, Parent.TempMarking);
			if (inputIndex == -1) {
				temp = util.GetFromListByName(placeName, Parent.Parent.ConstantPlaceList);
			} else {
				temp = Parent.TempMarking.get(inputIndex);
			}

			if (temp == null) {
				continue;
			}

			if (temp instanceof DataFloatFloat) {
				if (result == null) {
					result = (PetriObject) ((DataFloatFloat) temp).clone();
				} else {
					FloatFloat ff = new FloatFloat(
							((FloatFloat) result.GetValue()).V1 + ((FloatFloat) temp.GetValue()).V1,
							((FloatFloat) result.GetValue()).V2 + ((FloatFloat) temp.GetValue()).V2);
					result.SetValue(ff);
				}
			}

		}
		result.SetName(OutputPlaceName);
		Parent.Parent.PlaceList.set(outputIndex, result);
	}

	private void Sub_FloatFlaot() throws CloneNotSupportedException {
		Integer outputIndex = util.GetIndexByName(OutputPlaceName, Parent.Parent.PlaceList);
		PetriObject result = null;

		for (String placeName : InputPlaceNames) {
			PetriObject temp;
			Integer inputIndex = util.GetIndexByName(placeName, Parent.TempMarking);
			if (inputIndex == -1) {
				temp = util.GetFromListByName(placeName, Parent.Parent.ConstantPlaceList);
			} else {
				temp = Parent.TempMarking.get(inputIndex);
			}

			if (temp == null) {
				continue;
			}

			if (temp instanceof DataFloatFloat) {
				if (result == null) {
					result = (PetriObject) ((DataFloatFloat) temp).clone();
				} else {
					FloatFloat ff = new FloatFloat(
							((FloatFloat) result.GetValue()).V1 - ((FloatFloat) temp.GetValue()).V1,
							((FloatFloat) result.GetValue()).V2 - ((FloatFloat) temp.GetValue()).V2);
					result.SetValue(ff);
				}
			}

		}
		result.SetName(OutputPlaceName);
		Parent.Parent.PlaceList.set(outputIndex, result);
	}

	private void Prod_FloatFloat() throws CloneNotSupportedException {
		Integer outputIndex = util.GetIndexByName(OutputPlaceName, Parent.Parent.PlaceList);
		PetriObject result = null;

		for (String placeName : InputPlaceNames) {
			PetriObject temp;
			Integer inputIndex = util.GetIndexByName(placeName, Parent.TempMarking);
			if (inputIndex == -1) {
				temp = util.GetFromListByName(placeName, Parent.Parent.ConstantPlaceList);
			} else {
				temp = Parent.TempMarking.get(inputIndex);
			}

			if (temp == null) {
				continue;
			}

			if (temp instanceof DataFloatFloat) {
				if (result == null) {
					result = (PetriObject) ((DataFloatFloat) temp).clone();
				} else {
					FloatFloat ff = new FloatFloat(
							((FloatFloat) result.GetValue()).V1 * ((FloatFloat) temp.GetValue()).V1,
							((FloatFloat) result.GetValue()).V2 * ((FloatFloat) temp.GetValue()).V2);
					result.SetValue(ff);
				}
			}

		}
		result.SetName(OutputPlaceName);
		Parent.Parent.PlaceList.set(outputIndex, result);
	}

	private void Div_FloatFlaot() throws CloneNotSupportedException {
		Integer outputIndex = util.GetIndexByName(OutputPlaceName, Parent.Parent.PlaceList);
		PetriObject result = null;

		for (String placeName : InputPlaceNames) {
			PetriObject temp;
			Integer inputIndex = util.GetIndexByName(placeName, Parent.TempMarking);
			if (inputIndex == -1) {
				temp = util.GetFromListByName(placeName, Parent.Parent.ConstantPlaceList);
			} else {
				temp = Parent.TempMarking.get(inputIndex);
			}

			if (temp == null) {
				continue;
			}

			if (temp instanceof DataFloatFloat) {
				if (result == null) {
					result = (PetriObject) ((DataFloatFloat) temp).clone();
				} else {
					FloatFloat ff = new FloatFloat(
							((FloatFloat) result.GetValue()).V1 / ((FloatFloat) temp.GetValue()).V1,
							((FloatFloat) result.GetValue()).V2 / ((FloatFloat) temp.GetValue()).V2);
					result.SetValue(ff);
				}
			}

		}
		result.SetName(OutputPlaceName);
		Parent.Parent.PlaceList.set(outputIndex, result);
	}

	// --------------------------DynamicDelay----------------------------------

	private void DynamicDelay() throws CloneNotSupportedException {
		PetriObject temp = util.GetFromListByName(InputPlaceName, Parent.TempMarking);
		if (temp != null) {
			if (temp instanceof DataInteger)
				Parent.Delay = ((Integer) (temp.GetValue()));
		} else {
			temp = util.GetFromListByName(InputPlaceName, Parent.Parent.ConstantPlaceList);
			if (temp != null) {
				if (temp instanceof DataInteger)
					Parent.Delay = ((Integer) (temp.GetValue()));
			}
		}
	}

	// -------------Fuzzy Stuff--------------------
	private void Add_Fuzzy() throws CloneNotSupportedException {
		Integer outputIndex = util.GetIndexByName(OutputPlaceName, Parent.Parent.PlaceList);
		DataFuzzy result = null;

		for (PlaceNameWithWeight placeName : InputPlaceNamesWithWeight) {
			PetriObject temp;
			Integer inputIndex = util.GetIndexByName(placeName.PlaceName, Parent.TempMarking);
			if (inputIndex == -1) {
				temp = util.GetFromListByName(placeName.PlaceName, Parent.Parent.ConstantPlaceList);
			} else {
				temp = Parent.TempMarking.get(inputIndex);
			}

			if (temp == null) {
				continue;
			}

			if (temp instanceof DataFuzzy) {
				DataFuzzy cuurentObj = (DataFuzzy) temp;
				cuurentObj.Value.GenerateFuzzyToken(placeName.Weight * cuurentObj.Value.Value);

				if (result == null) {
					result = new DataFuzzy();
					result.SetValue(new Fuzzy(placeName.Weight * cuurentObj.Value.Value));
				} else {
					result.Value.Value += placeName.Weight * cuurentObj.Value.Value;
				}
				
				this.Parent.Parent.LogThis(cuurentObj.toString());
			}

		}
		result.SetName(OutputPlaceName);
		Parent.Parent.PlaceList.set(outputIndex, result);
	}

	private void Sub_Fuzzy() throws CloneNotSupportedException {
		Integer outputIndex = util.GetIndexByName(OutputPlaceName, Parent.Parent.PlaceList);
		DataFuzzy result = null;

		for (PlaceNameWithWeight placeName : InputPlaceNamesWithWeight) {
			PetriObject temp;
			Integer inputIndex = util.GetIndexByName(placeName.PlaceName, Parent.TempMarking);
			if (inputIndex == -1) {
				temp = util.GetFromListByName(placeName.PlaceName, Parent.Parent.ConstantPlaceList);
			} else {
				temp = Parent.TempMarking.get(inputIndex);
			}

			if (temp == null) {
				continue;
			}

			if (temp instanceof DataFuzzy) {
				DataFuzzy cuurentObj = (DataFuzzy) temp;
				cuurentObj.Value.GenerateFuzzyToken(placeName.Weight * cuurentObj.Value.Value);

				if (result == null) {
					result = new DataFuzzy();
					result.SetValue(new Fuzzy(placeName.Weight * cuurentObj.Value.Value));
				} else {
					result.Value.Value -= placeName.Weight * cuurentObj.Value.Value;
				}
	
				this.Parent.Parent.LogThis(cuurentObj.toString());
				
			}

		}
		result.SetName(OutputPlaceName);
		Parent.Parent.PlaceList.set(outputIndex, result);
	}

	private void Prod_Fuzzy() throws CloneNotSupportedException {
		Integer outputIndex = util.GetIndexByName(OutputPlaceName, Parent.Parent.PlaceList);
		DataFuzzy result = null;

		for (PlaceNameWithWeight placeName : InputPlaceNamesWithWeight) {
			PetriObject temp;
			Integer inputIndex = util.GetIndexByName(placeName.PlaceName, Parent.TempMarking);
			if (inputIndex == -1) {
				temp = util.GetFromListByName(placeName.PlaceName, Parent.Parent.ConstantPlaceList);
			} else {
				temp = Parent.TempMarking.get(inputIndex);
			}

			if (temp == null) {
				continue;
			}

			if (temp instanceof DataFuzzy) {
				DataFuzzy cuurentObj = (DataFuzzy) temp;
				cuurentObj.Value.GenerateFuzzyToken(placeName.Weight * cuurentObj.Value.Value);

				if (result == null) {
					result = new DataFuzzy();
					result.SetValue(new Fuzzy(placeName.Weight * cuurentObj.Value.Value));
				} else {
					result.Value.Value *= placeName.Weight * cuurentObj.Value.Value;
				}
				this.Parent.Parent.LogThis(cuurentObj.toString());
			}

		}
		result.SetName(OutputPlaceName);
		Parent.Parent.PlaceList.set(outputIndex, result);
	}

	private void Div_Fuzzy() throws CloneNotSupportedException {
		Integer outputIndex = util.GetIndexByName(OutputPlaceName, Parent.Parent.PlaceList);
		DataFuzzy result = null;

		for (PlaceNameWithWeight placeName : InputPlaceNamesWithWeight) {
			PetriObject temp;
			Integer inputIndex = util.GetIndexByName(placeName.PlaceName, Parent.TempMarking);
			if (inputIndex == -1) {
				temp = util.GetFromListByName(placeName.PlaceName, Parent.Parent.ConstantPlaceList);
			} else {
				temp = Parent.TempMarking.get(inputIndex);
			}

			if (temp == null) {
				continue;
			}

			if (temp instanceof DataFuzzy) {
				DataFuzzy cuurentObj = (DataFuzzy) temp;
				cuurentObj.Value.GenerateFuzzyToken(placeName.Weight * cuurentObj.Value.Value);

				if (result == null) {
					result = new DataFuzzy();
					result.SetValue(new Fuzzy(placeName.Weight * cuurentObj.Value.Value));
				} else {
					result.Value.Value /= placeName.Weight * cuurentObj.Value.Value;
				}
				this.Parent.Parent.LogThis(cuurentObj.toString());
			}

		}
		result.SetName(OutputPlaceName);
		Parent.Parent.PlaceList.set(outputIndex, result);
	}

	private void FLRS_Fuzzy() throws CloneNotSupportedException {

		if (InputPlaceNamesWithWeight.size() == 1) {
			PlaceNameWithWeight placeName1 = InputPlaceNamesWithWeight.get(0);
			Integer input1Index = util.GetIndexByName(placeName1.PlaceName, Parent.TempMarking);
			PetriObject DataFuzzy1;
			if (input1Index == -1) {
				DataFuzzy1 = util.GetFromListByName(placeName1.PlaceName, Parent.Parent.ConstantPlaceList);
			} else {
				DataFuzzy1 = Parent.TempMarking.get(input1Index);
			}

			if (DataFuzzy1 == null) {
				return;
			}
			if (DataFuzzy1 instanceof DataFuzzy) {
				DataFuzzy DF1 = (DataFuzzy) DataFuzzy1;

				DF1.Value.GenerateFuzzyToken(placeName1.Weight * DF1.Value.Value);

				String msg= placeName1.PlaceName + " * Weight(" + placeName1.Weight + ") ------>>>>>>>>>"
						+ DF1.Value.toString();
				this.Parent.Parent.LogThis(msg);
				ArrayList<FuzzyVectorValue> DF1Values = DF1.Value.Vector.GetNoneZeroValues();

				ArrayList<FLRSPart> TriggeredMapping = new ArrayList<FLRSPart>();
				for (FuzzyVectorValue fuzzyVectorValue1 : DF1Values) {
					FLRSPart p = Flrs.Parts.stream().filter((x) -> x.Input1 == fuzzyVectorValue1.Zone).findFirst()
							.orElse(null);
					if (p != null) {
						TriggeredMapping.add(p);
					}
				}

				Float SumOutput1 = null;
				Float SumOutput2 = null;

				for (FLRSPart flrsPart : TriggeredMapping) {

					FuzzyVectorValue fi11 = DF1Values.stream().filter((x) -> x.Zone == flrsPart.Input1).findFirst()
							.orElse(null);

					FuzzyVectorValue fi21 = DF1Values.stream().filter((x) -> x.Zone == flrsPart.Input1).findFirst()
							.orElse(null);

					if (flrsPart.Value.Value1 != FZ.FF) {
						if (SumOutput1 == null)
							SumOutput1 = 0f;
						SumOutput1 += util.FuzzyZoneToValue(flrsPart.Value.Value1) * fi11.Value;
					}
					if (OutputPlaceNames.size() == 2) {
						if (flrsPart.Value.Value2 != FZ.FF) {
							if (SumOutput2 == null)
								SumOutput2 = 0f;
							SumOutput2 += util.FuzzyZoneToValue(flrsPart.Value.Value2) * fi21.Value;
						}
					}
				}

				Integer outputIndex1 = util.GetIndexByName(OutputPlaceNames.get(0), Parent.Parent.PlaceList);
				DataFuzzy result1 = new DataFuzzy();
				if (SumOutput1 == null) {
					result1.SetValue(null);
				} else {
					result1.SetValue(new Fuzzy(SumOutput1));
				}
				result1.SetName(OutputPlaceNames.get(0));
				Parent.Parent.PlaceList.set(outputIndex1, result1);

				if (OutputPlaceNames.size() == 2) {
					Integer outputIndex2 = util.GetIndexByName(OutputPlaceNames.get(1), Parent.Parent.PlaceList);
					DataFuzzy result2 = new DataFuzzy();
					if (SumOutput2 == null) {
						result2.SetValue(null);
					} else {
						result2.SetValue(new Fuzzy(SumOutput2));
					}
					result2.SetName(OutputPlaceNames.get(1));
					Parent.Parent.PlaceList.set(outputIndex2, result2);
				}

			} else {
				return;
			}
		}
		if (InputPlaceNamesWithWeight.size() == 2) {
			PlaceNameWithWeight placeName1 = InputPlaceNamesWithWeight.get(0);
			PlaceNameWithWeight placeName2 = InputPlaceNamesWithWeight.get(1);

			Integer input1Index = util.GetIndexByName(placeName1.PlaceName, Parent.TempMarking);
			Integer input2Index = util.GetIndexByName(placeName2.PlaceName, Parent.TempMarking);

			PetriObject DataFuzzy1;
			if (input1Index == -1) {
				DataFuzzy1 = util.GetFromListByName(placeName1.PlaceName, Parent.Parent.ConstantPlaceList);
			} else {
				DataFuzzy1 = Parent.TempMarking.get(input1Index);
			}

			if (DataFuzzy1 == null) {
				return;
			}

			PetriObject DataFuzzy2;
			if (input2Index == -1) {
				DataFuzzy2 = util.GetFromListByName(placeName2.PlaceName, Parent.Parent.ConstantPlaceList);
			} else {
				DataFuzzy2 = Parent.TempMarking.get(input2Index);
			}

			if (DataFuzzy2 == null) {
				return;
			}

			if (DataFuzzy1 instanceof DataFuzzy && DataFuzzy2 instanceof DataFuzzy) {
				DataFuzzy DF1 = (DataFuzzy) DataFuzzy1;
				DataFuzzy DF2 = (DataFuzzy) DataFuzzy2;

				DF1.Value.GenerateFuzzyToken(placeName1.Weight * DF1.Value.Value);
				DF2.Value.GenerateFuzzyToken(placeName2.Weight * DF2.Value.Value);

				String msg = placeName1.PlaceName + " * Weight (" + placeName1.Weight + ") ------>>>>>>>>>"
						+ DF1.Value.toString() + placeName2.PlaceName + " * Weight (" + placeName2.Weight
						+ ") ------>>>>>>>>>" + DF2.Value.toString();
			
				this.Parent.Parent.LogThis(msg);
				ArrayList<FuzzyVectorValue> DF1Values = DF1.Value.Vector.GetNoneZeroValues();
				ArrayList<FuzzyVectorValue> DF2Values = DF2.Value.Vector.GetNoneZeroValues();

				ArrayList<FLRSPart> TriggeredMapping = new ArrayList<FLRSPart>();
				for (FuzzyVectorValue fuzzyVectorValue1 : DF1Values) {
					for (FuzzyVectorValue fuzzyVectorValue2 : DF2Values) {
						FLRSPart p = Flrs.Parts.stream()
								.filter((x) -> x.Input1 == fuzzyVectorValue1.Zone && x.Input2 == fuzzyVectorValue2.Zone)
								.findFirst().orElse(null);
						if (p != null) {
							TriggeredMapping.add(p);
						}
					}
				}

				Float SumOutput1 = null;
				Float SumOutput2 = null;

				for (FLRSPart flrsPart : TriggeredMapping) {

					FuzzyVectorValue fi11 = DF1Values.stream().filter((x) -> x.Zone == flrsPart.Input1).findFirst()
							.orElse(null);
					FuzzyVectorValue fi12 = DF2Values.stream().filter((x) -> x.Zone == flrsPart.Input2).findFirst()
							.orElse(null);

					FuzzyVectorValue fi21 = DF1Values.stream().filter((x) -> x.Zone == flrsPart.Input1).findFirst()
							.orElse(null);
					FuzzyVectorValue fi22 = DF2Values.stream().filter((x) -> x.Zone == flrsPart.Input2).findFirst()
							.orElse(null);

					if (flrsPart.Value.Value1 != FZ.FF) {
						if (SumOutput1 == null)
							SumOutput1 = 0f;
						SumOutput1 += util.FuzzyZoneToValue(flrsPart.Value.Value1) * fi11.Value * fi12.Value;
					}
					if (OutputPlaceNames.size() == 2) {
						if (flrsPart.Value.Value2 != FZ.FF) {
							if (SumOutput2 == null)
								SumOutput2 = 0f;
							SumOutput2 += util.FuzzyZoneToValue(flrsPart.Value.Value2) * fi21.Value * fi22.Value;
						}
					}
				}

				Integer outputIndex1 = util.GetIndexByName(OutputPlaceNames.get(0), Parent.Parent.PlaceList);
				DataFuzzy result1 = new DataFuzzy();
				if (SumOutput1 == null) {
					result1.SetValue(null);
				} else {
					result1.SetValue(new Fuzzy(SumOutput1));
				}
				result1.SetName(OutputPlaceNames.get(0));
				Parent.Parent.PlaceList.set(outputIndex1, result1);

				if (OutputPlaceNames.size() == 2) {
					Integer outputIndex2 = util.GetIndexByName(OutputPlaceNames.get(1), Parent.Parent.PlaceList);
					DataFuzzy result2 = new DataFuzzy();

					if (SumOutput2 == null) {
						result2.SetValue(null);
					} else {
						result2.SetValue(new Fuzzy(SumOutput2));
					}
					result2.SetName(OutputPlaceNames.get(1));
					Parent.Parent.PlaceList.set(outputIndex2, result2);
				}

			} else {
				return;
			}
		}
	}

}
