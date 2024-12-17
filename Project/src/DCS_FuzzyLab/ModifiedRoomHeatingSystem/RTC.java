package DCS_FuzzyLab.ModifiedRoomHeatingSystem;

import Components.*;
import DataObjects.DataFuzzy;
import DataObjects.DataNetworkCommand;
import DataObjects.DataTransfer;
import DataOnly.*;
import Enumerations.*;

import java.io.FileNotFoundException;
import java.util.ArrayList;
 
public class RTC {
	public static void main (String[]args) throws FileNotFoundException {
	PetriNet pn = new PetriNet();
	pn.PetriNetName = "RTC";
	pn.NetworkPort = 1082;
	
	pn.SetInputFile("/home/adrian/Documents/DCS_Lab/All_Petri_FW/All_Petri_FW/src/DCS_FuzzyLab/ModifiedRoomHeatingSystem/RTC.txt");
	
	
	FLRS reader = new FLRS(new FV(FZ.NL), new FV(FZ.NM), new FV(FZ.ZR), new FV(FZ.PM), new FV(FZ.PL), 
						   new FV(FZ.NL), new FV(FZ.NM), new FV(FZ.ZR), new FV(FZ.PM), new FV(FZ.PL), 
						   new FV(FZ.NL), new FV(FZ.NM), new FV(FZ.ZR), new FV(FZ.PM), new FV(FZ.PL), 
						   new FV(FZ.NL), new FV(FZ.NM), new FV(FZ.ZR), new FV(FZ.PM), new FV(FZ.PL),
						   new FV(FZ.NL), new FV(FZ.NM), new FV(FZ.ZR), new FV(FZ.PM), new FV(FZ.PL));
	
	FLRS doubleChannelDifferentiator = new FLRS(new FV(FZ.ZR, FZ.ZR), new FV(FZ.NM, FZ.NM), new FV(FZ.NL, FZ.NL), new FV(FZ.NL, FZ.NL),new FV(FZ.NL, FZ.NL), 
												 new FV(FZ.PM, FZ.PM), new FV(FZ.ZR, FZ.ZR), new FV(FZ.NM, FZ.NM), new FV(FZ.NL, FZ.NL),new FV(FZ.NL, FZ.NL), 
												 new FV(FZ.PL, FZ.PL), new FV(FZ.PM, FZ.PM), new FV(FZ.ZR, FZ.ZR), new FV(FZ.NM, FZ.NM),new FV(FZ.NL, FZ.NL), 
												 new FV(FZ.PL, FZ.PL), new FV(FZ.PL, FZ.PL), new FV(FZ.PM, FZ.PM), new FV(FZ.ZR, FZ.ZR),new FV(FZ.NM, FZ.NM),
												 new FV(FZ.PL, FZ.PL), new FV(FZ.PL, FZ.PL), new FV(FZ.PL, FZ.PL), new FV(FZ.PM, FZ.PM),new FV(FZ.ZR, FZ.ZR));
	
	FLRS OneXOneDefaultTable = new FLRS(new FV(FZ.NL), new FV(FZ.NM), new FV(FZ.ZR), new FV(FZ.PM),new FV(FZ.PL));
	
	FLRS t3Table = new FLRS(new FV(FZ.FF,FZ.ZR), new FV(FZ.FF,FZ.FF), new FV(FZ.FF,FZ.FF), new FV(FZ.FF,FZ.FF),new FV(FZ.ZR,FZ.FF));

	reader.Print();
	doubleChannelDifferentiator.Print();
	OneXOneDefaultTable.Print();
	t3Table.Print();
	
	
	DataFuzzy p0 = new DataFuzzy();
	p0.SetName("P0");
	p0.SetValue(new Fuzzy(0.0F));
	pn.PlaceList.add(p0);
	
	DataFuzzy p1 = new DataFuzzy(); //Room ref. temp
	p1.SetName("P1");
	pn.PlaceList.add(p1);

	DataFuzzy p2 = new DataFuzzy(); 
	p2.SetName("P2");
	pn.PlaceList.add(p2);

	DataFuzzy p3 = new DataFuzzy(); //from Room Plant_Current Room temp
	p3.SetName("P3");
	p3.SetValue(new Fuzzy(0.96F));
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
	
	DataFuzzy p7 = new DataFuzzy();
	p7.SetName("P7");
	p7.SetValue(new Fuzzy(0.0F));
	pn.PlaceList.add(p7);
	
	DataTransfer u = new DataTransfer();
	u.SetName("u");
	u.Value = new TransferOperation("localhost", "1081", "u"); //to Heater Tank Plant ON/OFF
	pn.PlaceList.add(u);
	
	
	DataNetworkCommand PauseCommand = new DataNetworkCommand();
	PauseCommand.SetName("PauseCommand");
	PauseCommand.SetValue(new NetworkCommand(NetworkCommands.Pause));
	pn.ConstantPlaceList.add(PauseCommand);
	
	DataNetworkCommand StartCommand = new DataNetworkCommand();
	StartCommand.SetName("StartCommand");
	StartCommand.SetValue(new NetworkCommand(NetworkCommands.Start));
	pn.ConstantPlaceList.add(StartCommand);
	
	DataFuzzy NL = new DataFuzzy();
	NL.SetName("NL");
	NL.SetValue(new Fuzzy(-1F));
	pn.ConstantPlaceList.add(NL);
	
	DataFuzzy PL = new DataFuzzy();
	PL.SetName("PL");
	PL.SetValue(new Fuzzy(1F));
	pn.ConstantPlaceList.add(PL);
	
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


				grdT0.Activations.add(new Activation(t0, reader, input0, TransitionOperation.FLRS, Output0));
				
				t0.GuardMappingList.add(grdT0);

				t0.Delay = 0;
				pn.Transitions.add(t0);
	
	
	
	// T1 ------------------------------------------------
			PetriTransition t1 = new PetriTransition(pn);
			t1.TransitionName = "T1";
			t1.InputPlaceName.add("P2");
			t1.InputPlaceName.add("P3");

			Condition T1Ct1 = new Condition(t1, "P2", TransitionCondition.NotNull);
			Condition T1Ct2 = new Condition(t1, "P3", TransitionCondition.NotNull);
			T1Ct1.SetNextCondition(LogicConnector.AND, T1Ct2);

			GuardMapping grdT1 = new GuardMapping();
			grdT1.condition = T1Ct1;

			ArrayList<PlaceNameWithWeight> input1 = new ArrayList<>();
			input1.add(new PlaceNameWithWeight("P2", 1F));
			input1.add(new PlaceNameWithWeight("P3", 1F));

			ArrayList<String> Output1 = new ArrayList<>();
			Output1.add("P4");
			Output1.add("P5");
			

			grdT1.Activations.add(new Activation(t1, doubleChannelDifferentiator, input1, TransitionOperation.FLRS, Output1));
			
			t1.GuardMappingList.add(grdT1);

			t1.Delay = 0;
			pn.Transitions.add(t1);
			
			
			// T2 ------------------------------------------------
			PetriTransition t2 = new PetriTransition(pn);
			t2.TransitionName = "T2";
			t2.InputPlaceName.add("P4");

			Condition T2Ct1 = new Condition(t2, "P4", TransitionCondition.NotNull);
			
			GuardMapping grdT2 = new GuardMapping();
			grdT2.condition = T2Ct1;

			ArrayList<PlaceNameWithWeight> input2 = new ArrayList<>();
			input2.add(new PlaceNameWithWeight("P4", 1F));
			
			ArrayList<String> Output2 = new ArrayList<>();
			Output2.add("P0");
			
			grdT2.Activations.add(new Activation(t2, OneXOneDefaultTable, input2, TransitionOperation.FLRS, Output2));
			
			t2.GuardMappingList.add(grdT2);

			t2.Delay = 1;
			pn.Transitions.add(t2);
			
			
			
			// T3 ------------------------------------------------
			PetriTransition t3 = new PetriTransition(pn);
			t3.TransitionName = "T3";
			t3.InputPlaceName.add("P5");

			Condition T3Ct1 = new Condition(t3, "P5", TransitionCondition.NotNull);

			GuardMapping grdT3 = new GuardMapping();
			grdT3.condition = T3Ct1;

			ArrayList<PlaceNameWithWeight> input3 = new ArrayList<>();
			input3.add(new PlaceNameWithWeight("P5", 1F));

			
			ArrayList<String> Output3 = new ArrayList<>();
			Output3.add("P6");
			Output3.add("P7");

			
			grdT3.Activations.add(new Activation(t3, t3Table, input3, TransitionOperation.FLRS, Output3));
			
			t3.GuardMappingList.add(grdT3);

			t3.Delay = 0;
			pn.Transitions.add(t3);
			
			
			// T4 ------------------------------------------------
			PetriTransition t4 = new PetriTransition(pn);
			t4.TransitionName = "T4";
			t4.InputPlaceName.add("P6");
			t4.InputPlaceName.add("P7");

	
			Condition T4Ct1 = new Condition(t4, "P6", TransitionCondition.NotNull);
			Condition T4Ct2 = new Condition(t4, "P6", TransitionCondition.Equal, "NL");
			T4Ct1.SetNextCondition(LogicConnector.AND, T4Ct2);

			GuardMapping grdT41 = new GuardMapping();
			grdT41.condition = T4Ct1;

			grdT41.Activations.add(new Activation(t4, "PauseCommand", TransitionOperation.SendOverNetwork, "u"));
			
			t4.GuardMappingList.add(grdT41);
			
			//-----------------------------------------------------------------
			
			Condition T4Ct3 = new Condition(t4, "P7", TransitionCondition.NotNull);
			Condition T4Ct4 = new Condition(t4, "P7", TransitionCondition.Equal, "PL");
			T4Ct3.SetNextCondition(LogicConnector.AND, T4Ct4);

			GuardMapping grdT42 = new GuardMapping();
			grdT42.condition = T4Ct3;

			grdT42.Activations.add(new Activation(t4, "StartCommand", TransitionOperation.SendOverNetwork, "u"));
			
			t4.GuardMappingList.add(grdT42);
			

			t4.Delay = 0;
			pn.Transitions.add(t4);
			
			
			
			// -------------------------------------------

			pn.Delay = 500;
			//pn.PrintingSpeed=0;
			pn.ShowLogInWindow=true;
			// pn.Start();

			PetriNetWindow frame = new PetriNetWindow(false);
			frame.petriNet = pn;
			frame.setVisible(true);
	}
}
