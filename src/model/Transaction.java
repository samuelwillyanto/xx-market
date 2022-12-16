package model;

import database.Connect;

public class Transaction {
	
	private int personid;
	private int productid;
	private int qty;
	private int amount;
	
	public Transaction( int personid, int productid, int qty, int amount) {
		super();
		this.personid = personid;
		this.productid = productid;
		this.qty = qty;
		this.amount = amount;
	}
	
	public int getPersonid() {
		return personid;
	}
	
	public void setPersonid(int personid) {
		this.personid = personid;
	}
	
	public int getProductid() {
		return productid;
	}
	
	public void setProductid(int productid) {
		this.productid = productid;
	}
	
	public int getQty() {
		return qty;
	}
	
	public void setQty(int qty) {
		this.qty = qty;
	}
	
	public int getAmount() {
		return amount;
	}
	
	public void setAmount(int amount) {
		this.amount = amount;
	}
	
	public void insert() {
		Connect con = Connect.getConnection();
	
		String query = String.format("INSERT INTO transaction (personId, productId, transactionDate, QTY, amount) "
									+ "VALUES (%d, %d, CURRENT_TIMESTAMP, %d, %d)", personid, productid, qty, amount);
		
		con.executeUpdate(query);
	}

}
