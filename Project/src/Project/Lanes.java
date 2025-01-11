package Project;

import Components.*;
import DataObjects.DataCar;
import DataObjects.DataCarQueue;
import DataObjects.DataString;
import DataObjects.DataTransfer;
import DataOnly.TransferOperation;
import Enumerations.LogicConnector;
import Enumerations.TransitionCondition;
import Enumerations.TransitionOperation;

import java.util.ArrayList;

public class Lanes {
    public static void main(String[] args) {
        
        PetriNet pn = new PetriNet();
        pn.PetriNetName = "Lanes Petri";
        pn.NetworkPort = 1080;

        //--------------------------------------------------------------------
        //-------------------------Strada Ferdinand---------------------------
        //--------------------------------------------------------------------

        //--------------------------------------------------------------------
        //-------------------------------Lane1--------------------------------
        //--------------------------------------------------------------------


        // -------------------------------Places--------------------------------
        DataCar p1 = new DataCar();
        p1.SetName("F_a1");
        pn.PlaceList.add(p1);

        DataCarQueue p2 = new DataCarQueue();
        p2.Value.Size = 3;
        p2.SetName("F_x1");
        pn.PlaceList.add(p2);

        DataCarQueue p3 = new DataCarQueue();
        p3.Value.Size = 3;
        p3.SetName("F_x2");
        pn.PlaceList.add(p3);

        DataCarQueue p4 = new DataCarQueue();
        p4.Value.Size = 3;
        p4.SetName("F_station");
        pn.PlaceList.add(p4);

        DataString p5 = new DataString();
        p5.SetName("FUsReqStation1");
        pn.PlaceList.add(p5);

        DataCar p6 = new DataCar();
        p6.SetName("F_T2");
        pn.PlaceList.add(p6);

        DataCar p7 = new DataCar();
        p7.SetName("F_tipografiei");
        pn.PlaceList.add(p7);

        DataCar p8 = new DataCar();
        p8.SetName("F_tipografiei_o");
        pn.PlaceList.add(p8);

        DataCarQueue p9 = new DataCarQueue();
        p9.Value.Size = 3;
        p9.SetName("F_x3");
        pn.PlaceList.add(p9);

        DataString p10 = new DataString();   // for first crossing
        p10.SetName("F_TL3");
        pn.PlaceList.add(p10);

        DataString p11 = new DataString();
        p11.SetName("FUsReq3");
        pn.PlaceList.add(p11);

        DataString p12 = new DataString();
        p12.SetName("F_PTL3");
        pn.PlaceList.add(p12);

        DataTransfer p13 = new DataTransfer();
        p13.SetName("OFReq3");
        p13.Value = new TransferOperation("localhost", "1082", "");
        pn.PlaceList.add(p13);

        DataCar p14 = new DataCar();
        p14.SetName("F_b2");
        pn.PlaceList.add(p14);

        DataCarQueue p15 = new DataCarQueue();
        p15.Value.Size = 3;
        p15.SetName("F_x4");
        pn.PlaceList.add(p15);

        DataCar p16 = new DataCar(); // intrare strada Andrei Saguna
        p16.SetName("F_saguna_i");
        pn.PlaceList.add(p16);

        DataCar p17 = new DataCar(); // intrare strada Andrei Saguna
        p17.SetName("F_saguna");
        pn.PlaceList.add(p17);

        DataTransfer p18 = new DataTransfer(); /// Locatie pt async controller ( Traffic jam)
        p18.SetName("F_OP4");
        p18.Value = new TransferOperation("localhost", "1083", "");
        pn.PlaceList.add(p18);

        DataString p19 = new DataString();   // pt semafor cu trecere dar fara req de la pietoni
        p19.SetName("F_TL4");
        pn.PlaceList.add(p19);

        DataCar p20 = new DataCar();
        p20.SetName("F_b4");
        pn.PlaceList.add(p20);

        DataString full = new DataString();
        full.SetName("full");
        full.SetValue("full");
        pn.ConstantPlaceList.add(full);

        DataString green= new DataString();
        green.SetName("green");
        green.SetValue("green");
        green.Printable= false;
        pn.ConstantPlaceList.add(green);

        DataString yellow= new DataString();
        yellow.SetName("yellow");
        yellow.SetValue("yellow");
        yellow.Printable= false;
        pn.ConstantPlaceList.add(yellow);

        DataString red= new DataString();
        red.SetName("red");
        red.SetValue("red");
        red.Printable= false;
        pn.ConstantPlaceList.add(red);
        // ----------------------------------------------------------------------------
        // ----------------------------Exit lane 1-------------------------------------
        // ----------------------------Ferdinand-------------------------------------

        DataCarQueue p23 = new DataCarQueue(); //p17.Printable = false;
        p23.Value.Size = 3;
        p23.SetName("F_o1");
        pn.PlaceList.add(p23);

        DataCar p22 = new DataCar(); //p18.Printable = false;
        p22.SetName("F_o1e");
        pn.PlaceList.add(p22);

        DataCarQueue p24 = new DataCarQueue(); //p17.Printable = false;
        p24.Value.Size = 3;
        p24.SetName("F_x5");
        pn.PlaceList.add(p24);

        // ----------------------------------------------------------------------------
        // ----------------------------Exit lane 1-------------------------------------
        // ----------------------------M. Viteazu-------------------------------------

        DataCarQueue p21 = new DataCarQueue(); //p17.Printable = false;
        p21.Value.Size = 3;
        p21.SetName("M_o1");
        pn.PlaceList.add(p17);

       // DataCar p22 = new DataCar(); //p18.Printable = false;
      //  p22.SetName("M_o1e");
       // pn.PlaceList.add(p18);

        // -------------------------------------------------------------------------------------------
        // --------------------------------Baritiu-Intersection---------------------------------------
        // -------------------------------------------------------------------------------------------

        DataCarQueue p25 = new DataCarQueue();
        p25.Value.Size = 3;
        p25.SetName("P_I1");
        pn.PlaceList.add(p25);

        DataCarQueue p26 = new DataCarQueue();
        p26.Value.Size = 3;
        p26.SetName("P_I2");
        pn.PlaceList.add(p26);

        DataCarQueue p27 = new DataCarQueue();
        p27.Value.Size = 3;
        p27.SetName("P_I3");
        pn.PlaceList.add(p27);

        DataCarQueue p28 = new DataCarQueue();
        p28.Value.Size = 3;
        p28.SetName("P_I4");
        pn.PlaceList.add(p28);

        // -------------------------------Transitions--------------------------------

        // T1 ------------------------------------------------
        PetriTransition t1 = new PetriTransition(pn);
        t1.TransitionName = "T_u1";
        t1.InputPlaceName.add("F_a1");

        Condition T1Ct1 = new Condition(t1, "F_a1", TransitionCondition.NotNull);
        Condition T1Ct2 = new Condition(t1, "F_x1", TransitionCondition.CanAddCars);
        T1Ct1.SetNextCondition(LogicConnector.AND, T1Ct2);

        GuardMapping grdT1 = new GuardMapping();
        grdT1.condition = T1Ct1;
        grdT1.Activations.add(new Activation(t1, "F_a1", TransitionOperation.AddElement, "F_x1"));
        t1.GuardMappingList.add(grdT1);

        t1.Delay = 0;
        pn.Transitions.add(t1);

        // T2 ------------------------------------------------
        PetriTransition t2 = new PetriTransition(pn);
        t2.TransitionName = "T_s1";
        t2.InputPlaceName.add("F_x1");

        Condition T2Ct1 = new Condition(t2, "F_x1", TransitionCondition.HaveBus);
        Condition T2Ct2 = new Condition(t2, "F_x1", TransitionCondition.HaveCarForMe);
        T2Ct1.SetNextCondition(LogicConnector.AND, T2Ct2);

        GuardMapping grdT2 = new GuardMapping();
        grdT2.condition = T2Ct1;
        grdT2.Activations.add(new Activation(t2, "F_x1", TransitionOperation.PopElementWithTargetToQueue, "F_station"));
        t2.GuardMappingList.add(grdT2);

        t2.Delay = 0;
        pn.Transitions.add(t2);

        // T3 ------------------------------------------------
        PetriTransition t3 = new PetriTransition(pn);
        t3.TransitionName = "T_es";
        t3.InputPlaceName.add("F_station");
        t3.InputPlaceName.add("FUsReqStation1");

        Condition T3Ct1 = new Condition(t3, "F_station", TransitionCondition.HaveBus);
        Condition T3Ct2 = new Condition(t3, "FUsReqStation1", TransitionCondition.NotNull);
        T3Ct1.SetNextCondition(LogicConnector.AND, T3Ct2);

        ArrayList<String> input = new ArrayList<>();
        input.add("F_station");
        input.add("FUsReqStation1");

        GuardMapping grdT3 = new GuardMapping();
        grdT3.condition = T3Ct1;
        grdT3.Activations.add(new Activation(t2, input, TransitionOperation.PopElementWithTargetToQueue, "F_x2")); // or should it be popBusToQueue
        t3.GuardMappingList.add(grdT3);

        t3.Delay = 0;
        pn.Transitions.add(t3);

        // T4 ------------------------------------------------
        PetriTransition t4 = new PetriTransition(pn);
        t4.TransitionName = "T_1";
        t4.InputPlaceName.add("F_x1");
        Condition T4Ct1 = new Condition(t4, "F_x1", TransitionCondition.HaveCarForMe);
        Condition T4Ct2 = new Condition(t4, "F_x2", TransitionCondition.CanAddCars);
        T4Ct1.SetNextCondition(LogicConnector.AND, T4Ct2);


        GuardMapping grdT4 = new GuardMapping();
        grdT4.condition = T4Ct1;
        grdT4.Activations.add(new Activation(t4, "F_x2", TransitionOperation.PopElementWithTargetToQueue, "F_x2"));
        t4.GuardMappingList.add(grdT4);

        t4.Delay = 0;
        pn.Transitions.add(t4);


        // T5 ------------------------------------------------
        PetriTransition t5 = new PetriTransition(pn);
        t5.TransitionName = "T_2";
        t5.InputPlaceName.add("F_x2");

        Condition T5Ct1 = new Condition(t5, "F_x2", TransitionCondition.HaveCarForMe);

        GuardMapping grdT5 = new GuardMapping();
        grdT5.condition = T5Ct1;
        grdT5.Activations.add(new Activation(t5, "F_x2", TransitionOperation.PopElementWithTarget, "F_T2"));
        t5.GuardMappingList.add(grdT5);

        t5.Delay = 0;
        pn.Transitions.add(t5);

        // T6 ------------------------------------------------
        PetriTransition t6 = new PetriTransition(pn);
        t6.TransitionName = "T_tip1";
        t6.InputPlaceName.add("F_x2");

        Condition T6Ct1 = new Condition(t6, "F_x2", TransitionCondition.HaveCarForMe);
//        Condition T6Ct2 = new Condition(t6, "F_tipografiei", TransitionCondition.CanAddCars);
//        T6Ct1.SetNextCondition(LogicConnector.AND, T6Ct2);

        GuardMapping grdT6 = new GuardMapping();
        grdT6.condition = T6Ct1;
        grdT6.Activations.add(new Activation(t6, "F_x2", TransitionOperation.Move, "F_tipografiei"));
        t6.GuardMappingList.add(grdT6);

        t6.Delay = 0;
        pn.Transitions.add(t6);

        // T7 ------------------------------------------------
        PetriTransition t7 = new PetriTransition(pn);
        t7.TransitionName = "T_tip2";
        t7.InputPlaceName.add("F_tipografiei");

        Condition T7Ct1 = new Condition(t7, "F_tipografiei", TransitionCondition.NotNull);

        GuardMapping grdT7 = new GuardMapping();
        grdT7.condition = T7Ct1;
        grdT7.Activations.add(new Activation(t7, "F_tipografiei", TransitionOperation.Move, "F_tipografiei_o"));
        t7.GuardMappingList.add(grdT7);

        t7.Delay = 0;
        pn.Transitions.add(t7);

        // T8 ------------------------------------------------
        PetriTransition t8 = new PetriTransition(pn);
        t8.TransitionName = "T_u2";
        t8.InputPlaceName.add("F_T2");

        Condition T8Ct1 = new Condition(t8, "F_T2", TransitionCondition.NotNull);
        Condition T8Ct2 = new Condition(t8, "F_x3", TransitionCondition.CanAddCars);
        T8Ct1.SetNextCondition(LogicConnector.AND, T8Ct2);


        GuardMapping grdT8 = new GuardMapping();
        grdT8.condition = T8Ct1;
        grdT8.Activations.add(new Activation(t8, "F_T2", TransitionOperation.AddElement, "F_x3"));
        t8.GuardMappingList.add(grdT8);

        t8.Delay = 0;
        pn.Transitions.add(t8);


        // T9 ------------------------------------------------
        PetriTransition t9 = new PetriTransition(pn);
        t9.TransitionName = "T_e3";
        t9.InputPlaceName.add("F_x3");
        t9.InputPlaceName.add("F_TL3");
        t9.InputPlaceName.add("F_PTL3");
        t9.InputPlaceName.add("FUsReq3");


        Condition T9Ct1 = new Condition(t9, "F_TL3", TransitionCondition.Equal,"green");
        Condition T9Ct2 = new Condition(t9, "F_x3", TransitionCondition.HaveCar);
        T9Ct1.SetNextCondition(LogicConnector.AND, T9Ct2);

        GuardMapping grdT9 = new GuardMapping();
        grdT9.condition= T9Ct1;
        grdT9.Activations.add(new Activation(t9, "F_x3", TransitionOperation.PopElementWithoutTarget, "F_b2"));
        grdT9.Activations.add(new Activation(t9, "F_TL3", TransitionOperation.Move, "F_TL3"));
        grdT9.Activations.add(new Activation(t9, "F_PTL3", TransitionOperation.Move, "F_PTL3"));

        t9.GuardMappingList.add(grdT9);

        Condition T9Ct3 = new Condition(t9, "FUsReq3", TransitionCondition.NotNull);

        GuardMapping grdT92 = new GuardMapping();
        grdT92.condition= T9Ct3;
        grdT92.Activations.add(new Activation(t9, "FUsReq3", TransitionOperation.SendOverNetwork, "OFReq3"));
        grdT92.Activations.add(new Activation(t9, "F_TL3", TransitionOperation.Move, "F_TL3"));
        grdT92.Activations.add(new Activation(t9, "F_PTL3", TransitionOperation.Move, "F_PTL3"));

        t9.GuardMappingList.add(grdT92);

        t9.Delay = 1;
        pn.Transitions.add(t9);

        // T10 ------------------------------------------------
        PetriTransition t10 = new PetriTransition(pn);
        t10.TransitionName = "T_u3";
        t10.InputPlaceName.add("F_b2");

        Condition T10Ct1 = new Condition(t10, "F_b2", TransitionCondition.NotNull);
        Condition T10Ct2 = new Condition(t10, "F_x4", TransitionCondition.CanAddCars);
        T10Ct1.SetNextCondition(LogicConnector.AND, T10Ct2);


        GuardMapping grdT10 = new GuardMapping();
        grdT10.condition = T10Ct1;
        grdT10.Activations.add(new Activation(t10, "F_b2", TransitionOperation.AddElement, "F_x4"));
        t10.GuardMappingList.add(grdT10);

        t10.Delay = 0;
        pn.Transitions.add(t10);

        // T11 ------------------------------------------------
        PetriTransition t11 = new PetriTransition(pn);
        t11.TransitionName = "T_sag1";
        t11.InputPlaceName.add("F_saguna_i");

        Condition T11Ct1 = new Condition(t11, "F_saguna_i", TransitionCondition.NotNull);

        GuardMapping grdT11 = new GuardMapping();
        grdT11.condition = T11Ct1;
        grdT11.Activations.add(new Activation(t11, "F_saguna_i", TransitionOperation.Move, "F_saguna"));
        t11.GuardMappingList.add(grdT11);

        t11.Delay = 0;
        pn.Transitions.add(t11);

        // T12 ------------------------------------------------
        PetriTransition t12 = new PetriTransition(pn);
        t12.TransitionName = "T_sag2";
        t12.InputPlaceName.add("F_saguna");

        Condition T12Ct1 = new Condition(t12, "F_saguna", TransitionCondition.NotNull);
        Condition T12Ct2 = new Condition(t12, "F_x4", TransitionCondition.CanAddCars);
        T12Ct1.SetNextCondition(LogicConnector.AND, T12Ct2);


        GuardMapping grdT12 = new GuardMapping();
        grdT12.condition = T12Ct1;
        grdT12.Activations.add(new Activation(t12, "F_saguna", TransitionOperation.AddElement, "F_x4"));
        t12.GuardMappingList.add(grdT12);

        t12.Delay = 0;
        pn.Transitions.add(t12);


        // T13 ------------------------------------------------
        PetriTransition t13 = new PetriTransition(pn);
        t13.TransitionName = "T_e4";
        t13.InputPlaceName.add("F_x4");
        t13.InputPlaceName.add("F_TL4");

        Condition T13Ct1 = new Condition(t13, "F_TL4", TransitionCondition.Equal, "green");
        Condition T13Ct2 = new Condition(t13, "F_x4", TransitionCondition.HaveCar);
        T13Ct1.SetNextCondition(LogicConnector.AND, T13Ct2);

        GuardMapping grdT13 = new GuardMapping();
        grdT13.condition = T13Ct1;
        grdT13.Activations.add(new Activation(t13, "F_x4", TransitionOperation.PopElementWithoutTarget, "F_b4"));
        grdT13.Activations.add(new Activation(t13, "F_TL4", TransitionOperation.Move, "F_TL4"));
        t13.GuardMappingList.add(grdT13);

        t13.Delay = 1;
        pn.Transitions.add(t13);

        // T14 ------------------------------------------------
        PetriTransition t14 = new PetriTransition(pn);
        t14.TransitionName = "T_out4";
        t14.InputPlaceName.add("F_b2");
        t14.InputPlaceName.add("F_x4");
        t14.IsAsync = true;

        Condition T14Ct1 = new Condition(t14, "F_b2", TransitionCondition.NotNull);
        Condition T14Ct2 = new Condition(t14, "F_x4", TransitionCondition.CanNotAddCars);
        T14Ct1.SetNextCondition(LogicConnector.AND, T14Ct2);

        GuardMapping grdT14 = new GuardMapping();
        grdT14.condition = T14Ct1;
        grdT14.Activations.add(new Activation(t14, "full", TransitionOperation.PopElementWithoutTarget, "F_OP4"));
        t14.GuardMappingList.add(grdT14);

        t14.Delay = 0;
        pn.Transitions.add(t14);


        // --------------------------------------firstpart-------------------------------------------

        // T15 ------------------------------------------------
        PetriTransition t15 = new PetriTransition(pn);
        t15.TransitionName = "T_i1";
        t15.InputPlaceName.add("F_b4");
        t15.InputPlaceName.add("P_I1");

        Condition T15Ct1 = new Condition(t15, "F_b4", TransitionCondition.NotNull);
        Condition T15Ct2 = new Condition(t15, "P_I1", TransitionCondition.CanAddCars);
        T15Ct1.SetNextCondition(LogicConnector.AND, T15Ct2);

        GuardMapping grdT15 = new GuardMapping();
        grdT15.condition = T15Ct1;
        grdT15.Activations.add(new Activation(t15, "F_b4", TransitionOperation.AddElement, "P_I1"));
        t15.GuardMappingList.add(grdT15);

        t15.Delay = 0;
        pn.Transitions.add(t15);

        // T16-----------------------------------------------------------
        PetriTransition t16 = new PetriTransition(pn);
        t16.TransitionName = "T_g2";
        t16.InputPlaceName.add("P_I1");
        t16.InputPlaceName.add("M_o1");

        Condition T16Ct1 = new Condition(t16, "P_I1", TransitionCondition.HaveCarForMe);
        Condition T16Ct2 = new Condition(t16, "M_o1", TransitionCondition.CanAddCars);
        T16Ct1.SetNextCondition(LogicConnector.AND, T16Ct2);

        GuardMapping grdT16 = new GuardMapping();
        grdT16.condition = T16Ct1;
        grdT16.Activations.add(new Activation(t16, "P_I1", TransitionOperation.PopElementWithTargetToQueue, "M_o1"));
        t16.GuardMappingList.add(grdT16);

        t16.Delay = 1;
        pn.Transitions.add(t16);

        // T17----------------------------------------------------------------
        PetriTransition t17 = new PetriTransition(pn);
        t17.TransitionName = "T_g2e";
        t17.InputPlaceName.add("M_o1");

        Condition T17Ct1 = new Condition(t17, "M_o1", TransitionCondition.HaveCar);

        GuardMapping grdT17 = new GuardMapping();
        grdT17.condition = T17Ct1;
        grdT17.Activations.add(new Activation(t17, "M_o1", TransitionOperation.PopElementWithoutTarget, "M_o1e"));
        t17.GuardMappingList.add(grdT10);

        t17.Delay = 0;
        pn.Transitions.add(t17);





        // -------------------------------------------------------------------------------------
        // ----------------------------PNStart-------------------------------------------------
        // -------------------------------------------------------------------------------------

        System.out.println("Lanes started \n ------------------------------");
        pn.Delay = 2000;
        // pn.Start();

        PetriNetWindow frame = new PetriNetWindow(false);
        frame.petriNet = pn;
        frame.setVisible(true);


    }
}
