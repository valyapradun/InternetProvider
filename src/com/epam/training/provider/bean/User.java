package com.epam.training.provider.bean;

import java.io.Serializable;
/**
 * Entity class.
 * 
 * @author Valentina Pradun
 * @version 1.0
 */
public class User extends Entity implements Serializable {
	private static final long serialVersionUID = 4933598474348743805L;
	
	private String login;
	private String password;
	private String role;
	private String name;
	private String email;
	private double balance;
	private double traffic;
	private boolean activeBan;
	private int numberContract;
	private Tariff tariff;

	public User() {
	}

	public User(int id, String login, String password, String name, String role) {
		super(id);
		this.login = login;
		this.password = password;
		this.name = name;
		this.role = role;
	}

	public User(int id, String login, String name, String email, double balance, double traffic) {
		super(id);
		this.login = login;
		this.name = name;
		this.email = email;
		this.balance = balance;
		this.traffic = traffic;
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

	public boolean isActiveBan() {
		return activeBan;
	}

	public void setActiveBan(boolean activeBan) {
		this.activeBan = activeBan;
	}

	public int getNumberContract() {
		return numberContract;
	}

	public void setNumberContract(int numberContract) {
		this.numberContract = numberContract;
	}

	public Tariff getTariff() {
		return tariff;
	}

	public void setTariff(Tariff tariff) {
		this.tariff = tariff;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + (activeBan ? 1231 : 1237);
		long temp;
		temp = Double.doubleToLongBits(balance);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((login == null) ? 0 : login.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + numberContract;
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((role == null) ? 0 : role.hashCode());
		result = prime * result + ((tariff == null) ? 0 : tariff.hashCode());
		temp = Double.doubleToLongBits(traffic);
		result = prime * result + (int) (temp ^ (temp >>> 32));
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
		if (numberContract != other.numberContract)
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
		if (tariff == null) {
			if (other.tariff != null)
				return false;
		} else if (!tariff.equals(other.tariff))
			return false;
		if (Double.doubleToLongBits(traffic) != Double.doubleToLongBits(other.traffic))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "User [login=" + login + ", password=" + password + ", role=" + role + ", name=" + name + ", email="
				+ email + ", balance=" + balance + ", traffic=" + traffic + ", activeBan=" + activeBan
				+ ", numberContract=" + numberContract + ", tariff=" + tariff + "]";
	}

}
