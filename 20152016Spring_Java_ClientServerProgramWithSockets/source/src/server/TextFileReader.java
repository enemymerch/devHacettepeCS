package server;

import java.io.BufferedReader;
import java.io.FileReader;

public class TextFileReader {
	private String filePath;
	
	public TextFileReader(String filePath){
		this.filePath = filePath;
	}
	
	public String readFile(){
		
		
		BufferedReader buffer;
		try {
			buffer = new BufferedReader(new FileReader(filePath));

			String allContext = "";
			String temp;
			while((temp = buffer.readLine()) != null){
				allContext = allContext + temp.trim();
			}
		// Reading disk to memory done 
			buffer.close();
			return allContext;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}

