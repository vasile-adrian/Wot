package Enumerations;

import java.io.Serializable;

public enum TransitionOperation implements Serializable{
	Undefined,
	Add,
	Sub,
	Prod,
	Div,
	Mod,
	Move,
	Copy,
	AddElement,
	PopElementWithTarget,
	PopElementWithoutTarget,
	PopElementWithTargetToQueue,
	PopElementWithoutTargetToQueue,
	SendOverNetwork,
	SendROverNetwork,
	SendPetriNetOverNetwork,
	PopElement_R_E,
	ActivateSubPetri,
	StopPetriNet,
	MakeNull,
	Add_FloatFlaot,  //the activations for floatfloat
	Sub_FloatFloat,  //the activations for floatfloat
	Prod_FloatFloat, //the activations for floatfloat
	Div_FloatFloat,  //the activations for floatfloat
	DynamicDelay,
	FLRS,
	Add_Fuzzy,
	Sub_Fuzzy,
	Prod_Fuzzy,
	Div_Fuzzy,
	PopTaxiToQueue,
}
