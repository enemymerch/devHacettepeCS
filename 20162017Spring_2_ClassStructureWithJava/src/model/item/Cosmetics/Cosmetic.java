/**
 * 
 */
package model.item.Cosmetics;

import model.item.Item;

/**
 * @author mcany
 *
 */
public class Cosmetic extends Item{
	
	private String Manifacturer;
	private String Brand;
	private String ExpirationDate;
	private double weight;
	private boolean isOrganic;
	
	

	/**
	 * @param iD
	 * @param price
	 * @param manifacturer
	 * @param brand
	 * @param expirationDate
	 * @param weight
	 * @param isOrganic
	 */
	public Cosmetic(String iD, double price, String manifacturer, String brand, String expirationDate, double weight,
			boolean isOrganic) {
		super(iD, price);
		Manifacturer = manifacturer;
		Brand = brand;
		ExpirationDate = expirationDate;
		this.weight = weight;
		this.isOrganic = isOrganic;
	}
	/**
	 * @return the manifacturer
	 */
	public String getManifacturer() {
		return Manifacturer;
	}
	/**
	 * @param manifacturer the manifacturer to set
	 */
	public void setManifacturer(String manifacturer) {
		Manifacturer = manifacturer;
	}
	/**
	 * @return the brand
	 */
	public String getBrand() {
		return Brand;
	}
	/**
	 * @param brand the brand to set
	 */
	public void setBrand(String brand) {
		Brand = brand;
	}
	/**
	 * @return the expirationDate
	 */
	public String getExpirationDate() {
		return ExpirationDate;
	}
	/**
	 * @param expirationDate the expirationDate to set
	 */
	public void setExpirationDate(String expirationDate) {
		ExpirationDate = expirationDate;
	}
	/**
	 * @return the weight
	 */
	public double getWeight() {
		return weight;
	}
	/**
	 * @param weight the weight to set
	 */
	public void setWeight(double weight) {
		this.weight = weight;
	}
	/**
	 * @return the isOrganic
	 */
	public boolean isOrganic() {
		return isOrganic;
	}
	/**
	 * @param isOrganic the isOrganic to set
	 */
	public void setOrganic(boolean isOrganic) {
		this.isOrganic = isOrganic;
	}
	
	
}
