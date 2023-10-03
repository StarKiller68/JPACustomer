package com.hbreck;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Id;

@Entity
@Table(name = "customer")
public class Customer implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "custID", unique = true)
	private int id;
	
	@Column(name = "firstName", nullable = false)
	private String fName;
	
	@Column(name = "lastName", nullable = false)
	private String lName;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getfName() {
		return fName;
	}

	public void setfName(String fName) {
		this.fName = fName;
	}

	public String getlName() {
		return lName;
	}

	public void setlName(String lName) {
		this.lName = lName;
	}
	
	
	

}
