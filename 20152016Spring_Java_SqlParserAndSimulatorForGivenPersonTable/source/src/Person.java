
public class Person implements Comparable<Person>{
	private int CID;
	private String FirstName;
	private String LastName;
	private String City;
	private String AdressLine1;
	private int SocialSecurityNumber;
	
	public Person(int cID, String firstName, String lastName, String city, String adressLine1,
			int socialSecurityNumber) {
		CID = cID;
		FirstName = firstName;
		LastName = lastName;
		City = city;
		AdressLine1 = adressLine1;
		SocialSecurityNumber = socialSecurityNumber;
	}
	@Override
	public int compareTo(Person arg0) {
		if(this.SocialSecurityNumber <= arg0.getSocialSecurityNumber())
			return 1;
		if(this.SocialSecurityNumber > arg0.getSocialSecurityNumber())
			return -1;
		return 0;
	}
	@Override
	public String toString(){
		
		return "CID :" +CID +"\nFirstName :"+ FirstName + "\nLastName :"+ LastName +"\nCity :"+City+"\nAdressLine1 :"+
				AdressLine1+"\nSocialSecurityNumber :"+ SocialSecurityNumber+"\n";
		
	}

	public int getCID() {
		return CID;
	}

	public void setCID(int cID) {
		CID = cID;
	}

	public String getFirstName() {
		return FirstName;
	}

	public void setFirstName(String firstName) {
		FirstName = firstName;
	}

	public String getLastName() {
		return LastName;
	}

	public void setLastName(String lastName) {
		LastName = lastName;
	}

	public String getCity() {
		return City;
	}

	public void setCity(String city) {
		City = city;
	}

	public String getAdressLine1() {
		return AdressLine1;
	}

	public void setAdressLine1(String adressLine1) {
		AdressLine1 = adressLine1;
	}

	public int getSocialSecurityNumber() {
		return SocialSecurityNumber;
	}

	public void setSocialSecurityNumber(int socialSecurityNumber) {
		SocialSecurityNumber = socialSecurityNumber;
	}



}

