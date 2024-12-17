package DataOnly;

import java.io.Serializable;

import Enumerations.NetworkCommands;

public class NetworkCommand implements Cloneable, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public NetworkCommand clone() throws CloneNotSupportedException {
		return (NetworkCommand) super.clone();
	}
	public NetworkCommands command;


	public NetworkCommand(NetworkCommands command) {
		this.command = command;
	}

	public String toString() {
		return this.command.toString();
	}
}
