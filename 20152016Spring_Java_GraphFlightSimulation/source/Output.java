import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Output {
	private String OutputPath;
	
	public Output(String outputPath){
		OutputPath = outputPath ;
	}
	
	public void WriteToOutputFile(Command[] Commands) throws IOException{
		
		try{
			
			File file = new File(OutputPath);
		
			if (file.exists()) {
				file.delete();
			}
		
			FileWriter filewWriter = new FileWriter(file.getAbsolutePath());
			BufferedWriter bufferedWriter = new BufferedWriter(filewWriter);
			
			for(int i=0;i<Commands.length;i++){
				bufferedWriter.append(Commands[i].getResult());
			}
			bufferedWriter.close();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
}
