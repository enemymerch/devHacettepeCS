import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class RegistryHandler {

	private String filePath;
	private File registry = null;
	
	public RegistryHandler(String filePath){	
		this.filePath = filePath;
		registry = new File(filePath);

	}
	public ArrayList<byte[]> getRegistriedHashValues(){

		ArrayList<byte[]> regInfo = new ArrayList<byte[]>();
		try{
			ArrayList<String> lines = getRegistryFileLines(); // lines
			lines.remove(lines.size()-1); // deleting last line! (sing line)
			for(String line:lines){
				String[] tokens = line.split(" ");
				regInfo.add(strigtoByteArray(tokens[1]));
			}
			
		}catch(Exception e){
			e.printStackTrace();
			System.exit(0);
		}
		return regInfo;
	}

	public ArrayList<String> getRegistriedFiles(){

		ArrayList<String> regInfo = new ArrayList<String>();
		try{
			ArrayList<String> lines = getRegistryFileLines(); // lines
			lines.remove(lines.size()-1); // deleting last line! (sing line)
			for(String line:lines){
				String[] tokens = line.split(" ");
				regInfo.add(tokens[0]);
			}
			
		}catch(Exception e){
			e.printStackTrace();
			System.exit(0);
		}
		return regInfo;
	}
	
	/*
	 * appends the signed_hash param at the end of reg file 
	 * @param signed_hash
	 * @throws Exception
	 */
	public void signResigtryFile(byte[] signed_hash) throws Exception{
		String line ;
		line = "Sign: " + byteArrayToString(signed_hash);
		appendToRegistryFile(line);
	}
	
	
	/*
	 * gets all lines form reg file 
	 * then get the signed hash value 
	 * and returns it as byte[]  
	 * @return
	 * @throws Exception
	 */
	public byte[] getSign() throws Exception{
		ArrayList<String> lines = getRegistryFileLines();
		
		
		String sign = null;
		for(int i=0;i<lines.size();i++){
			if(i==lines.size()-1){
					sign = (lines.get(i).split(" "))[1];
					System.out.println(sign);
			}
		}
		return strigtoByteArray(sign);		
	}
	

	/*
	 * returns everyline of registry files 
	 * @return ArrayList<String>
	 */
	public ArrayList<String> getRegistryFileLines() throws Exception{
		BufferedReader br = new BufferedReader(new FileReader(registry));
		ArrayList<String> lines = new ArrayList<String>();
		String line;
		while(( line = br.readLine())!=null){
			lines.add(line);
		}
		br.close();
		return lines;
	}
	
	/*
	 * returns everylines from registryfile(without the last line) 
	 * as byte[] array;
	 * @return byte[] 
	 * @throws Exception
	 */
	public byte[] getRegistryFileBytesWithoutSign() throws Exception{
		ArrayList<String> lines = getRegistryFileLines();
		
		// converting string arraylist to byte array
		//System.out.println(lines.size());
		String temp = "";
		for(int i=0;i<lines.size();i++){
			if(!(i == lines.size()-1)){
				temp = temp +lines.get(i) + "\n";	
				//System.out.println(lines.get(i));
			}
		}
		return temp.getBytes();
	}
	
	/*
	 * add new file info to the registry file
	 * doesn't care about sign, just appends the to line to the file
	 * @param filePath
	 * @param hash
	 */
	public void addNewDataToReg(String filePath, byte[] hash){
		
		try{
			String line = filePath + " " + byteArrayToString(hash);
			appendToRegistryFile(line);
		}catch(IOException e){
			System.out.println("Error: while appending to registry file");
		}

	}
	
	/*
	 * appends the line to the registry file 
	 * add a new line char to the end of line 
	 * @param line
	 * @throws IOException
	 */
	public void appendToRegistryFile(String line) throws IOException{

		/*
		 * FileOutputStream out = new FileOutputStream(registry, true);
		out.write(data);
		out.close();
		 */
		
		BufferedWriter bw = new BufferedWriter(new FileWriter(registry, true) );
		bw.write(line);
		bw.newLine();
		bw.flush();
		bw.close();
	}
	/*
	 * creates new reg file! 
	 * @return
	 */
	public boolean createNewRegistryFile() {
		boolean result = false;
		

		try {
			if(registry.exists()){
				registry.delete();
			}
			registry.createNewFile();
			result = true;
		} catch (IOException e) {
			System.out.println(e.getMessage()+"\nIs not able to create new registry file: \n" + filePath);
			result = false;
		}

		return result;
	}
	
	
	public String byteArrayToString(byte[] data)
	{	//System.out.println(data.length);
		String str = "";
		for(int i=0;i<data.length;i++){
			str = str + Integer.toString((int)data[i]) +"/";
		}
		return str;
	}
	
	public byte[] strigtoByteArray(String str){
		String[] tokens = str.split("/");
		byte[] data = new byte[tokens.length];
		for(int i=0;i<tokens.length;i++){
			data[i] = (byte)Integer.parseInt(tokens[i]);
			
		}
		return data;
	}
}
