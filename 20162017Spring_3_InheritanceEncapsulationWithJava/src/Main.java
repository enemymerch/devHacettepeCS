import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.assgn3.userCollection.UserCollection;


/**
 * Basic Explanation::
 * main method,
 * 1- starts with reading command line arguments (argument[0] corresponds to "users.txt", argument[1] cýrresponds to "commands.txt"),
 * 2- creates a instance of userCollection (instance name : userController), and initializes users !
 * 3- Reads commands.txt through BufferedReader ,
 * 4- all the commands that readed will be splitted and executed in a switch-case block !
 * @author mcany
 *
 */
public class Main {
	
	public static void main(String[] args){
		// Assigning args to variables
		String users = args[0];
		String commands = args[1];
		
		// Creating userController and initializing all default users into the system
		UserCollection userController = new UserCollection();
		userController.initializeUsers(users);
		
		
		// Going to start reading from "commands.txt" file and execution of commands
		
		FileReader fReader = null;
		BufferedReader bufReader = null;
		
		try{
			fReader = new FileReader(commands);
			bufReader = new BufferedReader(fReader);
			
			String line;
			
			while((line = bufReader.readLine()) != null){
				String[] tokens = line.split("\t");
				try{
					DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
					Date dateOfBirth = new Date();
					dateFormat.clone();
					switch(tokens[0]){
						case "ADDUSER":
							dateOfBirth = dateFormat.parse(tokens[4]);
							userController.addUser(tokens[1], tokens[2], tokens[3], dateOfBirth, tokens[5]);
							break;
						case "REMOVEUSER":
							userController.removeUser(Integer.parseInt(tokens[1]));
							break;
						case "SIGNIN":
							userController.signIn(tokens[1], tokens[2]);
							break;
						case "LISTUSERS":
							userController.listUsers();
							break;
						case "UPDATEPROFILE":
							dateOfBirth = dateFormat.parse(tokens[2]);
							userController.updateProfile(tokens[1], dateOfBirth, tokens[3]);
							break;
						case "CHPASS":
							userController.changePassword(tokens[1], tokens[2]);
							break;
						case "ADDFRIEND":
							userController.addFriend(tokens[1]);
							break;
						case "REMOVEFRIEND":
							userController.removeFriend(tokens[1]);
							break;
						case "LISTFRIENDS":
							userController.listFriends();
							break;
						case "ADDPOST-TEXT":
							userController.addTextPost(tokens[1], Double.parseDouble(tokens[2]), Double.parseDouble(tokens[3]), tokens[4]);
							break;
						case "ADDPOST-IMAGE":
							String[] resolution = tokens[6].split("x");
							int imageWidth = Integer.parseInt(resolution[0]);
							int imageHeight = Integer.parseInt(resolution[1]);
							userController.addImagePost(tokens[1], Double.parseDouble(tokens[2]), 
									Double.parseDouble(tokens[3]), tokens[4], 
									tokens[5], imageWidth, imageHeight);
							break;
						case "ADDPOST-VIDEO":
							userController.addVideoPost(tokens[1], Double.parseDouble(tokens[2])
									, Double.parseDouble(tokens[3]), tokens[4]
									, tokens[5], Integer.parseInt(tokens[6]));
							break;
						case "REMOVELASTPOST":
							userController.removeLastPost();
							break;
						case "SHOWPOSTS":
							userController.showPosts(tokens[1]);
							break;
						case "BLOCK":
							userController.block(tokens[1]);
							break;
						case "SHOWBLOCKEDFRIENDS":
							userController.showBlockedFriends();
							break;
						case "SHOWBLOCKEDUSERS":
							userController.showBlockedUsers();
							break;
						case "UNBLOCK":
							userController.unblock(tokens[1]);
							break;
						case "SIGNOUT":
							userController.signOut();
							break;
						default:
							throw new IOException("There's no command type such as :" + tokens[0]);
					}
				}catch(IOException e){
					System.out.println(e.getMessage());
				}
			}
			
			fReader.close();
			bufReader.close();
			
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}
 	}
}
