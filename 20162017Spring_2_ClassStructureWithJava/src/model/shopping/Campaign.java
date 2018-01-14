package model.shopping;

import java.util.Date;

public class Campaign extends Shopping{
	
	private Date StartDate;
	private Date EndDate;
	private String itemType;
	private int DiscountRate;
	/**
	 * @param startDate
	 * @param endDate
	 * @param itemType
	 * @param discountRate
	 */
	public Campaign(Date startDate, Date endDate, String itemType, int discountRate) {
		super();
		StartDate = startDate;
		EndDate = endDate;
		this.itemType = itemType;
		DiscountRate = discountRate;
	}
	/**
	 * @return the startDate
	 */
	public Date getStartDate() {
		return StartDate;
	}
	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(Date startDate) {
		StartDate = startDate;
	}
	/**
	 * @return the endDate
	 */
	public Date getEndDate() {
		return EndDate;
	}
	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(Date endDate) {
		EndDate = endDate;
	}
	/**
	 * @return the itemType
	 */
	public String getItemType() {
		return itemType;
	}
	/**
	 * @param itemType the itemType to set
	 */
	public void setItemType(String itemType) {
		this.itemType = itemType;
	}
	/**
	 * @return the discountRate
	 */
	public int getDiscountRate() {
		return DiscountRate;
	}
	/**
	 * @param discountRate the discountRate to set
	 */
	public void setDiscountRate(int discountRate) {
		DiscountRate = discountRate;
	}
	
	
}
