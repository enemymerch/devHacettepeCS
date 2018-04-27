package model.item.Electronics;

public class Computer extends Electronic{
	private String OS;
	private String CPUType;
	private int RAM;
	private int HDD;
	
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
	 */
	public Computer(String iD, double price, String manifacturer, String brand, int maxInputValtage,
			int maxPowerConsumption, String oS, String cPUType, int rAM, int hDD) {
		super(iD, price, manifacturer, brand, maxInputValtage, maxPowerConsumption);
		OS = oS;
		CPUType = cPUType;
		RAM = rAM;
		HDD = hDD;
	}

	/**
	 * @return the oS
	 */
	public String getOS() {
		return OS;
	}

	/**
	 * @param oS the oS to set
	 */
	public void setOS(String oS) {
		OS = oS;
	}

	/**
	 * @return the cPUType
	 */
	public String getCPUType() {
		return CPUType;
	}

	/**
	 * @param cPUType the cPUType to set
	 */
	public void setCPUType(String cPUType) {
		CPUType = cPUType;
	}

	/**
	 * @return the rAM
	 */
	public int getRAM() {
		return RAM;
	}

	/**
	 * @param rAM the rAM to set
	 */
	public void setRAM(int rAM) {
		RAM = rAM;
	}

	/**
	 * @return the hDD
	 */
	public int getHDD() {
		return HDD;
	}

	/**
	 * @param hDD the hDD to set
	 */
	public void setHDD(int hDD) {
		HDD = hDD;
	}
	
	
}
