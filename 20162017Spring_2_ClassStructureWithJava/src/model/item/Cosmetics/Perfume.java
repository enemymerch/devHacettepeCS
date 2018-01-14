package model.item.Cosmetics;

public class Perfume extends Cosmetic{
	private String LastingDuration;
	


	/**
	 * @param iD
	 * @param price
	 * @param manifacturer
	 * @param brand
	 * @param expirationDate
	 * @param weight
	 * @param isOrganic
	 * @param lastingDuration
	 */
	public Perfume(String iD, double price, String manifacturer, String brand, String expirationDate, double weight,
			boolean isOrganic, String lastingDuration) {
		super(iD, price, manifacturer, brand, expirationDate, weight, isOrganic);
		LastingDuration = lastingDuration;
	}

	/**
	 * @return the lastingDuration
	 */
	public String getLastingDuration() {
		return LastingDuration;
	}

	/**
	 * @param lastingDuration the lastingDuration to set
	 */
	public void setLastingDuration(String lastingDuration) {
		LastingDuration = lastingDuration;
	}
	
	/**
	 * 
	 */
	public String toString(){
		String perfumeStr = "Type: Desktop\n";
		perfumeStr = perfumeStr + "Item ID: "+ this.getID()+"\n";
		perfumeStr = perfumeStr + "Price: "+Double.toString(this.getPrice())+"$\n";
		perfumeStr = perfumeStr + "Manufacturer: "+this.getManifacturer()+"\n";
		perfumeStr = perfumeStr + "Brand: "+this.getBrand()+"\n";
		perfumeStr = perfumeStr + "Organic: ";
		if(isOrganic()){
			perfumeStr = perfumeStr + "Yes\n";
		}else{
			perfumeStr = perfumeStr + "No\n";	
		}
		
		perfumeStr = perfumeStr + "Expiration Date: "+ getExpirationDate() +" W\n";
		perfumeStr = perfumeStr + "Weight: "+ Double.toString(getWeight())+ " g.\n";
		perfumeStr = perfumeStr + "Lasting Duration: " + getLastingDuration() + " min.\n";

		return perfumeStr;
	}
}
