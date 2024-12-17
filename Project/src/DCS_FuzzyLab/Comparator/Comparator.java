package DCS_FuzzyLab.Comparator;

import java.io.FileNotFoundException;
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
import DataOnly.FuzzyVector;
import DataOnly.PlaceNameWithWeight;
import Enumerations.FZ;
import Enumerations.LogicConnector;
import Enumerations.TransitionCondition;
import Enumerations.TransitionOperation;

public class Comparator {
	public static void main (String[]args) throws FileNotFoundException {
	PetriNet pn = new PetriNet();
	pn.PetriNetName = "Comparator";
	pn.NetworkPort = 1081;
	
	FLRS differentiator = new FLRS(new FV(FZ.ZR), new FV(FZ.NM), new FV(FZ.NL), new FV(FZ.NL),new FV(FZ.NL), 
								   new FV(FZ.PM), new FV(FZ.ZR), new FV(FZ.NM), new FV(FZ.NL), new FV(FZ.NL), 
								   new FV(FZ.PL), new FV(FZ.PM), new FV(FZ.ZR), new FV(FZ.NM), new FV(FZ.NL), 
								   new FV(FZ.PL), new FV(FZ.PL), new FV(FZ.PM), new FV(FZ.ZR), new FV(FZ.NM),
								   new FV(FZ.PL), new FV(FZ.PL), new FV(FZ.PL), new FV(FZ.PM),	new FV(FZ.ZR));
	
	FLRS separator = new FLRS(new FV(FZ.NL,FZ.FF), new FV(FZ.NL,FZ.FF), new FV(FZ.FF,FZ.FF), new FV(FZ.FF,FZ.PL),new FV(FZ.FF,FZ.PL));

	differentiator.Print();
	separator.Print();
	
	pn.SetInputFile("/home/adrian/Documents/DCS_Lab/All_Petri_FW/All_Petri_FW/src/DCS_FuzzyLab/Comparator/comparator.txt");
	
	DataFuzzy p0 = new DataFuzzy();
	p0.SetName("P0");
//	p0.SetValue(new Fuzzy(0.0F));
	pn.PlaceList.add(p0);
	
	DataFuzzy p1 = new DataFuzzy();
	p1.SetName("P1");
//	p1.SetValue(new Fuzzy(0.0F));
	pn.PlaceList.add(p1);

	DataFuzzy p2 = new DataFuzzy();
	p2.SetName("P2");
	pn.PlaceList.add(p2);

	DataFuzzy p3 = new DataFuzzy();
	p3.SetName("P3");
	pn.PlaceList.add(p3);

	DataFuzzy p4 = new DataFuzzy();
	p4.SetName("P4");
	pn.PlaceList.add(p4);
	
	DataFuzzy c1 = new DataFuzzy();
	c1.SetName("NLToken");
	c1.SetValue(new Fuzzy(-1.0F));
	pn.ConstantPlaceList.add(c1);
	
	DataFuzzy c2 = new DataFuzzy();
	c2.SetName("PLToken");
	c2.SetValue(new Fuzzy(1.0F));
	pn.ConstantPlaceList.add(c2);
	
	DataFuzzy p5 = new DataFuzzy();
	p5.SetName("P5");
	pn.PlaceList.add(p5);
	
	DataFuzzy p6 = new DataFuzzy();
	p6.SetName("P6");
	pn.PlaceList.add(p6);
	
	// T0 ------------------------------------------------
			PetriTransition t0 = new PetriTransition(pn);
			t0.TransitionName = "T0";
			t0.InputPlaceName.add("P0");
			t0.InputPlaceName.add("P1");

			Condition T0Ct1 = new Condition(t0, "P0", TransitionCondition.NotNull);
			Condition T0Ct2 = new Condition(t0, "P1", TransitionCondition.NotNull);
			T0Ct1.SetNextCondition(LogicConnector.AND, T0Ct2);

			GuardMapping grdT0 = new GuardMapping();
			grdT0.condition = T0Ct1;

			ArrayList<PlaceNameWithWeight> input0 = new ArrayList<>();
			input0.add(new PlaceNameWithWeight("P0", 1F));
			input0.add(new PlaceNameWithWeight("P1", 1F));

			ArrayList<String> Output0 = new ArrayList<>();
			Output0.add("P2");
			

			grdT0.Activations.add(new Activation(t0, differentiator, input0, TransitionOperation.FLRS, Output0));
			
			t0.GuardMappingList.add(grdT0);

			t0.Delay = 0;
			pn.Transitions.add(t0);
			
			
			// T1 ------------------------------------------------
			PetriTransition t1 = new PetriTransition(pn);
			t1.TransitionName = "T1";
			t1.InputPlaceName.add("P2");

			Condition T1Ct1 = new Condition(t1, "P2", TransitionCondition.NotNull);

			GuardMapping grdT1 = new GuardMapping();
			grdT1.condition = T1Ct1;

			ArrayList<PlaceNameWithWeight> input1 = new ArrayList<>();
			input1.add(new PlaceNameWithWeight("P2", 1F));

			
			ArrayList<String> Output1 = new ArrayList<>();
			Output1.add("P3");
			Output1.add("P4");

			
			grdT1.Activations.add(new Activation(t1, separator, input1, TransitionOperation.FLRS, Output1));
			
			t1.GuardMappingList.add(grdT1);

			t1.Delay = 0;
			pn.Transitions.add(t1);
			
			// tOut ------------------------------------------------
			PetriTransition tOut = new PetriTransition(pn);
			tOut.TransitionName = "TOut";
			tOut.InputPlaceName.add("P3");
			tOut.InputPlaceName.add("P4");

			Condition TOutCt1 = new Condition(tOut, "P3", TransitionCondition.NotNull);

			GuardMapping grdtOut1 = new GuardMapping();
			grdtOut1.condition = TOutCt1;
			
			grdtOut1.Activations.add(new Activation(tOut, "NLToken", TransitionOperation.Move, "P6"));
			grdtOut1.Activations.add(new Activation(tOut, "", TransitionOperation.MakeNull, "P5")); //to consume the token from the previous tick
			tOut.GuardMappingList.add(grdtOut1);
			
			
			Condition TOutCt2 = new Condition(tOut, "P4", TransitionCondition.NotNull);

			GuardMapping grdtOut2 = new GuardMapping();
			grdtOut2.condition = TOutCt2;
			
			grdtOut2.Activations.add(new Activation(tOut, "PLToken", TransitionOperation.Move, "P5"));
			grdtOut2.Activations.add(new Activation(tOut, "", TransitionOperation.MakeNull, "P6")); //to consume the token from the previous tick
			tOut.GuardMappingList.add(grdtOut2);
			
			
			tOut.Delay = 0;
			pn.Transitions.add(tOut);

			// -------------------------------------------

			// PetriTransition t3 = new PetriTransition(pn);
			// pn.Transitions.add(t3);

			System.out.println("Comparator started \n ------------------------------");
			pn.Delay = 10;
			pn.PrintingSpeed=10;

			pn.ShowLogInWindow=true; 
			// pn.Start();

			PetriNetWindow frame = new PetriNetWindow(false);
			frame.petriNet = pn;
			frame.setVisible(true);
	}
}
