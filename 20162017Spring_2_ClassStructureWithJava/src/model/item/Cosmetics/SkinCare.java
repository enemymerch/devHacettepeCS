package model.item.Cosmetics;

public class SkinCare extends Cosmetic{
	private boolean isBabySevsitive;

	/**
	 * @param iD
	 * @param price
	 * @param manifacturer
	 * @param brand
	 * @param expirationDate
	 * @param weight
	 * @param isOrganic
	 * @param isBabySevsitive
	 */
	public SkinCare(String iD, double price, String manifacturer, String brand, String expirationDate, double weight,
			boolean isOrganic, boolean isBabySevsitive) {
		super(iD, price, manifacturer, brand, expirationDate, weight, isOrganic);
		this.isBabySevsitive = isBabySevsitive;
	}

	/**
	 * @return the isBabySevsitive
	 */
	public boolean isBabySevsitive() {
		return isBabySevsitive;
	}

	/**
	 * @param isBabySevsitive the isBabySevsitive to set
	 */
	public void setBabySevsitive(boolean isBabySevsitive) {
		this.isBabySevsitive = isBabySevsitive;
	}
	
	
	/**
	 * 
	 */
	public String toString(){
		String skincareStr = "Type: Desktop\n";
		skincareStr = skincareStr + "Item ID: "+ this.getID()+"\n";
		skincareStr = skincareStr + "Price: "+Double.toString(this.getPrice())+"$\n";
		skincareStr = skincareStr + "Manufacturer: "+this.getManifacturer()+"\n";
		skincareStr = skincareStr + "Brand: "+this.getBrand()+"\n";
		skincareStr = skincareStr + "Organic: ";
		if(isOrganic()){
			skincareStr = skincareStr + "Yes\n";
		}else{
			skincareStr = skincareStr + "No\n";	
		}
		
		skincareStr = skincareStr + "Expiration Date: "+ getExpirationDate() +" W\n";
		skincareStr = skincareStr + "Weight: "+ Double.toString(getWeight())+ " g.\n";
		skincareStr = skincareStr + "Baby Sensitive: ";

		if(isBabySevsitive()){
			skincareStr = skincareStr + "Yes\n";
		}else{
			skincareStr = skincareStr + "No\n";	
		}

		return skincareStr;
	}
	
}
