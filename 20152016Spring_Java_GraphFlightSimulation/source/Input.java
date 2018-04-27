import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Input {
	private String FlightsFilePath;
	private String AirportsFilePath;
	private String CommandsFilePath;
	

	/**
	 * @param flightsFilePath
	 * @param airportsFilePath
	 * @param commandsFilePath
	 */
	public Input(String airportsFilePath, String flightsFilePath, String commandsFilePath) {
		FlightsFilePath = flightsFilePath;
		AirportsFilePath = airportsFilePath;
		CommandsFilePath = commandsFilePath;
	}
	// Reads from CommandsFile
	// Return lines of file
	public String[] ReadFromCommandsFile(){
		BufferedReader Buffer = null;
		String line = "";
		try {
			int length = 0,i=0;
			Buffer = new BufferedReader(new FileReader(CommandsFilePath));
			while ((line = Buffer.readLine()) != null) {
				length++;
			}
			String[] Data = new String[length];
			Buffer.close();
			Buffer = new BufferedReader(new FileReader(CommandsFilePath));
			while((line = Buffer.readLine()) != null){
				Data[i] = line;
				i++;
			}
			Buffer.close();
			return Data;
		} catch(IOException e){
			e.printStackTrace();
			return null;
		}
	}
	
	// Reads from Airport file 
	// return lines of file
	public String[] ReadFromAirportsFile(){
		BufferedReader Buffer = null;
		String line = "";
		try {
			int length = 0,i=0;
			Buffer = new BufferedReader(new FileReader(AirportsFilePath));
			while ((line = Buffer.readLine()) != null) {
				length++;
			}
			String[] Data = new String[length];
			Buffer.close();
			Buffer = new BufferedReader(new FileReader(AirportsFilePath));
			while((line = Buffer.readLine()) != null){
				Data[i] = line;
				i++;
			}
			Buffer.close();
			return Data;
		} catch(IOException e){
			e.printStackTrace();
			return null;
		}
	}
	//Reads from flights.txt and return lines of file as string array
	public String[] ReadFromFlightsFile(){
		BufferedReader Buffer = null;
		String line = "";
		try {
			int length = 0,i=0;
			Buffer = new BufferedReader(new FileReader(FlightsFilePath));
			while ((line = Buffer.readLine()) != null) {
				length++;
			}
			String[] Data = new String[length];
			Buffer.close();
			Buffer = new BufferedReader(new FileReader(FlightsFilePath));
			while((line = Buffer.readLine()) != null){
				Data[i] = line;
				i++;
			}
			Buffer.close();
			return Data;
		} catch(IOException e){
			e.printStackTrace();
			return null;
		}
	}
	
	// Read from file which's path is given as argument 
	// and return lines of file as string array
	public String[] ReadFromFile(String CommandsFilePath){
		BufferedReader Buffer = null;
		String line = "";
		try {
			int length = 0,i=0;
			Buffer = new BufferedReader(new FileReader(CommandsFilePath));
			while ((line = Buffer.readLine()) != null) {
				length++;
			}
			String[] Data = new String[length];
			Buffer.close();
			Buffer = new BufferedReader(new FileReader(CommandsFilePath));
			while((line = Buffer.readLine()) != null){
				Data[i] = line;
				i++;
			}
			Buffer.close();
			return Data;
		} catch(IOException e){
			e.printStackTrace();
			return null;
		}
	}


	/**
	 * @return the flightsFilePath
	 */
	public String getFlightsFilePath() {
		return FlightsFilePath;
	}


	/**
	 * @param flightsFilePath the flightsFilePath to set
	 */
	public void setFlightsFilePath(String flightsFilePath) {
		FlightsFilePath = flightsFilePath;
	}


	/**
	 * @return the airportsFilePath
	 */
	public String getAirportsFilePath() {
		return AirportsFilePath;
	}


	/**
	 * @param airportsFilePath the airportsFilePath to set
	 */
	public void setAirportsFilePath(String airportsFilePath) {
		AirportsFilePath = airportsFilePath;
	}


	/**
	 * @return the commandsFilePath
	 */
	public String getCommandsFilePath() {
		return CommandsFilePath;
	}


	/**
	 * @param commandsFilePath the commandsFilePath to set
	 */
	public void setCommandsFilePath(String commandsFilePath) {
		CommandsFilePath = commandsFilePath;
	}
}
