/**
 * 
 */
package model.shopping;

import java.util.ArrayList;
import java.util.Date;
import model.item.Item;

/**
 * @author mcany
 *
 */
public class Order extends Shopping{
	
	private Date OrderDate;
	private double TotalCost;
	private ArrayList<Item> purchasedItems;
	private String CustomerIDofBuyer;
	
	
	
	
	/**
	 * 
	 */
	public Order() {
		super();
	}


	/**
	 * @param orderDate
	 * @param totalCost
	 * @param purchasedItems
	 * @param customerIDofBuyer
	 */
	public Order(Date orderDate, double totalCost, ArrayList<Item> purchasedItems, String customerIDofBuyer) {
		super();
		OrderDate = orderDate;
		TotalCost = totalCost;
		this.purchasedItems = purchasedItems;
		CustomerIDofBuyer = customerIDofBuyer;
	}


	/**
	 * @return the orderDate
	 */
	public Date getOrderDate() {
		return OrderDate;
	}


	/**
	 * @param orderDate the orderDate to set
	 */
	public void setOrderDate(Date orderDate) {
		OrderDate = orderDate;
	}


	/**
	 * @return the totalCost
	 */
	public double getTotalCost() {
		return TotalCost;
	}


	/**
	 * @param totalCost the totalCost to set
	 */
	public void setTotalCost(double totalCost) {
		TotalCost = totalCost;
	}


	/**
	 * @return the purchasedItems
	 */
	public ArrayList<Item> getPurchasedItems() {
		return purchasedItems;
	}


	/**
	 * @param purchasedItems the purchasedItems to set
	 */
	public void setPurchasedItems(ArrayList<Item> purchasedItems) {
		this.purchasedItems = purchasedItems;
	}


	/**
	 * @return the customerIDofBuyer
	 */
	public String getCustomerIDofBuyer() {
		return CustomerIDofBuyer;
	}


	/**
	 * @param customerIDofBuyer the customerIDofBuyer to set
	 */
	public void setCustomerIDofBuyer(String customerIDofBuyer) {
		CustomerIDofBuyer = customerIDofBuyer;
	}
	
	
}
