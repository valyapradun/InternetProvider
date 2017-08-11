package com.epam.training.provider.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * Entity class.
 * 
 * @author Valentina Pradun
 * @version 1.0
 */
public class Ban extends Entity implements Serializable {
	private static final long serialVersionUID = -7817698796309899140L;
	
	private int userId;
	private int adminId;
	private Date startDate;
	private Date endDate;
	private String reason;

	public Ban() {
		super();
	}

	public Ban(int id, int userId, int adminId, String reason) {
		super(id);
		this.userId = userId;
		this.adminId = adminId;
		this.reason = reason;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getAdminId() {
		return adminId;
	}

	public void setAdminId(int adminId) {
		this.adminId = adminId;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + adminId;
		result = prime * result + ((endDate == null) ? 0 : endDate.hashCode());
		result = prime * result + ((reason == null) ? 0 : reason.hashCode());
		result = prime * result + ((startDate == null) ? 0 : startDate.hashCode());
		result = prime * result + userId;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Ban other = (Ban) obj;
		if (adminId != other.adminId)
			return false;
		if (endDate == null) {
			if (other.endDate != null)
				return false;
		} else if (!endDate.equals(other.endDate))
			return false;
		if (reason == null) {
			if (other.reason != null)
				return false;
		} else if (!reason.equals(other.reason))
			return false;
		if (startDate == null) {
			if (other.startDate != null)
				return false;
		} else if (!startDate.equals(other.startDate))
			return false;
		if (userId != other.userId)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Ban [" + super.toString() + ", userId=" + userId + ", adminId=" + adminId + ", startDate=" + startDate
				+ ", endDate=" + endDate + ", reason=" + reason + "]";
	}
}
