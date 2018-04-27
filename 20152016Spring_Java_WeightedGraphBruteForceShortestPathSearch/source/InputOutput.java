import java.io.*;
import java.util.ArrayList;


public class InputOutput {
	
	public ArrayList<String> readLines(String filePath){
		BufferedReader buffer;
		ArrayList<String> Context = new ArrayList<String>();
		try {
			buffer = new BufferedReader(new FileReader(filePath));
			
			
			String temp;
			while((temp = buffer.readLine()) != null){
				Context.add(temp);
			}
		// Reading disk to memory done 
			buffer.close();
			
			return Context;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void WriteToFile(String OutputPath,String ShortestPathOutput,String ConstrainedShortestPathOutput){
		try{
			
			File file = new File(OutputPath);
		
			if (file.exists()) {
				file.delete();
			}
		
			FileWriter filewWriter = new FileWriter(file.getAbsolutePath());
			BufferedWriter bufferedWriter = new BufferedWriter(filewWriter);
			
			bufferedWriter.append("21228932\n\n");
			bufferedWriter.append(ShortestPathOutput);
			bufferedWriter.append(ConstrainedShortestPathOutput);
			
			bufferedWriter.close();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
}
