package com.epam.training.provider.command.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.epam.training.provider.util.Permanent.*;

import com.epam.training.provider.command.Command;

public class ShowAdminPageCommand implements Command{
	private final static Logger logger = LogManager.getLogger(ShowAdminPageCommand.class.getName());

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		logger.log(Level.INFO, "Administrator (session id:" + request.getSession(false).getId() + ") opened page of administrator.");

		return ADMIN_PAGE;
	}

}
