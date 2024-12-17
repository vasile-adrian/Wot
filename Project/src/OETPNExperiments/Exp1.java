package OETPNExperiments;

import java.util.ArrayList;

import Components.Activation;
import Components.Condition;
import Components.GuardMapping;
import Components.PetriNet;
import Components.PetriNetWindow;
import Components.PetriTransition;
import DataObjects.DataFloat;
import DataObjects.DataSubPetriNet;
import DataOnly.SubPetri;
import Enumerations.LogicConnector;
import Enumerations.TransitionCondition;
import Enumerations.TransitionOperation;

public class Exp1 {

	public static void main(String[] args) {
		PetriNet pn = new PetriNet();
		pn.PetriNetName = "Main Petri";
		pn.NetworkPort = 1080;
		
		// ----------------------- sub petri ------------------------------------
		PetriNet spn = new PetriNet();
		spn.PetriNetName = "Sub Petri";

		DataFloat subConstantValue1 = new DataFloat();
		subConstantValue1.SetName("subConstantValue1");
		subConstantValue1.SetValue(0.1f);
		spn.ConstantPlaceList.add(subConstantValue1);

		DataFloat subConstantValue2 = new DataFloat();
		subConstantValue2.SetName("subConstantValue2");
		subConstantValue2.SetValue(2.0f);
		spn.ConstantPlaceList.add(subConstantValue2);

		DataFloat p21 = new DataFloat();
		p21.SetName("p21");
		spn.PlaceList.add(p21);

		DataFloat p22 = new DataFloat();
		p22.SetName("p22");
		spn.PlaceList.add(p22);

		DataFloat p23 = new DataFloat();
		p23.SetName("p23");
		spn.PlaceList.add(p23);

		DataFloat p24 = new DataFloat();
		p24.SetName("p24");
		//p24.SetValue(1.0f);
		spn.PlaceList.add(p24);

		// T21 ------------------------------------------------
		PetriTransition t21 = new PetriTransition(spn);
		t21.TransitionName = "t21";
		t21.InputPlaceName.add("p21");

		Condition T21Ct1 = new Condition(t21, "p21", TransitionCondition.NotNull);

		GuardMapping grdT21 = new GuardMapping();
		grdT21.condition = T21Ct1;
		grdT21.Activations.add(new Activation(t21, "p21", TransitionOperation.Move, "p22"));

		t21.GuardMappingList.add(grdT21);
		t21.Delay = 0;
		spn.Transitions.add(t21);

		// T22 ------------------------------------------------
		PetriTransition t22 = new PetriTransition(spn);
		t22.TransitionName = "t22";
		t22.InputPlaceName.add("p22");

		Condition T22Ct1 = new Condition(t22, "p22", TransitionCondition.NotNull);

		GuardMapping grdT22 = new GuardMapping();
		grdT22.condition = T22Ct1;

		ArrayList<String> lstInput = new ArrayList<String>();
		lstInput.add("p22");
		lstInput.add("subConstantValue1");
		grdT22.Activations.add(new Activation(t22, lstInput, TransitionOperation.Add, "p23"));
		
		t22.GuardMappingList.add(grdT22);
		t22.Delay = 0;
		spn.Transitions.add(t22);

		// T23 ------------------------------------------------
		PetriTransition t23 = new PetriTransition(spn);
		t23.TransitionName = "t23";
		t23.InputPlaceName.add("p23");
		
		Condition T23Ct1 = new Condition(t23, "p23", TransitionCondition.NotNull);
		Condition T23Ct2 = new Condition(t23, "p23", TransitionCondition.LessThan, "subConstantValue2");
		T23Ct1.SetNextCondition(LogicConnector.AND, T23Ct2);

		GuardMapping grdT23 = new GuardMapping();
		grdT23.condition = T23Ct1;

		grdT23.Activations.add(new Activation(t23,"p23", TransitionOperation.Move, "p24"));
		grdT23.Activations.add(new Activation(t23,"p23", TransitionOperation.Move, "p21"));
		
		
		t23.GuardMappingList.add(grdT23);
		t23.Delay = 1;
		spn.Transitions.add(t23);
		
		
		// T24 ------------------------------------------------
		PetriTransition t24 = new PetriTransition(spn);
		t24.TransitionName = "t24";
		t24.InputPlaceName.add("p23");

		Condition T24Ct1 = new Condition(t24, "p23", TransitionCondition.NotNull);
		Condition T24Ct2 = new Condition(t24, "p23", TransitionCondition.MoreThanOrEqual, "subConstantValue2");
		T24Ct1.SetNextCondition(LogicConnector.AND, T24Ct2);

		GuardMapping grdT24 = new GuardMapping();
		grdT24.condition = T24Ct1;
		grdT24.Activations.add(new Activation(t24,"", TransitionOperation.StopPetriNet, ""));
		
		t24.GuardMappingList.add(grdT24);
		t24.Delay = 0;
		spn.Transitions.add(t24);
		
		spn.Delay = 3000;
		
		// ------------------------------------------------------------------------
		DataSubPetriNet subPetriNet = new DataSubPetriNet();
		subPetriNet.SetName("SubPetri");
		SubPetri sptr= new SubPetri(spn);
		subPetriNet.SetValue(sptr);
		pn.ConstantPlaceList.add(subPetriNet);

		DataFloat constantValue1 = new DataFloat();
		constantValue1.SetName("constantValue1");
		constantValue1.SetValue(1.0f);
		pn.ConstantPlaceList.add(constantValue1);

		DataFloat p1 = new DataFloat();
		p1.SetName("p1");
		p1.SetValue(1.0f);
		pn.PlaceList.add(p1);

		DataSubPetriNet p2 = new DataSubPetriNet();
		p2.SetName("p2");
		pn.PlaceList.add(p2);

		DataFloat p3 = new DataFloat();
		p3.SetName("p3");
		pn.PlaceList.add(p3);

		DataFloat p4 = new DataFloat();
		p4.SetName("p4");
		//p4.SetValue(1.0f);//<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<test
		pn.PlaceList.add(p4);

		DataFloat p5 = new DataFloat();
		p5.SetName("p5");
		pn.PlaceList.add(p5);

		// T1 ------------------------------------------------
		PetriTransition t1 = new PetriTransition(pn);
		t1.TransitionName = "t1";
		//t1.IsAsync = true;
		t1.InputPlaceName.add("p1");
		t1.InputPlaceName.add("p4");
		
		Condition T1Ct1 = new Condition(t1, "p1", TransitionCondition.NotNull);
		Condition T1Ct2 = new Condition(t1, "p4", TransitionCondition.LessThanOrEqual, "constantValue1");
		T1Ct1.SetNextCondition(LogicConnector.AND, T1Ct2);

		GuardMapping grdT1 = new GuardMapping();
		grdT1.condition = T1Ct1;
		grdT1.Activations.add(new Activation(t1, "SubPetri", TransitionOperation.Move, "p2"));
		grdT1.Activations.add(new Activation(t1, "p4", TransitionOperation.Move, "p2-p21"));
		grdT1.Activations.add(new Activation(t1, "p2", TransitionOperation.ActivateSubPetri, ""));

		t1.GuardMappingList.add(grdT1);

		Condition T1Ct3 = new Condition(t1, "p1", TransitionCondition.NotNull);
		Condition T1Ct4 = new Condition(t1, "p4", TransitionCondition.MoreThan, "constantValue1");
		T1Ct3.SetNextCondition(LogicConnector.AND, T1Ct4);

		GuardMapping grd2T1 = new GuardMapping();
		grd2T1.condition = T1Ct3;
		grd2T1.Activations.add(new Activation(t1, "SubPetri", TransitionOperation.Copy, "p2"));
		grd2T1.Activations.add(new Activation(t1, "p4", TransitionOperation.Move, "p2-p21"));
		
		t1.GuardMappingList.add(grd2T1);

		t1.Delay = 0;
		pn.Transitions.add(t1);

		// T2 ------------------------------------------------
		PetriTransition t2 = new PetriTransition(pn);
		t2.TransitionName = "t2";
		t2.InputPlaceName.add("p2");

		Condition T2Ct1 = new Condition(t2, "p2", TransitionCondition.NotNull);
		Condition T2Ct2 = new Condition(t2, "p2", TransitionCondition.SubPetriStopped);
		T2Ct1.SetNextCondition(LogicConnector.AND, T2Ct2);

		GuardMapping grdT2 = new GuardMapping();
		grdT2.condition = T2Ct1;
		grdT2.Activations.add(new Activation(t2, "p2-p24", TransitionOperation.Copy, "p3"));
		grdT2.Activations.add(new Activation(t2, "p2-p24", TransitionOperation.Move, "p5"));

		t2.GuardMappingList.add(grdT2);
		t2.Delay = 0;
		pn.Transitions.add(t2);

		// T3 ------------------------------------------------
		PetriTransition t3 = new PetriTransition(pn);
		t3.TransitionName = "t3";
		t3.InputPlaceName.add("p3");

		Condition T3Ct1 = new Condition(t3, "p3", TransitionCondition.NotNull);

		GuardMapping grdT3 = new GuardMapping();
		grdT3.condition = T3Ct1;
		grdT3.Activations.add(new Activation(t3, "p3", TransitionOperation.Move, "p1"));

		t3.GuardMappingList.add(grdT3);
		t3.Delay = 1;
		pn.Transitions.add(t3);

		System.out.println("Exp1 started \n ------------------------------");
		pn.Delay = 3000;

		PetriNetWindow frame = new PetriNetWindow(false);
		frame.petriNet = pn;
		frame.setVisible(true);
	}
}
