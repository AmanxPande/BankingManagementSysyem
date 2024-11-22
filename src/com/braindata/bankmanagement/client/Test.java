package com.braindata.bankmanagement.client;

import java.sql.SQLException;
import java.util.Scanner;

import com.braindata.bankmanagement.service.RBI2;
import com.braindata.bankmanagement.serviceImpl.SBI2;

public class Test {

	public void options() {

		
		System.out.println("													Enter 0 to Login to Application");
		System.out.println("													Enter 1 to Create New Account");
		System.out.println("													Enter 2 to view Account Details");
		System.out.println("													Enter 3 to Deposit Money");
		System.out.println("													Enter 4 to Withdraw Money");
		System.out.println("													Enter 5 to Check Balance");
		System.out.println("													Enter 6 to Update Details");
		System.out.println("													Enter 7 to Reset Password");
		System.out.println("													Enter 8 to Exit form Application");

	}

	public static void main(String[] args) throws ClassNotFoundException, SQLException {

		System.out.println(
				"                                                                                -------------- BANKING -- MANAGEMENT -- SYSTEM ---------------                                                                                                ");
		System.out.println();

		Scanner sc = new Scanner(System.in);

		Test t = new Test();
		t.options();
		System.out.println(
				"      ****************************************************************************************************************************************************");
		System.out.println(
				"      ****************************************************************************************************************************************************");

		RBI2 bank = new SBI2();

		boolean flag = true;
		while (flag) {

			System.out.print("Please choose an option: ");

			int choice = sc.nextInt();
			switch (choice) {
			
			case 0:
				bank.login();
				break;
			case 1:
				bank.createAccount();
				break;
			case 2:
				System.out.println("************* Account Details *************");
				bank.displayAllDetails();
				break;
			case 3:
				System.out.println("************* Deposit Money *************");
				bank.depositeMoney();
				break;
			case 4:
				System.out.println("************* Withdraw Money *************");
				bank.withdrawal();
				break;
			case 5:
				System.out.println("************* Check Balance *************");
				bank.balanceCheck();
				break;
			case 6:
				System.out.println("********* Update Account Details *******");
				bank.updateDetails();
				break;
				
			case 7:
				System.out.println("********* Reset Password *******");
				bank.forgetPassword();
				break;
				
			case 8:
				System.out.println("Thank You for using the application. Goodbye!");
				flag = false;
				break;
			default:
				System.out.println("Invalid option. Please select a valid option from the menu.");
			}

		}
	}
}
