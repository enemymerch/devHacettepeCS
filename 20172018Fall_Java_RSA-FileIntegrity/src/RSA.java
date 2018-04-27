import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import javax.crypto.Cipher;
import java.security.KeyPair;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;


public class RSA {

	private PublicKey publicKey;
	private PrivateKey privateKey;
	private Cipher myRSACipher;
	
	public RSA(String publicKeyFile, String privateKeyFile) throws Exception{

		try{
			setPublicKey(publicKeyFile);
			
			setPrivateKey(privateKeyFile);
			
		}catch(Exception e){
			//System.out.println(" key files...");
			generateKey(privateKeyFile, publicKeyFile);
			publicKey = (PublicKey)(new ObjectInputStream(new FileInputStream(publicKeyFile))).readObject();
			privateKey = (PrivateKey)(new ObjectInputStream(new FileInputStream(privateKeyFile))).readObject();
		}
	    myRSACipher = Cipher.getInstance("RSA");

	}
	public RSA(String publicKeyFile) throws Exception{

		try{
			setPublicKey(publicKeyFile);
			
		
			
		}catch(Exception e){
			//System.out.println("Creating key files...");
			//generateKey("temp.txt", publicKeyFile);
			publicKey = (PublicKey)(new ObjectInputStream(new FileInputStream(publicKeyFile))).readObject();
			//privateKey = (PrivateKey)(new ObjectInputStream(new FileInputStream("temp.txt"))).readObject();
		}
		
		privateKey = null;
	    // init. cipher
	    myRSACipher = Cipher.getInstance("RSA");

	}
	
	public void setPublicKey(String publicKeyFile) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException{
		byte[] keyBytes = Files.readAllBytes(Paths.get(publicKeyFile));
		
	    X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
	    KeyFactory kf = KeyFactory.getInstance("RSA");
	    this.publicKey = kf.generatePublic(spec);
	}
	public void setPrivateKey(String privateKeyFile) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException{
		byte[] keyBytes = Files.readAllBytes(Paths.get(privateKeyFile));

	    PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
	    KeyFactory kf = KeyFactory.getInstance("RSA");
	    this.privateKey = kf.generatePrivate(spec);
	}

	
	public byte[] enc(byte[] data) throws Exception{
		
		myRSACipher.init(Cipher.ENCRYPT_MODE, privateKey);
		byte[] temp = myRSACipher.doFinal(data);
		return temp;
	}
	
	public byte[] dec(byte[] data) throws Exception{
		myRSACipher.init(Cipher.DECRYPT_MODE, publicKey);
		byte[] temp = null;
		temp = myRSACipher.doFinal(data);
			 
		return temp;
	}
	
	
	private void generateKey(String PRIVATE_KEY_FILE, String PUBLIC_KEY_FILE) {
	    try {
	      final KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
	      keyGen.initialize(2048);
	      final KeyPair key = keyGen.generateKeyPair();

	      File privateKeyFile = new File(PRIVATE_KEY_FILE);
	      File publicKeyFile = new File(PUBLIC_KEY_FILE);

	      // Create files to store public and private key
	      if (privateKeyFile.getParentFile() != null) {
	        privateKeyFile.getParentFile().mkdirs();
	      }
	      privateKeyFile.createNewFile();

	      if (publicKeyFile.getParentFile() != null) {
	        publicKeyFile.getParentFile().mkdirs();
	      }
	      publicKeyFile.createNewFile();

	      // Saving the Public key in a file
	      ObjectOutputStream publicKeyOS = new ObjectOutputStream(
	          new FileOutputStream(publicKeyFile));
	      publicKeyOS.writeObject(key.getPublic());
	      publicKeyOS.close();

	      // Saving the Private key in a file
	      ObjectOutputStream privateKeyOS = new ObjectOutputStream(
	          new FileOutputStream(privateKeyFile));
	      privateKeyOS.writeObject(key.getPrivate());
	      privateKeyOS.close();
	    } catch (Exception e) {
	      e.printStackTrace();
	    }

	  }
	
}
