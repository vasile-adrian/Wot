package DCS_UVS;

import Components.Activation;
import Components.Condition;
import Components.GuardMapping;
import Components.PetriNet;
import Components.PetriNetWindow;
import Components.PetriTransition;
import DataObjects.DataCar;
import DataObjects.DataCarQueue;
import Enumerations.LogicConnector;
import Enumerations.TransitionCondition;
import Enumerations.TransitionOperation;

public class TestSquare {
	public static void main(String[] args) {

		PetriNet pn = new PetriNet();
		pn.PetriNetName = "Main Petri";
		pn.NetworkPort = 1081;
		
		DataCar p1 = new DataCar();
		p1.SetName("P1");
		pn.PlaceList.add(p1);

		DataCar p2 = new DataCar();
		p2.SetName("P2");
		pn.PlaceList.add(p2);
		
		DataCarQueue sq = new DataCarQueue();
		sq.SetName("SQ");
		sq.Value.Size = 3;
		pn.PlaceList.add(sq);
		

		DataCar p3 = new DataCar();
		p3.SetName("P3");
		pn.PlaceList.add(p3);
		
		// T1 ------------------------------------------------
		PetriTransition t1 = new PetriTransition(pn);
		t1.TransitionName = "T1";
		t1.InputPlaceName.add("P1");

		Condition T1Ct1 = new Condition(t1, "P1", TransitionCondition.NotNull);
		Condition T1Ct2 = new Condition(t1, "SQ", TransitionCondition.CanAddCars);
		T1Ct1.SetNextCondition(LogicConnector.AND, T1Ct2);

		GuardMapping grdT1 = new GuardMapping();
		grdT1.condition= T1Ct1;
		grdT1.Activations.add(new Activation(t1, "P1", TransitionOperation.AddElement, "SQ"));
		t1.GuardMappingList.add(grdT1);
		
		t1.Delay = 0;
		pn.Transitions.add(t1);

		// T2 ------------------------------------------------
		PetriTransition t2 = new PetriTransition(pn);
		t2.TransitionName = "T2";
		t2.InputPlaceName.add("SQ");

		Condition T2Ct1 = new Condition(t2, "SQ", TransitionCondition.HaveCarForMe);

		GuardMapping grdT2 = new GuardMapping();
		grdT2.condition= T2Ct1;
		grdT2.Activations.add(new Activation(t2, "SQ", TransitionOperation.PopElementWithTarget, "P2"));
		t2.GuardMappingList.add(grdT2);
		
		t2.Delay = 0;
		pn.Transitions.add(t2);
		
		// T3 ------------------------------------------------
		PetriTransition t3 = new PetriTransition(pn);
		t3.TransitionName = "T3";
		t3.InputPlaceName.add("SQ");

		Condition T3Ct1 = new Condition(t3, "SQ", TransitionCondition.HaveCarForMe);

		GuardMapping grdT3 = new GuardMapping();
		grdT3.condition= T3Ct1;
		grdT3.Activations.add(new Activation(t3, "SQ", TransitionOperation.PopElementWithTarget, "P3"));
		t3.GuardMappingList.add(grdT3);
				
		t3.Delay = 0;
		pn.Transitions.add(t3);

		System.out.println("Square started \n ------------------------------");
		pn.Delay = 2000;
		//pn.Start();
		
		PetriNetWindow frame = new PetriNetWindow(false);
		frame.petriNet = pn;
		frame.setVisible(true);

	}
}
