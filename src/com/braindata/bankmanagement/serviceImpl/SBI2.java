package com.braindata.bankmanagement.serviceImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;
import java.util.Scanner;

import com.braindata.bankmanagement.model.Account;
import com.braindata.bankmanagement.service.RBI2;

import DBConfigue.DBconfigue;

public class SBI2 implements RBI2 {
	Random random = new Random();
	private Scanner sc = new Scanner(System.in);
	private Account ac = new Account();

	@Override
	public void createAccount() throws ClassNotFoundException, SQLException {

		// Database Connection
		Connection con = DBconfigue.getConnection();
		try {

			System.out.println("Enter your Age");
			int age = sc.nextInt();

			if (age >= 18) {
				ac.setAge(age);

			} else {
				System.out.println("You are not eligible...");
				return;
			}
			System.out.println("Enter Full Name");
			String name = sc.next() + sc.nextLine();
			ac.setName(name);

			System.out.println("Enter Mobile Number");
			long mobNo = sc.nextLong();
			ac.setMobNo(mobNo);

			System.out.println("Enter Gender");
			String gender = sc.next();
			ac.setGender(gender);

			System.out.println("Enter Adhar Number");
			long adharNo = sc.nextLong();
			ac.setAdharNo(adharNo);

			System.out.println("Enter PAN Number");
			String panNo = sc.next();
			panNo = panNo.toUpperCase();
			ac.setPanNo(panNo);

			System.out.println("Thankyou...! Account Created Successfully");
			System.out.println();

			System.out.println("Your Account Number ");
			int accNo = random.nextInt((int) Math.pow(10, 10));
			ac.setAccNo(accNo);
			System.out.println(accNo);

			System.out.println("Enter your 4-digit Password");
			String pinNo = sc.next() + sc.nextLine();
			ac.setPassword(pinNo);

			System.out.println();
			System.out.println("Enter Initial Amount");
			float balance = sc.nextFloat();
			ac.setBalance(balance);

			System.out.println("=============================");
			System.out.println();

			String insert = "insert into Account values(? , ? , ? , ? , ? , ? , ? , ? , ? )";
			PreparedStatement ps = con.prepareStatement(insert);
			ps.setInt(1, accNo);
			ps.setString(2, name);
			ps.setString(3, gender);
			ps.setInt(4, age);
			ps.setLong(5, mobNo);
			ps.setLong(6, adharNo);
			ps.setString(7, panNo);
			ps.setFloat(8, balance);
			ps.setString(9, pinNo);
			ps.execute();

		} catch (Exception e) {
			System.out.println("Invalid input. Please enter the correct data type.");

		}

	}

	public static void showMenu() {

		System.out.println("Enter 3 to deposit money");
		System.out.println("Enter 4 to withdraw money");
		System.out.println("Enter 5 to check balance");
		System.out.println("Enter 6 to Update Details");
	}

	public void login() throws ClassNotFoundException, SQLException {
		Connection con = DBconfigue.getConnection();

		String fetch = "Select * from Account where accNo = ? and password = ?";
		PreparedStatement ps = con.prepareStatement(fetch);

		System.out.println("Enter your Account Number");
		int accNo = sc.nextInt();

		System.out.println("Enter your 4 - digit Pin");
		String pin = sc.next();

		ps.setInt(1, accNo);
		ps.setString(2, pin);
		ResultSet rs = ps.executeQuery();

		boolean flag = rs.next();
		if (flag) {
			showMenu();
		} else {
			System.out.println("Incorrect AccNo or Password");
		}
	}
	
	@Override
	public  void forgetPassword() throws ClassNotFoundException, SQLException {
		
		Connection con = DBconfigue.getConnection();
		String fetch = "Select * from Account where accNo = ?";
		PreparedStatement ps = con.prepareStatement(fetch);

		System.out.println("Enter your Account Number to Reset Password");
		int accNo = sc.nextInt();
		ps.setInt(1, accNo);
		
		ResultSet rs = ps.executeQuery();

		boolean flag = rs.next();
		
		if(flag) {
			System.out.println("Enter new  4 - digit Pin");
			String pin = sc.next();
			String reset = "update Account set password  = ? where accNo = ?";
			PreparedStatement ps1 = con.prepareStatement(reset);
			ps1.setString(1, pin);
			ps1.setInt(2, accNo);
			ps1.executeUpdate();
			System.out.println("Password updated successfully.");
			
		}
		
		

		
		
	}

	@Override
	public void displayAllDetails() throws ClassNotFoundException, SQLException {
		Connection con = DBconfigue.getConnection();
		String fetch = "Select * from Account";
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery(fetch);

		while (rs.next()) {
			System.out.println("*********************");
			System.out.println("Account Number => " + rs.getInt(1));
			System.out.println("Name => " + rs.getString(2));
			System.out.println("Gender => " + rs.getString(3));
			System.out.println("Age => " + rs.getInt(4));

			System.out.println("Mobile Number => " + rs.getLong(5));
			System.out.println("Adhar Number => " + rs.getLong(6));
			System.out.println("PAN Number => " + rs.getString(7));
			System.out.println("Account Balance => " + rs.getFloat(8));
			System.out.println("========================");
		}

	}

	@Override
	public void depositeMoney() throws ClassNotFoundException, SQLException {
		Connection con = DBconfigue.getConnection();

		System.out.println("Enter your Account Number to deposite money");
		int accNo = sc.nextInt();

		String accNum = "Select * from Account where accNo = ?";
		PreparedStatement ps = con.prepareStatement(accNum);
		ps.setInt(1, accNo);
		ResultSet rs = ps.executeQuery();

		boolean flag = rs.next();

		if (flag) {

			float avBal = rs.getFloat("Balance");
			System.out.println("Enter amount to depsite");
			float deposit = sc.nextFloat();
			float currentBalance = avBal + deposit;

			String update = "Update Account set Balance = ? where accNo = ?";
			PreparedStatement ps1 = con.prepareStatement(update);
			ps1.setFloat(1, currentBalance);
			ps1.setInt(2, accNo);

			ps1.executeUpdate();
			System.out.println("Balance Updated Thank You...!");

		} else {
			System.out.println("Could not found Account with Account number : " + accNo);
		}

	}

	@Override
	public void withdrawal() throws ClassNotFoundException, SQLException {

		Connection con = DBconfigue.getConnection();
		System.out.println("Enter your Account Number to withdraw money");
		int accNo = sc.nextInt();

		String accNum = "Select * from Account where accNo = ?";
		PreparedStatement ps = con.prepareStatement(accNum);

		ps.setInt(1, accNo);

		ResultSet rs = ps.executeQuery();

		boolean flag = rs.next();

		if (flag) {

			float avBal = rs.getFloat("Balance");
			System.out.println("Enter the amount you want to withdraw");
			float withdraw = sc.nextFloat();

			if (withdraw > avBal) {
				System.out.println("Insufficient balance.");
			} else {
				float currentBalance = avBal - withdraw;
				String Update = "Update Account set Balance = ? where accNo = ? ";
				PreparedStatement ps2 = con.prepareStatement(Update);
				ps2.setFloat(1, currentBalance);
				ps2.setInt(2, accNo);

				ps2.executeUpdate();

				System.out.println("Amount Withdrawn : " + withdraw);
				System.out.println("Remaining Balance : " + currentBalance);
			}

		}

	}

	@Override
	public void balanceCheck() throws ClassNotFoundException, SQLException {

		Connection con = DBconfigue.getConnection();
		System.out.println("Enter your Account Number to Check Balance");
		int accNo = sc.nextInt();

		String fetchBalance = "Select * from Account where accNo = ? ";
		PreparedStatement ps = con.prepareStatement(fetchBalance);
		ps.setInt(1, accNo);
		ResultSet rs = ps.executeQuery();

		boolean flag = rs.next();
		if (flag) {
			System.out.println("*****************");
			float avBal = rs.getFloat("Balance");
			String fetch = "Select * from Account where accNo = ? ";
			PreparedStatement ps1 = con.prepareStatement(fetch);
			ps1.setInt(1, accNo);

			ps1.execute();
			System.out.println("Total Balance  " + avBal);
			System.out.println("*****************");
			System.out.println();

		}

	}

	@Override
	public void updateDetails() throws ClassNotFoundException, SQLException {

		Connection con = DBconfigue.getConnection();
		System.out.println("Enter your Account Number to Update Details");
		int accNo = sc.nextInt();

		String accNum2 = "Select * from Account where accNo = ? ";
		PreparedStatement ps = con.prepareStatement(accNum2);
		ps.setInt(1, accNo);
		ResultSet rs = ps.executeQuery();

		System.out.println("Choose the detail you want to update:");
		System.out.println("1. Name");
		System.out.println("2. Mobile Number");
		System.out.println("3. Adhar Number");
		System.out.println("4. PAN Number");
		System.out.println("5. Gender");
		System.out.println("6. Age");
		System.out.println("7. Exit");

		boolean flag = true;
		boolean flag2 = rs.next();

		if (flag) {
			while (flag) {

				int choice = sc.nextInt();

				switch (choice) {
				case 1:
					System.out.println("Enter new Name:");
					String newName = sc.next() + sc.nextLine();
					String updateName = "Update Account set name = ? where accNo = ? ";
					PreparedStatement ps2 = con.prepareStatement(updateName);
					ps2.setString(1, newName);
					ps2.setInt(2, accNo);
					ps2.executeUpdate();
					System.out.println("Name updated successfully.");
					break;

				case 2:
					System.out.println("Enter new Mobile Number:");
					long newMobNo = sc.nextLong();
					String updateMob = "Update Account set phoneNO = ? where accNo = ? ";
					PreparedStatement ps3 = con.prepareStatement(updateMob);
					ps3.setLong(1, newMobNo);
					ps3.setInt(2, accNo);
					ps3.executeUpdate();
					System.out.println("Mobile Number updated successfully.");
					break;

				case 3:
					System.out.println("Enter new Adhar Number:");
					long newAdharNo = sc.nextLong();
					String updateAdhar = "Update Account set adharNo = ? where accNo = ? ";
					PreparedStatement ps4 = con.prepareStatement(updateAdhar);
					ps4.setLong(1, newAdharNo);
					ps4.setInt(2, accNo);
					ps4.executeUpdate();
					System.out.println("Adhar Number updated successfully.");
					break;

				case 4:
					System.out.println("Enter new PAN Number:");
					String newPanNo = sc.next();
					String updatePan = "Update Account set panNo = ? where accNo = ? ";
					PreparedStatement ps5 = con.prepareStatement(updatePan);
					ps5.setString(1, newPanNo);
					ps5.setInt(2, accNo);
					ps5.executeUpdate();
					System.out.println("PAN Number updated successfully.");
					sc.next();
					break;

				case 5:
					System.out.println("Enter new Gender:");
					String newGender = sc.next();
					String updateGender = "Update Account set gender = ? where accNo = ? ";
					PreparedStatement ps6 = con.prepareStatement(updateGender);
					ps6.setString(1, newGender);
					ps6.setInt(2, accNo);
					ps6.executeUpdate();
					System.out.println("Gender updated successfully.");
					sc.next();
					break;

				case 6:
					System.out.println("Enter new Age:");
					int newAge = sc.nextInt();
					String updateAge = "Update Account set age = ? where accNo = ? ";
					PreparedStatement ps7 = con.prepareStatement(updateAge);
					ps7.setInt(1, newAge);
					ps7.setInt(2, accNo);
					ps7.executeUpdate();
					System.out.println("Age updated successfully.");
					sc.next();
					break;

				case 7:
					System.out.println("Exiting update menu.");
					flag = false;
					break;
					
				case 8:
				
					flag = false;
					break;
				default:
					System.out.println("Invalid choice. Please select a valid option.");
				}
			}
		}

	}

	

}
