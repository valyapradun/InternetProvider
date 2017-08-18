package com.epam.training.provider.bean;

import java.io.Serializable;
/**
 * Entity class.
 * 
 * @author Valentina Pradun
 * @version 1.0
 */
public class Tariff extends Entity implements Serializable {
	private static final long serialVersionUID = -3554253448127674416L;
	
	private String name;
	private TariffType type;
	private double price;
	private double size;
	private int speed;
	private String picture;


	public Tariff() {
	}

	public Tariff(int id, String name, TariffType type, double price, double size, int speed, String picture) {
		super(id);
		this.name = name;
		this.type = type;
		this.price = price;
		this.size = size;
		this.speed = speed;
		this.picture = picture;
	}
	
	public Tariff(String name, TariffType type, double price, double size, int speed, String picture) {
		this.name = name;
		this.type = type;
		this.price = price;
		this.size = size;
		this.speed = speed;
		this.picture = picture;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public TariffType getType() {
		return type;
	}

	public void setType(TariffType type) {
		this.type = type;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public double getSize() {
		return size;
	}

	public void setSize(double size) {
		this.size = size;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((picture == null) ? 0 : picture.hashCode());
		long temp;
		temp = Double.doubleToLongBits(price);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(size);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + speed;
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		Tariff other = (Tariff) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (picture == null) {
			if (other.picture != null)
				return false;
		} else if (!picture.equals(other.picture))
			return false;
		if (Double.doubleToLongBits(price) != Double.doubleToLongBits(other.price))
			return false;
		if (Double.doubleToLongBits(size) != Double.doubleToLongBits(other.size))
			return false;
		if (speed != other.speed)
			return false;
		if (type != other.type)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Tariff [name=" + name + ", type=" + type + ", price=" + price + ", size=" + size + ", speed=" + speed
				+ ", picture=" + picture + "]";
	}
}
