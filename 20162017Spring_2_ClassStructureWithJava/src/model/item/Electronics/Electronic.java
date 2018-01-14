package model.item.Electronics;

import model.item.Item;

public class Electronic extends Item{
	
	private String Manifacturer;
	private String Brand;
	private int MaxInputVoltage;
	private int MaxPowerConsumption;
	
	
	/**
	 * @param iD
	 * @param price
	 */
	public Electronic(String iD, double price, String manifacturer, String brand, int maxInputValtage, int maxPowerConsumption) {
		super(iD, price);
		this.Manifacturer = manifacturer;
		this.Brand = brand;
		this.MaxInputVoltage = maxInputValtage;
		this.MaxPowerConsumption = maxPowerConsumption;
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
	 * @return the maxInputVoltage
	 */
	public int getMaxInputVoltage() {
		return MaxInputVoltage;
	}
	/**
	 * @param maxInputVoltage the maxInputVoltage to set
	 */
	public void setMaxInputVoltage(int maxInputVoltage) {
		MaxInputVoltage = maxInputVoltage;
	}
	/**
	 * @return the maxPowerConsumption
	 */
	public int getMaxPowerConsumption() {
		return MaxPowerConsumption;
	}
	/**
	 * @param maxPowerConsumption the maxPowerConsumption to set
	 */
	public void setMaxPowerConsumption(int maxPowerConsumption) {
		MaxPowerConsumption = maxPowerConsumption;
	}
	
	
}
