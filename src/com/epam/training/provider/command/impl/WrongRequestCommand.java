package com.epam.training.provider.command.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.epam.training.provider.util.Permanent.*;

import com.epam.training.provider.command.Command;

public class WrongRequestCommand implements Command {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		return ERROR_PAGE;
	}

}
