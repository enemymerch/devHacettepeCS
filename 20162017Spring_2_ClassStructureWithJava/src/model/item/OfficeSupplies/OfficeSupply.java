package model.item.OfficeSupplies;

import model.item.Item;

public class OfficeSupply extends Item{
	
	private String ReleaseDate;
	private String CoverTitle;

	
	/**
	 * @param iD
	 * @param price
	 * @param releaseDate
	 * @param coverTitle
	 */
	public OfficeSupply(String iD, double price, String releaseDate, String coverTitle) {
		super(iD, price);
		ReleaseDate = releaseDate;
		CoverTitle = coverTitle;
	}
	/**
	 * @return the releaseDate
	 */
	public String getReleaseDate() {
		return ReleaseDate;
	}
	/**
	 * @param releaseDate the releaseDate to set
	 */
	public void setReleaseDate(String releaseDate) {
		ReleaseDate = releaseDate;
	}
	/**
	 * @return the coverTitle
	 */
	public String getCoverTitle() {
		return CoverTitle;
	}
	/**
	 * @param coverTitle the coverTitle to set
	 */
	public void setCoverTitle(String coverTitle) {
		CoverTitle = coverTitle;
	}
	
	
}
