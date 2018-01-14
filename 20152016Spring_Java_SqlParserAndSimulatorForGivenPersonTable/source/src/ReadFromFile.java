import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;

public class ReadFromFile {
	private String DataFilePath;
	private String CommandsFilePath;
	
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
	
	  public String[] ReadFromDataFile() {

			BufferedReader Buffer = null;
			String line = "";

			try {
				int length = 0,i=0;
				Buffer = new BufferedReader(new FileReader(DataFilePath));
				while ((line = Buffer.readLine()) != null) {
					length++;
				}
				String[] Data = new String[length];
				Buffer.close();
				Buffer = new BufferedReader(new FileReader(DataFilePath));
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

	
	public ReadFromFile(String commandsFilePath, String dataFilePath) {
		CommandsFilePath = commandsFilePath;
		DataFilePath = dataFilePath;
	}
	public String getDataFilePath() {
		return DataFilePath;
	}
	public void setDataFilePath(String dataFilePath) {
		DataFilePath = dataFilePath;
	}
	public String getCommandsFilePath() {
		return CommandsFilePath;
	}
	public void setCommandsFilePath(String commandsFilePath) {
		CommandsFilePath = commandsFilePath;
	}
	
	
}
