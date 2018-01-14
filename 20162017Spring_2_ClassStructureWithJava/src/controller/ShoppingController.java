package controller;

import java.util.ArrayList;
import java.util.Date;

import model.shopping.Campaign;
import model.shopping.Order;

/**
 * @author mcany
 *
 */
public class ShoppingController {

	private ArrayList<Campaign> Campaigns;
	private ArrayList<Order> Orders;
	
	
	/**
	 * Class constructer ,
	 * doesn't creates user objects ,
	 * only Initializes ArrayLists
	 */
	public ShoppingController() {
		super();
		Campaigns = new ArrayList<Campaign>();
		Orders = new ArrayList<Order>();
	}
	/**
	 * @return the campaigns
	 */
	public ArrayList<Campaign> getCampaigns() {
		return Campaigns;
	}
	/**
	 * @param campaigns the campaigns to set
	 */
	public void setCampaigns(ArrayList<Campaign> campaigns) {
		Campaigns = campaigns;
	}
	/**
	 * @return the orders
	 */
	public ArrayList<Order> getOrders() {
		return Orders;
	}
	/**
	 * @param orders the orders to set
	 */
	public void setOrders(ArrayList<Order> orders) {
		Orders = orders;
	}
	
	public void createCampaign(Date startDate, Date endDate, String itemType, int discountRate){
		this.Campaigns.add(new Campaign(startDate, endDate, itemType, discountRate));
	}
}
