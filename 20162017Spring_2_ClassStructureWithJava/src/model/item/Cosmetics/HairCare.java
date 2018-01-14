package model.item.Cosmetics;

public class HairCare extends Cosmetic{
	private boolean isMedicated;

	
	
	/**
	 * @param iD
	 * @param price
	 * @param manifacturer
	 * @param brand
	 * @param expirationDate
	 * @param weight
	 * @param isOrganic
	 * @param isMedicated
	 */
	public HairCare(String iD, double price, String manifacturer, String brand, String expirationDate, double weight,
			boolean isOrganic, boolean isMedicated) {
		super(iD, price, manifacturer, brand, expirationDate, weight, isOrganic);
		this.isMedicated = isMedicated;
	}

	/**
	 * @return the isMedicated
	 */
	public boolean isMedicated() {
		return isMedicated;
	}

	/**
	 * @param isMedicated the isMedicated to set
	 */
	public void setMedicated(boolean isMedicated) {
		this.isMedicated = isMedicated;
	}
	
	
	/**
	 * 
	 */
	public String toString(){
		String haircareStr = "Type: Desktop\n";
		haircareStr = haircareStr + "Item ID: "+ this.getID()+"\n";
		haircareStr = haircareStr + "Price: "+Double.toString(this.getPrice())+"$\n";
		haircareStr = haircareStr + "Manufacturer: "+this.getManifacturer()+"\n";
		haircareStr = haircareStr + "Brand: "+this.getBrand()+"\n";
		haircareStr = haircareStr + "Organic: ";
		if(isOrganic()){
			haircareStr = haircareStr + "Yes\n";
		}else{
			haircareStr = haircareStr + "No\n";	
		}
		
		haircareStr = haircareStr + "Expiration Date: "+ getExpirationDate() +" W\n";
		haircareStr = haircareStr + "Weight: "+ Double.toString(getWeight())+ " g.\n";
		haircareStr = haircareStr + "Medicated: ";

		if(isMedicated()){
			haircareStr = haircareStr + "Yes\n";
		}else{
			haircareStr = haircareStr + "No\n";	
		}
		return haircareStr;
	}
	
	
}
