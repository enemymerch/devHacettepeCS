package model.item.Electronics;

public class Desktop extends Computer{
	private String BoxColor;

	/**
	 * @param iD
	 * @param price
	 * @param manifacturer
	 * @param brand
	 * @param maxInputValtage
	 * @param maxPowerConsumption
	 * @param oS
	 * @param cPUType
	 * @param rAM
	 * @param hDD
	 * @param boxColor
	 */
	public Desktop(String iD, double price, String manifacturer, String brand, int maxInputValtage,
			int maxPowerConsumption, String oS, String cPUType, int rAM, int hDD, String boxColor) {
		super(iD, price, manifacturer, brand, maxInputValtage, maxPowerConsumption, oS, cPUType, rAM, hDD);
		BoxColor = boxColor;
	}

	/**
	 * @return the boxColor
	 */
	public String getBoxColor() {
		return BoxColor;
	}

	/**
	 * @param boxColor the boxColor to set
	 */
	public void setBoxColor(String boxColor) {
		BoxColor = boxColor;
	}
	
	/**
	 * 
	 */
	public String toString(){
		String desktopStr = "Type: Desktop\n";
		desktopStr = desktopStr + "Item ID: "+ this.getID()+"\n";
		desktopStr = desktopStr + "Price: "+Double.toString(this.getPrice())+"$\n";
		desktopStr = desktopStr + "Manufacturer: "+this.getManifacturer()+"\n";
		desktopStr = desktopStr + "Brand: "+this.getBrand()+"\n";
		desktopStr = desktopStr + "Max Volt: "+ Integer.toString(getMaxInputVoltage())+" V\n";
		desktopStr = desktopStr + "Max Watt: "+ Integer.toString(getMaxPowerConsumption())+" W\n";
		desktopStr = desktopStr + "Operating System: "+ getOS()+"\n";
		desktopStr = desktopStr + "CPU Type: "+ getCPUType()+"\n";
		desktopStr = desktopStr + "RAM Capacity: "+ Integer.toString(getRAM())+" GB\n";
		desktopStr = desktopStr + "HDD Capacity: "+ Integer.toString(getHDD())+" GB\n";
		desktopStr = desktopStr + "Box Color: "+ getBoxColor()+"\n";
		
		return desktopStr;
	}
}
