package model.item.Electronics;

public class Tablet extends Computer{
	private int dimention;

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
	 * @param dimention
	 */
	public Tablet(String iD, double price, String manifacturer, String brand, int maxInputValtage,
			int maxPowerConsumption, String oS, String cPUType, int rAM, int hDD, int dimention) {
		super(iD, price, manifacturer, brand, maxInputValtage, maxPowerConsumption, oS, cPUType, rAM, hDD);
		this.dimention = dimention;
	}

	/**
	 * @return the dimention
	 */
	public int getDimention() {
		return dimention;
	}

	/**
	 * @param dimention the dimention to set
	 */
	public void setDimention(int dimention) {
		this.dimention = dimention;
	}
	
	
	/**
	 * 
	 */
	public String toString(){
		String tabletStr = "Type: Desktop\n";
		tabletStr = tabletStr + "Item ID: "+ this.getID()+"\n";
		tabletStr = tabletStr + "Price: "+Double.toString(this.getPrice())+"$\n";
		tabletStr = tabletStr + "Manufacturer: "+this.getManifacturer()+"\n";
		tabletStr = tabletStr + "Brand: "+this.getBrand()+"\n";
		tabletStr = tabletStr + "Max Volt: "+ Integer.toString(getMaxInputVoltage())+" V\n";
		tabletStr = tabletStr + "Max Watt: "+ Integer.toString(getMaxPowerConsumption())+" W\n";
		tabletStr = tabletStr + "Operating System: "+ getOS()+"\n";
		tabletStr = tabletStr + "CPU Type: "+ getCPUType()+"\n";
		tabletStr = tabletStr + "RAM Capacity: "+ Integer.toString(getRAM())+" GB\n";
		tabletStr = tabletStr + "HDD Capacity: "+ Integer.toString(getHDD())+" GB\n";
		tabletStr = tabletStr + "Dimension: " + Integer.toString(getDimention())+ "in\n";
		
		return tabletStr;
	}
}
