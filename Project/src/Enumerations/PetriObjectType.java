package Enumerations;

import java.io.Serializable;

public enum PetriObjectType implements Serializable{
	Undefined,
	PetriNet,
	DataInteger,
	DataFloat,
	PetriPlace,
	PetriTransition,
	DataString,
	DataCar,
	DataCarQueue,
	DataTransfer,
	DataREL,
	DataRELQueue,
	DataSubPetri,
	PetriData,
	DataBoolean,
	DataFloatFloat,	//the new data type
	DataFuzzy,
	DataNetworkCommand
}
