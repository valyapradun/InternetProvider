package com.epam.training.provider.bean;

import java.io.Serializable;

/**
 * Abstract Entity class.
 * 
 * @author Valentina Pradun
 * @version 1.0
 */
public abstract class Entity implements Serializable {
	private static final long serialVersionUID = -8676299422290081193L;
	private int id;

	public Entity() {
	}

	public Entity(int id) {
		super();
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
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
		Entity other = (Entity) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "id=" + id;
	}

}
