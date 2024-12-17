package DCS_UVS;

import Components.Activation;
import Components.Condition;
import Components.GuardMapping;
import Components.PetriNet;
import Components.PetriNetWindow;
import Components.PetriTransition;
import DataObjects.DataInteger;
import DataObjects.DataString;
import Enumerations.LogicConnector;
import Enumerations.TransitionCondition;
import Enumerations.TransitionOperation;

public class TestDelay {
	    public static void main(String[] args) {
	        PetriNet pn = new PetriNet();
	        pn.PetriNetName = "Delay Transition Petri";
	        pn.NetworkPort = 1080;

	        DataString p1 = new DataString();
	        p1.SetName("P1");
	        p1.SetValue("signal");
	        pn.PlaceList.add(p1);
	        
	        DataString p2 = new DataString();
	        p2.SetName("P2");
	        pn.PlaceList.add(p2);
	        
	        DataString in = new DataString();
	        in.SetName("in");
	        pn.PlaceList.add(in);
	        
	        DataInteger Five = new DataInteger();
	        Five.SetName("Five");
	        Five.SetValue(5);
	        pn.ConstantPlaceList.add(Five);
	        
	        DataInteger One = new DataInteger();
	        One.SetName("One");
	        One.SetValue(1);
	        pn.ConstantPlaceList.add(One);
	        
	        //T1-----------------------------------------------------------------
	        PetriTransition t1 = new PetriTransition(pn);
	        t1.TransitionName = "t1";
	        t1.InputPlaceName.add("P1");
	        t1.InputPlaceName.add("in");

	        Condition T1Ct1 = new Condition(t1,"P1", TransitionCondition.NotNull);
	        Condition T1Ct3 = new Condition(t1,"in",TransitionCondition.NotNull);
	        T1Ct1.SetNextCondition(LogicConnector.AND, T1Ct3);

	        GuardMapping grdT1 = new GuardMapping();
	        grdT1.condition = T1Ct1;
	        grdT1.Activations.add(new Activation(t1, "P1", TransitionOperation.Move, "P2"));
	        grdT1.Activations.add(new Activation(t1, "Five", TransitionOperation.DynamicDelay,""));
	        t1.GuardMappingList.add(grdT1);
	        
	        Condition T1Ct4 = new Condition(t1,"P1", TransitionCondition.NotNull);
	        Condition T1Ct6 = new Condition(t1,"in",TransitionCondition.IsNull);
	        T1Ct4.SetNextCondition(LogicConnector.AND, T1Ct6);

	        GuardMapping grdT12 = new GuardMapping();
	        grdT12.condition = T1Ct4;
	        grdT12.Activations.add(new Activation(t1, "P1", TransitionOperation.Move, "P2"));
	        grdT12.Activations.add(new Activation(t1, "One", TransitionOperation.DynamicDelay,""));
	        t1.GuardMappingList.add(grdT12);

	        t1.Delay = 0;
	        pn.Transitions.add(t1);
	        
	        //T2-------------------------------------------------------------------------------

	        PetriTransition t2 = new PetriTransition(pn);
	        t2.TransitionName = "t2";
	        t2.InputPlaceName.add("P2");

	        Condition T2Ct1 = new Condition(t2, "P2", TransitionCondition.NotNull);

	        GuardMapping grdT2 = new GuardMapping();
	        grdT2.condition = T2Ct1;
	        grdT2.Activations.add(new Activation(t2, "P2", TransitionOperation.Move, "P1"));
	        t2.GuardMappingList.add(grdT2);

	        t2.Delay = 0;
	        pn.Transitions.add(t2);
	        
	        //-------------------------------------------------------------------------------
	        System.out.println("Dynamic Delay started \n ------------------------------");
	        pn.Delay = 2000;
	        //pn.Start();

	        PetriNetWindow frame = new PetriNetWindow(false);
	        frame.petriNet = pn;
	        frame.setVisible(true);
	    }
}
