/**
 * 
 */
package controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


import model.user.Customers.Customer;
import model.user.Employees.*;

/**
 * @author mcany
 *
 */

public class UserController {
	
	private ArrayList<Admin> Admins;
	private ArrayList<Technician> Technicians;
	private ArrayList<Customer> Customers;
	
	
	
	
	/**
	 * Class constructer ,
	 * doesn't creates user objects ,
	 * only Initializes ArrayLists
	 * @param admins
	 * @param technicians
	 * @param customers
	 */
	public UserController() {
		this.Admins = new ArrayList<Admin>();
		this.Technicians = new ArrayList<Technician>();
		this.Customers = new ArrayList<Customer>();
	}

	/**
	 * Class constructer ,
	 * This cons. reads the users.txt file and creates all relative objects,
	 * and stores them in ArrayLists
	 * @param inputFile
	 */
	public UserController(String inputFile){
		this.Admins = new ArrayList<Admin>();
		this.Technicians = new ArrayList<Technician>();
		this.Customers = new ArrayList<Customer>();
		
		InitializeUsers(inputFile);
	}
	
	/**
	 * Readsthe "users.txt" file, 
	 * and creates all releated users,
	 * 
	 * @param inputFile
	 */
	public void InitializeUsers(String inputFile){
		
		FileReader fReader = null;
		BufferedReader bufReader = null;
		
		try{
			fReader = new FileReader(inputFile);
			bufReader = new BufferedReader(fReader);
			
			String line;
			
			while((line = bufReader.readLine()) != null){
				String[] tokens = line.split("\t");

				// First, going to create an date object for birthDay
				DateFormat format = new SimpleDateFormat("dd.MM.yyyy");
				Date birthDay = format.parse(tokens[3]);
				
				// Now, going to create users and add them to releated lists !
				try{
					switch (tokens[0]) {
						case "ADMIN":
							Admin newAdmin = new Admin(tokens[1], tokens[2], birthDay, Integer.parseInt(tokens[4]), tokens[5]);
							(this.Admins).add(newAdmin);
							break;
						case "TECH":
							boolean isSenior=true;
							if(tokens[5].equals("0")){
								isSenior=false;
							}
							Technician newTech = new Technician(tokens[1], tokens[2], birthDay, Integer.parseInt(tokens[4]), isSenior);
							this.Technicians.add(newTech);
							break;
						case "CUSTOMER":
							Customer newCustomer = new Customer(tokens[1], tokens[2], birthDay, 
									Integer.toString(this.Customers.size()+1)
									, tokens[5], Double.parseDouble(tokens[4]));
							this.Customers.add(newCustomer);
							break;
						default:
							throw new IOException("There's no user type such as :" + tokens[0]);
					
					}
				}catch(ArrayIndexOutOfBoundsException e){
					System.out.println(e.getMessage());
				}
			}
			
			fReader.close();
			bufReader.close();
			
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
	}

	/**
	 * @return the admins
	 */
	public ArrayList<Admin> getAdmins() {
		return Admins;
	}


	/**
	 * @param admins the admins to set
	 */
	public void setAdmins(ArrayList<Admin> admins) {
		Admins = admins;
	}


	/**
	 * @return the technicians
	 */
	public ArrayList<Technician> getTechnicians() {
		return Technicians;
	}


	/**
	 * @param technicians the technicians to set
	 */
	public void setTechnicians(ArrayList<Technician> technicians) {
		Technicians = technicians;
	}


	/**
	 * @return the customers
	 */
	public ArrayList<Customer> getCustomers() {
		return Customers;
	}


	/**
	 * @param customers the customers to set
	 */
	public void setCustomers(ArrayList<Customer> customers) {
		Customers = customers;
	}
	
	/**
	 * Checks if there is a admin named "adminName"
	 * @param adminName
	 * @return
	 */
	public boolean isAdmin(String adminName){
		boolean isAdmin = false;
		
		for(Admin adm:this.Admins){
			if(adm.getName().equals(adminName)){
				isAdmin = true;
				break;
			}
		}
		
		return isAdmin;
	}
	
	/**
	 * Checks if there is a tech named "techName"
	 * @param adminName
	 * @return
	 */
	public boolean isTech(String techName){
		boolean isTech = false;
		
		for(Technician tech:this.Technicians){
			if(tech.getName().equals(techName)){
				isTech = true;
				break;
			}
		}
		
		return isTech;
	}
	/**
	 * Checks if there is a employee  named "empName"
	 * @param empName
	 * @return
	 */
	public boolean isEmployee(String empName){
		return ( isAdmin(empName) || isTech(empName) );
	}
	
	/**
	 * Adds new customer to the system
	 * @param customerName
	 * @param customerMail
	 * @param customerBirthDay
	 * @param customerBalance
	 * @param password
	 */
	public void addNewCustomer(String customerName, String customerMail, Date customerBirthDay, double customerBalance, String password){
		this.Customers.add(new Customer(customerName, customerMail, customerBirthDay, Integer.toString(this.Customers.size()+1), 
				password, customerBalance));
	}
	
	/**
	 * Adds new admin to the system
	 * @param adminName
	 * @param adminMail
	 * @param adminBirthDay
	 * @param adminSalary
	 * @param adminPassword
	 */
	public void addNewAdmin(String adminName, String adminMail, Date adminBirthDay, int adminSalary, String adminPassword){
		this.Admins.add(new Admin(adminName, adminMail, adminBirthDay, adminSalary, adminPassword));
	}
	
	/**
	 * Adds new tech to the system
	 * @param techName
	 * @param techMail
	 * @param techBirthDay
	 * @param techSalary
	 * @param isSenior
	 */
	public void addNewTech(String techName, String techMail, Date techBirthDay, int techSalary, boolean isSenior){
		this.Technicians.add(new Technician(techName, techMail, techBirthDay, techSalary, isSenior));
	}
	
	/**
	 * 
	 * @param customerID
	 * @return
	 */
	public Customer getCustomerByID(String customerID){
		for(Customer cus:this.Customers){
			if(cus.getCustomerID().equals(customerID)){
				return cus;
			}
		}
		
		return null;
	}
	
	/**
	 * 
	 * @param adminName
	 * @return
	 */
	public Admin getAdminByName(String adminName){
		for(Admin adm:this.Admins){
			if(adm.getName().equals(adminName)){
				return adm;
			}
		}
		return null;
	}
}
