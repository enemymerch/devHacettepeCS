import java.io.IOException;
import java.util.*;

public class Main {
	public static void main(String argv[]){
		
		/* 
		 * Argv[0] ---> airports.txt
		 * Argv[1] ---> flights.txt
		 * Argv[2] ---> commands.txt
		 * Argv[3] ---> output.txt
		 */
		Main deneme = new Main();
		
		// created input object for .txt reading
		Input input = new Input(argv[0], argv[1], argv[2]);
		
		// read .txt files 
		String[] FlightsInfo = input.ReadFromFlightsFile();
		String[] AirportsInfo = input.ReadFromAirportsFile();
		String[] CommandsInfo = input.ReadFromCommandsFile();
		
		// Create Commands[] (array contains every command from commands.txt)
		Command[] Commands = deneme.getCommands(CommandsInfo);
		
		// Create Cities[] (array contains every Cities from airports.txt)
		City[] Cities = deneme.getCities(AirportsInfo);
		
		// Create Flights[] (array contains every Flight from flights.txt)
		Flight[] Flights = deneme.getFlights(FlightsInfo);
		
		
		/*
		 * CityFlightMap contain key as city objects and
		 * values as ArrayList<Flight> ,ArrayList<Flight> will contain that city's every flight.
		 * CityFlightMap will be used in getAllPaths method 
		 * for keep track between two vertices(two cities)
		 */
		HashMap<City, ArrayList<Flight>> CityFlightMap = new HashMap<City, ArrayList<Flight>>();
		for(int i=0;i<Cities.length;i++){
			ArrayList<Flight>  FlightsOfCity = Cities[i].getFlightsOfCity(Flights);
			CityFlightMap.put(Cities[i], FlightsOfCity);
		}
		
		for(int j=0;j<Commands.length;j++){
			/*
		 	*   flightsStack will be passed through recursive getAllPaths method 
		 	*   to keep track of path we're on
		 	*   if recursive method gets to targetCity(CommandArvvlCity) 
		 	*   This stack will be saved in ListOfPaths .
		 	*/
			Stack<Flight> FlightStack = new Stack<Flight>();

			/*
		 	* ListOfPaths  contain flightsPaths
		 	* and will be used in FindAllPaths method for
		 	* saving flightPaths of asked command
		 	*/
			List<Stack<Flight>> ListOfPaths = new ArrayList<Stack<Flight>>();
		
			/*
		 	*  find all paths from DeptCity to ArvvlCity
		 	*  getAllPaths method don't care about dates , times , day or airports of flights
		 	*  so, the flightPlans we get from getAllPaths method is not proper
		 	*  these flights which is not proper will be ruled out in next step  
		 	*  this procedure will be done for every command
		 	*/
			ListOfPaths = deneme.getAllPaths(ListOfPaths, CityFlightMap, Cities, FlightStack, 
						deneme.getCitybyName(Cities, Commands[j].getCommandDeptCity()), 
						deneme.getCitybyName(Cities, Commands[j].getCommandArrvlCity()));
		
			/*
		 	* isFlightPlanProper method
		 	* return true or false ; according to if flightPlan is proper or not
			* if not , flightPlan will be deleted from ListOfPaths !
		 	* this procedure will be done for every command
			*/
			int i=0;
			while(i<ListOfPaths.size()){
				if(!deneme.isFlightPlanProper(ListOfPaths.get(i))){
					ListOfPaths.remove(i);
					// Position of Elements of ListOfPaths has changed 
					// Start looping from the top
					i = 0;
				}else{
					i++;
				}
			}
			String CommandName = Commands[j].getCommandName();
			/*
			 * we have a list that contains all the flights !
			 * time to execute the commands
			 */
			if(CommandName.equalsIgnoreCase("listall")){
				Commands[j].listAll(ListOfPaths);
			}else if(CommandName.equalsIgnoreCase("listproper")){
				Commands[j].listProper(ListOfPaths);
			}else if(CommandName.equalsIgnoreCase("listcheapest")){
				Commands[j].listCheapest(ListOfPaths);
			}else if(CommandName.equalsIgnoreCase("listquickest")){
				Commands[j].listQuickest(ListOfPaths);
			}else if(CommandName.equalsIgnoreCase("listcheaper")){
				Commands[j].listCheaper(ListOfPaths);
			}else if(CommandName.equalsIgnoreCase("listquicker")){
				Commands[j].setProper(ListOfPaths);
				Commands[j].listQuicker(ListOfPaths);
			}else if(CommandName.equalsIgnoreCase("listexcluding")){
				Commands[j].listExcluding(ListOfPaths);
			}else if(CommandName.equalsIgnoreCase("listonlyfrom")){
				Commands[j].listOnlyFrom(ListOfPaths);
			}
		}
		
		Output write = new Output(argv[3]);
		try{
			write.WriteToOutputFile(Commands);
		}catch(IOException e){
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 
	 * @param Cities contains every city object
	 * @param CityName is a string for searching for City object
	 * @return City object that has found , if not found return null 
	 */
	public City getCitybyName(City[] Cities, String CityName){
		for(int i=0;i<Cities.length;i++){
			if(Cities[i].getCityName().equals(CityName)){
				return Cities[i];
			}
		}
		return null;
	}
	
	/**
	 * 
	 * @param AirportsInfo readed from airport.txt 
	 * @return 	City[] Cities which contains every city object
	 */
	public City[] getCities(String[] AirportsInfo){
		City[] Cities = new City[AirportsInfo.length];
		for(int i=0;i<Cities.length;i++){
			Cities[i] = new City(AirportsInfo[i]);
		}
		return Cities;
	}
	
	/**
	 * 
	 * @param FlightsInfo readed from flights.txt 
	 * @return Flight[] which contains every flight object
	 */
	public Flight[] getFlights(String[] FlightsInfo){
		Flight[] Flights = new Flight[FlightsInfo.length];
		for(int i=0;i<Flights.length;i++){
			Flights[i] = new Flight(FlightsInfo[i]);
		}
		return Flights;
	}
	
	/**
	 * 
	 * @param CommandsInfo readed from commands.txt 
	 * @return Command[] which contains evert command object
	 */
	public Command[] getCommands(String[] CommandsInfo){
		Command[] Commands = new Command[CommandsInfo.length];
		for(int i=0;i<Commands.length;i++){
			Commands[i] = new Command(CommandsInfo[i]);
		}
		return Commands;
	}
	
	/**
	 * 
	 * @param FlightPlan gets Stack FlightsPlan which has found from getAllPaths() method  
	 * @return true if flightPlan is proper , false if it's not . 
	 */
	public boolean isFlightPlanProper(Stack<Flight> FlightPlan){
		for(int i=0;i<FlightPlan.size()-1;i++){
			
			// Checking airports f1.arrival == f2.dept ?
			if(!FlightPlan.get(i).getArrival().equals(FlightPlan.get(i+1).getDeparture())){
				return false;
			}

			if(!FlightPlan.get(i).compareDatesTimes(FlightPlan.get(i+1))){
				return false;
			}
		}

		// Nothing is wrong with this flightPlan 
		return true;
	}
	
	
	
	public List<Stack<Flight>> getAllPaths(List<Stack<Flight>> ListOfPaths,HashMap<City, ArrayList<Flight>> CityFlightMap,City[] Cities,Stack<Flight> FlightStack,
	 City From, City To ){

		if(From.isEquals(To)){
			Stack<Flight> tempStack = new Stack<Flight>();
			
			for(int i=0;i<FlightStack.size();i++){
				Flight temp1 = new Flight();
				temp1.setAll(FlightStack.get(i));
				tempStack.push(temp1);
			}
			ListOfPaths.add(tempStack);
			FlightStack.pop();

			return ListOfPaths;
		}else{
		
		if(CityFlightMap.get(From).size() != 0){

		
			for(int i=0;i<CityFlightMap.get(From).size();i++){
				City temp =  CityFlightMap.get(From).get(i).getArrivalCity(Cities);
				
				FlightStack.push(CityFlightMap.get(From).get(i));
				getAllPaths(ListOfPaths, CityFlightMap, Cities, FlightStack, temp , To);
				
				
			}

		}
		if(!FlightStack.isEmpty()){
			FlightStack.pop();
		}
		return ListOfPaths;
		}
	}

	
}
