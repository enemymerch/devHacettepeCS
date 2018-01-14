package model.item.Electronics;

public class SmartPhone extends Electronic{
	private String ScreenType;

	/**
	 * @param iD
	 * @param price
	 * @param manifacturer
	 * @param brand
	 * @param maxInputValtage
	 * @param maxPowerConsumption
	 * @param screenType
	 */
	public SmartPhone(String iD, double price, String manifacturer, String brand, int maxInputValtage,
			int maxPowerConsumption, String screenType) {
		super(iD, price, manifacturer, brand, maxInputValtage, maxPowerConsumption);
		ScreenType = screenType;
	}

	/**
	 * @return the screenType
	 */
	public String getScreenType() {
		return ScreenType;
	}

	/**
	 * @param screenType the screenType to set
	 */
	public void setScreenType(String screenType) {
		ScreenType = screenType;
	}
	
	/**
	 * 
	 */
	public String toString(){
		String smartPhoneStr = "Type: Desktop\n";
		smartPhoneStr = smartPhoneStr + "Item ID: "+ this.getID()+"\n";
		smartPhoneStr = smartPhoneStr + "Price: "+Double.toString(this.getPrice())+"$\n";
		smartPhoneStr = smartPhoneStr + "Manufacturer: "+this.getManifacturer()+"\n";
		smartPhoneStr = smartPhoneStr + "Brand: "+this.getBrand()+"\n";
		smartPhoneStr = smartPhoneStr + "Max Volt: "+ Integer.toString(getMaxInputVoltage())+" V\n";
		smartPhoneStr = smartPhoneStr + "Max Watt: "+ Integer.toString(getMaxPowerConsumption())+" W\n";
		smartPhoneStr = smartPhoneStr + "Screen Type: "+ getScreenType() + "\n";

		return smartPhoneStr;
	}
	
}
