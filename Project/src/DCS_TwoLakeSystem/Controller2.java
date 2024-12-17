package DCS_TwoLakeSystem;

import Components.Activation;
import Components.Condition;
import Components.GuardMapping;
import Components.PetriNet;
import Components.PetriNetWindow;
import Components.PetriTransition;
import DataObjects.DataFloat;
import DataObjects.DataString;
import DataObjects.DataTransfer;
import DataOnly.TransferOperation;
import Enumerations.LogicConnector;
import Enumerations.TransitionCondition;
import Enumerations.TransitionOperation;

public class Controller2 {
	public static void main(String[] args) {

		PetriNet pn = new PetriNet();
		pn.PetriNetName = "Controller 2";
		pn.NetworkPort = 1081;

		DataFloat hr2 = new DataFloat();
		hr2.SetName("hr2");
		hr2.SetValue(30.0f);
		pn.PlaceList.add(hr2);

		DataFloat h2 = new DataFloat();
		h2.SetName("h2"); //input a value to h2 from GUI input float
		pn.PlaceList.add(h2);

		DataString dc2 = new DataString();
		dc2.SetName("dc2");
		pn.PlaceList.add(dc2);

		DataString c2 = new DataString();
		c2.SetName("c2");
		pn.PlaceList.add(c2);

		DataString c2Previous = new DataString();
		c2Previous.SetName("c2Previous");
		c2Previous.SetValue("No Action");
		pn.PlaceList.add(c2Previous);

		DataTransfer m13 = new DataTransfer();
		m13.SetName("m13");
		m13.Value = new TransferOperation("localhost", "1082", "m13");
		pn.PlaceList.add(m13);

		DataString m12 = new DataString();
		m12.SetName("m12");
		pn.PlaceList.add(m12);

		DataString po = new DataString();
		po.SetName("po");
		pn.PlaceList.add(po);

		DataString po2 = new DataString();
		po2.SetName("po2");
		po2.SetValue("No Action");
		pn.PlaceList.add(po2);

		DataString NoAction = new DataString(); // constant value
		NoAction.SetName("No Action");
		NoAction.SetValue("No Action");
		pn.ConstantPlaceList.add(NoAction);

		DataString Increase = new DataString(); // constant value
		Increase.SetName("Increase");
		Increase.SetValue("Increase");
		pn.ConstantPlaceList.add(Increase);

		DataString Decrease = new DataString(); // constant value
		Decrease.SetName("Decrease");
		Decrease.SetValue("Decrease");
		pn.ConstantPlaceList.add(Decrease);

		// T0 ------------------------------------------------
		PetriTransition t0 = new PetriTransition(pn);
		t0.TransitionName = "T0";
		t0.InputPlaceName.add("h2");
		t0.InputPlaceName.add("hr2");
		t0.InputPlaceName.add("m12");
		t0.InputPlaceName.add("c2Previous");
		t0.InputPlaceName.add("po2");

		// -------Sub guard 1---------
		Condition T0Ct1 = new Condition(t0, "h2", TransitionCondition.NotNull);
		Condition T0Ct2 = new Condition(t0, "hr2", TransitionCondition.NotNull);
		Condition T0Ct3 = new Condition(t0, "c2Previous", TransitionCondition.NotNull);
		Condition T0Ct4 = new Condition(t0, "po2", TransitionCondition.NotNull);
		Condition T0Ct5 = new Condition(t0, "hr2", TransitionCondition.MoreThan, "h2");
		Condition T0Ct6 = new Condition(t0, "m12", TransitionCondition.Equal, "Decrease");

		T0Ct5.SetNextCondition(LogicConnector.AND, T0Ct6);
		T0Ct4.SetNextCondition(LogicConnector.AND, T0Ct5);
		T0Ct3.SetNextCondition(LogicConnector.AND, T0Ct4);
		T0Ct2.SetNextCondition(LogicConnector.AND, T0Ct3);
		T0Ct1.SetNextCondition(LogicConnector.AND, T0Ct2);

		GuardMapping grdT0 = new GuardMapping();
		grdT0.condition = T0Ct1;
		grdT0.Activations.add(new Activation(t0, "Decrease", TransitionOperation.Move, "dc2"));
		grdT0.Activations.add(new Activation(t0, "Decrease", TransitionOperation.Move, "c2"));
		grdT0.Activations.add(new Activation(t0, "Decrease", TransitionOperation.Move, "c2Previous"));
		grdT0.Activations.add(new Activation(t0, "hr2", TransitionOperation.Move, "hr2"));
		
		t0.GuardMappingList.add(grdT0);

		// -------Sub guard 2---------
		Condition T0Ct7 = new Condition(t0, "h2", TransitionCondition.NotNull);
		Condition T0Ct8 = new Condition(t0, "hr2", TransitionCondition.NotNull);
		Condition T0Ct9 = new Condition(t0, "c2Previous", TransitionCondition.NotNull);
		Condition T0Ct10 = new Condition(t0, "po2", TransitionCondition.NotNull);
		Condition T0Ct11 = new Condition(t0, "hr2", TransitionCondition.MoreThan, "h2");
		Condition T0Ct12 = new Condition(t0, "m12", TransitionCondition.Equal, "Increase");

		T0Ct11.SetNextCondition(LogicConnector.AND, T0Ct12);
		T0Ct10.SetNextCondition(LogicConnector.AND, T0Ct11);
		T0Ct9.SetNextCondition(LogicConnector.AND, T0Ct10);
		T0Ct8.SetNextCondition(LogicConnector.AND, T0Ct9);
		T0Ct7.SetNextCondition(LogicConnector.AND, T0Ct8);

		GuardMapping grdT02 = new GuardMapping();
		grdT02.condition = T0Ct7;
		grdT02.Activations.add(new Activation(t0, "No Action", TransitionOperation.Move, "dc2"));
		grdT02.Activations.add(new Activation(t0, "No Action", TransitionOperation.Move, "c2"));
		grdT02.Activations.add(new Activation(t0, "No Action", TransitionOperation.Move, "c2Previous"));
		grdT02.Activations.add(new Activation(t0, "hr2", TransitionOperation.Move, "hr2"));
		
		t0.GuardMappingList.add(grdT02);

		// -------Sub guard 3---------
		Condition T0Ct13 = new Condition(t0, "h2", TransitionCondition.NotNull);
		Condition T0Ct14 = new Condition(t0, "hr2", TransitionCondition.NotNull);
		Condition T0Ct15 = new Condition(t0, "c2Previous", TransitionCondition.NotNull);
		Condition T0Ct16 = new Condition(t0, "po2", TransitionCondition.NotNull);
		Condition T0Ct17 = new Condition(t0, "hr2", TransitionCondition.LessThan, "h2");

		T0Ct16.SetNextCondition(LogicConnector.AND, T0Ct17);
		T0Ct15.SetNextCondition(LogicConnector.AND, T0Ct16);
		T0Ct14.SetNextCondition(LogicConnector.AND, T0Ct15);
		T0Ct13.SetNextCondition(LogicConnector.AND, T0Ct14);

		GuardMapping grdT03 = new GuardMapping();
		grdT03.condition = T0Ct13;
		grdT03.Activations.add(new Activation(t0, "Increase", TransitionOperation.Move, "dc2"));
		grdT03.Activations.add(new Activation(t0, "Increase", TransitionOperation.Move, "c2"));
		grdT03.Activations.add(new Activation(t0, "Increase", TransitionOperation.Move, "c2Previous"));
		grdT03.Activations.add(new Activation(t0, "hr2", TransitionOperation.Move, "hr2"));
		
		t0.GuardMappingList.add(grdT03);

		// -------Sub guard 4---------
		Condition T0Ct18 = new Condition(t0, "h2", TransitionCondition.NotNull);
		Condition T0Ct19 = new Condition(t0, "hr2", TransitionCondition.NotNull);
		Condition T0Ct20 = new Condition(t0, "c2Previous", TransitionCondition.NotNull);
		Condition T0Ct21 = new Condition(t0, "po2", TransitionCondition.NotNull);
		Condition T0Ct22 = new Condition(t0, "hr2", TransitionCondition.Equal, "h2");
		Condition T0Ct23 = new Condition(t0, "m12", TransitionCondition.Equal, "Decrease");

		T0Ct22.SetNextCondition(LogicConnector.AND, T0Ct23);
		T0Ct21.SetNextCondition(LogicConnector.AND, T0Ct22);
		T0Ct20.SetNextCondition(LogicConnector.AND, T0Ct21);
		T0Ct19.SetNextCondition(LogicConnector.AND, T0Ct20);
		T0Ct18.SetNextCondition(LogicConnector.AND, T0Ct19);

		GuardMapping grdT04 = new GuardMapping();
		grdT04.condition = T0Ct18;
		grdT04.Activations.add(new Activation(t0, "No Action", TransitionOperation.Move, "dc2"));
		grdT04.Activations.add(new Activation(t0, "No Action", TransitionOperation.Move, "c2"));
		grdT04.Activations.add(new Activation(t0, "No Action", TransitionOperation.Move, "c2Previous"));
		grdT04.Activations.add(new Activation(t0, "hr2", TransitionOperation.Move, "hr2"));
		
		t0.GuardMappingList.add(grdT04);

		// -------Sub guard 5---------
		Condition T0Ct24 = new Condition(t0, "h2", TransitionCondition.NotNull);
		Condition T0Ct25 = new Condition(t0, "hr2", TransitionCondition.NotNull);
		Condition T0Ct26 = new Condition(t0, "c2Previous", TransitionCondition.NotNull);
		Condition T0Ct27 = new Condition(t0, "po2", TransitionCondition.NotNull);
		Condition T0Ct28 = new Condition(t0, "hr2", TransitionCondition.Equal, "h2");
		Condition T0Ct29 = new Condition(t0, "m12", TransitionCondition.Equal, "Increase");

		T0Ct28.SetNextCondition(LogicConnector.AND, T0Ct29);
		T0Ct27.SetNextCondition(LogicConnector.AND, T0Ct28);
		T0Ct26.SetNextCondition(LogicConnector.AND, T0Ct27);
		T0Ct25.SetNextCondition(LogicConnector.AND, T0Ct26);
		T0Ct24.SetNextCondition(LogicConnector.AND, T0Ct25);

		GuardMapping grdT05 = new GuardMapping();
		grdT05.condition = T0Ct24;
		grdT05.Activations.add(new Activation(t0, "Increase", TransitionOperation.Move, "dc2"));
		grdT05.Activations.add(new Activation(t0, "Increase", TransitionOperation.Move, "c2"));
		grdT05.Activations.add(new Activation(t0, "Increase", TransitionOperation.Move, "c2Previous"));
		grdT05.Activations.add(new Activation(t0, "hr2", TransitionOperation.Move, "hr2"));
		
		t0.GuardMappingList.add(grdT05);

		t0.Delay = 0;
		pn.Transitions.add(t0);

		// T1 ------------------------------------------------
		PetriTransition t1 = new PetriTransition(pn);
		t1.TransitionName = "T1";
		t1.InputPlaceName.add("c2");

		Condition T1Ct1 = new Condition(t1, "c2", TransitionCondition.NotNull);

		GuardMapping grdT1 = new GuardMapping();
		grdT1.condition = T1Ct1;
		grdT1.Activations.add(new Activation(t1, "c2", TransitionOperation.Move, "po"));
		grdT1.Activations.add(new Activation(t1, "c2", TransitionOperation.SendOverNetwork, "m13"));

		t1.GuardMappingList.add(grdT1);
		
		t1.Delay = 0;
		pn.Transitions.add(t1);

		// T2 ------------------------------------------------
		PetriTransition t2 = new PetriTransition(pn);
		t2.TransitionName = "T2";
		t2.InputPlaceName.add("po");

		Condition T2Ct1 = new Condition(t2, "po", TransitionCondition.NotNull);

		GuardMapping grdT2 = new GuardMapping();
		grdT2.condition = T2Ct1;
		grdT2.Activations.add(new Activation(t2, "po", TransitionOperation.Move, "po2"));

		t2.GuardMappingList.add(grdT2);
		
		t2.Delay = 1;
		pn.Transitions.add(t2);

		// -------Run OER-TPN

		System.out.println("Controller 2 started \n ------------------------------");

		pn.Delay = 2000;

		PetriNetWindow frame = new PetriNetWindow(false);
		frame.petriNet = pn;
		frame.setVisible(true);

	}
}

