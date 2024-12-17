package DCS_FuzzyLab.ModifiedRoomHeatingSystem;

import Components.*;
import DataObjects.DataFuzzy;
import DataObjects.DataTransfer;
import DataOnly.FLRS;
import DataOnly.FV;
import DataOnly.PlaceNameWithWeight;
import DataOnly.TransferOperation;
import Enumerations.FZ;
import Enumerations.LogicConnector;
import Enumerations.TransitionCondition;
import Enumerations.TransitionOperation;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class ORC {

    public static void main (String[]args) throws FileNotFoundException {
        PetriNet pn = new PetriNet();
        pn.PetriNetName = "ORC";
        pn.NetworkPort = 1085;

        pn.SetInputFile("/home/adrian/Documents/DCS_Lab/All_Petri_FW/All_Petri_FW/src/DCS_FuzzyLab/ModifiedRoomHeatingSystem/ORC.txt");

        FLRS reader = new FLRS(new FV(FZ.NL, FZ.NL), new FV(FZ.NM, FZ.NM), new FV(FZ.ZR, FZ.ZR), new FV(FZ.PM, FZ.PM), new FV(FZ.PL, FZ.PL),
                new FV(FZ.NL, FZ.NL), new FV(FZ.NM, FZ.NM), new FV(FZ.ZR, FZ.ZR), new FV(FZ.PM, FZ.PM), new FV(FZ.PL, FZ.PL),
                new FV(FZ.NL, FZ.NL), new FV(FZ.NM, FZ.NM), new FV(FZ.ZR, FZ.ZR), new FV(FZ.PM, FZ.PM), new FV(FZ.PL, FZ.PL),
                new FV(FZ.NL, FZ.NL), new FV(FZ.NM, FZ.NM), new FV(FZ.ZR, FZ.ZR), new FV(FZ.PM, FZ.PM), new FV(FZ.PL, FZ.PL),
                new FV(FZ.NL, FZ.NL), new FV(FZ.NM, FZ.NM), new FV(FZ.ZR, FZ.ZR), new FV(FZ.PM, FZ.PM), new FV(FZ.PL, FZ.PL));

        FLRS t2_Table = new FLRS(new FV(FZ.PL), new FV(FZ.PM), new FV(FZ.ZR), new FV(FZ.NM),new FV(FZ.NL));

        DataFuzzy p0 = new DataFuzzy();
        p0.SetName("P0");
        p0.SetValue(0.0F);
        pn.PlaceList.add(p0);

        DataFuzzy p1 = new DataFuzzy();
        p1.SetName("P1");
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

        DataTransfer p5 = new DataTransfer();
        p5.SetName("P5");
        p5.Value = new TransferOperation("localhost", "1083", "P1");
        pn.PlaceList.add(p5);

        //-------------------------------
        PetriTransition t0 = new PetriTransition(pn);
        t0.SetName("t0");
        t0.InputPlaceName.add("P0");
        t0.InputPlaceName.add("P1");

        Condition T0Ct1 = new Condition(t0, "P0", TransitionCondition.NotNull);
        Condition T0Ct2 = new Condition(t0, "P1", TransitionCondition.NotNull);
        T0Ct1.SetNextCondition(LogicConnector.AND, T0Ct2);

        GuardMapping grdT0 = new GuardMapping();
        grdT0.condition = T0Ct1;

        ArrayList<PlaceNameWithWeight> input0 = new ArrayList<PlaceNameWithWeight>();
        input0.add(new PlaceNameWithWeight("P0", 1F));
        input0.add(new PlaceNameWithWeight("P1", 1F));

        ArrayList<String> Output0 = new ArrayList<>();
        Output0.add("P3");
        Output0.add("P2");

        grdT0.Activations.add(new Activation(t0, reader, input0, TransitionOperation.FLRS, Output0));

        t0.GuardMappingList.add(grdT0);

        t0.Delay = 0;
        pn.Transitions.add(t0);

        // -------------------------------

        PetriTransition t1 = new PetriTransition(pn);
        t1.SetName("t1");
        t1.InputPlaceName.add("P2");

        Condition T1Ct1 = new Condition(t1, "P2", TransitionCondition.NotNull);

        GuardMapping grdT1 = new GuardMapping();
        grdT1.condition = T1Ct1;

        grdT1.Activations.add(new Activation(t1, "P2", TransitionOperation.Move, "P0"));

        t1.GuardMappingList.add(grdT1);

        t1.Delay = 1;
        pn.Transitions.add(t1);

        // ---------------------------------

        PetriTransition t2 = new PetriTransition(pn);
        t2.SetName("t2");
        t2.InputPlaceName.add("P3");

        Condition T2Ct1 = new Condition(t2, "P3", TransitionCondition.NotNull);

        GuardMapping grdT2 = new GuardMapping();
        grdT2.condition = T2Ct1;

        ArrayList<PlaceNameWithWeight> input2 = new ArrayList<PlaceNameWithWeight>();
        input2.add(new PlaceNameWithWeight("P3", 1F));

        ArrayList<String> Output2 = new ArrayList<>();
        Output2.add("P4");

        grdT2.Activations.add(new Activation(t2, t2_Table, input2, TransitionOperation.FLRS, Output2));

        t2.GuardMappingList.add(grdT2);

        t2.Delay = 0;
        pn.Transitions.add(t2);

        // ------------------------------
        PetriTransition t3 = new PetriTransition(pn);
        t3.SetName("t3");
        t3.InputPlaceName.add("P4");

        Condition T3Ct1 = new Condition(t2, "P4", TransitionCondition.NotNull);

        GuardMapping grdT3 = new GuardMapping();
        grdT3.condition = T3Ct1;

        grdT3.Activations.add(new Activation(t3, "P4", TransitionOperation.SendOverNetwork, "P5"));

        t3.GuardMappingList.add(grdT3);

        t3.Delay = 0;
        pn.Transitions.add(t3);


        System.out.println("HTC started \n ------------------------------");
        pn.Delay = 500;
        //pn.PrintingSpeed=0;
        pn.ShowLogInWindow=true;
        // pn.Start();

        PetriNetWindow frame = new PetriNetWindow(false);
        frame.petriNet = pn;
        frame.setVisible(true);
    }

}
