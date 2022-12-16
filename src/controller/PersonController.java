package controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import database.Connect;
import model.Person;

public class PersonController {

	public PersonController() {
		
	}
	
	static Scanner scan = new Scanner(System.in);
	
	public static void managePerson() {
		int input = -1;
		
		do {
			System.out.println("");
			System.out.println("Manage People");
			System.out.println("=====================");
			System.out.println("1. View all people");
			System.out.println("2. Register a new person");
			System.out.println("3. Update a person");
			System.out.println("4. Delete a person");
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
		System.out.println("VIEW ALL PERSON");
		System.out.println("===============================================================================");
		System.out.println("| personId | personName | email                | address            | zipCode |");
		System.out.println("===============================================================================");
		
		Connect con = Connect.getConnection();
		String query = "SELECT * FROM person";
		
		ResultSet rs = con.executeQuery(query);
		
		try {
			while(rs.next()) {
				System.out.printf("| %-9d| %-11s| %-21s| %-19s| %-8d|\n", rs.getInt("personId"), rs.getString("personName"), rs.getString("email"), rs.getString("address"), rs.getInt("zipCode"));
			}
		} catch (SQLException e) {
			System.out.println("Query Failed");
		}
		
		System.out.println("===============================================================================\n");
	}

public static void create() {
		
		System.out.println("REGISTER A PERSON");
		
		String personName, email, address, zipString;
		int zipCode = -1;
		
		do {
			System.out.print("Input person name [at least 5 characters]: ");
			personName = scan.nextLine();
		} while (personName.length()<5);
		
		do {
			System.out.print("Input person email [ends with '@gmail.com']: ");
			email = scan.nextLine();
		} while (!email.endsWith("@gmail.com"));
		
		do {
			System.out.print("Input person address [starts with 'Jl. ']: ");
			address = scan.nextLine();
		} while (!address.startsWith("Jl. "));
		
		do {
			System.out.print("Input zip code [5 characters]: ");
			try {
				zipCode = scan.nextInt();
				scan.nextLine();
			} catch (Exception e) {
				scan.nextLine();
			}
			zipString = zipCode+"";
		} while (zipString.length()!=5);
		
		Person person = new Person(personName, email, address, zipCode);
		person.insert();
		
		System.out.println("Successfully added a person!\n");
		
		System.out.println("Press enter to continue ...");
		scan.nextLine();
	}
	
public static void update() {
	System.out.println("UPDATE A PRODUCT");
	
	viewAll();
	
	int personId = -1;
	
	do {
		System.out.print("Input person id: ");
		try {
			personId = scan.nextInt();
			scan.nextLine();
		} catch (Exception e) {
			scan.nextLine();
		}
	} while (!isValidID(personId));
	
	System.out.println("NEW DATA:");
	
	String personName, email, address, zipString;
	int zipCode = -1;
	
	do {
		System.out.print("Input new person name [at least 5 characters]: ");
		personName = scan.nextLine();
	} while (personName.length()<5);
	
	do {
		System.out.print("Input new person email [ends with '@gmail.com']: ");
		email = scan.nextLine();
	} while (!email.endsWith("@gmail.com"));
	
	do {
		System.out.print("Input new person address [starts with 'Jl. ']: ");
		address = scan.nextLine();
	} while (!address.startsWith("Jl. "));
	
	do {
		System.out.print("Input new zip code [5 characters]: ");
		try {
			zipCode = scan.nextInt();
			scan.nextLine();
		} catch (Exception e) {
			scan.nextLine();
		}
		zipString = zipCode+"";
	} while (zipString.length()!=5);
	
	Connect con = Connect.getConnection();
	String query = "UPDATE person SET personName = '" +personName+ "', email = '"+email+"', address = '"+address+"', zipCode = "+zipCode+" WHERE personId = "+personId;
	
	con.executeUpdate(query);
	
	System.out.println("Successfully updated a person!\n");
	System.out.println("Press enter to continue ...");
	scan.nextLine();
}
	
	public static void delete() {
		viewAll();
		int id = -1;
		
		String sure;
		
		do {
			System.out.print("Input person id: ");
			try {
				id = scan.nextInt();
				scan.nextLine();
			} catch (Exception e) {
				scan.nextLine();
			}
		} while (!isValidID(id));
		
		do {
			System.out.print("Are you sure want to delete person no."+id+"? [Y/N]: ");
			sure = scan.nextLine();
		} while (!sure.equals("Y") && !sure.equals("N"));
		
		if (sure.equals("Y")) {
			Connect con = Connect.getConnection();
			String query = "DELETE FROM person where person.personId = " + id;
			
			con.executeUpdate(query);
			
			System.out.println("Successfully deleted a person!\n");
		}
		
		System.out.println("Press enter to continue ...");
		scan.nextLine();
	}
	
	public static boolean isValidID(int personId){
		Connect con = Connect.getConnection();
		String query = "SELECT personId FROM person WHERE personId = '" + personId + "'";
		
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
