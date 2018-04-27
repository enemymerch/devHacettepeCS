/*
 * in this class
 * i keep variables  about command 
 */
public class Command {
	private String CIDSearchVal = " ";
	private String FirstNameSearchVal = " ";
	private String LastNameSearchVal = " ";
	private String CitySearchVal = " ";
	private String AdressLine1SearchVal = " ";
	private String LowerSocialSecurityNumberSearchVal = " ";
	private String UpperSocialSecurityNumberSearchVal = " ";
	private String SELECT = " ";
	@Override
	public String toString(){
		return 	CIDSearchVal+"\t"+FirstNameSearchVal+"\t"
				+LastNameSearchVal+"\t"+CitySearchVal+"\t"+AdressLine1SearchVal
				+"\t"+LowerSocialSecurityNumberSearchVal+"\t"+UpperSocialSecurityNumberSearchVal+"\t";
	} 
	
	public Command(String CommandData){
		this.SELECT = CommandData;
		/*
		 * Splitting the all line from commands.txt
		 * and matching tokens to variables
		 */
		String[] tempTokens1 = CommandData.split("WHERE");
		String[] tempTokens2 = tempTokens1[1].split("AND");
		for(int i=0;i<tempTokens2.length;i++){
			if(tempTokens2[i].contains("<")){
				String[] tempTokens3 = tempTokens2[i].split("<");
				this.LowerSocialSecurityNumberSearchVal = tempTokens3[1].trim();
			}else if(tempTokens2[i].contains(">")){
				String[] tempTokens3 = tempTokens2[i].split(">");
				this.UpperSocialSecurityNumberSearchVal = tempTokens3[1].trim();
			}else if(tempTokens2[i].contains("~")){
				String[] tempTokens3 = tempTokens2[i].split("~");
				tempTokens3[0] =tempTokens3[0].trim();
				tempTokens3[1] =tempTokens3[1].trim();
				if(tempTokens3[0].trim().equalsIgnoreCase("FirstName")){
					this.FirstNameSearchVal = tempTokens3[1].trim();
				}else if(tempTokens3[0].trim().equalsIgnoreCase("LastName")){
					this.LastNameSearchVal = tempTokens3[1].trim();
				}else if(tempTokens3[0].trim().equalsIgnoreCase("City")){
					this.CitySearchVal = tempTokens3[1].trim();
				}else if(tempTokens3[0].trim().equalsIgnoreCase("AddressLine1")){
					this.AdressLine1SearchVal = tempTokens3[1].trim();
				}
			}
		}
	}
	/*
	 * is there any more search to do ?
	 */
	public boolean isCommandDone(){
		if(this.AdressLine1SearchVal.equals(" ") && this.CIDSearchVal.equals(" ") && this.CitySearchVal.equals(" ")
			&& this.FirstNameSearchVal.equals(" ") && this.LastNameSearchVal.equals(" ") && this.LowerSocialSecurityNumberSearchVal.equals(" ")
			&& this.UpperSocialSecurityNumberSearchVal.equals(" ")){
			return true;
		}
		return false;
	}



	public String getSELECT() {
		return SELECT;
	}

	public void setSELECT(String sELECT) {
		SELECT = sELECT;
	}

	public String getUpperSocialSecurityNumberSearchVal() {
		return UpperSocialSecurityNumberSearchVal;
	}

	public void setUpperSocialSecurityNumberSearchVal(String upperSocilaSecurityNumberSearchVal) {
		UpperSocialSecurityNumberSearchVal = upperSocilaSecurityNumberSearchVal;
	}

	public String getLowerSocialSecurityNumberSearchVal() {
		return LowerSocialSecurityNumberSearchVal;
	}

	public void setLowerSocialSecurityNumberSearchVal(String lowerSocilaSecurityNumberSearchVal) {
		LowerSocialSecurityNumberSearchVal = lowerSocilaSecurityNumberSearchVal;
	}

	public String getAdressLine1SearchVal() {
		return AdressLine1SearchVal;
	}

	public void setAdressLine1SearchVal(String adressLine1SearchVal) {
		AdressLine1SearchVal = adressLine1SearchVal;
	}

	public String getCitySearchVal() {
		return CitySearchVal;
	}

	public void setCitySearchVal(String citySearchVal) {
		CitySearchVal = citySearchVal;
	}

	public String getLastNameSearchVal() {
		return LastNameSearchVal;
	}

	public void setLastNameSearchVal(String lastNameSearchVal) {
		LastNameSearchVal = lastNameSearchVal;
	}

	public String getFirstNameSearchVal() {
		return FirstNameSearchVal;
	}

	public void setFirstNameSearchVal(String firstNameSearchVal) {
		FirstNameSearchVal = firstNameSearchVal;
	}

	public String getCIDSearchVal() {
		return CIDSearchVal;
	}

	public void setCIDSearchVal(String cIDSearchVal) {
		CIDSearchVal = cIDSearchVal;
	}
}
