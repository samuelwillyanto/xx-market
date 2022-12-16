package controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import database.Connect;
import model.Product;

public class ProductController {

	public ProductController() {
		// TODO Auto-generated constructor stub
	}
	
static Scanner scan = new Scanner(System.in);
	
	public static void manageProduct() {
		int input = -1;
		
		do {
			System.out.println("");
			System.out.println("Manage Product");
			System.out.println("=====================");
			System.out.println("1. View all product");
			System.out.println("2. Create new product");
			System.out.println("3. Update a product");
			System.out.println("4. Delete a product");
			System.out.println("5. Back");
			System.out.println("=====================");
			System.out.print(">> ");
			
			try {
				input = scan.nextInt();
				scan.nextLine();
			} catch (Exception e) {
				System.out.println("Please enter a valid number!");
				scan.nextLine();
			}
			
			switch (input) {
			case 1:
				viewAll();
				System.out.println("Press enter to continue ...");
				scan.nextLine();
				break;
			case 2:
				create();
				break;
			case 3:
				update();
				break;
			case 4:
				delete();
				break;
			case 5:
				return;
			default:
				break;
			}
		} while (input!=5);
	}
	
	public static void viewAll() {
		System.out.println("");
		System.out.println("VIEW ALL PRODUCTS");
		System.out.println("===========================================================================================");
		System.out.println("| productId | productName       | productBrand      | productCategory   | Price           |");
		System.out.println("===========================================================================================");
	
		Connect con = Connect.getConnection();
		String query = "SELECT * FROM product";
		
		ResultSet rs = con.executeQuery(query);
		
		try {
			while(rs.next()) {
				System.out.printf("| %-10d| %-18s| %-18s| %-18s| %-16s|\n", rs.getInt("productId"), rs.getString("productName"), rs.getString("productBrand"), 
						rs.getString("productCategory"), "Rp. " + rs.getInt("priceIDR"));
			}
		} catch (SQLException e) {
			System.out.println("Query Failed");
		}
		
		System.out.println("===========================================================================================");
	}

	public static void create() {
		
		System.out.println("CREATE A PRODUCT");
		
		String productName, productBrand, productCategory;
		int price = -1;
		
		do {
			System.out.print("Input product name [at least 5 characters]: ");
			productName = scan.nextLine();
		} while (productName.length()<5);
		
		do {
			System.out.print("Input product brand [at least 5 characters]: ");
			productBrand = scan.nextLine();
		} while (productBrand.length()<5);
		
		do {
			System.out.print("Input product category [Clothes | Stationary | Food | Electronics]: ");
			productCategory = scan.nextLine();
		} while (!productCategory.equals("Clothes") &&
				!productCategory.equals("Stationary") &&
				!productCategory.equals("Food") &&
				!productCategory.equals("Electronics"));
		
		do {
			System.out.print("Input product price [1000 - 10000000]: ");
			try {
				price = scan.nextInt();
				scan.nextLine();
			} catch (Exception e) {
				scan.nextLine();
			}
		} while (price<1000 || price > 10000000);
		
		Product product = new Product(productName, productBrand, productCategory, price);
		product.insert();
		
		System.out.println("Successfully added a product!\n");
		System.out.println("Press enter to continue ...");
		scan.nextLine();
	}
	
	public static void update() {
		System.out.println("UPDATE A PRODUCT");
		
		viewAll();
		
		int productId = -1;
		
		do {
			System.out.print("Input product id: ");
			try {
				productId = scan.nextInt();
				scan.nextLine();
			} catch (Exception e) {
				scan.nextLine();
			}
		} while (!isValidID(productId));
		
		String productName, productBrand, productCategory;
		int price = -1;
		
		System.out.println("NEW DATA:");
		
		do {
			System.out.print("Input new product name [at least 5 characters]: ");
			productName = scan.nextLine();
		} while (productName.length()<5);
		
		do {
			System.out.print("Input new product brand [at least 5 characters]: ");
			productBrand = scan.nextLine();
		} while (productBrand.length()<5);
		
		do {
			System.out.print("Input new product category [Clothes | Stationary | Food | Electronics]: ");
			productCategory = scan.nextLine();
		} while (!productCategory.equals("Clothes") &&
				!productCategory.equals("Stationary") &&
				!productCategory.equals("Food") &&
				!productCategory.equals("Electronics"));
		
		do {
			System.out.print("Input new product price [1000 - 10000000]: ");
			try {
				price = scan.nextInt();
				scan.nextLine();
			} catch (Exception e) {
				scan.nextLine();
			}
		} while (price<1000 || price > 10000000);
		
		Connect con = Connect.getConnection();
		String query = "UPDATE product SET productName = '" +productName+ "', productBrand = '"+productBrand+"', productCategory = '"+productCategory+"', priceIDR = "+price+" WHERE productId = "+productId;
		
		con.executeUpdate(query);
		
		System.out.println("Successfully updated a product!\n");
		System.out.println("Press enter to continue ...");
		scan.nextLine();
	}
	
	public static void delete() {
		viewAll();
		int id = -1;
		
		String sure;
		
		do {
			System.out.print("Input product id: ");
			try {
				id = scan.nextInt();
				scan.nextLine();
			} catch (Exception e) {
				scan.nextLine();
			}
		} while (!isValidID(id));
		
		do {
			System.out.print("Are you sure want to delete product no."+id+"? [Y/N]: ");
			sure = scan.nextLine();
		} while (!sure.equals("Y") && !sure.equals("N"));
		
		if (sure.equals("Y")) {
			Connect con = Connect.getConnection();
			String query = "DELETE FROM product where product.productId = " + id;
			
			con.executeUpdate(query);
			
			System.out.println("Successfully deleted a product!\n");
		}
		
		System.out.println("Press enter to continue ...");
		scan.nextLine();
	}
	
	public static boolean isValidID(int productId){
		Connect con = Connect.getConnection();
		String query = "SELECT productId FROM product WHERE productId = '" + productId + "'";
		
		ResultSet rs = con.executeQuery(query);
		
		try {
			if (!rs.isBeforeFirst() ) {    
			    System.out.println("No id found! Please enter a valid id."); 
			    return false;
			}
		} catch (SQLException e) {
			System.out.println(e);
		}
		
		return true;
	}
}
