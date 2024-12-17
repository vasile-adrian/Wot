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

public class Robot {
	public static void main(String[] args) {
		PetriNet pn = new PetriNet();
		pn.PetriNetName = "Robot Petri";
		pn.NetworkPort = 1081;

		DataInteger p_i = new DataInteger();
		p_i.SetName("p_i");
		pn.PlaceList.add(p_i);

		DataTransfer p_o = new DataTransfer();
		p_o.SetName("p_o");
		p_o.Value = new TransferOperation("localhost", "1080", "p_i2");
		pn.PlaceList.add(p_o);

		DataInteger p_0 = new DataInteger();
		p_0.SetName("p_0");
		p_0.SetValue(0);
		pn.PlaceList.add(p_0);

		DataInteger p_1 = new DataInteger();
		p_1.SetName("p_1");
		pn.PlaceList.add(p_1);

		// ----------------------------------T_1-----------------------------------------
		PetriTransition t_1 = new PetriTransition(pn);
		t_1.TransitionName = "t_1";
		t_1.InputPlaceName.add("p_i");
		t_1.InputPlaceName.add("p_0");

		Condition T1Ct1 = new Condition(t_1, "p_i", TransitionCondition.NotNull);
		Condition T1Ct2 = new Condition(t_1, "p_0", TransitionCondition.NotNull);
		T1Ct1.SetNextCondition(LogicConnector.AND, T1Ct2);

		GuardMapping grdT1 = new GuardMapping();
		grdT1.condition = T1Ct1;

		grdT1.Activations.add(new Activation(t_1, t_1.InputPlaceName, TransitionOperation.Add, "p_1"));
		t_1.GuardMappingList.add(grdT1);

		t_1.Delay = 0;
		pn.Transitions.add(t_1);

		// ----------------------------------T_2-----------------------------------------
		PetriTransition t_2 = new PetriTransition(pn);
		t_2.TransitionName = "t_2";
		t_2.InputPlaceName.add("p_1");

		Condition T2Ct1 = new Condition(t_2, "p_1", TransitionCondition.NotNull);

		GuardMapping grdT2 = new GuardMapping();
		grdT2.condition = T2Ct1;

		grdT2.Activations.add(new Activation(t_2, "p_1", TransitionOperation.SendOverNetwork, "p_o"));
		grdT2.Activations.add(new Activation(t_2, "p_1", TransitionOperation.Move, "p_0"));
		t_2.GuardMappingList.add(grdT2);

		t_2.Delay = 5;
		pn.Transitions.add(t_2);

		System.out.println("Robot started \n ------------------------------");
		pn.Delay = 3000;
		// pn.Start();

		PetriNetWindow frame = new PetriNetWindow(false);
		frame.petriNet = pn;
		frame.setVisible(true);

	}
}
