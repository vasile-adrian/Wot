package DCS_FuzzyLab.ModifiedRoomHeatingSystem;

import Components.*;
import DataObjects.DataFuzzy;
import DataObjects.DataTransfer;
import DataOnly.*;
import Enumerations.FZ;
import Enumerations.LogicConnector;
import Enumerations.TransitionCondition;
import Enumerations.TransitionOperation;

import java.io.FileNotFoundException;
import java.util.ArrayList;

/*				The Equations are:
*               xNew = a * x + b * c + d1 * v1 + d2 * v2;
*               The constants are:
*				a = 1.0f;
*			    b = 0.01f;
*				d1 = 0.01f;
*				d2 = 0.00055f;
*/
public class RoomPlant {
	public static void main(String[] args) throws FileNotFoundException {
		PetriNet pn = new PetriNet();
		pn.PetriNetName = "Room Plant";
		pn.NetworkPort = 1080;
		
		pn.SetInputFile("/home/adrian/Documents/DCS_Lab/All_Petri_FW/All_Petri_FW/src/DCS_FuzzyLab/ModifiedRoomHeatingSystem/OutsideTemp.txt");
		
		FLRS reader = new FLRS(new FV(FZ.NL), new FV(FZ.NM), new FV(FZ.ZR), new FV(FZ.PM), new FV(FZ.PL), 
				   new FV(FZ.NL), new FV(FZ.NM), new FV(FZ.ZR), new FV(FZ.PM), new FV(FZ.PL), 
				   new FV(FZ.NL), new FV(FZ.NM), new FV(FZ.ZR), new FV(FZ.PM), new FV(FZ.PL), 
				   new FV(FZ.NL), new FV(FZ.NM), new FV(FZ.ZR), new FV(FZ.PM), new FV(FZ.PL),
				   new FV(FZ.NL), new FV(FZ.NM), new FV(FZ.ZR), new FV(FZ.PM), new FV(FZ.PL));
		
		FLRS adder = new FLRS(new FV(FZ.NL), new FV(FZ.NL), new FV(FZ.NL), new FV(FZ.NM), new FV(FZ.ZR), 
				  new FV(FZ.NL), new FV(FZ.NL), new FV(FZ.NM), new FV(FZ.ZR), new FV(FZ.PM), 
				  new FV(FZ.NL), new FV(FZ.NM), new FV(FZ.ZR), new FV(FZ.PM), new FV(FZ.PL), 
				  new FV(FZ.NM), new FV(FZ.ZR), new FV(FZ.PM), new FV(FZ.PL), new FV(FZ.PL),
				  new FV(FZ.ZR), new FV(FZ.PM), new FV(FZ.PL), new FV(FZ.PL), new FV(FZ.PL));
		
		FLRS doubleChannelAdder = new FLRS(new FV(FZ.NL, FZ.NL), new FV(FZ.NL, FZ.NL), new FV(FZ.NL, FZ.NL), new FV(FZ.NM, FZ.NM),new FV(FZ.ZR, FZ.ZR), 
				   new FV(FZ.NL, FZ.NL), new FV(FZ.NL, FZ.NL), new FV(FZ.NM, FZ.NM), new FV(FZ.ZR, FZ.ZR),new FV(FZ.PM, FZ.PM), 
				   new FV(FZ.NL, FZ.NL), new FV(FZ.NM, FZ.NM), new FV(FZ.ZR, FZ.ZR), new FV(FZ.PM, FZ.PM),new FV(FZ.PL, FZ.PL), 
				   new FV(FZ.NM, FZ.NM), new FV(FZ.ZR, FZ.ZR), new FV(FZ.PM, FZ.PM), new FV(FZ.PL, FZ.PL),new FV(FZ.PL, FZ.PL),
				   new FV(FZ.ZR, FZ.ZR), new FV(FZ.PM, FZ.PM), new FV(FZ.PL, FZ.PL), new FV(FZ.PL, FZ.PL),new FV(FZ.PL, FZ.PL));
		
		FLRS OneXTwoDefaultTable = new FLRS(new FV(FZ.NL,FZ.NL), new FV(FZ.NM,FZ.NM), new FV(FZ.ZR,FZ.ZR), new FV(FZ.PM,FZ.PM),new FV(FZ.PL,FZ.PL));
		
		reader.Print();
		adder.Print();
		doubleChannelAdder.Print();
		OneXTwoDefaultTable.Print();
		

		DataFuzzy xold = new DataFuzzy();
		xold.SetName("xold");
		xold.SetValue(new Fuzzy(0.9F)); // start temp 23 and temp range -25 - 25
		pn.PlaceList.add(xold);
		
		DataFuzzy x = new DataFuzzy();
		x.SetName("x");
		pn.PlaceList.add(x);

		DataFuzzy c = new DataFuzzy(); // from Heater Tank Plant
		c.SetName("c");
		pn.PlaceList.add(c);

		DataFuzzy v1 = new DataFuzzy(); // outside temp
		v1.SetName("v1");
		pn.PlaceList.add(v1);

		DataFuzzy p0 = new DataFuzzy();
		p0.SetName("p0");
		p0.SetValue(new Fuzzy(0.0F));
		pn.PlaceList.add(p0);

		DataFuzzy p1 = new DataFuzzy();
		p1.SetName("p1");
		pn.PlaceList.add(p1);

		DataFuzzy p2 = new DataFuzzy();
		p2.SetName("p2");
		pn.PlaceList.add(p2);
		
		DataFuzzy p3 = new DataFuzzy();
		p3.SetName("p3");
		pn.PlaceList.add(p3);
		
		DataFuzzy p4 = new DataFuzzy();
		p4.SetName("p4");
		pn.PlaceList.add(p4);

		DataTransfer sendTemp = new DataTransfer();
		sendTemp.SetName("sendTemp");
		sendTemp.Value = new TransferOperation("localhost", "1082", "P3"); // to RTC
		pn.PlaceList.add(sendTemp);

		// t_11 ------------------------------------------------
		PetriTransition t_11 = new PetriTransition(pn);
		t_11.TransitionName = "t_11";
		t_11.InputPlaceName.add("p0");
		t_11.InputPlaceName.add("v1");

		Condition T_11Ct1 = new Condition(t_11, "v1", TransitionCondition.NotNull);
		Condition T_11Ct2 = new Condition(t_11, "p0", TransitionCondition.NotNull);
		T_11Ct1.SetNextCondition(LogicConnector.AND, T_11Ct2);

		GuardMapping grdt_11 = new GuardMapping();
		grdt_11.condition = T_11Ct1;

		ArrayList<PlaceNameWithWeight> input1 = new ArrayList<>();
		input1.add(new PlaceNameWithWeight("p0", 1F));
		input1.add(new PlaceNameWithWeight("v1", 0.01005F)); // window disturbance + Walls v1(d1+d2)
		
		ArrayList<String> output1 = new ArrayList<>();
		output1.add("p1");

		grdt_11.Activations.add(new Activation(t_11, reader, input1, TransitionOperation.FLRS, output1));

		t_11.GuardMappingList.add(grdt_11);

		t_11.Delay = 0;
		pn.Transitions.add(t_11);

		// t_12 ------------------------------------------------
		PetriTransition t_12 = new PetriTransition(pn);
		t_12.TransitionName = "t_12";
		t_12.InputPlaceName.add("p1");
		t_12.InputPlaceName.add("c");

		Condition T_12Ct1 = new Condition(t_12, "c", TransitionCondition.NotNull);
		Condition T_12Ct2 = new Condition(t_12, "p1", TransitionCondition.NotNull);
		T_12Ct1.SetNextCondition(LogicConnector.AND, T_12Ct2);

		GuardMapping grdt_12 = new GuardMapping();
		grdt_12.condition = T_12Ct1;

		ArrayList<PlaceNameWithWeight> input2 = new ArrayList<>();
		input2.add(new PlaceNameWithWeight("p1", 1F));
		input2.add(new PlaceNameWithWeight("c", 0.01F));
	
		
		ArrayList<String> output2 = new ArrayList<>();
		output2.add("p2");

		grdt_12.Activations.add(new Activation(t_12, adder, input2, TransitionOperation.FLRS, output2));

		t_12.GuardMappingList.add(grdt_12);

		t_12.Delay = 0;
		pn.Transitions.add(t_12);

		// t_13 ------------------------------------------------
		PetriTransition t_13 = new PetriTransition(pn);
		t_13.TransitionName = "t_13";
		t_13.InputPlaceName.add("p2");
		t_13.InputPlaceName.add("xold");

		Condition T_13Ct1 = new Condition(t_13, "xold", TransitionCondition.NotNull);
		Condition T_13Ct2 = new Condition(t_13, "p2", TransitionCondition.NotNull);
		T_13Ct1.SetNextCondition(LogicConnector.AND, T_13Ct2);

		GuardMapping grdt_13 = new GuardMapping();
		grdt_13.condition = T_13Ct1;

		ArrayList<PlaceNameWithWeight> input3 = new ArrayList<>();
		input3.add(new PlaceNameWithWeight("p2",1F));
		input3.add(new PlaceNameWithWeight("xold", 1F));
		
		ArrayList<String> output3 = new ArrayList<>();
		output3.add("x");
		output3.add("p3");

		grdt_13.Activations.add(new Activation(t_13, doubleChannelAdder, input3, TransitionOperation.FLRS, output3));
	
		t_13.GuardMappingList.add(grdt_13);

		t_13.Delay = 0;
		pn.Transitions.add(t_13);

		// t_14 ------------------------------------------------
		PetriTransition t_14 = new PetriTransition(pn);
		t_14.TransitionName = "t_14";
		t_14.InputPlaceName.add("x");
		
		Condition T_14Ct1 = new Condition(t_14, "x", TransitionCondition.NotNull);

		GuardMapping grdt_14 = new GuardMapping();
		grdt_14.condition = T_14Ct1;

		ArrayList<PlaceNameWithWeight> input4 = new ArrayList<>();
		input4.add(new PlaceNameWithWeight("x", 1F));
				
		ArrayList<String> output4 = new ArrayList<>();
		output4.add("xold");
		output4.add("p0");

		grdt_14.Activations.add(new Activation(t_14, OneXTwoDefaultTable, input4, TransitionOperation.FLRS, output4));

		t_14.GuardMappingList.add(grdt_14);

		t_14.Delay = 1;
		pn.Transitions.add(t_14);
		
		
		// t_15 ------------------------------------------------
		PetriTransition t_15 = new PetriTransition(pn);
		t_15.TransitionName = "t_15";
		t_15.InputPlaceName.add("p3");
		
		Condition T_15Ct1 = new Condition(t_14, "p3", TransitionCondition.NotNull);

		GuardMapping grdt_15 = new GuardMapping();
		grdt_15.condition = T_15Ct1;

				

		grdt_15.Activations.add(new Activation(t_15, "p3", TransitionOperation.SendOverNetwork, "sendTemp"));

		t_15.GuardMappingList.add(grdt_15);

		t_15.Delay = 0;
		pn.Transitions.add(t_15);		
		
		//---------------------------------------------------------

		System.out.println("Room Plant started \n ------------------------------");
		pn.Delay = 500;
		//pn.PrintingSpeed=0;
		pn.ShowLogInWindow=true;
		// pn.Start();

		PetriNetWindow frame = new PetriNetWindow(false);
		frame.petriNet = pn;
		frame.setVisible(true);
	}
}
