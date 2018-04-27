package client;

import java.io.*;
import java.net.*;
import java.util.*;

/**
 * args[0] == PORT,
 * args[1] == SequenceFile,
 * args[2] == Subsequence,
 * args[3] == ThreadCount.
 * 
 * outputFileDirectory = "out.txt"
 * 
 * Client request format == <PORT>\t<SequenceFile>\t<Subsequence>\t<ThreadCount>
 * @author mcany
 *
 */
public class ClientMain {
	public static int PORT;
	public static int ThreadCount;
	public static String SequenceFile;
	public static String Subsequence;
	
	
	public static void main(String[] args) throws UnknownHostException, IOException {
		//Connect to server
		try{// input args's
			PORT = Integer.parseInt(args[0]);
			ThreadCount = Integer.parseInt(args[3]);
			SequenceFile = args[1];
			Subsequence = args[2];
		}catch(NumberFormatException nfe){// if PORT or ThreadCount is wrong , throws NumberFormatException 
			System.out.println(nfe.getStackTrace()+"Invalid client args, \nPORT is set to 4444 &"
					+ "\nThreadCount is set to 2"
					+ "\nSequenceFile is set to sequence.txt"
					+ "\nSubsequence is set to aaaa");
			PORT = 4444;
			ThreadCount = 2;
			SequenceFile = "sequence.txt";
			Subsequence = "aaaa";
		}
		
		//Connect Server
		Socket mySocket = new Socket("localhost", PORT);
		
		// Created writer&reader for mySocket
		PrintWriter SocketWriter = new PrintWriter(mySocket.getOutputStream(), true);
		BufferedReader SocketReader = new BufferedReader(new InputStreamReader(mySocket.getInputStream()));
		
		//Send request to server  -> PORT SequenceFile Subsequence ThreadCount
		SocketWriter.println(PORT+"\t"+SequenceFile+"\t"+Subsequence+"\t"+ThreadCount);
		
		//Listen PORT
		String ThreadMessage ;
		// Thread's Message will be decoded and stored in.
		ArrayList<String> OutputList = new ArrayList<String>();
		int i=1;
		while((ThreadMessage = SocketReader.readLine())!=null || mySocket.isClosed()){
			//Decode ThreadMessage
			String temp = "";
			String[] ThreadMessageTokens = ThreadMessage.split("\t");
			temp = ThreadMessageTokens[0];
			temp =  temp + ":"  + ThreadMessageTokens[1] + ":";
			temp = temp + "Thread" + ThreadMessageTokens[2] + ":" + Integer.toString(i);
			// Save Message
			OutputList.add(temp);
			i++;
		}
		// Close mySocket
		mySocket.close();
		
		// Sort 
		Collections.sort(OutputList,new ClientOutputCompare());
		PrintWriter OutputWriter = new PrintWriter("out.txt","UTF-8");
		for(i=0;i<OutputList.size();i++){
			OutputWriter.println(OutputList.get(i));
		}
		OutputWriter.close();
	}
}
