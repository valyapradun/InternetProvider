package com.epam.training.provider.service;

import java.util.HashMap;
import java.util.List;

import com.epam.training.provider.bean.Payment;
import com.epam.training.provider.service.exception.ServiceException;

public interface PaymentService {
	public List<Payment> listTransactionWithParameters(HashMap<String, String> parameters) throws ServiceException;
	public void addPayment(Payment payment) throws ServiceException;
}
