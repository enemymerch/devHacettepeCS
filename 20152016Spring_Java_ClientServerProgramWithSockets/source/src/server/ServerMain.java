package server;

import java.io.*;
import java.net.*;

/**
 * args[0] = PORT
 * 
 * one server can execute with (max)5 client processes at the same time.
 * ServerMain process won't terminate if max_client_process(=5) limit isn't reach.(I could have design it to
 * execute only one client process only and terminate but this way seem more neat).
 * 
 * logFileDirectory :"log.txt"
 * 
 * @author mcany
 *
 */
public class ServerMain {
	public static final int MAX_CLIENT_NUMBER = 5;
	private int PORT ;
	
	public ServerMain(int PORT){
		this.PORT = PORT;
	}
	
	public static void main(String[] args) throws IOException, InterruptedException {
		int PORT;
		try{
			PORT = Integer.parseInt(args[0]);
		}catch(NumberFormatException nfe){
			System.out.println("Invalid Server args , \nPORT is set to 4444.");
			PORT = 4444;
		}
		new ServerMain(PORT).runServer();
	}
	
	/**
	 * 	Runs Server
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public void runServer() throws IOException, InterruptedException{
		//log.txt writer
		PrintWriter  logWriter = new PrintWriter("log.txt","UTF-8");
		
		// Server is up & running
		ServerSocket myServer = new ServerSocket(this.PORT);
		logWriter.println("Server is online.");
		
		int clientNumber=0;//Counter for ClientMain process,system can execute
						   //more than one process at the same time(MAX_CLIENT_NUMBER = 5)
		
		while(clientNumber < MAX_CLIENT_NUMBER){
			// Wait for client to connect !
			Socket mySocket = myServer.accept();
			logWriter.println("Client connected to server.");
			clientNumber++;
			
			BufferedReader SocketReader = new BufferedReader(new InputStreamReader(mySocket.getInputStream()));
			//PrintWriter printWriter = new PrintWriter(mySocket.getOutputStream(), true);
			
			//Listening client for input 
			String buf = SocketReader.readLine();
			int ThreadCount =0;
			String Subsequence = null;
			String SequenceFile = null;
			if(buf != null){// parse input !
				try{
					logWriter.println("Client request.");
					String[] temp = buf.split("\t");
					SequenceFile = temp[1];
					Subsequence = temp[2];
					ThreadCount =  Integer.parseInt(temp[3]);
				}catch(NumberFormatException nfe){
					System.out.println("Wrong Input From Client");
				}
			}

			
			// Creating Threads ! ---> Threads[ThreadCount] is  for residual Search !
			ServerThread[] Threads = new ServerThread[ThreadCount+1];
			for(int i=0;i<Threads.length;i++){
				Threads[i] = new ServerThread(ThreadCount, i+1, SequenceFile, Subsequence, logWriter, mySocket);
				// Set last thread to residual thread
				if(i == Threads.length-1){
					Threads[i].setResidual(true);
				}
			}
			
			// Run threads
			for(int i=0;i<Threads.length;i++){
				Threads[i].start();
			}
			
			// wait for threads to join 
			for(int i=0;i<Threads.length;i++){
				Threads[i].join();
			}
			
			// After threads join , close socket 
			// So, client could terminate
			mySocket.close();
			logWriter.close();
		}
		// close server !
		myServer.close();
	}
}
