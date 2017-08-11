package com.epam.training.provider.bean;

import java.io.Serializable;
import java.util.Date;
/**
 * Entity class.
 * 
 * @author Valentina Pradun
 * @version 1.0
 */
public class Payment implements Serializable {
	private static final long serialVersionUID = 6550775677947500975L;
	private int id;
	private PaymentType type;
	private double amount;
	private Date date;
	private int userId;

	public Payment() {
	}

	public Payment(int id, PaymentType type, double amount, Date date, int userId) {
		this.id = id;
		this.type = type;
		this.amount = amount;
		this.date = date;
		this.userId = userId;
	}
	
	public Payment(PaymentType type, double amount, Date date, int userId) {
		this.type = type;
		this.amount = amount;
		this.date = date;
		this.userId = userId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public PaymentType getType() {
		return type;
	}

	public void setType(PaymentType type) {
		this.type = type;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(amount);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + id;
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		result = prime * result + userId;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Payment other = (Payment) obj;
		if (Double.doubleToLongBits(amount) != Double.doubleToLongBits(other.amount))
			return false;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (id != other.id)
			return false;
		if (type != other.type)
			return false;
		if (userId != other.userId)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Payment [id=" + id + ", type=" + type + ", amount=" + amount + ", date=" + date + ", userId="
				+ userId + "]";
	}

}
