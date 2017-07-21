package com.epam.training.provider.bean;

import java.io.Serializable;

public class User implements Serializable {
	private static final long serialVersionUID = -1802610255722244828L;

	private int id;
	private String login;
	private String password;
	private String role;
	private String name;
	private String email;
	private double balance;
	private double traffic;
	private String tariffTitle;
	private boolean activeBan;

	public User() {
	}

	public User(int id, String login, String password, String name, String role) {
		this.login = login;
		this.password = password;
		this.name = name;
		this.role = role;
		this.id = id;
	}

	public User(int id, String login, String name, String email, double balance, double traffic) {
		this.id = id;
		this.login = login;
		this.name = name;
		this.email = email;
		this.balance = balance;
		this.traffic = traffic;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public double getTraffic() {
		return traffic;
	}

	public void setTraffic(double traffic) {
		this.traffic = traffic;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getTariffTitle() {
		return tariffTitle;
	}

	public void setTariffTitle(String tariffTitle) {
		this.tariffTitle = tariffTitle;
	}

	public boolean isActiveBan() {
		return activeBan;
	}

	public void setActiveBan(boolean activeBan) {
		this.activeBan = activeBan;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (activeBan ? 1231 : 1237);
		long temp;
		temp = Double.doubleToLongBits(balance);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + id;
		result = prime * result + ((login == null) ? 0 : login.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((role == null) ? 0 : role.hashCode());
		result = prime * result + ((tariffTitle == null) ? 0 : tariffTitle.hashCode());
		temp = Double.doubleToLongBits(traffic);
		result = prime * result + (int) (temp ^ (temp >>> 32));
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
		User other = (User) obj;
		if (activeBan != other.activeBan)
			return false;
		if (Double.doubleToLongBits(balance) != Double.doubleToLongBits(other.balance))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (id != other.id)
			return false;
		if (login == null) {
			if (other.login != null)
				return false;
		} else if (!login.equals(other.login))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (role == null) {
			if (other.role != null)
				return false;
		} else if (!role.equals(other.role))
			return false;
		if (tariffTitle == null) {
			if (other.tariffTitle != null)
				return false;
		} else if (!tariffTitle.equals(other.tariffTitle))
			return false;
		if (Double.doubleToLongBits(traffic) != Double.doubleToLongBits(other.traffic))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", login=" + login + ", password=" + password + ", role=" + role + ", name=" + name
				+ ", email=" + email + ", balance=" + balance + ", traffic=" + traffic + ", tariffTitle=" + tariffTitle
				+ ", activeBan=" + activeBan + "]";
	}

	
}
