package DCS_FuzzyLab.OETPN_C;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import Components.Activation;
import Components.Condition;
import Components.GuardMapping;
import Components.PetriNet;
import Components.PetriNetWindow;
import Components.PetriTransition;
import DataObjects.DataFuzzy;
import DataObjects.DataTransfer;
import DataOnly.FLRS;
import DataOnly.FV;
import DataOnly.Fuzzy;
import DataOnly.FuzzyVector;
import DataOnly.PlaceNameWithWeight;
import DataOnly.TransferOperation;
import Enumerations.FZ;
import Enumerations.LogicConnector;
import Enumerations.TransitionCondition;
import Enumerations.TransitionOperation;

public class Plant {
	public static void main (String[]args) throws FileNotFoundException {
	PetriNet pn = new PetriNet();
	pn.PetriNetName = "Plant";
	pn.NetworkPort = 1082;
	
	FLRS doubleChannelAdder = new FLRS(new FV(FZ.NL, FZ.NL), new FV(FZ.NL, FZ.NL), new FV(FZ.NL, FZ.NL), new FV(FZ.NM, FZ.NM),new FV(FZ.ZR, FZ.ZR), 
			   new FV(FZ.NL, FZ.NL), new FV(FZ.NL, FZ.NL), new FV(FZ.NM, FZ.NM), new FV(FZ.ZR, FZ.ZR),new FV(FZ.PM, FZ.PM), 
			   new FV(FZ.NL, FZ.NL), new FV(FZ.NM, FZ.NM), new FV(FZ.ZR, FZ.ZR), new FV(FZ.PM, FZ.PM),new FV(FZ.PL, FZ.PL), 
			   new FV(FZ.NM, FZ.NM), new FV(FZ.ZR, FZ.ZR), new FV(FZ.PM, FZ.PM), new FV(FZ.PL, FZ.PL),new FV(FZ.PL, FZ.PL),
			   new FV(FZ.ZR, FZ.ZR), new FV(FZ.PM, FZ.PM), new FV(FZ.PL, FZ.PL), new FV(FZ.PL, FZ.PL),new FV(FZ.PL, FZ.PL));
	
	doubleChannelAdder.Print();
	
	DataFuzzy p_i2 = new DataFuzzy(); //from OETPN
	p_i2.SetName("u");
	pn.PlaceList.add(p_i2);
	
	DataFuzzy p_20 = new DataFuzzy(); //from operator
	p_20.SetName("x");
	p_20.SetValue(new Fuzzy(0.0F));
	pn.PlaceList.add(p_20);
	
	DataFuzzy p_21 = new DataFuzzy();
	p_21.SetName("y");
	pn.PlaceList.add(p_21);
	
	DataTransfer p_o2 = new DataTransfer();
	p_o2.SetName("Cc_2_y");
	p_o2.Value = new TransferOperation("localhost", "1080", "Cc_2_y"); //to OETPN
	pn.PlaceList.add(p_o2);

	
	
	// T_21 ------------------------------------------------
				PetriTransition t_21 = new PetriTransition(pn);
				t_21.TransitionName = "t_21";
				t_21.InputPlaceName.add("u");
				t_21.InputPlaceName.add("x");
			

				Condition T_21Ct1 = new Condition(t_21, "u", TransitionCondition.NotNull);
				Condition T_21Ct2 = new Condition(t_21, "x", TransitionCondition.NotNull);
				T_21Ct1.SetNextCondition(LogicConnector.AND, T_21Ct2);

				GuardMapping grdt_21 = new GuardMapping();
				grdt_21.condition = T_21Ct1;
				
				ArrayList<PlaceNameWithWeight> input1 = new ArrayList<>();
				input1.add(new PlaceNameWithWeight("x", 0.5F)); //a*x
				input1.add(new PlaceNameWithWeight("u", 0.7F)); //b*u
				
				ArrayList<String> output1 = new ArrayList<>();
				output1.add("x"); 
				output1.add("y"); 


				grdt_21.Activations.add(new Activation(t_21, doubleChannelAdder, input1, TransitionOperation.FLRS, output1));
				
				
				
				t_21.GuardMappingList.add(grdt_21);

				t_21.Delay = 1;
				pn.Transitions.add(t_21);
	

				// T22 ------------------------------------------------
				PetriTransition t_22 = new PetriTransition(pn);
				t_22.TransitionName = "t_22";
				t_22.InputPlaceName.add("y");

				Condition T_22Ct1 = new Condition(t_22, "y", TransitionCondition.NotNull);

				GuardMapping grdt_22 = new GuardMapping();
				grdt_22.condition = T_22Ct1;

				grdt_22.Activations.add(new Activation(t_22, "y", TransitionOperation.SendOverNetwork, "Cc_2_y"));
				
				t_22.GuardMappingList.add(grdt_22);

				t_22.Delay = 0;
				pn.Transitions.add(t_22);	

	// -------------------------------------------

			System.out.println("Plant started \n ------------------------------");
			pn.Delay = 10;
			pn.PrintingSpeed=50;
			pn.ShowLogInWindow=false;
			// pn.Start();

			PetriNetWindow frame = new PetriNetWindow(false);
			frame.petriNet = pn;
			frame.setVisible(true);
	}
}
