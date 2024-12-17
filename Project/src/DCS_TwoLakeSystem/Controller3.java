package DCS_TwoLakeSystem;

import Components.Activation;
import Components.Condition;
import Components.GuardMapping;
import Components.PetriNet;
import Components.PetriNetWindow;
import Components.PetriTransition;
import DataObjects.DataFloat;
import DataObjects.DataString;
import Enumerations.LogicConnector;
import Enumerations.TransitionCondition;
import Enumerations.TransitionOperation;

public class Controller3 {

    public static void main(String[] args) {


            PetriNet pn = new PetriNet();
            pn.PetriNetName = "Controller 3";
            pn.NetworkPort = 1082;

            DataFloat hr3 = new DataFloat();
            hr3.SetName("hr3");
            hr3.SetValue(30.0f);
            pn.PlaceList.add(hr3);

            DataFloat h3 = new DataFloat();
            h3.SetName("h3"); //input a value to h3 from GUI input float
            pn.PlaceList.add(h3);

            DataString dc3 = new DataString();
            dc3.SetName("dc3");
            pn.PlaceList.add(dc3);

            DataString c3 = new DataString();
            c3.SetName("c3");
            pn.PlaceList.add(c3);

            DataString c3Previous = new DataString();
            c3Previous.SetName("c3Previous");
            c3Previous.SetValue("No Action");
            pn.PlaceList.add(c3Previous);

            DataString m13 = new DataString();
            m13.SetName("m13");
            pn.PlaceList.add(m13);

            DataString po3 = new DataString();
            po3.SetName("po3");
            pn.PlaceList.add(po3);

            DataString po3Next = new DataString();
            po3Next.SetName("po3Next");
            po3Next.SetValue("No Action");
            pn.PlaceList.add(po3Next);

            DataString NoAction = new DataString(); // constant value
            NoAction.SetName("No Action");
            NoAction.SetValue("No Action");
            pn.ConstantPlaceList.add(NoAction);

            DataString Increase = new DataString(); // constant value
            Increase.SetName("Increase");
            Increase.SetValue("Increase");
            pn.ConstantPlaceList.add(Increase);

            DataString Decrease = new DataString(); // constant value
            Decrease.SetName("Decrease");
            Decrease.SetValue("Decrease");
            pn.ConstantPlaceList.add(Decrease);

            // T0 ------------------------------------------------
            PetriTransition t0 = new PetriTransition(pn);
            t0.TransitionName = "T0";
            t0.InputPlaceName.add("h3");
            t0.InputPlaceName.add("hr3");
            t0.InputPlaceName.add("m13");
            t0.InputPlaceName.add("c3Previous");
            t0.InputPlaceName.add("po3Next");

            // -------Sub guard 1---------
            Condition T0Ct1 = new Condition(t0, "h3", TransitionCondition.NotNull);
            Condition T0Ct2 = new Condition(t0, "hr3", TransitionCondition.NotNull);
            Condition T0Ct3 = new Condition(t0, "c3Previous", TransitionCondition.NotNull);
            Condition T0Ct4 = new Condition(t0, "po3Next", TransitionCondition.NotNull);
            Condition T0Ct5 = new Condition(t0, "hr3", TransitionCondition.MoreThan, "h3");
            Condition T0Ct6 = new Condition(t0, "m13", TransitionCondition.Equal, "Decrease");

            T0Ct5.SetNextCondition(LogicConnector.AND, T0Ct6);
            T0Ct4.SetNextCondition(LogicConnector.AND, T0Ct5);
            T0Ct3.SetNextCondition(LogicConnector.AND, T0Ct4);
            T0Ct2.SetNextCondition(LogicConnector.AND, T0Ct3);
            T0Ct1.SetNextCondition(LogicConnector.AND, T0Ct2);

            GuardMapping grdT0 = new GuardMapping();
            grdT0.condition = T0Ct1;
            grdT0.Activations.add(new Activation(t0, "Decrease", TransitionOperation.Move, "dc3"));
            grdT0.Activations.add(new Activation(t0, "Decrease", TransitionOperation.Move, "c3"));
            grdT0.Activations.add(new Activation(t0, "Decrease", TransitionOperation.Move, "c3Previous"));
            grdT0.Activations.add(new Activation(t0, "hr3", TransitionOperation.Move, "hr3"));

            t0.GuardMappingList.add(grdT0);

            // -------Sub guard 2---------
            Condition T0Ct7 = new Condition(t0, "h3", TransitionCondition.NotNull);
            Condition T0Ct8 = new Condition(t0, "hr3", TransitionCondition.NotNull);
            Condition T0Ct9 = new Condition(t0, "c3Previous", TransitionCondition.NotNull);
            Condition T0Ct10 = new Condition(t0, "po3Next", TransitionCondition.NotNull);
            Condition T0Ct11 = new Condition(t0, "hr3", TransitionCondition.MoreThan, "h3");
            Condition T0Ct12 = new Condition(t0, "m13", TransitionCondition.Equal, "Increase");

            T0Ct11.SetNextCondition(LogicConnector.AND, T0Ct12);
            T0Ct10.SetNextCondition(LogicConnector.AND, T0Ct11);
            T0Ct9.SetNextCondition(LogicConnector.AND, T0Ct10);
            T0Ct8.SetNextCondition(LogicConnector.AND, T0Ct9);
            T0Ct7.SetNextCondition(LogicConnector.AND, T0Ct8);

            GuardMapping grdT02 = new GuardMapping();
            grdT02.condition = T0Ct7;
            grdT02.Activations.add(new Activation(t0, "No Action", TransitionOperation.Move, "dc3"));
            grdT02.Activations.add(new Activation(t0, "No Action", TransitionOperation.Move, "c3"));
            grdT02.Activations.add(new Activation(t0, "No Action", TransitionOperation.Move, "c3Previous"));
            grdT02.Activations.add(new Activation(t0, "hr3", TransitionOperation.Move, "hr3"));

            t0.GuardMappingList.add(grdT02);

            // -------Run OER-TPN
            System.out.println("Controller 3 started \n ------------------------------");

            pn.Delay = 2000;

            PetriNetWindow frame = new PetriNetWindow(false);
            frame.petriNet = pn;
            frame.setVisible(true);
        }

}
