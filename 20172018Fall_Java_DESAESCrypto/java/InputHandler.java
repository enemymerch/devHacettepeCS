
import java.io.File;
import java.io.BufferedReader;

import java.io.FileReader;
import java.util.ArrayList;

public class InputHandler {
    private static InputHandler instance = null;
	private Logger logger = Logger.getInstance();

    private InputHandler() {}

    public static InputHandler getInstance() {
        if(instance == null) { instance = new InputHandler();}
        return instance;
    }

    public boolean isFileAvaibleForOperation(String op, String alg, String mode, String file){
    	
    	boolean state = false;
    	try {
    		if(op.equals("-e")){
    			state =  true;
    		}else{
        		ArrayList<String> logs = new ArrayList<String>();
        		BufferedReader br = new BufferedReader(new FileReader("run.log"));
        		String line;
        		while((line = br.readLine()) !=null){
        			logs.add(line);
        		}
        		br.close();
        		String tempMode="", tempAlg="";
        		for (String log : logs) {
        			String[] tokens = log.split(" ");
        			if(tokens[2].equals("enc") && tokens[1].equals(file)){
        				tempMode = tokens[4];
        				tempAlg = tokens[3];
        			}
				}
        		
        		if(tempAlg.equals(alg) && tempMode.equals(mode)){
        			state = true;
        		}
        		
    		}
    		
		} catch (Exception e) {
			System.exit(0);
		}
    	
    	
    	
    	return state;
    }
    public void getInput(String[] args) throws Exception {



        if (args.length >= 8 && args.length <=10){ // If the input is valid

            ArrayList<String> cmdList = new ArrayList<String>();

            // parsing the args
            for (int i = 0; i < args.length ; i++){
            	cmdList.add(args[i]);
            }
            
        	int threadCount;
        	String inputFile, outputFile, keyFile, alg, mode;
        	
            if (!cmdList.contains("CTR") && cmdList.size()==8){ // Single operation !

            	inputFile = cmdList.get(2);
		if(new File(inputFile).getParentFile()==null){
			
			outputFile = cmdList.get(4);		
		}else{
			outputFile = new File(inputFile).getParentFile().getAbsolutePath() + "/" + cmdList.get(4);		
		}
            	
            	alg = cmdList.get(5);
            	mode = cmdList.get(6);
            	keyFile = cmdList.get(7);
            	threadCount = 1;

            }else if(cmdList.contains("CTR") && cmdList.size()==10) { // Multi Thread Part
            	
            	threadCount = Integer.parseInt(cmdList.get(2));
            	inputFile = cmdList.get(4);
		if(new File(inputFile).getParentFile()==null){
			
			outputFile = cmdList.get(6);		
		}else{
			outputFile = new File(inputFile).getParentFile().getAbsolutePath() + "/" + cmdList.get(6);		
		}

            	alg = cmdList.get(7);
            	mode = cmdList.get(8);
            	keyFile = cmdList.get(9);
            	
            }else{
                throw new Exception("Wrong input format");
            }
            
            
            if(!isFileAvaibleForOperation(cmdList.get(0), alg, mode, inputFile)){
            	System.out.println("File has been encoded with different alg or mode \nRe-run the program with different parameters.\nSource: run.log !");
            	System.exit(0);
            }
            
        	long startTime = System.currentTimeMillis();
            
        	if (cmdList.get(0).equals("-e")){// if -e enc
                Encryptor.encrypt(inputFile, outputFile, alg, mode, keyFile, threadCount);
            }else if (cmdList.get(0).equals("-d")){// if -d denc
                Decryptor.decrypt(inputFile, outputFile, alg, mode, keyFile, threadCount);
            }else{ // no such operation
                throw new Exception("Check your operation -e / -d");
            }
            
        	
            String op= "enc";
            if(cmdList.get(0).equals("-d")){ op = "dec" ;}
        	
            long stopTime = System.currentTimeMillis();
            long elapsedTime = stopTime - startTime;

            float seconds = elapsedTime / 1000f;
            String log;
            log = String.format("%s " +
                    "%s " +
                    "%s " +
                    "%s " +
                    "%s " +
                    "%f ",
                    inputFile,
                    outputFile,
                    op,
                    alg,
                    mode,
                    seconds);
            Logger.log_info(log);
        }else{
            // Invalid input
            throw new Exception("Missing arguments");
        }
    }

	public Logger getLogger() {
		return logger;
	}

	public void setLogger(Logger logger) {
		this.logger = logger;
	}
}
