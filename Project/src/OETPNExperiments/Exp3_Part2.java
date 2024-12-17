package OETPNExperiments;

import Components.Activation;
import Components.Condition;
import Components.GuardMapping;
import Components.PetriNet;
import Components.PetriNetWindow;
import Components.PetriTransition;
import DataObjects.DataFloat;
import DataObjects.DataSubPetriNet;
import Enumerations.LogicConnector;
import Enumerations.TransitionCondition;
import Enumerations.TransitionOperation;

public class Exp3_Part2 {

	public static void main(String[] args) {
		PetriNet pn = new PetriNet();
		pn.PetriNetName = "PN2";
		pn.NetworkPort = 1090;

		// ------------------------------------------------------------------------
		DataFloat constValue = new DataFloat();
		constValue.SetName("constValue");
		constValue.SetValue(1.0f);
		pn.ConstantPlaceList.add(constValue);
		
		DataFloat p21 = new DataFloat();
		p21.SetName("p21");
		p21.SetValue(1.0f);
		pn.PlaceList.add(p21);

		DataSubPetriNet p22 = new DataSubPetriNet();
		p22.SetName("p22");
		pn.PlaceList.add(p22);

		DataSubPetriNet p23 = new DataSubPetriNet();
		p23.SetName("p23");
		pn.PlaceList.add(p23);

		DataSubPetriNet p24 = new DataSubPetriNet();
		p24.SetName("p24");
		pn.PlaceList.add(p24);

		DataFloat p25 = new DataFloat();
		p25.SetName("p25");
		pn.PlaceList.add(p25);
		
	

		// T21 ------------------------------------------------
		PetriTransition t21 = new PetriTransition(pn);
		t21.TransitionName = "t21";
		t21.InputPlaceName.add("p21");
		t21.InputPlaceName.add("p22");

		Condition T21Ct1 = new Condition(t21, "p21", TransitionCondition.NotNull);
		Condition T21Ct2 = new Condition(t21, "p22", TransitionCondition.NotNull);
		T21Ct1.SetNextCondition(LogicConnector.AND, T21Ct2);

		GuardMapping grdT21 = new GuardMapping();
		grdT21.condition = T21Ct1;

		grdT21.Activations.add(new Activation(t21, "p22", TransitionOperation.Move, "p23"));

		t21.GuardMappingList.add(grdT21);
		t21.Delay = 0;
		pn.Transitions.add(t21);

		// T22 ------------------------------------------------
		PetriTransition t22 = new PetriTransition(pn);
		t22.TransitionName = "t22";
		t22.InputPlaceName.add("p23");

		Condition T22Ct1 = new Condition(t22, "p23", TransitionCondition.NotNull);

		GuardMapping grdT22 = new GuardMapping();
		grdT22.condition = T22Ct1;

		grdT22.Activations.add(new Activation(t22, "p23", TransitionOperation.Move, "p24"));
		grdT22.Activations.add(new Activation(t22, "p24", TransitionOperation.ActivateSubPetri, ""));
		grdT22.Activations.add(new Activation(t22, "constValue", TransitionOperation.Move, "p25"));

		t22.GuardMappingList.add(grdT22);
		t22.Delay = 0;
		pn.Transitions.add(t22);

		// T23 ------------------------------------------------
		PetriTransition t23 = new PetriTransition(pn);
		t23.TransitionName = "t23";
		t23.InputPlaceName.add("p25");

		Condition T23Ct1 = new Condition(t23, "p25", TransitionCondition.NotNull);

		GuardMapping grdT23 = new GuardMapping();
		grdT23.condition = T23Ct1;

		grdT23.Activations.add(new Activation(t23, "p25", TransitionOperation.Move, "p21"));

		t23.GuardMappingList.add(grdT23);
		t23.Delay = 0;
		pn.Transitions.add(t23);

		System.out.println("Exp2 started \n ------------------------------");
		pn.Delay = 3000;

		PetriNetWindow frame = new PetriNetWindow(false);
		frame.petriNet = pn;
		frame.setVisible(true);
	}
}
