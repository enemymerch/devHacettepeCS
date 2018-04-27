import java.util.ArrayList;

public class City {
	private String CityName;
	private String[] AirportList;
	private int NumberOfAirports;
	
	
	public City(String Info){
		String[] Tokens = CityInfoParser(Info);
		
		// First token is cityName expInfo: Paris	CDG	ORY	BVA
		CityName = Tokens[0];
		
		//Create a array for AirportList
		NumberOfAirports = Tokens.length-1;  
		AirportList = new String[NumberOfAirports];
		for(int i=0;i<NumberOfAirports;i++){
			AirportList[i] = Tokens[i+1];
		}
		
	}
	// Checks two city object for equality
	public boolean isEquals(City T){
		if(this.AirportList.equals(T.getAirportList())&& this.CityName.equals(T.getCityName())
				&& this.NumberOfAirports == T.getNumberOfAirports()){
			return true;
		}
		return false;
	}
	
	// Parse given Info with "\\t" char 
	public String[] CityInfoParser(String Info){
		return 	Info.split("\\t");
	}
	// Gets Flights[] and ---> all flights taken from flights.txt
	// Finds flights of this city according to the Flight[j].getDeparture()
	// Returns FlightsOfCity[] ArrayList which contains  flights of this city !
	public ArrayList<Flight> getFlightsOfCity(Flight[] Flights){
		ArrayList<Flight> FlightsOfCity = new ArrayList<Flight>();
		
		for(int i = 0;i<NumberOfAirports;i++){
			for(int j = 0;j<Flights.length;j++){
				if(AirportList[i].equals(Flights[j].getDeparture())){
					FlightsOfCity.add(Flights[j]);
				}
			}
		}
		return FlightsOfCity;
	}
	
	
	@Override
	public String toString(){
		String Airports = "";
		for(int i=0;i<NumberOfAirports;i++){
			Airports = Airports + " "+ AirportList[i];  
		}
		return "\nCityName :"+CityName+"\nAirports of City :"+Airports;
	}
	/**
	 * @return the cityName
	 */
	public String getCityName() {
		return CityName;
	}
	/**
	 * @param cityName the cityName to set
	 */
	public void setCityName(String cityName) {
		CityName = cityName;
	}
	/**
	 * @return the airportList
	 */
	public String[] getAirportList() {
		return AirportList;
	}
	/**
	 * @param airportList the airportList to set
	 */
	public void setAirportList(String[] airportList) {
		AirportList = airportList;
	}
	/**
	 * @return the numberOfAirports
	 */
	public int getNumberOfAirports() {
		return NumberOfAirports;
	}
	/**
	 * @param numberOfAirports the numberOfAirports to set
	 */
	public void setNumberOfAirports(int numberOfAirports) {
		NumberOfAirports = numberOfAirports;
	}
	
}
