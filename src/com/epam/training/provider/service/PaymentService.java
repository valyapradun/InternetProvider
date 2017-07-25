package com.epam.training.provider.service;

import java.util.List;
import java.util.Map;

import com.epam.training.provider.bean.Payment;
import com.epam.training.provider.service.exception.ServiceException;
import com.epam.training.provider.service.exception.ValidateException;

public interface PaymentService {
	
	public List<Payment> listPaymentsWithCriteria(Map<String, String> criteria) throws ServiceException, ValidateException;
	public void addPayment(Payment payment) throws ServiceException, ValidateException;
	public void buyTariff(int userID, int tariffID) throws ServiceException, ValidateException;
	public void prolongUnlimTariffs() throws ServiceException;
	
}
