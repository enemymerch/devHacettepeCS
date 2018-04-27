package server;

import java.io.*;
import java.net.*;


/**
 * @author mcany
 *
 * 	Two Types of Thread-->Determined by variable "boolean isResidual"
 *  if isResidual is false, thread is normal(thread will search of its own part).
 *  if isResidual is true, the thread is residual(thread will search for string parts between threads) "+1 Thread"
 *  
 *  Every normal thread reads all of the Sequence file 
 *  and takes substring of it 
 *  This substring which will be unique for every thread(not including Residual thread).
 *  Because every thread gets its own substring 
 *  with their own beginIndex end endIndex !
 *  
 *  beginIndex & endIndex of thread is calculated
 *  with threadID , threadID contains number of that thread.
 */
public class ServerThread extends Thread{
	int ThreadCount;
	Socket MessageSocket;
	int ThreadID;
	String Subsequence;
	String SequenceFile;
	boolean isResidual = false; // Default is false 
	PrintWriter logWriter;
	
	public ServerThread(int ThreadCount, int ThreadID,String SequenceFile, String Subsequence,PrintWriter logWriter, Socket MessageSocket) throws IOException{
		this.ThreadCount = ThreadCount;
		this.ThreadID = ThreadID;
		this.SequenceFile = SequenceFile;
		this.Subsequence = Subsequence;
		this.logWriter = logWriter;
		logWriter.write("Thread"+ThreadID+" is created.\n");
		this.MessageSocket = MessageSocket;
	}
	
	public void run(){
		// Read SequenceFile
		TextFileReader reader = new TextFileReader(SequenceFile);
		String allContext = reader.readFile();// SequenceFile in allContext
		
		// Check thread type
		if(this.isResidual){
			try {
				SearchResidual(allContext);// Residual search on SequenceFile
			} catch (IOException e) {
				System.out.println("\n Oops , SearchResidual is couldn't done."
									+"\nSomething might be wrong with connection between threads and client."
									+"\nYou might wanna check the port numbers.\n"+e.getStackTrace());
			}
		}else{
			// Find boundaries of thread
			int beginIndex = getBeginIndex(allContext.length());
			int endIndex = getEndIndex(allContext.length());
		
			// Get subContext of thread for searching  
			String subContext = allContext.substring(beginIndex, endIndex);
			try {
				Search(subContext, beginIndex);
			} catch (IOException e) {
				System.out.println("\n Oops , Search is couldn't done."
						+"\nSomething might be wrong with connection between threads and client."
						+"\nYou might wanna check the port numbers.\n"+e.getStackTrace());
			}
		}
		logWriter.println("Thread"+this.ThreadID+" is terminated.");
	}	
	
	/**
	 * Finds endIndex of this thread according to threadID  in SequenceFile.
	 * @param ContextLength	Length of SequenceFile.
	 * @return	endIndex of thread
	 */
	private int getEndIndex(int ContextLength ){
		int charNumbeperThread = ContextLength/this.ThreadCount;
		int endIndex = charNumbeperThread*(ThreadID-1) + charNumbeperThread;
		
		if(ContextLength%this.ThreadCount != 0 && ThreadID == this.ThreadCount){
			endIndex = endIndex+ ContextLength%this.ThreadCount;
		}
		
		return endIndex;
	}
	/**
	 * Finds beginIndex of this thread according to threadID  in SequenceFile.
	 * @param ContextLength   Length of SequenceFile.
	 * @return beginIndex of thread
	 */
	private int getBeginIndex(int ContextLength){
		int charNumbeperThread = ContextLength/this.ThreadCount;
		int beginIndex = charNumbeperThread*(ThreadID-1);
		
		return beginIndex;
	}
	
	/**
	 * @param isResidual 
	 */
	public void setResidual(boolean isResidual){
		this.isResidual = isResidual;
	}
	/**
	 * 
	 * @param Context		String part which Subsequence will be searched in
	 * @throws IOException	I/O
	 */
	private void SearchResidual(String Context) throws IOException{
		int charNumberperThread = Context.length()/this.ThreadCount;
		int tempIndex = 0;
		// Searching between threads
		for(int i=1;i<this.ThreadCount;i++){
			tempIndex = (i*charNumberperThread);
			// 
			for(int j=tempIndex-3;j<tempIndex;j++){
				if(Context.substring(j,j+4).equals(this.Subsequence)){// Subsequence found.
					// Write log 
					this.logWriter.println("Sequence found.--->ThreadNumber:" +this.ThreadID);
					// Send Message to Client 
					PrintWriter SocketWriter = new PrintWriter(this.MessageSocket.getOutputStream(), true);
					SocketWriter.println(this.Subsequence+"\t"+j+"\t"+this.ThreadID);
				}
			}
		}
	}
	
	/**
	 * 
	 * @param Context		String part which Subsequence will be searched in
	 * @param beginIndex 	Context first elements index at SequenceFile
	 * 					 	Very first index which this thread will start to searching
	 * @throws IOException 	I/O
	 */
	private void Search(String Context,int beginIndex) throws IOException{
		
		int length = Context.length();
		String temp;
		for(int i=0;i<length-this.Subsequence.length();i++){
			temp = Context.substring(i, i+4);
			if(this.Subsequence.equals(temp)){// Subsequence found.
				// Write log
				this.logWriter.println("Sequence found.--->ThreadNumber:" +this.ThreadID);
				// Send Message to Client 
				int index = beginIndex+i+1;
				PrintWriter SocketWriter = new PrintWriter(this.MessageSocket.getOutputStream(), true);
				SocketWriter.println(this.Subsequence+"\t"+index+"\t"+this.ThreadID);
			}
		}
	}
}
