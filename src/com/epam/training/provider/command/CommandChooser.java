package com.epam.training.provider.command;

public class CommandChooser {
	public static Command chooseCommand(String name) {
		Command currCommand = null;
		switch (name) {
		case "auth":
			currCommand = new AuthCommand();
			break;
		case "allTariff":
			currCommand = new AllTariffCommand();
			break;
		case "reg":
			currCommand = new RegCommand();
			break;
		default:
			
		}
		return currCommand;
	}

}
