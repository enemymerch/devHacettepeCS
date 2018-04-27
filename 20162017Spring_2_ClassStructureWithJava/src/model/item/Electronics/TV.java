package model.item.Electronics;

public class TV extends Electronic{
	private int ScreenSize;

	/**
	 * @param iD
	 * @param price
	 * @param manifacturer
	 * @param brand
	 * @param maxInputValtage
	 * @param maxPowerConsumption
	 * @param screenSize
	 */
	public TV(String iD, double price, String manifacturer, String brand, int maxInputValtage, int maxPowerConsumption,
			int screenSize) {
		super(iD, price, manifacturer, brand, maxInputValtage, maxPowerConsumption);
		ScreenSize = screenSize;
	}

	/**
	 * @return the screenSize
	 */
	public int getScreenSize() {
		return ScreenSize;
	}

	/**
	 * @param screenSize the screenSize to set
	 */
	public void setScreenSize(int screenSize) {
		ScreenSize = screenSize;
	}
	
	/**
	 * 
	 */
	public String toString(){
		String tvStr = "Type: Desktop\n";
		tvStr = tvStr + "Item ID: "+ this.getID()+"\n";
		tvStr = tvStr + "Price: "+Double.toString(this.getPrice())+"$\n";
		tvStr = tvStr + "Manufacturer: "+this.getManifacturer()+"\n";
		tvStr = tvStr + "Brand: "+this.getBrand()+"\n";
		tvStr = tvStr + "Max Volt: "+ Integer.toString(getMaxInputVoltage())+" V\n";
		tvStr = tvStr + "Max Watt: "+ Integer.toString(getMaxPowerConsumption())+" W\n";
		tvStr = tvStr + "Screen size: "+ Integer.toString(getScreenSize()) + "\"\n";

		return tvStr;
	}
	
}
