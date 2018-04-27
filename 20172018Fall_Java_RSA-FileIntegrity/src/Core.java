import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;



public class Core {

	private InputObtions obtions;
	private LogHandler logHandler;
	private RegistryHandler regHandler;
	private HashHandler hashHandler;
	private RSA myRSA;
	
	public Core(InputObtions obtions) throws Exception{
		
		this.obtions = obtions;
		logHandler = new LogHandler(obtions.getOption("-l"));
		regHandler = new  RegistryHandler(obtions.getOption("-r"));
		hashHandler = new HashHandler(obtions.getOption("-h"));

		if(obtions.getOption("-prik")!=null){
			myRSA = new RSA(obtions.getOption("-pubk"), obtions.getOption("-prik"));
			// start
		}else{
			myRSA = new RSA(obtions.getOption("-pubk"));	
		}
	}

	/*
	 * With this emethod, 
	 * going to do the periodic opearations with given InputObtions 
	 */
	public void doPeriodicOperations() throws Exception{
		
		Calendar cal = Calendar.getInstance();
	    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
	    String timeStamp = sdf.format(cal.getTime());
		
		// (i) first going to read the registry file and dec the sign! and validate the reg file
		byte[] BytesOfRegFileWithoutSignLine = regHandler.getRegistryFileBytesWithoutSign();
		byte[] regHash = hashHandler.getHash(BytesOfRegFileWithoutSignLine);
		
		byte[] sign = regHandler.getSign();
		byte[] decSignedHas = myRSA.dec(sign);

		if(Equals(regHash, decSignedHas)){// validated ? 
			//validated !
			// getting file paths and hash values 
			
			ArrayList<String> filePaths = regHandler.getRegistriedFiles();
			ArrayList<byte[]> hashValuesOfFiles = regHandler.getRegistriedHashValues();
			
			File myDir = new File(obtions.getOption("-p")); // getting the path to watch
			
			// going to create a new reg file
			//regHandler.createNewRegistryFile();
			
			//going to seek the given directory
			if(myDir.isDirectory()){
				for(File tempFile:myDir.listFiles()){ // iterating through the dir !
					
					// path to file
					String filePath = tempFile.getAbsoluteFile().toString();
					
					// getting hashed value of file
					byte[] hash= hashHandler.getHash(filePath);
					
					if(!filePaths.contains(filePath)){ // is file new ?
						
						// file is new
						
						// saving it into reg file
						//regHandler.addNewDataToReg(filePath, hash);
						
						// log the info
						logHandler.log_info(timeStamp+": "+filePath+" created");
					
					}else{// file is not new, but is it changed ?
						// yea , files is changed
					
						// saving the hash value to reg file
						//regHandler.addNewDataToReg(filePath, hash);
						if(!Equals(hashValuesOfFiles.get(filePaths.indexOf(filePath)), hash)){
							// yea file is changed

							// logging the info
							logHandler.log_info(timeStamp+": "+filePath+" altered");
							
						}
						
						/*
						 *  going to remove the saved file path and hash values from the
						 * array lists, then the last standing ones will be the deleted ones!
						 */
						int index = filePaths.indexOf(filePath);
						// removing the changed
						filePaths.remove(index);
						hashValuesOfFiles.remove(index);
					}
					
				}
				
				// iteration trough the directory is ended.
				// now, going to look if there are files that deleted!
				
				
				// checking, you know, ... just in case of something goes wrong
				if(filePaths.size() != hashValuesOfFiles.size()){
					System.out.println("Periodic Operation, something went wrong!");
					System.exit(0);
				}
				// now iterating through deleted files
				for(int i=0;i<filePaths.size();i++){
					//going to log the deleted files
					logHandler.log_info(timeStamp+": "+filePaths.get(i)+" deleted");
				}
				
				// now we can sign the reg file !
				//regHandler.signResigtryFile(decSignedHas);
				
				
			}else{
				System.out.println("-p obtion is not a directory sorry");
				System.exit(0);
			}
		}else{


			// going to log the not valid info
			//logHandler.log_info("Dude, reg file is not validated!");
			logHandler.log_info(timeStamp+": verification failed");
		}
		
	}
	
	/*
	 * With this method, 
	 * going to create register file && log file
	 */
	public void doStartOperations() throws Exception{
		
		// (i) create new registry and log files!
		logHandler.createNewLogFile();
		regHandler.createNewRegistryFile();
		
		// (ii) going to seek the given directory
		File myDir = new File(obtions.getOption("-p"));
		if(myDir.isDirectory()){
			for(File tempFile:myDir.listFiles()){
				String filePath = tempFile.getAbsolutePath();
				// Going to hash Every file 
				byte[] hash = hashHandler.getHash(filePath);
				//System.out.println(hash.length);
				regHandler.addNewDataToReg(filePath, hash);
			}
			// getting hash value of reigstry file so we can sign it !
			byte[] hashValueOfRegFile = hashHandler.getHash(obtions.getOption("-r"));
 			regHandler.signResigtryFile(myRSA.enc(hashValueOfRegFile));
		}else{
			System.out.println("-p obtion is not a directory sorry");
				System.exit(0);
		}
	}

	
	public boolean Equals(byte[] data1, byte[] data2){
		
		boolean result = false;
		//System.out.println(Arrays.toString(data1));
		//System.out.println(Arrays.toString(data2));
		if(data1.length==data2.length){
			result = true;
			for(int i=0;i<data1.length;i++){
				if(data1[i]!=data2[i]){
					result = false;
					break;
				}
			}
		}
		//System.out.println(result);
		return result;
	}
}
