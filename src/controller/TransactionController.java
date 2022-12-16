package controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import database.Connect;
import model.Transaction;

public class TransactionController {

	public TransactionController() {
		// TODO Auto-generated constructor stub
	}
	
static Scanner scan = new Scanner(System.in);
	
	public static void manageTransaction() {
		int input = -1;
		
		do {
			System.out.println("");
			System.out.println("Manage Transaction");
			System.out.println("==============================");
			System.out.println("1. View all transactions");
			System.out.println("2. Create new transaction");
			System.out.println("3. Back");
			System.out.println("==============================");
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
				break;
			case 2:
				create();
				break;
			case 3:
				return;
			default:
				break;
			}
		} while (input!=5);
	}
	
	public static void viewAll() {
		System.out.println("");
		System.out.println("VIEW ALL TRANSACTIONS");
		System.out.println("=====================================================================================================================");
		System.out.println("| transactionId | Ordered By       | Product Ordered      | Transaction Date               | QTY   | Amount         |");
		System.out.println("=====================================================================================================================");
	
		Connect con = Connect.getConnection();
		String query = "SELECT * FROM transaction t join person p on t.personId = p.personId join product pr on pr.productId = t.productId";
		
		ResultSet rs = con.executeQuery(query);
		
		try {
			while(rs.next()) {
				System.out.printf("| %-14d| %-17s| %-21s| %-31s| %-6d| %-15s|\n", rs.getInt("transactionId"), rs.getString("personName"), rs.getString("productName"), rs.getTimestamp("transactionDate"),rs.getInt("QTY"), "Rp. "+ rs.getInt("amount"));
			}
		} catch (SQLException e) {
			System.out.println("Query Failed");
		}
		
		System.out.println("=====================================================================================================================\n");
		System.out.println("Press enter to continue ...");
		scan.nextLine();
	}
	
	public static void create() {
		System.out.println("CREATE A TRANSACTION");
		System.out.println("");
		int personId = -1, productId = -1, productPrice, qty=-1, amount;
		
		PersonController.viewAll();
		
		do {
			System.out.print("Enter person id: ");
			try {
				personId = scan.nextInt();
				scan.nextLine();
			} catch (Exception e) {
				scan.nextLine();
			}
		} while (!PersonController.isValidID(personId));
		
		ProductController.viewAll();
		
		do {
			System.out.print("Enter product id: ");
			try {
				productId = scan.nextInt();
				scan.nextLine();
			} catch (Exception e) {
				scan.nextLine();
			}
			productPrice = getProductPrice(productId);
		} while (!ProductController.isValidID(productId));
		
		do {
			System.out.print("Enter product quantity [More than 0]: ");
			try {
				qty = scan.nextInt();
				scan.nextLine();
			} catch (Exception e) {
				scan.nextLine();
			}
		} while (qty<1);
		
		amount = qty*productPrice;
		
		Transaction tr = new Transaction(personId, productId, qty, amount);
		tr.insert();
		
		System.out.println("\nTransaction sucessfully made!\n");
		
		System.out.println("Press enter to continue ...");
		scan.nextLine();
	}
	
	public static int getProductPrice(int productId){
		Connect con = Connect.getConnection();
		String query = "SELECT priceIDR FROM product WHERE productId = '" + productId + "'";
		
		int price = -1;
		
		ResultSet rs = con.executeQuery(query);
		
		try {
			if(rs.next()){
				price = rs.getInt("priceIDR");
			}
		} catch (SQLException e) {
			System.out.println(e);
		}
		
		return price;
	}

}
