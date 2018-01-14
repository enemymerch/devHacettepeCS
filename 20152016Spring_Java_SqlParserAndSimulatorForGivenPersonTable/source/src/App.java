import java.util.ArrayList;
/*
 * There're 7 classes
 * App, BinarySearch, Command , QuickSort, Person , ReadFromFile and WriteToFile
 * Step 1- Read data and command files
 * Create Arraylist with data.csv ,  create command object with string line taken from commands.txt  
 * BinarySearch on ArrayList with the variables(SearcValues) in the command object !
 * Every binarysearching on Arraylist decreases the number of elements in the arraylist 
 * Until every needed range search is done ArrayList will have the needed person records inside !
 * (OuickSorting done inside BinarySearch method's !)
 */

public class App {
	public static void main(String[] argv){
		System.out.println("Selman hocam "
				+ "\nPiazza'da son perþembe günü gördüm "
				+ "\nSearch'de < ve > haricinde = 'de olabilirmiþ."
				+ "\nFöy'de ve verilen örnek.txt den yola çýkarak yaptým ödevimi"
				+ "\n= ve CID üzerinden arama yapmýyor kodum ."
				+ "\nAma geri kalan kýsýmlarda yaptýðým testlerde bir sorun bulamadým."
				+ "\nargv[0] == data.csv argv[1] == commands.txt");
		App app = new App();
		
		ReadFromFile Reader = new ReadFromFile(argv[1], argv[0]);
		WriteToFile Writer = new WriteToFile();
		String[] Data = Reader.ReadFromDataFile();
		String[] Commands = Reader.ReadFromCommandsFile();
		BinarySearch bSearch = new BinarySearch();
		ArrayList<Person> arrayPerson = app.CreateArrayList(Data);
		Command temp ;
		for(int i = 0;i<Commands.length;i++){
			temp = new Command(Commands[i]);
			arrayPerson = app.CreateArrayList(Data);
			long time1 = System.nanoTime();
			while(!temp.isCommandDone()){
				/*
				 * After searching for any criterion 
				 * for example: Searched for LowerSSNumber
				 * 			command object's LowerSocialSecurityNumberSearchVal must be : " "
				 */
				if(!temp.getLowerSocialSecurityNumberSearchVal().equals(" ")){
					// LowerSocialSecurityNumberSearch
					arrayPerson = bSearch.binarySearch(arrayPerson, temp.getLowerSocialSecurityNumberSearchVal(), 1);
					temp.setLowerSocialSecurityNumberSearchVal(" ");
				}else if(!temp.getUpperSocialSecurityNumberSearchVal().equals(" ")){
					//UpperSocialSecurityNumberSearch
					arrayPerson = bSearch.binarySearch(arrayPerson, temp.getUpperSocialSecurityNumberSearchVal(), 2);
					temp.setUpperSocialSecurityNumberSearchVal(" ");
				}else if(!temp.getFirstNameSearchVal().equals(" ")){
					//FirstNameSearch
					arrayPerson = bSearch.binarySearch(arrayPerson, temp.getFirstNameSearchVal(), 3);
					temp.setFirstNameSearchVal(" ");
				}else if(!temp.getLastNameSearchVal().equals(" ")){
					//LastNameSearch
					arrayPerson = bSearch.binarySearch(arrayPerson, temp.getLastNameSearchVal(), 4);
					temp.setLastNameSearchVal(" ");
				}else if(!temp.getCitySearchVal().equals(" ")){
					//CitySearch
					arrayPerson = bSearch.binarySearch(arrayPerson, temp.getCitySearchVal(), 5);
					temp.setCitySearchVal(" ");
				}else if(!temp.getAdressLine1SearchVal().equals(" ")){
					//AdressLine1
					arrayPerson = bSearch.binarySearch(arrayPerson, temp.getAdressLine1SearchVal(), 6);
					temp.setAdressLine1SearchVal(" ");
				}
			}
			long time2 = System.nanoTime();
			Writer.WriteToOutputFile(arrayPerson, temp, (time2-time1)/1000000, i);
			arrayPerson.clear();
		}

	}	
	public ArrayList<Person> CreateArrayList(String[] Data){
		/*
		 * Creates ArrayList with input taken from data.csv
		 */
		String[] tempTokens = new String[6];
		ArrayList<Person> tempList =  new ArrayList<Person>();
		for(int i=1;i<Data.length;i++){
			tempTokens = Data[i].split("\\|");		
			tempList.add(new Person(Integer.parseInt(tempTokens[0]), tempTokens[1],tempTokens[2],
							tempTokens[3], tempTokens[4], Integer.parseInt(tempTokens[5])));
		}
		return tempList;
	}
}
