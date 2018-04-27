import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class WriteToFile {
	public void WriteToOutputFile(ArrayList<Person> array, Command command, long milisec, int t){
		try {
			
			File file = new File("output.txt");
			if (file.exists() && t == 0) {
				file.delete();
				file.createNewFile();
			}else{
				file.createNewFile();
			}
			FileWriter fw = new FileWriter(file.getAbsolutePath(),true);
			BufferedWriter bw = new BufferedWriter(fw);

			String[] tempTokens1 = command.getSELECT().split("WHERE");
			String[] tempTokens2 = tempTokens1[0].split(",");
			String[] tempTokens3 = tempTokens2[0].split(" ");
			String[] fTokens = new String[tempTokens2.length];

			fTokens[0] = tempTokens3[1].trim();
			for(int i=1;i<tempTokens2.length;i++){
				fTokens[i] = tempTokens2[i].trim();
			}
			
			String output = "";
			bw.append("CommandText: \" "+command.getSELECT() + "\" "+
					"\nResult:\n\n");
			for(int i=0;i<fTokens.length;i++){
				output = output + fTokens[i] + "\t\t";
			}				
			output = output +"\n";
			bw.append(output);
			output = "";
			if(array.isEmpty()){
				bw.append("Empty rowset");
			}
			else{
				for(int k=0;k<array.size();k++){
					for(int i=0;i<fTokens.length;i++){
						if(fTokens[i].equalsIgnoreCase("FirstName")){
							output = output + (array.get(k).getFirstName()+"\t\t");
						}else if(fTokens[i].equalsIgnoreCase("LastName")){
							output = output + (array.get(k).getLastName()+"\t\t");
						}else if(fTokens[i].equalsIgnoreCase("City")){
							output = output + (array.get(k).getCity()+"\t\t");
						}else if(fTokens[i].equalsIgnoreCase("AdressLine1")){
							output = output + (array.get(k).getAdressLine1()+"\t\t");
						}else if (fTokens[i].equalsIgnoreCase("SocialSecurityNumber")){
							output = output + (array.get(k).getSocialSecurityNumber()+"\t\t");
						}else if(fTokens[i].equalsIgnoreCase("CID")){
							output = output + (array.get(k).getCID()+"\t\t");
						}
					}
					output = output + "\n";
					bw.append(output);
					output = "";
				}
			}
			bw.append("\n-------------------\nProcessTime: "+milisec+" milliseconds\n\n");
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
