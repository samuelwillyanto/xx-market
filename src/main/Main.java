package main;

import java.util.Scanner;

import controller.PersonController;
import controller.ProductController;
import controller.TransactionController;

public class Main {
	
	Scanner scan = new Scanner(System.in);

	public Main() {
		
		int input = -1;
		
		do {
			System.out.println("");
			System.out.println("XX MARKET");
			System.out.println("=====================");
			System.out.println("1. Manage people");
			System.out.println("2. Manage products");
			System.out.println("3. Manage transactions");
			System.out.println("4. Exit");
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
				PersonController.managePerson();
				break;
			case 2:
				ProductController.manageProduct();
				break;
			case 3:
				TransactionController.manageTransaction();
				break;
			case 4:
				System.out.println("Thank you for using the program!");
				break;
			default:
				break;
			}
			
		} while (input!=4);
	}

	public static void main(String[] args) {
		new Main();
	}

}
