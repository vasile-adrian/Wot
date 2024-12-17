package HeatPipe;

import java.util.ArrayList;
import Components.Activation;
import Components.Condition;
import Components.GuardMapping;
import Components.PetriNet;
import Components.PetriNetWindow;
import Components.PetriTransition;
import DataObjects.DataFuzzy;
import DataOnly.FLRS;
import DataOnly.FV;
import DataOnly.Fuzzy;
import DataOnly.PlaceNameWithWeight;
import Enumerations.FZ;
import Enumerations.LogicConnector;
import Enumerations.TransitionCondition;
import Enumerations.TransitionOperation;

public class HeatPipe {
	public static void main(String[] args) {

		FLRS flrs2x1 = new FLRS(new FV(FZ.PL), new FV(FZ.PM), new FV(FZ.ZR), new FV(FZ.NL), new FV(FZ.ZR),
								new FV(FZ.PL), new FV(FZ.NM), new FV(FZ.PL), new FV(FZ.PL), new FV(FZ.NM), 
								new FV(FZ.NL), new FV(FZ.PL), new FV(FZ.ZR), new FV(FZ.ZR), new FV(FZ.PL), 
								new FV(FZ.ZR), new FV(FZ.ZR), new FV(FZ.NM), new FV(FZ.PM),	new FV(FZ.NL), 
								new FV(FZ.ZR), new FV(FZ.PM), new FV(FZ.ZR), new FV(FZ.NM), new FV(FZ.PL));

		flrs2x1.Print();

		FLRS flrs6_1x1 = new FLRS(new FV(FZ.PL), new FV(FZ.PM), new FV(FZ.ZR), new FV(FZ.NL), new FV(FZ.ZR));

		flrs6_1x1.Print();

		PetriNet pn = new PetriNet();
		pn.PetriNetName = "Main Petri";
		pn.NetworkPort = 1080;
		pn.NonBooking = true;
		DataFuzzy p0 = new DataFuzzy();
		p0.SetName("x0");
		p0.SetValue(new Fuzzy(0.0F)); // initial token
		pn.PlaceList.add(p0);

		DataFuzzy p1 = new DataFuzzy();
		p1.SetName("x1");
		pn.PlaceList.add(p1);

		DataFuzzy p2 = new DataFuzzy();
		p2.SetName("x2");
		p2.SetValue(new Fuzzy(0.2F)); // initial token
		pn.PlaceList.add(p2);

		DataFuzzy p3 = new DataFuzzy();
		p3.SetName("x3");
		pn.PlaceList.add(p3);

		DataFuzzy p4 = new DataFuzzy();
		p4.SetName("x4");
		pn.PlaceList.add(p4);

		DataFuzzy p5 = new DataFuzzy();
		p5.SetName("y1");
		pn.PlaceList.add(p5);

		DataFuzzy p6 = new DataFuzzy();
		p6.SetName("u");
		pn.PlaceList.add(p6);

		DataFuzzy p7 = new DataFuzzy();
		p7.SetName("v");
		p7.SetValue(0.28F);
		pn.PlaceList.add(p7);

		DataFuzzy p8 = new DataFuzzy();
		p8.SetName("y2");
		pn.PlaceList.add(p8);

		// T0 ------------------------------------------------
		PetriTransition t0 = new PetriTransition(pn);
		t0.TransitionName = "T0";
		t0.InputPlaceName.add("x0");
		t0.InputPlaceName.add("u");

		Condition T0Ct1 = new Condition(t0, "x0", TransitionCondition.NotNull);
		Condition T0Ct2 = new Condition(t0, "u", TransitionCondition.NotNull);
		T0Ct1.SetNextCondition(LogicConnector.AND, T0Ct2);

		GuardMapping grdT0 = new GuardMapping();
		grdT0.condition = T0Ct1;

		ArrayList<PlaceNameWithWeight> input0 = new ArrayList<>();
		input0.add(new PlaceNameWithWeight("xo", 1F));
		input0.add(new PlaceNameWithWeight("u", -1F));

		ArrayList<String> Output0 = new ArrayList<>();
		Output0.add("x1");

		grdT0.Activations.add(new Activation(t0, flrs2x1, input0, TransitionOperation.FLRS, Output0));
		grdT0.Activations.add(new Activation(t0, "x0", TransitionOperation.Move, "x0"));

		t0.GuardMappingList.add(grdT0);

		t0.Delay = 1;
		pn.Transitions.add(t0);

		// T1 ------------------------------------------------

		PetriTransition t1 = new PetriTransition(pn);
		t1.TransitionName = "T1";
		t1.InputPlaceName.add("x0");
		t1.InputPlaceName.add("x1");

		Condition T1Ct1 = new Condition(t1, "P1", TransitionCondition.NotNull);
		Condition T1Ct2 = new Condition(t1, "P2", TransitionCondition.NotNull);
		T1Ct1.SetNextCondition(LogicConnector.AND, T1Ct2);

		GuardMapping grdT1 = new GuardMapping();
		grdT1.condition = T1Ct1;

		ArrayList<PlaceNameWithWeight> input1 = new ArrayList<>();
		input1.add(new PlaceNameWithWeight("x0", 1F));
		input1.add(new PlaceNameWithWeight("x1", 1F));

		grdT1.Activations.add(new Activation(input1, TransitionOperation.Add_Fuzzy, "x0", t1));
		grdT1.Activations.add(new Activation(input1, TransitionOperation.Add_Fuzzy, "y1", t1));

		t1.GuardMappingList.add(grdT1);

		t1.Delay = 0;
		pn.Transitions.add(t1);

		// T2 ------------------------------------------------
		PetriTransition t2 = new PetriTransition(pn);
		t2.TransitionName = "T2";
		t2.InputPlaceName.add("x0");
		t2.InputPlaceName.add("x2");

		Condition T2Ct1 = new Condition(t2, "x0", TransitionCondition.NotNull);
		Condition T2Ct2 = new Condition(t2, "x2", TransitionCondition.NotNull);
		T2Ct1.SetNextCondition(LogicConnector.AND, T2Ct2);

		GuardMapping grdT2 = new GuardMapping();
		grdT2.condition = T2Ct1;

		ArrayList<PlaceNameWithWeight> input2 = new ArrayList<>();
		input2.add(new PlaceNameWithWeight("x0", 1F));
		input2.add(new PlaceNameWithWeight("x2", -1F));

		ArrayList<String> Output2 = new ArrayList<>();
		Output2.add("x3");

		grdT2.Activations.add(new Activation(t2, flrs2x1, input2, TransitionOperation.FLRS, Output2));
		grdT2.Activations.add(new Activation(t2, "x0", TransitionOperation.Move, "x0"));
		grdT2.Activations.add(new Activation(t2, "x2", TransitionOperation.Move, "x2"));

		t2.GuardMappingList.add(grdT2);

		t2.Delay = 1;
		pn.Transitions.add(t2);

		// T3 ------------------------------------------------

		PetriTransition t3 = new PetriTransition(pn);
		t3.TransitionName = "T3";
		t3.InputPlaceName.add("x0");
		t3.InputPlaceName.add("x2");
		t3.InputPlaceName.add("x3");

		Condition T3Ct1 = new Condition(t3, "x0", TransitionCondition.NotNull);
		Condition T3Ct2 = new Condition(t3, "x2", TransitionCondition.NotNull);
		Condition T3Ct3 = new Condition(t3, "x3", TransitionCondition.NotNull);
		T3Ct2.SetNextCondition(LogicConnector.AND, T3Ct3);
		T3Ct1.SetNextCondition(LogicConnector.AND, T3Ct2);

		GuardMapping grdT3 = new GuardMapping();
		grdT3.condition = T3Ct1;

		ArrayList<PlaceNameWithWeight> input31 = new ArrayList<>();
		input31.add(new PlaceNameWithWeight("x0", 1F));
		input31.add(new PlaceNameWithWeight("x3", 1F));

		ArrayList<PlaceNameWithWeight> input32 = new ArrayList<>();
		input32.add(new PlaceNameWithWeight("x2", 1F));
		input32.add(new PlaceNameWithWeight("x3", 1F));

		grdT3.Activations.add(new Activation(input31, TransitionOperation.Add_Fuzzy, "x0", t3));
		grdT3.Activations.add(new Activation(input32, TransitionOperation.Sub_Fuzzy, "x2", t3));

		t3.GuardMappingList.add(grdT3);

		t3.Delay = 0;
		pn.Transitions.add(t3);

		// T4 ------------------------------------------------
		PetriTransition t4 = new PetriTransition(pn);
		t4.TransitionName = "T4";
		t4.InputPlaceName.add("x2");
		t4.InputPlaceName.add("v");

		Condition T4Ct1 = new Condition(t4, "x2", TransitionCondition.NotNull);
		Condition T4Ct2 = new Condition(t4, "v", TransitionCondition.NotNull);
		T4Ct1.SetNextCondition(LogicConnector.AND, T4Ct2);

		GuardMapping grdT4 = new GuardMapping();
		grdT4.condition = T4Ct1;

		ArrayList<PlaceNameWithWeight> input4 = new ArrayList<>();
		input4.add(new PlaceNameWithWeight("x2", 1F));
		input4.add(new PlaceNameWithWeight("v", -1F));

		ArrayList<String> Output4 = new ArrayList<>();
		Output4.add("x4");

		grdT4.Activations.add(new Activation(t4, flrs2x1, input4, TransitionOperation.FLRS, Output4));
		grdT4.Activations.add(new Activation(t4, "x2", TransitionOperation.Move, "x2"));

		t4.GuardMappingList.add(grdT4);

		t4.Delay = 1;
		pn.Transitions.add(t4);

		// T5 ------------------------------------------------

		PetriTransition t5 = new PetriTransition(pn);
		t5.TransitionName = "T5";
		t5.InputPlaceName.add("x2");
		t5.InputPlaceName.add("x4");

		Condition T5Ct1 = new Condition(t5, "x2", TransitionCondition.NotNull);
		Condition T5Ct2 = new Condition(t5, "x4", TransitionCondition.NotNull);
		T5Ct1.SetNextCondition(LogicConnector.AND, T5Ct2);

		GuardMapping grdT5 = new GuardMapping();
		grdT5.condition = T5Ct1;

		ArrayList<PlaceNameWithWeight> input5 = new ArrayList<>();
		input5.add(new PlaceNameWithWeight("x2", 1F));
		input5.add(new PlaceNameWithWeight("x4", 1F));

		grdT5.Activations.add(new Activation(input5, TransitionOperation.Add_Fuzzy, "x2", t5));

		t5.GuardMappingList.add(grdT5);

		t5.Delay = 0;
		pn.Transitions.add(t5);

		// T6 ------------------------------------------------
		PetriTransition t6 = new PetriTransition(pn);
		t6.TransitionName = "T6";
		t6.InputPlaceName.add("x2");

		Condition T6Ct1 = new Condition(t6, "x2", TransitionCondition.NotNull);

		GuardMapping grdT6 = new GuardMapping();
		grdT6.condition = T6Ct1;

		ArrayList<PlaceNameWithWeight> input6 = new ArrayList<>();
		input6.add(new PlaceNameWithWeight("x2", 1F));

		ArrayList<String> Output6 = new ArrayList<>();
		Output6.add("y2");

		grdT6.Activations.add(new Activation(t6, flrs6_1x1, input6, TransitionOperation.FLRS, Output6));
		grdT6.Activations.add(new Activation(t6, "x2", TransitionOperation.Move, "x2"));

		t6.GuardMappingList.add(grdT6);

		t6.Delay = 1;
		pn.Transitions.add(t6);

		// Start FLETPN -------------------------------------------

		System.out.println("Heat Pipe started \n ------------------------------");
		pn.Delay = 0; //tic delay
		pn.Start(); //to start the consule only

//      To start the graphics window--------------
//		PetriNetWindow frame = new PetriNetWindow(false);
//		frame.petriNet = pn;
//		frame.setVisible(true);
	}
}
