
import javax.crypto.Cipher;

public class CyrptoThread extends Thread{

	private Cipher desCipher;
	private byte[] inputData;
	private byte[]	outputData;
	private int blockSize;
	
	public CyrptoThread(Cipher desCipher){
		this.desCipher = desCipher;

	}

	
	@Override
	public void run() {
		setOutputData(this.desCipher.update(this.inputData));
	}


	public byte[] getInputData() {
		return inputData;
	}


	public void setInputData(byte[] inputData) {
		this.inputData = inputData;
	}


	public byte[] getOutputData() {
		return outputData;
	}


	public void setOutputData(byte[] outputData) {
		this.outputData = outputData;
	}


	public int getBlockSize() {
		return blockSize;
	}


	public void setBlockSize(int blockSize) {
		this.blockSize = blockSize;
	}


}
