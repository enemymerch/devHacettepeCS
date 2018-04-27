
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class LogHandler {

	private String filePath;
	private File log = null;
	
	public LogHandler (String filePath) {
		this.filePath = filePath;
		log = new File(filePath);
	}

    public void log_info(String msg) throws IOException {
        msg += "\n";
        Files.write(Paths.get(filePath), msg.getBytes(), StandardOpenOption.APPEND);
    }

    
	public boolean createNewLogFile() {
		boolean result = false;
		
		if(log.exists()){
			//System.out.println("File already exits!: "  + filePath);
		}else{
			try {
				log.createNewFile();
				result = true;
			} catch (IOException e) {
				System.out.println(e.getMessage()+"\nIs not able to create new log file: \n" + filePath);
				result = false;
			}
		}
		return result;
	}
	
}
