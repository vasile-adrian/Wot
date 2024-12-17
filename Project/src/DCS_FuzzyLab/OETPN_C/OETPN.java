package DCS_FuzzyLab.OETPN_C;

import java.io.FileNotFoundException;
import Components.Activation;
import Components.Condition;
import Components.GuardMapping;
import Components.PetriNet;
import Components.PetriNetWindow;
import Components.PetriTransition;
import DataObjects.DataFuzzy;
import DataObjects.DataTransfer;
import DataOnly.Fuzzy;
import DataOnly.TransferOperation;
import Enumerations.LogicConnector;
import Enumerations.TransitionCondition;
import Enumerations.TransitionOperation;

public class OETPN {
	public static void main (String[]args) throws FileNotFoundException {
	PetriNet pn = new PetriNet();
	pn.PetriNetName = "OETPN";
	pn.NetworkPort = 1080;
	
	pn.SetInputFile("D:\\PetriInputData\\OETPNInput.txt");
	
	DataFuzzy p_00 = new DataFuzzy();
	p_00.SetName("p_00");
	p_00.SetValue(new Fuzzy(0.0F));
	pn.PlaceList.add(p_00);
	
	DataFuzzy p_02 = new DataFuzzy(); //from operator
	p_02.SetName("r");
	pn.PlaceList.add(p_02);
	
	DataFuzzy p_01 = new DataFuzzy();
	p_01.SetName("p_01");
	pn.PlaceList.add(p_01);

	DataFuzzy p_03 = new DataFuzzy(); //from plant
	p_03.SetName("Cc_2_y");
	p_03.SetValue(new Fuzzy(0.55F));
	pn.PlaceList.add(p_03);

	DataFuzzy p_04 = new DataFuzzy();
	p_04.SetName("p_04");
	pn.PlaceList.add(p_04);
	
	DataTransfer p_05_r = new DataTransfer();
	p_05_r.SetName("Cc_1_r");
	p_05_r.Value = new TransferOperation("localhost", "1081", "P4"); //to controller
	pn.PlaceList.add(p_05_r);
	
	DataTransfer p_05_y = new DataTransfer();
	p_05_y.SetName("Cc_1_y");
	p_05_y.Value = new TransferOperation("localhost", "1081", "P2");  //to controller
	pn.PlaceList.add(p_05_y);

	DataFuzzy p_06 = new DataFuzzy();
	p_06.SetName("p_06");
	pn.PlaceList.add(p_06);
	
	DataFuzzy p_07 = new DataFuzzy();
	p_07.SetName("Cp_o_y");
	pn.PlaceList.add(p_07);
	
	DataFuzzy p_08 = new DataFuzzy(); //from controller
	p_08.SetName("Cc_1_c");
	pn.PlaceList.add(p_08);
	
	DataTransfer p_09 = new DataTransfer();
	p_09.SetName("Cc_2_u");
	p_09.Value = new TransferOperation("localhost", "1082", "u"); //to plant
	pn.PlaceList.add(p_09);
		
	
	// T_00 ------------------------------------------------
				PetriTransition t_00 = new PetriTransition(pn);
				t_00.TransitionName = "t_00";
				t_00.InputPlaceName.add("r");
				t_00.InputPlaceName.add("p_00");
			

				Condition T_10Ct1 = new Condition(t_00, "r", TransitionCondition.NotNull);
				Condition T_10Ct2 = new Condition(t_00, "p_00", TransitionCondition.NotNull);
				T_10Ct1.SetNextCondition(LogicConnector.AND, T_10Ct2);

				GuardMapping grdt_00 = new GuardMapping();
				grdt_00.condition = T_10Ct1;
				
				grdt_00.Activations.add(new Activation(t_00, "r", TransitionOperation.Move, "p_01"));
				
				t_00.GuardMappingList.add(grdt_00);

				t_00.Delay = 1;
				pn.Transitions.add(t_00);
	
	
	
	// T_01 ------------------------------------------------
			PetriTransition t_01 = new PetriTransition(pn);
			t_01.TransitionName = "t_01";
			t_01.InputPlaceName.add("Cc_2_y");
			t_01.InputPlaceName.add("p_01");
			//t_01.IsAsync= true;

			Condition T_01Ct1 = new Condition(t_01, "Cc_2_y", TransitionCondition.NotNull);
			Condition T_01Ct2 = new Condition(t_01, "p_01", TransitionCondition.NotNull);
			T_01Ct1.SetNextCondition(LogicConnector.AND, T_01Ct2);

			GuardMapping grdt_01 = new GuardMapping();
			grdt_01.condition = T_01Ct1;

			grdt_01.Activations.add(new Activation(t_01, "Cc_2_y", TransitionOperation.SendOverNetwork, "Cc_1_y"));
			grdt_01.Activations.add(new Activation(t_01, "p_01", TransitionOperation.SendOverNetwork, "Cc_1_r"));
			grdt_01.Activations.add(new Activation(t_01, "Cc_2_y", TransitionOperation.Move, "p_04"));
			
			t_01.GuardMappingList.add(grdt_01);

			t_01.Delay = 0;
			pn.Transitions.add(t_01);
			
			
			// T_02 ------------------------------------------------
			PetriTransition t_02 = new PetriTransition(pn);
			t_02.TransitionName = "t_02";
			t_02.InputPlaceName.add("p_04");
			
			Condition T_02Ct1 = new Condition(t_02, "p_04", TransitionCondition.NotNull);
			

			GuardMapping grdt_02 = new GuardMapping();
			grdt_02.condition = T_02Ct1;

			grdt_02.Activations.add(new Activation(t_02, "p_04", TransitionOperation.Move, "p_05"));
			grdt_02.Activations.add(new Activation(t_02, "p_04", TransitionOperation.Move, "p_06"));
			
			t_02.GuardMappingList.add(grdt_02);

			t_02.Delay = 0;
			pn.Transitions.add(t_02);
			
			
			
			// T_03 ------------------------------------------------
			PetriTransition t_03 = new PetriTransition(pn);
			t_03.TransitionName = "t_03";
			t_03.InputPlaceName.add("p_06");
			t_03.InputPlaceName.add("Cc_1_c");
			//t_03.IsAsync= true;

			Condition T_03Ct1 = new Condition(t_03, "p_06", TransitionCondition.NotNull);
			Condition T_03Ct2 = new Condition(t_03, "Cc_1_c", TransitionCondition.NotNull);
			T_03Ct1.SetNextCondition(LogicConnector.AND, T_03Ct2);

			GuardMapping grdt_03 = new GuardMapping();
			grdt_03.condition = T_03Ct1;

			grdt_03.Activations.add(new Activation(t_03,"Cc_1_c", TransitionOperation.SendOverNetwork, "Cc_2_u"));
			grdt_03.Activations.add(new Activation(t_03,"p_06", TransitionOperation.Move, "p_00"));
			
			t_03.GuardMappingList.add(grdt_03);

			t_03.Delay = 0;
			pn.Transitions.add(t_03);
			
						
			// -------------------------------------------

			System.out.println("OETPN started \n ------------------------------");
			pn.Delay = 10;
			pn.PrintingSpeed=50;
			pn.ShowLogInWindow=false;
			// pn.Start();

			PetriNetWindow frame = new PetriNetWindow(false);
			frame.petriNet = pn;
			frame.setVisible(true);
	}
}
