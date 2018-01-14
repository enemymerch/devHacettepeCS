package controller;

import java.util.Date;

import model.user.Customers.Customer;
import model.user.Employees.Admin;

public class Controller {
	/**
	 * 
	 */
	private ItemController itemController;
	private UserController userController;
	private ShoppingController shoppingController;
	
	/**
	 * 
	 * @param itemTxtDir
	 * @param userTxtDir
	 */
	public Controller(String itemTxtDir, String userTxtDir){
		super();
		this.itemController = new ItemController(itemTxtDir);
		this.userController = new UserController(userTxtDir);
		this.shoppingController = new ShoppingController();
	}
	
	/**
	 * 
	 * @param adminName
	 * @param customerName
	 * @param customerMail
	 * @param customerBirthDay
	 * @param customerBalance
	 * @param password
	 */
	public void AddCustomer(String adminName, String customerName, String customerMail, Date customerBirthDay, double customerBalance, String password){
		System.out.println("COMMAND TEXT: <ADDCUSTOMER	"+adminName+"	"+customerName
				+"	"+ customerMail+"	"+customerBirthDay.toString()+"	"+customerBalance+"	"+password+">\n");
		if(userController.isAdmin(adminName)){
			userController.addNewCustomer(customerMail, customerMail, customerBirthDay, customerBalance, password);
			System.out.println("");
		}else{
			System.out.println("No admin person named "+adminName+" exits!\n");
		}
	}
	
	/**
	 * 
	 * @param adminName
	 * @param customerID
	 */
	public void ShowCustomer(String adminName, String customerID){
		System.out.println("COMMAND TEXT: <SHOWCUSTOMER	"+adminName+" "+customerID+"\n");
		
		if(userController.isAdmin(adminName)){
			Customer tempCustomer = userController.getCustomerByID(customerID);
			if(tempCustomer!=null){
				System.out.println("Customer name: "+tempCustomer.getName()+"\tID: "+tempCustomer.getCustomerID()+"\te-mail: "+tempCustomer.getEmail()+
						"\tDate of Birth: "+tempCustomer.getBirthDay().toString()+"\t Status: "+tempCustomer.getStatus().toString()+"\n");
			}
		}else{
			System.out.println("No admin person named "+adminName+" exists! \n");
		}
	}

	/**
	 * 
	 * @param adminName
	 */
	public void ShowCustomers(String adminName){
		System.out.println("COMMAND TEXT: <SHOWCUSTOMERS\t"+adminName+"\n");
		
		if(userController.isAdmin(adminName)){
			for(Customer tempCustomer:userController.getCustomers()){
				System.out.println("Customer name: "+tempCustomer.getName()+"\tID: "+tempCustomer.getCustomerID()+"\te-mail: "+tempCustomer.getEmail()+
						"\tDate of Birth: "+tempCustomer.getBirthDay().toString()+"\t Status: "+tempCustomer.getStatus().toString());
			}
		}else{
			System.out.println("No admin person named "+adminName+" exists!");
		}
		System.out.print("\n");
	}
	
	/**
	 * 
	 * @param adminName
	 */
	public void ShowAdminInfo(String adminName){
		System.out.println("COMMAND TEXT: <SHOWADMININFO\t"+adminName+"\n");
		
		if(userController.isAdmin(adminName)){
			Admin tempAdmin = userController.getAdminByName(adminName);
			
			System.out.println("----------- Admin info -----------\n"+
						"Admin name: " + tempAdmin.getName()+
						"\nAdmin e-mail: "+tempAdmin.getEmail()+
						"\nAdmin date of birth: "+tempAdmin.getBirthDay().toString());
		}else{
			System.out.println("No admin person named "+adminName+" exists!");
		}
		System.out.print("\n");
	}
	
	
	/**
	 * 
	 * @param adminName
	 * @param startDate
	 * @param endDate
	 * @param itemType
	 * @param discountRate
	 */
	public void CreateCampaign(String adminName, Date startDate, Date endDate, String itemType, int discountRate){
		System.out.println("CREATECAMPAIGN\t"+adminName+"\t"+startDate.toString()+"\t"+endDate.toString()+"\t"+itemType+"\t"+discountRate);
		if(userController.isAdmin(adminName)){
			shoppingController.createCampaign(startDate, endDate, itemType, discountRate);
			System.out.println("Campaign was not created. Discount rate exceeds maximum rate of 50%.");
		}else{
			System.out.println("No admin person named "+adminName+" exists!");
		}
		System.out.print("\n");
	}
	
	/**
	 * 
	 * @param adminName
	 * @param newAdminName
	 * @param newAdminMail
	 * @param newAdminBirthDay
	 * @param newAdminSalary
	 * @param newAdminPassword
	 */
	public void AddAdmin(String adminName, String newAdminName, String newAdminMail, Date newAdminBirthDay, int newAdminSalary, String newAdminPassword){
		System.out.println("COMMAND TEXT: <ADDADMIN	"+adminName+"\t"+newAdminName+"\t"+newAdminMail+"\t"+newAdminBirthDay.toString()+"\t"+newAdminSalary+"\t"+newAdminPassword);
		if(userController.isAdmin(adminName)){
			userController.addNewAdmin(newAdminName, newAdminMail, newAdminBirthDay, newAdminSalary, newAdminPassword);
		}else{
			System.out.println("No admin person named "+adminName+" exists!");
		}
		System.out.print("\n");
	}
	
	/**
	 * 
	 * @param adminName
	 * @param newTechName
	 * @param newTechMail
	 * @param newTechBirthDay
	 * @param newTechSalary
	 * @param isSenior
	 */
	public void AddTech(String adminName, String newTechName, String newTechMail, Date newTechBirthDay, int newTechSalary, boolean isSenior){
		System.out.print("COMMAND TEXT: <ADDTECH	"+adminName+"\t"+newTechName+"\t"+newTechMail+"\t"+newTechBirthDay.toString()+"\t"+newTechSalary+"\t"+newTechSalary+"\t");
		if(isSenior){
			System.out.println("1");
		}else{
			System.out.println("0");
		}
		
		if(userController.isAdmin(adminName)){
			userController.addNewTech(newTechName, newTechMail, newTechBirthDay, newTechSalary, isSenior);
		}else{
			System.out.println("No admin person named "+adminName+" exists!");
		}
		System.out.print("\n");
	}
	
	/**
	 * 
	 * @param EmployeeName
	 */
	public void ListItem(String EmployeeName){
		System.out.println("LISTITEM\t"+EmployeeName+"\n");
		
		if(userController.isEmployee(EmployeeName)){
			System.out.println("-----------------------");
			itemController.displayOfficeSupplies();
			itemController.displayElectronics();
			itemController.displayCosmetics();
		}else{
			System.out.println("No admin or technician person named "+EmployeeName+" exists!");
		}
		System.out.print("\n");
	}
	
	/**
	 * 
	 * @param EmployeeName
	 * @param entity
	 */
	public void ShowItemsLowOnStock(String EmployeeName, int entity){
		// TODO:
	}
	
	/**
	 * 
	 * @param EmployeeName
	 */
	public void ShowVIP(String EmployeeName){
		// TODO:
	}
	/**
	 * 
	 * @param TechName
	 * @param itemTypes
	 */
	public void DispItemsOf(String TechName, String[] itemTypes){
		// TODO:
	}
	
	/**
	 * 
	 * @param TechName
	 * @param tokens
	 */
	public void AddItem(String TechName, String[] tokens){
		// TODO:
	}
	
	/**
	 * 
	 * @param TechName
	 */
	public void ShowOrders(String TechName){
		// TODO:
	}
	
	/**
	 * 
	 * @param CustomerID
	 * @param oldPassword
	 * @param newPassword
	 */
	public void ChPass(String CustomerID, String oldPassword, String newPassword){
		// TODO:
	}
	
	/**
	 * 
	 * @param CustomerID
	 * @param loadAmount
	 */
	public void DepositMoney(String CustomerID, double loadAmount){
		// TODO:
	}
	
	/**
	 * 
	 * @param CustomerID
	 */
	public void SHOWCAMPAIGNS(String CustomerID){
		// TODO:
	}
	
	/**
	 * 
	 * @param CustomerID
	 * @param itemID
	 */
	public void AddToCart(String CustomerID, String itemID){
		// TODO:
	}
	
	/**
	 * 
	 * @param CustomerID
	 */
	public void EmptyCart(String CustomerID){
		// TODO:
	}
	
	/**
	 * 
	 * @param CustomerID
	 */
	public void Order(String CustomerID){
		// TODO:
	}

	/**
	 * @return the itemController
	 */
	public ItemController getItemController() {
		return itemController;
	}

	/**
	 * @param itemController the itemController to set
	 */
	public void setItemController(ItemController itemController) {
		this.itemController = itemController;
	}

	/**
	 * @return the userController
	 */
	public UserController getUserController() {
		return userController;
	}

	/**
	 * @param userController the userController to set
	 */
	public void setUserController(UserController userController) {
		this.userController = userController;
	}
	
	
}
