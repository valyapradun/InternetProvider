package com.epam.training.provider.command;

public class CommandChooser {
	public static Command chooseCommand(String name) {
		Command currCommand = null;
		switch (name) {
		case "auth":
			currCommand = new AuthCommand();
			break;
		default:
			
		}
		return currCommand;
	}

}
