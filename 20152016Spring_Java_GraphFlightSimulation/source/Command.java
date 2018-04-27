import java.util.List;
import java.util.Stack;

public class Command {
	private String CommandName;
	private String CommandDeptCity;
	private String CommandArrvlCity;
	private String StartDate;
	/*
	 * Additional info for :
	 * -List Cheaper ---> <max_price>
	 * -list Quicker ---> <latest_date_time>
	 *	-List Excluding ---><company>
	 */
	private String AdditionalInfo;
	
	
	/*
	 * This string will be written in to output.txt later !
	 * in constructor it will get null !
	 * after searching flights  ,result will be 
	 * saved in this String variable to write file
	 */
	private String Result;
	
	public Command(String Info){
		
		Result = null;
		String[] Tokens = Info.split("\\t");
		String[] tempTokens = Tokens[1].split("->");
		
		
		CommandName = Tokens[0];
		CommandDeptCity = tempTokens[0];
		CommandArrvlCity = tempTokens[1];
		StartDate = Tokens[2];
		AdditionalInfo = "";
		if(Tokens.length>3){
			AdditionalInfo = Tokens[3];
		}
	}
	public long getPathsDurationinMin(Stack<Flight> FlightPath){

		String temp1 = FlightPath.get(0).getDate();
		String temp2 = FlightPath.peek().getDate();
		
		String[] firstDate = temp1.split("/");
		String[] lastDate = temp2.split("/");
		

		long month1 = Integer.parseInt(firstDate[1]);
		long month2 = Integer.parseInt(lastDate[1]);

		
		long day1 = Integer.parseInt(firstDate[0]);
		long day2 = Integer.parseInt(lastDate[0]);

		
		long totalMin = (month2-month1)*30*24*60;
		totalMin = totalMin + (day2-day1)*24*60;


		return totalMin + FlightPath.peek().getDepartureDateAsMin() - FlightPath.get(0).getDepartureDateAsMin()+
				FlightPath.peek().getDurationAsSecond();
	}
	
	public int getPathsPrice(Stack<Flight> FlightStack){
		int sum =0;
		for(int i=0;i<FlightStack.size();i++){
			sum += FlightStack.get(i).getPrice();
		}
		
		return sum;
	}
	
	public void listAll(List<Stack<Flight>> ListOfPaths){
		Result  = "Command : "+this.getCommandName()+"\t"+this.getCommandDeptCity()+"->"+
					this.getCommandArrvlCity()+this.getStartDate()+"\t"+this.getAdditionalInfo()+"\n";
		for(int i=0;i<ListOfPaths.size();i++){
			for(int j=0;j<ListOfPaths.get(i).size();j++){
				if(j==0){
					Result = Result +ListOfPaths.get(i).get(j).getFlightID()+"\t"+
							ListOfPaths.get(i).get(j).getDeparture()+"->"+ListOfPaths.get(i).get(j).getArrival();
				}else {
					Result = Result +"||"+ListOfPaths.get(i).get(j).getFlightID()+"\t"+
							ListOfPaths.get(i).get(j).getDeparture()+"->"+ListOfPaths.get(i).get(j).getArrival();
				}
				
			}
			
			long totalMin = this.getPathsDurationinMin(ListOfPaths.get(i));
			long hours = totalMin/60;
			long mins = totalMin-(hours*60);
			
			String m = Long.toString(mins);;
			String h = Long.toString(hours);
			if(mins<10){
				m = "0"+m;
			}
			if(hours<10){
				h = "0"+h;
			}
			int price = this.getPathsPrice(ListOfPaths.get(i));
			
			Result = Result +"\t" +h+":"+m+"/"+Integer.toString(price)+"\n";
		}
		
		Result = Result +"\n\n";
	}
	public void listProper(List<Stack<Flight>> ListOfPaths){
		Result  = "Command : "+this.getCommandName()+"\t"+this.getCommandDeptCity()+"->"+
				this.getCommandArrvlCity()+this.getStartDate()+"\t"+this.getAdditionalInfo()+"\n";
		int k= 0;
		while(k<ListOfPaths.size()){
			int i =0;
			int temp1 = ListOfPaths.size();
			while(i<ListOfPaths.size()){
				int temp = this.whichOneisProper(ListOfPaths.get(k),ListOfPaths.get(i));
				if(temp !=0){
					if(temp < 0){
						ListOfPaths.remove(i);
						// fPlan deleted from ListOfPaths
						// Stop inner loop and go top 
						break;
					}else{
						ListOfPaths.remove(k);
						// fPlan deleted from ListOfPaths
						// Stop inner loop and go top 
						break;
					}
				}
				i++;
			}
			int temp2 = ListOfPaths.size();
			if(temp1>temp2){
				k=0;
			}else{
				k++;
			}
		}
		
		
		for(int i=0;i<ListOfPaths.size();i++){
			for(int j=0;j<ListOfPaths.get(i).size();j++){
				if(j==0){
					Result = Result +ListOfPaths.get(i).get(j).getFlightID()+"\t"+
							ListOfPaths.get(i).get(j).getDeparture()+"->"+ListOfPaths.get(i).get(j).getArrival();
				}else {
					Result = Result +"||"+ListOfPaths.get(i).get(j).getFlightID()+"\t"+
							ListOfPaths.get(i).get(j).getDeparture()+"->"+ListOfPaths.get(i).get(j).getArrival();
				}
				
			}
			
			long totalMin = this.getPathsDurationinMin(ListOfPaths.get(i));
			long hours = totalMin/60;
			long mins = totalMin-(hours*60);
			
			String m = Long.toString(mins);;
			String h = Long.toString(hours);
			if(mins<10){
				m = "0"+m;
			}
			if(hours<10){
				h = "0"+h;
			}
			int price = this.getPathsPrice(ListOfPaths.get(i));
			
			Result = Result +"\t" +h+":"+m+"/"+Integer.toString(price)+"\n";
		}
		
		Result = Result +"\n\n";
	}
	
	
	public void setProper(List<Stack<Flight>> ListOfPaths){
		int k= 0;
		while(k<ListOfPaths.size()){
			int i =0;
			int temp1 = ListOfPaths.size();
			while(i<ListOfPaths.size()){
				int temp = this.whichOneisProper(ListOfPaths.get(k),ListOfPaths.get(i));
				if(temp !=0){
					if(temp < 0){
						ListOfPaths.remove(i);
						// fPlan deleted from ListOfPaths
						// Stop inner loop and go top 
						break;
					}else{
						ListOfPaths.remove(k);
						// fPlan deleted from ListOfPaths
						// Stop inner loop and go top 
						break;
					}
				}
				i++;
			}
			int temp2 = ListOfPaths.size();
			if(temp1>temp2){
				k=0;
			}else{
				k++;
			}
		}
	}
	public void listCheapest(List<Stack<Flight>> ListOfPaths){
		Result  = "Command : "+this.getCommandName()+"\t"+this.getCommandDeptCity()+"->"+
				this.getCommandArrvlCity()+this.getStartDate()+"\t"+this.getAdditionalInfo()+"\n";
		
		
		int	Price = this.getPathsPrice(ListOfPaths.get(0));
		for(int i=0;i<ListOfPaths.size()-1;i++){
			if(Price > this.getPathsPrice(ListOfPaths.get(i)) ){
				Price = this.getPathsPrice(ListOfPaths.get(i));
			}
		}
		int k = 0;
		while(k<ListOfPaths.size()){
			if(Price != this.getPathsPrice(ListOfPaths.get(k))){
					ListOfPaths.remove(k);
					// fPlan deleted from ListOfPaths
					//  go top of loop
					k = 0;
			}else{
				k++;
			}
		}
		
		for(int i=0;i<ListOfPaths.size();i++){
			for(int j=0;j<ListOfPaths.get(i).size();j++){
				if(j==0){
					Result = Result +ListOfPaths.get(i).get(j).getFlightID()+"\t"+
							ListOfPaths.get(i).get(j).getDeparture()+"->"+ListOfPaths.get(i).get(j).getArrival();
				}else {
					Result = Result +"||"+ListOfPaths.get(i).get(j).getFlightID()+"\t"+
							ListOfPaths.get(i).get(j).getDeparture()+"->"+ListOfPaths.get(i).get(j).getArrival();
				}
				
			}
			
			long totalMin = this.getPathsDurationinMin(ListOfPaths.get(i));
			long hours = totalMin/60;
			long mins = totalMin-(hours*60);
			
			String m = Long.toString(mins);;
			String h = Long.toString(hours);
			if(mins<10){
				m = "0"+m;
			}
			if(hours<10){
				h = "0"+h;
			}
			int price = this.getPathsPrice(ListOfPaths.get(i));
			
			Result = Result +"\t" +h+":"+m+"/"+Integer.toString(price)+"\n";
		}
		
		Result = Result +"\n\n";
		
	}
	public void listQuickest(List<Stack<Flight>> ListOfPaths){
		Result  = "Command :\t"+this.getCommandName()+"\t"+this.getCommandDeptCity()+"->"+
				this.getCommandArrvlCity()+this.getStartDate()+"\t"+this.getAdditionalInfo()+"\n";
		

		long dur = this.getPathsDurationinMin(ListOfPaths.get(0));
		for(int i=0;i<ListOfPaths.size()-1;i++){
			if(dur > this.getPathsDurationinMin(ListOfPaths.get(i)) ){
				dur = this.getPathsDurationinMin(ListOfPaths.get(i));

			}
		}
		
		int k = 0;
		while(k<ListOfPaths.size()){
			if(dur != this.getPathsDurationinMin(ListOfPaths.get(k))){
					ListOfPaths.remove(k);
					// fPlan deleted from ListOfPaths
					//  go top of loop
					k = 0;
			}else{
				k++;
			}
		}

		for(int i=0;i<ListOfPaths.size();i++){
			for(int j=0;j<ListOfPaths.get(i).size();j++){
				if(j==0){
					Result = Result +ListOfPaths.get(i).get(j).getFlightID()+"\t"+
							ListOfPaths.get(i).get(j).getDeparture()+"->"+ListOfPaths.get(i).get(j).getArrival();
				}else {
					Result = Result +"||"+ListOfPaths.get(i).get(j).getFlightID()+"\t"+
							ListOfPaths.get(i).get(j).getDeparture()+"->"+ListOfPaths.get(i).get(j).getArrival();
				}
				
			}
			
			long totalMin = this.getPathsDurationinMin(ListOfPaths.get(i));
			long hours = totalMin/60;
			long mins = totalMin-(hours*60);
			
			String m = Long.toString(mins);;
			String h = Long.toString(hours);
			if(mins<10){
				m = "0"+m;
			}
			if(hours<10){
				h = "0"+h;
			}
			int price = this.getPathsPrice(ListOfPaths.get(i));
			
			Result = Result +"\t" +h+":"+m+"/"+Integer.toString(price)+"\n";
		}
		
		Result = Result +"\n\n";
	}
	public void listCheaper(List<Stack<Flight>> ListOfPaths){
		Result  = "Command : "+this.getCommandName()+"\t"+this.getCommandDeptCity()+"->"+
				this.getCommandArrvlCity()+this.getStartDate()+"\t"+this.getAdditionalInfo()+"\n";
		
		int cPrice = Integer.parseInt(this.getAdditionalInfo());
		
		int k= 0;
		while(k<ListOfPaths.size()){
			if(!this.isCheaper(ListOfPaths.get(k),cPrice)){
					ListOfPaths.remove(k);
					// fPlan deleted from ListOfPaths
					//  go top of loop
					k = 0;
			}else{
				k++;
			}
		}
		
		for(int i=0;i<ListOfPaths.size();i++){
			for(int j=0;j<ListOfPaths.get(i).size();j++){
				if(j==0){
					Result = Result +ListOfPaths.get(i).get(j).getFlightID()+"\t"+
							ListOfPaths.get(i).get(j).getDeparture()+"->"+ListOfPaths.get(i).get(j).getArrival();
				}else {
					Result = Result +"||"+ListOfPaths.get(i).get(j).getFlightID()+"\t"+
							ListOfPaths.get(i).get(j).getDeparture()+"->"+ListOfPaths.get(i).get(j).getArrival();
				}
				
			}
			
			long totalMin = this.getPathsDurationinMin(ListOfPaths.get(i));
			long hours = totalMin/60;
			long mins = totalMin-(hours*60);
			
			String m = Long.toString(mins);;
			String h = Long.toString(hours);
			if(mins<10){
				m = "0"+m;
			}
			if(hours<10){
				h = "0"+h;
			}
			int price = this.getPathsPrice(ListOfPaths.get(i));
			
			Result = Result +"\t" +h+":"+m+"/"+Integer.toString(price)+"\n";
		}
		
		Result = Result +"\n\n";
	}
	public void listQuicker(List<Stack<Flight>> ListOfPaths){
		Result  = "Command : "+this.getCommandName()+"\t"+this.getCommandDeptCity()+"->"+
				this.getCommandArrvlCity()+this.getStartDate()+"\t"+this.getAdditionalInfo()+"\n";
		int k = 0;
		while(k<ListOfPaths.size()){
			if(!this.isQuicker(ListOfPaths.get(k),this.getAdditionalInfo())){
					
					// fPlan deleted from ListOfPaths
					//  go top of loop
					ListOfPaths.remove(k);
					k =0;
			}else{
				k++;
			}
		}

		
		for(int i=0;i<ListOfPaths.size();i++){
			for(int j=0;j<ListOfPaths.get(i).size();j++){
				if(j==0){
					Result = Result +ListOfPaths.get(i).get(j).getFlightID()+"\t"+
							ListOfPaths.get(i).get(j).getDeparture()+"->"+ListOfPaths.get(i).get(j).getArrival();
				}else {
					Result = Result +"||"+ListOfPaths.get(i).get(j).getFlightID()+"\t"+
							ListOfPaths.get(i).get(j).getDeparture()+"->"+ListOfPaths.get(i).get(j).getArrival();
				}
				
			}
			
			long totalMin = this.getPathsDurationinMin(ListOfPaths.get(i));
			long hours = totalMin/60;
			long mins = totalMin-(hours*60);
			
			String m = Long.toString(mins);;
			String h = Long.toString(hours);
			if(mins<10){
				m = "0"+m;
			}
			if(hours<10){
				h = "0"+h;
			}
			int price = this.getPathsPrice(ListOfPaths.get(i));
			
			Result = Result +"\t" +h+":"+m+"/"+Integer.toString(price)+"\n";
		}
		
		Result = Result +"\n\n";
		
		
	}
	
	public void listExcluding(List<Stack<Flight>> ListOfPaths){
		Result  = "Command : "+this.getCommandName()+"\t"+this.getCommandDeptCity()+"->"+
				this.getCommandArrvlCity()+this.getStartDate()+"\t"+this.getAdditionalInfo()+"\n";
		
		int k = 0;
		while(k<ListOfPaths.size()){
			if(!isExcluding(ListOfPaths.get(k), this.getAdditionalInfo())){
				ListOfPaths.remove(k);
				k = 0;
			}else{
				k++;
			}
		}
		
		
		for(int i=0;i<ListOfPaths.size();i++){
			for(int j=0;j<ListOfPaths.get(i).size();j++){
				if(j==0){
					Result = Result +ListOfPaths.get(i).get(j).getFlightID()+"\t"+
							ListOfPaths.get(i).get(j).getDeparture()+"->"+ListOfPaths.get(i).get(j).getArrival();
				}else {
					Result = Result +"||"+ListOfPaths.get(i).get(j).getFlightID()+"\t"+
							ListOfPaths.get(i).get(j).getDeparture()+"->"+ListOfPaths.get(i).get(j).getArrival();
				}
				
			}
			
			long totalMin = this.getPathsDurationinMin(ListOfPaths.get(i));
			long hours = totalMin/60;
			long mins = totalMin-(hours*60);
			
			String m = Long.toString(mins);;
			String h = Long.toString(hours);
			if(mins<10){
				m = "0"+m;
			}
			if(hours<10){
				h = "0"+h;
			}
			int price = this.getPathsPrice(ListOfPaths.get(i));
			
			Result = Result +"\t" +h+":"+m+"/"+Integer.toString(price)+"\n";
		}
		
		Result = Result +"\n\n";
	}
	
	public boolean isExcluding(Stack<Flight> fP1, String Info){
		for(int i=0;i<fP1.size();i++){
			if(fP1.get(i).getFlightID().substring(0, 2).equalsIgnoreCase(Info)){
				return false;
			}
		}
		
		return true;
	}
	
	public boolean isAllIncluding(Stack<Flight> fP1, String Info){
		for(int i=0;i<fP1.size();i++){
			if(fP1.get(i).getFlightID().substring(0, 2).equalsIgnoreCase(Info)){

			}else{
				return false;
			}
		}
		
		return true;
	}
	public void listOnlyFrom(List<Stack<Flight>> ListOfPaths){
		Result  = "Command : "+this.getCommandName()+"\t"+this.getCommandDeptCity()+"->"+
				this.getCommandArrvlCity()+this.getStartDate()+"\t"+this.getAdditionalInfo()+"\n";
		int k = 0;
		while(k<ListOfPaths.size()){
			if(!isAllIncluding(ListOfPaths.get(k), this.getAdditionalInfo())){
				ListOfPaths.remove(k);
				k = 0;
			}else{
				k++;
			}
		}
		
		
		for(int i=0;i<ListOfPaths.size();i++){
			for(int j=0;j<ListOfPaths.get(i).size();j++){
				if(j==0){
					Result = Result +ListOfPaths.get(i).get(j).getFlightID()+"\t"+
							ListOfPaths.get(i).get(j).getDeparture()+"->"+ListOfPaths.get(i).get(j).getArrival();
				}else {
					Result = Result +"||"+ListOfPaths.get(i).get(j).getFlightID()+"\t"+
							ListOfPaths.get(i).get(j).getDeparture()+"->"+ListOfPaths.get(i).get(j).getArrival();
				}
				
			}
			
			long totalMin = this.getPathsDurationinMin(ListOfPaths.get(i));
			long hours = totalMin/60;
			long mins = totalMin-(hours*60);
			
			String m = Long.toString(mins);;
			String h = Long.toString(hours);
			if(mins<10){
				m = "0"+m;
			}
			if(hours<10){
				h = "0"+h;
			}
			int price = this.getPathsPrice(ListOfPaths.get(i));
			
			Result = Result +"\t" +h+":"+m+"/"+Integer.toString(price)+"\n";
		}
		
		Result = Result +"\n\n";
	}
	public boolean isCheaper(Stack<Flight> fP1, int cPrice){
		int price1 = this.getPathsPrice(fP1);
		
		if(price1<cPrice){
			return true;
		}
		return false;
	}
	
	public boolean isQuicker(Stack<Flight> fP1, String AddInfo){
		String[] Tokens= AddInfo.split(" ");
		String[] Date1 = Tokens[0].split("/");
		int month1 = Integer.parseInt(Date1[1]);
		int day1 = Integer.parseInt(Date1[0]);
		String[] dayTime = Tokens[1].split(":");
		int dayTime1 = (Integer.parseInt(dayTime[0])*60) + Integer.parseInt(dayTime[1]);
		int temp1 = (month1*30*24*60) + (day1*24*60) +dayTime1;
		
		
		String[] Date2 = fP1.peek().getDate().split("/");
		int month2 = Integer.parseInt(Date2[1]);
		int day2 = Integer.parseInt(Date2[0]);
		int dayTime2 = fP1.peek().getArrivalTime();
		int temp2 = (month2*30*24*60) + (day2*24*60) + dayTime2;
		int totalTimeDif = temp1 - temp2;
		if(dayTime2>1440){
			if(day1 == day2 && month1 == month2){
				return false;
			}
		}
		if(totalTimeDif>=0){

			return true;
		}

		return false;
	}
	public int whichOneisProper(Stack<Flight> fP1, Stack<Flight>fP2){
		long duration1 = this.getPathsDurationinMin(fP1);
		long duration2 = this.getPathsDurationinMin(fP2);
		
		int price1 = this.getPathsPrice(fP1);
		int price2 = this.getPathsPrice(fP2);
		
		long diffDur = duration1 - duration2;
		int diffPrice = price1 -price2;
		if(diffDur<0 && diffPrice<0){
			return -1;
		}else if(diffDur>0 && diffPrice>0){
			return 1;
		}
		return 0;
	}
	
	@Override
	public String toString(){
		return "\nCommand Info" + "\nCommand Name :"+CommandName + "\nCommand Dept :" +CommandDeptCity 
				+"\nCommand ArrivalDept :" + CommandArrvlCity +"\nCommand StartDate :"+StartDate+
				"\nAdditionalInfo :"+ AdditionalInfo;
	}
	/**
	 * @return the commandName
	 */
	public String getCommandName() {
		return CommandName;
	}

	/**
	 * @param commandName the commandName to set
	 */
	public void setCommandName(String commandName) {
		CommandName = commandName;
	}

	/**
	 * @return the commandDeptCity
	 */
	public String getCommandDeptCity() {
		return CommandDeptCity;
	}

	/**
	 * @param commandDeptCity the commandDeptCity to set
	 */
	public void setCommandDeptCity(String commandDeptCity) {
		CommandDeptCity = commandDeptCity;
	}

	/**
	 * @return the commandArrvlCity
	 */
	public String getCommandArrvlCity() {
		return CommandArrvlCity;
	}

	/**
	 * @param commandArrvlCity the commandArrvlCity to set
	 */
	public void setCommandArrvlCity(String commandArrvlCity) {
		CommandArrvlCity = commandArrvlCity;
	}

	/**
	 * @return the startDate
	 */
	public String getStartDate() {
		return StartDate;
	}

	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(String startDate) {
		StartDate = startDate;
	}

	/**
	 * @return the additionalInfo
	 */
	public String getAdditionalInfo() {
		return AdditionalInfo;
	}

	/**
	 * @param additionalInfo the additionalInfo to set
	 */
	public void setAdditionalInfo(String additionalInfo) {
		AdditionalInfo = additionalInfo;
	}

	/**
	 * @return the result
	 */
	public String getResult() {
		return Result;
	}

	/**
	 * @param result the result to set
	 */
	public void setResult(String result) {
		Result = result;
	}
	

}
