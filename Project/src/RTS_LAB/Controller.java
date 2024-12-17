package RTS_LAB;

import Components.Activation;
import Components.Condition;
import Components.GuardMapping;
import Components.PetriNet;
import Components.PetriNetWindow;
import Components.PetriTransition;
import DataObjects.DataInteger;
import DataObjects.DataTransfer;
import DataOnly.TransferOperation;
import Enumerations.LogicConnector;
import Enumerations.TransitionCondition;
import Enumerations.TransitionOperation;

public class Controller {
	public static void main(String[] args) {
		PetriNet pn = new PetriNet();
		pn.PetriNetName = "Controller Petri";
		pn.NetworkPort = 1080;

		DataInteger p_i1 = new DataInteger();
		p_i1.SetName("p_i1");
		pn.PlaceList.add(p_i1);

		DataInteger p_i2 = new DataInteger();
		p_i2.SetName("p_i2");
		pn.PlaceList.add(p_i2);

		DataInteger p_1 = new DataInteger();
		p_1.SetName("p_1");
		p_1.SetValue(0);
		pn.PlaceList.add(p_1);

		DataInteger p_2 = new DataInteger();
		p_2.SetName("p_2");
		pn.PlaceList.add(p_2);

		DataInteger p_o1 = new DataInteger();
		p_o1.SetName("p_o1");
		pn.PlaceList.add(p_o1);

		DataTransfer p_o2 = new DataTransfer();
		p_o2.SetName("p_o2");
		p_o2.Value = new TransferOperation("localhost", "1081", "p_i");
		pn.PlaceList.add(p_o2);

		DataInteger Halt = new DataInteger();
		Halt.SetName("Halt");
		Halt.SetValue(0);
		pn.ConstantPlaceList.add(Halt);

		DataInteger Right = new DataInteger();
		Right.SetName("Right");
		Right.SetValue(1);
		pn.ConstantPlaceList.add(Right);

		DataInteger Left = new DataInteger();
		Left.SetName("Left");
		Left.SetValue(-1);
		pn.ConstantPlaceList.add(Left);

		// ----------------------------------T_1-----------------------------------------
		PetriTransition t_1 = new PetriTransition(pn);
		t_1.TransitionName = "t_1";
		t_1.InputPlaceName.add("p_i1");
		t_1.InputPlaceName.add("p_1");

		// ---------grd1--------------------------
		Condition T1Ct1 = new Condition(t_1, "p_i1", TransitionCondition.NotNull);
		Condition T1Ct2 = new Condition(t_1, "p_1", TransitionCondition.NotNull);
		Condition T1Ct3 = new Condition(t_1, "p_i1", TransitionCondition.Equal, "p_1");

		T1Ct2.SetNextCondition(LogicConnector.AND, T1Ct3);
		T1Ct1.SetNextCondition(LogicConnector.AND, T1Ct2);

		GuardMapping grdT11 = new GuardMapping();
		grdT11.condition = T1Ct1;

		grdT11.Activations.add(new Activation(t_1, "Halt", TransitionOperation.Copy, "p_2"));

		t_1.GuardMappingList.add(grdT11);

		// ---------grd2-------------------------------
		Condition T1Ct4 = new Condition(t_1, "p_i1", TransitionCondition.NotNull);
		Condition T1Ct5 = new Condition(t_1, "p_1", TransitionCondition.NotNull);
		Condition T1Ct6 = new Condition(t_1, "p_i1", TransitionCondition.MoreThan, "p_1");

		T1Ct5.SetNextCondition(LogicConnector.AND, T1Ct6);
		T1Ct4.SetNextCondition(LogicConnector.AND, T1Ct5);

		GuardMapping grdT12 = new GuardMapping();
		grdT12.condition = T1Ct4;

		grdT12.Activations.add(new Activation(t_1, "Right", TransitionOperation.Copy, "p_2"));
		grdT12.Activations.add(new Activation(t_1, "p_i1", TransitionOperation.Move, "p_i1"));
		t_1.GuardMappingList.add(grdT12);

		// -----------grd3------------------------------------
		Condition T1Ct7 = new Condition(t_1, "p_i1", TransitionCondition.NotNull);
		Condition T1Ct8 = new Condition(t_1, "p_1", TransitionCondition.NotNull);
		Condition T1Ct9 = new Condition(t_1, "p_i1", TransitionCondition.LessThan, "p_1");

		T1Ct8.SetNextCondition(LogicConnector.AND, T1Ct9);
		T1Ct7.SetNextCondition(LogicConnector.AND, T1Ct8);

		GuardMapping grdT13 = new GuardMapping();
		grdT13.condition = T1Ct7;

		grdT13.Activations.add(new Activation(t_1, "Left", TransitionOperation.Copy, "p_2"));
		grdT13.Activations.add(new Activation(t_1, "p_i1", TransitionOperation.Move, "p_i1"));
		t_1.GuardMappingList.add(grdT13);

		t_1.Delay = 0;
		pn.Transitions.add(t_1);

		// ----------------------------------T_2-----------------------------------------
		PetriTransition t_2 = new PetriTransition(pn);
		t_2.TransitionName = "t_2";
		t_2.InputPlaceName.add("p_2");
		t_2.InputPlaceName.add("p_i2");

		Condition T2Ct1 = new Condition(t_2, "p_2", TransitionCondition.NotNull);
		Condition T2Ct2 = new Condition(t_2, "p_i2", TransitionCondition.NotNull);
		T2Ct1.SetNextCondition(LogicConnector.AND, T2Ct2);

		GuardMapping grdT2 = new GuardMapping();
		grdT2.condition = T2Ct1;

		grdT2.Activations.add(new Activation(t_2, "p_i2", TransitionOperation.Move, "p_1"));
		grdT2.Activations.add(new Activation(t_2, "p_i2", TransitionOperation.Move, "p_o1"));
		//Send to the Supervisor:
		//P_o1 must be turned to a data transfer that is linked to the supervisor
		t_2.GuardMappingList.add(grdT2);

		t_2.Delay = 0;
		pn.Transitions.add(t_2);

		// --------------------T_o2-----------------------------------------
		PetriTransition t_o2 = new PetriTransition(pn);
		t_o2.TransitionName = "t_o2";
		t_o2.InputPlaceName.add("p_2");

		Condition T3Ct1 = new Condition(t_o2, "p_2", TransitionCondition.NotNull);
		GuardMapping grdT3 = new GuardMapping();
		grdT3.condition = T3Ct1;

		grdT3.Activations.add(new Activation(t_o2, "p_2", TransitionOperation.SendOverNetwork, "p_o2"));
		grdT3.Activations.add(new Activation(t_o2, "p_2", TransitionOperation.Move, "p_2"));
		t_o2.GuardMappingList.add(grdT3);

		t_o2.Delay = 0;
		pn.Transitions.add(t_o2);

		
		
		System.out.println("Controller started \n ------------------------------");
		pn.Delay = 3000;
		// pn.Start();

		PetriNetWindow frame = new PetriNetWindow(false);
		frame.petriNet = pn;
		frame.setVisible(true);

	}
}
