import java.util.HashMap;


public class InputObtions {

	HashMap<String,String> obtions;
	
	public InputObtions(String[] args){
		obtions = new HashMap<String,String>();
		
		for(int i=0;i<args.length;i++){
			if(args[i].equals("-k")){// -k key
				addOption("-pubk", args[i+2]);
				addOption("-prik", args[i+1]);
			}else if(args[i].equals("-p") || args[i].equals("-r") || args[i].equals("-l") || args[i].equals("-h") || args[i].equals("-i")){ 
				// other choices
				addOption(args[i], args[i+1]);
			}
		}
	}
	
	public InputObtions(String[] args, String str){
		obtions = new HashMap<String,String>();
		
		for(int i=0;i<args.length;i++){
			if(args[i].equals("-k")){// -k key
				addOption("-pubk", args[i+1]);
			}else if(args[i].equals("-p") || args[i].equals("-r") || args[i].equals("-l") || args[i].equals("-h") || args[i].equals("-i")){ 
				// other choices
				addOption(args[i], args[i+1]);
			}
		}
	}
	
	
	
	public void addOption(String paramter, String value){
		obtions.put(paramter, value);
	}
	public String getOption(String parameter){
		return obtions.get(parameter);
	}
}
