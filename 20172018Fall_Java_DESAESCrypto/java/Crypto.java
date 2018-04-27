

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class Crypto {
	
	/*
	 * fields ! 
	 */

	// Cipher
	Cipher myCipher;
	
	// inputFile , will be initialized when constructor executed
	private File inputFile ;
	
	// outputFile , will be initialized when constructor executed
	private File outputFile;
	
	// keyFile , will be initialized when constructor executed
	private File keyFile;
	
	// alg for cyprto
	private String alg;
	
	// mode for DES algorithm
	private String mode;
	
	// threadCount, default threadCount:(1)
	private int threadCount = -1;
	
	//SecretKeySpec
	private SecretKeySpec mySecretKeySpec;
	//IvParameterSpec 
	private IvParameterSpec initVectorSpec;
	
	public Crypto(String inputFilePath, String outputFilePath, String keyFilePath, String alg, String mode, int threadCount){

		/*
		 * initializing file fields!
		 */
		

		
		try{
			// (i)inputFile
			this.inputFile = new File(inputFilePath);
			if(!this.inputFile.exists()){
				throw new NoSuchFileException("input File is not found at given directory!");
			}
			
			// (ii) outputFile --it's okay if no such file exits
			this.outputFile = new File(outputFilePath);
			
			// (iii) keyFile
			this.keyFile = new File(keyFilePath);
			if(!this.keyFile.exists()){
				throw new NoSuchFileException("key File is not found at given directory!");
			}

			
			//alg
			this.alg = alg;
			
			// mode
			this.mode = mode;
			if (!(mode.equals("CTR") || mode.equals("CBC") || mode.equals("OFB"))){
				throw new Exception("No such mode exits for DES Algorithm.");
			}else if(mode.equals("CTR")){
				this.threadCount = threadCount;
			}
			
			
			
			
			// initializing the Cipher and the keys!
			this.myCipher = Cipher.getInstance(this.alg+ "/"+this.mode+"/PKCS5Padding");
			parseKeys();
			

			
		}catch (NoSuchFileException e) {
			System.out.println(e.toString());
			System.out.println("Re-run the program with a new directory for files!");
			System.exit(0);
		}catch (Exception e) {
			System.out.println(e.toString());
			System.out.println("Re-run the program with a correct mode!");
			System.exit(0);
		}
	}

	public void encrypt(){

		
		// : init. Cipher
		try {
			this.myCipher.init(Cipher.ENCRYPT_MODE, this.mySecretKeySpec, this.initVectorSpec);
		} catch (Exception e) {
			System.out.println("Somethings wrong with .init(...) -- enc");
			System.exit(0);
		}
		
		
		try{
			if(this.mode.equals("CTR")){
				multiCrpytion();
				return;
			}
		}catch(Exception e){
			System.out.println("Somethings are wrong with multi-thread CTR alg");
			System.exit(0);
		}	
		
		try {
			CipherInputStream cis = new CipherInputStream(new FileInputStream(this.inputFile), this.myCipher);
			singleCrpytion(cis, new FileOutputStream(this.outputFile));
		} catch (IOException e) {
			System.out.println("Somethings are wrong with CipherStreams");
			System.exit(0);
		}
		
		return;
	}

	public void decrypt(){

		
		// : init. Cipher
		try {
			this.myCipher.init(Cipher.DECRYPT_MODE, this.mySecretKeySpec, this.initVectorSpec);
		} catch (Exception e) {
			System.out.println("Somethings are wrong with .init(...) -- decr");
			System.exit(0);
		}
		
		try{
			if(this.mode.equals("CTR")){
				multiCrpytion();
				return;
			}
		}catch(Exception e){
			System.out.println("Somethings are wrong with multi-thread CTR alg");
			System.exit(0);
		}	
		
		
		try {
			CipherOutputStream cos = new CipherOutputStream(new FileOutputStream(this.outputFile), this.myCipher);
			singleCrpytion(new FileInputStream(this.inputFile), cos);
		} catch (IOException e) {
			System.out.println("Somethings are wrong with CipherStreams");
			System.exit(0);
		}
		

		return;
	}

	
	public void singleCrpytion(InputStream is, OutputStream os) throws IOException{
		
		
		byte[] bytes = new byte[64];
		int numBytes;
		while ((numBytes = is.read(bytes)) != -1) {
			os.write(bytes, 0, numBytes);
		}
		os.flush();
		os.close();
		is.close();
	}


	public void multiCrpytion() throws IOException{



		// before running the threads , have to extract the data
		Path fileLocation;
		byte[] data;
		fileLocation = Paths.get(this.inputFile.getAbsolutePath());
		data = Files.readAllBytes(fileLocation);
		
		int dataLength = data.length;
		
		int lastThreadsBlockSize = dataLength % (this.threadCount-1);
		int blockSize = dataLength / (this.threadCount-1);

		ArrayList<CyrptoThread> myThreads = new ArrayList<CyrptoThread>();
		
		for(int i=0;i<this.threadCount-1;i++){
			CyrptoThread temp = new CyrptoThread(this.myCipher);
			myThreads.add(temp);
		}
		
		for(int i=0; i<this.threadCount-1; i++){
			
			byte[] iThreadData ;

				iThreadData = new byte[blockSize];
				for(int j=0;j<blockSize;j++){
					iThreadData[j] = data[(blockSize*i)+j];
				}
			CyrptoThread temp = myThreads.get(i);
			temp.setBlockSize(blockSize);
			temp.setInputData(iThreadData);
			temp.run();
		}
		
		CyrptoThread lastThread = new CyrptoThread(this.myCipher);
		// running last thread
		if(lastThreadsBlockSize!=0){
			byte[] iThreadData = new byte[lastThreadsBlockSize];
			for(int j=0;j<lastThreadsBlockSize;j++){
				iThreadData[j] = data[(blockSize*(this.threadCount-1))+j];
			}
			lastThread.setBlockSize(blockSize);
			lastThread.setInputData(iThreadData);
			lastThread.run();
		}
		FileOutputStream out = new FileOutputStream(this.outputFile);
		try {
			for(int i = 0; i< this.threadCount-1; i++){
					CyrptoThread temp = myThreads.get(i);
					temp.join();
					out.write(temp.getOutputData());
			}
			if(lastThreadsBlockSize!=0){
				out.write(lastThread.getOutputData());
			}
			out.close();
		} catch (InterruptedException e) {
			System.out.println("Somethings wrong with the threads!");
			out.close();
			System.exit(0);
		}
		

		return;
	}


	public Cipher getmyCipher() {
		return myCipher;
	}

	public void setmyCipher(Cipher myCipher) {
		this.myCipher = myCipher;
	}

	public File getInputFile() {
		return inputFile;
	}

	public void setInputFile(File inputFile) {
		this.inputFile = inputFile;
	}

	public File getOutputFile() {
		return outputFile;
	}

	public void setOutputFile(File outputFile) {
		this.outputFile = outputFile;
	}

	public File getKeyFile() {
		return keyFile;
	}

	public void setKeyFile(File keyFile) {
		this.keyFile = keyFile;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public int getThreadCount() {
		return threadCount;
	}

	public void setThreadCount(int threadCount) {
		this.threadCount = threadCount;
	}

	public SecretKeySpec getmySecretKeySpec() {
		return mySecretKeySpec;
	}

	public void setmySecretKeySpec(SecretKeySpec mySecretKeySpec) {
		this.mySecretKeySpec = mySecretKeySpec;
	}

	public IvParameterSpec getInitVectorSpec() {
		return initVectorSpec;
	}

	public void setInitVectorSpec(IvParameterSpec initVectorSpec) {
		this.initVectorSpec = initVectorSpec;
	}
	
	public void parseKeys() throws IOException{
		
		BufferedReader keyFileReader = new BufferedReader(new FileReader(this.keyFile));
		String[] params = keyFileReader.readLine().split(" - ");
		keyFileReader.close();
		
		if (this.alg.equals("AES")){
			if(params[0].length() == 16 && params[1].length() == 16){
				this.initVectorSpec = new IvParameterSpec(params[0].getBytes());
				this.mySecretKeySpec = new SecretKeySpec(params[1].getBytes("UTF8"), "AES");
			}else{
				throw new IOException("Key lengths are not appropriate for AES");
			}
		}else{
			if(params[0].length() == 8 && params[1].length() == 8){
				this.initVectorSpec = new IvParameterSpec(params[0].getBytes());
				this.mySecretKeySpec = new SecretKeySpec(params[1].getBytes("UTF8"), "DES");
			}else{
				throw new IOException("Key lengths are not appropriate for DES");
			}
		}
	}

}


