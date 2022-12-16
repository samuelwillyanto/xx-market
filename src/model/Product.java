package model;

import database.Connect;

public class Product {
	
	private String productName;
	private String productBrand;
	private String productCategory;
	int price;
	
	public Product(String productName, String productBrand, String productCategory, int price) {
		super();
		this.productName = productName;
		this.productBrand = productBrand;
		this.productCategory = productCategory;
		this.price = price;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductBrand() {
		return productBrand;
	}

	public void setProductBrand(String productBrand) {
		this.productBrand = productBrand;
	}

	public String getProductCategory() {
		return productCategory;
	}

	public void setProductCategory(String productCategory) {
		this.productCategory = productCategory;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}
	
	public void insert() {
		Connect con = Connect.getConnection();
		
		String query = String.format("INSERT INTO product (productName, productBrand, productCategory, priceIDR) "
									+ "VALUES ('%s', '%s', '%s', %d)", productName, productBrand, productCategory, price);
		
		con.executeUpdate(query);
	}

}
