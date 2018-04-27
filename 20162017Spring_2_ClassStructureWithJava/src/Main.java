


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import controller.*;


public class Main {

	public static void main(String[] args) {
		String itemTxtDir = args[1];
		String userTxtDir = args[0];
		
		Controller mainController = new Controller(itemTxtDir, userTxtDir);
		
		FileReader fReader = null;
		BufferedReader bufReader = null;
		
		try{
			fReader = new FileReader(args[2]);
			bufReader = new BufferedReader(fReader);
			
			String line;
			
			while((line = bufReader.readLine()) != null){
				String[] tokens = line.split("\t");
				
				try{
					DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
					
					switch(tokens[0]){
						case "ADDCUSTOMER":
							mainController.AddCustomer(tokens[1], tokens[2], tokens[3] , dateFormat.parse(tokens[4]), Double.parseDouble(tokens[5]), tokens[6]);
							break;
						case "SHOWCUSTOMER":
							mainController.ShowCustomer(tokens[1], tokens[2]);
							break;
						case "SHOWCUSTOMERS":
							mainController.ShowCustomers(tokens[1]);
							break;
						case "SHOWADMININFO":
							mainController.ShowAdminInfo(tokens[1]);
							break;
						case "CREATECAMPAIGN":
							mainController.CreateCampaign(tokens[1], dateFormat.parse(tokens[2]) , dateFormat.parse(tokens[3]),
									tokens[4], Integer.parseInt(tokens[5]));
							break;
						case "ADDADMIN":
							mainController.AddAdmin(tokens[1], tokens[2], tokens[3], dateFormat.parse(tokens[4]), 
									Integer.parseInt(tokens[5]), tokens[6]);
							break;
						case "ADDTECH":
							Boolean isSenior = false;
							if(tokens[6].equals("1")){
								isSenior = true;
							}
							mainController.AddTech(tokens[1], tokens[2], tokens[3], dateFormat.parse(tokens[4]), 
									Integer.parseInt(tokens[5]), isSenior);
							break;
						case "LISTITEM":
							mainController.ListItem(tokens[1]);
							// TODO:
							break;
						case "SHOWITEMSLOWONSTOCK":
							// TODO:
							break;
						case "SHOWVIP":
							// TODO:
							break;
						case "DISPITEMSOF":
							// TODO:
							break;
						case "ADDITEM":
							// TODO:
							break;
						case "SHOWORDERS":
							// TODO:
							break;
						case "CHPASS":
							// TODO:
							break;
						case "DEPOSITMONEY":
							// TODO:
							break;
						case "SHOWCAMPAIGNS":
							// TODO:
							break;
						case "ADDTOCART":
							// TODO:
							break;
						case "EMPTYCART":
							// TODO:
							break;
						case "ORDER":
							// TODO:
							break;
						default:
							throw new IOException("There's no command type such as :" + tokens[0]);
					}
				}catch(IOException e){
					System.out.println(e.getMessage());
				}
			}
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
	}

}
