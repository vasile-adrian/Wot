package DCS_UVS;

import Components.*;
import DataObjects.DataString;
import DataObjects.DataTransfer;
import DataOnly.TransferOperation;
import Enumerations.LogicConnector;
import Enumerations.TransitionCondition;
import Enumerations.TransitionOperation;

public class ControllerPed {

    public static void main(String[] args) {

        PetriNet pn = new PetriNet();
        pn.SetName("Crosswalk Controller");

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

        DataString us_req = new DataString();
        us_req.SetName("UsReq");
        pn.PlaceList.add(us_req);

        DataString gr = new DataString();
        gr.SetName("gr");
        pn.PlaceList.add(gr);

        DataString yr = new DataString();
        yr.SetName("yr");
        pn.PlaceList.add(yr);

        DataString rg = new DataString();
        rg.SetName("rg");
        pn.PlaceList.add(rg);

        DataString ry = new DataString();
        ry.SetName("ry");
        pn.PlaceList.add(ry);

        DataString p_ini = new DataString();
        p_ini.SetName("P_ini");
        pn.PlaceList.add(p_ini);

        DataString wait = new DataString();
        wait.SetName("wait");
        pn.PlaceList.add(wait);

        DataTransfer p_tl = new DataTransfer();
        p_tl.SetName("P_tl");
        p_tl.Value = new TransferOperation("localhost", "1080", "P_TL1");
        pn.PlaceList.add(p_tl);

        DataTransfer p_ptl = new DataTransfer();
        p_ptl.SetName("p_ptl");
        p_ptl.Value = new TransferOperation("localhost", "1080", "P_PTL");
        pn.PlaceList.add(p_ptl);

        // T1 -----------------------------------------
        PetriTransition t1 = new PetriTransition(pn);
        t1.SetName("t1");
        t1.InputPlaceName.add("UsReq");
        t1.InputPlaceName.add("wait");

        Condition T1Ct1 = new Condition(t1, "wait", TransitionCondition.NotNull);
        Condition T1Ct2 = new Condition(t1, "UsReq", TransitionCondition.NotNull);
        T1Ct1.SetNextCondition(LogicConnector.AND, T1Ct2);

        GuardMapping grdT1 = new GuardMapping();
        grdT1.condition = T1Ct1;
        grdT1.Activations.add(new Activation(t1, "wait", TransitionOperation.Move, "yr"));
        grdT1.Activations.add(new Activation(t1, "yellow", TransitionOperation.SendOverNetwork, "P_TL1"));

        t1.GuardMappingList.add(grdT1);
        t1.Delay = 0;

        pn.PlaceList.add(t1);

        // T2 ----------------------------------------
        PetriTransition t2 = new PetriTransition(pn);
        t2.SetName("t2");
        t2.InputPlaceName.add("yr");

        Condition T2Ct1 = new Condition(t2, "yr", TransitionCondition.NotNull);

        GuardMapping grdT2 = new GuardMapping();
        grdT2.condition = T2Ct1;
        grdT2.Activations.add(new Activation(t2, "yr", TransitionOperation.Move, "rg"));
        grdT2.Activations.add(new Activation(t2, "red", TransitionOperation.SendOverNetwork, "P_TL1"));
        grdT2.Activations.add(new Activation(t2, "green", TransitionOperation.SendOverNetwork, "P_PTL"));

        t2.GuardMappingList.add(grdT2);
        t2.Delay = 5;

        pn.PlaceList.add(t2);

        // T3 ---------------------------------------
        PetriTransition t3 = new PetriTransition(pn);
        t3.SetName("t3");
        t3.InputPlaceName.add("rg");

        Condition T3Ct1 = new Condition(t3, "rg", TransitionCondition.NotNull);

        GuardMapping grdT3 = new GuardMapping();
        grdT3.condition = T3Ct1;
        grdT3.Activations.add(new Activation(t3, "rg", TransitionOperation.Move, "ry"));
        grdT3.Activations.add(new Activation(t3, "yellow", TransitionOperation.SendOverNetwork, "P_PTL"));

        t3.GuardMappingList.add(grdT3);
        t3.Delay = 5;

        pn.PlaceList.add(t3);

        // T4 ---------------------------------------
        PetriTransition t4 = new PetriTransition(pn);
        t4.SetName("t4");
        t4.InputPlaceName.add("ry");

        Condition T4Ct1 = new Condition(t4, "ry", TransitionCondition.NotNull);
        GuardMapping grdT4 = new GuardMapping();
        grdT4.condition = T4Ct1;
        grdT4.Activations.add(new Activation(t4, "ry", TransitionOperation.Move, "gr"));
        grdT4.Activations.add(new Activation(t4, "red", TransitionOperation.SendOverNetwork, "P_PTL"));
        grdT4.Activations.add(new Activation(t4, "green", TransitionOperation.SendOverNetwork, "P_TL1"));

        t4.GuardMappingList.add(grdT4);
        t4.Delay = 2;

        pn.PlaceList.add(t4);

        // T5 --------------------------------------
        PetriTransition t5 = new PetriTransition(pn);
        t5.SetName("t5");
        t5.InputPlaceName.add("gr");

        Condition T5Ct1 = new Condition(t5, "gr", TransitionCondition.NotNull);
        GuardMapping grdT5 = new GuardMapping();
        grdT5.condition = T5Ct1;
        grdT5.Activations.add(new Activation(t5, "gr", TransitionOperation.Move, "wait"));

        t5.GuardMappingList.add(grdT5);
        t5.Delay = 5;
        pn.PlaceList.add(t5);

        //-------------------------------------------------------------------------------------
        //----------------------------PN Start-------------------------------------------------
        //-------------------------------------------------------------------------------------

        System.out.println("Lane started \n ------------------------------");
        pn.Delay = 2000;
        //pn.Start();

        PetriNetWindow frame = new PetriNetWindow(false);
        frame.petriNet = pn;
        frame.setVisible(true);

    }

}
