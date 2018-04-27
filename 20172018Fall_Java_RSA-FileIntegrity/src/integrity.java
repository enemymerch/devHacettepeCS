
import java.io.*;

public class integrity {

	private static final String CRON_FILENAME = "cron-file";

	/**
	 * @param args
	 * @throws FileNotFoundException 
	 */
	public static void main(String[] args) throws FileNotFoundException {

		
		if(args.length==1){
			// TODO : STOP
			File f = new File(CRON_FILENAME);
			if(f.exists() && !f.isDirectory()) { 
				//System.out.println("cron file var");
				f.delete();
				BufferedWriter bw = null;
				FileWriter fw = null;
				try {
					fw = new FileWriter("empty.txt");
					bw = new BufferedWriter(fw);
				} catch (IOException e) {
					e.printStackTrace();
				}
				String command = "crontab empty.txt";	
				executeCommandNonReturn(command);
			}

		}else{
			try{
				InputObtions inputHandler;
				if(args[0].equals("start")){
					//:START
					inputHandler = new InputObtions(args);

					File f = new File(CRON_FILENAME);
					if(!f.exists() && !f.isDirectory()) { 
						String pwd = executeCommandReturn("pwd").trim();
						
						createCronFile(inputHandler.getOption("-i"),
									   inputHandler.getOption("-p"),
									   inputHandler.getOption("-r"),
									   inputHandler.getOption("-l"),
									   inputHandler.getOption("-h"),
									   inputHandler.getOption("-pubk"),
									   pwd);

						String command = "crontab "+CRON_FILENAME;	
						System.out.println(executeCommandReturn(command));
					}

					Core myOperationCore = new Core(inputHandler);
					myOperationCore.doStartOperations();
				}else{
					//:PERIODIC
					inputHandler = new InputObtions(args, "periodic");
					Core myOperationCore = new Core(inputHandler);
					myOperationCore.doPeriodicOperations();
				}
			}catch(Exception e) {
				e.printStackTrace();
			}		 
		}
	}

	public static void createCronFile(String i, String p,String r, String l,String h, String pubk, String pwd){
		BufferedWriter bw = null;
		FileWriter fw = null;
		try {
			StringBuilder sb=new StringBuilder("*/"+ i + " * * * * ");  
			//StringBuilder sb=new StringBuilder("* * * * * ");  
			sb.append("java -cp " + pwd);
			sb.append("integrity -p ");
			sb.append(p);
			sb.append(" -r ");
			sb.append(r);
			sb.append(" -l ");
			sb.append(l);
			sb.append(" -h ");
			sb.append(h);
			sb.append(" -k ");
			sb.append(pubk);
			sb.append("\n");
			System.out.println(sb);//prints Hello Java  
			fw = new FileWriter(CRON_FILENAME);
			bw = new BufferedWriter(fw);
			bw.write(sb.toString());
			System.out.println("Cron file Done");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (bw != null)
					bw.close();
				if (fw != null)
					fw.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}
	public static String executeCommandReturn(String command) {
		StringBuffer output = new StringBuffer();
		Process p;
		try {
			p = Runtime.getRuntime().exec(command);
			p.waitFor();
			BufferedReader reader =
                            new BufferedReader(new InputStreamReader(p.getInputStream()));

                        String line = "";
			while ((line = reader.readLine())!= null) {
				output.append(line + "\n");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(command);
		System.out.println("command done");
		return output.toString();

	}
	public static void executeCommandNonReturn(String command) {
		Process p;
		try {
			p = Runtime.getRuntime().exec(command);
			//p.waitFor();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(command);
		System.out.println("command done");
	}
}
