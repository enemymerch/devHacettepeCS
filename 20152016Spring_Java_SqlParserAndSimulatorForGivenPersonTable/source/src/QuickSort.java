import java.util.ArrayList;

public class QuickSort {
	
	public ArrayList<Person> quickSort(ArrayList<Person> Array, int ch){
		switch(ch){
			case 1:	Array = quickSortbySocialSecurityNumber(Array);
					break;
			case 2:	Array = quickSortbyFirstName(Array);
					break;
			case 3: Array = quickSortbyLastName(Array);
					break;
			case 4: Array = quickSortbyCity(Array);
					break;
			case 5: Array = quickSortbyAdressLine1(Array);
					break;
			default:System.out.println("Couldn't sort the array !");
					break;
		}
		return Array;
	}
	
	private ArrayList<Person> quickSortbySocialSecurityNumber(ArrayList<Person> Array){
		if(Array.size() <= 1){
			return Array;
		}
		int Middle  = (int)Math.ceil((double) Array.size()/2);
		Person Pivot = Array.get(Middle);
		
		ArrayList<Person> Less = new ArrayList();
		ArrayList<Person> Greater = new ArrayList();
		
		for(int i=0;i < Array.size();i++){
			if(Array.get(i).compareTo(Pivot) > 0){
				if(i == Middle){
					continue;
				}
				Less.add(Array.get(i));
			}else{
				Greater.add(Array.get(i));
			}
		}
		return Concatenate(quickSortbySocialSecurityNumber(Less),Pivot,quickSortbySocialSecurityNumber(Greater),Array);
	}
	private ArrayList<Person> quickSortbyFirstName(ArrayList<Person> Array){
		
		if(Array.size() <= 1){
			return Array;
		}
		int Middle  = (int)Math.ceil((double) Array.size()/2);
		Person Pivot = Array.get(Middle);
		
		ArrayList<Person> Less = new ArrayList();
		ArrayList<Person> Greater = new ArrayList();
		
		for(int i=0;i < Array.size();i++){
			if(Array.get(i).getFirstName().compareToIgnoreCase(Pivot.getFirstName()) <= 0){
				if(i == Middle){
					continue;
				}
				Less.add(Array.get(i));
			}else{
				Greater.add(Array.get(i));
			}
		}
		return Concatenate(quickSortbyFirstName(Less),Pivot,quickSortbyFirstName(Greater),Array);
	}
	private ArrayList<Person> quickSortbyLastName(ArrayList<Person> Array){
		
		if(Array.size() <= 1){
			return Array;
		}
		int Middle  = (int)Math.ceil((double) Array.size()/2);
		Person Pivot = Array.get(Middle);
		
		ArrayList<Person> Less = new ArrayList();
		ArrayList<Person> Greater = new ArrayList();
		
		for(int i=0;i < Array.size();i++){
			if(Array.get(i).getLastName().compareToIgnoreCase(Pivot.getLastName()) <= 0){
				if(i == Middle){
					continue;
				}
				Less.add(Array.get(i));
			}else{
				Greater.add(Array.get(i));
			}
		}
		return Concatenate(quickSortbyLastName(Less),Pivot,quickSortbyLastName(Greater),Array);
	}
	private ArrayList<Person> quickSortbyCity(ArrayList<Person> Array){
		
		if(Array.size() <= 1){
			return Array;
		}
		int Middle  = (int)Math.ceil((double) Array.size()/2);
		Person Pivot = Array.get(Middle);
		
		ArrayList<Person> Less = new ArrayList();
		ArrayList<Person> Greater = new ArrayList();
		
		for(int i=0;i < Array.size();i++){
			if(Array.get(i).getCity().compareToIgnoreCase(Pivot.getCity()) <= 0){
				if(i == Middle){
					continue;
				}
				Less.add(Array.get(i));
			}else{
				Greater.add(Array.get(i));
			}
		}
		return Concatenate(quickSortbyCity(Less),Pivot,quickSortbyCity(Greater),Array);
	}	
	private ArrayList<Person> quickSortbyAdressLine1(ArrayList<Person> Array){
		
		if(Array.size() <= 1){
			return Array;
		}
		int Middle  = (int)Math.ceil((double) Array.size()/2);
		Person Pivot = Array.get(Middle);
		
		ArrayList<Person> Less = new ArrayList();
		ArrayList<Person> Greater = new ArrayList();
		
		for(int i=0;i < Array.size();i++){
			if(Array.get(i).getAdressLine1().compareToIgnoreCase(Pivot.getAdressLine1()) <= 0){
				if(i == Middle){
					continue;
				}
				Less.add(Array.get(i));
			}else{
				Greater.add(Array.get(i));
			}
		}
		return Concatenate(quickSortbyAdressLine1(Less),Pivot,quickSortbyAdressLine1(Greater),Array);
	}
	
	private ArrayList<Person> Concatenate(ArrayList<Person> Less, Person Pivot, ArrayList<Person> Greater, ArrayList<Person> List){
		List.clear();
		
		for(int i=0;i< Less.size();i++){
			List.add(Less.get(i));
		}
		List.add(Pivot);
		
		for(int i=0;i<Greater.size();i++){
			List.add(Greater.get(i));
		}
		return List;
	}
}
