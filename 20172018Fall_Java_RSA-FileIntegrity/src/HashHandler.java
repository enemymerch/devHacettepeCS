import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class HashHandler {

	private MessageDigest md = null;
	
	public HashHandler(String param){
		try {
			md = MessageDigest.getInstance(param);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}

	public byte[] getHash(byte[] data){
		byte[] hash;
		//getting hash value
		md.reset();
		md.update(data);
		hash = md.digest();
		
		return hash;
	}
	
	/*
	 * reads file the into byte buffer,
	 * calculates the hash value 
	 * @return hashValue of a file of Given file path
	 */
	public byte[] getHash(String filePath){

		// init. vars.
		byte[] data = null, hash = null;
		
		// File object
		File myFile = new File(filePath);
		
		
		if(myFile.exists()){// does file exits
			try {
				// Getting all data as byte
				data = Files.readAllBytes(Paths.get(myFile.getAbsolutePath()));

				
				//getting hash value
				md.reset();
				md.update(data);
				hash = md.digest();
				
				
			} catch (Exception e) {
				e.printStackTrace();
				
			}
			
		}
			
		
		return hash;
	}
}
