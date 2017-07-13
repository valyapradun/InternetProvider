package com.epam.training.provider.service;

import java.util.List;
import java.util.Map;

import com.epam.training.provider.bean.Payment;
import com.epam.training.provider.service.exception.ServiceException;
import com.epam.training.provider.service.exception.ValidateException;

public interface PaymentService {
	
	public List<Payment> listPaymentsWithParameters(Map<String, String> parameters) throws ServiceException;
	public void addPayment(Payment payment) throws ServiceException;
	public void buyTariff(int userID, int tariffID) throws ServiceException, ValidateException;
	
}
