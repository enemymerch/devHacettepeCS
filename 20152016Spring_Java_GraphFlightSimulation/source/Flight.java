
public class Flight {
	private String FlightID;
	private String Departure;
	private String Arrival;
	private String Date;
	private String DepartureDate;
	private String Day;
	private String Duration;
	private int Price;
	
	
	public Flight(){
		// TODO:
	}
	
	public void setAll(Flight T){
		this.FlightID = T.getFlightID();
		this.Departure = T.getDeparture();
		this.Arrival = T.getArrival();
		this.Date = T.getDate();
		this.DepartureDate = T.getDepartureDate();
		this.Day = T.getDay();
		this.Duration = T.getDuration();
		this.Price = T.getPrice();
	}
	public Flight(String Info){
		String[] Tokens = FlightInfoParser(Info);
		FlightID = Tokens[0];
		Departure = Tokens[1];
		Arrival = Tokens[2];
		Date = Tokens[3];
		DepartureDate = Tokens[4];
		Day = Tokens[5];
		Duration = Tokens[6];
		Price = Integer.parseInt(Tokens[7]);
	}

	
	// compares two flights' dates and Times 
	public boolean compareDatesTimes(Flight T){
		String[] date1 = this.getDate().split("/");
		String[] date2 = T.getDate().split("/");
		
		int year1 = Integer.parseInt(date1[2]);
		int year2 = Integer.parseInt(date2[2]);

		int month1 = Integer.parseInt(date1[1]);
		int month2 = Integer.parseInt(date2[1]);
		
		int day1 = Integer.parseInt(date1[0]);
		int day2 = Integer.parseInt(date2[0]);
		
		int hour1 = this.getArrivalTime();
		int hour2 = T.getDepartureDateAsMin();

		if(year1==year2){
			if(month1==month2){
				if(day1==day2){
					if(hour1<hour2){
						return true;
					}else{
						return false;
					}
				}else{
					return true;
				}
			}else if(month1<month2){
				return true;
			}
		}

		return false;
	}
	
	// returns the arrival city object of flight
	public City getArrivalCity(City[] Cities){
		for(int i=0;i<Cities.length;i++){
			//String[] AirportList = new String[Cities.length];
			//AirportList = Cities[i].getAirportList();
			String[] AirportList = Cities[i].getAirportList();
			for(int j=0;j<AirportList.length;j++){
				if(this.Arrival.equals(AirportList[j])){
					return Cities[i];
				}
			}
		}
		return null;
	}
	
	// Parser for contructor of Flight class
	public String[] FlightInfoParser(String Info){
		String[] TempTokens = Info.split("\\t");
		
		// Because we're gonna parse <dept>-><arr> 
		// meaning : +1 length 
		String[] Tokens = new String[TempTokens.length+3];
		Tokens[0] = TempTokens[0];
		
		String[] TempTokens2 = TempTokens[1].split("->");
		Tokens[1] = TempTokens2[0];
		Tokens[2] = TempTokens2[1];
		
		String[] TempTokens3 = TempTokens[2].split(" ");
		Tokens[3] = TempTokens3[0];
		Tokens[4] = TempTokens3[1];
		Tokens[5] = TempTokens3[2];
		//System.out.println(Tokens[1]);
		//System.out.println(Tokens[2]);
		//System.out.println("length : " +TempTokens.length);
		

		Tokens[6] = TempTokens[3];
		Tokens[7] = TempTokens[4];
		
		return Tokens;
	}
	// returns arrival time of the flight 
	public int getArrivalTime(){
		return 	this.getDepartureDateAsMin()+this.getDurationAsSecond();
	}
	
	
	@Override
	public String toString(){
		String temp = "\nFlightID :";
		temp = temp + FlightID+"\nFrom :" + Departure+" to :"+Arrival+"\n"+Date+"   "+Departure+"\n";
		return temp;
	}
	
	/**
	 * @return the date
	 */
	public String getDate() {
		return Date;
	}

	/**
	 * @param date the date to set
	 */
	public void setDate(String date) {
		Date = date;
	}
	
	/**
	 * @return the flightID
	 */
	public String getFlightID() {
		return FlightID;
	}
	/**
	 * @param flightID the flightID to set
	 */
	public void setFlightID(String flightID) {
		FlightID = flightID;
	}
	/**
	 * @return the departure
	 */
	public String getDeparture() {
		return Departure;
	}
	/**
	 * @param departure the departure to set
	 */
	public void setDeparture(String departure) {
		Departure = departure;
	}
	/**
	 * @return the arrival
	 */
	public String getArrival() {
		return Arrival;
	}
	/**
	 * @param arrival the arrival to set
	 */
	public void setArrival(String arrival) {
		Arrival = arrival;
	}
	/**
	 * @return the departureDate
	 */
	public String getDepartureDate() {
		return DepartureDate;
	}
	public int getDepartureDateAsMin(){
		String[] time = DepartureDate.split(":");
		int hours = Integer.parseInt(time[0]);
		int mins = Integer.parseInt(time[1]);
		
		return (hours*60)+mins;
	}
	/**
	 * @param departureDate the departureDate to set
	 */
	public void setDepartureDate(String departureDate) {
		DepartureDate = departureDate;
	}
	/**
	 * @return the day
	 */
	public String getDay() {
		return Day;
	}
	/**
	 * @param day the day to set
	 */
	public void setDay(String day) {
		Day = day;
	}
	/**
	 * @return the duration
	 */
	public String getDuration() {

		
		return Duration;
	}
	/**
	 * 
	 * @return the duration in seconds
	 */
	public int getDurationAsSecond(){
		String[] time = Duration.split(":");
		int hours = Integer.parseInt(time[0]);
		int mins = Integer.parseInt(time[1]);
		
		return (hours*60)+mins;
	}
	/**
	 * @param duration the duration to set
	 */
	public void setDuration(String duration) {
		Duration = duration;
	}
	/**
	 * @return the price
	 */
	public int getPrice() {
		return Price;
	}
	/**
	 * @param price the price to set
	 */
	public void setPrice(int price) {
		Price = price;
	}
}
