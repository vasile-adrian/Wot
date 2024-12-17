package TestFuzzy.TestFLRS;

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

public class MathTest {
	public static void main(String[] args) {

//		Fuzzy f1= new Fuzzy(new FuzzyVector(0.0F,0.0F,0.8F, 0.2F, 0.0F), 0.1F);
//		Fuzzy f2= new Fuzzy(0.1F);
//f2.GenerateFuzzyToken(0.6F);
//		System.out.println(f2.toString());
		
		PetriNet pn = new PetriNet();
		pn.PetriNetName = "Main Petri";
		pn.NetworkPort = 1081;

		DataFuzzy p1 = new DataFuzzy();
		p1.SetName("P1");
		p1.SetValue(new Fuzzy(0.5F));
		pn.PlaceList.add(p1);

		DataFuzzy p2 = new DataFuzzy();
		p2.SetName("P2");
		p2.SetValue(new Fuzzy(0.1F));
		pn.PlaceList.add(p2);

		DataFuzzy p3 = new DataFuzzy();
		p3.SetName("P3");
		pn.PlaceList.add(p3);

		DataFuzzy p4 = new DataFuzzy(); 
		p4.SetName("P4"); 
		pn.PlaceList.add(p4);
		
		DataFuzzy p5 = new DataFuzzy();
		p5.SetName("P5"); 
		pn.PlaceList.add(p5); 

		DataFuzzy p6 = new DataFuzzy(); 
		p6.SetName("P6"); 
		pn.PlaceList.add(p6);
		
		// T1 ------------------------------------------------
		PetriTransition t1 = new PetriTransition(pn);
		t1.TransitionName = "T1";
		t1.InputPlaceName.add("P1");
		t1.InputPlaceName.add("P2");

		Condition T1Ct1 = new Condition(t1, "P1", TransitionCondition.NotNull);
		Condition T1Ct2 = new Condition(t1, "P2", TransitionCondition.NotNull);
		T1Ct1.SetNextCondition(LogicConnector.AND, T1Ct2);

		GuardMapping grdT1 = new GuardMapping();
		grdT1.condition = T1Ct1;

		ArrayList<PlaceNameWithWeight> input = new ArrayList<>();
		input.add(new PlaceNameWithWeight("P1", 2F));
		input.add(new PlaceNameWithWeight("P2", -1F));
		
		
		grdT1.Activations.add(new Activation(input, TransitionOperation.Add_Fuzzy, "P3", t1));
		
		grdT1.Activations.add(new Activation(input, TransitionOperation.Sub_Fuzzy, "P4", t1));
		
		grdT1.Activations.add(new Activation(input, TransitionOperation.Prod_Fuzzy, "P5", t1));
		
		grdT1.Activations.add(new Activation(input, TransitionOperation.Div_Fuzzy, "P6", t1));

		t1.GuardMappingList.add(grdT1);

		t1.Delay = 0;
		pn.Transitions.add(t1);

		// -------------------------------------------

		System.out.println("Exp1 started \n ------------------------------");
		pn.Delay = 3000;
		// pn.Start();

		PetriNetWindow frame = new PetriNetWindow(false);
		frame.petriNet = pn;
		frame.setVisible(true);

	}
}
