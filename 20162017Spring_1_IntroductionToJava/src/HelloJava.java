import java.io.*;
import java.util.Scanner;

public class HelloJava {
	
	public HelloJava(){
		// Nothing to do here.
	}

	public static void main(String[] args) {
		
		HelloJava obj = new HelloJava();
		
		// First things first
		// Going to start reading the "input.txt"
		String fileDir =  "input.txt";
		try{
			
			// Creating a scanner object
			Scanner scanner = new Scanner(new File(fileDir));
			
			
			// Going to execute all commands from "input.txt" within a "while-loop"
			
			while(scanner.hasNextLine()){ //loop-end if there's no-line
				
				String line = scanner.nextLine(); // A Line from a input.txt file
				
				String[] tokens = obj.trim(line.split(" ")); // Array of tokens from a line
				
				try{
				switch (tokens[0]) {
					case "IntegrateReimann": // Command is :IntegrateReimann
						obj.IntegrateRiemann(Double.parseDouble(tokens[2]), Double.parseDouble(tokens[3]), Integer.parseInt(tokens[4]), tokens[1]); 
					break;
					case "Arcsinh":  // Command is :Arcsinh
						obj.Arcsinh(Double.parseDouble(tokens[1]));
						break;
					case "Armstrong": // Command is :Armstrong
						obj.Armstrong(Integer.parseInt(tokens[1]));
						break;
				default:
					throw new IllegalArgumentException("Command is not a known!\n");
				}
				}
				catch(IllegalArgumentException e){
					System.out.println("There's a error in the line!\n Line : "+line+"\n" + e.toString());
				}
			}
			

			// Going to close the "input.txt" file.
			scanner.close();
		}
		catch(FileNotFoundException e){
			System.out.println("No such file found.\n Dir : " + fileDir);
		}
		
	}
	
	
	/**
	 * @param strArray:
	 * returns: type(String[])
	 * takes a array of strings and returns the trimed array of same string's 
	 */
	private String[] trim(String[] strArray){
		for(int i = 0; i< strArray.length; i++){
			strArray[i] = strArray[i].trim();
		}
		return strArray;
	}

	/**
	 * Riemann Sum
	 * @param lowerBound : lower Bound for integral
	 * @param upperBound : upper Bound for integral
	 * @param partitionNumber : number of total partitions
	 * @param functionName : func1 - func2- func3(arcsinh(x))
	 * @return the result ot middle sum of Riemann formula for given parameters
	 */
	private void IntegrateRiemann(double lowerBound, double upperBound, int partitionNumber, String functionName ){
		
		// Init. all needed variables for calculations
		double Result = 0.0;
		double interval =( (upperBound - lowerBound)/partitionNumber);
		double tempLowerBound = lowerBound;
		double tempUpperBound = lowerBound+interval;
		int funcType= -1;
		
		
		// Checking the l-u Bounds
		if(lowerBound >= upperBound){
			throw new IllegalArgumentException("lower-upper Bound not suitable for calculations! : lower bound is bigger than or equal to upper bound");
		}
		
		
		
		// Checking the functionName
		switch(functionName){
			case "Func1":
				funcType = 1; // Error-Free  Assign funct Type
				break;
			case "Func2":
				funcType = 2; // Error-Free  Assign funct Type
				break;
			case "Func3":
				/*
				 *  Checking if the parameters are error-free
				 *  |x| < 1
				 *	
				 */
				if(lowerBound >= 1 || lowerBound <= -1 || upperBound >= 1 || upperBound <= -1){
					throw new IllegalArgumentException("In IntegrateRiemann :lower-upper Bound not suitable for calculations for arcsinh(x) ");
				}else{
					funcType = 3; // Error-Free  Assign funct Type	
				}
				break;
			default:
				// Illegal function type
				throw new IllegalArgumentException("Illegal type of function");
		}
		
		// Calculating the integral between [lowerBound, upperBound]

		for(int i = 1; i <= partitionNumber; i++){
			double x = ( tempLowerBound + tempUpperBound) / 2;
			
			Result = Result + getArea(x, interval, funcType);
			//System.out.println(interval +" "+tempLowerBound + " " + tempUpperBound+" "+ x  + " "+ getArea(x,interval, funcType));
			
			tempLowerBound = tempLowerBound + interval;
			tempUpperBound = tempUpperBound + interval;
		}
		
		
		// Output
		
		System.out.println("IntegrateReinmann "+ functionName + " "+ lowerBound + " " + upperBound + " " + partitionNumber + " Result: " + Result);

	}
	
	
	/**
	 * 
	 * @param x
	 */
	private void Arcsinh(double x){
		double result = getArcsinh(x);
		
		System.out.println("Arcsinh "+ x +" Result: "+ result);
	}
	
	/**
	 * 
	 * @param x
	 * @return
	 */
	private void Armstrong(int x){
		
		System.out.print("Armstrong "+x+" Results:");
		
		int upperBound = (int) Math.pow(10, x+1);// Upper bound
		
		for(int i=0; i < upperBound; i++){
			if(isArmstrong(i, x)){
				System.out.print(" "+i);
			}
		}
	}
	
	
	/**
	 * 
	 * @param x
	 * @param interval
	 * @param funcType
	 * @return
	 */
	private double getArea(double x, double interval, int funcType){
		// Checking functinon Type for calculation
		double y = 0.0;
		switch(funcType){
			case 1:
				y = func1(x); 
				break;
			case 2:
				y = func2(x);
				break;
			case 3:
				y = getArcsinh(x);
				break;
			default:
				y = 0;
				break;
		}
		
		return y*interval;
	}
	
	
	
	/**
	 * @param x
	 * @return the value of func1(x) -- x^2 - x + 3 
	 */
	private double func1(double x){
		double temp = Math.pow(x, 2);
		temp = temp - x;
		temp = temp + 3.0;
		return temp;
	}
	
	
	/**
	 * @param x
	 * @return the value of func2(x) -- (3 sin(x)-4)^^2
	 */
	private double func2(double x){
		double temp = Math.sin(x);
		temp = temp*3.0;
		temp = temp - 4.0;
		temp = Math.pow(temp, 2);
		return temp;
	}
	
	/**
	 * @param x
	 * @return the value of func3(x)  --  arcsinh(x) |x| < 1 
	 */
	private double getArcsinh(double x){
		
		// Going to add up first 30 terms of the Series.
		
		if (x >=1 || x<=-1){//  |x| < 1 ?
			throw new IllegalArgumentException("parameter is not suitable for arcsinh(x) calculations.");
		}
		
		double result = 0.0;
		
		for(int i=0; i<30; i++){
			double temp1 = Math.pow(-1, i) * factoriel(2*i);
			double temp2 = Math.pow(4, i) * Math.pow(factoriel(i), 2) * ((2*i)+1);
			double temp3 = Math.pow(x, ((2*i)+1));
			result += (temp1/temp2)*temp3; 
		}
		
		return result;
	}



	
	/**
	 * 
	 * @param x
	 * @return if x is a armstrong number ? 
	 */
	private boolean isArmstrong(int x, int digitNumber){
		int temp = x;
		//int counter = 0;
		int total = 0;
		
		while(temp>0){// Going to calculate the if abc =? (a*a*a + b*b*b + c*c*c) 
			int digit = temp%10;// First digit !
			
			total = total + ((int)(Math.pow(digit, digitNumber)));// Calculating the total
			
			// Now we divide by 10 to get to the next digit!
			temp = temp/10;
		}
		// Now we have (a*a*a + b*b*b + c*c*c), going to check if its equal to abc
		if(total == x){
			return true;
		}
		
		return false;
	}
	
	/**
	 * 
	 * @param x
	 * @return basic factoriel calculation
	 */
	private double factoriel(double x){
		if(x==0){
			return 1;
		}
		return x*factoriel(x-1);
	}
	
}


