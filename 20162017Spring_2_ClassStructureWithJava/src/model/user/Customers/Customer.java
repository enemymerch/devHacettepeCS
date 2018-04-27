package model.user.Customers;

import java.util.ArrayList;
import java.util.Date;

import model.item.Item;
import model.shopping.Order;
import model.user.User;

public class Customer extends User{
	
	private String CustomerID;
	private String Password;
	private double Balance;
	private CustomerStatus Status;
	private ArrayList<Item> ShoppingList;
	private Order ShoppingCart;
	private ArrayList<Order> OrderHistory;
	
	/**
	 * @param name
	 * @param email
	 * @param birthDay
	 * @param customerID
	 * @param password
	 * @param balance
	 * @param status
	 * @param shoppingList
	 * @param shoppingCart
	 * @param orderHistory
	 */
	public Customer(String name, String email, Date birthDay, String customerID, String password, double balance) {
		super(name, email, birthDay);
		CustomerID = customerID;
		Password = password;
		Balance = balance;
		Status = CustomerStatus.CLASSIC;
		ShoppingList = new ArrayList<Item>();
		ShoppingCart = new Order();
		OrderHistory = new ArrayList<Order>();
	}
	/**
	 * @return the customerID
	 */
	public String getCustomerID() {
		return CustomerID;
	}
	/**
	 * @param customerID the customerID to set
	 */
	public void setCustomerID(String customerID) {
		CustomerID = customerID;
	}
	/**
	 * @return the password
	 */
	public String getPassword() {
		return Password;
	}
	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		Password = password;
	}
	/**
	 * @return the balance
	 */
	public double getBalance() {
		return Balance;
	}
	/**
	 * @param balance the balance to set
	 */
	public void setBalance(int balance) {
		Balance = balance;
	}
	/**
	 * @return the status
	 */
	public CustomerStatus getStatus() {
		return Status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(CustomerStatus status) {
		Status = status;
	}
	/**
	 * @return the shoppingList
	 */
	public ArrayList<Item> getShoppingList() {
		return ShoppingList;
	}
	/**
	 * @param shoppingList the shoppingList to set
	 */
	public void setShoppingList(ArrayList<Item> shoppingList) {
		ShoppingList = shoppingList;
	}
	/**
	 * @return the shoppingCart
	 */
	public Order getShoppingCart() {
		return ShoppingCart;
	}
	/**
	 * @param shoppingCart the shoppingCart to set
	 */
	public void setShoppingCart(Order shoppingCart) {
		ShoppingCart = shoppingCart;
	}
	/**
	 * @return the orderHistory
	 */
	public ArrayList<Order> getOrderHistory() {
		return OrderHistory;
	}
	/**
	 * @param orderHistory the orderHistory to set
	 */
	public void setOrderHistory(ArrayList<Order> orderHistory) {
		OrderHistory = orderHistory;
	}
	
	
}
