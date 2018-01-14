package model.item;

/**
 * @author mcany
 *
 */
public class Item {
	
	
	private String ID;
	private double price;
	
	
	/**
	 * @param iD
	 * @param price
	 */
	public Item(String iD, double price) {
		super();
		ID = iD;
		this.price = price;
	}


	/**
	 * @return the iD
	 */
	public String getID() {
		return ID;
	}


	/**
	 * @param iD the iD to set
	 */
	public void setID(String iD) {
		ID = iD;
	}


	/**
	 * @return the price
	 */
	public double getPrice() {
		return price;
	}


	/**
	 * @param price the price to set
	 */
	public void setPrice(double price) {
		this.price = price;
	}
	
	

	
}
