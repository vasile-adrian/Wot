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
        DataCar RF_a = new DataCar();
        RF_a.SetName("RF_a");
        pn.PlaceList.add(RF_a);

        DataCarQueue RF_x1 = new DataCarQueue();
        RF_x1.Value.Size = 3;
        RF_x1.SetName("RF_x1");
        pn.PlaceList.add(RF_x1);

        DataCarQueue RF_x2 = new DataCarQueue();
        RF_x2.Value.Size = 3;
        RF_x2.SetName("RF_x2");
        pn.PlaceList.add(RF_x2);

        DataCarQueue RF_station = new DataCarQueue();
        RF_station.Value.Size = 3;
        RF_station.SetName("RF_station");
        pn.PlaceList.add(RF_station);

        DataString RFUsReqStation1 = new DataString();
        RFUsReqStation1.SetName("RFUsReqStation1");
        pn.PlaceList.add(RFUsReqStation1);

        DataCar RF_T2 = new DataCar();
        RF_T2.SetName("RF_T2");
        pn.PlaceList.add(RF_T2);

        DataCar RF_tipografiei = new DataCar();
        RF_tipografiei.SetName("RF_tipografiei");
        pn.PlaceList.add(RF_tipografiei);

        DataCar RF_tipografiei_o = new DataCar();
        RF_tipografiei_o.SetName("RF_tipografiei_o");
        pn.PlaceList.add(RF_tipografiei_o);

        DataCarQueue RF_x3 = new DataCarQueue();
        RF_x3.Value.Size = 3;
        RF_x3.SetName("RF_x3");
        pn.PlaceList.add(RF_x3);

        DataString RF_TL3 = new DataString();   // for first crossing
        RF_TL3.SetName("RF_TL3");
        pn.PlaceList.add(RF_TL3);

        DataString RFUsReq3 = new DataString();
        RFUsReq3.SetName("RFUsReq3");
        pn.PlaceList.add(RFUsReq3);

        DataString RF_PTL3 = new DataString();
        RF_PTL3.SetName("RF_PTL3");
        pn.PlaceList.add(RF_PTL3);

        DataTransfer ORFReq3 = new DataTransfer();
        ORFReq3.SetName("ORFReq3");
        ORFReq3.Value = new TransferOperation("localhost", "1082", "");
        pn.PlaceList.add(ORFReq3);

        DataCar RF_b2 = new DataCar();
        RF_b2.SetName("RF_b2");
        pn.PlaceList.add(RF_b2);

        DataCarQueue RF_x4 = new DataCarQueue();
        RF_x4.Value.Size = 3;
        RF_x4.SetName("RF_x4");
        pn.PlaceList.add(RF_x4);

        DataCar RF_saguna_i = new DataCar(); // intrare strada Andrei Saguna
        RF_saguna_i.SetName("RF_saguna_i");
        pn.PlaceList.add(RF_saguna_i);

        DataCar RF_saguna = new DataCar(); // intrare strada Andrei Saguna
        RF_saguna.SetName("RF_saguna");
        pn.PlaceList.add(RF_saguna);

        DataTransfer RF_OP4 = new DataTransfer(); /// Locatie pt async controller ( Traffic jam)
        RF_OP4.SetName("RF_OP4");
        RF_OP4.Value = new TransferOperation("localhost", "1083", "");
        pn.PlaceList.add(RF_OP4);

        DataString RF_TL4 = new DataString();   // pt semafor cu trecere dar fara req de la pietoni
        RF_TL4.SetName("RF_TL4");
        pn.PlaceList.add(RF_TL4);

        DataCar RF_b4 = new DataCar();
        RF_b4.SetName("RF_b4");
        pn.PlaceList.add(RF_b4);

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
        // ----------------------------Exit lane-------------------------------------
        // -----------------------------Ferdinand-------------------------------------

        DataCarQueue RF_o = new DataCarQueue();
        RF_o.Value.Size = 3;
        RF_o.SetName("RF_o");
        pn.PlaceList.add(RF_o);

        DataCar RF_oe = new DataCar();
        RF_oe.SetName("RF_oe");
        pn.PlaceList.add(RF_oe);

        DataCarQueue RF_x5 = new DataCarQueue();
        RF_x5.Value.Size = 3;
        RF_x5.SetName("RF_x5");
        pn.PlaceList.add(RF_x5);

        DataCar RF_b5 = new DataCar();
        RF_b5.SetName("RF_b5");
        pn.PlaceList.add(RF_b5);

        DataCar RF_oct = new DataCar();
        RF_oct.SetName("RF_oct");
        pn.PlaceList.add(RF_oct);

        DataCar RF_oct_o = new DataCar();
        RF_oct_o.SetName("RF_oct_o");
        pn.PlaceList.add(RF_oct_o);

        DataCar RF_o2 = new DataCar();
        RF_o2.SetName("RF_o2");
        pn.PlaceList.add(RF_o2);

        DataCar RF_o2e = new DataCar();
        RF_o2e.SetName("RF_o2e");
        pn.PlaceList.add(RF_o2e);

        DataCarQueue P_I = new DataCarQueue();
        P_I.Value.Size = 3;
        P_I.SetName("P_I");
        pn.PlaceList.add(P_I);


        // -------------------------------Transitions--------------------------------

        // T_RF_u1 ------------------------------------------------
        PetriTransition T_RF_u1 = new PetriTransition(pn);
        T_RF_u1.TransitionName = "T_RF_u1";
        T_RF_u1.InputPlaceName.add("RF_a");

        Condition T_RF_u1Ct1 = new Condition(T_RF_u1, "RF_a", TransitionCondition.NotNull);
        Condition T_RF_u1Ct2 = new Condition(T_RF_u1, "RF_x1", TransitionCondition.CanAddCars);
        T_RF_u1Ct1.SetNextCondition(LogicConnector.AND, T_RF_u1Ct2);

        GuardMapping grdT_RF_u1 = new GuardMapping();
        grdT_RF_u1.condition = T_RF_u1Ct1;
        grdT_RF_u1.Activations.add(new Activation(T_RF_u1, "RF_a", TransitionOperation.AddElement, "RF_x1"));
        T_RF_u1.GuardMappingList.add(grdT_RF_u1);

        T_RF_u1.Delay = 0;
        pn.Transitions.add(T_RF_u1);

        // T_RF_s ------------------------------------------------
        PetriTransition T_RF_s = new PetriTransition(pn);
        T_RF_s.TransitionName = "T_RF_s";
        T_RF_s.InputPlaceName.add("RF_x1");

        Condition T_RF_sCt1 = new Condition(T_RF_s, "RF_x1", TransitionCondition.HaveBus);
        Condition T_RF_sCt2 = new Condition(T_RF_s, "RF_x1", TransitionCondition.HaveCarForMe);
        T_RF_sCt1.SetNextCondition(LogicConnector.AND, T_RF_sCt2);

        GuardMapping grdT_RF_s = new GuardMapping();
        grdT_RF_s.condition = T_RF_sCt1;
        grdT_RF_s.Activations.add(new Activation(T_RF_s, "RF_x1", TransitionOperation.PopElementWithTargetToQueue, "RF_station"));
        T_RF_s.GuardMappingList.add(grdT_RF_s);

        T_RF_s.Delay = 0;
        pn.Transitions.add(T_RF_s);

        // T_RF_es ------------------------------------------------
        PetriTransition T_RF_es = new PetriTransition(pn);
        T_RF_es.TransitionName = "T_RF_es";
        T_RF_es.InputPlaceName.add("RF_station");
        T_RF_es.InputPlaceName.add("RFUsReqStation1");

        Condition T_RF_esCt1 = new Condition(T_RF_es, "RF_station", TransitionCondition.HaveBus);
        Condition T_RF_esCt2 = new Condition(T_RF_es, "RFUsReqStation1", TransitionCondition.NotNull);
        T_RF_esCt1.SetNextCondition(LogicConnector.AND, T_RF_esCt2);

        ArrayList<String> i_RF_station = new ArrayList<>();
        i_RF_station.add("RF_station");
        i_RF_station.add("RFUsReqStation1");

        GuardMapping grdT_RF_es = new GuardMapping();
        grdT_RF_es.condition = T_RF_esCt1;
        grdT_RF_es.Activations.add(new Activation(T_RF_es, i_RF_station, TransitionOperation.PopElementWithTargetToQueue, "RF_x2")); // or should it be popBusToQueue
        T_RF_es.GuardMappingList.add(grdT_RF_es);

        T_RF_es.Delay = 0;
        pn.Transitions.add(T_RF_es);

        // T_RF_1 ------------------------------------------------
        PetriTransition T_RF_1 = new PetriTransition(pn);
        T_RF_1.TransitionName = "T_RF_1";
        T_RF_1.InputPlaceName.add("RF_x1");
        Condition T_RF_1Ct1 = new Condition(T_RF_1, "RF_x1", TransitionCondition.HaveCarForMe);
        Condition T_RF_1Ct2 = new Condition(T_RF_1, "RF_x2", TransitionCondition.CanAddCars);
        T_RF_1Ct1.SetNextCondition(LogicConnector.AND, T_RF_1Ct2);


        GuardMapping grdT_RF_1 = new GuardMapping();
        grdT_RF_1.condition = T_RF_1Ct1;
        grdT_RF_1.Activations.add(new Activation(T_RF_1, "RF_x2", TransitionOperation.PopElementWithTargetToQueue, "RF_x2"));
        T_RF_1.GuardMappingList.add(grdT_RF_1);

        T_RF_1.Delay = 0;
        pn.Transitions.add(T_RF_1);


        // T_RF_2 ------------------------------------------------
        PetriTransition T_RF_2 = new PetriTransition(pn);
        T_RF_2.TransitionName = "T_RF_2";
        T_RF_2.InputPlaceName.add("RF_x2");

        Condition T_RF_2Ct1 = new Condition(T_RF_2, "RF_x2", TransitionCondition.HaveCarForMe);

        GuardMapping grdT_RF_2 = new GuardMapping();
        grdT_RF_2.condition = T_RF_2Ct1;
        grdT_RF_2.Activations.add(new Activation(T_RF_2, "RF_x2", TransitionOperation.PopElementWithTarget, "RF_T2"));
        T_RF_2.GuardMappingList.add(grdT_RF_2);

        T_RF_2.Delay = 0;
        pn.Transitions.add(T_RF_2);

        // T_RF_tip1 ------------------------------------------------
        PetriTransition T_RF_tip1 = new PetriTransition(pn);
        T_RF_tip1.TransitionName = "T_RF_tip1";
        T_RF_tip1.InputPlaceName.add("RF_x2");

        Condition T_RF_tip1Ct1 = new Condition(T_RF_tip1, "RF_x2", TransitionCondition.HaveCarForMe);
//        Condition T_RF_tip1Ct2 = new Condition(T_RF_tip1, "RF_tipografiei", TransitionCondition.CanAddCars);
//        T_RF_tip1Ct1.SetNextCondition(LogicConnector.AND, T_RF_tip1Ct2);

        GuardMapping grdT_RF_tip1 = new GuardMapping();
        grdT_RF_tip1.condition = T_RF_tip1Ct1;
        grdT_RF_tip1.Activations.add(new Activation(T_RF_tip1, "RF_x2", TransitionOperation.Move, "RF_tipografiei"));
        T_RF_tip1.GuardMappingList.add(grdT_RF_tip1);

        T_RF_tip1.Delay = 0;
        pn.Transitions.add(T_RF_tip1);

        // T_RF_tip2 ------------------------------------------------
        PetriTransition T_RF_tip2 = new PetriTransition(pn);
        T_RF_tip2.TransitionName = "T_RF_tip2";
        T_RF_tip2.InputPlaceName.add("RF_tipografiei");

        Condition T_RF_tip2Ct1 = new Condition(T_RF_tip2, "RF_tipografiei", TransitionCondition.NotNull);

        GuardMapping grdT_RF_tip2 = new GuardMapping();
        grdT_RF_tip2.condition = T_RF_tip2Ct1;
        grdT_RF_tip2.Activations.add(new Activation(T_RF_tip2, "RF_tipografiei", TransitionOperation.Move, "RF_tipografiei_o"));
        T_RF_tip2.GuardMappingList.add(grdT_RF_tip2);

        T_RF_tip2.Delay = 0;
        pn.Transitions.add(T_RF_tip2);

        // T_RF_u2 ------------------------------------------------
        PetriTransition T_RF_u2 = new PetriTransition(pn);
        T_RF_u2.TransitionName = "T_RF_u2";
        T_RF_u2.InputPlaceName.add("RF_T2");

        Condition T_RF_u2Ct1 = new Condition(T_RF_u2, "RF_T2", TransitionCondition.NotNull);
        Condition T_RF_u2Ct2 = new Condition(T_RF_u2, "RF_x3", TransitionCondition.CanAddCars);
        T_RF_u2Ct1.SetNextCondition(LogicConnector.AND, T_RF_u2Ct2);


        GuardMapping grdT_RF_u2 = new GuardMapping();
        grdT_RF_u2.condition = T_RF_u2Ct1;
        grdT_RF_u2.Activations.add(new Activation(T_RF_u2, "RF_T2", TransitionOperation.AddElement, "RF_x3"));
        T_RF_u2.GuardMappingList.add(grdT_RF_u2);

        T_RF_u2.Delay = 0;
        pn.Transitions.add(T_RF_u2);


        // T_RF_e3 ------------------------------------------------
        PetriTransition T_RF_e3 = new PetriTransition(pn);
        T_RF_e3.TransitionName = "T_RF_e3";
        T_RF_e3.InputPlaceName.add("RF_x3");
        T_RF_e3.InputPlaceName.add("RF_TL3");
        T_RF_e3.InputPlaceName.add("RF_PTL3");
        T_RF_e3.InputPlaceName.add("RFUsReq3");


        Condition T_RF_e3Ct1 = new Condition(T_RF_e3, "RF_TL3", TransitionCondition.Equal,"green");
        Condition T_RF_e3Ct2 = new Condition(T_RF_e3, "RF_x3", TransitionCondition.HaveCar);
        T_RF_e3Ct1.SetNextCondition(LogicConnector.AND, T_RF_e3Ct2);

        GuardMapping grdT_RF_e3 = new GuardMapping();
        grdT_RF_e3.condition= T_RF_e3Ct1;
        grdT_RF_e3.Activations.add(new Activation(T_RF_e3, "RF_x3", TransitionOperation.PopElementWithoutTarget, "RF_b2"));
        grdT_RF_e3.Activations.add(new Activation(T_RF_e3, "RF_TL3", TransitionOperation.Move, "RF_TL3"));
        grdT_RF_e3.Activations.add(new Activation(T_RF_e3, "RF_PTL3", TransitionOperation.Move, "RF_PTL3"));

        T_RF_e3.GuardMappingList.add(grdT_RF_e3);

        Condition T_RF_e3Ct3 = new Condition(T_RF_e3, "RFUsReq3", TransitionCondition.NotNull);

        GuardMapping grdT_RF_e32 = new GuardMapping();
        grdT_RF_e32.condition= T_RF_e3Ct3;
        grdT_RF_e32.Activations.add(new Activation(T_RF_e3, "RFUsReq3", TransitionOperation.SendOverNetwork, "ORFReq3"));
        grdT_RF_e32.Activations.add(new Activation(T_RF_e3, "RF_TL3", TransitionOperation.Move, "RF_TL3"));
        grdT_RF_e32.Activations.add(new Activation(T_RF_e3, "RF_PTL3", TransitionOperation.Move, "RF_PTL3"));

        T_RF_e3.GuardMappingList.add(grdT_RF_e32);

        T_RF_e3.Delay = 1;
        pn.Transitions.add(T_RF_e3);

        // T_RF_u3 ------------------------------------------------
        PetriTransition T_RF_u3 = new PetriTransition(pn);
        T_RF_u3.TransitionName = "T_RF_u3";
        T_RF_u3.InputPlaceName.add("RF_b2");

        Condition T_RF_u3Ct1 = new Condition(T_RF_u3, "RF_b2", TransitionCondition.NotNull);
        Condition T_RF_u3Ct2 = new Condition(T_RF_u3, "RF_x4", TransitionCondition.CanAddCars);
        T_RF_u3Ct1.SetNextCondition(LogicConnector.AND, T_RF_u3Ct2);


        GuardMapping grdT_RF_u3 = new GuardMapping();
        grdT_RF_u3.condition = T_RF_u3Ct1;
        grdT_RF_u3.Activations.add(new Activation(T_RF_u3, "RF_b2", TransitionOperation.AddElement, "RF_x4"));
        T_RF_u3.GuardMappingList.add(grdT_RF_u3);

        T_RF_u3.Delay = 0;
        pn.Transitions.add(T_RF_u3);

        // T_RF_sag1 ------------------------------------------------
        PetriTransition T_RF_sag1 = new PetriTransition(pn);
        T_RF_sag1.TransitionName = "T_RF_sag1";
        T_RF_sag1.InputPlaceName.add("RF_saguna_i");

        Condition T_RF_sag1Ct1 = new Condition(T_RF_sag1, "RF_saguna_i", TransitionCondition.NotNull);

        GuardMapping grdT_RF_sag1 = new GuardMapping();
        grdT_RF_sag1.condition = T_RF_sag1Ct1;
        grdT_RF_sag1.Activations.add(new Activation(T_RF_sag1, "RF_saguna_i", TransitionOperation.Move, "RF_saguna"));
        T_RF_sag1.GuardMappingList.add(grdT_RF_sag1);

        T_RF_sag1.Delay = 0;
        pn.Transitions.add(T_RF_sag1);

        // T_RF_sag2 ------------------------------------------------
        PetriTransition T_RF_sag2 = new PetriTransition(pn);
        T_RF_sag2.TransitionName = "T_RF_sag2";
        T_RF_sag2.InputPlaceName.add("RF_saguna");

        Condition T_RF_sag2Ct1 = new Condition(T_RF_sag2, "RF_saguna", TransitionCondition.NotNull);
        Condition T_RF_sag2Ct2 = new Condition(T_RF_sag2, "RF_x4", TransitionCondition.CanAddCars);
        T_RF_sag2Ct1.SetNextCondition(LogicConnector.AND, T_RF_sag2Ct2);


        GuardMapping grdT_RF_sag2 = new GuardMapping();
        grdT_RF_sag2.condition = T_RF_sag2Ct1;
        grdT_RF_sag2.Activations.add(new Activation(T_RF_sag2, "RF_saguna", TransitionOperation.AddElement, "RF_x4"));
        T_RF_sag2.GuardMappingList.add(grdT_RF_sag2);

        T_RF_sag2.Delay = 0;
        pn.Transitions.add(T_RF_sag2);


        // T_RF_e4 ------------------------------------------------
        PetriTransition T_RF_e4 = new PetriTransition(pn);
        T_RF_e4.TransitionName = "T_RF_e4";
        T_RF_e4.InputPlaceName.add("RF_x4");
        T_RF_e4.InputPlaceName.add("RF_TL4");

        Condition T_RF_e4Ct1 = new Condition(T_RF_e4, "RF_TL4", TransitionCondition.Equal, "green");
        Condition T_RF_e4Ct2 = new Condition(T_RF_e4, "RF_x4", TransitionCondition.HaveCar);
        T_RF_e4Ct1.SetNextCondition(LogicConnector.AND, T_RF_e4Ct2);

        GuardMapping grdT_RF_e4 = new GuardMapping();
        grdT_RF_e4.condition = T_RF_e4Ct1;
        grdT_RF_e4.Activations.add(new Activation(T_RF_e4, "RF_x4", TransitionOperation.PopElementWithoutTarget, "RF_b4"));
        grdT_RF_e4.Activations.add(new Activation(T_RF_e4, "RF_TL4", TransitionOperation.Move, "RF_TL4"));
        T_RF_e4.GuardMappingList.add(grdT_RF_e4);

        T_RF_e4.Delay = 1;
        pn.Transitions.add(T_RF_e4);

        // T_RF_out4 ------------------------------------------------
        PetriTransition T_RF_out4 = new PetriTransition(pn);
        T_RF_out4.TransitionName = "T_RF_out4";
        T_RF_out4.InputPlaceName.add("RF_b2");
        T_RF_out4.InputPlaceName.add("RF_x4");
        T_RF_out4.IsAsync = true;

        Condition T_RF_out4Ct1 = new Condition(T_RF_out4, "RF_b2", TransitionCondition.NotNull);
        Condition T_RF_out4Ct2 = new Condition(T_RF_out4, "RF_x4", TransitionCondition.CanNotAddCars);
        T_RF_out4Ct1.SetNextCondition(LogicConnector.AND, T_RF_out4Ct2);

        GuardMapping grdT_RF_out4 = new GuardMapping();
        grdT_RF_out4.condition = T_RF_out4Ct1;
        grdT_RF_out4.Activations.add(new Activation(T_RF_out4, "full", TransitionOperation.PopElementWithoutTarget, "RF_OP4"));
        T_RF_out4.GuardMappingList.add(grdT_RF_out4);

        T_RF_out4.Delay = 0;
        pn.Transitions.add(T_RF_out4);

        // ----------------------------------------------------------------------------
        // ----------------------------Exit lane-------------------------------------
        // ----------------------------Regele Ferdinand--------------------------------------

        // T_RF_g ------------------------------------------------
        PetriTransition T_RF_g = new PetriTransition(pn);
        T_RF_g.TransitionName = "T_RF_g";
        T_RF_g.InputPlaceName.add("P_I");
        T_RF_g.InputPlaceName.add("RF_o");

        Condition T_RF_gCt1 = new Condition(T_RF_g, "P_I", TransitionCondition.HaveCarForMe);
        Condition T_RF_gCt2 = new Condition(T_RF_g, "RF_o", TransitionCondition.CanAddCars);
        Condition T_RF_gCt3 = new Condition(T_RF_g, "P_I", TransitionCondition.isBus);

        T_RF_gCt1.SetNextCondition(LogicConnector.AND, T_RF_gCt2);
        T_RF_gCt2.SetNextCondition(LogicConnector.AND, T_RF_gCt3);


        GuardMapping grdT_RF_g = new GuardMapping();
        grdT_RF_g.condition = T_RF_gCt1;
        grdT_RF_g.Activations.add(new Activation(T_RF_g, "P_I", TransitionOperation.PopElementWithTargetToQueue, "RF_o"));
        T_RF_g.GuardMappingList.add(grdT_RF_g);

        T_RF_out4.Delay = 1;
        pn.Transitions.add(T_RF_g);

        // T_RF_ge----------------------------------------------------------------

        PetriTransition T_RF_ge = new PetriTransition(pn);
        T_RF_ge.TransitionName = "T_RF_ge";
        T_RF_ge.InputPlaceName.add("RF_o");

        Condition T_RF_geCt1 = new Condition(T_RF_ge, "RF_o", TransitionCondition.HaveCar);

        GuardMapping grdT_RF_ge = new GuardMapping();
        grdT_RF_ge.condition = T_RF_geCt1;
        grdT_RF_ge.Activations.add(new Activation(T_RF_ge, "RF_o", TransitionOperation.PopElementWithoutTarget, "RF_oe"));
        T_RF_ge.GuardMappingList.add(grdT_RF_ge);

        T_RF_ge.Delay = 0;
        pn.Transitions.add(T_RF_ge);

        // T_RF_u4 ------------------------------------------------
        PetriTransition T_RF_u4 = new PetriTransition(pn);
        T_RF_u4.TransitionName = "T_RF_u4";
        T_RF_u4.InputPlaceName.add("RF_oe");

        Condition T_RF_u4Ct1 = new Condition(T_RF_u4, "RF_oe", TransitionCondition.NotNull);
        Condition T_RF_u4Ct2 = new Condition(T_RF_u4, "RF_x5", TransitionCondition.CanAddCars);
        T_RF_u4Ct1.SetNextCondition(LogicConnector.AND, T_RF_u4Ct2);


        GuardMapping grdT_RF_u4 = new GuardMapping();
        grdT_RF_u4.condition = T_RF_u4Ct1;
        grdT_RF_u4.Activations.add(new Activation(T_RF_u4, "RF_oe", TransitionOperation.AddElement, "RF_x5"));
        T_RF_u4.GuardMappingList.add(grdT_RF_u4);

        T_RF_u4.Delay = 0;
        pn.Transitions.add(T_RF_u4);

        // T_RF_e5 ------------------------------------------------
        PetriTransition T_RF_e5 = new PetriTransition(pn);
        T_RF_e5.TransitionName = "T_RF_e5";
        T_RF_e5.InputPlaceName.add("RF_x5");
        T_RF_e5.InputPlaceName.add("RF_TL3");
        T_RF_e5.InputPlaceName.add("RF_PTL3");
        T_RF_e5.InputPlaceName.add("RFUsReq3");


        Condition T_RF_e5Ct1 = new Condition(T_RF_e5, "RF_TL3", TransitionCondition.Equal,"green");
        Condition T_RF_e5Ct2 = new Condition(T_RF_e5, "RF_x5", TransitionCondition.HaveCar);
        T_RF_e5Ct1.SetNextCondition(LogicConnector.AND, T_RF_e5Ct2);

        GuardMapping grdT_RF_e5 = new GuardMapping();
        grdT_RF_e5.condition= T_RF_e5Ct1;
        grdT_RF_e5.Activations.add(new Activation(T_RF_e5, "RF_x5", TransitionOperation.PopElementWithoutTarget, "RF_b5"));
        grdT_RF_e5.Activations.add(new Activation(T_RF_e5, "RF_TL3", TransitionOperation.Move, "RF_TL3"));
        grdT_RF_e5.Activations.add(new Activation(T_RF_e5, "RF_PTL3", TransitionOperation.Move, "RF_PTL3"));

        T_RF_e5.GuardMappingList.add(grdT_RF_e5);

        Condition T_RF_e5Ct3 = new Condition(T_RF_e5, "RFUsReq3", TransitionCondition.NotNull);

        GuardMapping grdT_RF_e52 = new GuardMapping();
        grdT_RF_e52.condition= T_RF_e5Ct3;
        grdT_RF_e52.Activations.add(new Activation(T_RF_e5, "RFUsReq3", TransitionOperation.SendOverNetwork, "ORFReq3"));
        grdT_RF_e52.Activations.add(new Activation(T_RF_e5, "RF_TL3", TransitionOperation.Move, "RF_TL3"));
        grdT_RF_e52.Activations.add(new Activation(T_RF_e5, "RF_PTL3", TransitionOperation.Move, "RF_PTL3"));

        T_RF_e5.GuardMappingList.add(grdT_RF_e52);

        T_RF_e5.Delay = 1;
        pn.Transitions.add(T_RF_e5);

        // T_RF_oct1 ------------------------------------------------
        PetriTransition T_RF_oct1 = new PetriTransition(pn);
        T_RF_oct1.TransitionName = "T_RF_oct1";
        T_RF_oct1.InputPlaceName.add("RF_b5");

        Condition T_RF_oct1Ct1 = new Condition(T_RF_oct1, "RF_b5", TransitionCondition.HaveCarForMe);
//        Condition T_RF_oct1Ct2 = new Condition(T_RF_oct1, "RF_oct", TransitionCondition.CanAddCars);
//        T_RF_oct1Ct1.SetNextCondition(LogicConnector.AND, T_RF_oct1Ct2);

        GuardMapping grdT_RF_oct1 = new GuardMapping();
        grdT_RF_oct1.condition = T_RF_oct1Ct1;
        grdT_RF_oct1.Activations.add(new Activation(T_RF_oct1, "RF_b5", TransitionOperation.Move, "RF_oct"));
        T_RF_oct1.GuardMappingList.add(grdT_RF_oct1);

        T_RF_oct1.Delay = 0;
        pn.Transitions.add(T_RF_oct1);

        // T_RF_oct2 ------------------------------------------------
        PetriTransition T_RF_oct2 = new PetriTransition(pn);
        T_RF_oct2.TransitionName = "T_RF_oct2";
        T_RF_oct2.InputPlaceName.add("RF_oct");

        Condition T_RF_oct2Ct1 = new Condition(T_RF_oct2, "RF_oct", TransitionCondition.NotNull);

        GuardMapping grdT_RF_oct2 = new GuardMapping();
        grdT_RF_oct2.condition = T_RF_oct2Ct1;
        grdT_RF_oct2.Activations.add(new Activation(T_RF_oct2, "RF_oct", TransitionOperation.Move, "RF_oct_o"));
        T_RF_oct2.GuardMappingList.add(grdT_RF_oct2);

        T_RF_oct2.Delay = 0;
        pn.Transitions.add(T_RF_oct2);

        // T_RF_g2 ------------------------------------------------
        PetriTransition T_RF_g2 = new PetriTransition(pn);
        T_RF_g2.TransitionName = "T_RF_g2";
        T_RF_g2.InputPlaceName.add("RF_b5");

        Condition T_RF_g2Ct1 = new Condition(T_RF_g2, "RF_b5", TransitionCondition.HaveCarForMe);
//        Condition T_RF_g2Ct2 = new Condition(T_RF_g2, "RF_o2", TransitionCondition.CanAddCars);
//        T_RF_g2Ct1.SetNextCondition(LogicConnector.AND, T_RF_g2Ct2);

        GuardMapping grdT_RF_g2 = new GuardMapping();
        grdT_RF_g2.condition = T_RF_g2Ct1;
        grdT_RF_g2.Activations.add(new Activation(T_RF_g2, "RF_b5", TransitionOperation.Move, "RF_o2"));
        T_RF_g2.GuardMappingList.add(grdT_RF_g2);

        T_RF_g2.Delay = 0;
        pn.Transitions.add(T_RF_g2);

        // T_RF_g2e ------------------------------------------------
        PetriTransition T_RF_g2e = new PetriTransition(pn);
        T_RF_g2e.TransitionName = "T_RF_g2e";
        T_RF_g2e.InputPlaceName.add("RF_o2");

        Condition T_RF_g2eCt1 = new Condition(T_RF_g2e, "RF_o2", TransitionCondition.NotNull);

        GuardMapping grdT_RF_g2e = new GuardMapping();
        grdT_RF_g2e.condition = T_RF_g2eCt1;
        grdT_RF_g2e.Activations.add(new Activation(T_RF_g2e, "RF_o2", TransitionOperation.Move, "RF_o2e"));
        T_RF_g2e.GuardMappingList.add(grdT_RF_g2e);

        T_RF_g2e.Delay = 0;
        pn.Transitions.add(T_RF_g2e);


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
