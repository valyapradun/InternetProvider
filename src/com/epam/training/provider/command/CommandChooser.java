package com.epam.training.provider.command;

public final class CommandChooser {
	private final static CommandProvider PROVIDER = new CommandProvider();

	public static Command chooseCommand(String command) {
		Command currentCommand = PROVIDER.getCommand(command);
		return currentCommand;
	}
	
}
