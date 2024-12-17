package DCS_FuzzyLab.OETPN_C;

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

public class PIDController {

    public static void main (String[]args) throws FileNotFoundException {
        PetriNet pn = new PetriNet();
        pn.PetriNetName = "PID Controller";
        pn.NetworkPort = 1081;

        pn.SetInputFile("/home/adrian/Documents/DCS_Lab/Lab4/src/DCS_FuzzyLab/OETPN_C/PIController.txt"); //for testing PI controller alone, T7 must all be commented

        FLRS reader = new FLRS(new FV(FZ.NL), new FV(FZ.NM), new FV(FZ.ZR), new FV(FZ.PM), new FV(FZ.PL),
                new FV(FZ.NL), new FV(FZ.NM), new FV(FZ.ZR), new FV(FZ.PM), new FV(FZ.PL),
                new FV(FZ.NL), new FV(FZ.NM), new FV(FZ.ZR), new FV(FZ.PM), new FV(FZ.PL),
                new FV(FZ.NL), new FV(FZ.NM), new FV(FZ.ZR), new FV(FZ.PM), new FV(FZ.PL),
                new FV(FZ.NL), new FV(FZ.NM), new FV(FZ.ZR), new FV(FZ.PM), new FV(FZ.PL));


        FLRS doubleChannelAdder = new FLRS(new FV(FZ.NL, FZ.NL), new FV(FZ.NL, FZ.NL), new FV(FZ.NL, FZ.NL), new FV(FZ.NM, FZ.NM),new FV(FZ.ZR, FZ.ZR),
                new FV(FZ.NL, FZ.NL), new FV(FZ.NL, FZ.NL), new FV(FZ.NM, FZ.NM), new FV(FZ.ZR, FZ.ZR),new FV(FZ.PM, FZ.PM),
                new FV(FZ.NL, FZ.NL), new FV(FZ.NM, FZ.NM), new FV(FZ.ZR, FZ.ZR), new FV(FZ.PM, FZ.PM),new FV(FZ.PL, FZ.PL),
                new FV(FZ.NM, FZ.NM), new FV(FZ.ZR, FZ.ZR), new FV(FZ.PM, FZ.PM), new FV(FZ.PL, FZ.PL),new FV(FZ.PL, FZ.PL),
                new FV(FZ.ZR, FZ.ZR), new FV(FZ.PM, FZ.PM), new FV(FZ.PL, FZ.PL), new FV(FZ.PL, FZ.PL),new FV(FZ.PL, FZ.PL));

        FLRS doubleChannelDifferentiator = new FLRS(new FV(FZ.ZR, FZ.ZR), new FV(FZ.PM, FZ.PM), new FV(FZ.PL, FZ.PL), new FV(FZ.PL, FZ.PL),new FV(FZ.PL, FZ.PL),
                new FV(FZ.NM, FZ.NM), new FV(FZ.ZR, FZ.ZR), new FV(FZ.PM, FZ.PM), new FV(FZ.PL, FZ.PL),new FV(FZ.PL, FZ.PL),
                new FV(FZ.NL, FZ.NL), new FV(FZ.NM, FZ.NM), new FV(FZ.ZR, FZ.ZR), new FV(FZ.PM, FZ.PM),new FV(FZ.PL, FZ.PL),
                new FV(FZ.NL, FZ.NL), new FV(FZ.NL, FZ.NL), new FV(FZ.NM, FZ.NM), new FV(FZ.ZR, FZ.ZR),new FV(FZ.PM, FZ.PM),
                new FV(FZ.NL, FZ.NL), new FV(FZ.NL, FZ.NL), new FV(FZ.NL, FZ.NL), new FV(FZ.NM, FZ.NM),new FV(FZ.ZR, FZ.ZR));

        FLRS doubleChannelDifferentiator2 = new FLRS(new FV(FZ.ZR, FZ.ZR), new FV(FZ.NM, FZ.NM), new FV(FZ.NL, FZ.NL), new FV(FZ.NL, FZ.NL),new FV(FZ.NL, FZ.NL),
                new FV(FZ.PM, FZ.PM), new FV(FZ.ZR, FZ.ZR), new FV(FZ.NM, FZ.NM), new FV(FZ.NL, FZ.NL),new FV(FZ.NL, FZ.NL),
                new FV(FZ.PL, FZ.PL), new FV(FZ.PM, FZ.PM), new FV(FZ.ZR, FZ.ZR), new FV(FZ.NM, FZ.NM),new FV(FZ.NL, FZ.NL),
                new FV(FZ.PL, FZ.PL), new FV(FZ.PL, FZ.PL), new FV(FZ.PM, FZ.PM), new FV(FZ.ZR, FZ.ZR),new FV(FZ.NM, FZ.NM),
                new FV(FZ.PL, FZ.PL), new FV(FZ.PL, FZ.PL), new FV(FZ.PL, FZ.PL), new FV(FZ.PM, FZ.PM),new FV(FZ.ZR, FZ.ZR));


        FLRS adder = new FLRS(new FV(FZ.NL), new FV(FZ.NL), new FV(FZ.NL), new FV(FZ.NM), new FV(FZ.ZR),
                new FV(FZ.NL), new FV(FZ.NL), new FV(FZ.NM), new FV(FZ.ZR), new FV(FZ.PM),
                new FV(FZ.NL), new FV(FZ.NM), new FV(FZ.ZR), new FV(FZ.PM), new FV(FZ.PL),
                new FV(FZ.NM), new FV(FZ.ZR), new FV(FZ.PM), new FV(FZ.PL), new FV(FZ.PL),
                new FV(FZ.ZR), new FV(FZ.PM), new FV(FZ.PL), new FV(FZ.PL), new FV(FZ.PL));

        FLRS OneXOneDefaultTable = new FLRS(new FV(FZ.NL), new FV(FZ.NM), new FV(FZ.ZR), new FV(FZ.PM),new FV(FZ.PL));

        FLRS OneXTwoDefaultTable = new FLRS(new FV(FZ.NL,FZ.NL), new FV(FZ.NM,FZ.NM), new FV(FZ.ZR,FZ.ZR), new FV(FZ.PM,FZ.PM),new FV(FZ.PL,FZ.PL));

        reader.Print();
        doubleChannelAdder.Print();
        doubleChannelDifferentiator.Print();
        doubleChannelDifferentiator2.Print();
        adder.Print();
        OneXOneDefaultTable.Print();
        OneXTwoDefaultTable.Print();


        DataFuzzy p0 = new DataFuzzy();
        p0.SetName("P0");
        p0.SetValue(new Fuzzy(0.0F));
        pn.PlaceList.add(p0);

        DataFuzzy p1 = new DataFuzzy();
        p1.SetName("P1");
        pn.PlaceList.add(p1);

        DataFuzzy p2 = new DataFuzzy(); //from OETPN
        p2.SetName("P2");
        p2.SetValue(new Fuzzy(0.0F));
        pn.PlaceList.add(p2);

        DataFuzzy p3 = new DataFuzzy();
        p3.SetName("P3");
        pn.PlaceList.add(p3);

        DataFuzzy p4 = new DataFuzzy();  //from OETPN
        p4.SetName("P4");
        //p4.SetValue(new Fuzzy(0.55F));
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

        DataFuzzy p8 = new DataFuzzy();
        p8.SetName("P8");
        pn.PlaceList.add(p8);

        DataFuzzy p9 = new DataFuzzy();
        p9.SetName("P9");
        pn.PlaceList.add(p9);

        DataFuzzy p10 = new DataFuzzy();
        p10.SetName("P10");
        pn.PlaceList.add(p10);

        DataFuzzy p11 = new DataFuzzy();
        p11.SetName("P11");
        p11.SetValue(new Fuzzy(0.0F));
        pn.PlaceList.add(p11);

        DataFuzzy p12 = new DataFuzzy();
        p12.SetName("P12");
        p12.SetValue(new Fuzzy(0.0F));
        pn.PlaceList.add(p12);

        DataFuzzy p13 = new DataFuzzy();
        p13.SetName("P13");
        p13.SetValue(new Fuzzy(0.0F));
        pn.PlaceList.add(p13);

        DataFuzzy p14 = new DataFuzzy();
        p14.SetName("P14");
        pn.PlaceList.add(p14);

        DataTransfer p_o_1 = new DataTransfer();
        p_o_1.SetName("c");
        p_o_1.Value = new TransferOperation("localhost", "1080", "Cc_1_c"); //to OETPN
        pn.PlaceList.add(p_o_1);


        // T0 ------------------------------------------------
        PetriTransition t0 = new PetriTransition(pn);
        t0.TransitionName = "T0";
        t0.InputPlaceName.add("P0");


        Condition T0Ct1 = new Condition(t0, "P0", TransitionCondition.NotNull);

        GuardMapping grdT0 = new GuardMapping();
        grdT0.condition = T0Ct1;

        ArrayList<PlaceNameWithWeight> input0 = new ArrayList<>();
        input0.add(new PlaceNameWithWeight("P0", 1F));

        ArrayList<String> Output0 = new ArrayList<>();
        Output0.add("P1");


        grdT0.Activations.add(new Activation(t0, OneXOneDefaultTable, input0, TransitionOperation.FLRS, Output0));

        t0.GuardMappingList.add(grdT0);

        t0.Delay = 0;
        pn.Transitions.add(t0);



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

        ArrayList<PlaceNameWithWeight> input1 = new ArrayList<>();
        input1.add(new PlaceNameWithWeight("P1", 1F));
        input1.add(new PlaceNameWithWeight("P2", 1F));

        ArrayList<String> Output1 = new ArrayList<>();
        Output1.add("P3");


        grdT1.Activations.add(new Activation(t1, reader, input1, TransitionOperation.FLRS, Output1));

        t1.GuardMappingList.add(grdT1);

        t1.Delay = 0;
        pn.Transitions.add(t1);


        // T2 ------------------------------------------------
        PetriTransition t2 = new PetriTransition(pn);
        t2.TransitionName = "T2";
        t2.InputPlaceName.add("P3");
        t2.InputPlaceName.add("P4");

        Condition T2Ct1 = new Condition(t2, "P3", TransitionCondition.NotNull);
        Condition T2Ct2 = new Condition(t2, "P4", TransitionCondition.NotNull);
        T2Ct1.SetNextCondition(LogicConnector.AND, T2Ct2);

        GuardMapping grdT2 = new GuardMapping();
        grdT2.condition = T2Ct1;

        ArrayList<PlaceNameWithWeight> input2 = new ArrayList<>();
        input2.add(new PlaceNameWithWeight("P3", 1F));
        input2.add(new PlaceNameWithWeight("P4", 1F));


        ArrayList<String> Output2 = new ArrayList<>();
        Output2.add("P5");
        Output2.add("P6");


        grdT2.Activations.add(new Activation(t2, doubleChannelDifferentiator, input2, TransitionOperation.FLRS, Output2));

        t2.GuardMappingList.add(grdT2);

        t2.Delay = 0;
        pn.Transitions.add(t2);

        // T3 ------------------------------------------------
        PetriTransition t3 = new PetriTransition(pn);
        t3.TransitionName = "T3";
        t3.InputPlaceName.add("P5");
        t3.InputPlaceName.add("P14");

        Condition T3Ct1 = new Condition(t3, "P5", TransitionCondition.NotNull);
        Condition T3Ct2 = new Condition(t3, "P14", TransitionCondition.NotNull);
        T3Ct1.SetNextCondition(LogicConnector.AND, T3Ct2);

        GuardMapping grdT3 = new GuardMapping();
        grdT3.condition = T3Ct1;

        ArrayList<PlaceNameWithWeight> input3 = new ArrayList<>();
        input3.add(new PlaceNameWithWeight("P5", 0.8F));
        input3.add(new PlaceNameWithWeight("P14", 1F));

        ArrayList<String> Output3 = new ArrayList<>();
        Output3.add("P8");


        grdT3.Activations.add(new Activation(t3, adder, input3, TransitionOperation.FLRS, Output3));

        t3.GuardMappingList.add(grdT3);

        t3.Delay = 0;
        pn.Transitions.add(t3);


        // T4 ------------------------------------------------
        PetriTransition t4 = new PetriTransition(pn);
        t4.TransitionName = "T4";
        t4.InputPlaceName.add("P6");

        Condition T4Ct1 = new Condition(t4, "P6", TransitionCondition.NotNull);

        GuardMapping grdT4 = new GuardMapping();
        grdT4.condition = T4Ct1;

        ArrayList<PlaceNameWithWeight> input4 = new ArrayList<>();
        input4.add(new PlaceNameWithWeight("P6", 1F));

        ArrayList<String> Output4 = new ArrayList<>();
        Output4.add("P7");
        Output4.add("P12");

        grdT4.Activations.add(new Activation(t4, OneXTwoDefaultTable, input4, TransitionOperation.FLRS, Output4));

        t4.GuardMappingList.add(grdT4);

        t4.Delay = 1;
        pn.Transitions.add(t4);



        // T5 ------------------------------------------------
        PetriTransition t5 = new PetriTransition(pn);
        t5.TransitionName = "T5";
        t5.InputPlaceName.add("P8");
        t5.InputPlaceName.add("P11");

        Condition T5Ct1 = new Condition(t5, "P8", TransitionCondition.NotNull);
        Condition T5Ct2 = new Condition(t5, "P11", TransitionCondition.NotNull);
        T5Ct1.SetNextCondition(LogicConnector.AND, T5Ct2);

        GuardMapping grdT5 = new GuardMapping();
        grdT5.condition = T5Ct1;

        ArrayList<PlaceNameWithWeight> input5 = new ArrayList<>();
        input5.add(new PlaceNameWithWeight("P8", 1.2F));
        input5.add(new PlaceNameWithWeight("P11", 1F));


        ArrayList<String> Output5 = new ArrayList<>();
        Output5.add("P9");
        Output5.add("P10");


        grdT5.Activations.add(new Activation(t5, doubleChannelAdder, input5, TransitionOperation.FLRS, Output5));

        t5.GuardMappingList.add(grdT5);

        t5.Delay = 0;
        pn.Transitions.add(t5);


        // T6 ------------------------------------------------
        PetriTransition t6 = new PetriTransition(pn);
        t6.TransitionName = "T6";
        t6.InputPlaceName.add("P10");

        Condition T6Ct1 = new Condition(t6, "P10", TransitionCondition.NotNull);

        GuardMapping grdT6 = new GuardMapping();
        grdT6.condition = T6Ct1;

        ArrayList<PlaceNameWithWeight> input6 = new ArrayList<>();
        input6.add(new PlaceNameWithWeight("P10", 1F));

        ArrayList<String> Output6 = new ArrayList<>();
        Output6.add("P1");
        Output6.add("P11");


        grdT6.Activations.add(new Activation(t6, OneXTwoDefaultTable, input6, TransitionOperation.FLRS, Output6));

        t6.GuardMappingList.add(grdT6);

        t6.Delay = 1;
        pn.Transitions.add(t6);

// T7 ------------------------------------------------
        PetriTransition t7 = new PetriTransition(pn);
        t7.TransitionName = "T7";
        t7.InputPlaceName.add("P9");

        Condition T7Ct1 = new Condition(t6, "P9", TransitionCondition.NotNull);

        GuardMapping grdT7 = new GuardMapping();
        grdT7.condition = T7Ct1;

        grdT7.Activations.add(new Activation(t7, "P9", TransitionOperation.SendOverNetwork, "c"));

        t7.GuardMappingList.add(grdT7);

        t7.Delay = 0;
        pn.Transitions.add(t7);

        // -------------------------------------------

        PetriTransition t8 = new PetriTransition(pn);
        t8.TransitionName = "T8";
        t8.InputPlaceName.add("P12");

        Condition T8Ct1 = new Condition(t8, "P12", TransitionCondition.NotNull);

        GuardMapping grdT8 = new GuardMapping();

        grdT8.condition = T8Ct1;

        ArrayList<PlaceNameWithWeight> input8 = new ArrayList<>();
        input8.add(new PlaceNameWithWeight("P12", 1F));

        ArrayList<String> Output8 = new ArrayList<>();
        Output8.add("P13");

        grdT8.Activations.add(new Activation(t8, OneXOneDefaultTable, input8, TransitionOperation.FLRS, Output8));

        t8.GuardMappingList.add(grdT8);

        t8.Delay = 1;
        pn.Transitions.add(t8);

        // -------------------------------------------

        PetriTransition t9 = new PetriTransition(pn);
        t9.TransitionName = "T9";
        t9.InputPlaceName.add("P13");
        t9.InputPlaceName.add("P7");

        Condition T9Ct1 = new Condition(t9, "P13", TransitionCondition.NotNull);
        Condition T9Ct2 = new Condition(t9, "P7", TransitionCondition.NotNull);
        T9Ct1.SetNextCondition(LogicConnector.AND, T9Ct2);

        GuardMapping grdT9 = new GuardMapping();

        grdT9.condition = T9Ct1;

        ArrayList<PlaceNameWithWeight> input9 = new ArrayList<>();
        input9.add(new PlaceNameWithWeight("P13", 0.2F));
        input9.add(new PlaceNameWithWeight("P7", 1F));

        ArrayList<String> Output9 = new ArrayList<>();
        Output9.add("P14");

        grdT9.Activations.add(new Activation(t9, adder, input9, TransitionOperation.FLRS, Output9));

        t9.GuardMappingList.add(grdT9);

        t9.Delay = 0;
        pn.Transitions.add(t9);

        // -------------------------------------------

        // PetriTransition t3 = new PetriTransition(pn);
        // pn.Transitions.add(t3);

        System.out.println("PIDController started \n ------------------------------");
        pn.Delay = 100;
        pn.PrintingSpeed=10;
        pn.ShowLogInWindow=false;
        // pn.Start();

        PetriNetWindow frame = new PetriNetWindow(false);
        frame.petriNet = pn;
        frame.setVisible(true);
    }

}
