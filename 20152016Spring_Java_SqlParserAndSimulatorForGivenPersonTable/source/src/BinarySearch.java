import java.util.ArrayList;

public class BinarySearch {
	public ArrayList<Person> binarySearch(ArrayList<Person> Array,  String SearchVal, int ch){
		
		if(ch == 1){
			return LowerbinarySearchonSocialSecurityNumber(Array, SearchVal);
		}else if(ch == 2){
			return UpperbinarySearchonFirstName(Array, SearchVal);
		}else if(ch == 3){
			return binarySearchonFirstName(Array, SearchVal);
		}else if(ch == 4){
			return binarySearchonLastName(Array, SearchVal);
		}else if(ch == 5){
			return binarySearchonCity(Array, SearchVal);
		}else if(ch == 6){
			return binarySearchonAdressLine1(Array, SearchVal);
		}else{
			System.out.println("Couldn't search !, returning null");
		}
		return null;
	}



	private ArrayList<Person> binarySearchonAdressLine1(ArrayList<Person> array, String searchVal) {
		QuickSort Sorter = new QuickSort();
		/*
		 * Sort on adressline1
		 */
		array = Sorter.quickSort(array, 5);
		
		int rightIndex = array.size()-1;
		int leftIndex = 0;
		
		if(array.isEmpty()){
			return array;
		}
		
		if(isStartsWith(array.get(leftIndex).getAdressLine1(),searchVal)){
			/*
			 * searchVal found at first record 
			 * create new array  and add the matched ones
			 */
			ArrayList<Person> nArray = new ArrayList<Person>();
			for(int i=0;i<array.size() && isStartsWith(array.get(i).getAdressLine1(),searchVal);i++){
				nArray.add(array.get(i));
			}
			return nArray;
		}
		if(searchVal.compareToIgnoreCase(array.get(rightIndex).getAdressLine1()) > 0){
			/*
			 * searchVal is bigger than the last elements AdressLine1
			 * return an empty array
			 */
			array.clear();
			return array;
		}
		int middleIndex = (rightIndex+leftIndex)/2;
		while(!isStartsWith(array.get(middleIndex).getAdressLine1(),searchVal)){
			if(middleIndex == 0){
				array.clear();
				return array;
			}
			if(searchVal.compareToIgnoreCase(array.get(middleIndex).getAdressLine1()) > 0){
				/*
				 * searchVal is bigger than middleIndex's AdressLine1
				 */
				leftIndex = middleIndex;
				middleIndex = (rightIndex+leftIndex)/2;
			}else{
				rightIndex = middleIndex;
				middleIndex = (rightIndex+leftIndex)/2;
			}
		}
		/*
		 * while loop is over
		 * so , middleIndex element's AdressLine1 starts with searchVal
		 * Find the matched ones and add 
		 */
		ArrayList<Person> nArray = new ArrayList<Person>();
		/*
		 * Adding the matched elements at the left side of index that searchVal found
		 */

		for(int i=middleIndex;i>=0;i--){
			if(isStartsWith(array.get(i).getAdressLine1(),searchVal)){
				nArray.add(array.get(i));
			}else{
				break;
			}
		}
			/*
			 * Adding the matched elements at the right side of index that searchVal found
			 */
		for(int i=middleIndex+1;i<array.size();i++){
			if(isStartsWith(array.get(i).getAdressLine1(),searchVal)){
					nArray.add(array.get(i));
			}else{
				break;
			}
		}

		
		return nArray;
	}

	private ArrayList<Person> binarySearchonCity(ArrayList<Person> array, String searchVal) {
		QuickSort Sorter = new QuickSort();
		array = Sorter.quickSort(array, 4);
		int rightIndex = array.size()-1;
		int leftIndex = 0;
		
		if(array.isEmpty()){
			return array;
		}
		
		if(isStartsWith(array.get(leftIndex).getLastName(),searchVal)){
			/*
			 * searchVal found at first record 
			 * create new array  and add the matched ones
			 */
			ArrayList<Person> nArray = new ArrayList<Person>();
			for(int i=0;i<array.size() && isStartsWith(array.get(i).getCity(),searchVal);i++){
				nArray.add(array.get(i));
			}
			return nArray;
		}
		if(searchVal.compareToIgnoreCase(array.get(rightIndex).getCity()) > 0){
			/*
			 * searchVal is bigger than the last elements City
			 * return an empty array
			 */
			array.clear();
			return array;
		}
		int middleIndex = (rightIndex+leftIndex)/2;
		while(!isStartsWith(array.get(middleIndex).getCity(),searchVal)){
			if(middleIndex == 0){
				array.clear();
				return array;
			}
			if(searchVal.compareToIgnoreCase(array.get(middleIndex).getCity()) > 0){
				/*
				 * searchVal is bigger than middleIndex's City
				 */
				leftIndex = middleIndex;
				middleIndex = (rightIndex+leftIndex)/2;
			}else{
				rightIndex = middleIndex;
				middleIndex = (rightIndex+leftIndex)/2;
			}
		}
		/*
		 * while loop is over
		 * so , middleIndex element's City starts with searchVal
		 * Find the matched ones and add 
		 */
		ArrayList<Person> nArray = new ArrayList<Person>();
		/*
		 * Adding the matched elements at the left side of index that searchVal found
		 */

		for(int i=middleIndex;i>=0;i--){
			if(isStartsWith(array.get(i).getCity(),searchVal)){
				nArray.add(array.get(i));
			}else{
				break;
			}
		}
			/*
			 * Adding the matched elements at the right side of index that searchVal found
			 */
		for(int i=middleIndex+1;i<array.size();i++){
			if(isStartsWith(array.get(i).getCity(),searchVal)){
					nArray.add(array.get(i));
			}else{
				break;
			}
		}

		
		return nArray;
	}

	private ArrayList<Person> binarySearchonLastName(ArrayList<Person> array, String searchVal) {
		QuickSort Sorter = new QuickSort();
		array = Sorter.quickSort(array, 3);
		
		int rightIndex = array.size()-1;
		int leftIndex = 0;
		if(array.isEmpty()){
			return array;
		}
		
		if(isStartsWith(array.get(leftIndex).getLastName(),searchVal)){
			/*
			 * searchVal found at first record 
			 * create new array  and add the matched ones
			 */
			ArrayList<Person> nArray = new ArrayList<Person>();
			for(int i=0;i<array.size() && isStartsWith(array.get(i).getLastName(),searchVal);i++){
				nArray.add(array.get(i));
			}
			return nArray;
		}
		if(searchVal.compareToIgnoreCase(array.get(rightIndex).getLastName()) > 0){
			/*
			 * searchVal is bigger than the last elements LastName
			 * return an empty array
			 */
			array.clear();
			return array;
		}
		int middleIndex = (rightIndex+leftIndex)/2;
		while(!isStartsWith(array.get(middleIndex).getLastName(),searchVal)){
			if(middleIndex == 0){
				array.clear();
				return array;
			}
			if(searchVal.compareToIgnoreCase(array.get(middleIndex).getLastName()) > 0){
				/*
				 * searchVal is bigger than middleIndex's LastName
				 */
				leftIndex = middleIndex;
				middleIndex = (rightIndex+leftIndex)/2;
			}else{
				rightIndex = middleIndex;
				middleIndex = (rightIndex+leftIndex)/2;
			}
		}
		/*
		 * while loop is over
		 * so , middleIndex element's lastName starts with searchVal
		 * Find the matched ones and add them to nArray !
		 */
		ArrayList<Person> nArray = new ArrayList<Person>();
		/*
		 * Adding the matched elements at the left side of index that searchVal found
		 */

		for(int i=middleIndex;i>=0;i--){
			if(isStartsWith(array.get(i).getLastName(),searchVal)){
				nArray.add(array.get(i));
			}else{
				break;
			}
		}
			/*
			 * Adding the matched elements at the right side of index that searchVal found
			 */
		for(int i=middleIndex+1;i<array.size();i++){
			if(isStartsWith(array.get(i).getLastName(),searchVal)){
					nArray.add(array.get(i));
			}else{
				break;
			}
		}

		
		return nArray;
	}

	private ArrayList<Person> binarySearchonFirstName(ArrayList<Person> array, String searchVal) {
		QuickSort Sorter = new QuickSort();
		array = Sorter.quickSort(array, 2);
		
		int rightIndex = array.size()-1;
		int leftIndex = 0;
		if(array.isEmpty()){
			return array;
		}
		
		if(isStartsWith(array.get(leftIndex).getFirstName(),searchVal)){
			/*
			 * searchVal found at first record 
			 * create new array  and add the matched ones
			 */
			ArrayList<Person> nArray = new ArrayList<Person>();
			for(int i=0;i<array.size() && isStartsWith(array.get(i).getFirstName(),searchVal);i++){
				nArray.add(array.get(i));
			}
			return nArray;
		}
		if(searchVal.compareToIgnoreCase(array.get(rightIndex).getFirstName()) > 0){
			/*
			 * searchVal is bigger than the last elements firstName
			 * return an empty array
			 */
			array.clear();
			return array;
		}
		int middleIndex = (rightIndex+leftIndex)/2;
		while(!isStartsWith(array.get(middleIndex).getFirstName(),searchVal)){
			if(middleIndex == 0){
				array.clear();
				return array;
			}
			if(searchVal.compareToIgnoreCase(array.get(middleIndex).getFirstName()) > 0){
				/*
				 * searchVal is bigger than middleIndex's firstName
				 */
				leftIndex = middleIndex;
				middleIndex = (rightIndex+leftIndex)/2;
			}else{
				rightIndex = middleIndex;
				middleIndex = (rightIndex+leftIndex)/2;
			}
		}
		/*
		 * while loop is over
		 * so , middleIndex element's firstName starts with searchVal
		 * Find the matched ones and add 
		 */
		ArrayList<Person> nArray = new ArrayList<Person>();
		/*
		 * Adding the matched elements at the left side of index that searchVal found
		 */
		for(int i=middleIndex;i>=0;i--){
			if(isStartsWith(array.get(i).getFirstName(),searchVal)){
				nArray.add(array.get(i));
			}else{
				break;
			}
		}
			/*
			 * Adding the matched elements at the right side of index that searchVal found
			 */
		for(int i=middleIndex+1;i<array.size();i++){
			if(isStartsWith(array.get(i).getFirstName(),searchVal)){
					nArray.add(array.get(i));
			}else{
				break;
			}
		}

		
		return nArray;
	}
	
	private boolean isStartsWith(String str1, String str2){
		if(str2.length()> str1.length())
			return false;
		String temp = str1.substring(0,str2.length());
		return temp.equalsIgnoreCase(str2);
	}

	private ArrayList<Person> LowerbinarySearchonSocialSecurityNumber(ArrayList<Person> array, String searchVal) {
		QuickSort Sorter = new QuickSort();
		array = Sorter.quickSort(array, 1);
		int SearchVal = Integer.parseInt(searchVal.trim());
		int rightIndex = array.size()-1;
		int leftIndex = 0;
		if(array.isEmpty()){
			return array;
		}
		
		if(array.get(leftIndex).getSocialSecurityNumber() >= SearchVal ){
			/* SearchVal is under or equals  first SocialSecurityNumber
			 * return an empty array
			 */
			array.clear();
			return array;
		}else if(array.get(rightIndex).getSocialSecurityNumber() == SearchVal){
			/*  SearchVal equals to biggest SocialSecurityNumber
			 *  delete last element and 
			 *  return the array 
			 */
			array.remove(rightIndex);
			return array;
		}else if(array.get(rightIndex).getSocialSecurityNumber() < SearchVal){
			/* SearchVal is bigger then every SocialSecurityNumber
			 * return the all array
			 */
			return array;
		}
		/*
		 * SearchVal is between 
		 * array.get(leftIndex) < SearchVal < array.get(rightIndex)
		 * Start BinarySearch
		 */
		int middleIndex = (rightIndex + leftIndex ) / 2; 
		while(array.get(middleIndex).getSocialSecurityNumber() != SearchVal){
			if(array.get(middleIndex).getSocialSecurityNumber() < SearchVal){
				/*
				 * SearchVal is still between
				 * array.get(middleIndex) < SearchVal < array.get(rightIndex)
				 * continue searching ...
				 */
				if(array.get(middleIndex+1).getSocialSecurityNumber() > SearchVal){
					/* Found the range for SearchVal
					 * array.get(middleIndex) < SearchVal < array.get(middleIndex+1)
					 * Create new array and add the matched Person records
					 * return the new array
					 */
					ArrayList<Person> nArray = new ArrayList<Person>();
					for(int i = 0;i<=middleIndex;i++){
						nArray.add(array.get(i));
					}
					return nArray;
				}else{
					/*
					 * SearchVal is still between
					 * array.get(middleIndex) < SearchVal < array.get(rightIndex)
					 * increase the lowerIndex
					 */
					leftIndex = middleIndex;
					middleIndex = (rightIndex + leftIndex ) / 2; 
				}
			}else{
				/*
				 * SearchVal is still between
				 * array.get(leftIndex) < SearchVal < array.get(middleIndex)
				 * continue searching ...
				 */
				if(array.get(middleIndex-1).getSocialSecurityNumber() < SearchVal){
					/* Found the range for SearchVal
					 * array.get(middleIndex) < SearchVal < array.get(middleIndex+1)
					 * Create new array and add the matched Person records
					 * return the new array
					 */
					ArrayList<Person> nArray = new ArrayList<Person>();
					for(int i = 0;i<middleIndex;i++){
						nArray.add(array.get(i));
					}
					return nArray;
				}else{
					/*
					 * SearchVal is still between
					 * array.get(middleIndex) < SearchVal < array.get(rightIndex)
					 * increase the lowerIndex
					 */
					rightIndex = middleIndex;
					middleIndex = (rightIndex + leftIndex ) / 2; 
				}
				
			}
		}
		/*
		 * while loop is over 
		 * So , array.get(middleIndex).getSocialSecurityNumber() is equals to  SearchVal
		 * Create new array and add the matched person records
		 * return the array
		 */
		ArrayList<Person> nArray = new ArrayList<Person>();
		for(int i = 0;i<middleIndex;i++){
			nArray.add(array.get(i));
		}
		return nArray;
	}
	private ArrayList<Person> UpperbinarySearchonFirstName(ArrayList<Person> array, String searchVal) {
		QuickSort Sorter = new QuickSort();
		array = Sorter.quickSort(array, 1);
		int SearchVal = Integer.parseInt(searchVal.trim());
		int rightIndex = array.size()-1;
		int leftIndex = 0;
		if(array.isEmpty()){
			return array;
		}
		
		if(array.get(leftIndex).getSocialSecurityNumber() >= SearchVal ){
			/* SearchVal is under or equals  first SocialSecurityNumber
			 */
			if(array.get(leftIndex).getSocialSecurityNumber() == SearchVal ){
				/*
				 * SearcVal is equals first SocialSecurityNumber
				 * remove the first element 
				 * return array
				 */
				array.remove(0);
				return array;
			}else{
				/*
				 * SearcVal is lower than first SocialSecurityNumber
				 * return array
				 */
				return array;
			}
		}else if(array.get(rightIndex).getSocialSecurityNumber() <= SearchVal){
			/*  SearchVal equals to biggest SocialSecurityNumber
			 *  return empty array 
			 */
			array.clear();
			return array;
		}
		/*
		 * SearchVal is between 
		 * array.get(leftIndex) < SearchVal < array.get(rightIndex)
		 * Start BinarySearch
		 */
		int middleIndex = (rightIndex + leftIndex ) / 2; 
		while(array.get(middleIndex).getSocialSecurityNumber() != SearchVal){
			if(array.get(middleIndex).getSocialSecurityNumber() < SearchVal){
				/*
				 * SearchVal is still between
				 * array.get(middleIndex) < SearchVal < array.get(rightIndex)
				 * continue searching ...
				 */
				if(array.get(middleIndex+1).getSocialSecurityNumber() > SearchVal){
					/* Found the range for SearchVal
					 * array.get(middleIndex) < SearchVal < array.get(middleIndex+1)
					 * Create new array and add the matched Person records
					 * return the new array
					 */
					ArrayList<Person> nArray = new ArrayList<Person>();
					for(int i = middleIndex;i<array.size();i++){
						nArray.add(array.get(i));
					}
					return nArray;
				}else{
					/*
					 * SearchVal is still between
					 * array.get(middleIndex) < SearchVal < array.get(rightIndex)
					 * increase the lowerIndex
					 */
					leftIndex = middleIndex;
					middleIndex = (rightIndex + leftIndex ) / 2; 
				}
			}else{
				/*
				 * SearchVal is still between
				 * array.get(leftIndex) < SearchVal < array.get(middleIndex)
				 * continue searching ...
				 */
				if(array.get(middleIndex-1).getSocialSecurityNumber() < SearchVal){
					/* Found the range for SearchVal
					 * array.get(middleIndex) < SearchVal < array.get(middleIndex+1)
					 * Create new array and add the matched Person records
					 * return the new array
					 */
					ArrayList<Person> nArray = new ArrayList<Person>();
					for(int i = middleIndex;i<array.size();i++){
						nArray.add(array.get(i));
					}
					return nArray;
				}else{
					/*
					 * SearchVal is still between
					 * array.get(middleIndex) < SearchVal < array.get(rightIndex)
					 * increase the lowerIndex
					 */
					rightIndex = middleIndex;
					middleIndex = (rightIndex + leftIndex ) / 2; 
				}
				
			}
		}
		/*
		 * while loop is over 
		 * So , array.get(middleIndex).getSocialSecurityNumber() is equals to  SearchVal
		 * Create new array and add the matched person records
		 * return the array
		 */
		ArrayList<Person> nArray = new ArrayList<Person>();
		for(int i = middleIndex+1;i<array.size();i++){
			nArray.add(array.get(i));
		}
		return nArray;
	}
}
