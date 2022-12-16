package model;

import database.Connect;

public class Person{
	private String personName;
	private String email;
	private String address;
	private int zipCode;
	
	public Person(String personName, String email, String address, int zipCode) {
		super();
		this.personName = personName;
		this.email = email;
		this.address = address;
		this.zipCode = zipCode;
	}

	public String getPersonName() {
		return personName;
	}
	
	public void setPersonName(String personName) {
		this.personName = personName;
	}
	
	public String getAddress() {
		return address;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	public int getZipCode() {
		return zipCode;
	}
	
	public void setZipCode(int zipCode) {
		this.zipCode = zipCode;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void insert() {
		Connect con = Connect.getConnection();
		
		String query = String.format("INSERT INTO person (personName, email, address, zipCode) "
									+ "VALUES ('%s', '%s', '%s', %d)", personName, email, address, zipCode);
		
		con.executeUpdate(query);
	}

}
