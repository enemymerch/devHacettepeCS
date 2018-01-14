package model.item.Electronics;

public class Laptop extends Computer{
	private boolean hasHTMLSupport;

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
	 * @param hasHTMLSupport
	 */
	public Laptop(String iD, double price, String manifacturer, String brand, int maxInputValtage,
			int maxPowerConsumption, String oS, String cPUType, int rAM, int hDD, boolean hasHTMLSupport) {
		super(iD, price, manifacturer, brand, maxInputValtage, maxPowerConsumption, oS, cPUType, rAM, hDD);
		this.hasHTMLSupport = hasHTMLSupport;
	}

	/**
	 * @return the hasHTMLSupport
	 */
	public boolean isHasHTMLSupport() {
		return hasHTMLSupport;
	}

	/**
	 * @param hasHTMLSupport the hasHTMLSupport to set
	 */
	public void setHasHTMLSupport(boolean hasHTMLSupport) {
		this.hasHTMLSupport = hasHTMLSupport;
	}
	
	/**
	 * 
	 */
	public String toString(){
		String laptopStr = "Type: Desktop\n";
		laptopStr = laptopStr + "Item ID: "+ this.getID()+"\n";
		laptopStr = laptopStr + "Price: "+Double.toString(this.getPrice())+"$\n";
		laptopStr = laptopStr + "Manufacturer: "+this.getManifacturer()+"\n";
		laptopStr = laptopStr + "Brand: "+this.getBrand()+"\n";
		laptopStr = laptopStr + "Max Volt: "+ Integer.toString(getMaxInputVoltage())+" V\n";
		laptopStr = laptopStr + "Max Watt: "+ Integer.toString(getMaxPowerConsumption())+" W\n";
		laptopStr = laptopStr + "Operating System: "+ getOS()+"\n";
		laptopStr = laptopStr + "CPU Type: "+ getCPUType()+"\n";
		laptopStr = laptopStr + "RAM Capacity: "+ Integer.toString(getRAM())+" GB\n";
		laptopStr = laptopStr + "HDD Capacity: "+ Integer.toString(getHDD())+" GB\n";
		laptopStr = laptopStr + "HDMI support: ";

		
		if(hasHTMLSupport){
			laptopStr = laptopStr + "Yes\n";
		}else{
			laptopStr = laptopStr + "No\n";	
		}
		
		return laptopStr;
	}
}
