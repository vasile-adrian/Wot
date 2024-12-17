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
import DataObjects.DataTransfer;
import DataOnly.SubPetri;
import DataOnly.TransferOperation;
import Enumerations.LogicConnector;
import Enumerations.TransitionCondition;
import Enumerations.TransitionOperation;

public class Exp2 {

	public static PetriNet NP2() {
		
		PetriNet spn = new PetriNet();
		spn.PetriNetName = "NP2";
		spn.NetworkPort = 1081;

		DataFloat subConstantValue1 = new DataFloat();
		subConstantValue1.SetName("subConstantValue1");
		subConstantValue1.SetValue(0.1f);
		spn.ConstantPlaceList.add(subConstantValue1);

		DataFloat subConstantValue2 = new DataFloat();
		subConstantValue2.SetName("subConstantValue2");
		subConstantValue2.SetValue(3.0f);
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
		spn.PlaceList.add(p24);

		DataTransfer p25 = new DataTransfer();
		p25.SetName("p25");
		p25.Value = new TransferOperation("localhost", "1082", "p34");
		spn.PlaceList.add(p25);

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

		grdT23.Activations.add(new Activation(t23, "p23", TransitionOperation.SendOverNetwork, "p25"));
		grdT23.Activations.add(new Activation(t23, "p23", TransitionOperation.Move, "p24"));
		grdT23.Activations.add(new Activation(t23, "p23", TransitionOperation.Move, "p21"));

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
		
		grdT24.Activations.add(new Activation(t24, "", TransitionOperation.StopPetriNet, ""));

		t24.GuardMappingList.add(grdT24);
		t24.Delay = 0;
		spn.Transitions.add(t24);

		spn.Delay = 3000;

		return spn;
	}

	public static PetriNet NP3() {
		
		PetriNet spn = new PetriNet();
		spn.PetriNetName = "NP3";
		spn.NetworkPort = 1082;

		DataFloat subConstantValue1 = new DataFloat();
		subConstantValue1.SetName("subConstantValue1");
		subConstantValue1.SetValue(4.0f);
		spn.ConstantPlaceList.add(subConstantValue1);

		DataFloat p31 = new DataFloat();
		p31.SetName("p31");
		spn.PlaceList.add(p31);

		DataFloat p32 = new DataFloat();
		p32.SetName("p32");
		spn.PlaceList.add(p32);

		DataFloat p33 = new DataFloat();
		p33.SetName("p33");
		spn.PlaceList.add(p33);

		DataFloat p34 = new DataFloat();
		p34.SetName("p34");
		spn.PlaceList.add(p34);

		DataFloat p35 = new DataFloat();
		p35.SetName("p35");
		spn.PlaceList.add(p35);

		// T31 ------------------------------------------------
		PetriTransition t31 = new PetriTransition(spn);
		t31.TransitionName = "t31";
		t31.InputPlaceName.add("p31");
		t31.InputPlaceName.add("p34");

		Condition T31Ct1 = new Condition(t31, "p31", TransitionCondition.NotNull);
		Condition T31Ct2 = new Condition(t31, "p34", TransitionCondition.NotNull);
		T31Ct1.SetNextCondition(LogicConnector.AND, T31Ct2);

		GuardMapping grdT31 = new GuardMapping();
		grdT31.condition = T31Ct1;

		ArrayList<String> lstInput = new ArrayList<String>();
		lstInput.add("p31");
		lstInput.add("p34");

		grdT31.Activations.add(new Activation(t31, lstInput, TransitionOperation.Add, "p32"));

		t31.GuardMappingList.add(grdT31);
		t31.Delay = 0;
		spn.Transitions.add(t31);

		// T32 ------------------------------------------------
		PetriTransition t32 = new PetriTransition(spn);
		t32.TransitionName = "t32";
		t32.InputPlaceName.add("p32");

		Condition T32Ct1 = new Condition(t32, "p32", TransitionCondition.NotNull);

		GuardMapping grdT32 = new GuardMapping();
		grdT32.condition = T32Ct1;

		grdT32.Activations.add(new Activation(t32, "p32", TransitionOperation.Copy, "p35"));
		grdT32.Activations.add(new Activation(t32, "p32", TransitionOperation.Move, "p33"));

		t32.GuardMappingList.add(grdT32);
		t32.Delay = 0;
		spn.Transitions.add(t32);

		// T33 ------------------------------------------------
		PetriTransition t33 = new PetriTransition(spn);
		t33.TransitionName = "t33";
		t33.InputPlaceName.add("p33");

		Condition T33Ct1 = new Condition(t33, "p33", TransitionCondition.NotNull);
		Condition T33Ct2 = new Condition(t33, "p33", TransitionCondition.LessThanOrEqual, "subConstantValue1");
		T33Ct1.SetNextCondition(LogicConnector.AND, T33Ct2);

		GuardMapping grdT33 = new GuardMapping();
		grdT33.condition = T33Ct1;

		grdT33.Activations.add(new Activation(t33, "p33", TransitionOperation.Move, "p31"));

		t33.GuardMappingList.add(grdT33);
		t33.Delay = 1;
		spn.Transitions.add(t33);

		// T34 ------------------------------------------------
		PetriTransition t34 = new PetriTransition(spn);
		t34.TransitionName = "t34";
		t34.InputPlaceName.add("p33");

		Condition T34Ct1 = new Condition(t34, "p33", TransitionCondition.NotNull);
		Condition T34Ct2 = new Condition(t34, "p33", TransitionCondition.MoreThan, "subConstantValue1");
		T34Ct1.SetNextCondition(LogicConnector.AND, T34Ct2);

		GuardMapping grdT34 = new GuardMapping();
		grdT34.condition = T34Ct1;
		grdT34.Activations.add(new Activation(t34, "", TransitionOperation.StopPetriNet, ""));

		t34.GuardMappingList.add(grdT34);
		t34.Delay = 0;
		spn.Transitions.add(t34);

		spn.Delay = 3000;

		return spn;
	}

	public static PetriNet MainPetri1() {
		// ----------------------- main petri1 ------------------------------------
		PetriNet mpn = new PetriNet();
		mpn.PetriNetName = "mpn1";
		mpn.NetworkPort = 0;

		DataFloat p2 = new DataFloat();
		p2.SetName("p2");
		mpn.PlaceList.add(p2);

		DataSubPetriNet p3 = new DataSubPetriNet();
		p3.SetName("p3");
		mpn.PlaceList.add(p3);

		DataFloat p4 = new DataFloat();
		p4.SetName("p4");
		mpn.PlaceList.add(p4);

		DataFloat p5 = new DataFloat();
		p5.SetName("p5");
		p5.SetValue(2.0f);
		mpn.PlaceList.add(p5);
		
		DataSubPetriNet NP3 = new DataSubPetriNet();
		NP3.SetName("NP3");
		SubPetri sptr2 = new SubPetri(NP3());
		NP3.SetValue(sptr2);
		mpn.ConstantPlaceList.add(NP3);

		// T2 ------------------------------------------------
		PetriTransition t2 = new PetriTransition(mpn);
		t2.TransitionName = "t2";
		t2.InputPlaceName.add("p2");
		t2.InputPlaceName.add("p5");

		Condition T2Ct1 = new Condition(t2, "p2", TransitionCondition.NotNull);
		Condition T2Ct2 = new Condition(t2, "p5", TransitionCondition.NotNull);
		T2Ct1.SetNextCondition(LogicConnector.AND, T2Ct2);

		GuardMapping grdT2 = new GuardMapping();
		grdT2.condition = T2Ct1;
		grdT2.Activations.add(new Activation(t2, "NP3", TransitionOperation.Copy, "p3"));
		grdT2.Activations.add(new Activation(t2, "p5", TransitionOperation.Move, "p3-p31"));
		grdT2.Activations.add(new Activation(t2, "p3", TransitionOperation.ActivateSubPetri, ""));

		t2.GuardMappingList.add(grdT2);
		t2.Delay = 0;
		mpn.Transitions.add(t2);

		// T3 ------------------------------------------------
		PetriTransition t3 = new PetriTransition(mpn);
		t3.TransitionName = "t3";
		t3.InputPlaceName.add("p3");

		Condition T3Ct1 = new Condition(t3, "p3", TransitionCondition.NotNull);
		Condition T3Ct2 = new Condition(t3, "p3", TransitionCondition.SubPetriStopped);
		T3Ct1.SetNextCondition(LogicConnector.AND, T3Ct2);

		GuardMapping grdT3 = new GuardMapping();
		grdT3.condition = T3Ct1;
		grdT3.Activations.add(new Activation(t3, "p3-p33", TransitionOperation.Move, "p4"));

		t3.GuardMappingList.add(grdT3);
		t3.Delay = 0;
		mpn.Transitions.add(t3);

		// T4 ------------------------------------------------
		PetriTransition t4 = new PetriTransition(mpn);
		t4.TransitionName = "t4";
		t4.InputPlaceName.add("p4");

		Condition T4Ct1 = new Condition(t4, "p4", TransitionCondition.NotNull);

		GuardMapping grdT4 = new GuardMapping();
		grdT4.condition = T4Ct1;
		grdT4.Activations.add(new Activation(t4, "p4", TransitionOperation.Move, "p2"));

		t4.GuardMappingList.add(grdT4);
		t4.Delay = 1;
		mpn.Transitions.add(t4);

		mpn.Delay = 3000;

		return mpn;
	}

	public static PetriNet MainPetri2() {
		// ----------------------- main petri2 ------------------------------------
		PetriNet mpn = new PetriNet();
		mpn.PetriNetName = "mpn2";
		mpn.NetworkPort = 0;

		DataFloat constantValue1 = new DataFloat();
		constantValue1.SetName("constantValue1");
		constantValue1.SetValue(1.0f);
		mpn.ConstantPlaceList.add(constantValue1);
		
		DataFloat p6 = new DataFloat();
		p6.SetName("p6");
		mpn.PlaceList.add(p6);

		DataSubPetriNet p7 = new DataSubPetriNet();
		p7.SetName("p7");
		mpn.PlaceList.add(p7);

		DataFloat p8 = new DataFloat();
		p8.SetName("p8");
		mpn.PlaceList.add(p8);

		DataFloat p9 = new DataFloat();
		p9.SetName("p9");
		p9.SetValue(2.0f);
		mpn.PlaceList.add(p9);
		
		DataSubPetriNet NP2 = new DataSubPetriNet();
		NP2.SetName("NP2");
		SubPetri sptr1 = new SubPetri(NP2());
		NP2.SetValue(sptr1);
		mpn.ConstantPlaceList.add(NP2);

		// T5 ------------------------------------------------
		PetriTransition t5 = new PetriTransition(mpn);
		t5.TransitionName = "t5";
		t5.InputPlaceName.add("p6");
		t5.InputPlaceName.add("p9");

		Condition T5Ct1 = new Condition(t5, "p6", TransitionCondition.NotNull);
		Condition T5Ct2 = new Condition(t5, "p9", TransitionCondition.NotNull);
		Condition T5Ct3 = new Condition(t5, "p9", TransitionCondition.MoreThanOrEqual, "constantValue1");
		T5Ct2.SetNextCondition(LogicConnector.AND, T5Ct3);
		T5Ct1.SetNextCondition(LogicConnector.AND, T5Ct2);

		GuardMapping grdT5 = new GuardMapping();
		grdT5.condition = T5Ct1;
		
		grdT5.Activations.add(new Activation(t5, "NP2", TransitionOperation.Copy, "p7"));
		grdT5.Activations.add(new Activation(t5, "p9", TransitionOperation.Move, "p7-p21"));
		grdT5.Activations.add(new Activation(t5, "p7", TransitionOperation.ActivateSubPetri, ""));
		grdT5.Activations.add(new Activation(t5, "p6", TransitionOperation.Move, "p6"));

		t5.GuardMappingList.add(grdT5);
		t5.Delay = 0;
		mpn.Transitions.add(t5);

		// T6 ------------------------------------------------
		PetriTransition t7 = new PetriTransition(mpn);
		t7.TransitionName = "t7";
		t7.InputPlaceName.add("p7");

		Condition T7Ct1 = new Condition(t7, "p7", TransitionCondition.NotNull);
		Condition T7Ct2 = new Condition(t7, "p7", TransitionCondition.SubPetriStopped);
		T7Ct1.SetNextCondition(LogicConnector.AND, T7Ct2);

		GuardMapping grdT7 = new GuardMapping();
		grdT7.condition = T7Ct1;
		grdT7.Activations.add(new Activation(t7, "p7-p23", TransitionOperation.Move, "p8"));

		t7.GuardMappingList.add(grdT7);
		t7.Delay = 0;
		mpn.Transitions.add(t7);

		mpn.Delay = 3000;

		return mpn;
	}

	public static void main(String[] args) {

		PetriNet pn = new PetriNet();
		pn.PetriNetName = "Main Petri NP1";
		pn.NetworkPort = 0;

		// ------------------------------------------------------------------------

		DataSubPetriNet MP1 = new DataSubPetriNet();
		MP1.SetName("MP1");
		SubPetri mptr1 = new SubPetri(MainPetri1());
		MP1.SetValue(mptr1);
		pn.ConstantPlaceList.add(MP1);
		
		DataSubPetriNet MP2 = new DataSubPetriNet();
		MP2.SetName("MP2");
		SubPetri mptr2 = new SubPetri(MainPetri2());
		MP2.SetValue(mptr2);
		pn.ConstantPlaceList.add(MP2);

		DataFloat p1 = new DataFloat();
		p1.SetName("p1");
		p1.SetValue(2.0f);
		pn.PlaceList.add(p1);
		
		DataSubPetriNet p10 = new DataSubPetriNet(); //First thread of execution of the main petri
		p10.SetName("p10");
		pn.PlaceList.add(p10);
		
		DataSubPetriNet p11 = new DataSubPetriNet(); //Second thread of execution of the main petri
		p11.SetName("p11");
		pn.PlaceList.add(p11);

		// T1 ------------------------------------------------
		PetriTransition t1 = new PetriTransition(pn);
		t1.TransitionName = "t1";
		t1.InputPlaceName.add("p1");
		
		Condition T1Ct1 = new Condition(t1, "p1", TransitionCondition.NotNull);

		GuardMapping grdT1 = new GuardMapping();
		grdT1.condition = T1Ct1;

		grdT1.Activations.add(new Activation(t1, "MP1", TransitionOperation.Copy, "p10"));
		grdT1.Activations.add(new Activation(t1, "MP2", TransitionOperation.Copy, "p11"));
		
		grdT1.Activations.add(new Activation(t1, "p1", TransitionOperation.Move, "p10-p2"));
		grdT1.Activations.add(new Activation(t1, "p1", TransitionOperation.Move, "p11-p6"));
		
		grdT1.Activations.add(new Activation(t1, "p10", TransitionOperation.ActivateSubPetri, ""));
		grdT1.Activations.add(new Activation(t1, "p11", TransitionOperation.ActivateSubPetri, ""));

		t1.GuardMappingList.add(grdT1);
		t1.Delay = 0;
		pn.Transitions.add(t1);

		System.out.println("Exp2 started \n ------------------------------");
		pn.Delay = 3000;

		PetriNetWindow frame = new PetriNetWindow(false);
		frame.petriNet = pn;
		frame.setVisible(true);
	}
}
